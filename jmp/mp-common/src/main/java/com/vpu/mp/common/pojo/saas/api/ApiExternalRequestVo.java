package com.vpu.mp.common.pojo.saas.api;

import lombok.Data;

/**
 * 对接外部系统请求返回结果包装类
 * @author 李晓冰
 * @date 2020年07月15日
 */
@Data
public class ApiExternalRequestVo {
    /**
     * 错误码：非0为错误,对方错误码需要是0或正整数
     */
    private Integer error = 0;

    /**
     * 错误消息
     */
    private Object message;

    /**
     * 返回内容
     */
    private String content;
}
