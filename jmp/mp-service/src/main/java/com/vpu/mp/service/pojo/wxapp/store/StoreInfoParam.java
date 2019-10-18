package com.vpu.mp.service.pojo.wxapp.store;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import javax.validation.constraints.PositiveOrZero;

/**
 * @author liufei
 * @date 10/18/19
 */
@Data
public class StoreInfoParam {
    @PositiveOrZero
    @JsonAlias({"store_id", "storeId"})
    public Integer storeId;
    public Long userId;
}
