package com.vpu.mp.service.pojo.shop.medical.goods.vo;

import com.vpu.mp.service.pojo.shop.medical.goods.base.GoodsMedicalBaseInfo;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * his对接药品信息类
 * @author 李晓冰
 * @date 2020年07月16日
 */
@Getter
@Setter
public class GoodsMedicalExternalRequestVo extends GoodsMedicalBaseInfo {
    private BigDecimal goodsPrice;
    private Integer goodsNumber;
    private Integer state;
    private Integer lastUpdateTime;
}
