package com.vpu.mp.service.shop.goods;

import static com.vpu.mp.db.shop.Tables.GOODS_LABEL_COUPLE;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jooq.Condition;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.GoodsLabelCoupleRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.goods.label.GoodsLabel;
import com.vpu.mp.service.pojo.shop.goods.label.GoodsLabelCouple;
import com.vpu.mp.service.pojo.shop.goods.label.GoodsLabelCoupleTypeEnum;

/**
 * @author 黄荣刚
 * @date 2019年7月5日
 */
@Service

public class GoodsLabelCoupleService extends ShopBaseService {

    /**
     * 根据标签Id，查询标签的关联信息
     *
     * @param goodsLabel
     * @return
     */
    public List<GoodsLabelCouple> getList(GoodsLabel goodsLabel) {
        if (goodsLabel == null) {
            return new ArrayList<GoodsLabelCouple>();
        }
        List<GoodsLabelCouple> list = getListByLabelId(goodsLabel.getId());
        return list;
    }

    /**
     * 根据标签Id，查询标签的关联信息
     *
     * @param id
     * @return
     */
    public List<GoodsLabelCouple> getListByLabelId(Integer id) {
        if (id == null) {
            return new ArrayList<GoodsLabelCouple>(0);
        }
        List<GoodsLabelCouple> list = db().select(GOODS_LABEL_COUPLE.ID, GOODS_LABEL_COUPLE.LABEL_ID, GOODS_LABEL_COUPLE.GTA_ID, GOODS_LABEL_COUPLE.TYPE)
                .from(GOODS_LABEL_COUPLE)
                .where(GOODS_LABEL_COUPLE.LABEL_ID.eq(id))
                .fetchInto(GoodsLabelCouple.class);
        return list;
    }


    /**
     * 将传入商品标签所关联的关联信息入库
     *
     * @param goodsLabel
     */
    public void insert(GoodsLabel goodsLabel) {
        if (goodsLabel == null) {
            return;
        }
        Integer goodsLabelId = goodsLabel.getId();
        if( goodsLabel.getIsAll() == 0 ){
            this.transaction(() -> {
                batchInsertGoodsTypeGoodsLabelCouple(goodsLabelId, goodsLabel.getGoodsId());
                batchInsertCattTypeGoodsLabelCouple(goodsLabelId, goodsLabel.getCatId());
                batchInsertSortTypeGoodsLabelCouple(goodsLabelId, goodsLabel.getSortId());
            });
        }else{
            insertAllGoodsTypeGoodsLabelCouple(goodsLabelId);
        }

    }

    /**
     * 全部商品插入到关联表内
     * @param id 商品标签ID
     */
    private void insertAllGoodsTypeGoodsLabelCouple(Integer id){
        if (id == null) {
            return;
        }
        GoodsLabelCoupleRecord record = new GoodsLabelCoupleRecord();
        record.setLabelId(id);
        record.setType(GoodsLabelCoupleTypeEnum.ALLTYPE.getCode());
        record.insert();
    }

    /**
     * 批量插入商品标签所关联的商品ID信息，注意没有开启事务，须由调用者开启
     *
     * @param id          商品标签ID
     * @param goodsIdList 商品ID列表
     */
    public void batchInsertGoodsTypeGoodsLabelCouple(Integer id, List<Integer> goodsIdList) {
        if (id == null || goodsIdList == null) {
            return;
        }
        List<GoodsLabelCoupleRecord> goodsLabelCoupleList = new ArrayList<GoodsLabelCoupleRecord>(goodsIdList.size());
        for (Integer goodsId : goodsIdList) {
            GoodsLabelCoupleRecord record = new GoodsLabelCoupleRecord();
            record.setLabelId(id);
            record.setType(GoodsLabelCoupleTypeEnum.GOODSTYPE.getCode());
            record.setGtaId(goodsId);
            goodsLabelCoupleList.add(record);
        }
        db().batchInsert(goodsLabelCoupleList).execute();
        return;
    }

    /**
     * 批量插入商品标签所关联的商家分类ID信息，注意没有开启事务，须由调用者开启
     *
     * @param id         商品标签ID
     * @param sortIdList 商家分类ID列表
     */
    public void batchInsertSortTypeGoodsLabelCouple(Integer id, List<Integer> sortIdList) {
        if (id == null || sortIdList == null) {
            return;
        }
        List<GoodsLabelCoupleRecord> goodsLabelCoupleList = new ArrayList<GoodsLabelCoupleRecord>(sortIdList.size());
        for (Integer sortId : sortIdList) {
            GoodsLabelCoupleRecord record = new GoodsLabelCoupleRecord();
            record.setLabelId(id);
            record.setType(GoodsLabelCoupleTypeEnum.SORTTYPE.getCode());
            record.setGtaId(sortId);
            goodsLabelCoupleList.add(record);
        }
        db().batchInsert(goodsLabelCoupleList).execute();
        return;
    }

    /**
     * 批量插入商品标签所关联的平台分类ID信息，注意没有开启事务，须由调用者开启
     *
     * @param id        商品标签ID
     * @param catIdList 平台分类ID列表
     */
    public void batchInsertCattTypeGoodsLabelCouple(Integer id, List<Integer> catIdList) {
        if (id == null || catIdList == null) {
            return;
        }
        List<GoodsLabelCoupleRecord> goodsLabelCoupleList = new ArrayList<GoodsLabelCoupleRecord>(catIdList.size());
        for (Integer catId : catIdList) {
            GoodsLabelCoupleRecord record = new GoodsLabelCoupleRecord();
            record.setLabelId(id);
            record.setType(GoodsLabelCoupleTypeEnum.CATTYPE.getCode());
            record.setGtaId(catId);
            goodsLabelCoupleList.add(record);
        }
        db().batchInsert(goodsLabelCoupleList).execute();
        return;
    }

    /**
     * 批量删除商品标签关联信息
     *
     * @param goodsLabelCoupleList
     */
    public void batchDelete(List<GoodsLabelCouple> goodsLabelCoupleList) {
        if (goodsLabelCoupleList == null || goodsLabelCoupleList.size() == 0) {
            return;
        }
        List<GoodsLabelCoupleRecord> labelCoupleRecordList = new ArrayList<GoodsLabelCoupleRecord>(goodsLabelCoupleList.size());
        for (GoodsLabelCouple goodsLabelCouple : goodsLabelCoupleList) {
            labelCoupleRecordList.add(goodsLabelCouple.toRecord());
        }
        db().batchDelete(labelCoupleRecordList).execute();
        return;
    }

    /**
     * 批量增加商品标签的关联信息
     *
     * @param goodsLabelCoupleList
     */
    public void batchInsert(List<GoodsLabelCouple> goodsLabelCoupleList) {
        if (goodsLabelCoupleList == null || goodsLabelCoupleList.size() == 0) {
            return;
        }
        List<GoodsLabelCoupleRecord> labelCoupleRecordList = new ArrayList<GoodsLabelCoupleRecord>(goodsLabelCoupleList.size());
        for (GoodsLabelCouple goodsLabelCouple : goodsLabelCoupleList) {
            labelCoupleRecordList.add(goodsLabelCouple.toRecord());
        }
        db().batchInsert(labelCoupleRecordList).execute();
    }

    /**
     * 插入数据关系，但是不包含在数据库中已存在的对应关系
     * @param gtas
     * @param labels
     * @param type
     * @author 李晓冰
     * @return
     */
    public List<GoodsLabelCouple> calculateGtaLabelDiffer(List<Integer> gtas, List<Integer> labels, GoodsLabelCoupleTypeEnum type) {
        Map<Integer, List<Integer>> gtaLabelMap =
                db().select(GOODS_LABEL_COUPLE.GTA_ID, GOODS_LABEL_COUPLE.LABEL_ID)
                .from(GOODS_LABEL_COUPLE)
                .where(GOODS_LABEL_COUPLE.GTA_ID.in(gtas)).and(GOODS_LABEL_COUPLE.TYPE.eq(type.getCode()))
                .fetch().intoGroups(GOODS_LABEL_COUPLE.GTA_ID, GOODS_LABEL_COUPLE.LABEL_ID);

        List<GoodsLabelCouple> list=new ArrayList<>(gtas.size());

        gtas.forEach(gtaId->{
            List<Integer> labelIds = gtaLabelMap.get(gtaId);

            if (labelIds == null) {
                labels.forEach(id->{
                    GoodsLabelCouple goodsLabelCouple=new GoodsLabelCouple();
                    goodsLabelCouple.setGtaId(gtaId);
                    goodsLabelCouple.setLabelId(id);
                    goodsLabelCouple.setType(type.getCode());
                    list.add(goodsLabelCouple);
                });
                //从上一层的foreach返回
                return;
            }

            for (Integer id : labels) {
                if (labelIds.contains(id)) {
                    continue;
                }
                GoodsLabelCouple goodsLabelCouple=new GoodsLabelCouple();
                goodsLabelCouple.setGtaId(gtaId);
                goodsLabelCouple.setLabelId(id);
                goodsLabelCouple.setType(type.getCode());
                list.add(goodsLabelCouple);
            }
        });

        return list;
    }

    /**
     * 根据商品ids 删除对应标签对应关系 并对外提供统一事务入口
     * @param goodsIds
     * @author 李晓冰
     */
    public void deleteByGoodsIds(List<Integer> goodsIds) {
        db().delete(GOODS_LABEL_COUPLE)
                .where(GOODS_LABEL_COUPLE.GTA_ID.in(goodsIds))
                .and(GOODS_LABEL_COUPLE.TYPE.eq(GoodsLabelCoupleTypeEnum.GOODSTYPE.getCode()))
                .execute();
    }

    public void updateByGoodsId(Integer goodsId, List<Integer> labelIds) {
        db().delete(GOODS_LABEL_COUPLE)
            .where(GOODS_LABEL_COUPLE.GTA_ID.eq(goodsId))
            .and(GOODS_LABEL_COUPLE.TYPE.eq(GoodsLabelCoupleTypeEnum.GOODSTYPE.getCode()))
            .execute();
        List<GoodsLabelCoupleRecord> list=new ArrayList<>(labelIds.size());
        labelIds.forEach(labelId ->{
            GoodsLabelCoupleRecord record=new GoodsLabelCoupleRecord();
            record.setGtaId(goodsId);
            record.setLabelId(labelId);
            record.setType(GoodsLabelCoupleTypeEnum.GOODSTYPE.getCode());
            list.add(record);
        });
        db().batchInsert(list).execute();
    }

    /**
     * 根据标签id活动所有关联的商品ids或分类ids
     *
     * @param labelIds
     * @return
     */
    public Map<Byte,List<Integer>> selectGatIdsByLabelIds(List<Integer> labelIds) {
        Map<Byte, List<Integer>> byteListMap = db().select(GOODS_LABEL_COUPLE.GTA_ID, GOODS_LABEL_COUPLE.TYPE).from(GOODS_LABEL_COUPLE)
            .where(GOODS_LABEL_COUPLE.LABEL_ID.in(labelIds))
            .fetch().intoGroups(GOODS_LABEL_COUPLE.TYPE, GOODS_LABEL_COUPLE.GTA_ID);

        return byteListMap;
    }

    /**
     * 根据商品标签ID删除所有关联该标签的所有信息
     *
     * @param id
     */
    public void deleteByGoodsLabelId(Integer id) {
        db().deleteFrom(GOODS_LABEL_COUPLE)
                .where(GOODS_LABEL_COUPLE.LABEL_ID.eq(id))
                .execute();
    }

    public List<GoodsLabelCouple> getGoodsLabelCouple(Condition param){
        return db().select(GOODS_LABEL_COUPLE.ID, GOODS_LABEL_COUPLE.LABEL_ID, GOODS_LABEL_COUPLE.GTA_ID, GOODS_LABEL_COUPLE.TYPE)
            .from(GOODS_LABEL_COUPLE)
            .where(param)
            .fetchInto(GoodsLabelCouple.class);
    }
}
