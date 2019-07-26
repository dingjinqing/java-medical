package com.vpu.mp.service.pojo.shop.goods.brand;

import lombok.Data;

import java.util.List;

/**
 * @author 李晓冰
 * @date 2019年07月26日
 */
@Data
public class GoodsBrandBatchParam {
    private List<Integer> ids;
    private Integer classifyId;
}
