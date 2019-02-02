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