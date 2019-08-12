package com.vpu.mp.service.pojo.shop.market.form;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liufei
 * @date 2019/8/9
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedBackInnerVo {
    @JsonIgnore
    private String moduleValue;

    private String moduleType;
    private Integer votes;
    private Double percentage;

    public FeedBackInnerVo(String moduleValue, String moduleType) {
        this.moduleValue = moduleValue;
        this.moduleType = moduleType;
    }
}
