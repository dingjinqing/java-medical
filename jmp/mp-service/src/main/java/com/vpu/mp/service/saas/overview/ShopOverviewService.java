package com.vpu.mp.service.saas.overview;

import com.vpu.mp.db.main.tables.ShopRenew;
import com.vpu.mp.db.main.tables.records.MpAuthShopRecord;
import com.vpu.mp.db.main.tables.records.MpOfficialAccountUserRecord;
import com.vpu.mp.db.main.tables.records.ShopAccountRecord;
import com.vpu.mp.db.main.tables.records.ShopChildAccountRecord;
import com.vpu.mp.service.foundation.service.MainBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.saas.overView.LoginRecordVo;
import com.vpu.mp.service.pojo.shop.auth.AdminTokenAuthInfo;
import com.vpu.mp.service.pojo.shop.image.ShareQrCodeVo;
import com.vpu.mp.service.pojo.shop.overview.*;
import com.vpu.mp.service.pojo.shop.qrcode.QrCodeTypeEnum;
import com.vpu.mp.service.saas.shop.MpAuthShopService;
import com.vpu.mp.service.saas.shop.ShopChildAccountService;
import com.vpu.mp.service.saas.shop.ShopOfficialAccount;
import com.vpu.mp.service.shop.image.QrCodeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.vpu.mp.db.main.tables.Article.ARTICLE;
import static com.vpu.mp.db.main.tables.MpAuthShop.MP_AUTH_SHOP;
import static com.vpu.mp.db.main.tables.Shop.SHOP;
import static com.vpu.mp.db.main.tables.ShopVersion.SHOP_VERSION;
import static com.vpu.mp.db.main.tables.UserLoginRecord.USER_LOGIN_RECORD;
import static com.vpu.mp.service.shop.store.store.StoreWxService.BYTE_TWO;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static org.apache.commons.lang3.math.NumberUtils.BYTE_ONE;
import static org.apache.commons.lang3.math.NumberUtils.BYTE_ZERO;

/**
 * author liufei
 * date 2019/7/15
 * 概览
 */
@Slf4j
@Service
public class ShopOverviewService extends MainBaseService {
    @Autowired
    public MpAuthShopService mpAuthShopService;

    @Autowired
    public ShopChildAccountService childAccountService;

    @Autowired
    public ShopOfficialAccount shopOfficialAccount;

    @Autowired
    QrCodeService qrCodeService;

    /**
     * 绑定解绑
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
     * 获取绑定/解绑状态  概览使用
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
     */
    public ShopBaseInfoVo getShopBaseInfo(Integer shopId, String bindAppId, AdminTokenAuthInfo user) {
        return ShopBaseInfoVo.builder()
            // 店铺到期时间
            .expireTime(shopExpireTime(shopId))
            // 店铺版本
            .version(shopVersion(shopId))
            // 当前绑定解绑状态
            .bindInfo(getbindUnBindStatusUseByOver(user, bindAppId))
            // 分享二维码信息
            .shareQrCodeVo(share(QrCodeTypeEnum.PAGE_BOTTOM, ""))
            .build();
    }

    /**
     * Shop expire time timestamp.店铺到期时间
     *
     * @param shopId the shop id
     * @return the timestamp
     */
    public Timestamp shopExpireTime(Integer shopId) {
        return db().select(ShopRenew.SHOP_RENEW.EXPIRE_TIME)
            .from(ShopRenew.SHOP_RENEW)
            .where(ShopRenew.SHOP_RENEW.SHOP_ID.eq(shopId))
            .fetchOneInto(Timestamp.class);
//            .fetchOptionalInto(Timestamp.class).orElseThrow(() -> {
//                throw new BusinessException(JsonResultCode.CODE_ACCOUNT_SHOP_EXPRIRE);
//            });
    }

    /**
     * Shop version map.店铺版本
     *
     * @param shopId the shop id
     * @return the map<K, V> K=版本级别, V=版本名称
     */
    public Map<String, String> shopVersion(Integer shopId) {
        Select<Record1<String>> select = db().select(SHOP.SHOP_TYPE).from(SHOP).where(SHOP.SHOP_ID.eq(shopId));
        return db().select(SHOP_VERSION.LEVEL, SHOP_VERSION.VERSION_NAME).from(SHOP_VERSION).where(SHOP_VERSION.LEVEL.eq(select))
            .fetchMap(SHOP_VERSION.LEVEL, SHOP_VERSION.VERSION_NAME);
    }

    /**
     * Share store service share qr code vo.通用分享方法
     *
     * @param qrCodeTypeEnum the qr code type enum
     * @param pathParam      the path param
     * @return the share qr code vo
     */
    public ShareQrCodeVo share(QrCodeTypeEnum qrCodeTypeEnum, String pathParam) {
        String imageUrl = qrCodeService.getMpQrCode(qrCodeTypeEnum, pathParam);
        ShareQrCodeVo vo = new ShareQrCodeVo();
        vo.setImageUrl(imageUrl);
        vo.setPagePath(QrCodeTypeEnum.SECKILL_GOODS_ITEM_INFO.getPathUrl(pathParam));
        return vo;
    }

    /**
     * 获取指定数量的公告title
     */
    public List<FixedAnnouncementVo> getFixedAnnouncement(FixedAnnouncementParam param){
        SortField<Timestamp> orderBy = "asc".equals(param.getOrderBy()) ? ARTICLE.CREATE_TIME.asc() : ARTICLE.CREATE_TIME.desc();
        List<FixedAnnouncementVo> listVo = db().select(ARTICLE.ARTICLE_ID, ARTICLE.TITLE, ARTICLE.CREATE_TIME)
            .from(ARTICLE)
            .where(ARTICLE.STATUS.eq(BYTE_ONE))
            .and(ARTICLE.CATEGORY_ID.eq(param.getCategoryId()))
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

    private ShopAssistantVo shopNav(ShopAssistantParam param, ShopAssistantVo vo, Integer shopId, Integer sysId) {
        AssiDataShop dataShop = Objects.nonNull(vo.getDataShop()) ? vo.getDataShop() : new AssiDataShop();
        // 微信配置（授权和支付）
        MpAuthShopRecord authShopRecord = mpAuthShopService.getAuthShopByShopId(shopId);
        /* 微信配置（授权和支付）,决定了五个完成项
        2表示：未注册小程序，未配置小程序客服，未授权小程序，未开通微信支付，未配置微信支付
        1表示：已注册小程序，已配置小程序客服，已授权小程序，未开通微信支付，未配置微信支付
        0表示：已注册小程序，已配置小程序客服，已授权小程序，已开通微信支付，已配置微信支付
        */
        if (authShopRecord != null) {
            boolean condi1 = StringUtils.isNotBlank(authShopRecord.getPayCertContent());
            boolean condi2 = StringUtils.isNotBlank(authShopRecord.getPayKey());
            boolean condi3 = StringUtils.isNotBlank(authShopRecord.getPayKeyContent());
            boolean condi4 = StringUtils.isNotBlank(authShopRecord.getPayMchId());

            if(condi1 && condi2 && condi3 && condi4){
                dataShop.setWxPayConfigInfo(BYTE_ZERO);
            } else {
                dataShop.setWxPayConfigInfo(BYTE_ONE);
            }
        } else {
            dataShop.setWxPayConfigInfo(BYTE_TWO);
        }
        // 子账号设置 0：已完成子账号设置，否未完成
        if (CollectionUtils.isNotEmpty(childAccountService.getInfoBySysId(sysId))) {
            dataShop.setChildAccountConf(BYTE_ZERO);
        } else {
            dataShop.setChildAccountConf(BYTE_ONE);
        }
        // 公众号  0：已授权公众号，否未授权公众号
        if (CollectionUtils.isNotEmpty(shopOfficialAccount.getOfficialAccountBySysId(sysId, BYTE_ONE))) {
            dataShop.setOfficialAccountConf(BYTE_ZERO);
        } else {
            dataShop.setOfficialAccountConf(BYTE_ONE);
        }
        vo.setDataShop(dataShop);
        log.debug("主库的统计数据整完了");
        return vo;
    }

    /**
     * 用户登录日志
     * @param param
     * @return
     */
    public PageResult<LoginRecordVo> loginRecord(LoginRecordVo param){
        SelectConditionStep<? extends Record> select = db().select(USER_LOGIN_RECORD.SHOP_ID, USER_LOGIN_RECORD.SHOP_NAME, USER_LOGIN_RECORD.SYS_ID,
                USER_LOGIN_RECORD.USER_ID, USER_LOGIN_RECORD.USER_NAME, USER_LOGIN_RECORD.ADD_TIME, USER_LOGIN_RECORD.USER_IP, USER_LOGIN_RECORD.ACCOUNT_TYPE, MP_AUTH_SHOP.NICK_NAME)
                .from(USER_LOGIN_RECORD.leftJoin(MP_AUTH_SHOP).on(USER_LOGIN_RECORD.SHOP_ID.eq(MP_AUTH_SHOP.SHOP_ID)))
                .where(DSL.trueCondition());
        buildOptions(select,param);

        PageResult<LoginRecordVo> list = this.getPageResult(select, param.getCurrentPage(), param.getPageRows(), LoginRecordVo.class);
        return list;
    }

    /**
     * 用户登录日志条件查询
     * @param select
     * @param param
     */
    private void buildOptions(SelectConditionStep<? extends Record> select,LoginRecordVo param){
        if(param.getShopId() != null) {
            select.and(USER_LOGIN_RECORD.SHOP_ID.eq(param.getShopId()));
        }
        if(isNotEmpty(param.getShopName())){
            select.and(USER_LOGIN_RECORD.SHOP_NAME.eq(param.getShopName()));
        }
        if(param.getSysId() != null){
            select.and(USER_LOGIN_RECORD.SYS_ID.eq(param.getSysId()));
        }
        if(isNotEmpty(param.getUserName())){
            select.and(USER_LOGIN_RECORD.USER_NAME.eq(param.getUserName()));
        }
        if(param.getStartAddTime() != null){
            select.and(USER_LOGIN_RECORD.ADD_TIME.ge(param.getStartAddTime()));
        }
        if(param.getEndAddTime() != null){
            select.and(USER_LOGIN_RECORD.ADD_TIME.le(param.getEndAddTime()));
        }
        if(param.getAccountType() != null){
            select.and(USER_LOGIN_RECORD.ACCOUNT_TYPE.eq(param.getAccountType()));
        }
    }

}
