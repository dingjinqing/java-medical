package com.vpu.mp.service.shop.distribution;

import static com.vpu.mp.db.shop.Tables.FANLI_GOODS_STATISTICS;
import static com.vpu.mp.db.shop.Tables.GOODS;

import org.jooq.Record;
import org.jooq.SelectJoinStep;
import org.springframework.stereotype.Service;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.distribution.RebateGoodsParam;
import com.vpu.mp.service.pojo.shop.distribution.RebateGoodsVo;

/**
 * 	商品返利统计
 * @author 常乐
 * 2019年8月10日
 */
@Service
public class RebateGoodsService extends ShopBaseService{
	/**
	 * 返利商品统计列表
	 * @param param
	 * @return
	 */
	public PageResult<RebateGoodsVo> getRebateGoods(RebateGoodsParam param) {
		 SelectJoinStep<? extends Record> select = db().select(FANLI_GOODS_STATISTICS.SALE_NUMBER,FANLI_GOODS_STATISTICS.PRD_TOTAL_FANLI,GOODS.GOODS_NAME,GOODS.GOODS_SALE_NUM,GOODS.SHOP_PRICE).from(FANLI_GOODS_STATISTICS
				.leftJoin(GOODS).on(FANLI_GOODS_STATISTICS.GOODS_ID.eq(GOODS.GOODS_ID)));
		optionBuild(select,param);
		PageResult<RebateGoodsVo> pageList = this.getPageResult(select, param.currentPage, param.pageRows, RebateGoodsVo.class);
		return pageList;
	}
	
	/**
	 * 返利商品统计条件查询
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
}
