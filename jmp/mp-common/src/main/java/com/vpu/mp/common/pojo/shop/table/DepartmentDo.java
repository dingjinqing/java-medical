/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.common.pojo.shop.table;


import lombok.Data;

import javax.annotation.Generated;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;


/**
 * @author chenjie
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@Data
public class DepartmentDo {

    private Integer   id;
    private String    code;
    private String    name;
    private Integer   parentId;
    private String    parentIds;
    private Integer   level;
    private Byte      isLeaf;
    private Byte      isDelete;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Integer   first;
}