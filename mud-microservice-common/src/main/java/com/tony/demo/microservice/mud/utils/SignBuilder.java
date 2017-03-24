package com.tony.demo.microservice.mud.utils;

import jodd.util.StringUtil;
import org.apache.commons.codec.digest.DigestUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.TreeMap;

/**
 * http签名builder
 * <p>
 * todo
 * Created by Tony on 06/03/2017.
 */
public class SignBuilder {

    public static <R> String build(R req, String uniqueKey) throws InvocationTargetException, IllegalAccessException {
        Class clazz = req.getClass();
        Map<String, Object> map = new TreeMap<>();
        for (Method method : clazz.getDeclaredMethods()) {
            String methodName = method.getName();
            if (StringUtil.startsWithIgnoreCase(methodName, "get")) {
                String key = StringUtil.replace(methodName, "get", "").toLowerCase();
                Object val = method.invoke(req, null);
                if (val != null)
                    map.put(key, val);
            }
        }
        return md5(map, uniqueKey);
    }

    public static String build(Map<String, Object> req, String uniqueKey) throws InvocationTargetException, IllegalAccessException {
        Map<String, Object> map = new TreeMap<>();
        for (Map.Entry<String, Object> entry : req.entrySet())
            map.put(entry.getKey(), entry.getValue());
        return md5(map, uniqueKey);
    }

    private static String md5(Map<String, Object> map, String uniqueKey) {
        if (map == null || map.isEmpty())
            return "";
        StringBuilder params = new StringBuilder();
        int i = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            params.append(entry.getKey());
            params.append("=");
            params.append(entry.getValue());
            if (i < map.entrySet().size() - 1)
                params.append("&");
        }
        return DigestUtils.md5Hex(params.append("&key=").append(uniqueKey).toString()).toUpperCase();
    }

}
