package com.vpu.mp.service.shop.express;

import com.google.common.collect.Lists;
import com.vpu.mp.db.shop.tables.Shipping;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.express.ExpressVo;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.vpu.mp.db.shop.tables.Shipping.SHIPPING;

/**
 * 配送公司
 * @author 王帅
 */
@Service
public class ExpressService extends ShopBaseService {

    /**无单号物流发货时置单号为空*/
    public static final Byte NO_2_EXPRESS = 42;

    public final Shipping TABLE = SHIPPING;

    public List<ExpressVo> getEnabledList(){
        return db().selectFrom(TABLE).where(TABLE.ENABLED.eq(OrderConstant.YES)).fetchInto(ExpressVo.class);
    }

    public Map<Byte, ExpressVo> gets(List<Byte> ids){
        return db().selectFrom(TABLE).where(TABLE.SHIPPING_ID.in(ids)).fetchMap(TABLE.SHIPPING_ID, ExpressVo.class);
    }

    public ExpressVo get(Byte id){
        return gets(Lists.newArrayList(id)).get(id);
    }

    public ExpressVo get(String name) {
        if(StringUtils.isBlank(name)) {
            return null;
        }
        return db().selectFrom(TABLE).where(TABLE.SHIPPING_NAME.eq(name)).fetchOneInto(ExpressVo.class);
    }

    public ExpressVo getByCode(String name) {
        if(StringUtils.isBlank(name)) {
            return null;
        }
        return db().selectFrom(TABLE).where(TABLE.SHIPPING_CODE.eq(name)).fetchOneInto(ExpressVo.class);
    }

    public List<ExpressVo> getAll(){
        return db().selectFrom(TABLE).fetchInto(ExpressVo.class);
    }
}
