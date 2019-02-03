package com.study.crud.bean;

/**
 * Created by IntelliJ IDEA.
 *
 * @version : 1.0
 * @auther : Firewine
 * @mail ： 1451661318@qq.com
 * @Program Name: <br>
 * @Create : 2019-02-02-22:36
 * @Description :  <br/>
 */

import com.github.pagehelper.PageInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用的返回的类
 */
public class Msg {

    //状态码 100 成功 200 失败
    private int code;

    //提示信息
    private String msg;

    //用户返回给浏览器的数据
    private Map<String,Object> extend = new HashMap<String,Object>();

    public static Msg success(){

        Msg result = new Msg();
        result.setCode(100);
        result.setMsg("处理成功！");
        return result;
    }
    public static Msg fail(){

        Msg result = new Msg();
        result.setCode(200);
        result.setMsg("处理失败！");
        return result;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Msg add(String key, Object value) {

        this.getExtend().put(key,value);
        return this;

    }

    public Map<String, Object> getExtend() {
        return extend;
    }

    public void setExtend(Map<String, Object> extend) {
        this.extend = extend;
    }
}
