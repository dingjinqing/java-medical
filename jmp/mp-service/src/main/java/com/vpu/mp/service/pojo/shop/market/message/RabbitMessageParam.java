package com.vpu.mp.service.pojo.shop.market.message;


import cn.binarywang.wx.miniapp.bean.WxMaTemplateData;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class RabbitMessageParam {
    private Integer shopId;
    private Integer messageTemplateId;

    private List<Integer> userIdList;




//    private Integer shopId;
//
//    private String templateId;
//
//    private String toUser;
//    private String page;
//    private String formId;
//    private List<WxMaTemplateData> data;
//    private String emphasisKeyword;
}
