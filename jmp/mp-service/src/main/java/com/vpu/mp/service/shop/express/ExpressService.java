package com.vpu.mp.service.shop.express;

import com.vpu.mp.db.shop.tables.Shipping;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.express.ExpressVo;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.vpu.mp.db.shop.tables.Shipping.SHIPPING;

/**
 * 配送公司
 * @author 王帅
 */
@Service
public class ExpressService extends ShopBaseService {
    public final Shipping TABLE = SHIPPING;
    public List<ExpressVo> getEnabledList(){
        return db().selectFrom(TABLE).where(TABLE.ENABLED.eq(OrderConstant.YES)).fetchInto(ExpressVo.class);
    }
}
