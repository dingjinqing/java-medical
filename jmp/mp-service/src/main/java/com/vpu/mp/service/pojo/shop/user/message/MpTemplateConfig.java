package com.vpu.mp.service.pojo.shop.user.message;

import lombok.Builder;
import lombok.Getter;

/**
 * 公众号模版消息
 * @author 卢光耀
 * @date 2019-08-22 10:22
 *
*/
@Getter
@Builder
public class MpTemplateConfig {
    private String id;
    private String title;
    private String content;
    private String[] colors;
}
