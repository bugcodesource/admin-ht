package com.sc.adminht.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sc.adminht.utils.JsonUtil;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class BaseController {
    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        CustomDateEditor dateEditor = new CustomDateEditor(format, true);
        binder.registerCustomEditor(Date.class, dateEditor);

    }

    public Object getAttribute(String attributeName) {
        return this.getRequest().getAttribute(attributeName);
    }

    public void setAttribute(String attributeName, Object object) {
        this.getRequest().setAttribute(attributeName, object);
    }

    public Object getSession(String attributeName) {
        return this.getRequest().getSession(true).getAttribute(attributeName);
    }

    public void setSession(String attributeName, Object object) {
        this.getRequest().getSession(true).setAttribute(attributeName, object);
    }

    public HttpServletRequest getRequest() {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        return ((ServletRequestAttributes) ra).getRequest();
    }

    public HttpServletResponse getResponse(){
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        return ((ServletRequestAttributes) ra).getResponse();
    }

    public HttpSession getSession() {
        return this.getRequest().getSession(true);
    }

    public String getParameter(String paraName) {
        return this.getRequest().getParameter(paraName);
    }

    /**
     * 获取表单格式数据(或url拼接参数)
     * @return
     */
    @SuppressWarnings("rawtypes")
    public Map getParameterMap(){
        return this.getRequest().getParameterMap();
    }

    public String getHeader(String headerName) {
        return this.getRequest().getHeader(headerName);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Map getHeaderMap() {
        Enumeration headerNames = this.getRequest().getHeaderNames();
        Map headerMap = new HashMap<>();
        while(headerNames.hasMoreElements()){
            String headerName = (String) headerNames.nextElement();
            String headerValue = getRequest().getHeader(headerName);
            headerMap.put(headerName, headerValue);
        }
        return headerMap;
    }

    public String getIpAddress(){
        String ip =  this.getRequest().getRemoteAddr();
        return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
    }

    /**
     * 获取服务器ip地址
     * @return
     */
    public String getServerIpAddress(){
        InetAddress address;
        String serverIpAddress = null;
        try {
            address = InetAddress.getLocalHost(); //获取的是本地的IP地址 //PC-20140317PXKX/192.168.0.121
            serverIpAddress = address.getHostAddress();//192.168.0.121
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return serverIpAddress;
    }

    /**
     * 获取json格式数据
     * @return
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> getRequestMap(){
        try {
            InputStream inStream = this.getRequest().getInputStream();
            //默认为json
            BufferedReader in = new BufferedReader(new InputStreamReader(inStream , "UTF-8"));
            StringBuffer stringBuffer = new StringBuffer();
            String buffer = "";
            while(null!=(buffer=(in.readLine()))){
                stringBuffer.append(buffer);
            }
            String reqDoc = stringBuffer.toString();
            if(reqDoc==null||reqDoc.equals("")){
                return null;
            }
            return JsonUtil.toMap(reqDoc) ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /*返回响应字段*/
    public Map<String, Object> setJson(boolean success, String message, Object entity) {
        Map<String, Object> json = new HashMap();
        json.put("success", Boolean.valueOf(success));
        json.put("msg", message);
        json.put("entity", entity);
        return json;
    }
    public Map<String, Object> setPageJson(boolean success, String msg,Integer status,Integer total, Object records) {
        Map<String, Object> json = new HashMap();
        json.put("success", Boolean.valueOf(success));
        json.put("msg", msg);
        json.put("status", status);
        json.put("total", total);
        json.put("records", records);
        return json;
    }
    //异常返回信息
    public Map<String, Object> setAjaxException(Map<String, Object> map) {
        map.put("success", Boolean.valueOf(false));
        map.put("msg", "系统繁忙，请稍后再操作！");
        map.put("entity", null);
        return map;
    }
    /**
     * 允许跨域访问
     */
    public void allowCrossDomainAccess(){
        HttpServletResponse servletResponse = getResponse();
        servletResponse.setHeader("Access-Control-Allow-Origin", "*");
        servletResponse.setHeader("Access-Control-Allow-Methods", "POST,GET");
        servletResponse.setHeader("Access-Control-Allow-Headers:x-requested-with", "content-type");
    }
    /**
     *  <p> 字符串转地址 </p>
     * @param address - 地址字符串 <br/>
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.String>> <br/>
     * @throws
     */
    public static List<Map<String,String>> stringToAddress(String address){
        String regex="(?<province>[^省]+自治区|.*?省|.*?行政区|.*?市)(?<city>[^市]+自治州|.*?地区|.*?行政单位|.+盟|市辖区|.*?市|.*?县)(?<county>[^县]+县|.+区|.+市|.+旗|.+海域|.+岛)?(?<town>[^区]+区|.+镇)?(?<village>.*)";
        Matcher m= Pattern.compile(regex).matcher(address);
        String province=null,city=null,county=null,town=null,village=null;
        List<Map<String,String>> table=new ArrayList<Map<String,String>>();
        Map<String,String> row=null;
        while(m.find()){
            row=new LinkedHashMap<String,String>();
            province=m.group("province");
            row.put("province", province==null?"":province.trim());
            city=m.group("city");
            row.put("city", city==null?"":city.trim());
            county=m.group("county");
            row.put("county", county==null?"":county.trim());
            town=m.group("town");
            row.put("town", town==null?"":town.trim());
            village=m.group("village");
            row.put("village", village==null?"":village.trim());
            table.add(row);
        }
        return table;
    }
}