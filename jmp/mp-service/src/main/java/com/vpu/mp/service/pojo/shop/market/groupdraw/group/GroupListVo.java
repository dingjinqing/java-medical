package com.vpu.mp.service.pojo.shop.market.groupdraw.group;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 成团明细出参
 *
 * @author 郑保乐
 */
@Data
public class GroupListVo {

    private Integer groupId;
    private Integer userCount;
    private String goodsImg;
    private String goodsName;
    /** 开团时间 **/
    private LocalDateTime openTime;
    /** 成团时间 **/
    private LocalDateTime endTime;
    /** 团长昵称 **/
    private String grouperName;
    /** 团长手机号 **/
    private String mobile;
}
