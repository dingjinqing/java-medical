package com.vpu.mp.service.shop.decoration;

import static com.vpu.mp.db.shop.Tables.DECORATE_LINK;
import static com.vpu.mp.db.shop.Tables.MP_JUMP;
import static com.vpu.mp.db.shop.Tables.MP_JUMP_USABLE;
import static com.vpu.mp.db.shop.Tables.STORE;
import static com.vpu.mp.db.shop.tables.XcxCustomerPage.XCX_CUSTOMER_PAGE;

import java.util.List;

import org.jooq.Record3;
import org.jooq.SelectJoinStep;

import com.vpu.mp.db.shop.tables.records.DecorateLinkRecord;
import com.vpu.mp.db.shop.tables.records.XcxCustomerPageRecord;
import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.FieldsUtil;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.pojo.shop.decoration.StoreVo;
import com.vpu.mp.service.pojo.shop.decoration.XcxCustomerPageVo;
import com.vpu.mp.service.pojo.shop.decoration.XcxLinkListVo;
import com.vpu.mp.service.pojo.shop.decoration.XcxNameListVo;
import com.vpu.mp.service.pojo.shop.store.store.StoreListQueryParam;

/**
 * 
 * @author 常乐
 * 2019年7月9日
 */
public class ChooseLinkService extends BaseService {

//	常用链接
	public Boolean commonLink() {
		return false;
	}
	
//	商品链接
	public Boolean goodsLink() {
		return false;
	}
	
	/**
	 * 自定义页面
	 * @param page
	 * @return
	 */
	public PageResult<XcxCustomerPageVo> customPage(Integer page) {
		XcxCustomerPageVo xcx = new XcxCustomerPageVo();
		xcx.setPageId(page);
		PageResult<XcxCustomerPageVo> list = saas().getShopApp(shopId).mpDecoration.getPageList(xcx);
		return list;
	}
	
//	营销活动
	public Boolean activityList() {
		return false;
	}
	
//	商品分类
	public Boolean goodsCategory() {
		return false;
	}
	
	/**
	 * 创建跳转网页内容
	 * @param info
	 * @return
	 */
	public Boolean saveWebLink(XcxLinkListVo info) {
		DecorateLinkRecord record = db().newRecord(DECORATE_LINK,info);
		record.setShopId(shopId);
		record.setLinkAction((byte) 1);
		System.out.println(record);
//		//过滤空参数
//		DecorateLinkRecord record1 = new DecorateLinkRecord();
//		FieldsUtil.assignNotNull(record, record1);
		int res = db().executeInsert(record);
		if(res > 0) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * 网页跳转列表
	 * @return
	 */
	public List<XcxLinkListVo> getWebLink() {
		List<XcxLinkListVo> list = db().select().from(DECORATE_LINK)
				.where(DECORATE_LINK.LINK_ACTION.eq((byte) 1))
				.fetch().into(XcxLinkListVo.class);
		return list;
	}
	
//	表单页面
	public Boolean fromPage() {
		return false;
	}
	
	/**
	 * 门店
	 * @param page
	 * @return
	 */
	public PageResult<StoreVo> store(StoreListQueryParam param) {
		SelectJoinStep<Record3<String,Byte,Integer>> select = db()
				.select(STORE.STORE_NAME,STORE.BUSINESS_STATE,STORE.STORE_ID)
				.from(STORE);
		select.orderBy(STORE.STORE_ID.desc());
		PageResult<StoreVo> pageResult = this.getPageResult(select, param.getCurrentPage(), param.getPageRows(), StoreVo.class);
		return pageResult;
	}
	
	/**
	 * 获取小程序名称
	 * @return
	 */
	public List<XcxNameListVo> getXcxNameList() {
		List<XcxNameListVo> list = db().select(MP_JUMP.APP_NAME)
				.from(MP_JUMP .leftJoin(MP_JUMP_USABLE) .on(MP_JUMP.APP_ID.eq(MP_JUMP_USABLE.APP_ID)))
				.where(MP_JUMP.DEL_FLAG.eq((byte) 0))
				.fetch().into(XcxNameListVo.class);
		return list;
	}
	
	/**
	 * 获得装修跳转链接列表
	 * @return
	 */
	public  List<XcxLinkListVo> getXcxLinkList() {
		 List<XcxLinkListVo> linkList = db().select(DECORATE_LINK.APPID,DECORATE_LINK.TITLE,DECORATE_LINK.PATH_NAME,DECORATE_LINK.LINK_PATH)
				 .from(DECORATE_LINK)
				.where(DECORATE_LINK.DEL_FLAG.eq((byte) 0))
				.orderBy(DECORATE_LINK.ID.desc())
				.fetch().into(XcxLinkListVo.class);
		return linkList;
	}
	
	/**
	 * 保存小程序跳转链接
	 * @param info
	 * @return
	 */
	public Boolean saveXcxLink(XcxLinkListVo info) {
		XcxCustomerPageRecord record = db().newRecord(XCX_CUSTOMER_PAGE, info);
		int res = db().executeInsert(record);
		if(res > 0) {
			return true;
		}else {
			return false;
		}
	}
}
