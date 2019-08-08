package com.vpu.mp.service.pojo.shop.market.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author liufei
 * @date 2019/8/8
 * @description
 */
@Data
public class FormDetailParam {
    @NotNull
    private Integer pageId;
    private Integer shopId;
    private Integer userId;
}
