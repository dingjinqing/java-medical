package com.vpu.mp.service.foundation.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.jooq.tools.json.JSONObject;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

/**
 * @author 孔德成
 * @date 2019/11/18 10:25
 */
@Slf4j
public class HttpsUtils {
    private static final String CHARSET = "UTF-8";
    private static final String GET = "GET";
    private static final String POST = "POST";
    private static PoolingHttpClientConnectionManager connMgr;
    private static RequestConfig requestConfig;
    private static final int MAX_TIMEOUT = 7000;

    static {
        // 设置连接池
        connMgr = new PoolingHttpClientConnectionManager();
        // 设置连接池大小
        connMgr.setMaxTotal(100);
        connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());
        // Validate connections after 1 sec of inactivity
        connMgr.setValidateAfterInactivity(1000);
        RequestConfig.Builder configBuilder = RequestConfig.custom();
        // 设置连接超时
        configBuilder.setConnectTimeout(MAX_TIMEOUT);
        // 设置读取超时
        configBuilder.setSocketTimeout(MAX_TIMEOUT);
        // 设置从连接池获取连接实例的超时
        configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);

        requestConfig = configBuilder.build();
    }
    /**
     * GET 请求
     *
     * @param url     请求地址
     * @param params  请求参数
     * @param headers 请求头
     * @param isHttps 是否 HTTPS请求
     * @return
     * @throws IOException
     */
    public static String get(String url, Map<String, Object> params, Map<String, String> headers, boolean isHttps) {
        return http(GET, url, params, headers, isHttps);
    }
    /**
     * GET 请求
     *
     * @param url     请求地址
     * @param params  请求参数
     * @param isHttps 是否 HTTPS请求
     * @return
     * @throws IOException
     */
    public static String get(String url, Map<String, Object> params, boolean isHttps) {
        return http(GET, url, params, null, isHttps);
    }

    /**
     * POST 请求
     *
     * @param url     请求地址
     * @param params  请求参数
     * @param headers 请求头
     * @param isHttps 是否 HTTPS请求
     * @return
     * @throws IOException
     */
    public static String post(String url, Map<String, Object> params, Map<String, String> headers, boolean isHttps) {
        return http(POST, url, params, headers, isHttps);
    }
    /**
     * POST 请求
     *
     * @param url     请求地址
     * @param params  请求参数
     * @param isHttps 是否 HTTPS请求
     * @return
     * @throws IOException
     */
    public static String post(String url, Map<String, Object> params, boolean isHttps) {
        return http(POST, url, params, null, isHttps);
    }

    private static String http(String method, String url, Map<String, Object> params,
                               Map<String, String> headers, boolean isHttps) {
        long start = System.currentTimeMillis();
        try {
            log.info("请求方式 = {},请求地址 = {},请求参数 = {},请求头信息 = {},https请求 = {}", method, url, JSONObject.toJSONString(params), JSONObject.toJSONString(headers), isHttps);
            HttpClient httpClient;
            if (isHttps) {
                httpClient = createSSLClientDefault();
            } else {
                httpClient = HttpClients.createDefault();
            }
            if (POST.equalsIgnoreCase(method)) {
                HttpPost post = new HttpPost(url);
                if (headers != null) {
                    headers.forEach(post::setHeader);
                }
                if (params != null) {
                    post.setEntity(new StringEntity(JSONObject.toJSONString(params), ContentType.APPLICATION_JSON));
                }
                HttpResponse response = httpClient.execute(post);
                return parseRes(response, CHARSET);
            }
            if (GET.equalsIgnoreCase(method)) {
                if (params != null && params.size() > 0) {
                    if (url.contains("?")) {
                        url += "&" + buildUrlParams(params);
                    } else {
                        url += "?" + buildUrlParams(params);
                    }
                }
                HttpGet get = new HttpGet(url);
                if (headers != null && headers.size() > 0) {
                    headers.forEach(get::setHeader);
                }
                HttpResponse response = httpClient.execute(get);
                return parseRes(response, CHARSET);
            }
            throw new RuntimeException("Unsupported request method");
        } catch (IOException e) {
            throw new RuntimeException("请求异常：" + url);
        } finally {
            log.info("请求耗时：{}", System.currentTimeMillis() - start + " ms");
        }
    }

    /**
     * 解析响应结果
     *
     * @param response
     * @param charSet
     * @return
     * @throws IOException
     */
    private static String parseRes(HttpResponse response, String charSet) throws IOException {
        return EntityUtils.toString(response.getEntity(), charSet);
    }

    /**
     * 创建https
     *
     * @return
     */
    private static CloseableHttpClient createSSLClientDefault() {
        try {
            HttpClientBuilder custom = HttpClients.custom();
            custom.setSSLSocketFactory(createSSLConnSocketFactory());
            custom.setConnectionManager(connMgr);
            custom.setDefaultRequestConfig(requestConfig);
            return custom.build();
        }catch (Exception e){
            e.printStackTrace();
        }
        return HttpClients.createDefault();
    }

    /**
     * 创建SSL安全连接
     *
     * @return
     */
    private static SSLConnectionSocketFactory createSSLConnSocketFactory() throws RuntimeException {
        SSLConnectionSocketFactory sslsf = null;
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, (TrustStrategy) (chain, authType) -> true).build();
            sslsf = new SSLConnectionSocketFactory(sslContext, (arg0, arg1) -> true);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e.getMessage());
        }
        return sslsf;
    }

    /**
     * map to urlEncode 参数
     *
     * @param params
     * @return
     * @throws IOException
     */
    private static String buildUrlParams(Map<String, Object> params) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            sb.append(URLEncoder.encode(entry.getKey(), CHARSET));
            sb.append("=");
            sb.append(URLEncoder.encode(entry.getValue().toString(), CHARSET));
            sb.append("&");
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }
}
