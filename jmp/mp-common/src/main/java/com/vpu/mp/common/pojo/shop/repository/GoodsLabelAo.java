package com.vpu.mp.common.pojo.shop.repository;

import lombok.Data;

/**
 * 处于repository层，用于对上层屏蔽底层数据存储结构
 * @author 李晓冰
 * @date 2020年07月09日
 */
@Data
public class GoodsLabelAo {
    private Integer id;
    private String labelName;
    private Integer gtaId;
}
