package com.vpu.mp.service.pojo.shop.title;

import lombok.Data;

import java.util.Date;

@Data
public class TitleFetchOneParam {
    private String positionCode;
    private String name;
    private Integer state;
    private Integer createTime;
    private Date lastUpdateTime;
}
