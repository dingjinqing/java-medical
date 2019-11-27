package com.vpu.mp.service.shop.goods;

import com.vpu.mp.db.shop.tables.records.GoodsLabelCoupleRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.goods.label.GoodsLabelCouple;
import com.vpu.mp.service.pojo.shop.goods.label.GoodsLabelCoupleTypeEnum;
import org.jooq.Condition;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.vpu.mp.db.shop.Tables.GOODS_LABEL_COUPLE;

/**
 * @author 黄荣刚
 * @date 2019年7月5日
 */
@Service

public class GoodsLabelCoupleService extends ShopBaseService {

    /**
     * 批量插入商品标签所关联的商品ID信息，注意没有开启事务，须由调用者开启
     *
     * @param labelId  商品标签ID
     * @param goodsIdList 商品ID列表
     */
    public void batchInsertGoodsTypeGoodsLabelCouple(Integer labelId, List<Integer> goodsIdList) {
        if (labelId == null || goodsIdList == null) {
            return;
        }
        batchInsertGoodsLabelCouple(labelId,goodsIdList,GoodsLabelCoupleTypeEnum.GOODSTYPE);
    }

    /**
     * 批量插入商品标签所关联的商家分类ID信息
     * @param labelId         商品标签ID
     * @param sortIdList 商家分类ID列表
     */
    public void batchInsertSortTypeGoodsLabelCouple(Integer labelId, List<Integer> sortIdList) {
        if (labelId == null || sortIdList == null) {
            return;
        }
        batchInsertGoodsLabelCouple(labelId,sortIdList,GoodsLabelCoupleTypeEnum.SORTTYPE);
    }

    /**
     * 批量插入商品标签所关联的平台分类ID信息
     * @param labelId        商品标签ID
     * @param catIdList 平台分类ID列表
     */
    public void batchInsertCatTypeGoodsLabelCouple(Integer labelId, List<Integer> catIdList) {
        if (labelId == null || catIdList == null) {
            return;
        }
        batchInsertGoodsLabelCouple(labelId,catIdList,GoodsLabelCoupleTypeEnum.CATTYPE);
    }

    /**
     * 批量插入商品标签关联所有商品信息
     * @param labelId        商品标签ID
     */
    public void insertAllGoodsLabelCouple(Integer labelId) {
        if (labelId == null) {
            return;
        }
        batchInsertGoodsLabelCouple(labelId,null,GoodsLabelCoupleTypeEnum.ALLTYPE);
    }
    /**
     * 批量增加商品标签的关联信息
     * @param labelId 标签id
     * @param gtaIdList 关联项id集合
     * @param typeEnum 插入的关联类型
     */
    private void batchInsertGoodsLabelCouple(Integer labelId, List<Integer> gtaIdList, GoodsLabelCoupleTypeEnum typeEnum) {
        if (GoodsLabelCoupleTypeEnum.ALLTYPE.equals(typeEnum)) {
            GoodsLabelCoupleRecord record = new GoodsLabelCoupleRecord();
            record.setLabelId(labelId);
            record.setType(GoodsLabelCoupleTypeEnum.ALLTYPE.getCode());
            db().executeInsert(record);
        } else {
            List<GoodsLabelCoupleRecord> recordList = new ArrayList<>();
            gtaIdList.forEach(gtaId->{
                GoodsLabelCoupleRecord record = new GoodsLabelCoupleRecord();
                record.setLabelId(labelId);
                record.setType(typeEnum.getCode());
                record.setGtaId(gtaId);
                recordList.add(record);
            });
            db().batchInsert(recordList).execute();
        }
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
