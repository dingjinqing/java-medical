package com.vpu.mp.service.saas.shop;

import static com.vpu.mp.db.main.tables.StoreAccount.STORE_ACCOUNT;

import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.apache.commons.collections.map.HashedMap;
import org.jooq.SelectConditionStep;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.vpu.mp.db.main.tables.records.StoreAccountRecord;
import com.vpu.mp.service.foundation.service.MainBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.store.account.StoreAccountEditParam;
import com.vpu.mp.service.pojo.shop.store.account.StoreAccountParam;
import com.vpu.mp.service.pojo.shop.store.account.StoreAccountVo;
import com.vpu.mp.service.pojo.shop.store.authority.StoreAuthListPage;

/**
 * 
 * @author zhaojianqiang
 * @time 下午4:58:30
 */
@Service
public class StoreAccountService extends MainBaseService {
	private static final Byte ISDEL = 1;
	private static final Byte NODEL = 0;

	/**
	 * 获取用户列表
	 * 
	 * @param currentPage
	 * @param pageRows
	 * @param sysId
	 * @param shopId
	 * @return
	 */
	public PageResult<StoreAccountVo> accountList(StoreAuthListPage param, Integer sysId, Integer shopId) {
		SelectConditionStep<StoreAccountRecord> where = db().selectFrom(STORE_ACCOUNT).where(STORE_ACCOUNT.DEL_FLAG
				.eq(NODEL).and(STORE_ACCOUNT.SYS_ID.eq(sysId).and(STORE_ACCOUNT.SHOP_ID.eq(shopId))));
		if (!StringUtils.isEmpty(param.getAccountName())) {
			where.and(STORE_ACCOUNT.ACCOUNT_NAME.like(likeValue(param.getAccountName())));
		}
		if (!StringUtils.isEmpty(param.getMobile())) {
			where.and(STORE_ACCOUNT.MOBILE.like(likeValue(param.getMobile())));
		}
		if (param.getAccountType() > 0) {
			where.and(STORE_ACCOUNT.ACCOUNT_TYPE.eq(param.getAccountType()));
		}
		if (param.getStatus() > -1) {
			where.and(STORE_ACCOUNT.STATUS.eq(param.getStatus()));
		}
		return this.getPageResult(where, param.getCurrentPage(), param.getPageRows(), StoreAccountVo.class);
	}

	/**
	 * 根据id获取
	 * 
	 * @param accountId
	 * @return
	 */
	public StoreAccountVo getStoreInfoById(Integer accountId) {
		return db().selectFrom(STORE_ACCOUNT).where(STORE_ACCOUNT.ACCOUNT_ID.eq(accountId))
				.fetchAnyInto(StoreAccountVo.class);
	}

	public int updateStoreList(Integer accountId, String storeList) {
		return db().update(STORE_ACCOUNT).set(STORE_ACCOUNT.STORE_LIST, storeList)
				.where(STORE_ACCOUNT.ACCOUNT_ID.eq(accountId)).execute();
	}

	/**
	 * 删除
	 * 
	 * @param accountId
	 * @return
	 */
	public int delStoreAccount(Integer accountId) {
		return db().update(STORE_ACCOUNT).set(STORE_ACCOUNT.DEL_FLAG, ISDEL)
				.where(STORE_ACCOUNT.ACCOUNT_ID.eq(accountId)).execute();
	}

	/**
	 * 更新状态
	 * 
	 * @param accountId
	 * @param status
	 * @return
	 */
	public int updateStatus(Integer accountId, Byte status) {
		return db().update(STORE_ACCOUNT).set(STORE_ACCOUNT.STATUS, status)
				.where(STORE_ACCOUNT.ACCOUNT_ID.eq(accountId)).execute();
	}

	/**
	 * 新建
	 * 
	 * @param param
	 * @return
	 */
	public int create(StoreAccountParam param) {
		StoreAccountRecord record = db().newRecord(STORE_ACCOUNT, param);
		record.setAccountPasswd(Util.md5(param.getAccountPasswd()));
		int insert = record.insert();
		return insert;
	}

	/**
	 * 编辑
	 * 
	 * @param param
	 * @return
	 */
	public int edit(StoreAccountEditParam param) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put(STORE_ACCOUNT.MOBILE, param.getMobile());
		map.put(STORE_ACCOUNT.ACCOUNT_NAME, param.getAccountName());
		map.put(STORE_ACCOUNT.ACCOUNT_TYPE, param.getAccountType());
		map.put(STORE_ACCOUNT.STORE_LIST, param.getStoreList());
		if (!StringUtils.isEmpty(param.getAccountPasswd())) {
			map.put(STORE_ACCOUNT.ACCOUNT_PASSWD, Util.md5(param.getAccountPasswd()));
		}
		int execute = db().update(STORE_ACCOUNT).set(map).where(STORE_ACCOUNT.ACCOUNT_ID.eq(param.getAccountId()))
				.execute();
		return execute;
	}

	public StoreAccountRecord findInfo(String accountName, String mobile, Integer accountId) {
		SelectConditionStep<StoreAccountRecord> where = db().selectFrom(STORE_ACCOUNT)
				.where(STORE_ACCOUNT.ACCOUNT_NAME.eq(accountName).and(STORE_ACCOUNT.MOBILE.eq(mobile)));
		if (accountId != null) {
			where.and(STORE_ACCOUNT.ACCOUNT_ID.eq(accountId));
		}
		StoreAccountRecord any = where.fetchAny();
		return any;
	}
}
