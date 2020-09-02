package com.vpu.mp.dao.shop.order;

import com.vpu.mp.common.pojo.shop.table.ReturnOrderGoodsDo;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

import static com.vpu.mp.db.shop.Tables.RETURN_ORDER_GOODS;

/**
 * @author 孔德成
 * @date 2020/8/21 9:03
 */
@Component
public class ReturnOrderGoodsDao extends ShopBaseDao {

    public List<ReturnOrderGoodsDo> listCreateOrderGoodsByYesterday(Timestamp beginTime, Timestamp endTime) {
        return  db().selectFrom(RETURN_ORDER_GOODS)
                .where(RETURN_ORDER_GOODS.CREATE_TIME.ge(beginTime)).and(RETURN_ORDER_GOODS.CREATE_TIME.le(endTime))
                .fetchInto(ReturnOrderGoodsDo.class);
    }
}
