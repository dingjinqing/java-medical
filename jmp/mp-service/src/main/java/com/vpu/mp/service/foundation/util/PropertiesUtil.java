package com.vpu.mp.service.foundation.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
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
//        URL url = ClassLoader.getSystemResourceAsStream(filePath);
//        URL url = PropertiesUtil.class.getClassLoader().getResource(filePath);
//        log.info("文件地址为："+url);
        InputStream inputStream = ClassLoader.getSystemResourceAsStream(filePath);
        ClassPathResource resource = new ClassPathResource(filePath);

        try {
            if (Objects.isNull(inputStream)) {
                log.debug("以方式【ClassLoader.getSystemResourceAsStream(filePath)】获取资源文件失败！");

                inputStream = resource.getInputStream();
                if (Objects.isNull(inputStream)) {
                    log.debug("以方式【new ClassPathResource(filePath)】获取资源文件失败！");
                }
            }
//            properties.load(new FileInputStream(new File(url.getFile())));
            properties.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("转换完成的properties："+properties);
        HashMap<String, String> map = new HashMap<>(100);
        properties.forEach((k, v) -> map.put((String) k, (String) v));
        return map;
    }
}
