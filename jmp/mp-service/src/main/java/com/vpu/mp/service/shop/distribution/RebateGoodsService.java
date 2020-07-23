package com.vpu.mp.service.shop.distribution;

import static com.vpu.mp.db.shop.Tables.FANLI_GOODS_STATISTICS;
import static com.vpu.mp.db.shop.Tables.GOODS;
import static com.vpu.mp.db.shop.Tables.GOODS_SPEC_PRODUCT;
import static com.vpu.mp.db.shop.Tables.ORDER_GOODS_REBATE;
import static com.vpu.mp.db.shop.Tables.ORDER_INFO;
import static com.vpu.mp.db.shop.Tables.ORDER_GOODS;
import static com.vpu.mp.db.shop.Tables.USER;

import org.jooq.Record;
import org.jooq.SelectJoinStep;
import org.springframework.stereotype.Service;

import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.saas.category.SysCatevo;
import com.vpu.mp.service.pojo.shop.distribution.RebateGoodsDetailParam;
import com.vpu.mp.service.pojo.shop.distribution.RebateGoodsDetailVo;
import com.vpu.mp.service.pojo.shop.distribution.RebateGoodsParam;
import com.vpu.mp.service.pojo.shop.distribution.RebateGoodsVo;

/**
 * 	商品返利统计
 * @author 常乐
 * 2019年8月10日
 */
@Service
public class RebateGoodsService extends ShopBaseService{
	
	/** 查询别名 **/
    private static final String INVITE = "n";
    
	/**
	 * 商品返利统计列表
	 * @param param
	 * @return
	 */
	public PageResult<RebateGoodsVo> getRebateGoods(RebateGoodsParam param) {
		 SelectJoinStep<? extends Record> select = db().select(GOODS.GOODS_ID,FANLI_GOODS_STATISTICS.SALE_NUMBER,FANLI_GOODS_STATISTICS.PRD_TOTAL_FANLI,GOODS.GOODS_NAME,GOODS.GOODS_SALE_NUM,GOODS.SHOP_PRICE,GOODS_SPEC_PRODUCT.PRD_PRICE,GOODS.CAT_ID).from(FANLI_GOODS_STATISTICS
				.leftJoin(GOODS).on(FANLI_GOODS_STATISTICS.GOODS_ID.eq(GOODS.GOODS_ID)))
				 .leftJoin(GOODS_SPEC_PRODUCT).on(FANLI_GOODS_STATISTICS.PRD_ID.eq(GOODS_SPEC_PRODUCT.PRD_ID));
		optionBuild(select,param);
		PageResult<RebateGoodsVo> pageList = this.getPageResult(select, param.getCurrentPage(), param.getPageRows(), RebateGoodsVo.class);
		//获取商品对应分类名称
		for(RebateGoodsVo listInfo : pageList.dataList) {
			SysCatevo catInfo = saas.sysCate.getOneCateInfo(listInfo.getCatId());
			listInfo.setCatName(catInfo.getCatName());
		}
		return pageList;
	}
	
	/**
	 * 商品返利统计条件查询
	 * @param select
	 * @param param
	 */
	public void optionBuild(SelectJoinStep<? extends Record> select,RebateGoodsParam param) {
		//商品类型
		if(param.getGoodsType() != null) {
			select.where(FANLI_GOODS_STATISTICS.CAT_ID.eq(param.getGoodsType()));
		}
		//商品名称
		if(param.getGoodsName() != null) {
			select.where(GOODS.GOODS_NAME.contains(param.getGoodsName()));
		}
		select.orderBy(FANLI_GOODS_STATISTICS.PRD_TOTAL_FANLI.desc());
	}
	
	/**
	 * 商品返利明细列表
	 * @param param
	 * @return
	 */
	public PageResult<RebateGoodsDetailVo> getRebateGoodsDetail(RebateGoodsDetailParam param) {
		SelectJoinStep<? extends Record> select = db().select(ORDER_GOODS_REBATE.ORDER_SN,ORDER_GOODS_REBATE.REBATE_MONEY,
				ORDER_GOODS_REBATE.REBATE_USER_ID,ORDER_GOODS_REBATE.REBATE_PERCENT,ORDER_INFO.SETTLEMENT_FLAG,
				ORDER_INFO.FINISHED_TIME,ORDER_INFO.USER_ID,ORDER_GOODS.RETURN_NUMBER,ORDER_GOODS.CAN_CALCULATE_MONEY,
				ORDER_GOODS.GOODS_NAME,ORDER_GOODS.GOODS_NUMBER,ORDER_INFO.MOBILE,USER.as(INVITE).USERNAME.as("distributorName"))
				.from(ORDER_GOODS_REBATE
				.leftJoin(ORDER_INFO).on(ORDER_GOODS_REBATE.ORDER_SN.eq(ORDER_INFO.ORDER_SN))
				.leftJoin(USER.as(INVITE)).on(ORDER_GOODS_REBATE.REBATE_USER_ID.eq(USER.as(INVITE).USER_ID))
				.leftJoin(USER).on(ORDER_INFO.USER_ID.eq(USER.USER_ID)))
				.leftJoin(ORDER_GOODS).on(ORDER_GOODS_REBATE.ORDER_SN.eq(ORDER_GOODS.ORDER_SN));
		buildOptionDetail(select,param);
		PageResult<RebateGoodsDetailVo> pageList = this.getPageResult(select, param.getCurrentPage(), param.getPageRows(), RebateGoodsDetailVo.class);
		
		return pageList;
	}
	
	/**
	 * 商品返利明细条件查询
	 * @param select
	 * @param param
	 */
	public void buildOptionDetail(SelectJoinStep<? extends Record> select,RebateGoodsDetailParam param) {
		select.where(ORDER_INFO.FANLI_TYPE.eq((byte) 1).and(ORDER_GOODS_REBATE.GOODS_ID.eq(param.getGoodsId())));
		//被邀请人手机号
		if(param.getMobile() != null) {
			select.where(USER.MOBILE.contains(param.getMobile()));
		}
		//被邀请人用户名
		if(param.getUsername() != null) {
			select.where(USER.USERNAME.contains(param.getUsername()));
		}
		
		//分销员手机号
		if(param.getDistributorMobile() != null) {
			select.where(USER.as(INVITE).MOBILE.contains(param.getDistributorMobile()));
		}
		//分销员用户名
		if(param.getDistributorName() != null) {
			select.where(USER.as(INVITE).USERNAME.contains(param.getUsername()));
		}
		//返利开始时间
		if(param.getStartRebateTime() != null && param.getEndRebateTme() != null) {
			select.where(ORDER_INFO.FINISHED_TIME.ge(param.getStartRebateTime()));
		}
		//返利结束时间
		if(param.getEndRebateTme() != null) {
			select.where(ORDER_INFO.FINISHED_TIME.le(param.getEndRebateTme()));
		}
		//返利订单号
		if(param.getRebateOrderSn() != null) {
			select.where(ORDER_GOODS_REBATE.ORDER_SN.contains(param.getRebateOrderSn()));
		}
		//返利状态
		if(param.getRebateStatus() != null) {
			select.where(ORDER_INFO.SETTLEMENT_FLAG.eq(param.getRebateStatus()));
		}
	}
}
