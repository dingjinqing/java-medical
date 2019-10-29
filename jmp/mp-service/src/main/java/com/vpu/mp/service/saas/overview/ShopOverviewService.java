package com.vpu.mp.service.saas.overview;

import com.vpu.mp.db.main.tables.*;
import com.vpu.mp.db.main.tables.records.MpAuthShopRecord;
import com.vpu.mp.db.main.tables.records.MpOfficialAccountUserRecord;
import com.vpu.mp.db.main.tables.records.ShopAccountRecord;
import com.vpu.mp.db.main.tables.records.ShopChildAccountRecord;
import com.vpu.mp.service.foundation.service.MainBaseService;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.auth.AdminTokenAuthInfo;
import com.vpu.mp.service.pojo.shop.overview.*;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Record1;
import org.jooq.Select;
import org.jooq.SortField;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * author liufei
 * date 2019/7/15
 * 概览
 */
@Service
public class ShopOverviewService extends MainBaseService {
    /**
     * 绑定解绑
     * @param param
     * @return
     */
	public boolean bindUnBindOfficial(String act, AdminTokenAuthInfo user, Integer accountId) {
		if (StringUtils.isEmpty(act) || user == null) {
			return false;
		}
		byte isBind = 0;
		if ("bind".equals(act)) {
			// 绑定
			isBind = 1;
		}
		if ("del_bind".equals(act)) {
			// 解绑
			isBind = 0;
		}if(!("bind".equals(act)||"del_bind".equals(act))) {
			logger().debug("绑定解绑传入act参数错误："+act);
			return false;
		}
		int num = 0;
		if (!user.subLogin) {
			// 主账户
			if(accountId!=0) {
				//主账户在子账户权限管理处操作子账户解绑绑定
				num = saas.shop.subAccount.updateRowBind(accountId, isBind);
			}else {
				num = saas.shop.account.updateRowBind(user.sysId, isBind);
			}
		} else {
			// 子账户
			num = saas.shop.subAccount.updateRowBind(user.subAccountId, isBind);
		}
		if (num > 0) {
			return true;
		}
		return false;
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
     * 获取绑定/解绑状态  概览使用
     * @param param
     * @return
     */
    public BindofficialVo getbindUnBindStatusUseByOver(AdminTokenAuthInfo user,String bindAppId){
    	BindofficialVo bindofficialVo=new BindofficialVo();
		String officialOpenId = null;
		String nickname=null;
		Byte isBind=0;
		if (user.subLogin) {
			ShopChildAccountRecord subAccountInfo = saas.shop.subAccount.getSubAccountInfo(user.sysId,user.subAccountId);
			isBind = subAccountInfo.getIsBind();
			officialOpenId = subAccountInfo.getOfficialOpenId();
			// 子账户登录
		} else {
			// 主账户登录
			ShopAccountRecord accountInfoForId = saas.shop.account.getAccountInfoForId(user.sysId);
			isBind = accountInfoForId.getIsBind();
			officialOpenId = accountInfoForId.getOfficialOpenId();
		}
		if (StringUtils.isNotEmpty(officialOpenId)) {
			// 绑定过公众号
			// shopId找auth_shop
			MpOfficialAccountUserRecord user2 = saas.shop.mpOfficialAccountUserService.getUser(bindAppId,officialOpenId);
			nickname = user2.getNickname();
		}
		bindofficialVo.setOfficialOpenId(officialOpenId);
		bindofficialVo.setIsBind(isBind);
		bindofficialVo.setNickName(nickname);
		return bindofficialVo;
	}

    /**
     * 获取店铺基本信息
     * @param param
     * @return
     */
    public ShopBaseInfoVo getShopBaseInfo(ShopBaseInfoParam param){
        ShopBaseInfoVo shopBaseInfoVo = new ShopBaseInfoVo();
        /** 店铺到期时间 */
        List<ShopBaseInfoVo> baseInfoVos = db().select(ShopRenew.SHOP_RENEW.EXPIRE_TIME)
                .from(ShopRenew.SHOP_RENEW)
                .where(ShopRenew.SHOP_RENEW.SHOP_ID.eq(param.getShopId()))
                .fetchInto(ShopBaseInfoVo.class);
        shopBaseInfoVo.setExpireTime(baseInfoVos!=null&&!baseInfoVos.isEmpty() ? baseInfoVos.get(0).getExpireTime() : null);
        /** 店铺版本 */
        Shop shop = Shop.SHOP.as("shop");
        ShopVersion sv = ShopVersion.SHOP_VERSION.as("sv");
        Select<Record1<String>> select = db().select(shop.SHOP_TYPE).from(shop).where(shop.SHOP_ID.eq(param.getShopId()));
        List<ShopBaseInfoVo> infoVos = db().select(sv.VERSION_NAME).from(sv).where(sv.LEVEL.eq(select))
                .fetchInto(ShopBaseInfoVo.class);
        shopBaseInfoVo.setVersionName(infoVos!=null&&!infoVos.isEmpty() ? infoVos.get(0).getVersionName() : null);
        /** 当前绑定解绑状态 */

        shopBaseInfoVo.setBindStatus(getbindUnBindStatus(param.getOfficialParam()));
        return shopBaseInfoVo;
    }

    /**
     * 获取指定数量的公告title
     * @param param
     */
    public List<FixedAnnouncementVo> getFixedAnnouncement(FixedAnnouncementParam param){
        SortField<Timestamp> orderBy = "asc".equals(param.getOrderBy()) ? Article.ARTICLE.CREATE_TIME.asc() : Article.ARTICLE.CREATE_TIME.desc();
        List<FixedAnnouncementVo> listVo = db().select(Article.ARTICLE.TITLE,Article.ARTICLE.CREATE_TIME)
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
    public void shopAssistant(ShopAssistantParam param, ShopAssistantVo vo, Integer shopId, Integer sysId) {
        shopNav(param, vo, shopId, sysId);
    }

    public ShopAssistantVo shopNav(ShopAssistantParam param, ShopAssistantVo vo, Integer shopId, Integer sysId) {
        /** 微信配置（授权和支付） */
        List<MpAuthShop> authShopList = db().selectFrom(MpAuthShop.MP_AUTH_SHOP)
            .where(MpAuthShop.MP_AUTH_SHOP.SHOP_ID.eq(shopId))
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
        /** 子账号配置 */
        int subCount = db().fetchCount(ShopChildAccount.SHOP_CHILD_ACCOUNT,
            ShopChildAccount.SHOP_CHILD_ACCOUNT.SYS_ID.eq(sysId));
        vo.getDataShop().setChildAccountConf(subCount > 0 ? (byte)0 : (byte)-1);
        if(subCount <= 0){vo.totalPendingIncr();}
        /** 公众号 */
        int officialCount = db().fetchCount(MpOfficialAccount.MP_OFFICIAL_ACCOUNT,
            MpOfficialAccount.MP_OFFICIAL_ACCOUNT.SYS_ID.eq(sysId)
                        .and(MpOfficialAccount.MP_OFFICIAL_ACCOUNT.IS_AUTH_OK.eq(param.getIsAuthOk())));
        vo.getDataShop().setOfficialAccountConf(officialCount > 0 ? (byte)0 : (byte)-1);
        if(officialCount <= 0){vo.totalPendingIncr();}
        return vo;
    }

}
