package com.vpu.mp.service.pojo.shop.goods.brand;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author 李晓冰
 * @date 2019年07月26日
 */
@Data
public class GoodsBrandClassifyVo {
    private Integer classifyId;
    private String classifyName;
    private Short first;
    private Integer brandNum;
    private Timestamp createTime;
}
