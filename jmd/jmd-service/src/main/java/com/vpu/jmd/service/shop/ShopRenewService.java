package com.vpu.jmd.service.shop;


import cn.hutool.core.date.DateUtil;
import com.vpu.jmd.table.main.tables.ShopAccount;
import com.vpu.jmd.table.main.tables.ShopRenew;
import com.vpu.jmd.table.main.tables.records.ShopRecord;
import com.vpu.jmd.table.main.tables.records.ShopRenewRecord;
import com.vpu.jmd.service.base.MainBaseService;
import com.vpu.jmd.service.base.bo.PageResult;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.SelectWhereStep;
import org.jooq.impl.DSL;
import org.jooq.tools.Convert;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import static com.vpu.jmd.table.main.Tables.SHOP;
import static com.vpu.jmd.table.main.Tables.SHOP_ACCOUNT;
import static com.vpu.jmd.table.main.Tables.SHOP_RENEW;
import static com.vpu.jmd.table.main.Tables.SYSTEM_CHILD_ACCOUNT;


/**
 *
 * @author 新国
 *
 */
@Service

public class ShopRenewService extends MainBaseService {

	public void insertRenewDate() {

	}

	public Integer getShopNumber(Integer sysId) {
		return (Integer) db().select(DSL.count(SHOP_RENEW.SYS_ID)).from(SHOP_RENEW).where(SHOP_RENEW.SYS_ID.eq(sysId))
				.fetchAny(0);
	}

	public Result<Record> getRenewList(Integer sysId) {
		return db().select().from(SHOP_RENEW).where(SHOP_RENEW.SYS_ID.eq(sysId)).orderBy(SHOP_RENEW.EXPIRE_TIME.desc())
				.fetch();
	}

	public PageResult<ShopRenewVo> getShopRenewList(ShopRenewListParam sParam) {
		ShopRenew a = SHOP_RENEW.as("a");
		ShopAccount b = SHOP_ACCOUNT.as("b");
		SelectWhereStep<? extends Record> select = db().select(a.ID,a.SHOP_ID,a.SYS_ID,a.MOBILE,a.RENEW_MONEY,a.RENEW_DATE,a.EXPIRE_TIME,
				a.OPERATOR,a.RENEW_DESC,a.RENEW_TYPE,a.RENEW_DURATION,a.SEND_TYPE,a.SEND_CONTENT,b.ACCOUNT_NAME).from(a, b);
		select.where(a.SHOP_ID.eq(sParam.getShopId()).and(a.SYS_ID.eq(b.SYS_ID)));
		select.orderBy(a.ID.desc());

		PageResult<ShopRenewVo> pageResult = this.getPageResult(select, sParam.getCurrentPage(), sParam.getPageRows(),
				ShopRenewVo.class);
		for(ShopRenewVo sRenewVo:pageResult.dataList) {
			if(sRenewVo.getOperator().equals(0)) {
				sRenewVo.setOperatorName("system");
			} else {
				Record1<String> record1 = db().select(SYSTEM_CHILD_ACCOUNT.ACCOUNT_NAME).from(SYSTEM_CHILD_ACCOUNT)
						.where(SYSTEM_CHILD_ACCOUNT.ACCOUNT_ID.eq(sRenewVo.getOperator())).fetchAny();
				sRenewVo.setOperatorName(record1.getValue(SYSTEM_CHILD_ACCOUNT.ACCOUNT_NAME));
			}

		}
		return pageResult;
	}

	public BigDecimal getRenewTotal(Integer sysId) {
		Object total = db().select(DSL.sum(SHOP_RENEW.RENEW_MONEY)).from(SHOP_RENEW).where(SHOP_RENEW.SYS_ID.eq(sysId))
				.fetchAny(0);

		return total == null ? new BigDecimal("0") : Convert.convert(total, BigDecimal.class);
	}

	public BigDecimal getShopRenewTotal(Integer shopId) {
		Object total = db().select(DSL.sum(SHOP_RENEW.RENEW_MONEY)).from(SHOP_RENEW)
				.where(SHOP_RENEW.SHOP_ID.eq(shopId)).fetchAny(0);

		return total == null ? new BigDecimal(0) : Convert.convert(total, BigDecimal.class);
	}

	public Timestamp getShopRenewExpireTime(Integer shopId) {
		return  db().selectFrom(SHOP).where(SHOP.SHOP_ID.eq(shopId)).fetchAny(SHOP.EXPIRE_TIME);
//		return db().select().from(SHOP_RENEW).where(SHOP_RENEW.SHOP_ID.eq(shopId))
//				.orderBy(SHOP_RENEW.EXPIRE_TIME.desc()).fetchAny(SHOP_RENEW.EXPIRE_TIME);
	}

	public int insertShopRenew(ShopRenewReq sReq, SystemTokenAuthInfo info) {
		ShopRenewRecord sRecord=db().newRecord(SHOP_RENEW,sReq);
		if(sReq.getRenewType().equals((byte)4)){
			logger().info("退款{}",sReq.getRenewMoney());
			//改为负数
			sRecord.setRenewMoney(sReq.getRenewMoney().negate());
		}
		sRecord.setRenewDuration(sReq.getYear()+","+sReq.getMonth());
		sRecord.setSendContent(sReq.getSendYear()+","+sReq.getSendMonth());
		sRecord.setRenewDate(DateUtil.date().toTimestamp());
		if(info.isSubLogin()) {
			//子账户登录
			sRecord.setOperator(info.getSubAccountId());
		}else {
			sRecord.setOperator(0);
		}
		Integer shopId = sReq.getShopId();
		ShopRecord record = db().selectFrom(SHOP).where(SHOP.SHOP_ID.eq(shopId)).fetchAny();
		Timestamp funExpireTime = record.getFunExpireTime();
		if(null!=funExpireTime&&funExpireTime.before(sReq.getExpireTime())) {
			funExpireTime=sReq.getExpireTime();
			record.setFunExpireTime(funExpireTime);
		}
		 this.transaction(()->{
			 int execute = db().update(SHOP).set(SHOP.EXPIRE_TIME,sReq.getExpireTime()).set(SHOP.FUN_EXPIRE_TIME,record.getFunExpireTime()).where(SHOP.SHOP_ID.eq(shopId)).execute();
			 logger().info("店铺{}续费插入主库：{}",shopId,execute);
			 int executeInsert = db().executeInsert(sRecord);
			 logger().info("店铺{}续费插入从库：{}",shopId,executeInsert);
		 });
		return 1;
	}

	/**
	 * 续费次数
	 * @param shopId
	 * @return
	 */
	public Integer getRenewals(Integer shopId) {
		String value="1,5,6,7";
		String[] split = value.split(",");
		List<String> list = Arrays.asList(split);
		Integer into = db().select(DSL.count()).from(SHOP_RENEW).where(SHOP_RENEW.SHOP_ID.eq(shopId).and(SHOP_RENEW.RENEW_TYPE.in(list))).fetchAnyInto(Integer.class);
		if(into==null) {
			return 0;
		}
		return into;
	}

	/**
	 * 首次续费日期
	 * @param shopId
	 * @return
	 */
	public Timestamp firstRenewTime(Integer shopId) {
		BigDecimal bigDecimal = new BigDecimal("0");
		return db().select(SHOP_RENEW.RENEW_DATE).from(SHOP_RENEW)
				.where(SHOP_RENEW.SHOP_ID.eq(shopId).and(SHOP_RENEW.RENEW_MONEY.gt(bigDecimal)))
				.orderBy(SHOP_RENEW.ID.asc()).fetchAnyInto(Timestamp.class);
	}

	/**
	 * 根据类型统计总金额
	 * @param type
	 * @param shopId
	 * @return
	 */
	public BigDecimal getTypeMoney(Byte type, Integer shopId) {
		BigDecimal total = db().select(DSL.sum(SHOP_RENEW.RENEW_MONEY)).from(SHOP_RENEW)
				.where(SHOP_RENEW.SHOP_ID.eq(shopId).and(SHOP_RENEW.RENEW_TYPE.eq(type)))
				.fetchAnyInto(BigDecimal.class);

		return total == null ? new BigDecimal("0") : total.abs();
	}

	/**
	 * 根据类型统计次数
	 * @param type
	 * @param shopId
	 * @return
	 */
	public Integer getTypeNum(Byte type, Integer shopId) {
		Integer num = db().select(DSL.count()).from(SHOP_RENEW)
				.where(SHOP_RENEW.SHOP_ID.eq(shopId).and(SHOP_RENEW.RENEW_TYPE.eq(type))).fetchAnyInto(Integer.class);
		return num == null ? 0 : num;
	}
}
