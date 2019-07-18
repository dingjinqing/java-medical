package com.vpu.mp.service.shop.decoration;

import static com.vpu.mp.db.main.Tables.CATEGORY;
import static com.vpu.mp.db.shop.Tables.ASSESS_ACTIVITY;
import static com.vpu.mp.db.shop.Tables.COUPON_PACK;
import static com.vpu.mp.db.shop.Tables.DECORATE_LINK;
import static com.vpu.mp.db.shop.Tables.FRIEND_PROMOTE_ACTIVITY;
import static com.vpu.mp.db.shop.Tables.GOODS;
import static com.vpu.mp.db.shop.Tables.GOODS_LABEL;
import static com.vpu.mp.db.shop.Tables.GROUP_DRAW;
import static com.vpu.mp.db.shop.Tables.LOTTERY;
import static com.vpu.mp.db.shop.Tables.MEMBER_CARD;
import static com.vpu.mp.db.shop.Tables.MP_JUMP;
import static com.vpu.mp.db.shop.Tables.MP_JUMP_USABLE;
import static com.vpu.mp.db.shop.Tables.MRKING_STRATEGY;
import static com.vpu.mp.db.shop.Tables.MRKING_VOUCHER;
import static com.vpu.mp.db.shop.Tables.PACKAGE_SALE;
import static com.vpu.mp.db.shop.Tables.PIN_INTEGRATION_DEFINE;
import static com.vpu.mp.db.shop.Tables.PURCHASE_PRICE_DEFINE;
import static com.vpu.mp.db.shop.Tables.SORT;
import static com.vpu.mp.db.shop.Tables.STORE;
import static com.vpu.mp.db.shop.tables.XcxCustomerPage.XCX_CUSTOMER_PAGE;

import java.sql.Timestamp;
import java.util.List;

import org.jooq.Record3;
import org.jooq.Record4;
import org.jooq.SelectJoinStep;

import com.vpu.mp.db.shop.tables.records.DecorateLinkRecord;
import com.vpu.mp.db.shop.tables.records.XcxCustomerPageRecord;
import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.DelFlag;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.pojo.saas.category.SysCatevo;
import com.vpu.mp.service.pojo.shop.decoration.ActivityVo;
import com.vpu.mp.service.pojo.shop.decoration.ChooseLinkParam;
import com.vpu.mp.service.pojo.shop.decoration.GoodsLinkVo;
import com.vpu.mp.service.pojo.shop.decoration.StoreVo;
import com.vpu.mp.service.pojo.shop.decoration.XcxCustomerPageVo;
import com.vpu.mp.service.pojo.shop.decoration.XcxLinkListVo;
import com.vpu.mp.service.pojo.shop.decoration.XcxNameListVo;
import com.vpu.mp.service.pojo.shop.goods.label.GoodsLabelVo;
import com.vpu.mp.service.pojo.shop.sort.SortVo;
import com.vpu.mp.service.pojo.shop.store.store.StoreListQueryParam;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * 
 * @author 常乐
 * 2019年7月9日
 */
@Service
@Scope("prototype")
public class ChooseLinkService extends BaseService {
//	常用链接
	public Boolean commonLink() {
		return false;
	}
	
	/**
	 * 商品链接列表
	 * @param param
	 * @return
	 */
	public PageResult<GoodsLinkVo> getGoodsLink(GoodsLinkVo param) {
		SelectJoinStep<Record4<Integer, String, String, String>> select = db()
				.select(GOODS.GOODS_ID,GOODS.GOODS_NAME,GOODS.GOODS_SN,GOODS.GOODS_IMG)
				.from(GOODS);
		select = buildOptions(select, param);
		select.orderBy(GOODS.GOODS_ID.desc());
		return this.getPageResult(select, GoodsLinkVo.page,GoodsLinkVo.class);
	}
	
	/**
	 * 商品链接条件查询
	 * @param select
	 * @param param
	 * @return
	 */
	public SelectJoinStep<Record4<Integer, String, String, String>> buildOptions(SelectJoinStep<Record4<Integer, String, String, String>> select, GoodsLinkVo param) {
		if(param.getGoodsName() != null) {
			select.where(GOODS.GOODS_NAME.contains(param.getKeyWords()).or(GOODS.GOODS_SN.contains(param.getKeyWords())));
		}
		return select;
	}
		
	/**
	 * 自定义页面
	 * @param param
	 * @return
	 */
	public PageResult<XcxCustomerPageVo> customPage(ChooseLinkParam param) {
		XcxCustomerPageVo xcx = new XcxCustomerPageVo();
		xcx.setPageId(param.getCurrentPage());
		xcx.setPageName(param.getPageName());
		PageResult<XcxCustomerPageVo> list = saas().getShopApp(shopId).mpDecoration.getPageList(xcx);
		return list;
	}
	
	/**
	 * 拼团抽奖链接
	 * @return
	 */
	public List<ActivityVo> getGroupDrawList() {
		List<ActivityVo> list = db().select(GROUP_DRAW.ID,GROUP_DRAW.NAME.as("actName"),GROUP_DRAW.START_TIME,GROUP_DRAW.END_TIME)
				.from(GROUP_DRAW)
				.where(GROUP_DRAW.END_TIME.ge(new Timestamp(System.currentTimeMillis())))
				.and(GROUP_DRAW.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
				.fetch().into(ActivityVo.class);
		return list;
	}
	
	/**
	 * 瓜分积分链接
	 * @return
	 */
	public List<ActivityVo> getIntegrationList() {
		List<ActivityVo> list = db().select(PIN_INTEGRATION_DEFINE.ID,PIN_INTEGRATION_DEFINE.NAME.as("actName"),PIN_INTEGRATION_DEFINE.START_TIME,PIN_INTEGRATION_DEFINE.END_TIME)
				.from(PIN_INTEGRATION_DEFINE)
				.where(PIN_INTEGRATION_DEFINE.END_TIME.ge(new Timestamp(System.currentTimeMillis())))
				.fetch().into(ActivityVo.class);
		return list;
	}
	
	/**
	 * 好友助力链接
	 * @return
	 */
	public List<ActivityVo> getPromoteList() {
		List<ActivityVo> list = db().select(FRIEND_PROMOTE_ACTIVITY.ID,FRIEND_PROMOTE_ACTIVITY.ACT_NAME,FRIEND_PROMOTE_ACTIVITY.START_TIME,FRIEND_PROMOTE_ACTIVITY.END_TIME)
				.from(FRIEND_PROMOTE_ACTIVITY)
				.where(FRIEND_PROMOTE_ACTIVITY.END_TIME.ge(new Timestamp(System.currentTimeMillis())))
				.and(FRIEND_PROMOTE_ACTIVITY.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
				.fetch().into(ActivityVo.class);
		return list;
	}
	
	/**
	 * 加价购活动链接
	 * @return
	 */
	public List<ActivityVo> getPriceList() {
		List<ActivityVo> list = db().select(PURCHASE_PRICE_DEFINE.NAME.as("actName"),PURCHASE_PRICE_DEFINE.END_TIME,PURCHASE_PRICE_DEFINE.START_TIME)
				.from(PURCHASE_PRICE_DEFINE)
				.where(PURCHASE_PRICE_DEFINE.END_TIME.ge(new Timestamp(System.currentTimeMillis())))
				.and(PURCHASE_PRICE_DEFINE.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
				.and(PURCHASE_PRICE_DEFINE.STATUS.eq((byte) 1))
				.fetch().into(ActivityVo.class);
		return list;
	}
	
	/**
	 * 幸运大抽奖
	 * @return
	 */
	public List<ActivityVo> getLotteryList() {
		 List<ActivityVo> list = db().select(LOTTERY.LOTTERY_NAME.as("actName"),LOTTERY.START_TIME,LOTTERY.END_TIME,LOTTERY.ID)
				 .from(LOTTERY)
				.where(LOTTERY.END_TIME.ge(new Timestamp(System.currentTimeMillis())))
				.and(LOTTERY.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
				.and(LOTTERY.STATUS.eq((byte) 0))
				.fetch().into(ActivityVo.class);
		return list;
	}
	
	/**
	 * 优惠券链接
	 * @return
	 */
	public List<ActivityVo> getVoucherList() {
		 List<ActivityVo>list = db().select(MRKING_VOUCHER.ID,MRKING_VOUCHER.ACT_NAME,MRKING_VOUCHER.START_TIME,MRKING_VOUCHER.END_TIME).from(MRKING_VOUCHER)
				.where(MRKING_VOUCHER.END_TIME.ge(new Timestamp(System.currentTimeMillis())))
				.and(MRKING_VOUCHER.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
				.fetch().into(ActivityVo.class);
		return list;
	}
	
	/**
	 * 会员卡链接
	 * @return
	 */
	public List<ActivityVo> getCardList() {
		 List<ActivityVo> list = db().select(MEMBER_CARD.ID,MEMBER_CARD.CARD_NAME,MEMBER_CARD.START_TIME,MEMBER_CARD.END_TIME)
				.from(MEMBER_CARD)
				.where(MEMBER_CARD.END_TIME.ge(new Timestamp(System.currentTimeMillis())))
				.and(MEMBER_CARD.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
				.fetch().into(ActivityVo.class);
		return list;
	}
	
	/**
	 * 一口价链接
	 * @return
	 */
	public List<ActivityVo> getPackageList() {
		 List<ActivityVo> list = db().select(PACKAGE_SALE.ID,PACKAGE_SALE.PACKAGE_NAME,PACKAGE_SALE.START_TIME,PACKAGE_SALE.END_TIME)
				.from(PACKAGE_SALE)
				.where(PACKAGE_SALE.END_TIME.ge(new Timestamp(System.currentTimeMillis())))
				.and(PACKAGE_SALE.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
				.fetch().into(ActivityVo.class);
		return list;
	}
	
	/**
	 * 满折满减活动链接
	 * @return
	 */
	public List<ActivityVo> getMrkingList() {
		List<ActivityVo>list = db().select(MRKING_STRATEGY.ID,MRKING_STRATEGY.ACT_NAME,MRKING_STRATEGY.START_TIME,MRKING_STRATEGY.END_TIME)
				.from(MRKING_STRATEGY)
				.where(MRKING_STRATEGY.END_TIME.ge(new Timestamp(System.currentTimeMillis())))
				.and(MRKING_STRATEGY.DEL_FLAG.eq((int) 0))
				.fetch().into(ActivityVo.class);
		return list;
	}
	
	/**
	 * 测评活动链接
	 * @return
	 */
	public List<ActivityVo> getAssessList() {
		 List<ActivityVo> assessList = db().select(ASSESS_ACTIVITY.ACT_NAME,ASSESS_ACTIVITY.ID,ASSESS_ACTIVITY.START_TIME,ASSESS_ACTIVITY.END_TIME)
				.from(ASSESS_ACTIVITY)
				.where(ASSESS_ACTIVITY.END_TIME.ge(new Timestamp(System.currentTimeMillis())))
				.and(ASSESS_ACTIVITY.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
				.fetch().into(ActivityVo.class);
		return assessList;
	}
	
	/**
	 * 优惠券礼包活动链接
	 * @return
	 */
	public List<ActivityVo> getPackList() {
		List<ActivityVo>packList = db().select(COUPON_PACK.ID,COUPON_PACK.ACT_NAME,COUPON_PACK.START_TIME,COUPON_PACK.END_TIME)
				.from(COUPON_PACK)
				.where(COUPON_PACK.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
				.and(COUPON_PACK.END_TIME.ge(new Timestamp(System.currentTimeMillis())))
				.fetch().into(ActivityVo.class);
		return packList;
	}
	
	/**
	 * 创建跳转网页内容
	 * @param info
	 * @return
	 */
	public int saveWebLink(XcxLinkListVo info) {
		DecorateLinkRecord record = db().newRecord(DECORATE_LINK,info);
		record.setShopId(shopId);
		record.setLinkAction((byte) 1);
		int res = db().executeInsert(record);
		return res;
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
	 * 门店列表
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
				.where(MP_JUMP.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
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
				.where(DECORATE_LINK.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
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
	
	/**
	 * 选择平台分分类列表
	 * @return
	 */
	public List<SysCatevo> getSysCate() {
		//获取平台一级分类
		List<SysCatevo>	parentList = mainDb().select(CATEGORY.CAT_ID,CATEGORY.CAT_NAME)
				 .from(CATEGORY)
				 .where(CATEGORY.PARENT_ID.eq((short) 0 ))
				 .fetchInto(SysCatevo.class);
		
		//查询每级分类下的二级分类
		for(SysCatevo list : parentList ){
			List<SysCatevo> childList = mainDb().select(CATEGORY.CAT_ID,CATEGORY.CAT_NAME,CATEGORY.PARENT_ID)
					.from(CATEGORY)
					.where(CATEGORY.PARENT_ID.eq(list.getCatId()))
					.fetchInto(SysCatevo.class);
			list.setChildCate(childList);
		}
		return parentList;
	}
	
	/**
	 * 选择商家分类
	 * @return
	 */
	public List<SortVo> getSortList() {
		//一级分类信息
		List<SortVo> levelList = db().select(SORT.SORT_ID,SORT.SORT_NAME)
				.from(SORT)
				.where(SORT.LEVEL.eq((short) 0))
				.fetch().into(SortVo.class);
		
		//遍历每级分类下子分类
		for(SortVo level1 : levelList) {
			List<SortVo> level2List = db().select(SORT.SORT_ID,SORT.SORT_NAME)
				.from(SORT)
				.where(SORT.PARENT_ID.eq((int) level1.getSortId()))
				.fetch().into(SortVo.class);
			
			level1.setLevelList2(level2List);
		}
		return levelList;
	}
	
	/**
	 * 商品标签链接
	 * @return
	 */
	public List<GoodsLabelVo> getLabelList() {
		List<GoodsLabelVo> list = db().select(GOODS_LABEL.ID,GOODS_LABEL.NAME)
				.from(GOODS_LABEL)
				.where(GOODS_LABEL.DEL_FLAG.eq((int) 0))
				.fetch().into(GoodsLabelVo.class);
		return list;
	}
}
