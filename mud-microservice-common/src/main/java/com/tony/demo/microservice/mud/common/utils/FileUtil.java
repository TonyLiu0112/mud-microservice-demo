package com.tony.demo.microservice.mud.common.utils;

import java.io.File;
import java.util.UUID;

/**
 * 文件操作工具类
 * <p>
 * Created by Tony on 9/24/16.
 */
public class FileUtil {

    public static String getRangeFileName(String suffix) {
        return UUID.randomUUID().toString().replace("-", "") + "." + suffix;
    }

    public static String getPath(String path) {
        File dir = new File(path);
        if (!dir.exists())
            dir.mkdirs();
        return path;
    }

    public static void removeFile(String location) {
        File f = new File(location);
        f.deleteOnExit();
    }

}
