package com.vpu.mp.service.pojo.shop.goods.sort;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author 李晓冰
 * @date 2019年07月02日
 */
@Data
public class Sort {

    public static final Byte RECOMMENT_TYPE_CODE=1;
    public static final Byte NORMAL_TYPE_CODE=0;
    public static final Byte HAS_CHILD_CODE=1;
    public static final Byte HAS_NO_CHILD_CODE=0;

    private Integer sortId;
    private String sortName;
    private Integer parentId;
    private Short level;
    private Byte hasChild;
    private Timestamp createTime;
    private Timestamp updateTime;
    private String sortImg;
    private String imgLink;
    private Short first;
    private Byte type;
    private String sortDesc;
    private Integer goodsNumber;
    private List<? extends Sort> children;
}
