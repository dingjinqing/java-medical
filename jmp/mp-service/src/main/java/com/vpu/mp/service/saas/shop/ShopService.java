package com.vpu.mp.service.saas.shop;

import static com.vpu.mp.db.main.tables.MpAuthShop.MP_AUTH_SHOP;
import static com.vpu.mp.db.main.tables.Shop.SHOP;
import static com.vpu.mp.db.main.tables.ShopAccount.SHOP_ACCOUNT;
import static com.vpu.mp.db.main.tables.ShopChildRole.SHOP_CHILD_ROLE;
import static com.vpu.mp.db.main.tables.ShopRenew.SHOP_RENEW;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Record9;
import org.jooq.Result;
import org.jooq.SelectWhereStep;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.main.tables.records.ShopOperationRecord;
import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.db.main.tables.records.UserLoginRecordRecord;
import com.vpu.mp.service.foundation.database.DatasourceManager;
import com.vpu.mp.service.foundation.database.DbConfig;
import com.vpu.mp.service.foundation.service.MainBaseService;
import com.vpu.mp.service.foundation.util.FieldsUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.saas.auth.SystemTokenAuthInfo;
import com.vpu.mp.service.pojo.saas.shop.ShopConst;
import com.vpu.mp.service.pojo.saas.shop.ShopListQueryParam;
import com.vpu.mp.service.pojo.saas.shop.ShopListQueryResultVo;
import com.vpu.mp.service.pojo.saas.shop.ShopPojo;
import com.vpu.mp.service.pojo.saas.shop.version.VersionConfig;
import com.vpu.mp.service.pojo.saas.shop.version.VersionMainConfig;
import com.vpu.mp.service.pojo.shop.auth.AdminTokenAuthInfo;
import com.vpu.mp.service.pojo.shop.auth.ShopReq;
import com.vpu.mp.service.pojo.shop.auth.ShopSelectInnerResp;

/**
 * 
 * @author 新国
 *
 */
@Service
public class ShopService extends MainBaseService {

	@Autowired
	protected DatasourceManager datasourceManager;
	@Autowired
	public ShopAccountService account;
	@Autowired
	public ShopImageManageService image;
	@Autowired
	public ShopRenewService renew;
	@Autowired
	public ShopVersionService version;
	@Autowired
	public ShopChildAccountService subAccount;
	@Autowired
	public ShopRoleService role;
	@Autowired
	public ShopMenuService menu;
	@Autowired
	public MpDecorationService decoration;
	@Autowired
	public MpAuthShopService mp;
	
	@Autowired
	public MpVersionService mpVersion;
	
	@Autowired
	public MpOperateLogService mpOperateLog;
	
	
	public PageResult<ShopListQueryResultVo> getPageList(ShopListQueryParam param) {
		SelectWhereStep<Record> select = db()
				.select(SHOP.asterisk(), MP_AUTH_SHOP.APP_ID, MP_AUTH_SHOP.IS_AUTH_OK, MP_AUTH_SHOP.NICK_NAME,
						MP_AUTH_SHOP.PRINCIPAL_NAME)
				.from(SHOP).join(SHOP_ACCOUNT).on(SHOP.SYS_ID.eq(SHOP_ACCOUNT.SYS_ID)).leftJoin(MP_AUTH_SHOP)
				.on(SHOP.SHOP_ID.eq(DSL.cast(MP_AUTH_SHOP.SHOP_ID, Integer.class)));
		select = this.buildOptions(select, param);
		select.orderBy(SHOP.CREATED.desc());
		PageResult<ShopListQueryResultVo> result = account.getPageResult(select, param.currentPage, param.pageRows,
				ShopListQueryResultVo.class);
		for (ShopListQueryResultVo shopList : result.dataList) {
			shopList.setRenewMoney(this.renew.getShopRenewTotal(shopList.getShopId()));
			shopList.setExpireTime(this.renew.getShopRenewExpireTime(shopList.getShopId()));

		}
		return result;
	}

	public SelectWhereStep<Record> buildOptions(SelectWhereStep<Record> select, ShopListQueryParam param) {
		if (param == null) {
			return select;
		}
		if (!StringUtils.isEmpty(param.keywords)) {
			Integer shopId = Util.convert(param.keywords, Integer.class, 0);
			String keywords = likeValue(param.keywords);
			select.where(SHOP.SHOP_NAME.like(keywords).or(SHOP.MOBILE.like(keywords))
					.or(MP_AUTH_SHOP.NICK_NAME.like(keywords)).or(SHOP.SHOP_ID.eq(shopId)));
		}

		Integer shopUsingStatus = 1;
		Integer shopExpiredStatus = 2;
		if (param.isUse != null && param.isUse.equals(shopUsingStatus)) {
			// 店铺在使用中
			select.where(SHOP.SHOP_ID.in(db().selectDistinct(SHOP_RENEW.SHOP_ID).from(SHOP_RENEW).where(
					SHOP_RENEW.SHOP_ID.eq(SHOP.SHOP_ID).and(SHOP_RENEW.EXPIRE_TIME.ge(DSL.currentTimestamp())))));
		}

		if (param.isUse != null && param.isUse.equals(shopExpiredStatus)) {
			// 店铺已过期
			select.where(SHOP.SHOP_ID.in(db().selectDistinct(SHOP_RENEW.SHOP_ID).from(SHOP_RENEW)
					.where(SHOP_RENEW.SHOP_ID.eq(SHOP.SHOP_ID))
					.and(SHOP_RENEW.EXPIRE_TIME.lt(DSL.currentTimestamp()).or(SHOP_RENEW.EXPIRE_TIME.isNull()))));
		}

		if (!StringUtils.isEmpty(param.shopType)) {
			select.where(SHOP.SHOP_TYPE.eq(param.shopType));
		}

		if (StringUtils.isEmpty(param.shopType) && !StringUtils.isEmpty(param.shopTypes)) {
			// 区分体验版和付费版
			if (param.shopTypes.equals(ShopConst.shopTypes.TRIAL_VERSION)) {
				// 体验版
				select.where(SHOP.SHOP_TYPE.eq(ShopConst.shopType.v1));
			}
			if (param.shopTypes.equals(ShopConst.shopTypes.PAID_VERSION)) {
				// 付费版
				select.where(SHOP.SHOP_TYPE.in(ShopConst.shopType.v2, ShopConst.shopType.v3, ShopConst.shopType.v4));
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
		return select;
	}

	/**
	 * TODO 加个事务
	 * 
	 * @param shopReq
	 * @return
	 */
	public Boolean addShop(ShopReq shopReq, SystemTokenAuthInfo user, HttpServletRequest request) {
		shopReq.setShopId(getCanUseShopId());
		DbConfig dbConfig = datasourceManager.getInstallShopDbConfig(shopReq.getShopId());
		if(!StringUtils.isEmpty(shopReq.getDbConfigId())) {
			//TODO 加插入传来的数据库信息
		}
		shopReq.setDbConfig(Util.toJson(dbConfig));
		if (!databaseManager.installShopDb(dbConfig)) {
			return false;
		}
		logger().info("数据库创建成功");
		ShopRecord record = new ShopRecord();
		FieldsUtil.assignNotNull(shopReq, record);
		if (db().executeInsert(record) != 1) {
			return false;
		}
		logger().info("插入数据成功");

		// 更新version_config
		if (updateConfig(shopReq) == 1) {
			logger().info("更新version_config成功");			
		}
		// 更新记录表
		if (updateOperation(record, user, request) == 1) {			
			logger().info("更新ShopOperation记录表成功");
		}
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
			if (shop != null && shop.getSysId() == sysId) {
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
	public Result<Record9<Integer, Integer, String, String, Timestamp, Byte, Byte, Byte, String>> getRoleShopList(
			Integer sysId, Integer subAccountId) {
		SelectWhereStep<Record9<Integer, Integer, String, String, Timestamp, Byte, Byte, Byte, String>> select = db()
				.selectDistinct(SHOP.SHOP_ID, SHOP.SYS_ID, SHOP.SHOP_NAME, SHOP.SHOP_AVATAR, SHOP.CREATED, SHOP.STATE,
						SHOP.BUSINESS_STATE, SHOP.IS_ENABLED, SHOP.SHOP_TYPE)
				.from(SHOP).leftJoin(SHOP_CHILD_ROLE).on(SHOP.SHOP_ID.eq(SHOP_CHILD_ROLE.SHOP_ID));
		select.where(SHOP.SYS_ID.eq(sysId));
		if (subAccountId > 0) {
			select.where(SHOP_CHILD_ROLE.ACCOUNT_ID.eq(subAccountId));
		}
		return select.orderBy(SHOP.CREATED.asc()).fetch();
	}

	public ShopPojo getShopBaseInfoById(Integer shopId) {
		return db().select(SHOP.SHOP_AVATAR, SHOP.SHOP_NAME, SHOP.BUSINESS_STATE, SHOP.CREATED, SHOP.BUSINESS_STATE)
				.from(SHOP).where(SHOP.SHOP_ID.eq(shopId)).fetchOne().into(ShopPojo.class);
	}

	public Integer updateShopBaseInfo(ShopPojo shop) {
		return db().update(SHOP).set(SHOP.SHOP_NAME, shop.getShopName()).set(SHOP.SHOP_AVATAR, shop.getShopAvatar())
				.set(SHOP.BUSINESS_STATE, shop.getBusinessState()).where(SHOP.SHOP_ID.eq(shop.getShopId())).execute();
	}

	public List<ShopSelectInnerResp> getShopList(AdminTokenAuthInfo info,
			List<Record9<Integer, Integer, String, String, Timestamp, Byte, Byte, Byte, String>> shopList) {
		List<ShopSelectInnerResp> dataList = new ArrayList<>(shopList.size());
		for (Record9<Integer, Integer, String, String, Timestamp, Byte, Byte, Byte, String> record : shopList) {
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
			shopInner.setShopAvatar(record.get(SHOP.SHOP_AVATAR));
			shopInner.setCreated(record.get(SHOP.CREATED));
			shopInner.setState(record.get(SHOP.STATE));
			shopInner.setBusinessState(record.get(SHOP.BUSINESS_STATE));
			shopInner.setIsEnabled(record.get(SHOP.IS_ENABLED));
			shopInner.setShopType(record.get(SHOP.SHOP_TYPE));
			shopInner.setExpireTime(expireTime);
			shopInner.setExpireTimeStatus(expireStatus);
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

	public Integer updateOperation(ShopRecord shopRecord, SystemTokenAuthInfo user, HttpServletRequest request) {
		ShopOperationRecord sRecord = new ShopOperationRecord();
		sRecord.setShopId(shopRecord.getShopId());
		if (user.isSubLogin()) {
			sRecord.setOperatorId(user.subAccountId);
			sRecord.setOperator(user.getSubUserName());
		}
		sRecord.setOperator(user.getUserName());
		sRecord.setOperatorId(user.getSystemUserId());
		sRecord.setDesc(diffEdit(shopRecord, new ShopRecord()));
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
	 * @param newShop
	 * @param oldShop
	 * @return
	 */
	public String diffEdit(ShopRecord newShop, ShopRecord oldShop) {
		StringBuffer sbf = new StringBuffer("新建或者更新");
		if (!newShop.getMobile().equals(oldShop.getMobile())) {
			sbf.append("电话:" + newShop.getMobile() + ",");			
		}
		if (!newShop.getShopName().equals(oldShop.getShopName())) {
			sbf.append("店铺名称:" + newShop.getShopName() + ",");			
		}
		if (!newShop.getShopPhone().equals(oldShop.getShopPhone())) {
			sbf.append("店铺客服电话:" + newShop.getShopPhone() + ",");			
		}
		if (!newShop.getShopNotice().equals(oldShop.getShopNotice())) {
			sbf.append("店铺公告:" + newShop.getShopNotice() + ",");			
		}
		if (!newShop.getShopWx().equals(oldShop.getShopWx())) {
			sbf.append("店铺微信:" + newShop.getShopWx() + ",");			
		}
		if (!newShop.getShopEmail().equals(oldShop.getShopEmail())) {
			sbf.append("店铺邮箱:" + newShop.getShopEmail() + ",");			
		}
		if (!newShop.getIsEnabled().equals(oldShop.getIsEnabled())) {
			sbf.append("店铺禁用:" + newShop.getIsEnabled() + ",");			
		}
		if (!newShop.getShopQq().equals(oldShop.getShopQq())) {
			sbf.append("店铺客服QQ:" + newShop.getShopQq() + ",");			
		}
		if (!newShop.getShopType().equals(oldShop.getShopType())) {
			sbf.append("店铺类型:" + version.getVersionNameByLevel(newShop.getShopType()));			
		}
		return sbf.toString();

	}
	public ShopRecord checkShop(Integer shopId,Integer sysId) {
		 return db().selectFrom(SHOP).where(SHOP.SHOP_ID.eq(shopId).and(SHOP.SYS_ID.eq(sysId))).fetchOne();
	}

}
