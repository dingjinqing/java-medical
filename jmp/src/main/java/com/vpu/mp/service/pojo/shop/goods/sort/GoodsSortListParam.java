package com.vpu.mp.service.pojo.shop.goods.sort;

import java.sql.Timestamp;

import lombok.Data;

/**
 * @author 李晓冰
 * @date 2019年07月02日
 */
@Data
public class GoodsSortListParam {
    public static final byte TYPE_DEFAULT_VALUE = 0;//商家分类类型默认值 0普通分类，1推荐分类
    public static final int PARENT_ID_DEFAULT_VALUE = 0;//分类父id默认值 0一级分类，否则为二级分类

    private byte type = TYPE_DEFAULT_VALUE;
    private int parentId = PARENT_ID_DEFAULT_VALUE;
    private String sortName;
    private Timestamp startCreateTime;
    private Timestamp endCreateTime;
}