package com.vpu.mp.service.pojo.shop.title;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author chenjie
 */
@Data
public class TitleOneParam {
    private Integer id;
    /**
     * 职称名称
     */
    private String name;
    private String code;
    private Byte isDelete=0;
    private Timestamp createTime;
}
