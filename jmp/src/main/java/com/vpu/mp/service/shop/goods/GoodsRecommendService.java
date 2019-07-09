package com.vpu.mp.service.shop.goods;

import static com.vpu.mp.db.shop.Tables.RECOMMEND_GOODS;
import static com.vpu.mp.db.shop.Tables.GOODS;

import java.util.ArrayList;
import java.util.List;

import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.SelectConditionStep;
import org.jooq.SelectJoinStep;
import org.jooq.SelectWhereStep;
import org.jooq.tools.StringUtils;

import com.vpu.mp.db.shop.tables.RecommendGoods;
import com.vpu.mp.db.shop.tables.records.RecommendGoodsRecord;
import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.DelFlag;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.pojo.shop.goods.Goods;
import com.vpu.mp.service.pojo.shop.goods.recommend.GoodsRecommend;
import com.vpu.mp.service.pojo.shop.goods.recommend.GoodsRecommendPageListParam;

/**
 * @author 黄荣刚
 * @date 2019年7月9日
 *
 */
public class GoodsRecommendService extends BaseService {

	
	public PageResult<RecommendGoodsRecord> getPageList(GoodsRecommendPageListParam param){
		SelectWhereStep<RecommendGoodsRecord> selectFrom = db().selectFrom(RECOMMEND_GOODS);
		SelectConditionStep<?> select = this.buildOptions(selectFrom,param);
		PageResult<RecommendGoodsRecord> pageResult = this.getPageResult(select,param.getCurrentPage(),param.getPageRows(),RecommendGoodsRecord.class);
		
		return pageResult;
	}

	/**
	 * @param selectFrom
	 * @param param
	 * @return
	 */
	private SelectConditionStep<?> buildOptions(SelectWhereStep<RecommendGoodsRecord> selectFrom,
			GoodsRecommendPageListParam param) {
		SelectConditionStep<?> scs = selectFrom.where(RECOMMEND_GOODS.DEL_FLAG.eq(DelFlag.NORMAL.getCode()));
		if(!StringUtils.isBlank(param.getRecommendName())) {
			scs = scs.and(RECOMMEND_GOODS.RECOMMEND_NAME.like(this.likeValue(param.getRecommendName())));
		}
			
		return scs;
	}
	
	

}
