package com.lzh.demo.utils;

import com.alibaba.fastjson.JSONObject;
import com.lzh.demo.pojo.DataDto;
import org.yaml.snakeyaml.Yaml;

import java.io.*;

public class YamlUtils {

    public static DataDto getData (String className) {
        Yaml yaml = new Yaml();
        String[] path = className.split("\\.");
        String cName = path[path.length-1];
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("/data/");
        for (int i = 4; i < path.length - 1; i++) {
            stringBuilder.append(path[i]).append("/");
        }
       InputStream is = YamlUtils.class.getResourceAsStream(stringBuilder.toString()+cName.toLowerCase()+".yaml");
        DataDto dataDto = yaml.load(new InputStreamReader(is));
        return dataDto;
    }

    public static void main(String[] args) throws FileNotFoundException {
        DataDto dataDto = getData("com.lzh.demo.testcases.user.LoginTestCase");
        System.out.println(JSONObject.toJSONString(dataDto));
    }
}
