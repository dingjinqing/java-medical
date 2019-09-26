package com.vpu.mp.service.shop.goods;


import static com.vpu.mp.db.shop.Tables.GOODS_LABEL;
import static com.vpu.mp.db.shop.Tables.GOODS_LABEL_COUPLE;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jooq.Record1;
import org.jooq.SelectConditionStep;
import org.jooq.SelectWhereStep;
import org.jooq.tools.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.GoodsLabelRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsView;
import com.vpu.mp.service.pojo.shop.goods.label.GoodsLabel;
import com.vpu.mp.service.pojo.shop.goods.label.GoodsLabelCouple;
import com.vpu.mp.service.pojo.shop.goods.label.GoodsLabelCoupleTypeEnum;
import com.vpu.mp.service.pojo.shop.goods.label.GoodsLabelListVo;
import com.vpu.mp.service.pojo.shop.goods.label.GoodsLabelPageListParam;
import com.vpu.mp.service.pojo.shop.goods.label.GoodsLabelTypeEnum;
import com.vpu.mp.service.pojo.shop.goods.label.GoodsLabelVo;

/**
 * @author 黄荣刚
 * @date 2019年7月4日
 *
 */
@Service

public class GoodsLabelService extends ShopBaseService {
	
	/** 未删除为0 */
	public static final Integer NORMAL = 0;
	/** 已经删除为1 */
	public static final Integer DISABLE = 1;
	
	@Autowired public GoodsLabelCoupleService goodsLabelCoupleService;
	
	@Autowired public GoodsService goodsService;
	@Autowired public GoodsSortService sortService;
	 /**
     * 分页获取商品标签信息
     *
     * @param param
     * @return
     */
    public PageResult<GoodsLabelVo> getPageList(GoodsLabelPageListParam param) {
       PageResult<GoodsLabelRecord> recordList = getPageRecordList(param);
       List<GoodsLabelVo> dataList = new ArrayList<GoodsLabelVo>();
       if(recordList.getDataList()!=null) {
    	   for (GoodsLabelRecord record : recordList.dataList) {
    		   GoodsLabelVo goodsLabelVo = convert2GoodsLabelVo(record);
    		   dataList.add(goodsLabelVo);
    	   }
       }

//		构造响应数据
		PageResult<GoodsLabelVo> result = new PageResult<GoodsLabelVo>();
		result.setDataList(dataList);
		result.setPage(recordList.getPage());
        return result;
    }
    
    /**
	 * @param record
	 * @return
	 */
	public GoodsLabelVo convert2GoodsLabelVo(GoodsLabelRecord record) {
		if(record == null) {
			return null;
		}
		GoodsLabelVo goodsLabelVo = new GoodsLabelVo();
		goodsLabelVo.setId(record.getId());
		goodsLabelVo.setName(record.getName());
		goodsLabelVo.setLevel(record.getLevel());
		goodsLabelVo.setGoodsDetail(record.getGoodsDetail());
		goodsLabelVo.setGoodsList(record.getGoodsList());
		goodsLabelVo.setGoodsSelect(record.getGoodsSelect());
		goodsLabelVo.setListPattern(record.getListPattern());
		goodsLabelVo.setIsAll(record.getIsAll());
		goodsLabelVo.setCreateTime(record.getCreateTime());
		goodsLabelVo.setUpdateTime(record.getUpdateTime());
		
		if(GoodsLabelTypeEnum.PART.getCode().equals(record.getIsAll())){
			goodsLabelVo = selectGoodsLabelApplyDetail(goodsLabelVo);
		}
		
		return goodsLabelVo;
	}
	/**
	 * 从数据库中将商品标签应用到哪些商品、平台分类和商家分类的详情查出来
	 * @param goodsLabelVo
	 * @return
	 */
	private GoodsLabelVo selectGoodsLabelApplyDetail(GoodsLabelVo goodsLabelVo) {
		GoodsLabel goodsLabel = selectGoodsLabelApplyDetail(goodsLabelVo.getId());
		List<GoodsView> goodsViewList = goodsService.selectGoodsViewList(goodsLabel.getGoodsId());
		
		goodsLabelVo.setGoodsViewList(goodsViewList);
		goodsLabelVo.setCatIdList(goodsLabel.getCatId());
		goodsLabelVo.setSortIdList(goodsLabel.getSortId());

		goodsLabelVo.setSortList(sortService.getList(goodsLabel.getSortId()));
		goodsLabelVo.setCatList(saas().sysCate.getList(goodsLabel.getCatId()));
		return goodsLabelVo;
	}
	
	/**
	 * 从数据库中将商品标签应用到哪些商品、平台分类和商家分类的详情查出来
	 * @param goodsLabelId
	 * @return
	 */
	private GoodsLabel selectGoodsLabelApplyDetail(Integer goodsLabelId) {
		List<GoodsLabelCouple> labelCoupleList = goodsLabelCoupleService.getListByLabelId(goodsLabelId);
		List<Integer> goodsIdList = new ArrayList<Integer>();
		List<Integer> catIdList = new ArrayList<Integer>();
		List<Integer> sortIdList = new ArrayList<Integer>();
		GoodsLabel goodsLabel = new GoodsLabel();
		if(labelCoupleList != null) {
			for (GoodsLabelCouple couple : labelCoupleList) {
				if(GoodsLabelCoupleTypeEnum.GOODSTYPE.getCode().equals(couple.getType())) {
					goodsIdList.add(couple.getGtaId());
				}else if(GoodsLabelCoupleTypeEnum.CATTYPE.getCode().equals(couple.getType())) {
					catIdList.add(couple.getGtaId());
				}else if(GoodsLabelCoupleTypeEnum.SORTTYPE.getCode().equals(couple.getType())){
					sortIdList.add(couple.getGtaId());
				}
			}
			
			goodsLabel.setGoodsId(goodsIdList);
			goodsLabel.setCatId(catIdList);
			goodsLabel.setSortId(sortIdList);
		}
		return goodsLabel;
	}
	
	/**
	 * 分页查出数据库中的记录
	 * @param param
	 * @return
	 */
	public PageResult<GoodsLabelRecord> getPageRecordList(GoodsLabelPageListParam param){
    	SelectWhereStep<GoodsLabelRecord> selectFrom = db().selectFrom(GOODS_LABEL);
    	SelectConditionStep<?> select = this.buildOptions(selectFrom, param);
    	PageResult<GoodsLabelRecord> recordList = this.getPageResult(select, param.getCurrentPage(), param.getPageRows(), GoodsLabelRecord.class);
    	return recordList;
    }

	/**
	 * @param selectFrom
	 * @param param
	 * @return
	 */
	private SelectConditionStep<?> buildOptions(SelectWhereStep<?> selectFrom,GoodsLabelPageListParam param) {
		 SelectConditionStep<?> scs = selectFrom
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
		this.transaction(()->{
			Record1<Integer> result = db()
					.insertInto(GOODS_LABEL, 
							GOODS_LABEL.NAME,GOODS_LABEL.GOODS_DETAIL,GOODS_LABEL.GOODS_LIST,
							GOODS_LABEL.IS_ALL,GOODS_LABEL.LEVEL,
							GOODS_LABEL.CREATE_TIME,GOODS_LABEL.LIST_PATTERN,
							GOODS_LABEL.GOODS_SELECT)
					.values(goodsLabel.getName(),goodsLabel.getGoodsDetail(),goodsLabel.getGoodsList(),
							goodsLabel.getIsAll(),goodsLabel.getLevel(),
							new Timestamp(System.currentTimeMillis()),goodsLabel.getListPattern(),
							goodsLabel.getGoodsSelect())
					.returningResult(GOODS_LABEL.ID).fetchOne();
			int id = result.get(0, Integer.class);
			goodsLabel.setId(id);
			goodsLabelCoupleService.insert(goodsLabel);
		});
	    return 0;
	}

	/**
	 * @param goodsLabel
	 * @return
	 */
	public int delete(GoodsLabel goodsLabel) {
		if(goodsLabel == null) {
			return 0;
		}
		return delete(goodsLabel.getId());
	}
	/**
	 * 
	 * @param id
	 * @return
	 */
	public int delete(Integer id) {
		if(id == null) {
			return 0;
		}
		int result = db().update(GOODS_LABEL)
				.set(GOODS_LABEL.DEL_FLAG,DISABLE)
				.set(GOODS_LABEL.DEL_TIME,new Timestamp(System.currentTimeMillis()))
				.where(GOODS_LABEL.ID.eq(id))
				.and(GOODS_LABEL.DEL_FLAG.eq(NORMAL))
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
											.and(GOODS_LABEL.DEL_FLAG.eq(NORMAL))
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
		
		this.transaction(()->{
			db().update(GOODS_LABEL).set(GOODS_LABEL.NAME,goodsLabel.getName())
					.set(GOODS_LABEL.GOODS_DETAIL,goodsLabel.getGoodsDetail())
					.set(GOODS_LABEL.GOODS_LIST,goodsLabel.getGoodsList())
					.set(GOODS_LABEL.IS_ALL,goodsLabel.getIsAll())
					.set(GOODS_LABEL.LEVEL,goodsLabel.getLevel())
					.set(GOODS_LABEL.LIST_PATTERN,goodsLabel.getListPattern())
					.set(GOODS_LABEL.GOODS_SELECT,goodsLabel.getGoodsSelect())
					.where(GOODS_LABEL.ID.eq(goodsLabel.getId()))
					.execute();
			goodsLabelCoupleService.deleteByGoodsLabelId(goodsLabel.getId());
			goodsLabelCoupleService.insert(goodsLabel);
		});
		return 0;
	}
	
	public GoodsLabel selectById(Integer id) {
		if(id == null) {
			return null;
		}
		GoodsLabel goodsLabel = db().select()
		.from(GOODS_LABEL)
		.where(GOODS_LABEL.ID.eq(id))
		.and(GOODS_LABEL.DEL_FLAG.eq(NORMAL))
		.fetchOneInto(GoodsLabel.class); 
		return goodsLabel;
	}
	
	public GoodsLabelVo selectGoodsLabelVoById(Integer id) {
		if(id == null) {
			return null;
		}
		GoodsLabelRecord record = db().selectFrom(GOODS_LABEL)
							.where(GOODS_LABEL.ID.eq(id))
							.and(GOODS_LABEL.DEL_FLAG.eq(NORMAL))
							.fetchOne();
		GoodsLabelVo goodsLabelVo = convert2GoodsLabelVo(record);
		return goodsLabelVo;
	}

    /**
     *  根据标签的类型和gta集合查找结果，产生和gta对应的分组数据。
     * @author 李晓冰
     * @param gtas 要筛选的key值集合
     * @param type map key:type对应类型的id值，value 标签列表
     * @return
     */
	public Map<Integer,List<GoodsLabelListVo>> getGtaLabelMap(List<Integer> gtas,GoodsLabelCoupleTypeEnum type) {
	   return db().select(GOODS_LABEL_COUPLE.GTA_ID,GOODS_LABEL.ID,GOODS_LABEL.NAME)
                .from(GOODS_LABEL).innerJoin(GOODS_LABEL_COUPLE).on(GOODS_LABEL.ID.eq(GOODS_LABEL_COUPLE.LABEL_ID))
                .where(GOODS_LABEL_COUPLE.GTA_ID.in(gtas)).and(GOODS_LABEL_COUPLE.TYPE.eq(type.getCode()))
                .fetch()
                .intoGroups(GOODS_LABEL_COUPLE.GTA_ID, GoodsLabelListVo.class);
    }

    /**
     *  根据type类型对标签数据进行分组
     * @param type 标签对应的类型
     * @return
     */
    public Map<Integer,List<GoodsLabelListVo>> getGtaLabelMap(GoodsLabelCoupleTypeEnum type) {
        return db().select()
                .from(GOODS_LABEL).innerJoin(GOODS_LABEL_COUPLE).on(GOODS_LABEL.ID.eq(GOODS_LABEL_COUPLE.LABEL_ID))
                .where(GOODS_LABEL_COUPLE.TYPE.eq(type.getCode()))
                .fetch()
                .intoGroups(GOODS_LABEL_COUPLE.GTA_ID,GoodsLabelListVo.class);
    }

    /**
     *  列出所有关联了商品的label集合
     * @return
     */
    public List<GoodsLabel> listGoodsLabelName(){
	    return db().select(GOODS_LABEL.ID,GOODS_LABEL.NAME)
                .from(GOODS_LABEL)
                .where(GOODS_LABEL.DEL_FLAG.eq((int) DelFlag.NORMAL.getCode()))
                .fetch().into(GoodsLabel.class);

    }

}
