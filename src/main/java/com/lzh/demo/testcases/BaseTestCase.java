package com.lzh.demo.testcases;
import com.alibaba.fastjson.JSONObject;
import com.lzh.demo.pojo.DataDto;
import com.lzh.demo.pojo.HttpRequestDto;
import com.lzh.demo.utils.HttpUtils;
import com.lzh.demo.utils.YamlUtils;
import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.DataProvider;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class BaseTestCase extends AbstractTestCase {

    private Logger log = Logger.getLogger(BaseTestCase.class);
    @DataProvider
    public Object[][] providerMethod(Method method, ITestContext context){
        HttpRequestDto httpRequestDto = new HttpRequestDto();
        String[] groups = null;
        String className = "";
                ITestNGMethod[] testNGMethods = context.getAllTestMethods();
        for (ITestNGMethod testNGMethod : testNGMethods) {
            if (testNGMethod.getMethodName().equals(method.getName())) {
                groups = testNGMethod.getGroups();
                className = testNGMethod.getRealClass().getName();
                break;
            }
        }
        DataDto dataDto = YamlUtils.getData(className);
        httpRequestDto.setUrl(dataDto.getUrl());
        httpRequestDto.setConnectTimeOut(dataDto.getConnectTimeOut());
        httpRequestDto.setRequestType(dataDto.getRequestType());
        if (dataDto.getHeader() != null) {
            httpRequestDto.setHeader(dataDto.getHeader());
        }
        if ("normal".equals(groups[0])) {
            List<Map<String, Object>> normalList = dataDto.getNormal();
            for (Map<String, Object> normalMap : normalList) {
               if (method.getName().equals(normalMap.get("name").toString())) {
                   Map paramMap = JSONObject.parseObject(JSONObject.toJSONString(normalMap.get("params")), Map.class);
                   httpRequestDto.getParams().putAll(paramMap);
                   Map assertMap = JSONObject.parseObject(JSONObject.toJSONString(normalMap.get("assert")), Map.class);
                   httpRequestDto.getAssertMap().putAll(assertMap);
               }
            }
        } else {
            List<Map<String, Object>> exceptionList = dataDto.getException();
            log.info(String.format("接口解析异常请求数据:%s", JSONObject.toJSONString(exceptionList)));
            for (Map<String, Object> exceptionMap : exceptionList) {
                if (method.getName().equals(exceptionMap.get("name").toString())) {
                    Map paramMap = JSONObject.parseObject(JSONObject.toJSONString(exceptionMap.get("params")), Map.class);
                    httpRequestDto.getParams().putAll(paramMap);
                    Map assertMap = JSONObject.parseObject(JSONObject.toJSONString(exceptionMap.get("assert")), Map.class);
                    httpRequestDto.getAssertMap().putAll(assertMap);
                }
            }
        }
        return new Object[][]{new Object[] {
                httpRequestDto
        }};
    }

    @Override
    public void run(HttpRequestDto requestDto) {
        switch (requestDto.getRequestType()) {
            case GET:
                HttpUtils.get(requestDto);
                break;
            case POST:
                HttpUtils.post(requestDto);
                break;
        }
    }
}
