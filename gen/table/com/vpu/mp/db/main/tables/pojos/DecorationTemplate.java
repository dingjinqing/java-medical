/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.main.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DecorationTemplate implements Serializable {

    private static final long serialVersionUID = -959585804;

    private Integer   pageId;
    private String    pageName;
    private Byte      pageEnabled;
    private String    pageContent;
    private Timestamp createTime;
    private String    pageImg;

    public DecorationTemplate() {}

    public DecorationTemplate(DecorationTemplate value) {
        this.pageId = value.pageId;
        this.pageName = value.pageName;
        this.pageEnabled = value.pageEnabled;
        this.pageContent = value.pageContent;
        this.createTime = value.createTime;
        this.pageImg = value.pageImg;
    }

    public DecorationTemplate(
        Integer   pageId,
        String    pageName,
        Byte      pageEnabled,
        String    pageContent,
        Timestamp createTime,
        String    pageImg
    ) {
        this.pageId = pageId;
        this.pageName = pageName;
        this.pageEnabled = pageEnabled;
        this.pageContent = pageContent;
        this.createTime = createTime;
        this.pageImg = pageImg;
    }

    public Integer getPageId() {
        return this.pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    public String getPageName() {
        return this.pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public Byte getPageEnabled() {
        return this.pageEnabled;
    }

    public void setPageEnabled(Byte pageEnabled) {
        this.pageEnabled = pageEnabled;
    }

    public String getPageContent() {
        return this.pageContent;
    }

    public void setPageContent(String pageContent) {
        this.pageContent = pageContent;
    }

    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getPageImg() {
        return this.pageImg;
    }

    public void setPageImg(String pageImg) {
        this.pageImg = pageImg;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("DecorationTemplate (");

        sb.append(pageId);
        sb.append(", ").append(pageName);
        sb.append(", ").append(pageEnabled);
        sb.append(", ").append(pageContent);
        sb.append(", ").append(createTime);
        sb.append(", ").append(pageImg);

        sb.append(")");
        return sb.toString();
    }
}
