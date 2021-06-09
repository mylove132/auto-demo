package com.lzh.demo.utils;

import com.alibaba.fastjson.JSONObject;
import com.jayway.jsonpath.JsonPath;
import com.lzh.demo.config.HttpConstants;
import com.lzh.demo.pojo.HttpRequestDto;
import okhttp3.*;
import org.apache.log4j.Logger;
import org.testng.Assert;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HttpUtils {

    private static final String BASE_URL = SystemConfigUtils.getProperty("url");
    private static final Logger log = Logger.getLogger(HttpUtils.class);
    /**
     * get请求方法
     * @return
     */
    public static void get (HttpRequestDto httpRequestDto) {
        // 设置请求
        OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(httpRequestDto.getConnectTimeOut(), TimeUnit.SECONDS).build();
        Request.Builder builder = setHeader(httpRequestDto.getHeader());
        // 处理请求URL
        String url = "";
        if (httpRequestDto.getParams() != null && httpRequestDto.getParams().size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            httpRequestDto.getParams().forEach(
                    (key, value) -> {
                        if (key == null) {
                            return;
                        }
                        String pm = String.format("%s=%s&", key, value);
                        stringBuilder.append(pm);
                    }
            );
            String param = stringBuilder.toString();
            param = param.substring(0, param.length() - 1);
            url = String.format("%s%s?%s", BASE_URL, httpRequestDto.getUrl(), param);
        } else {
            url = String.format("%s%s", BASE_URL, httpRequestDto.getUrl());
        }
        Request request = builder.url(url).get().build();
        final Call call = client.newCall(request);
        try {
            Response response =  call.execute();
            isSuccess(httpRequestDto.getAssertMap(), response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * post请求方法
     * @param httpRequestDto
     * @return
     */
    public static void post (HttpRequestDto httpRequestDto) {
        // 设置请求
        log.info(String.format("接口请求参数：%s", JSONObject.toJSONString(httpRequestDto)));
        OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(httpRequestDto.getConnectTimeOut(), TimeUnit.SECONDS).build();
        Request.Builder builder = setHeader(httpRequestDto.getHeader());

        String param = "";
        if (httpRequestDto.getParams() != null && httpRequestDto.getParams().size() > 0) {
            JSONObject jsonObject = new JSONObject(httpRequestDto.getParams());
            param = jsonObject.toJSONString();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse(HttpConstants.MEDIA_TYPE), param);
        Request request = builder.url(BASE_URL + httpRequestDto.getUrl()).post(requestBody).build();
        final Call call = client.newCall(request);
        try {
            long startTime = System.currentTimeMillis();
            Response response =  call.execute();
            long endTime = System.currentTimeMillis();
            log.info(String.format("接口请求耗时：%d", endTime - startTime));
            isSuccess(httpRequestDto.getAssertMap(), response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置请求头
     * @param headerMap
     * @return
     */
    private static Request.Builder setHeader (Map<String, String> headerMap) {
        Request.Builder builder = new Request.Builder();
        // 构建header请求参数
        if (headerMap != null && headerMap.size() > 0) {
            headerMap.forEach(
                    (key, value) -> {
                        if (key == null) {
                            return;
                        }
                        builder.addHeader(key, value);
                    }
            );
        }
        return builder;
    }

    private static void isSuccess (Map<String, Object> assertMap, Response response) {
        if (!response.isSuccessful()) {
            Assert.assertTrue(false, String.format("接口请求失败,code:%d", response.code()));
        }
        try {
            String assertValue = assertMap.get("assertValue").toString();
            String responseValue = response.body().string();
            log.info(String.format("接口请求返回数据：%s", responseValue));
            switch (assertMap.get("assertType").toString()) {
                case "REGULAR":
                    String[] assertSt = assertValue.split("=");
                    String value = "";
                    try {
                        value = JsonPath.read(responseValue, assertSt[0]).toString();
                    } catch (Exception e) {
                     Assert.assertFalse(true, "解析json path失败:"+e.getMessage());
                    }
                    Assert.assertTrue(value.equals(assertSt[1]), String.format("期望值：%s与实际值：%s不一致", value, assertSt[1]));
                    break;
                case "CONTAIN":
                    Assert.assertTrue(responseValue.contains(assertValue));
                    break;
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
