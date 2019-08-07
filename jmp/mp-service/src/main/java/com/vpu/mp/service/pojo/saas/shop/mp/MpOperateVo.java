package com.vpu.mp.service.pojo.saas.shop.mp;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author 李晓冰
 * @date 2019年08月07日
 */
@Data
public class MpOperateVo {

    private String templateId;

    private Timestamp createTime;

    private String appId;

    private String nickName;

    private String memo;

    private String userVersion;
}
