package com.lzh.demo.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class SystemConfigUtils {

    public static String getProperty (String key) {
        Properties properties = new Properties();
        try {
            properties.load(SystemConfigUtils.class.getResourceAsStream("/system.properties"));
            return properties.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
