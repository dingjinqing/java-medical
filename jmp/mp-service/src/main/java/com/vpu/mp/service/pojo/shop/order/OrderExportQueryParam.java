package com.vpu.mp.service.pojo.shop.order;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author: 王兵兵
 * @create: 2019-10-15 15:17
 **/
@Data
public class OrderExportQueryParam extends OrderPageListQueryParam {
    @NotNull
    private Integer exportRowStart;
    @NotNull
    private Integer exportRowEnd;
}
