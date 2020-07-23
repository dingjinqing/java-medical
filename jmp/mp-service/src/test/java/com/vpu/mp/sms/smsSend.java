package com.vpu.mp.sms;



import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSON;
import com.google.common.collect.ImmutableMap;
import com.vpu.mp.common.foundation.util.Util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author 孔德成
 * @date 2020/7/23 8:49
 */
public class smsSend {
    public static void main(String[] args) {
        String msg ="【旺店通】验证码2356，用于登录，10分钟内有效。验证码提供给他人可能导致账号被盗，请勿泄漏，谨防被骗";
        String iphone ="15910451510";
        String sid ="erp_dev";
        long time = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        Map<String, Object> var1 = new LinkedHashMap<>();//保证和请求参数顺序一致
        var1.put("sid", sid);
        var1.put("version", "0");
        var1.put("pass", "dahan");
        var1.put("phone", iphone);
        var1.put("ext", "checkcode");
        var1.put("msg", msg);
        Map<String, Object> var2 = new TreeMap<>(Comparator.naturalOrder());
        var1.put("content", msg);
        var1.put("mobiles", iphone);
        var1.put("product", "1");


        var2.putAll(ImmutableMap.of("sms", Util.toJson(var1)));
        var2.put("timestamp", time);
        var2.put("appKey", "999999");
        var2.put("apiMethod", "wdt.sms.message.send");
        StringBuilder str = new StringBuilder("e063bed69948a2566e3de55250c815af");
        var2.forEach((k, v) -> str.append(k).append(v));
        str.append("e063bed69948a2566e3de55250c815af");
        System.out.println(Util.md5(str.toString()).toUpperCase());
        System.out.println(time);
        String url ="http://192.144.137.185:8902/sms/platform/api";
        Map<String,Object> postBody =new HashMap<>();
        Object sms = postBody.put("sms", "{\n" +
                "    \"sid\":\""+sid+"\",\n" +
                "    \"product\":1,\n" +
                "    \"channel\":mxt,\n" +
                "\"type\":0, \n" +
                "    \"mobiles\":\""+iphone+"\",\n" +
                "    \"ext\":\"-3\",\n" +
                "    \"content\":\""+msg+"\"\n" +
                "}");
        postBody.put("appKey","999999");
        postBody.put("timestamp",time);
        postBody.put("sign",Util.md5(str.toString()).toUpperCase());
        postBody.put("apiMethod","wdt.sms.message.send");
        String body = HttpRequest.post(url)
                .header(Header.CONTENT_TYPE, "multipart/form-data")//头信息，多个头信息多次调用此方法即可
                .form(var2)//表单内容
                .timeout(20000)//超时，毫秒
                .execute().body();
        System.out.println(body);
    }
}
