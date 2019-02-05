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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
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
     * 将单个批量  二合一，，进行操作
     * 批量删除 ，1-2-3
     * 单个删除 1
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/emp/{ids}",method = RequestMethod.DELETE)
    public Msg deleteEmpById(@PathVariable("ids")String  ids){

        if (ids.contains("-")){
            List<Integer> del_ids = new ArrayList<>();
            String[] strings = ids.split("-");
            //组装集合
            for( String id : strings){
                del_ids.add(Integer.parseInt(id));
            }
            employeeService.deleteBatch(del_ids);
        }else {
            Integer id = Integer.parseInt(ids);
            employeeService.deleteEmp(id);
        }

        return Msg.success();
    }
    /**
     * 如果直接发送ajax=PUT形式的请求
     * 封装的数据
     * Employee
     * [empId=1014, empName=null, gender=null, email=null, dId=null]
     *
     * 问题：
     * 请求体中有数据；
     * 但是Employee对象封装不上；
     * update tbl_emp  where emp_id = 1014;
     *
     * 原因：
     * Tomcat：
     * 		1、将请求体中的数据，封装一个map。
     * 		2、request.getParameter("empName")就会从这个map中取值。
     * 		3、SpringMVC封装POJO对象的时候。
     * 				会把POJO中每个属性的值，request.getParamter("email");
     * AJAX发送PUT请求引发的血案：
     * 		PUT请求，请求体中的数据，request.getParameter("empName")拿不到
     * 		Tomcat一看是PUT不会封装请求体中的数据为map，只有POST形式的请求才封装请求体为map
     * org.apache.catalina.connector.Request--parseParameters() (3111);
     *
     * protected String parseBodyMethods = "POST";
     * if( !getConnector().isParseBodyMethod(getMethod()) ) {
     success = true;
     return;
     }
     *
     *
     * 解决方案；
     * 我们要能支持直接发送PUT之类的请求还要封装请求体中的数据
     * 1、配置上HttpPutFormContentFilter；
     * 2、他的作用；将请求体中的数据解析包装成一个map。
     * 3、request被重新包装，request.getParameter()被重写，就会从自己封装的map中取数据
     * 员工更新方法
     * @param employee
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/emp/{empId}",method = RequestMethod.PUT)
    public Msg saveEmp(Employee employee){

        employeeService.updateEmp(employee);
        return Msg.success();
    }
    /**
     * 查询员工根据id
     * @param id
     * @return
     */
    @RequestMapping(value = "/emp/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Msg getEmp(@PathVariable("id") Integer id){

        Employee employee = employeeService.getEmp(id);
        return Msg.success().add("emp", employee);
    }
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
