package com.vpu.mp.service.shop.goods;


import static com.vpu.mp.db.shop.Tables.GOODS_LABEL;

import java.sql.Timestamp;

import org.jooq.Record1;
import org.jooq.Record10;
import org.jooq.SelectConditionStep;
import org.jooq.SelectJoinStep;
import org.jooq.tools.StringUtils;

import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.pojo.shop.goods.label.GoodsLabel;
import com.vpu.mp.service.pojo.shop.goods.label.GoodsLabelPageListParam;

/**
 * @author 黄荣刚
 * @date 2019年7月4日
 *
 */
public class GoodsLabelService extends BaseService {
	
	 /**
     * 分页获取商品标签信息
     *
     * @param param
     * @return
     */
    public PageResult<GoodsLabel> getPageList(GoodsLabelPageListParam param) {
        SelectJoinStep<Record10<Integer, String, Byte, Byte, Byte, Short, Timestamp, Timestamp, Short, Byte>> selectFrom = db().select(GOODS_LABEL.ID, GOODS_LABEL.NAME, GOODS_LABEL.GOODS_DETAIL, GOODS_LABEL.GOODS_LIST, GOODS_LABEL.IS_ALL, GOODS_LABEL.LEVEL, GOODS_LABEL.CREATE_TIME, GOODS_LABEL.UPDATE_TIME, GOODS_LABEL.LIST_PATTERN, GOODS_LABEL.GOODS_SELECT).from(GOODS_LABEL);

        SelectConditionStep<?> select = this.buildOptions(selectFrom, param);

        PageResult<GoodsLabel> pageResult = this.getPageResult(select, param.getCurrentPage(), param.getPageRows(), GoodsLabel.class);

        return pageResult;
    }

	/**
	 * @param selectFrom
	 * @param param
	 * @return
	 */
	private SelectConditionStep<?> buildOptions(SelectJoinStep<?> select,GoodsLabelPageListParam param) {
		 SelectConditionStep<?> scs = select
	                .where(GOODS_LABEL.DEL_FLAG.eq(GoodsLabelPageListParam.DEL_FLAG));

	        if (!StringUtils.isBlank(param.getLabelName())) {
	            scs = scs.and(GOODS_LABEL.NAME.like(this.likeValue(param.getLabelName())));
	        }

		return scs;
	}
	
	/**
	 * 数据库中已存在该名称的名称返回 true
	 * 否则返回 false
	 * @param goodsLabel
	 * @return 
	 * 
	 */
	public boolean isLabelNameExist(GoodsLabel goodsLabel) {
		Record1<Integer> recordCount = db().selectCount()
												.from(GOODS_LABEL)
												.where(GOODS_LABEL.NAME.eq(goodsLabel.getName()))
												.and(GOODS_LABEL.DEL_FLAG.eq(GoodsLabelPageListParam.DEL_FLAG))
												.fetchOne();
		Integer count = recordCount.getValue(0,Integer.class);
		if(count>0) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param goodsLabel
	 * @return
	 */
	public int insert(GoodsLabel goodsLabel) {
	        Record1<Integer> result = db()
	                .insertInto(GOODS_LABEL, GOODS_LABEL.NAME, GOODS_LABEL.GOODS_DETAIL,GOODS_LABEL.GOODS_LIST,GOODS_LABEL.IS_ALL,GOODS_LABEL.LEVEL,GOODS_LABEL.CREATE_TIME,GOODS_LABEL.LIST_PATTERN,GOODS_LABEL.GOODS_SELECT)
	                .values(goodsLabel.getName(),goodsLabel.getGoodsDetail(),goodsLabel.getGoodsList(),goodsLabel.getIsAll(),goodsLabel.getLevel(),goodsLabel.getCreateTime(),goodsLabel.getListPattern(),goodsLabel.getGoodsSelect())
	                .returningResult(GOODS_LABEL.ID).fetchOne();
	        int  id = result.get(0, Integer.class);
	        goodsLabel.setId(id);
	        return id;
	}

	/**
	 * @param goodsLabel
	 * @return
	 */
	public int delete(GoodsLabel goodsLabel) {
		int result = db().update(GOODS_LABEL)
			.set(GOODS_LABEL.DEL_FLAG,1)
			.set(GOODS_LABEL.DEL_TIME,new Timestamp(System.currentTimeMillis()))
			.where(GOODS_LABEL.ID.eq(goodsLabel.getId()))
			.execute();
		return result;
	}

	/**
	 * @param goodsLabel
	 * @return
	 */
	public boolean isOtherLabelNameExist(GoodsLabel goodsLabel) {
		Record1<Integer> recordCount = db().selectCount()
											.from(GOODS_LABEL)
											.where(GOODS_LABEL.NAME.eq(goodsLabel.getName()))
											.and(GOODS_LABEL.ID.ne(goodsLabel.getId()))
											.and(GOODS_LABEL.DEL_FLAG.eq(GoodsLabelPageListParam.DEL_FLAG))
											.fetchOne();
		Integer count = recordCount.getValue(0,Integer.class);
		if(count>0) {
			return true;
		}
		return false;
	}

	/**
	 * @param goodsLabel
	 */
	public int update(GoodsLabel goodsLabel) {
		int result = db().update(GOODS_LABEL).set(GOODS_LABEL.NAME,goodsLabel.getName())
								.set(GOODS_LABEL.GOODS_DETAIL,goodsLabel.getGoodsDetail())
								.set(GOODS_LABEL.GOODS_LIST,goodsLabel.getGoodsList())
								.set(GOODS_LABEL.IS_ALL,goodsLabel.getIsAll())
								.set(GOODS_LABEL.LEVEL,goodsLabel.getLevel())
								.set(GOODS_LABEL.LIST_PATTERN,goodsLabel.getListPattern())
								.set(GOODS_LABEL.GOODS_SELECT,goodsLabel.getGoodsSelect())
								.set(GOODS_LABEL.UPDATE_TIME,new Timestamp(System.currentTimeMillis()))
								.where(GOODS_LABEL.ID.eq(goodsLabel.getId()))
								.execute();
		return result;
	}
	
	public GoodsLabel selectById(Integer id) {
		if(id == null) {
			return null;
		}
		GoodsLabel goodsLabel = db().select()
		.from(GOODS_LABEL)
		.where(GOODS_LABEL.ID.eq(id))
		.fetchOneInto(GoodsLabel.class);
		return goodsLabel;
	}

}
