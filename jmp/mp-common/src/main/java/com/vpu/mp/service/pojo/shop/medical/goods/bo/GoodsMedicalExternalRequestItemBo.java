package com.vpu.mp.service.pojo.shop.medical.goods.bo;

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
public class GoodsMedicalExternalRequestItemBo extends GoodsMedicalBaseInfo {
    private Integer goodsId;
    private BigDecimal goodsPrice;
    private Integer goodsNumber;
    private Integer state;
    private Byte isMedical;
    private Byte source;
    private Integer lastUpdateTime;
}
