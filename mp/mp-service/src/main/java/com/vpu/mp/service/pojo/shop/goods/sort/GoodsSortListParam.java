package com.vpu.mp.service.pojo.shop.goods.sort;

import java.sql.Timestamp;

import lombok.Data;

/**
 * @author 李晓冰
 * @date 2019年07月02日
 */
@Data
public class GoodsSortListParam {

    private Byte type;
    private Integer parentId;
    private String sortName;
    private Timestamp startCreateTime;
    private Timestamp endCreateTime;
}