package com.vpu.mp.service.pojo.shop.market.form;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author liufei
 * @date 2019/8/8
 */
@Data
public class FormDetailVo{
    private Integer pageId;
    private String pageName;
    private Byte status;
    private String pageContent;
    private String formCfg;
    private Timestamp startTime;
    private Timestamp endTime;
    /**有效期*/
    private Byte isForeverValid;
    private Integer submitNum;
}
