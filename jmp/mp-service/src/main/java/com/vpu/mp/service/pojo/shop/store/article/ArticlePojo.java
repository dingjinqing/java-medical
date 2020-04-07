package com.vpu.mp.service.pojo.shop.store.article;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 门店公告
 * @author liangchen
 * @date 2020.03.31
 */
@Data
public class ArticlePojo {
    /** id */
    private Integer articleId;
    /** 标题 */
    private String title;
    /** 关键词 */
    private String keyword;
    /** 摘要 */
    private String desc;
    /** 内容 */
    private String content;
    /** 发布状态 */
    private Byte status;
    /** 文章所属类型 */
    private Byte partType = (byte)1;
    /** 更新时间 */
    private Timestamp updateTime;
}
