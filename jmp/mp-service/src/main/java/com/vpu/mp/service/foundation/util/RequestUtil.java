package com.vpu.mp.service.foundation.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * request工具类
 * @author 卢光耀
 * @date 2019-08-07 10:27
 *
*/
public class RequestUtil {

    private static final String UNKNOWN = "unknown";

    public static String getIp(HttpServletRequest request){
        String realIP = request.getHeader("X-Real-IP");
        String forwardedIP = request.getHeader("X-Forwarded-IP");
        if (StringUtils.isNotEmpty(forwardedIP) && UNKNOWN.equalsIgnoreCase(forwardedIP)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = forwardedIP.indexOf(",");
            if(index != -1){
                return forwardedIP.substring(0,index);
            }else{
                return forwardedIP;
            }
        }
        forwardedIP = realIP;

        if(StringUtils.isNotEmpty(forwardedIP) && !UNKNOWN.equalsIgnoreCase(forwardedIP)){
            return forwardedIP;
        }
        if (StringUtils.isBlank(forwardedIP) || UNKNOWN.equalsIgnoreCase(forwardedIP)) {
            forwardedIP = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(forwardedIP) || UNKNOWN.equalsIgnoreCase(forwardedIP)) {
            forwardedIP = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(forwardedIP) || UNKNOWN.equalsIgnoreCase(forwardedIP)) {
            forwardedIP = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isBlank(forwardedIP) || UNKNOWN.equalsIgnoreCase(forwardedIP)) {
            forwardedIP = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isBlank(forwardedIP) || UNKNOWN.equalsIgnoreCase(forwardedIP)) {
            forwardedIP = request.getRemoteAddr();
        }
        return forwardedIP;
    }
}
