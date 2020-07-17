package com.vpu.mp.dao.shop.goods;

import com.vpu.mp.common.foundation.data.DelFlag;
import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.common.pojo.shop.table.GoodsMedicalInfoDo;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.records.GoodsMedicalInfoRecord;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.vpu.mp.db.shop.Tables.GOODS_MEDICAL_INFO;

/**
 * @author 李晓冰
 * @date 2020年07月02日
 */
@Repository
public class GoodsMedicalInfoDao extends ShopBaseDao{

    /**
     * 插入商品的药品相关信息
     * @param goodsMedicalInfoDo
     */
    public void insert(GoodsMedicalInfoDo goodsMedicalInfoDo) {
        GoodsMedicalInfoRecord goodsMedicalInfoRecord = db().newRecord(GOODS_MEDICAL_INFO);
        FieldsUtil.assign(goodsMedicalInfoDo,goodsMedicalInfoRecord);
        goodsMedicalInfoRecord.insert();
        goodsMedicalInfoDo.setId(goodsMedicalInfoRecord.getId());
    }

    /**
     * 修改商品的药品相关信息
     * @param goodsMedicalInfoDo
     */
    public void update(GoodsMedicalInfoDo goodsMedicalInfoDo) {
        GoodsMedicalInfoRecord goodsMedicalInfoRecord = new GoodsMedicalInfoRecord();
        FieldsUtil.assign(goodsMedicalInfoDo,goodsMedicalInfoRecord);
        db().executeUpdate(goodsMedicalInfoRecord);
    }

    /**
     * 批量新增
     * @param goodsMedicalInfoDos
     */
    public void batchInsert(List<GoodsMedicalInfoDo> goodsMedicalInfoDos) {
        List<GoodsMedicalInfoRecord> goodsMedicalInfoRecords = new ArrayList<>(goodsMedicalInfoDos.size());

        for (GoodsMedicalInfoDo goodsMedicalInfoDo : goodsMedicalInfoDos) {
            GoodsMedicalInfoRecord goodsMedicalInfoRecord = new GoodsMedicalInfoRecord();
            FieldsUtil.assign(goodsMedicalInfoDo,goodsMedicalInfoRecord);
            goodsMedicalInfoRecords.add(goodsMedicalInfoRecord);
        }
        db().batchInsert(goodsMedicalInfoRecords).execute();
    }

    public void batchUpdate(List<GoodsMedicalInfoDo> goodsMedicalInfoDos) {
        List<GoodsMedicalInfoRecord> goodsMedicalInfoRecords = new ArrayList<>(goodsMedicalInfoDos.size());

        for (GoodsMedicalInfoDo goodsMedicalInfoDo : goodsMedicalInfoDos) {
            GoodsMedicalInfoRecord goodsMedicalInfoRecord = new GoodsMedicalInfoRecord();
            FieldsUtil.assign(goodsMedicalInfoDo,goodsMedicalInfoRecord);
            goodsMedicalInfoRecords.add(goodsMedicalInfoRecord);
        }
        db().batchUpdate(goodsMedicalInfoRecords).execute();
    }

    /**
     * 根据商品id查询
     * @param goodsId
     * @return
     */
    public GoodsMedicalInfoDo getByGoodsId(Integer goodsId) {
        GoodsMedicalInfoDo goodsMedicalInfoDo = db().selectFrom(GOODS_MEDICAL_INFO)
            .where(GOODS_MEDICAL_INFO.GOODS_ID.eq(goodsId).and(GOODS_MEDICAL_INFO.IS_DELETE.eq(DelFlag.NORMAL_VALUE)))
            .fetchAnyInto(GoodsMedicalInfoDo.class);

        return goodsMedicalInfoDo;
    }

    /**
     * 根据商品id获取对应的药品信息
     * @param goodsIds 商品id集合
     * @return 药品结合
     */
    public  List<GoodsMedicalInfoDo> listByGoodsIds(List<Integer> goodsIds) {
        return db().selectFrom(GOODS_MEDICAL_INFO)
            .where(GOODS_MEDICAL_INFO.IS_DELETE.eq(DelFlag.NORMAL_VALUE).and(GOODS_MEDICAL_INFO.GOODS_ID.in(goodsIds)))
            .fetchInto(GoodsMedicalInfoDo.class);
    }

    public List<GoodsMedicalInfoDo> listIdWithGoodsId(List<Integer> goodsId){
         return  db().select(GOODS_MEDICAL_INFO.GOODS_ID, GOODS_MEDICAL_INFO.ID).from(GOODS_MEDICAL_INFO)
            .where(GOODS_MEDICAL_INFO.IS_DELETE.eq(DelFlag.NORMAL_VALUE).and(GOODS_MEDICAL_INFO.GOODS_ID.in(goodsId)))
            .fetchInto(GoodsMedicalInfoDo.class);
    }


    public void deleteByGoodsId(Integer goodsId) {
        db().update(GOODS_MEDICAL_INFO)
            .set(GOODS_MEDICAL_INFO.IS_DELETE, DelFlag.DISABLE_VALUE)
            .where(GOODS_MEDICAL_INFO.GOODS_ID.eq(goodsId))
            .execute();
    }

}
