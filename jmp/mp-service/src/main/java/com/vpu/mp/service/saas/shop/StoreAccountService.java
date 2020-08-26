package com.vpu.mp.service.saas.shop;

import static com.vpu.mp.db.main.Tables.SHOP;
import static com.vpu.mp.db.main.tables.StoreAccount.STORE_ACCOUNT;

import java.util.*;

import com.vpu.mp.dao.main.StoreAccountDao;
import com.vpu.mp.service.pojo.shop.auth.StoreAuthConstant;
import com.vpu.mp.service.pojo.shop.auth.StoreAuthInfoVo;
import com.vpu.mp.service.pojo.shop.auth.StoreLoginParam;
import jodd.util.StringUtil;
import org.jooq.SelectConditionStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.foundation.util.Util;
import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.db.main.tables.records.StoreAccountRecord;
import com.vpu.mp.service.foundation.service.MainBaseService;
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
	private static final Byte IS_DEL = 1;
	private static final Byte NO_DEL = 0;
	private static final String DOT = ",";
	@Autowired
	public StoreAccountDao storeAccountDao;

    /**
     * 获取用户列表
     * @param param
     * @param sysId
     * @param shopId
     * @return
     */
	public PageResult<StoreAccountVo> accountList(StoreAuthListPage param, Integer sysId, Integer shopId) {
		SelectConditionStep<StoreAccountRecord> where = db().selectFrom(STORE_ACCOUNT).where(STORE_ACCOUNT.DEL_FLAG
				.eq(NO_DEL).and(STORE_ACCOUNT.SYS_ID.eq(sysId).and(STORE_ACCOUNT.SHOP_ID.eq(shopId))));
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
		 StoreAccountVo fetchAnyInto = db().selectFrom(STORE_ACCOUNT).where(STORE_ACCOUNT.ACCOUNT_ID.eq(accountId))
				.fetchAnyInto(StoreAccountVo.class);
		 if(fetchAnyInto!=null) {
			 List<Integer> list = changeToArray(fetchAnyInto.getStoreList());
			 fetchAnyInto.setStoreLists(list);
			 fetchAnyInto.setStoreNum(list.size());
		 }
		 return fetchAnyInto;

	}

	public int updateStoreList(Integer accountId, String storeList) {
		return db().update(STORE_ACCOUNT).set(STORE_ACCOUNT.STORE_LIST, storeList)
				.where(STORE_ACCOUNT.ACCOUNT_ID.eq(accountId)).execute();
	}
	
	public int updateStoreList(Integer accountId, Integer[] storeLists) {
		String string = changeToString(storeLists);
		return db().update(STORE_ACCOUNT).set(STORE_ACCOUNT.STORE_LIST, string)
				.where(STORE_ACCOUNT.ACCOUNT_ID.eq(accountId)).execute();
	}

	/**
	 * 删除
	 * 
	 * @param accountId
	 * @return
	 */
	public int delStoreAccount(Integer accountId) {
		return db().update(STORE_ACCOUNT).set(STORE_ACCOUNT.DEL_FLAG, IS_DEL)
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
	
	protected List<Integer> changeToArray(String stores) {
		List<Integer> list = new ArrayList<Integer>();
		if (stores != null) {
			if (stores.contains(DOT)) {
				String[] split = stores.split(DOT);
				for (String string : split) {
					list.add(Integer.valueOf(string));						
				}
			} else {
				list.add(Integer.valueOf(stores));
			}
		}
		return list;
	}
	
	protected String changeToString(Integer[] storeList) {
		Set<Integer> set = new LinkedHashSet<Integer>();
		for (Integer integer : storeList) {
			set.add(integer);
		}
		StringBuilder builder=new StringBuilder();
		for (Object object : set) {
			builder.append(object);
			builder.append(",");
		}
		if(builder.length()>0) {
			return builder.deleteCharAt(builder.length()-1).toString();
		}
		return null;
	}

    /**
     * 新建
     *
     * @param param
     * @return
     */
    public int create(StoreAccountParam param, Integer shopId) {
        ShopRecord shop = db().selectFrom(SHOP).where(SHOP.SHOP_ID.eq(shopId)).fetchAny();
        StoreAccountRecord record = db().newRecord(STORE_ACCOUNT, param);
        record.setStoreList(changeToString(param.getStoreList()));
        record.setAccountPasswd(Util.md5(param.getAccountPasswd()));
        record.setSysId(shop.getSysId());
        record.setShopId(shopId);
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
		map.put(STORE_ACCOUNT.STORE_LIST, changeToString(param.getStoreList()));
		if (!StringUtils.isEmpty(param.getAccountPasswd())) {
			map.put(STORE_ACCOUNT.ACCOUNT_PASSWD, Util.md5(param.getAccountPasswd()));
		}
		int execute = db().update(STORE_ACCOUNT).set(map).where(STORE_ACCOUNT.ACCOUNT_ID.eq(param.getAccountId()))
				.execute();
		return execute;
	}

	public StoreAccountRecord findInfo(String accountName, String mobile, Integer accountId) {
		SelectConditionStep<StoreAccountRecord> where = db().selectFrom(STORE_ACCOUNT)
				.where(STORE_ACCOUNT.ACCOUNT_NAME.eq(accountName).or(STORE_ACCOUNT.MOBILE.eq(mobile)).and(STORE_ACCOUNT.DEL_FLAG.eq(NO_DEL)));
		if (accountId != null) {
			where.and(STORE_ACCOUNT.ACCOUNT_ID.ne(accountId));
		}
		StoreAccountRecord any = where.fetchAny();
		return any;
	}

	public StoreAuthInfoVo getStoreAccountFlag(StoreLoginParam param){
        StoreAuthInfoVo storeAuthInfoVo = new StoreAuthInfoVo();
        StoreAccountVo storeAccountInfo = storeAccountDao.getStoreAccountInfo(param);
        if (storeAccountInfo == null) {
            storeAuthInfoVo.setMsg(StoreAuthConstant.ACCOUNT_NOT_EXIST);
        } else if (StoreAuthConstant.DEL_NORMAL.equals(storeAccountInfo.getDelFlag())){
            storeAuthInfoVo.setMsg(StoreAuthConstant.ACCOUNT_IS_DELETE);
        } else if (StoreAuthConstant.IS_FORBIDDEN.equals(storeAccountInfo.getStatus())){
            storeAuthInfoVo.setMsg(StoreAuthConstant.ACCOUNT_IS_DELETE);
        } else if (StringUtil.isBlank(storeAccountInfo.getStoreList())){
            storeAuthInfoVo.setMsg(StoreAuthConstant.STORE_IS_EMPTY);
        } else {
            List<Integer> list = changeToArray(storeAccountInfo.getStoreList());
            storeAccountInfo.setStoreLists(list);
        }
        storeAuthInfoVo.setStoreAccountInfo(storeAccountInfo);
        return storeAuthInfoVo;
    }

    public StoreAuthInfoVo verifyStoreLogin(StoreLoginParam param){
        StoreAuthInfoVo storeAuthInfoVo = getStoreAccountFlag(param);
        if (!StoreAuthConstant.STORE_AUTH_OK.equals(storeAuthInfoVo.getIsOk())) {
            return storeAuthInfoVo;
        }
        if (!Util.md5(param.getPassword()).equals(storeAuthInfoVo.getStoreAccountInfo().getAccountPasswd())) {
            storeAuthInfoVo.setIsOk(false);
            storeAuthInfoVo.setMsg(StoreAuthConstant.ACCOUNT_PW_ERROR);
        }
        return storeAuthInfoVo;
    }
}
