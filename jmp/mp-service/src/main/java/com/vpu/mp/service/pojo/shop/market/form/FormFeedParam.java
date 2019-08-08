package com.vpu.mp.service.pojo.shop.market.form;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.sql.Date;

/**
 * @author liufei
 * @date 2019/8/8
 * @description
 */
@Data
public class FormFeedParam {
    @NotNull
    private Integer pageId;
    @NotNull
    private Integer shopId;
    private String nickName;
    private Date startTime;
    private Date endTime;
    private Integer currentPage;
    private Integer pageRows;
}
