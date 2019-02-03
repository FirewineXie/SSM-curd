package com.study.crud.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.study.crud.bean.Employee;
import com.study.crud.bean.Msg;
import com.study.crud.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @version : 1.0
 * @auther : Firewine
 * @mail ： 1451661318@qq.com
 * @Program Name: <br>
 * @Create : 2019-02-01-14:55
 * @Description : 处理员工的CRUD请求
 */
@Controller
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    /**
     * 检查用户名是否可用
     *
     * @param empName
     * @return
     */
    @ResponseBody
    @RequestMapping("/checkuser")
    public Msg checkuse(@RequestParam("empName") String empName) {
        //先判断用户名是否是合法的表达式
        String regx = "(^[a-zA-Z0-9_-]{6,16}$)|([\\u2E80-\\u9FFF]{2,5}])";
        if (empName.matches(regx)) {
            return Msg.fail().add("va_msg", "用户名必须是6-16位数字或者字母组合或者2-5位中文");
        }
        //上面成功才数据库校验
        boolean b = employeeService.checkUser(empName);
        if (b) {
            return Msg.success();
        } else {
            return Msg.fail().add("va_msg", "用户名不可用");
        }
    }

    /**
     * 定义员工保存
     * <p>
     * 1.支持JSP303 校验
     * 2.导入Hibernate vaildator
     *
     * @return
     */
    @RequestMapping(value = "/emp", method = RequestMethod.POST)
    @ResponseBody
    public Msg saveEmp(@Valid Employee employee, BindingResult result) {

        if (result.hasErrors()) {
            //校验失败,应该在模态框中显示校验失败的错误信息
            Map<String ,Object> map = new HashMap<>();
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError fieldError: errors){
                System.out.println("错误的字段名"+fieldError.getField());
                System.out.println("错误信息" + fieldError.getDefaultMessage());
                map.put(fieldError.getField(),fieldError.getDefaultMessage());
            }
            return Msg.fail().add("errrorFields",map);
        } else {

            employeeService.saveEmp(employee);
            return Msg.success();
        }

    }

    /**
     * 导入jackon包
     *
     * @param pn
     * @return
     */
    @ResponseBody
    @RequestMapping("/emps")
    public Msg getEmpsWithJson(@RequestParam(value = "pn", defaultValue = "1") Integer pn) {

        // 这不是一个分页查询
        // 引入pagehelper 插件
        // 在查询之前需要调用,传入页码和每页的大小
        PageHelper.startPage(pn, 5);
        //startpage 后面紧跟的这个查询就是一个分页查询
        List<Employee> emps = employeeService.getALL();

        //使用pageInfo来包装结果，把这个交给页面
        //封装了详细的分页信息，包括我们查询出来的数据
        PageInfo page = new PageInfo(emps, 5);
        //连续显示的页数5

        return Msg.success().add("pageInfo", page);
    }

    /**
     * 查询员工数据(分页查询)
     *
     * @return
     */
//    @RequestMapping("/emps")
    public String getEmps(@RequestParam(value = "pn", defaultValue = "1") Integer pn, Model model) {

        // 这不是一个分页查询
        // 引入pagehelper 插件
        // 在查询之前需要调用,传入页码和每页的大小
        PageHelper.startPage(pn, 5);
        //startpage 后面紧跟的这个查询就是一个分页查询
        List<Employee> emps = employeeService.getALL();

        //使用pageInfo来包装结果，把这个交给页面
        //封装了详细的分页信息，包括我们查询出来的数据
        PageInfo page = new PageInfo(emps, 5);
        //连续显示的页数5

        model.addAttribute("pageInfo", page);

        return "list";
    }
}
