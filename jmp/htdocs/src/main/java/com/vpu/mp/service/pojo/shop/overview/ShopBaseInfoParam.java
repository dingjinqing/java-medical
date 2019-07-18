package com.vpu.mp.service.pojo.shop.overview;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;

/**
 * @Author:liufei
 * @Date:2019/7/16
 * @Description:
 */
@Data
@Component
public class ShopBaseInfoParam {
    @NotBlank
    private int shopId;
}
