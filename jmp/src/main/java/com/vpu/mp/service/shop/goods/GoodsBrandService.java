package com.vpu.mp.service.shop.goods;

import java.sql.Timestamp;

import com.vpu.mp.db.shop.tables.pojos.GoodsBrand;
import com.vpu.mp.service.foundation.Page;
import org.jooq.Record5;
import org.jooq.SelectConditionStep;
import org.jooq.SelectWhereStep;
import static org.jooq.impl.DSL.*;
import static com.vpu.mp.db.shop.Tables.GOODS_BRAND;

import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.PageResult;

import lombok.Data;

/**
 * 商品品牌
 * 
 * @author 李晓冰
 * @date 2019年6月25日
 */
public class GoodsBrandService extends BaseService {

	/**
	 *	 映射分页查询条件实体类
	 * 
	 * @author 李晓冰
	 * @date 2019年6月25日
	 */
	@Data
	public static class GoodsBrandPageListParam {

		public static final int CLASSIFY_ID_DEFAULT_VALUE = 0;// 品牌所附分类默认值
		public static final int IS_RECOMMEND_DEFAULT_VALUE = 0;// 是否推荐默认值
		public static final int IS_DELETE_DEFAULT_VALUE = 0;// 是否删除默认值0 未删除，1已删除
		/**
		 * 	搜索条件
		 */
		public String brandName;
		public Timestamp startAddTime;
		public Timestamp endAddTime;
		public int classifyId = CLASSIFY_ID_DEFAULT_VALUE;
		public int isRecommend = CLASSIFY_ID_DEFAULT_VALUE;

		/**
		 * 	分页信息
		 */
		public int currentPage = Page.DEFAULT_CURRENT_PAGE;
		public int pageRows = Page.DEFAULT_PAGE_ROWS;
	}

	/**
	 * 	分页获取品牌信息
	 * 
	 * @param param
	 * @return
	 */
	public PageResult getPageList(GoodsBrandPageListParam param) {
		SelectWhereStep<Record5<Integer, String, String, Byte, Timestamp>> selectFrom = db().select(GOODS_BRAND.ID,
				GOODS_BRAND.BRAND_NAME, GOODS_BRAND.LOGO, GOODS_BRAND.FIRST, GOODS_BRAND.ADD_TIME).from(GOODS_BRAND);

		SelectConditionStep<?> select = this.buildOptions(selectFrom, param);

		select.orderBy(GOODS_BRAND.FIRST.desc(), GOODS_BRAND.ADD_TIME.desc());

		PageResult pageResult = this.getPageResult(select, param.getCurrentPage(), param.getPageRows());

		return pageResult;
	}

	/**
	 * 	根据过滤条件构造对应的sql语句
	 * 
	 * @param select
	 * @param param
	 * @return
	 */
	private SelectConditionStep<?> buildOptions(SelectWhereStep<?> select, GoodsBrandPageListParam param) {
		SelectConditionStep<?> scs = select
				.where(field("is_delete").eq(GoodsBrandPageListParam.IS_DELETE_DEFAULT_VALUE));

		if (param.brandName != null) {
			scs = scs.and(GOODS_BRAND.BRAND_NAME.like(this.likeValue(param.getBrandName())));
		}

		if (param.startAddTime != null) {
			scs = scs.and(GOODS_BRAND.ADD_TIME.ge(param.getStartAddTime()));
		}

		if (param.endAddTime != null) {
			scs = scs.and(GOODS_BRAND.ADD_TIME.le(param.getEndAddTime()));
		}

		if (param.classifyId != GoodsBrandPageListParam.CLASSIFY_ID_DEFAULT_VALUE) {
			scs = scs.and(field("classify_id").eq(param.classifyId));
		}

		if (param.isRecommend != GoodsBrandPageListParam.IS_RECOMMEND_DEFAULT_VALUE) {
			scs = scs.and(field("is_recommend").eq(param.isRecommend));
		}

		return scs;
	}

	/**
	 * 	添加品牌
	 * 
	 * @param goodsBrand
	 * @return 数据库受影响行数
	 */
	public int insert(GoodsBrand goodsBrand) {
		int result = db()
				.insertInto(GOODS_BRAND, GOODS_BRAND.BRAND_NAME, GOODS_BRAND.E_NAME,
                        GOODS_BRAND.LOGO, GOODS_BRAND.FIRST, GOODS_BRAND.DESC,
						GOODS_BRAND.IS_RECOMMEND, GOODS_BRAND.CLASSIFY_ID)
				.values(goodsBrand.getBrandName(), goodsBrand.getEName(), goodsBrand.getLogo(), goodsBrand.getFirst(), goodsBrand.getDesc(),
						goodsBrand.getIsRecommend(), goodsBrand.getClassifyId())
				.execute();
		return result;
	}

	/**
	 * 	假删除指定品牌
	 * 
	 * @param goodsBrandId
	 * @return 数据库受影响行数
	 */
	public int delete(Integer goodsBrandId) {
		return db().update(GOODS_BRAND).set(GOODS_BRAND.IS_DELETE, (byte) 1).where(GOODS_BRAND.ID.eq(goodsBrandId))
				.execute();
	}

	/**
	 * 	更新指定商品
	 * 
	 * @param goodsBrand
	 * @return
	 */
	public int update(GoodsBrand goodsBrand) {
		return db().update(GOODS_BRAND).set(GOODS_BRAND.BRAND_NAME, goodsBrand.getBrandName())
				.set(GOODS_BRAND.E_NAME, goodsBrand.getEName()).set(GOODS_BRAND.LOGO, goodsBrand.getLogo())
				.set(GOODS_BRAND.FIRST, goodsBrand.getFirst()).set(GOODS_BRAND.DESC, goodsBrand.getDesc())
                .set(GOODS_BRAND.IS_RECOMMEND, goodsBrand.getIsRecommend()).set(GOODS_BRAND.CLASSIFY_ID, goodsBrand.getClassifyId())
                .where(GOODS_BRAND.ID.eq(goodsBrand.getId()))
				.execute();
	}

}
