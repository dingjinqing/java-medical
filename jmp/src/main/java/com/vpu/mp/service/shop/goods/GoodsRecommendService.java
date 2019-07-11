package com.vpu.mp.service.shop.goods;

import static com.vpu.mp.db.shop.Tables.RECOMMEND_GOODS;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.jooq.Record1;
import org.jooq.SelectConditionStep;
import org.jooq.SelectWhereStep;
import org.jooq.tools.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vpu.mp.db.shop.tables.records.RecommendGoodsRecord;
import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.DelFlag;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.foundation.Util;
import com.vpu.mp.service.pojo.shop.goods.GoodsView;
import com.vpu.mp.service.pojo.shop.goods.recommend.GoodsRecommend;
import com.vpu.mp.service.pojo.shop.goods.recommend.GoodsRecommendInsertParam;
import com.vpu.mp.service.pojo.shop.goods.recommend.GoodsRecommendPageListParam;
import com.vpu.mp.service.pojo.shop.goods.recommend.GoodsRecommendUpdateParam;


/**
 * @author 黄荣刚
 * @date 2019年7月9日
 *
 */
public class GoodsRecommendService extends BaseService {

	public GoodsService goodsService;
	
	/**
	 * 分页查询商品推荐 数据库记录
	 * @param param
	 * @return
	 */
	private PageResult<RecommendGoodsRecord> getPageRecordList(GoodsRecommendPageListParam param){
		SelectWhereStep<RecommendGoodsRecord> selectFrom = db().selectFrom(RECOMMEND_GOODS);
		SelectConditionStep<?> select = this.buildOptions(selectFrom,param);
		PageResult<RecommendGoodsRecord> pageResult = this.getPageResult(select,param.getCurrentPage(),param.getPageRows(),RecommendGoodsRecord.class);
		
		return pageResult;
	}
	/**
	 * 分页查询商品推荐 信息
	 * @param param
	 * @return
	 */
	public PageResult<GoodsRecommend>getPageList(GoodsRecommendPageListParam param){
		PageResult<RecommendGoodsRecord> pageList = getPageRecordList(param);
		List<GoodsRecommend> dataList = new ArrayList<GoodsRecommend>();
		if(pageList.getDataList()!= null) {
			List<RecommendGoodsRecord> list = pageList.getDataList();
//			遍历数据中的记录，转换为商品推荐信息的VO类
			for (RecommendGoodsRecord record : list) {
				GoodsRecommend goodsRecommend = convert2GoodsRecommend(record);
				dataList.add(goodsRecommend);
				if(!GoodsRecommend.PARTTYPE.equals(record.getRecommendType())) {
					continue;
				}
//				如果商品推荐信息为指定商品类型，则根据商品ID列表查出对应的商品VO类
				String recommendGoodsId = record.getRecommendGoodsId();
				if(recommendGoodsId!=null) {
					String[] split = recommendGoodsId.split(GoodsRecommend.DELIMITER);
					List<Integer> goodsIdList = Util.valueOf(split);
					List<GoodsView> goodsViewList = goodsService.selectGoodsViewList(goodsIdList);
					goodsRecommend.setRecommendGoods(goodsViewList);
				}
			}
		}
//		构造响应数据
		PageResult<GoodsRecommend> result = new PageResult<GoodsRecommend>();
		result.setDataList(dataList);
		result.setPage(pageList.getPage());
		return result;
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
	/**
	 * 查询指定名称的商品推荐 是否存在
	 * @param recommendName
	 * @return
	 */
	public boolean isGoodsRecommendNameExist(String recommendName) {
		if(recommendName == null) {
			return true;
		}
		Record1<Integer> record = db().selectCount()
										.from(RECOMMEND_GOODS)
										.where(RECOMMEND_GOODS.RECOMMEND_NAME.eq(recommendName))
										.and(RECOMMEND_GOODS.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
										.fetchOne();
		Integer num = record.value1();
		if(num>0) {
			return true;
		}
		return false;
	}

	/**
	 * @param goodsRecommendParam
	 * @return
	 */
	public int insert(GoodsRecommendInsertParam insertParam) {
		GoodsRecommend recommend = convert2GoodsRecommend(insertParam);
		return insert(recommend);
		
	}
	/**
	 * 将GoodsRecommendInsertParam类实例转换为 GoodsRecommend
	 * @param param
	 * @return
	 */
	public GoodsRecommend convert2GoodsRecommend(GoodsRecommendInsertParam param) {
		if(param == null) {
			return null;
		}
		GoodsRecommend recommend = new GoodsRecommend();
		recommend.setRecommendName(param.getRecommendName());
		recommend.setRecommendType(param.getRecommendType());
		recommend.setRecommendCatIds(param.getRecommendCatIds());
		recommend.setRecommendGoods(param.getRecommendGoods());
		recommend.setRecommendSortIds(param.getRecommendSortIds());
		recommend.setStatus(param.getStatus());
		recommend.setRecommendUsePage(param.getRecommendUsePage());
		return recommend;
	}
	
	/**
	 * @param goodsRecommendParam
	 * @return
	 */
	public int insert(GoodsRecommend goodsRecommend) {
		
		RecommendGoodsRecord record = new RecommendGoodsRecord();
		if(GoodsRecommend.PARTTYPE.equals(goodsRecommend.getRecommendType())) {
			record = convertListParam2Record(goodsRecommend.getRecommendGoods(), goodsRecommend.getRecommendCatIds(), goodsRecommend.getRecommendSortIds());
		}
		
		String usepageJson = convertUsePageList2Json(goodsRecommend.getRecommendUsePage());
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		int result = db().insertInto(RECOMMEND_GOODS, RECOMMEND_GOODS.RECOMMEND_NAME,
						RECOMMEND_GOODS.RECOMMEND_TYPE, RECOMMEND_GOODS.RECOMMEND_GOODS_ID,
						RECOMMEND_GOODS.RECOMMEND_SORT_ID, RECOMMEND_GOODS.RECOMMEND_CAT_ID,
						RECOMMEND_GOODS.RECOMMEND_USE_PAGE, RECOMMEND_GOODS.STATUS,
						RECOMMEND_GOODS.CREATE_TIME, RECOMMEND_GOODS.UPDATE_TIME, RECOMMEND_GOODS.DEL_FLAG )
			.values(goodsRecommend.getRecommendName(),
					goodsRecommend.getRecommendType(), record.getRecommendGoodsId(),
						record.getRecommendSortId(), record.getRecommendCatId(),
						usepageJson, goodsRecommend.getStatus(), 
						timestamp, timestamp, DelFlag.NORMAL.getCode()).execute();
		
		
		return result;
	}
	
	/**
	 * 根据ID将删除标志位置1
	 * @param id
	 */
	public int delete(Integer id) {
		int result = db().update(RECOMMEND_GOODS)
							.set(RECOMMEND_GOODS.DEL_FLAG, DelFlag.DISABLE.getCode())
							.set(RECOMMEND_GOODS.DEL_TIME,new Timestamp(System.currentTimeMillis()))
							.where(RECOMMEND_GOODS.ID.eq(id))
							.execute();
		return result;
	}
	
	/**
	 * 根据ID查数据库中的一条记录
	 * @param id
	 * @return
	 */
	public RecommendGoodsRecord selectRecord(Integer id) {
		return db().selectFrom(RECOMMEND_GOODS).where(RECOMMEND_GOODS.ID.eq(id)).fetchOne();
	}
	/**
	 * 根据ID 查找商品推荐信息,当ID记录删除标志位为1时，返回null
	 * @param id
	 * @return
	 */
	public GoodsRecommend select(Integer id) {
		RecommendGoodsRecord record = selectRecord(id);
		if(record == null || record.getDelFlag().equals(DelFlag.DISABLE.getCode())) {
			return null;
		}
		GoodsRecommend goodsRecommend = convert2GoodsRecommend(record);
		return goodsRecommend;
	}
	
	/**
	 * 更新记录
	 * @param goodsRecommendParam
	 * @return
	 */
	public int update(GoodsRecommendUpdateParam goodsRecommend) {
		if(goodsRecommend == null) {
			return 0;
		}
		GoodsRecommend recommend = convert2GoodsRecommend(goodsRecommend);
		int result = update(recommend);
		return result;
	}
	/**
	 * 将更新VO类转换为GoodsRecommend类实例
	 * @param param
	 * @return
	 */
	public GoodsRecommend convert2GoodsRecommend(GoodsRecommendUpdateParam param) {
		if(param == null) {
			return null;
		}
		GoodsRecommend recommend = new GoodsRecommend();
		recommend.setId(param.getId());
		recommend.setRecommendName(param.getRecommendName());
		recommend.setRecommendType(param.getRecommendType());
		recommend.setRecommendCatIds(param.getRecommendCatIds());
		recommend.setRecommendGoods(param.getRecommendGoods());
		recommend.setRecommendSortIds(param.getRecommendSortIds());
		recommend.setStatus(param.getStatus());
		recommend.setRecommendUsePage(param.getRecommendUsePage());
		return recommend;
	}
	
	/**
	 * 更新记录
	 * @param goodsRecommendParam
	 * @return
	 */
	public int update(GoodsRecommend goodsRecommend) {
		if(goodsRecommend == null) {
			return 0;
		}
		
		RecommendGoodsRecord record = new RecommendGoodsRecord();
		String usepageJson  = convertUsePageList2Json(goodsRecommend.getRecommendUsePage());
		if(GoodsRecommend.PARTTYPE.equals(goodsRecommend.getRecommendType())) {
			record = convertListParam2Record(goodsRecommend.getRecommendGoods(), goodsRecommend.getRecommendCatIds(), goodsRecommend.getRecommendSortIds());
		}
		int result = db().update(RECOMMEND_GOODS)
			.set(RECOMMEND_GOODS.RECOMMEND_NAME,goodsRecommend.getRecommendName())
			.set(RECOMMEND_GOODS.RECOMMEND_TYPE,goodsRecommend.getRecommendType())
			.set(RECOMMEND_GOODS.STATUS,goodsRecommend.getStatus())
			.set(RECOMMEND_GOODS.RECOMMEND_USE_PAGE,usepageJson)
			.set(RECOMMEND_GOODS.RECOMMEND_GOODS_ID, record.getRecommendGoodsId())
			.set(RECOMMEND_GOODS.RECOMMEND_CAT_ID,record.getRecommendCatId())
			.set(RECOMMEND_GOODS.RECOMMEND_SORT_ID, record.getRecommendSortId())
			.set(RECOMMEND_GOODS.UPDATE_TIME, new Timestamp(System.currentTimeMillis()))
			.where(RECOMMEND_GOODS.ID.eq(goodsRecommend.getId()))
			.execute();
		return result;
	}
	

	/**
	 * 将数据库记录转换为GoodsRecommend类 对象
	 * @param record
	 * @return
	 */
	public GoodsRecommend convert2GoodsRecommend(RecommendGoodsRecord record) {
		GoodsRecommend recommend = new GoodsRecommend();
		recommend.setId(record.getId());
		recommend.setRecommendName(record.getRecommendName());
		recommend.setRecommendType(record.getRecommendType());
		recommend.setStatus(record.getStatus());
		recommend.setDelFlag(record.getDelFlag());
		recommend.setDelTime(record.getDelTime());
		recommend.setUpdateTime(record.getUpdateTime());
		recommend.setCreateTime(record.getCreateTime());
		String usePageJson = record.getRecommendUsePage();
		List<String> usePageList = Util.parseJson(usePageJson,new TypeReference<List<String>>() {} );
		recommend.setRecommendUsePage(usePageList);
		if(GoodsRecommend.PARTTYPE.equals(recommend.getRecommendType())) {
			String catIds = record.getRecommendCatId();
			if(catIds != null ) {
				String[] split = catIds.split(GoodsRecommend.DELIMITER);
				List<Integer> catIdList = Util.valueOf(split);
				recommend.setRecommendCatIds(catIdList);
			}
			String sortIds = record.getRecommendSortId();
			if(sortIds != null) {
				String[] split = sortIds.split(GoodsRecommend.DELIMITER);
				List<Integer> sortIdList = Util.valueOf(split);
				recommend.setRecommendSortIds(sortIdList);
			}
			if(GoodsRecommend.PARTTYPE.equals(record.getRecommendType())) {
//				如果商品推荐信息为指定商品类型，则根据商品ID列表查出对应的商品VO类
					String recommendGoodsId = record.getRecommendGoodsId();
					if(recommendGoodsId!=null) {
						String[] split = recommendGoodsId.split(GoodsRecommend.DELIMITER);
						List<Integer> goodsIdList = Util.valueOf(split);
						List<GoodsView> goodsViewList = goodsService.selectGoodsViewList(goodsIdList);
						recommend.setRecommendGoods(goodsViewList);
					}
			}
		}
		return recommend;
	}
	
	private RecommendGoodsRecord convertListParam2Record(List<GoodsView> goodsList,List<Integer> catId,List<Integer> sortId) {
		List<Integer>goodsIdList = new ArrayList<Integer>();
		if(goodsList != null) {
			for (GoodsView goodsView : goodsList) {
				goodsIdList.add(goodsView.getGoodsId());
			}
		}
		return convertIdListParam2Record(goodsIdList,catId,sortId);
	}
	
	private RecommendGoodsRecord convertIdListParam2Record(List<Integer> goodsId,List<Integer> catId,List<Integer> sortId) {
		RecommendGoodsRecord record = new RecommendGoodsRecord();
		if(goodsId != null) {
			record.setRecommendGoodsId(StringUtils.join(goodsId.toArray(), GoodsRecommend.DELIMITER));
		}
		if(catId != null) {
			record.setRecommendCatId(StringUtils.join(catId.toArray(), GoodsRecommend.DELIMITER));
		}
		if(sortId != null) {
			record.setRecommendSortId(StringUtils.join(sortId.toArray(), GoodsRecommend.DELIMITER));
		}
		return record;
	}
	
	private String convertUsePageList2Json(List<String> usePageList) {
		usePageList = usePageList != null?usePageList:new ArrayList<String>(0);
		return Util.toJson(usePageList);
	}

}
