package com.study.crud.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.study.crud.bean.Employee;
import com.study.crud.bean.Msg;
import com.study.crud.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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
     * 导入jackon包
     * @param pn
     * @return
     */
    @ResponseBody
    @RequestMapping("/emps")
    public Msg getEmpsWithJson(@RequestParam(value = "pn",defaultValue = "1")Integer pn){

        // 这不是一个分页查询
        // 引入pagehelper 插件
        // 在查询之前需要调用,传入页码和每页的大小
        PageHelper.startPage(pn,5);
        //startpage 后面紧跟的这个查询就是一个分页查询
        List<Employee> emps =  employeeService.getALL();

        //使用pageInfo来包装结果，把这个交给页面
        //封装了详细的分页信息，包括我们查询出来的数据
        PageInfo page = new PageInfo(emps,5);
        //连续显示的页数5

        return Msg.success().add("pageInfo",page);
    }
    /**
     * 查询员工数据(分页查询)
     * @return
     */
//    @RequestMapping("/emps")
    public String getEmps(@RequestParam(value = "pn",defaultValue = "1")Integer pn, Model model){

        // 这不是一个分页查询
        // 引入pagehelper 插件
        // 在查询之前需要调用,传入页码和每页的大小
        PageHelper.startPage(pn,5);
        //startpage 后面紧跟的这个查询就是一个分页查询
       List<Employee> emps =  employeeService.getALL();

       //使用pageInfo来包装结果，把这个交给页面
        //封装了详细的分页信息，包括我们查询出来的数据
        PageInfo page = new PageInfo(emps,5);
        //连续显示的页数5

        model.addAttribute("pageInfo",page);

        return "list";
    }
}
