package com.vpu.mp.service.pojo.shop.market.message;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.vpu.mp.service.pojo.shop.official.message.MpTemplateData;
import com.vpu.mp.service.pojo.shop.user.message.MaTemplateData;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@JsonDeserialize(using = MessageParamDeserializer.class)
public class RabbitMessageParam {
    private Integer shopId;

    /**
     * 对应数据库表的ID
     */
    private Integer messageTemplateId;


    private List<Integer> userIdList;
    /**
     * 跳转的url
     */
    private String page;

    private Integer type;

    /**
     * 根据ID和type自动初始化
     */
    private MaTemplateData maTemplateData;
    /**
     * 根据ID和type自动初始化
     */
    private MpTemplateData mpTemplateData;


    private String emphasisKeyword;


}
