package com.vpu.mp.service.saas.shop;

import static com.vpu.mp.db.main.Tables.SHOP;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.service.MainBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.store.account.StoreAccountVo;
import com.vpu.mp.service.pojo.shop.store.account.StoreInfoVo;
import com.vpu.mp.service.pojo.shop.store.account.StoreManageParam;
import com.vpu.mp.service.pojo.shop.store.authority.StoreAuthListPage;
import com.vpu.mp.service.pojo.shop.store.authority.StoreAuthListParam;
import com.vpu.mp.service.pojo.shop.store.authority.StoreAuthParam;
import com.vpu.mp.service.pojo.shop.store.authority.StoreAuthVo;
import com.vpu.mp.service.pojo.shop.store.authority.StoreCfgParam;
import com.vpu.mp.service.pojo.shop.store.authority.StoreConstant;
import com.vpu.mp.service.pojo.shop.store.authority.Sub;
import com.vpu.mp.service.pojo.shop.store.store.StoreBasicVo;
import com.vpu.mp.service.shop.store.store.StoreService;

/**
 * 门店权限的service
 * 
 * @author zhaojianqiang
 * @time 上午10:46:39
 */
@Service
public class StoreManageService extends MainBaseService {

	@Autowired
	public StoreAccountService storeAccountService;

	private static final String STOREJSON = "admin.storeTop.json";
	private static final String dot = ",";

	/**
	 * 获取店员权限的配置和选中状态
	 * 
	 * @param shopId
	 * @return
	 */
	public StoreAuthVo getstoreJson(Integer shopId) {
		String cfgInfoJson = db().select(SHOP.STORE_CLERK_PRIVILEGE_LIST).from(SHOP).where(SHOP.SHOP_ID.eq(shopId))
				.fetchOneInto(String.class);
		StoreCfgParam cfgInfo = null;
		if (!StringUtils.isEmpty(cfgInfoJson)) {
			cfgInfo = Util.parseJson(cfgInfoJson, StoreCfgParam.class);
		}
		String json = Util.loadResource(STOREJSON);
		List<StoreAuthParam> list = Util.parseJson(json, new TypeReference<List<StoreAuthParam>>() {
		});
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getIsOnly().equals(StoreConstant.ISONLY_TWO)) {
				list.remove(i--);
				continue;
			}
			List<Sub> sub = list.get(i).getSub();
			Byte hasSubCheck = StoreConstant.CHECK_ZERO;
			if (sub != null && sub.size() > 0) {
				for (int j = 0; j < sub.size(); j++) {
					if (sub.get(j).getIsOnly().equals(StoreConstant.ISONLY_TWO)) {
						sub.remove(j--);
						continue;
					}
					if (cfgInfo != null && cfgInfo.getSubMenuCfg().size() > 0
							&& inList(sub.get(j).getEnName(), cfgInfo.getSubMenuCfg())) {
						sub.get(j).setCheck(StoreConstant.CHECK_ONE);
						hasSubCheck = StoreConstant.CHECK_ONE;
					}
				}
			}
			// 门店概况一直是选中的
			list.get(0).setCheck(StoreConstant.CHECK_ONE);
			list.get(i).setCheck(hasSubCheck);

		}
		List<String> funcfg = cfgInfo == null ? new ArrayList<String>() : cfgInfo.getFunCfg();
		return new StoreAuthVo(list, funcfg);
	}

	private Boolean inList(String eName, List<String> list) {
		for (String string : list) {
			if (eName.equals(string)) {
				return true;
			}
		}
		return false;

	}

	private List<Integer> splitString(String stores) {
		List<Integer> list = new ArrayList<Integer>();
		if (stores != null) {
			if (stores.contains(dot)) {
				String[] split = stores.split(dot);
				for (String string : split) {
					list.add(Integer.valueOf(string));
				}
			} else {
				list.add(Integer.valueOf(stores));
			}
		}
		return list;

	}

	public boolean setting(StoreAuthListParam param, Integer shopId) {
		List<String> menuCfg = param.getMenuCfg();
		boolean flag = false;
		for (String string : menuCfg) {
			if (string.equals("store_overview")) {
				flag = true;
			}
		}
		if (!flag) {
			menuCfg.add("store_overview");
		}
		String json = Util.toJson(param);
		int execute = db().update(SHOP).set(SHOP.STORE_CLERK_PRIVILEGE_LIST, json).where(SHOP.SHOP_ID.eq(shopId))
				.execute();
		return execute == 1;
	}

	/**
	 * 门店用户列表
	 * 
	 * @param sysId
	 * @param shopId
	 * @param param
	 * @return
	 */
	public PageResult<StoreAccountVo> getAccountList(Integer shopId, StoreAuthListPage param) {
		ShopRecord shop = db().selectFrom(SHOP).where(SHOP.SHOP_ID.eq(shopId)).fetchAny();
		Integer currentPage = param.getCurrentPage();
		Integer pageRows = param.getPageRows();
		PageResult<StoreAccountVo> accountList = storeAccountService.accountList(currentPage, pageRows, shop.getSysId(),
				shopId);
		List<StoreAccountVo> dataList = accountList.getDataList();
		for (StoreAccountVo item : dataList) {
			String storeList = item.getStoreList();
			if (!StringUtils.isEmpty(storeList)) {
				Integer storeNum = saas.getShopApp(shopId).store.getStoreNum(splitString(storeList));
				item.setStoreNum(storeNum == null ? 0 : storeNum);
			} else {
				item.setStoreNum(0);
			}
		}
		return accountList;
	}

	/**
	 * 应用门店弹窗用
	 * 
	 * @param accountId
	 * @param shopId
	 * @return
	 */
	public StoreInfoVo getStoreInfo(Integer accountId, Integer shopId) {
		StoreAccountVo storeAccount = storeAccountService.getStoreInfoById(accountId);
		if (storeAccount == null) {
			return null;
		}
		StoreService storeService = saas.getShopApp(shopId).store;
		String storeList = storeAccount.getStoreList();
		List<Integer> storeIds = splitString(storeList);
		List<StoreBasicVo> haveStore = storeService.getStoreByIds(storeIds);
		List<StoreBasicVo> allStore = storeService.getAllStore();
		return new StoreInfoVo(haveStore, allStore);
	}



	public boolean config(StoreManageParam param) {
		logger().info("进入更新门店数量");
		boolean flag;
		String storeList = param.getStoreList();
		if (StringUtils.isEmpty(storeList) || Objects.equals("null", storeList)) {
			storeList = null;
		}
		int exect = storeAccountService.updateStoreList(param.getAccountId(), storeList);
		flag = exect > 0 ? true : false;
		return flag;
	}
	
	
	public int stop(Integer accountId) {
		//账户状态1:启用，0：禁用
		return  storeAccountService.updateStatus(accountId, StoreConstant.STOP);
	}

	
	public JsonResultCode start(Integer accountId) {
		StoreAccountVo storeAccountVo = storeAccountService.getStoreInfoById(accountId);
		if(StringUtils.isEmpty(storeAccountVo.getStoreList())) {
			//请设置有权限门店
			return JsonResultCode.CODE_STORE_NEED_HAVE; 
		}
		//账户状态1:启用，0：禁用
		int updateStatus = storeAccountService.updateStatus(accountId, StoreConstant.STATRT);
		return  updateStatus>0?JsonResultCode.CODE_SUCCESS:JsonResultCode.CODE_FAIL;
	}
	
	public Boolean check(String passwd) {
		if (StringUtils.isEmpty(passwd)) {
			return true;
		}
		String pattern = "^[^\\u4e00-\\u9fa5]{6,16}$";
		boolean isMatch = Pattern.matches(pattern, passwd);
		return isMatch;
	}
	
}
