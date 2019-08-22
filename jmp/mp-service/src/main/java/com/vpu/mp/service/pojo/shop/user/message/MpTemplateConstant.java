package com.vpu.mp.service.pojo.shop.user.message;

public interface MpTemplateConstant {
    MpTemplateConfig ACTIVITY_CONFIG = MpTemplateConfig.builder()
        .id("OPENTM415477060")
        .title("业务处理结果通知")
        .content("{{first.DATA}}业务类型：{{keyword1.DATA}}业务内容：{{keyword2.DATA}}处理结果：" +
            "{{keyword3.DATA}}操作时间：{{keyword4.DATA}}{{remark.DATA}}")
        .build();
}
