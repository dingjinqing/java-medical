package com.vpu.mp.service.pojo.shop.goods.sort;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * @author 李晓冰
 * @date 2019年11月20日
 */
@Getter
@Setter
public class GoodsSortListVo{
    private Integer sortId;
    private String sortName;
    private Integer parentId;
    private Byte hasChild;
    private Short level;

    private String sortImg;
    private String imgLink;
    private Short first;
    private Timestamp createTime;
}
