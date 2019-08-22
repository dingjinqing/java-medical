package com.vpu.mp.service.pojo.shop.user.message;

/**
 * 小程序消息模版类
 * @author 卢光耀
 * @date 2019-08-22 09:50
 *
*/
public interface MaTemplateConstant {
    MaTemplateConfig ACTIVITY_CONFIG =  MaTemplateConfig.builder()
        .id("AT0654")
        .title("申请进度通知")
        .content("业务名称{{keyword1.DATA}}业务状态{{keyword2.DATA}}")
        .keywordIds(new Integer[] {1,2})
        .emphasisKeywordSn(1)
        .build();

}
