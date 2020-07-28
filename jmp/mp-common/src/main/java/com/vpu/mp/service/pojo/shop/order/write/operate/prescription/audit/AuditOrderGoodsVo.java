package com.vpu.mp.service.pojo.shop.order.write.operate.prescription.audit;

import com.vpu.mp.common.pojo.shop.table.OrderGoodsDo;
import com.vpu.mp.service.pojo.shop.prescription.PrescriptionSimpleVo;
import lombok.Data;

import java.util.List;

/**
 * 审核订单商品
 *
 * @author 孔德成
 * @date 2020/7/27 17:53
 */
@Data
public class AuditOrderGoodsVo {

    private List<OrderGoodsDo> goodsList;

    private PrescriptionSimpleVo prescription;
}
