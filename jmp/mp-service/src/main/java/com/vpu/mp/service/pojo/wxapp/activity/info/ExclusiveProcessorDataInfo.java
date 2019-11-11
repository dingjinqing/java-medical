package com.vpu.mp.service.pojo.wxapp.activity.info;

import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.MemberCardMpVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 会员专享信息
 * @author 李晓冰
 * @date 2019年10月31日
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ExclusiveProcessorDataInfo extends ProcessorDataInfo {
    public ExclusiveProcessorDataInfo() {
        dataType = GoodsConstant.ACTIVITY_TYPE_MEMBER_EXCLUSIVE;
    }

    public ExclusiveProcessorDataInfo(Integer gctaId, Byte type){
        this();
        this.gctaId = gctaId;
        this.type = type;
    }
    /****商品列表****/
    private Integer gctaId;
    /**会员卡关联到商品的方式1id,2平台分类，3商家分类，4全部商品*/
    private Byte type;
    /****商品详情****/
    private Integer id;
    private String cardName;
    /**卡的类型0普通，1限次，2等级*/
    private Byte cardType;
    /**是否需要激活*/
    private Byte activation;
    /**激活时间*/
    private Timestamp activationTime;
    /**用户领取的会员卡的过期时间*/
    private Timestamp expireTime;
    /**
     * 是否直接领取：0直接领，1需要购买，2需要领取码
     */
    private Byte isPay;
    /**需要购买时的费用*/
    private BigDecimal payFee;
    /**卡的等级*/
    private String grade;
    /**用户对本卡的使用状态：0 待领取，1已领取，2待激活，3待续费，4已过期*/
    private Byte status;

    public MemberCardMpVo convertToMemberCardMpVo(){
        MemberCardMpVo vo =new MemberCardMpVo();
        vo.setId(this.id);
        vo.setCardName(this.cardName);
        vo.setIsPay(this.isPay);
        vo.setPayFee(this.payFee);
        vo.setStatus(this.status);
        return vo;
    }
}
