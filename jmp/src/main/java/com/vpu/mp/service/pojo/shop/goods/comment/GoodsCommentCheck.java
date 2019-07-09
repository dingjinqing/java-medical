package com.vpu.mp.service.pojo.shop.goods.comment;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author liangchen
 * @date 2019年07月07日
 */
@Data
public class GoodsCommentCheck {

    private String orderSn;
    private String goodsImg;
    private String goodsName;
    private String username;
    private String mobile;
    private Byte commstar;
    private String commNote;
    private String lotteryAward;
    private String content;
    private Timestamp createTime;
    private Byte anonymousflag;
    private Byte flag;
    
}
