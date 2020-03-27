package com.vpu.mp.service.pojo.wxapp.comment;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 小程序商品页评价详情
 * @author liangchen
 * @date 2020.03.26
 */
@Data
public class MPGoodsCommentVo {
    /** 评价id */
    private Integer id;
    /** 评价星级 */
    private Byte commstar;
    /** 是否匿名 */
    private Byte anonymousflag;
    /** 评价心得 */
    private String commNote;
    /**  */
    private String commImg;
    /**  */
    private String goodsAttr;
    /**  */
    private String username;
    /**  */
    private String userAvatar;
    /**  */
    private Timestamp createTime;
    /**  */
    private String bogusUsername;
    /**  */
    private String bogusUserAvatar;
    /**  */
    private String prdDesc;
    /**  */
    private Integer answerId;
    /**  */
    private String answer;
}
