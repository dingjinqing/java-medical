package com.vpu.mp.service.pojo.shop.overview;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * author liufei
 * date 2019/7/18
 */
@Data
@NoArgsConstructor
public class AssiDataShop {
    /**
     * 微信配置（授权和支付）,决定了五个完成项
     * byte类型使用后两位表示配置信息结果
     * 2表示：
     *    未注册小程序
     *    未配置小程序客服
     *    未授权小程序
     *    未开通微信支付
     *    未配置微信支付
     * 1表示：
     *      已注册小程序
     *      已配置小程序客服
     *      已授权小程序
     *      未开通微信支付
     *      未配置微信支付
     * 0表示：
     *      已注册小程序
     *      已配置小程序客服
     *      已授权小程序
     *      已开通微信支付
     *      已配置微信支付
     */
    public Byte wxPayConfigInfo;
    /** 子账号设置 0：已完成子账号设置，否未完成 */
    public Byte childAccountConf;
    /** 公众号 0：已授权公众号，否未授权公众号 */
    public Byte officialAccountConf;
    /** 店铺首页 0：已已完成店铺首页装修，否未装修店铺首页 */
    public Byte homePageConf;
    /** 好物圈 0: 已开启好物圈，否未开启 */
    public Byte shopRecommendConf;
    public String shopRecommendLink;
    /** 客服 0: 已开启客服，否未开启 */
    public Byte customServiceConf;

    private AssiDataShop(Builder builder) {
        this.wxPayConfigInfo = builder.wxPayConfigInfo;
        this.childAccountConf = builder.childAccountConf;
        this.officialAccountConf = builder.officialAccountConf;
        this.homePageConf = builder.homePageConf;
        this.shopRecommendConf = builder.shopRecommendConf;
        this.shopRecommendLink = builder.shopRecommendLink;
        this.customServiceConf = builder.customServiceConf;
    }

    public static class Builder implements com.vpu.mp.service.pojo.shop.overview.Builder<AssiDataShop> {
        private Byte wxPayConfigInfo;
        private Byte childAccountConf;
        private Byte officialAccountConf;
        private Byte homePageConf;
        private Byte shopRecommendConf;
        private String shopRecommendLink;
        private Byte customServiceConf;

        public Builder() {
        }

        @Override
        public void reset() {
            this.wxPayConfigInfo = null;
            this.childAccountConf = null;
            this.officialAccountConf = null;
            this.homePageConf = null;
            this.shopRecommendConf = null;
            this.shopRecommendLink = null;
            this.customServiceConf = null;
        }

        @Override
        public AssiDataShop build() {
            return new AssiDataShop(this);
        }

        public Builder setWxPayConfigInfo(Byte wxPayConfigInfo) {
            this.wxPayConfigInfo = wxPayConfigInfo;
            return this;
        }

        public Builder setChildAccountConf(Byte childAccountConf) {
            this.childAccountConf = childAccountConf;
            return this;
        }

        public Builder setOfficialAccountConf(Byte officialAccountConf) {
            this.officialAccountConf = officialAccountConf;
            return this;
        }

        public Builder setHomePageConf(Byte homePageConf) {
            this.homePageConf = homePageConf;
            return this;
        }

        public Builder setShopRecommendConf(Byte shopRecommendConf) {
            this.shopRecommendConf = shopRecommendConf;
            return this;
        }

        public Builder setShopRecommendLink(String shopRecommendLink) {
            this.shopRecommendLink = shopRecommendLink;
            return this;
        }

        public Builder setCustomServiceConf(Byte customServiceConf) {
            this.customServiceConf = customServiceConf;
            return this;
        }
    }
}