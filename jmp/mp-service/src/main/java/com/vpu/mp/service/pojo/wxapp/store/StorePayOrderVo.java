package com.vpu.mp.service.pojo.wxapp.store;

import com.vpu.mp.service.pojo.shop.config.trade.PaymentConfigVo;
import com.vpu.mp.service.pojo.shop.member.card.ValidUserCardBean;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * The type Store pay order vo.
 *
 * @author liufei
 * @date 10 /21/19
 */
@Data
public class StorePayOrderVo {
    /**
     * The Score.用户积分
     */
    public Integer score;
    /**
     * The Account.用户余额
     */
    public BigDecimal account;
    /**
     * The Invoice switch.发票配置开关
     */
    public Byte invoiceSwitch;
    /**
     * The Member card list.可用会员卡列表
     */
    public List<ValidUserCardBean> memberCardList;
    /**
     * The Shop business state.店铺营业状态
     */
    public Byte shopBusinessState;
    /**
     * The Store business state.门店营业状态
     */
    public Byte storeBusinessState;
    /**
     * The Shop logo.店铺logo
     */
    public String shopLogo;
    /**
     * The Store buy.门店买单开关配置
     */
    public Byte storeBuy;
    /**
     * The Del flag.门店删除标识
     */
    public Byte delFlag;
    /**
     * 支付方式开关
     */
    private List<PaymentConfigVo> payStatusList;
    /**
     * 默认支付配置
     */
    private Map<String, Byte> defaultPayConf;
    /**
     * 积分支付限制
     */
    private Integer scoreDiscountRatio;
    /**
     * 积分抵扣比例
     */
    private Integer scorePayNum;
}
