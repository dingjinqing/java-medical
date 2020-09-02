package com.vpu.mp.dao.shop.brand;

import com.vpu.mp.common.foundation.data.DelFlag;
import com.vpu.mp.common.pojo.shop.table.GoodsBrandDo;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import org.springframework.stereotype.Repository;

import static com.vpu.mp.db.shop.Tables.GOODS_BRAND;

/**
 * @author 李晓冰
 * @date 2020年07月07日
 */
@Repository
public class GoodsBrandDao extends ShopBaseDao {

    public GoodsBrandDo getByBrandId(Integer brandId) {
        GoodsBrandDo goodsBrandDo = db().selectFrom(GOODS_BRAND).where(GOODS_BRAND.DEL_FLAG.eq(DelFlag.NORMAL_VALUE).and(GOODS_BRAND.ID.eq(brandId)))
            .fetchAnyInto(GoodsBrandDo.class);
        return goodsBrandDo;
    }
}
