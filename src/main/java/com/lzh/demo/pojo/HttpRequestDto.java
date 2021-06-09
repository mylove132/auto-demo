package com.lzh.demo.pojo;

import com.lzh.demo.enums.RequestType;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestDto implements Serializable {

    private String url;

    private int connectTimeOut = 10;

    private Map<String, String> header = new HashMap<>();

    private Map<String, Object> params = new HashMap<>();

    private Map<String, Object> assertMap = new HashMap<>();

    private RequestType requestType;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getConnectTimeOut() {
        return connectTimeOut;
    }

    public void setConnectTimeOut(int connectTimeOut) {
        this.connectTimeOut = connectTimeOut;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public Map<String, Object> getAssertMap() {
        return assertMap;
    }

    public void setAssertMap(Map<String, Object> assertMap) {
        this.assertMap = assertMap;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }
}
