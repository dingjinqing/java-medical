package com.vpu.mp.service.pojo.shop.market.coopen;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author 孔德成
 * @date 2019/11/22 15:33
 */
@Getter
@Setter
public class CoopenIssueListVo {

    private Integer userId;
    private String username;
    private String mobile;
    private String name;
    private Timestamp receiveTime;
    private String comment;

}
