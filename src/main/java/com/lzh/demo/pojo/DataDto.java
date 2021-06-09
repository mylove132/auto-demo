package com.lzh.demo.pojo;

import com.lzh.demo.enums.RequestType;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class DataDto implements Serializable {

    // 接口url
    private String url;

    // 接口请求方式
    private RequestType requestType;

    // 接口请求超时时间
    private int connectTimeOut = 10;

    // 接口正向用例数据
    private List<Map<String, Object>> normal;

    private Map<String, String> header;

    // 接口逆向用例数据
    private List<Map<String, Object>> exception;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public int getConnectTimeOut() {
        return connectTimeOut;
    }

    public void setConnectTimeOut(int connectTimeOut) {
        this.connectTimeOut = connectTimeOut;
    }

    public List<Map<String, Object>> getNormal() {
        return normal;
    }

    public void setNormal(List<Map<String, Object>> normal) {
        this.normal = normal;
    }

    public List<Map<String, Object>> getException() {
        return exception;
    }

    public void setException(List<Map<String, Object>> exception) {
        this.exception = exception;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }
}

