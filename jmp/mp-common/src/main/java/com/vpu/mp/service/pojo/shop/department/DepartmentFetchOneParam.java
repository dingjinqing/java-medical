package com.vpu.mp.service.pojo.shop.department;

import lombok.Data;

import java.util.Date;

@Data
public class DepartmentFetchOneParam {
    private String departCode;
    private String departName;
    private Integer state;
    private Integer createTime;
    private Date lastUpdateTime;
    private String pid;
}
