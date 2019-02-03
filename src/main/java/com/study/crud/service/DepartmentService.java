package com.study.crud.service;

import com.study.crud.bean.Department;
import com.study.crud.dao.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @version : 1.0
 * @auther : Firewine
 * @mail ： 1451661318@qq.com
 * @Program Name: <br>
 * @Create : 2019-02-03-13:07
 * @Description :  <br/>
 */


@Service
public class DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;


    public List<Department> getDepts() {

        List<Department> list = departmentMapper.selectByExample(null);

        return list;
    }
}
