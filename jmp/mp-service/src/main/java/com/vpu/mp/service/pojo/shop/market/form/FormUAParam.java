package com.vpu.mp.service.pojo.shop.market.form;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author liufei
 * @date 2019/8/7
 * @description 表单添加/更新实体类
 */
@Data
public class FormUAParam {
    private Integer pageId;
    @NotNull
    private Integer shopId;
    @NotNull
    private String pageName;
    @NotNull
    private Byte status;
    private String pageContent;
    private String formCfg;
    private Timestamp startTime;
    private Timestamp endTime;
    /**有效期*/
    @NotNull
    private Byte validityPeriod;
    /**反馈数量*/
    @NotNull
    private Integer submitNum;
    /**Form_List表数据参数*/
    private FormList formList;
    /**Form_detail表数据参数*/
    private List<FormDetail> details;
}
