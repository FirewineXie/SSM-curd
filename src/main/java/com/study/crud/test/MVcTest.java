package com.study.crud.test;

/**
 * Created by IntelliJ IDEA.
 *
 * @version : 1.0
 * @auther : Firewine
 * @mail ： 1451661318@qq.com
 * @Program Name: <br>
 * @Create : 2019-02-01-15:13
 * @Description :  使用spring测试模块提供的测试请求功能，测试curd请求的正确性
 *
 * spring4 测试的时候，需要servlet3.0的而支持
 */

import com.github.pagehelper.PageInfo;
import com.study.crud.bean.Employee;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration //自动配置文件外面的
@ContextConfiguration(locations = {"classpath:applicationContext.xml",
        "file:E:\\JAVA\\IdeaProjects\\SSM-curd\\web\\WEB-INF\\springmvc-servelt.xml"})
public class MVcTest {


    @Autowired
    WebApplicationContext context;
    //虚拟mvc请求，获取到处理结果
    MockMvc mockMvc;

    @Before
    public void initMockMvc(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

    }

    @Test
    public void testPage() throws Exception {
        //模拟请求，拿到返回值
        MvcResult result = (MvcResult) mockMvc.perform(MockMvcRequestBuilders.get("/emps").param("pn","1"))
                .andReturn();

        //请求成功后，请求域中会有pageInfo，我们可以获取pageInfo进行验证
        MockHttpServletRequest request = result.getRequest();

        PageInfo pi = (PageInfo) request.getAttribute("pageInfo");

        System.out.println("当前页码 ：" + pi.getPageNum());
        System.out.println("总页码：" + pi.getPages());
        System.out.println("总记录数：" + pi.getTotal());
        System.out.println("在页面㤇连续显示的页码");
        int[] nums = pi.getNavigatepageNums();
        for (int i : nums){
            System.out.print(" " + i);
        }

        //获取员工数据
        List<Employee> employeeList= pi.getList();

        for (Employee employee : employeeList){
            System.out.println("ID :" + employee.getEmpId() +"=====>Name ：" + employee.getEmpName());
        }

    }

}
