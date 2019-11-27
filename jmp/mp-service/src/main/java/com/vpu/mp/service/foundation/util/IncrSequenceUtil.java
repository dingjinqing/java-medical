package com.vpu.mp.service.foundation.util;

import com.vpu.mp.service.foundation.jedis.JedisManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 订单号序列工具
 * @author 孔德成
 * @date 2019/11/27 10:21
 */
@Component
public class IncrSequenceUtil {

    public static final String DATE_FORMAT_FULL_NO_UNDERLINE = "yyyyMMddHHmmss";

    public static final String INCR_SEQUENCE="IncrSequence:";
    private static JedisManager jedisManager;

    @Autowired
    public void setJedisManager(JedisManager jedisManager){
        IncrSequenceUtil.jedisManager =jedisManager;
    }


    /**
     * 订单号序列
     * @param prefix
     * @param dateFormat
     * @param key
     * @return
     */
    public static String generateOrderSn(String prefix,String dateFormat,String key){
        return new StringBuilder(INCR_SEQUENCE).append(prefix)
                .append(DateUtil.dateFormat(dateFormat))
                .append(jedisManager.getIncrSequence(key)).toString();
    }

    /**
     * 订单号序列
     * @param prefix
     * @return
     */
    public static String generateOrderSn(String prefix){
        return generateOrderSn(prefix,DATE_FORMAT_FULL_NO_UNDERLINE,prefix);
    }
}
