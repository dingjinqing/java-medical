package com.vpu.mp.service.saas.overview;

import com.vpu.mp.db.main.tables.*;
import com.vpu.mp.db.main.tables.records.MpAuthShopRecord;
import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.Util;
import com.vpu.mp.service.pojo.shop.overview.*;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Select;
import org.jooq.SortField;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:liufei
 * @Date:2019/7/15
 * @Description: 概览
 */
public class ShopOverviewService extends BaseService {
    /**
     * 绑定解绑
     * @param param
     * @return
     */
    public int bindUnBindOfficial(BindUnBindOfficialParam param){
        /** 更新主账户 */
        if(param.getIsSubAccount() == 0){
            return db().update(ShopAccount.SHOP_ACCOUNT)
                    .set(ShopAccount.SHOP_ACCOUNT.IS_BIND,param.getIsBind())
                    .where(ShopAccount.SHOP_ACCOUNT.SYS_ID.eq(param.getSysId()))
                    .execute();
            /** 更新子账户 */
        }else if(param.getIsSubAccount() == 1){
            return db().update(ShopChildAccount.SHOP_CHILD_ACCOUNT)
                    .set(ShopChildAccount.SHOP_CHILD_ACCOUNT.IS_BIND,param.getIsBind())
                    .where(ShopChildAccount.SHOP_CHILD_ACCOUNT.ACCOUNT_ID.eq(param.getAccountId()))
                    .execute();
        }else {
            return -1;
        }
    }

    /**
     * 获取绑定/解绑状态
     * @param param
     * @return
     */
    public byte getbindUnBindStatus(BindUnBindOfficialParam param){
        /** 主账户 */
        if(param.getIsSubAccount() == 0){
            List<Byte> bindStatus =  db().select(ShopAccount.SHOP_ACCOUNT.IS_BIND)
                    .from(ShopAccount.SHOP_ACCOUNT)
                    .where(ShopAccount.SHOP_ACCOUNT.SYS_ID.eq(param.getSysId()))
                    .fetchInto(Byte.class);
            return Util.isEmpty(bindStatus) ? bindStatus.get(0) : -1;
            /** 子账户 */
        }else if(param.getIsSubAccount() == 1){
            List<Byte> bindStatus = db().select(ShopChildAccount.SHOP_CHILD_ACCOUNT.IS_BIND)
                    .from(ShopChildAccount.SHOP_CHILD_ACCOUNT)
                    .where(ShopChildAccount.SHOP_CHILD_ACCOUNT.ACCOUNT_ID.eq(param.getAccountId()))
                    .fetchInto(Byte.class);
            return Util.isEmpty(bindStatus) ? bindStatus.get(0) : -1;
        }else {
            return -1;
        }
    }

    /**
     * 获取店铺基本信息
     * @param param
     * @return
     */
    public ShopBaseInfoVo getShopBaseInfo(ShopBaseInfoParam param){
        ShopBaseInfoVo shopBaseInfoVo = new ShopBaseInfoVo();
        //店铺到期时间
        List<ShopBaseInfoVo> baseInfoVos = db().select(ShopRenew.SHOP_RENEW.EXPIRE_TIME)
                .from(ShopRenew.SHOP_RENEW)
                .where(ShopRenew.SHOP_RENEW.SHOP_ID.eq(param.getShopId()))
                .fetchInto(ShopBaseInfoVo.class);
        shopBaseInfoVo.setExpireTime(baseInfoVos!=null&&!baseInfoVos.isEmpty() ? baseInfoVos.get(0).getExpireTime() : null);
        //店铺版本
        Shop shop = Shop.SHOP.as("shop");
        ShopVersion sv = ShopVersion.SHOP_VERSION.as("sv");
        Select select = db().select(shop.SHOP_TYPE).from(shop).where(shop.SHOP_ID.eq(param.getShopId()));
        List<ShopBaseInfoVo> infoVos = db().select(sv.VERSION_NAME).from(sv).where(sv.LEVEL.eq(select))
                .fetchInto(ShopBaseInfoVo.class);
        shopBaseInfoVo.setVersionName(infoVos!=null&&!infoVos.isEmpty() ? infoVos.get(0).getVersionName() : null);
        //当前绑定解绑状态

        shopBaseInfoVo.setBindStatus(getbindUnBindStatus(param.getOfficialParam()));
        return shopBaseInfoVo;
    }

    /**
     * 获取指定数量的公告title
     * @param param
     */
    public List<FixedAnnouncementVo> getFixedAnnouncement(FixedAnnouncementParam param){
        SortField orderBy = "asc".equals(param.getOrderBy()) ? Article.ARTICLE.CREATE_TIME.asc() : Article.ARTICLE.CREATE_TIME.desc();
        List<FixedAnnouncementVo> listVo = new ArrayList<>();
        listVo = db().select(Article.ARTICLE.TITLE,Article.ARTICLE.CREATE_TIME)
                .from(Article.ARTICLE)
                .where(Article.ARTICLE.STATUS.eq((byte)1))
                .and(Article.ARTICLE.CATEGORY_ID.eq(param.getCategoryId()))
                .orderBy(orderBy)
                .limit(param.getFixedNum())
                .fetchInto(FixedAnnouncementVo.class);
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd");
        for (FixedAnnouncementVo vo : listVo){
            vo.setFormatTime(formatter.format(vo.getCreateTime()));
        }
        return listVo;
    }

    /**
     * 店铺助手
     * @param param
     * @param vo
     */
    public void shopAssistant(ShopAssistantParam param,ShopAssistantVo vo){
        shopNav(param,vo);
    }
    public ShopAssistantVo shopNav(ShopAssistantParam param,ShopAssistantVo vo){
        //微信配置（授权和支付）
        List<MpAuthShop> authShopList = db().selectFrom(MpAuthShop.MP_AUTH_SHOP)
                .where(MpAuthShop.MP_AUTH_SHOP.SHOP_ID.eq(param.getShopId()))
                .fetchInto(MpAuthShop.class);
        if(authShopList!=null&&!authShopList.isEmpty()) {
            MpAuthShop authShop = authShopList.get(0);
            MpAuthShopRecord authShopRecord = new MpAuthShopRecord();

            boolean condi1 = StringUtils.isNotEmpty(authShop.PAY_CERT_CONTENT.get(authShopRecord));
            boolean condi2 = StringUtils.isNotEmpty(authShop.PAY_KEY.get(authShopRecord));
            boolean condi3 = StringUtils.isNotEmpty(authShop.PAY_KEY_CONTENT.get(authShopRecord));
            boolean condi4 = StringUtils.isNotEmpty(authShop.PAY_MCH_ID.get(authShopRecord));

            if(condi1 && condi2 && condi3 && condi4){
                vo.getDataShop().setWxPayConfigInfo((byte)0);
            }else{
                vo.getDataShop().setWxPayConfigInfo((byte)1);
            }
        }else {
            vo.getDataShop().setWxPayConfigInfo((byte)2);
            vo.totalPendingIncr();
        }
        //子账号配置
        int subCount = db().fetchCount(ShopChildAccount.SHOP_CHILD_ACCOUNT,
                ShopChildAccount.SHOP_CHILD_ACCOUNT.SYS_ID.eq(param.getSysId()));
        vo.getDataShop().setChildAccountConf(subCount > 0 ? (byte)0 : (byte)-1);
        //公众号
        int officialCount = db().fetchCount(MpOfficialAccount.MP_OFFICIAL_ACCOUNT,
                MpOfficialAccount.MP_OFFICIAL_ACCOUNT.SYS_ID.eq(param.getSysId())
                        .and(MpOfficialAccount.MP_OFFICIAL_ACCOUNT.IS_AUTH_OK.eq(param.getIsAuthOk())));
        vo.getDataShop().setOfficialAccountConf(officialCount > 0 ? (byte)0 : (byte)-1);
        return vo;
    }

}
