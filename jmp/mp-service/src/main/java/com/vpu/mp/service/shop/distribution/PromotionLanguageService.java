package com.vpu.mp.service.shop.distribution;

import static com.vpu.mp.db.shop.Tables.PROMOTION_LANGUAGE;

import org.jooq.Record;
import org.jooq.SelectJoinStep;
import org.springframework.stereotype.Service;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.distribution.PromotionLanguageListParam;
import com.vpu.mp.service.pojo.shop.distribution.PromotionLanguageListVo;

@Service
public class PromotionLanguageService extends ShopBaseService{
	
	/**
	 * 分销推广语列表
	 * @param param
	 * @return
	 */
	public PageResult<PromotionLanguageListVo> getPromotionLanguageList(PromotionLanguageListParam param) {
		SelectJoinStep<? extends Record> select = db().select(PROMOTION_LANGUAGE.ID,PROMOTION_LANGUAGE.TITLE,PROMOTION_LANGUAGE.PROMOTION_LANGUAGE_).from(PROMOTION_LANGUAGE);
		buildOptions(select,param);
		PageResult<PromotionLanguageListVo> pageList = this.getPageResult(select, param.getCurrentPage(), param.getPageRows(), PromotionLanguageListVo.class);
		return pageList;
	}
	
	/**
	 * 分销推广语条件查询
	 * @param select
	 * @param param
	 */
	public void buildOptions(SelectJoinStep<? extends Record> select,PromotionLanguageListParam param) {
		//推广内容
		if(param.getPromotionLanguage() != null) {
			select.where(PROMOTION_LANGUAGE.PROMOTION_LANGUAGE_.contains(param.getPromotionLanguage()));
		}
		//创建时间
		if(param.getStartCreateTime() != null && param.getEndCreateTime() != null) {
			select.where(PROMOTION_LANGUAGE.CREATE_TIME.ge(param.getStartCreateTime())).and(PROMOTION_LANGUAGE.CREATE_TIME.le(param.getEndCreateTime()));
		}
		//修改时间
		if(param.getStartUpdateTime() != null && param.getEndUpdateTime() != null) {
			select.where(PROMOTION_LANGUAGE.UPDATE_TIME.ge(param.getStartUpdateTime())).and(PROMOTION_LANGUAGE.UPDATE_TIME.le(param.getEndUpdateTime()));
		}
		select.orderBy(PROMOTION_LANGUAGE.UPDATE_TIME.desc());
	}
}
