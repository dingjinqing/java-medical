package com.vpu.mp.common.pojo.saas.api;

/**
 * 对接外部系统请求返回结果包装类
 * @author 李晓冰
 * @date 2020年07月15日
 */
public class ApiExternalRequestVo {
    /**
     * 错误码：非0为错误
     */
    private Integer error = 0;

    /**
     * 错误消息
     */
    private Object message;

    /**
     * 返回内容
     */
    private Object content;
}
