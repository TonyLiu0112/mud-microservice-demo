package com.tony.demo.microservice.mud.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 系统配置参数类
 *
 * @author Tony
 * @date Dec 5, 2015
 */
public class PropertUtil {

    private Logger logger = LoggerFactory.getLogger(PropertUtil.class);

    private final static Properties properties = new Properties();

    /**
     * 获得配置参数
     *
     * @param key
     * @return
     */
    public static String get(String key) {
        String val = properties.getProperty(key);
        if (StringUtils.isEmpty(val))
            return "";
        return val;
    }

    /**
     * 设置配置参数
     *
     * @param key
     * @param val
     */
    public static void set(String key, String val) {
        if (StringUtils.isEmpty(val))
            return;
        properties.setProperty(key, val);
    }

    static {
        InputStream inStream = PropertUtil.class.getClassLoader().getResourceAsStream("application.properties");
        try {
            properties.load(inStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
