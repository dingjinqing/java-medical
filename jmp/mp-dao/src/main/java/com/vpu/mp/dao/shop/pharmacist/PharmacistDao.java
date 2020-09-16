package com.vpu.mp.dao.shop.pharmacist;

import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.common.pojo.shop.table.PharmacistDo;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.records.PharmacistRecord;
import org.springframework.stereotype.Repository;

import static com.vpu.mp.db.shop.tables.Pharmacist.PHARMACIST;

/**
 * @author yangpengcheng
 * @date 2020/9/16
 **/
@Repository
public class PharmacistDao extends ShopBaseDao {

    public  int savePharmacist(PharmacistDo pharmacistDo){
        PharmacistRecord record=db().newRecord(PHARMACIST);
        FieldsUtil.assign(pharmacistDo,record);
        record.insert();
        pharmacistDo.setId(record.getId());
        return record.getId();
    }

}
