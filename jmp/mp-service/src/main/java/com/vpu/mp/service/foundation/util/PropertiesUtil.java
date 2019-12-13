package com.vpu.mp.service.foundation.util;

import lombok.extern.slf4j.Slf4j;

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
@Slf4j
public class PropertiesUtil {

    /**
     * 将 properties 文件转换成 map
     *
     * @param filePath properties 文件的相对路径（ClassPath）
     */
    public static Map<String, String> toMap(String filePath) {
        Properties properties = new Properties();
//        URL url = ClassLoader.getSystemResource(filePath);
        URL url = PropertiesUtil.class.getClassLoader().getResource(filePath);
        log.info("文件地址为："+url);
        try {
            log.info("properties要load的内容为："+new FileInputStream(new File(url.getFile())));
            properties.load(new FileInputStream(new File(url.getFile())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("转换完成的properties："+properties);
        HashMap<String, String> map = new HashMap<>(100);
        properties.forEach((k, v) -> map.put((String) k, (String) v));
        return map;
    }
}
