package com.sc.adminht.utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pages<T> implements Serializable {
    private static final long serialVersionUID = 768549219446295665L;
    //成功与否
    private boolean success;
    //信息
    private String msg;
    //状态
    private Integer status;
    //总条数
    private Integer total;
    //当前页显示数据
    private List<T> records;

    /*public Map<String, Object> setPageJson(boolean success, String msg,Integer status,Integer total, List<T> records) {
        Map<String, Object> json = new HashMap();
        json.put("success", Boolean.valueOf(success));
        json.put("msg", msg);
        json.put("status", status);
        json.put("total", total);
        json.put("records", records);
        return json;
    }*/

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }
}
