package com.vpu.mp.service.pojo.shop.order.write.operate.prescription;

import com.vpu.mp.service.pojo.shop.order.goods.OrderGoodsVo;
import com.vpu.mp.service.pojo.shop.prescription.PrescriptionVo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 处方关联订单商品
 * @author 孔德成
 * @date 2020/7/9 16:25
 */
@Data
public class PrescriptionOrderGoodsVo {

    /**
     * 处方关联商品
    */
   private List<OrderGoodsVo> orderGoodsList =new ArrayList<>();

    /**
    * 处方
    */
   private PrescriptionVo prescriptionVo;
}
