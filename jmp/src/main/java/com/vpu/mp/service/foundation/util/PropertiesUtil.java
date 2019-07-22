package com.vpu.mp.service.foundation.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Properties 工具
 *
 * @author 郑保乐
 */
public class PropertiesUtil {

    /**
     * 将 properties 文件转换成 map
     *
     * @param filePath properties 文件的相对路径（ClassPath）
     */
    public static Map<String, String> toMap(String filePath) {
        Properties properties = new Properties();
        URL url = ClassLoader.getSystemResource(filePath);
        try {
            properties.load(new FileInputStream(new File(url.getFile())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        HashMap<String, String> map = new HashMap<>(100);
        properties.forEach((k, v) -> map.put((String) k, (String) v));
        return map;
    }
}
