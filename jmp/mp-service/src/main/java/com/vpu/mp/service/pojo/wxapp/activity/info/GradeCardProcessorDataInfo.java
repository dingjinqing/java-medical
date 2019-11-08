package com.vpu.mp.service.pojo.wxapp.activity.info;

import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author 李晓冰
 * @date 2019年10月31日
 */
@Getter
@Setter
public class GradeCardProcessorDataInfo extends ProcessorDataInfo {
    public GradeCardProcessorDataInfo() {
        dataType = GoodsConstant.ACTIVITY_TYPE_MEMBER_GRADE;
    }

    private Integer prdId;
    private BigDecimal gradePrice;
}
