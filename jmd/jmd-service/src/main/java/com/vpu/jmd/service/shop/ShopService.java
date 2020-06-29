package com.vpu.jmd.service.shop;


import com.google.common.collect.Lists;

import com.vpu.jmd.common.NumberConstantType;
import com.vpu.jmd.common.Util;
import com.vpu.jmd.common.excel.ExcelFactory;
import com.vpu.jmd.common.excel.ExcelTypeEnum;
import com.vpu.jmd.common.excel.ExcelWriter;
import com.vpu.jmd.database.DatasourceManager;
import com.vpu.jmd.database.DbConfig;
import com.vpu.jmd.database.DslPlus;
import com.vpu.jmd.database.JsonResultCode;
import com.vpu.jmd.table.main.tables.records.AppAuthRecord;
import com.vpu.jmd.table.main.tables.records.MarketCalendarActivityRecord;
import com.vpu.jmd.table.main.tables.records.ShopAccountRecord;
import com.vpu.jmd.table.main.tables.records.ShopOperationRecord;
import com.vpu.jmd.table.main.tables.records.ShopRecord;
import com.vpu.jmd.table.main.tables.records.UserLoginRecordRecord;
import com.vpu.jmd.service.base.BusinessException;
import com.vpu.jmd.service.base.MainBaseService;
import com.vpu.jmd.service.base.bo.PageResult;
import com.vpu.jmd.service.shop.bo.ShopListQueryParam;
import com.vpu.jmd.service.shop.bo.ShopListQueryResultVo;
import com.vpu.jmd.service.shop.bo.ShopPojo;
import com.vpu.jmd.service.shop.config.OpenAccountTypeEnum;
import com.vpu.jmd.service.shop.config.ShopFlagEnum;
import com.vpu.jmd.service.shop.config.ShopRenewType;
import com.vpu.jmd.service.shop.config.ShopTypeEnum;
import com.vpu.jmd.service.shop.config.SubMerchantEnum;
import jodd.util.StringUtil;
import org.apache.commons.beanutils.BeanUtils;


import org.apache.poi.ss.usermodel.Workbook;
import org.jooq.DatePart;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Record11;
import org.jooq.Record2;
import org.jooq.Result;
import org.jooq.SelectWhereStep;
import org.jooq.Table;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import static com.vpu.jmd.table.main.Tables.MP_AUTH_SHOP;
import static com.vpu.jmd.table.main.Tables.SHOP;
import static com.vpu.jmd.table.main.Tables.SHOP_ACCOUNT;
import static com.vpu.jmd.table.main.Tables.SHOP_CHILD_ROLE;
import static com.vpu.jmd.table.main.Tables.SHOP_RENEW;


/**
 *
 * @author 新国
 *
 */
@Service
public class ShopService extends MainBaseService {

	@Autowired
	public ShopRenewService renew;

	@Autowired
	public ChildAccountService sysChildAccount;
	/**
	 * 店铺管理-店铺标签管理
	 */
	@Autowired
	public ShopTagService shopTagService;
	@Autowired
	public ShopTaokeService shopTaokeService;

	@Autowired
	public MpOperateLogService mpOperateLog;

	@Autowired
	public MpAuthShopService mp;

	@Autowired
	public MarketSysCalendarService calendarService;
	@Autowired
	protected DatasourceManager datasourceManager;
	@Autowired
	public ShopVersionService version;
	@Autowired
	public ShopImageManageService image;
	@Autowired
	public ShopAccountService account;
	@Autowired
	public ShopAppService shopApp;
	@Autowired
	public ShopChildAccountService subAccount;

	public PageResult<ShopListQueryResultVo> getPageList(ShopListQueryParam param) {
		logger().info("查询店铺列表");
		SelectWhereStep<?> select = getSql(param);
		PageResult<ShopListQueryResultVo> result = this.getPageResult(select, param.currentPage, param.pageRows,
				ShopListQueryResultVo.class);
		for (ShopListQueryResultVo shopList : result.dataList) {
			shopList.setRenewMoney(this.renew.getShopRenewTotal(shopList.getShopId()));
			Timestamp expireTime = this.renew.getShopRenewExpireTime(shopList.getShopId());
			String expireStatus = "1";
			if (expireTime != null) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(expireTime);
				cal.set(Calendar.HOUR, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				if (cal.getTimeInMillis() > Calendar.getInstance().getTimeInMillis()) {
					expireStatus = "0";
				}
			}
			shopList.setExpireTime(expireTime);
			shopList.setShopExpireStatus(expireStatus);
			shopList.setSpecialInfo(shopSpecialConf(shopList));
			shopList.setRenewNum(renew.getRenewals(shopList.getShopId()));
			shopList.setAccountIds(sysChildAccount.setManager(shopList.getAccountId()));
			shopList.setAfterSaleIds(sysChildAccount.setManager(shopList.getAfterSaleId()));
			shopList.setShopTags(shopTagService.getTagInfoByShopId(shopList.getShopId()));
			Integer failCount = shopList.getFailCount();
			failCount = failCount == null ? 0 : failCount;
			shopList.setFailCount(failCount);
		}
		logger().info("查询店铺列表结束");
		return result;
	}

	private SelectWhereStep<?> getSql(ShopListQueryParam param) {
		Table<Record2<String, Integer>> ol = mpOperateLog.getFailCoutSQL(param.getFailStartTime(),
				param.getFailEndTime());
		SelectWhereStep<?> select = db().select(SHOP.fields())
				.select(MP_AUTH_SHOP.APP_ID, MP_AUTH_SHOP.IS_AUTH_OK, MP_AUTH_SHOP.NICK_NAME,
						MP_AUTH_SHOP.PRINCIPAL_NAME, MP_AUTH_SHOP.IS_SUB_MERCHANT,
						SHOP_ACCOUNT.USER_NAME.as("auserName"), ol.field("failCount"))
				.from(SHOP).join(SHOP_ACCOUNT).on(SHOP.SYS_ID.eq(SHOP_ACCOUNT.SYS_ID)).leftJoin(MP_AUTH_SHOP)
				.on(SHOP.SHOP_ID.eq(DSL.cast(MP_AUTH_SHOP.SHOP_ID, Integer.class))).leftJoin(ol)
				.on(ol.field("olAppId").cast(String.class).eq(MP_AUTH_SHOP.APP_ID));
		select = this.buildOptions(select, param);
		Byte orderByType = param.getOrderByType();
		Byte orderByAsc = param.getOrderByAsc();
		byte zero = (byte) 0;
		orderByAsc = orderByAsc == null ? zero : orderByAsc;
		if (orderByType == null) {
			select.orderBy(SHOP.CREATED.desc());
		} else {
			switch (orderByType) {
			case (byte) 1:
				logger().info("根据创建日期");
				if (orderByAsc.equals(zero)) {
					select.orderBy(SHOP.CREATED.desc());
				} else {
					select.orderBy(SHOP.CREATED.asc());
				}
				break;
			case (byte) 2:
				logger().info("根据过期日期");
				if (orderByAsc.equals(zero)) {
					select.orderBy(SHOP.EXPIRE_TIME.desc());
				} else {
					select.orderBy(SHOP.EXPIRE_TIME.asc());
				}
				break;
			case (byte) 3:
				logger().info("根据失败次数日期");
				if (orderByAsc.equals(zero)) {
					select.orderBy(ol.field("failCount").desc());
				} else {
					select.orderBy(ol.field("failCount").asc());
				}
				break;
			default:
				select.orderBy(SHOP.CREATED.desc());
				break;
			}
		}
		return select;
	}

	public SelectWhereStep<?> buildOptions(SelectWhereStep<?> select, ShopListQueryParam param) {
		if (param == null) {
			return select;
		}
		if (!StringUtils.isEmpty(param.keywords)) {
			Integer shopId = Util.convert(param.keywords, Integer.class, 0);
			String keywords = likeValue(param.keywords);
			select.where(SHOP.SHOP_NAME.like(keywords).or(SHOP.MOBILE.like(keywords))
					.or(MP_AUTH_SHOP.NICK_NAME.like(keywords)).or(SHOP.SHOP_ID.eq(shopId)));
		}

		// 使用中
		Integer shopUsingStatus = 1;
		// 已过期
		Integer shopExpiredStatus = 2;
		// 即将过期
		Integer shopSoonExpiredStatus = 3;
		if (param.isUse != null && param.isUse.equals(shopUsingStatus)) {
			// 店铺在使用中
			select.where(SHOP.EXPIRE_TIME.ge(DSL.currentTimestamp()));
		}

		if (param.isUse != null && param.isUse.equals(shopExpiredStatus)) {
			// 店铺已过期
			select.where(SHOP.EXPIRE_TIME.lt(DSL.currentTimestamp()));
		}
		if (param.isUse != null && param.isUse.equals(shopSoonExpiredStatus)) {
			// 即将过期
			select.where(SHOP.EXPIRE_TIME.le(DSL.timestampAdd(DSL.currentTimestamp(), 1, DatePart.MONTH))
					.and(SHOP.EXPIRE_TIME.ge(DSL.currentTimestamp())));
		}

		if (!StringUtils.isEmpty(param.shopType)) {
			select.where(SHOP.SHOP_TYPE.eq(param.shopType));
		}

		if (StringUtils.isEmpty(param.shopType) && !StringUtils.isEmpty(param.shopTypes)) {
			// 区分体验版和付费版
			if (param.shopTypes.equals(ShopConst.shopTypes.TRIAL_VERSION)) {
				// 体验版
				select.where(SHOP.SHOP_TYPE.eq(ShopConst.shopType.V_1));
			}
			if (param.shopTypes.equals(ShopConst.shopTypes.PAID_VERSION)) {
				// 付费版
				select.where(SHOP.SHOP_TYPE.in(ShopConst.shopType.V_2, ShopConst.shopType.V_3, ShopConst.shopType.V_4));
			}

		}
		if (param.shopFlag != null) {
			if (param.shopFlag == 0) {
				select.where(SHOP.SHOP_FLAG.eq(param.shopFlag).or(SHOP.SHOP_FLAG.isNull()));
			} else {
				select.where(SHOP.SHOP_FLAG.eq(param.shopFlag));
			}
		}

		if (!StringUtils.isEmpty(param.accountKey)) {
			String key = this.likeValue(param.accountKey);
			select.where(SHOP_ACCOUNT.COMPANY.like(key).or(MP_AUTH_SHOP.PRINCIPAL_NAME.like(key))
					.or(SHOP.SHOP_ID.like(key)));
		}

		if (param.isEnabled != null) {
			select.where(SHOP.IS_ENABLED.eq(param.isEnabled));
		}
		if (param.hidBottom != null) {
			select.where(SHOP.HID_BOTTOM.eq(param.hidBottom));
		}

		if (!StringUtils.isEmpty(param.expireStartTime)) {
			select.where(SHOP.EXPIRE_TIME.ge(param.expireStartTime));
		}
		if (!StringUtils.isEmpty(param.expireEndTime)) {
			select.where(SHOP.EXPIRE_TIME.le(param.expireEndTime));
		}
		if (param.getCalendarActId() != null && param.getCalendarActId() != 0) {
			List<String> list = getNeedShopIdByCal(param.getCalendarActId());
			select.where(SHOP.SHOP_ID.in(list));
		}
		// 根据标签id查询
		if (null != param.getTagIds() && !param.getTagIds().isEmpty()) {
			List<Integer> shopIds = shopTagService.getShopIdByTagId(param.getTagIds());
			select.where(SHOP.SHOP_ID.in(shopIds));
		}

		Integer afterSaleId = param.getAfterSaleId();
		if (afterSaleId != null) {
			if (afterSaleId == -1) {
				// 未设置
				select.where(SHOP.AFTER_SALE_ID.isNull().or(SHOP.AFTER_SALE_ID.eq("")));
			}
			if (afterSaleId > 0) {
				// 设置了
				select.where(DslPlus.findInSet(afterSaleId, SHOP.AFTER_SALE_ID));
			}
		}
		Integer accountId = param.getAccountId();
		if (accountId != null) {
			if (accountId == -1) {
				// 未设置
				select.where(SHOP.ACCOUNT_ID.isNull().or(SHOP.AFTER_SALE_ID.eq("")));
			}
			if (accountId > 0) {
				// 设置了
				select.where(DslPlus.findInSet(accountId, SHOP.ACCOUNT_ID));
			}
		}

		if (null != param.getProvinceCode()) {
			select.where(SHOP.PROVINCE_CODE.eq(param.getProvinceCode()));
			if (null != param.getCityCode()) {
				select.where(SHOP.CITY_CODE.eq(param.getCityCode()));
				if (null != param.getDistrictCode()) {
					select.where(SHOP.DISTRICT_CODE.eq(param.getDistrictCode()));
				}
			}
		}

		if (null != param.getOpenAccountType()) {
			select.where(SHOP.OPEN_ACCOUNT_TYPE.eq(param.getOpenAccountType()));
		}
		Integer renewTimesStart = param.getRenewTimesStart();
		Integer renewTimesEnd = param.getRenewTimesEnd();
		if (renewTimesStart != null && renewTimesStart >= 0) {
			Integer[] i = { 1, 5, 6 };
			List<Integer> asList = Arrays.asList(i);
			select.where(db().select(DSL.count()).from(SHOP_RENEW)
					.where(SHOP_RENEW.SHOP_ID.eq(SHOP.SHOP_ID).and(SHOP_RENEW.RENEW_TYPE.in(asList))).asField()
					.ge(renewTimesStart));
		}
		if (renewTimesEnd != null && renewTimesEnd >= 0) {
			Integer[] i = { 1, 5, 6 };
			List<Integer> asList = Arrays.asList(i);
			select.where(db().select(DSL.count()).from(SHOP_RENEW)
					.where(SHOP_RENEW.SHOP_ID.eq(SHOP.SHOP_ID).and(SHOP_RENEW.RENEW_TYPE.in(asList))).asField()
					.le(renewTimesEnd));
		}
		return select;
	}

	private List<String> getNeedShopIdByCal(Integer calendarActId) {
		List<String> list = new ArrayList<String>();
		MarketCalendarActivityRecord record = calendarService.calendarActivityService.getInfoById(calendarActId);
		if (record != null) {
			SysCalendarActVo vo = record.into(SysCalendarActVo.class);
			String shopIds = vo.getShopIds();
			if (!StringUtils.isEmpty(shopIds)) {
				String[] split = shopIds.split(",");
				list = Arrays.asList(split);
				return list;
			}
		}
		return null;
	}

	/**
	 * TODO 加个事务
	 *
	 * @param shopReq
	 * @return
	 */
	public Boolean addShop(ShopReq shopReq, SystemTokenAuthInfo user, HttpServletRequest request) {
		someVaild(shopReq);
		shopReq.setShopId(getCanUseShopId());
		DbConfig dbConfig = datasourceManager.getInstallShopDbConfig(shopReq.getShopId());
		if (!StringUtils.isEmpty(shopReq.getDbConfigId())) {
			// TODO 加插入传来的数据库信息
		}
		shopReq.setDbConfig(Util.toJson(dbConfig));
		if (!databaseManager.installShopDb(dbConfig)) {
			return false;
		}
		logger().info("数据库创建成功");
		ShopRecord record = db().newRecord(SHOP, shopReq);
		if (shopReq.getExpireTime() != null && shopReq.getFunExpireTime() == null) {
			shopReq.setFunExpireTime(shopReq.getExpireTime());
		}
		if (db().executeInsert(record) != 1) {
			return false;
		}
		logger().info("插入数据成功");

		// 更新version_config
		if (updateConfig(shopReq) == 1) {
			logger().info("更新version_config成功");
		}
		// 更新记录表
		if (updateOperation(record, user, request, new ShopRecord()) == 1) {
			logger().info("更新ShopOperation记录表成功");
		}
		shopTaokeService.insertToke(shopReq);
		return true;
	}

	/**
	 * 得到所有店铺
	 *
	 * @return
	 */
	public Result<ShopRecord> getAll() {
		return db().fetch(SHOP);
	}

	/**
	 * 得到可用店铺ID
	 *
	 * @return
	 */
	public Integer getCanUseShopId() {
		int maxNumber = 1000;
		for (int i = 0; i < maxNumber; i++) {
			Integer shopId = Util.randomInteger(100000, 1000000);
			if (db().fetchCount(SHOP, SHOP.SHOP_ID.eq(shopId)) == 0) {
				return shopId;
			}
		}
		Integer maxShopId = (Integer) db().select(DSL.max(SHOP.SHOP_ID)).from(SHOP).fetch().getValue(0, 0);
		return maxShopId + 1;
	}

	public Integer updateShop(ShopPojo shop) {
		shop.setIsEnabled(shop.getIsEnabled() == null ? 0 : shop.getIsEnabled());
		shop.setIsEnabled(shop.getHidBottom() == null ? 0 : shop.getHidBottom());
		ShopRecord record = db().newRecord(SHOP, shop);
		return db().executeUpdate(record);
	}

	public boolean hasMobile(String mobile) {
		return getShopByMobile(mobile) != null;
	}

	public ShopRecord getShopById(Integer shopId) {
		return db().selectFrom(SHOP).where(SHOP.SHOP_ID.eq(shopId)).fetchAny();
	}

	public ShopRecord getShopByMobile(String mobile) {
		return db().selectFrom(SHOP).where(SHOP.MOBILE.eq(mobile)).fetchAny();
	}

	public Integer getShopAccessRoleId(Integer sysId, Integer shopId, Integer subAccountId) {
		if (subAccountId == 0) {
			ShopRecord shop = this.getShopById(shopId);
			if (shop != null && shop.getSysId().equals(sysId)) {
				return 0;
			}
			return -1;
		}
		Record1<Integer> role = db().select(SHOP_CHILD_ROLE.ROLE_ID).from(SHOP).leftJoin(SHOP_CHILD_ROLE)
				.on(SHOP.SHOP_ID.eq(SHOP_CHILD_ROLE.SHOP_ID)).where(SHOP.SHOP_ID.eq(shopId)).and(SHOP.SYS_ID.eq(sysId))
				.and(SHOP_CHILD_ROLE.ACCOUNT_ID.eq(subAccountId)).fetchAny();
		if (role != null) {
			return role.value1();
		}
		return -1;
	}

	/**
	 * 得到角色权限对应店铺列表 `state` '0 入驻申请，1审核通过，2审核不通过',审核不通过不能登录到店铺后台 `business_state`
	 * '营业状态 0未营业 1营业',未营业不能下单，加入购物车，下单接口提示 expire_time 过期可以登录到后台，店铺是未营业状态
	 *
	 * @param sysId
	 * @param subAccountId
	 * @return
	 */
	public Result<Record11<Integer, Integer, String, String, Timestamp, Byte, Byte, Byte, String, String, String>> getRoleShopList(
			Integer sysId, Integer subAccountId) {
		SelectWhereStep<Record11<Integer, Integer, String, String, Timestamp, Byte, Byte, Byte, String, String, String>> select = db()
				.selectDistinct(SHOP.SHOP_ID, SHOP.SYS_ID, SHOP.SHOP_NAME, SHOP.SHOP_AVATAR, SHOP.CREATED, SHOP.STATE,
						SHOP.BUSINESS_STATE, SHOP.IS_ENABLED, SHOP.SHOP_TYPE, SHOP.CURRENCY, SHOP.SHOP_LANGUAGE)
				.from(SHOP).leftJoin(SHOP_CHILD_ROLE).on(SHOP.SHOP_ID.eq(SHOP_CHILD_ROLE.SHOP_ID));
		select.where(SHOP.SYS_ID.eq(sysId));
		if (subAccountId > 0) {
			select.where(SHOP_CHILD_ROLE.ACCOUNT_ID.eq(subAccountId));
		}
		return select.orderBy(SHOP.CREATED.asc()).fetch();
	}

	/**
	 * 店铺基础配置-店铺基础信息get
	 *
	 */
	public ShopBaseConfig getShopBaseInfoById(Integer shopId) {
		ShopPojo shop = db().select(SHOP.SHOP_AVATAR, SHOP.SHOP_NAME, SHOP.BUSINESS_STATE, SHOP.CREATED,
				SHOP.BUSINESS_STATE, SHOP.LOGO).from(SHOP).where(SHOP.SHOP_ID.eq(shopId)).fetchOneInto(ShopPojo.class);
		ShopBaseConfig shopBaseCfgInfo = new ShopBaseConfig();
		shopBaseCfgInfo.setExpireTime(saas.shop.renew.getShopRenewExpireTime(shopId));
		shopBaseCfgInfo.setShopName(shop.getShopName());
		shopBaseCfgInfo.setShopAvatar(shop.getShopAvatar());
		shopBaseCfgInfo.setCreated(shop.getCreated());
		shopBaseCfgInfo.setBusinessState(shop.getBusinessState());
		shopBaseCfgInfo.setLogo(shop.getLogo());
		shopBaseCfgInfo.setShowLogo(saas.getShopApp(shopId).config.shopCommonConfigService.getShowLogo());
		shopBaseCfgInfo.setLogoLink(saas.getShopApp(shopId).config.shopCommonConfigService.getLogoLink());
		return shopBaseCfgInfo;
	}

	/**
	 * 店铺基础配置-店铺基础信息更新
	 *
	 */
	public void updateShopBaseInfo(ShopBaseConfig shop, int shopId) {
		shop.setCreated(null);
		shop.setExpireTime(null);
		ShopRecord record = new ShopRecord();
		assign(shop, record);
		record.setShopId(shopId);
		this.transaction(() -> {
			db().executeUpdate(record);
			saas.getShopApp(shopId).config.shopCommonConfigService.setShowLogo(shop.getShowLogo());
			if (StringUtil.isNotEmpty(shop.getLogoLink())) {
				saas.getShopApp(shopId).config.shopCommonConfigService.setLogoLink(shop.getLogoLink());
			}
		});
	}

	public List<ShopSelectInnerResp> getShopList(
			List<Record11<Integer, Integer, String, String, Timestamp, Byte, Byte, Byte, String, String, String>> shopList) {
		List<ShopSelectInnerResp> dataList = new ArrayList<>(shopList.size());
		for (Record11<Integer, Integer, String, String, Timestamp, Byte, Byte, Byte, String, String, String> record : shopList) {
			ShopSelectInnerResp shopInner = new ShopSelectInnerResp();
			Timestamp expireTime = renew.getShopRenewExpireTime(Util.getInteger(record.get(SHOP.SHOP_ID)));
			String expireStatus = "1";
			if (expireTime != null) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(expireTime);
				cal.set(Calendar.HOUR, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				if (cal.getTimeInMillis() > Calendar.getInstance().getTimeInMillis()) {
					expireStatus = "0";
				}
			}
			shopInner.setShopId(record.get(SHOP.SHOP_ID));
			shopInner.setSysId(record.get(SHOP.SYS_ID));
			shopInner.setShopName(record.get(SHOP.SHOP_NAME));
			shopInner.setShopAvatar(StringUtils.isEmpty(record.get(SHOP.SHOP_AVATAR))
					? image.imageUrl("image/admin/shop_logo_default.png")
					: image.imageUrl(record.get(SHOP.SHOP_AVATAR)));
			shopInner.setCreated(record.get(SHOP.CREATED));
			shopInner.setState(record.get(SHOP.STATE));
			shopInner.setBusinessState(record.get(SHOP.BUSINESS_STATE));
			shopInner.setIsEnabled(record.get(SHOP.IS_ENABLED));
			shopInner.setShopType(record.get(SHOP.SHOP_TYPE));
			shopInner.setExpireTime(expireTime);
			shopInner.setExpireTimeStatus(expireStatus);
			shopInner.setCurrency(record.get(SHOP.CURRENCY));
			shopInner.setShopLanguage(record.get(SHOP.SHOP_LANGUAGE));
			dataList.add(shopInner);
		}
		return dataList;
	}

	/**
	 * 判断店铺是否过期,true为过期，false没过期
	 *
	 * @return
	 */
	public Boolean checkExpire(Integer shopId) {
		Timestamp expireTime = renew.getShopRenewExpireTime(shopId);
		if (expireTime == null) {
			return true;
		}
		if (expireTime != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(expireTime);
			cal.set(Calendar.HOUR, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			if (cal.getTimeInMillis() > Calendar.getInstance().getTimeInMillis()) {
				return false;
			}
		}
		return true;
	}

	public int insertUserLoginRecord(UserLoginRecordRecord record) {
		return db().executeInsert(record);
	}

	/**
	 * 更新version_config
	 *
	 * @param shopReq
	 * @param shopId
	 * @return
	 */
	public Integer updateConfig(ShopReq shopReq) {
		VersionConfig versionConfig = version.getVersionConfig(shopReq.getShopType());
		// 把mainConfig置空，用户的菜单权限为自己mainConfig+shop_version中的权限，这样当增减shop_version中权限时候，当前表不用进行权限增删
		versionConfig.mainConfig = new VersionMainConfig();
		return db().update(SHOP).set(SHOP.VERSION_CONFIG, Util.toJson(versionConfig))
				.where(SHOP.SHOP_ID.eq(shopReq.getShopId())).execute();
	}

	public Integer updateOperation(ShopRecord shopRecord, SystemTokenAuthInfo user, HttpServletRequest request,
			ShopRecord newShopRecord) {
		ShopOperationRecord sRecord = new ShopOperationRecord();
		sRecord.setShopId(shopRecord.getShopId());
		if (user.isSubLogin()) {
			sRecord.setOperatorId(user.subAccountId);
			sRecord.setOperator(user.getSubUserName());
		}
		sRecord.setOperator(user.getUserName());
		sRecord.setOperatorId(user.getSystemUserId());
		sRecord.setDesc(diffEdit(shopRecord, newShopRecord));
		sRecord.setIp(Util.getCleintIp(request));
		return db().executeInsert(sRecord);
	}

	/**
	 * 根据id查询ShopRecord，diffEdit里用
	 *
	 * @param shopId
	 * @return
	 */
	public ShopRecord queryOldRcord(Integer shopId) {
		ShopRecord oldShop = db().selectFrom(SHOP).where(SHOP.SHOP_ID.eq(shopId)).fetchOne();
		return oldShop;
	}

	/**
	 * 更新b2c_shop_operation
	 *
	 * @param newShop
	 * @param oldShop
	 * @return
	 */
	public String diffEdit(ShopRecord newShop, ShopRecord oldShop) {
		StringBuffer sbf = new StringBuffer("新建或者更新");
		if ((!StringUtils.isEmpty(newShop.getMobile())) && !newShop.getMobile().equals(oldShop.getMobile())) {
			sbf.append("电话:" + newShop.getMobile() + ",");
		}
		if ((!StringUtils.isEmpty(newShop.getShopName())) && !newShop.getShopName().equals(oldShop.getShopName())) {
			sbf.append("店铺名称:" + newShop.getShopName() + ",");
		}
		if ((!StringUtils.isEmpty(newShop.getShopPhone())) && !newShop.getShopPhone().equals(oldShop.getShopPhone())) {
			sbf.append("店铺客服电话:" + newShop.getShopPhone() + ",");
		}
		if ((!StringUtils.isEmpty(newShop.getShopNotice()))
				&& !newShop.getShopNotice().equals(oldShop.getShopNotice())) {
			sbf.append("店铺公告:" + newShop.getShopNotice() + ",");
		}
		if ((!StringUtils.isEmpty(newShop.getShopWx())) && !newShop.getShopWx().equals(oldShop.getShopWx())) {
			sbf.append("店铺微信:" + newShop.getShopWx() + ",");
		}
		if ((!StringUtils.isEmpty(newShop.getShopEmail())) && !newShop.getShopEmail().equals(oldShop.getShopEmail())) {
			sbf.append("店铺邮箱:" + newShop.getShopEmail() + ",");
		}
		if ((!StringUtils.isEmpty(newShop.getIsEnabled())) && !newShop.getIsEnabled().equals(oldShop.getIsEnabled())) {
			sbf.append("店铺禁用:" + newShop.getIsEnabled() + ",");
		}
		if ((!StringUtils.isEmpty(newShop.getShopQq())) && !newShop.getShopQq().equals(oldShop.getShopQq())) {
			sbf.append("店铺客服QQ:" + newShop.getShopQq() + ",");
		}
		if ((!StringUtils.isEmpty(newShop.getShopType())) && !newShop.getShopType().equals(oldShop.getShopType())) {
			sbf.append("店铺类型:" + version.getVersionNameByLevel(newShop.getShopType()));
		}
		return sbf.toString();

	}

	public ShopRecord checkShop(Integer shopId, Integer sysId) {
		return db().selectFrom(SHOP).where(SHOP.SHOP_ID.eq(shopId).and(SHOP.SYS_ID.eq(sysId))).fetchOne();
	}

	public Integer getShopNumber(Integer sysId) {
		return (Integer) db().select(DSL.count(SHOP.SYS_ID)).from(SHOP).where(SHOP.SYS_ID.eq(sysId)).fetchAny(0);
	}

	public Record getShop(Integer shopId) {
		return db().select(SHOP.fields()).from(SHOP).join(MP_AUTH_SHOP).on(SHOP.SHOP_ID.eq(MP_AUTH_SHOP.SHOP_ID))
				.where(SHOP.SHOP_ID.eq(shopId)).fetchAny();
	}

	public Integer updateRowIsEnable(Integer shopId, Byte isEnable) {
		return db().update(SHOP).set(SHOP.IS_ENABLED, isEnable).where(SHOP.SHOP_ID.eq(shopId)).execute();
	}

	public Integer updateRowHidBottom(Integer shopId, Byte hidBottem) {
		return db().update(SHOP).set(SHOP.HID_BOTTOM, hidBottem).where(SHOP.SHOP_ID.eq(shopId)).execute();
	}

	public List<String> shopSpecialConf(ShopListQueryResultVo shopList) {
		List<String> specialInfo = new ArrayList<String>();
		ShopAccountRecord accountInfoForId = account.getAccountInfoForId(shopList.getSysId());
		if (accountInfoForId.getBaseSale() == 1) {
			// 初始销量功能开启
			specialInfo.add("BaseSale");
		}
		if (accountInfoForId.getAddCommentSwitch() == 1) {
			// 商家添加评价功能开启
			specialInfo.add("AddCommentSwitch");
		}
		AppAuthRecord shopAppByErp = shopApp.getShopAppByErp(shopList.getShopId());
		if (shopAppByErp != null) {
			if (shopAppByErp.getStatus() == 1) {
				// erp已对接
				specialInfo.add("ErpStatus");
			}
		}
		// TODO 开启微信全链路 shop表加 seller_account
		if (shopList.getHidBottom() == 1) {
			// 隐藏底部功能开启
			specialInfo.add("HidBottom");
		}
		if (shopList.getVersionConfig() != null) {
			VersionConfig parseJson = Util.parseJson(shopList.getVersionConfig(), VersionConfig.class);
			VersionNumberConfig numConfig = parseJson.numConfig;
			if (numConfig.pictureNumPlus != null && numConfig.pictureNumPlus != 0) {
				// 图片空间已扩容
				specialInfo.add("PictureNumPlus");
			}
			if (numConfig.videoNumPlus != null && numConfig.videoNumPlus != 0) {
				// 视频空间已扩容
				specialInfo.add("VideoNumPlus");
			}
			if (numConfig.decorateNumPlus != null && numConfig.decorateNumPlus != 0) {
				// 页面装修数量已扩容
				specialInfo.add("DecorateNumPlus");
			}
		}
		return specialInfo;
	}

	/**
	 * 编辑店铺
	 *
	 * @param shopReq
	 * @param user
	 * @param request
	 * @return
	 */
	public Boolean editShop(ShopReq shopReq, SystemTokenAuthInfo user, HttpServletRequest request,
			ShopRecord oldShopReq) {
		someVaild(shopReq);
		ShopRecord record = db().newRecord(SHOP, shopReq);
		if (db().executeUpdate(record) != 1) {
			return false;
		}
		logger().info("更新数据成功");

		// 更新记录表
		if (updateOperation(record, user, request, oldShopReq) == 1) {
			logger().info("更新ShopOperation记录表成功");
		}
		shopTaokeService.updateToke(shopReq);
		return true;
	}

	/**
	 * 根据店铺id获取店铺头像
	 *
	 * @param shopId
	 * @return
	 */
	public String getShopAvatarById(Integer shopId) {
		return db().select(SHOP.SHOP_AVATAR).from(SHOP).where(SHOP.SHOP_ID.eq(shopId)).fetchOptionalInto(String.class)
				.orElse(null);
	}

	/**
	 * 获取单个店铺信息
	 *
	 * @param shopId
	 * @return
	 */
	public ShopSelectInnerResp getShopInfo(Integer shopId) {
		ShopRecord record = getShopById(shopId);
		Timestamp expireTime = renew.getShopRenewExpireTime(shopId);
		String expireStatus = "1";
		if (expireTime != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(expireTime);
			cal.set(Calendar.HOUR, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			if (cal.getTimeInMillis() > Calendar.getInstance().getTimeInMillis()) {
				expireStatus = "0";
			}
		}
		ShopSelectInnerResp vo = record.into(ShopSelectInnerResp.class);
		vo.setExpireTime(expireTime);
		vo.setExpireTimeStatus(expireStatus);
		vo.setShopAvatar(
				StringUtils.isEmpty(record.get(SHOP.SHOP_AVATAR)) ? image.imageUrl("image/admin/shop_logo_default.png")
						: image.imageUrl(record.get(SHOP.SHOP_AVATAR)));
		return vo;
	}

	/**
	 * 获取当前店铺币种
	 *
	 * @param shopId shopId
	 * @return currency
	 */
	public String getCurrency(Integer shopId) {
		String currency = db().select(SHOP.CURRENCY).from(SHOP).where(SHOP.SHOP_ID.eq(shopId))
				.fetchOneInto(String.class);
		if (currency == null) {
			return "CNY";
		}
		return currency;
	}

	/**
	 * 获取所有可用店铺id
	 *
	 * @return 可用店铺id集合
	 */
	public List<Integer> getEnabledShopIds() {
		Result<ShopRecord> records = getAll();
		if (!CollectionUtils.isEmpty(records)) {
			return records.stream()
//                .filter(x-> x.getIsEnabled() == 1)
					.map(ShopRecord::getShopId).collect(Collectors.toList());
		}
		return Lists.newArrayList();
	}

	/**
	 * 不是体验版的 得到角色权限对应店铺列表 `state` '0 入驻申请，1审核通过，2审核不通过',审核不通过不能登录到店铺后台
	 * `business_state` '营业状态 0未营业 1营业',未营业不能下单，加入购物车，下单接口提示 expire_time
	 * 过期可以登录到后台，店铺是未营业状态
	 *
	 * @param sysId
	 * @param subAccountId
	 * @return
	 */
	public Result<Record11<Integer, Integer, String, String, Timestamp, Byte, Byte, Byte, String, String, String>> getRoleShopListNoV1(
			Integer sysId, Integer subAccountId) {
		SelectWhereStep<Record11<Integer, Integer, String, String, Timestamp, Byte, Byte, Byte, String, String, String>> select = db()
				.selectDistinct(SHOP.SHOP_ID, SHOP.SYS_ID, SHOP.SHOP_NAME, SHOP.SHOP_AVATAR, SHOP.CREATED, SHOP.STATE,
						SHOP.BUSINESS_STATE, SHOP.IS_ENABLED, SHOP.SHOP_TYPE, SHOP.CURRENCY, SHOP.SHOP_LANGUAGE)
				.from(SHOP).leftJoin(SHOP_CHILD_ROLE).on(SHOP.SHOP_ID.eq(SHOP_CHILD_ROLE.SHOP_ID));
		select.where(SHOP.SYS_ID.eq(sysId).and(SHOP.SHOP_TYPE.notEqual("v1")));
		if (subAccountId > 0) {
			select.where(SHOP_CHILD_ROLE.ACCOUNT_ID.eq(subAccountId));
		}
		return select.orderBy(SHOP.CREATED.asc()).fetch();
	}

	/**
	 * 店铺列表查询运营人或管理人列表，并标记
	 *
	 * @param act
	 * @param shopId
	 * @param currentPage
	 * @param pageRows
	 * @return
	 */
	public SystemChildSetVo accountUserList(String act, Integer shopId, Integer currentPage, Integer pageRows) {
		SystemChildSetVo result = new SystemChildSetVo();
		ShopRecord shopInfo = getShopById(shopId);
		if (null == shopInfo) {
			logger().info("传入的店铺id:{},对应店铺不存在", shopId);
			return null;
		}
		String value = "";
		if (act.equals("audit")) {
			// 运营人
			value = shopInfo.getAccountId();

		}
		if (act.equals("after_sale")) {
			// 售后人
			value = shopInfo.getAfterSaleId();
		}
		value = null == value ? "" : value;
		String[] ids = value.split(",");
		PageResult<SystemChildAccountVo> lists = sysChildAccount.getPageList(currentPage, pageRows);
		List<SystemChildAccountVo> dataList = lists.getDataList();
		List<SystemChildShowVo> resultList = new ArrayList<SystemChildShowVo>();
		int listSize = dataList.size();
		int num = 0;
		for (SystemChildAccountVo item : dataList) {
			SystemChildShowVo vo = new SystemChildShowVo();
			try {
				BeanUtils.copyProperties(vo, item);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Boolean have = haveInIt(ids, vo.getAccountId());
			num = have ? num + 1 : num;
			vo.setCheck(have);
			resultList.add(vo);
		}
		if (listSize == num && listSize != 0) {
			result.setAllCheck(true);
		}
		result.setList(resultList);
		result.setPage(lists.getPage());
		return result;
	}

	private Boolean haveInIt(String[] ids, Integer id) {
		for (String string : ids) {
			if (string.equals(String.valueOf(id))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 更新运营人或管理人
	 *
	 * @param shopId
	 * @param act
	 * @param managerId
	 * @return
	 */
	public Boolean setShopManager(Integer shopId, String act, String managerId) {
		ShopRecord shopInfo = getShopById(shopId);
		if (null == shopInfo) {
			logger().info("传入的店铺id:{},对应店铺不存在", shopId);
			return false;
		}

		int execute = 0;
		if (act.equals("audit")) {
			// 运营人
			execute = db().update(SHOP).set(SHOP.ACCOUNT_ID, managerId).where(SHOP.SHOP_ID.eq(shopId)).execute();
			logger().info("店铺：{}更新运营人为：{}，结果：{}", shopId, managerId, execute);

		}
		if (act.equals("after_sale")) {
			// 售后人
			execute = db().update(SHOP).set(SHOP.AFTER_SALE_ID, managerId).where(SHOP.SHOP_ID.eq(shopId)).execute();
			logger().info("店铺：{}更新售后人为：{}，结果：{}", shopId, managerId, execute);
		}
		return execute > 0;

	}

	/**
	 * 一些字段的校验
	 *
	 * @param shopReq
	 */
	public void someVaild(ShopReq shopReq) {
		Byte jdGoods = shopReq.getJdGoods();
		if(jdGoods!=null&&jdGoods.equals(NumberConstantType.ONE)) {
			buildCode(shopReq.getJdAppKey(), "京东appKey");
			buildCode(shopReq.getJdAppSecret(), "京东appSecret");
			buildCode(shopReq.getJdShopId(), "京东店铺Id");
			buildCode(shopReq.getJdRebateRatio(), "京东返佣比例");
		}
		Byte tbGoods = shopReq.getTbGoods();
		if(tbGoods!=null&&tbGoods.equals(NumberConstantType.ONE)) {
			buildCode(shopReq.getTbAppKey(), "淘宝appKey");
			buildCode(shopReq.getTbAppSecret(), "淘宝appSecret");
			buildCode(shopReq.getTbShopId(), "淘宝店铺Id");
		}
		Byte jtGoods = shopReq.getJtGoods();
		if(jtGoods!=null&&jtGoods.equals(NumberConstantType.ONE)) {
			buildCode(shopReq.getJtAppKey(), "聚塔appKey");
		}
	}

	private void buildCode(Object key, String value) {
		if (StringUtils.isEmpty(key)) {
			throw new BusinessException(JsonResultCode.CODE_SOME_MUST, value);
		}
	}


	/**
	 * 获取下载文件的查询
	 * @param param
	 * @return
	 */
	public Workbook getShopListExport(ShopListQueryParam param, String lang) {
		SelectWhereStep<?> sql = getSql(param);
		List<ShopListFileVo> list = sql.fetchInto(ShopListFileVo.class);
		for (ShopListFileVo item : list) {
			Integer shopId = item.getShopId();
			//续费类型：1续费，2试用，3赠送，4退款
			item.setRenewMoney(this.renew.getShopRenewTotal(shopId));
			item.setExpireTime(this.renew.getShopRenewExpireTime(shopId));
			item.setRenewNum(renew.getRenewals(shopId));
			item.setFirstRenewTime(renew.firstRenewTime(shopId));
			item.setRefundMoney(renew.getTypeMoney(ShopRenewType.REFUND, shopId));
			item.setRefundNum(renew.getTypeNum(ShopRenewType.REFUND, shopId));
			item.setSendNum(renew.getTypeNum(ShopRenewType.GIFT, shopId));
			item.setTryNum(renew.getTypeNum(ShopRenewType.TRIAL, shopId));
			Integer provinceCode = item.getProvinceCode();
			StringBuilder builder=new StringBuilder();
			if(provinceCode!=0) {
				builder.append(item.getProvinceName());
				if(item.getCityCode()!=0) {
					builder.append(item.getCityName());
					if(item.getDistrictCode()!=0) {
						builder.append(item.getDistrictName());
					}
				}
			}
			item.setShopType(ShopTypeEnum.getName(item.getShopType()));
			item.setAddress(String.valueOf(builder));
			item.setIsEnableds(item.getIsEnabled().equals(ShopRenewType.RENEWAL)?"已禁止":"未禁止");
			item.setIsSubMerchants(SubMerchantEnum.getName(item.getIsSubMerchant()));
			item.setShopFlags(ShopFlagEnum.getName(item.getShopFlag()));
			item.setHidBottoms(item.getHidBottom().equals(ShopRenewType.RENEWAL)?"是":"否");
			item.setOpenAccountTypes(OpenAccountTypeEnum.getName(item.getOpenAccountType()));
		}
		return getModel(lang, list);
	}

	/**
	 * 返回Excel文件
	 *
	 * @param lang
	 * @param list
	 * @return
	 */
	public Workbook getModel(String lang, List<ShopListFileVo> list) {
		Workbook workbook = ExcelFactory.createWorkbook(ExcelTypeEnum.XLSX);
		ExcelWriter excelWriter = new ExcelWriter(lang, workbook);
		excelWriter.writeModelList(list, ShopListFileVo.class);
		return workbook;
	}
}
