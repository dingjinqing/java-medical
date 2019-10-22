package com.vpu.mp.service.pojo.shop.goods.goods;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author: 王兵兵
 * @create: 2019-10-21 18:24
 **/
public class GoodsExportParam extends GoodsPageListParam{
    @NotNull
    @Setter
    @Getter
    private Integer exportRowStart;
    @NotNull
    @Setter
    @Getter
    private Integer exportRowEnd;

}
