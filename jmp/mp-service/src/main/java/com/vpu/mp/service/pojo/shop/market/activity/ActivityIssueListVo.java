package com.vpu.mp.service.pojo.shop.market.activity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * 发放明细出参
 *
 * @author 郑保乐
 */
@Data
public class ActivityIssueListVo {

    private Integer userId;
    private String username;
    private String mobile;
    private String name;
    private Timestamp receiveTime;
    private List<Integer> voucherIds;
    private List<String> voucherNames;

    @JsonIgnore
    private String mrkingVoucherId;
}
