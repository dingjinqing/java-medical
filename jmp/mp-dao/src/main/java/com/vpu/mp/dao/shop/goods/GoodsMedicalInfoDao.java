package com.vpu.mp.dao.shop.goods;

import com.vpu.mp.common.foundation.data.DelFlag;
import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.common.pojo.shop.table.GoodsMedicalInfoDo;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.records.GoodsMedicalInfoRecord;
import org.springframework.stereotype.Repository;

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


    public void deleteByGoodsId(Integer goodsId) {
        db().update(GOODS_MEDICAL_INFO)
            .set(GOODS_MEDICAL_INFO.IS_DELETE, DelFlag.DISABLE_VALUE)
            .where(GOODS_MEDICAL_INFO.GOODS_ID.eq(goodsId))
            .execute();
    }

    /**
     * 获取商品药品信息
     * @param goodsId 商品
     * @return GoodsMedicalInfo or null
     */
    public GoodsMedicalInfoDo getByGoodsId(Integer goodsId) {
      return db().select().from(GOODS_MEDICAL_INFO).where(GOODS_MEDICAL_INFO.GOODS_ID.eq(goodsId)).fetchAnyInto(GoodsMedicalInfoDo.class);
    }


}
