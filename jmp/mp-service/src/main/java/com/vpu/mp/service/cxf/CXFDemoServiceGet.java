package com.vpu.mp.service.cxf;

import com.google.gson.Gson;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author zhaoxiaodong
 * @create 2020-07-14 18:28
 */

@Service
public class CXFDemoServiceGet {

    /**
     * @Description
     * @param url 调用接口URL
     * @param methodName 调用方法名
     * @param ParamType 调用方法类型
     * @return String Json数据
     */
    public static String getWebService(String url, String methodName, Object... ParamType) {
        // 创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        //Json转换工具
        Gson gson = new Gson();
        //URL地址
        Client client = dcf.createClient(url);
        // 需要密码的情况需要加上用户名和密码
        // client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME,PASS_WORD));
        Object[] objects = new Object[0];
            // invoke("方法名",参数1,参数2,参数3....);
        try {
            objects = client.invoke(methodName, ParamType);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (objects != null && objects.length > 0){
            return gson.toJson(objects[0]);
        }
        return "";
    }
}
