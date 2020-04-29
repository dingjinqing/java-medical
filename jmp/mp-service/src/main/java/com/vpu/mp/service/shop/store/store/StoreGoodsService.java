package com.vpu.mp.service.shop.store.store;

import com.vpu.mp.db.shop.tables.records.StoreGoodsRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.saas.category.SysCatevo;
import com.vpu.mp.service.pojo.shop.goods.pos.PosSyncGoodsPrdParam;
import com.vpu.mp.service.pojo.shop.store.goods.StoreGoods;
import com.vpu.mp.service.pojo.shop.store.goods.StoreGoodsListQueryParam;
import com.vpu.mp.service.pojo.shop.store.goods.StoreGoodsListQueryVo;
import com.vpu.mp.service.pojo.shop.store.goods.StoreGoodsUpdateParam;
import com.vpu.mp.service.saas.categroy.SysCatServiceHelper;
import org.jooq.*;
import org.jooq.tools.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.tables.Goods.GOODS;
import static com.vpu.mp.db.shop.tables.GoodsSpecProduct.GOODS_SPEC_PRODUCT;
import static com.vpu.mp.db.shop.tables.StoreGoods.STORE_GOODS;

/**
 * @author 王兵兵
 *
 * 2019年7月12日
 */
@Service

public class StoreGoodsService extends ShopBaseService{

    public static final Byte ON_SALE = 1;
    public static final Byte OFF_SALE = 0;
    public static final Byte SYNC = 1;
    public static final Byte NOT_SYNC = 0;


    /**
     * 从店铺拉取数据更新门店内的商品信息
     * @param storeId 门店id
     */
    public void updateGoodsDataFromShop(Integer storeId) {

        List<StoreGoods> storeGoodsList = db().select(GOODS.GOODS_ID,GOODS.IS_ON_SALE,GOODS_SPEC_PRODUCT.PRD_ID,GOODS_SPEC_PRODUCT.PRD_SN,
            GOODS_SPEC_PRODUCT.PRD_NUMBER.as("product_number"),GOODS_SPEC_PRODUCT.PRD_PRICE.as("product_price"))
            .from(GOODS).innerJoin(GOODS_SPEC_PRODUCT).on(GOODS.GOODS_ID.eq(GOODS_SPEC_PRODUCT.GOODS_ID))
            .where(GOODS.DEL_FLAG.eq(DelFlag.NORMAL.getCode())).and(GOODS.IS_ON_SALE.eq((byte) 1)).fetchInto(StoreGoods.class);

        List<Integer> goodsPrdIdsForUpdate = db().select(STORE_GOODS.PRD_ID).from(STORE_GOODS).where(STORE_GOODS.STORE_ID.eq(storeId)).fetchInto(Integer.class);

        List<StoreGoodsRecord> recordsForInsert = new ArrayList<>(storeGoodsList.size());
        List<StoreGoodsRecord> recordsForUpdate = new ArrayList<>(goodsPrdIdsForUpdate.size());
        for (StoreGoods storeGoods : storeGoodsList) {
            StoreGoodsRecord record = new StoreGoodsRecord();
            assign(storeGoods,record);
            record.setStoreId(storeId);
            record.setFlag((byte)1);

            // TODO: 同步POS未实现

            if (goodsPrdIdsForUpdate.contains(storeGoods.getPrdId())) {
                recordsForUpdate.add(record);
            } else {
                recordsForInsert.add(record);
            }
        }
        transaction(()->{
            db().batchInsert(recordsForInsert).execute();
            db().batchUpdate(recordsForUpdate).execute();
        });
    }

	/**
	 * 门店商品列表分页查询
	 * @param param 数据查询过滤条件
	 * @return StorePageListVo 分页结果
	 */
	public PageResult<StoreGoodsListQueryVo> getPageList(StoreGoodsListQueryParam param) {
		SelectWhereStep<? extends Record> select = db().
				select(
				GOODS.GOODS_IMG,GOODS.GOODS_NAME,GOODS.CAT_ID,STORE_GOODS.IS_SYNC,GOODS_SPEC_PRODUCT.PRD_PRICE,GOODS_SPEC_PRODUCT.PRD_NUMBER,
				GOODS_SPEC_PRODUCT.PRD_DESC,STORE_GOODS.IS_ON_SALE,STORE_GOODS.PRODUCT_PRICE,STORE_GOODS.PRODUCT_NUMBER,STORE_GOODS.PRD_ID,STORE_GOODS.PRD_SN,GOODS_SPEC_PRODUCT.PRD_CODES
				).
				from(STORE_GOODS).
				leftJoin(GOODS_SPEC_PRODUCT).on(STORE_GOODS.PRD_ID.eq(GOODS_SPEC_PRODUCT.PRD_ID)).
				leftJoin(GOODS).on(GOODS.GOODS_ID.eq(GOODS_SPEC_PRODUCT.GOODS_ID));
		select = this.buildOptions(select, param);
		select.where(STORE_GOODS.STORE_ID.eq(param.getStoreId())).orderBy(STORE_GOODS.CREATE_TIME);
        PageResult<StoreGoodsListQueryVo> pageResult = getPageResult(select, param.getCurrentPage(), param.getPageRows(), StoreGoodsListQueryVo.class);
        // 处理平台分类信息
        List<SysCatevo> sysCate = SysCatServiceHelper.getAllSysCateVoByCat();
        Map<Integer, String> sysCateMap = sysCate.stream().collect(Collectors.toMap(SysCatevo::getCatId, SysCatevo::getCatName));
        pageResult.dataList.forEach(vo -> {
            vo.setCatName(sysCateMap.get(vo.getCatId()));
            vo.setGoodsImg(getImgFullUrlUtil(vo.getGoodsImg()));
        });

        return pageResult;
    }

	/**
	 * 条件查询
	 * @param select
	 * @param param
	 * @return
	 */
	public SelectWhereStep<? extends Record> buildOptions(SelectWhereStep<? extends  Record> select, StoreGoodsListQueryParam param) {
		if (param == null) {
			return select;
		}
		if (param.getIsOnSale() != null) {
			select.where(STORE_GOODS.IS_ON_SALE.eq(param.getIsOnSale()));
		}
		if (param.getIsSync() != null) {
			select.where(STORE_GOODS.IS_SYNC.eq(param.getIsSync()));
		}
		if (param.getCatId() != null && param.getCatId() > 0) {
			List<Integer> allCatId = saas().sysCate.findChildrenByParentId(param.getCatId());
			select.where(GOODS.CAT_ID.in(allCatId));
		}
		if (!StringUtils.isEmpty(param.getKeywords())) {
			select.where(GOODS.GOODS_NAME.contains(param.getKeywords()).or(GOODS_SPEC_PRODUCT.PRD_CODES.eq(param.getKeywords())));
		}
		return select;
	}

    /**
     * 门店商品-上架
     * @param param
     */
    public void storeGoodsPutOnSale(StoreGoodsUpdateParam param) {
        db().update(STORE_GOODS).set(STORE_GOODS.IS_ON_SALE,ON_SALE).where(STORE_GOODS.PRD_ID.in(param.getPrdId()).and(STORE_GOODS.STORE_ID.eq(param.getStoreId()))).execute();
    }

    /**
     * 门店商品-下架
     * @param param
     */
    public void storeGoodsPutOffSale(StoreGoodsUpdateParam param) {
        db().update(STORE_GOODS).set(STORE_GOODS.IS_ON_SALE,OFF_SALE).where(STORE_GOODS.PRD_ID.in(param.getPrdId()).and(STORE_GOODS.STORE_ID.eq(param.getStoreId()))).execute();
    }

    /**
     * 将相对路劲修改为全路径
     *
     * @param relativePath 相对路径
     * @return null或全路径
     */
    private String getImgFullUrlUtil(String relativePath) {
        if (org.apache.commons.lang3.StringUtils.isBlank(relativePath)) {
            return null;
        } else {
            return saas.shop.image.imageUrl(relativePath);
        }
    }

    /**
     * pos 同步数据批量更新
     * @param storeId 门店id
     * @param goodsPrdParams 待修改数据集合
     */
    public void batchUpdateForSyncPosProduct(Integer storeId, List<PosSyncGoodsPrdParam> goodsPrdParams) {
        if (goodsPrdParams.size()==0) {
            return;
        }
        PosSyncGoodsPrdParam item = goodsPrdParams.get(0);
        DSLContext db = db();
        String sql = db.update(STORE_GOODS)
            .set(STORE_GOODS.IS_ON_SALE,item.getIsOnSale())
            .set(STORE_GOODS.PRODUCT_PRICE,item.getPrdPrice())
            .set(STORE_GOODS.IS_SYNC,SYNC).where(STORE_GOODS.STORE_ID.eq(storeId).and(STORE_GOODS.PRD_ID.eq(item.getPrdId())))
            .getSQL();

        Query query = db.query(sql);
        BatchBindStep batchStep = db.batch(query);
        int addedCount = 0;
        final int batchCount = 500;

        for (int i = 0; i < goodsPrdParams.size(); i++) {
            item = goodsPrdParams.get(i);
            addedCount++;
            batchStep = batchStep.bind(item.getIsOnSale(), item.getPrdPrice(),SYNC, storeId, item.getPrdId());

            if (addedCount == batchCount) {
                batchStep.execute();
                batchStep = db.batch(query);
                addedCount = 0;
            }
        }
        batchStep.execute();
    }

    /**
     * 跟新门店商品规格库存
     * @param storeId 门店id
     * @param prdId 规格id
     * @param number 规格数量
     */
    public void updatePrdNumForPosSyncStock(Integer storeId, Integer prdId,Integer number) {
        db().update(STORE_GOODS).set(STORE_GOODS.PRODUCT_NUMBER,number)
            .where(STORE_GOODS.PRD_ID.eq(prdId).and(STORE_GOODS.STORE_ID.eq(storeId)))
            .execute();
    }
}
