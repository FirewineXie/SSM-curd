# SS整合-CURD


## 功能点
1. 分页
2. 数据校验-jquery前端校验+JSR303后端校验
3. ajax
4. Rest风格的URI；使用HTTP协议请求方式的动词，来表示对资
源的操作（GET（查询）， POST（新增）， PUT（修改）， DELETE
（删除））

## 技术点
1. 基础框架-ssm（SpringMVC+Spring+MyBatis）
2. 数据库-MySQL
3. 前端框架-bootstrap快速搭建简洁美观的界面
4. 项目的依赖管理-Maven
5. 分页-pagehelper
6. 逆向工程-MyBatis Generator

## 基础环境搭建 -使用软件idea
**我习惯先创建web工程，再添加maven框架**
1. 创建一个web工程
2. 增加maven框架支持
3. 增加依赖的jar包


## SSM整合的配置文件
1. 编写web.xml
2. 编写spring配置文件
3. 编写springmvc配置文件
4. 编写mybatis 配置文件
5. 利用mybatis的逆向工程生成对应的bean和mapper
6. mapper进行测试


## 业务

### 查询页面
1. 访问index.jsp页面
2. 页面发送出查询员工列表请求
3. EmployeeController 来接收请求，查出员工数据
4. 来到list.jsp页面进行展示

* url: /emps?pn


### 查询-ajax(有效数据)
1. 发送ajax请求进行员工分页数据的查询
2. 以json 字符串的形式返回给浏览器
3. 用js对json 进行解析，使用js通过dom增删改查改变页面
4. 返回json，，，实现客户端的无关性

### 新增-逻辑
1. 在index.jsp 点击新增，出现对话框
2. 去数据库查询部门列表，显示对话框
3. 然后校验  前后两种校验
4. 用户输入数据保存完成

5. URL:
    * /emp/{id} get查询员工
    * /emp  POST 保存员工
    * /emp/{id} PUT 修改员工
    * /emp/{id} delete 删除员工
    
6. jquery 前端校验，ajax用户名校验，后端校验(JSR303 唯一约束);



### 修改-逻辑
1. 点击编辑
2. 弹出用户修改的模态框
3. 点击更新，完成用户修改
**
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
**

### 删除-逻辑
1. 单个删除
    * url： /emp/{id}
