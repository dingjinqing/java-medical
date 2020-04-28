package com.vpu.mp.service.pojo.shop.market.form.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

/**
 * 表单pojo
 * @author 孔德成
 * @date 2020/4/28
 */
@Getter
@Setter
@ToString
public class FormInfoBo {

    private Integer pageId;
    private String pageName;
    private Timestamp startTime;
    private Timestamp endTime;
    private Byte validityPeriod;
    private Byte isForeverValid;
    /**
     * 提交数量
     */
    private Integer submitNum;
    /**
     * 状态：0未发布，1已发布 2已关闭 3 已删除
     */
    private Byte status;

    /**
     * json  页面内容
     */
    private String pageContent;
    /**
     * json  表单配置
     */
    private String formCfg;
    /**
     * 表单配置
     */
    private FormCfgBo formCfgBo;
    /**
     * 页面内容
     */
    private FormPageContentBo pageContentBo;


}
