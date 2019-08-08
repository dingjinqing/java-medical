package com.vpu.mp.service.pojo.shop.market.form;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author liufei
 * @date 2019/8/8
 * @description
 */
@Data
public class FormFeedVo {
    private Integer userId;
    private Integer pageId;
    private Integer submitId;
    private String nickName;
    private String mobile;
    private Timestamp createTime;
}
