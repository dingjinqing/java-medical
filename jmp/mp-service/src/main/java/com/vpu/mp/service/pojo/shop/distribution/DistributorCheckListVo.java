package com.vpu.mp.service.pojo.shop.distribution;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vpu.mp.service.pojo.shop.decoration.DistributorApplyParam;
import lombok.Data;

/**
 * 分销员审核列表出参类
 * @author 常乐
 * 2019年9月20日
 */
@Data
public class DistributorCheckListVo {
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 审核校验
     */
    private String activationFields;
    /**
     * 审核字段
     */
    private String configFields;

    private DistributorApplyParam.InfoField checkField;

    private String username;

    private String mobile;

    private Timestamp createTime;

    private Timestamp updateTime;

}
