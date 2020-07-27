package com.vpu.mp.service.pojo.shop.order.goods;

import com.vpu.mp.service.pojo.shop.medical.goods.vo.GoodsMedicalOneInfoVo;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author yangpengcheng
 * @date 2020/7/24
 **/
@Data
public class OrderGoodsMedicalVo {
    private Integer orderId;
    private String orderSn;
    private Timestamp createTime;

    private List<GoodsMedicalOneInfoVo> goodsMedicalOneInfoVoList;
}
