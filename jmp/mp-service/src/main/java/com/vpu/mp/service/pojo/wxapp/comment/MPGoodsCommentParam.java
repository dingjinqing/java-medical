package com.vpu.mp.service.pojo.wxapp.comment;

import lombok.Data;

/**
 * 小程序商品页评价详情
 * @author liangchen
 * @date 2020.03.26
 */
@Data
public class MPGoodsCommentParam {
    /** 商品id */
    private Integer goodsId;
    /** 筛选条件 */
    private Byte type;
}
