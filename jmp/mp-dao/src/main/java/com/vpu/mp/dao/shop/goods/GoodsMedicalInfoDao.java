package com.vpu.mp.dao.shop.goods;

import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.common.pojo.shop.table.GoodsMedicalInfoDo;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.records.GoodsMedicalInfoRecord;
import org.springframework.stereotype.Repository;

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
        GoodsMedicalInfoRecord goodsMedicalInfoRecord = new GoodsMedicalInfoRecord();
        FieldsUtil.assign(goodsMedicalInfoDo,goodsMedicalInfoRecord);
        db().executeInsert(goodsMedicalInfoRecord);
        goodsMedicalInfoDo.setId(goodsMedicalInfoRecord.getId());
    }
}
