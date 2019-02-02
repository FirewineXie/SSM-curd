package com.study.crud.test;


import com.study.crud.bean.Department;
import com.study.crud.bean.Employee;
import com.study.crud.dao.DepartmentMapper;


import com.study.crud.dao.EmployeeMapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;


/**
 * Created by IntelliJ IDEA.
 *
 * @version : 1.0
 * @auther : Firewine
 * @mail ： 1451661318@qq.com
 * @Program Name: <br>
 * @Create : 2019-02-01-13:42
 * @Description :  测试dao层工作
 * 1.导入springTest模块
 * 2. 导入@ContextConfiguration 指定spring配置文件的位置
 * 3. 直接autowired 要使用的组件即可
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class MapperTest {

    @Autowired
    DepartmentMapper departmentMapper;

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    SqlSession sqlSession;
    /**
     * 测试departmentMapper
     * 推荐spring 的项目就可以使用Spring的单元测试，可以自动注入我们需要的组件
     */
    @Test
    public void testCRUD(){
//        //1. 创建springIOC容器
//        ApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");
//
//        //2. 从容器中获取mapper
//        DepartmentMapper bena = ioc.getBean(DepartmentMapper.class);

        System.out.println(departmentMapper);

        //1. 插入几个部门
//        departmentMapper.insertSelective(new Department(null,"开发部"));
//        departmentMapper.insertSelective(new Department(null, "测试部"));

        //2. 生成员工数据
//        employeeMapper.insertSelective(new Employee(null,"Jerry","M","Jerry@11.com",1));

        //3. 批量掺入员工，使用可以执行批量操作的SQLSession
//        for (){
//            employeeMapper.insertSelective(new Employee(null,"Jerry","M","Jerry@11.com",1));
//        }

        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);

        for (int i =0; i<1000;i++){
            String uid = UUID.randomUUID().toString().substring(0,5) + i;
            mapper.insertSelective(new Employee(null,uid,"M",uid+"@qq.com",1));
        }
        System.out.println("完成");
    }
}
