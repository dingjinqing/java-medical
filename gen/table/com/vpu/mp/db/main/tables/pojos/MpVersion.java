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
public class MpVersion implements Serializable {

    private static final long serialVersionUID = 2147428552;

    private Integer   templateId;
    private String    userVersion;
    private String    userDesc;
    private Timestamp createTime;
    private Byte      delFlag;
    private Byte      currentInUse;
    private String    sourceMiniprogramAppid;
    private String    sourceMiniprogram;
    private String    developer;
    private Byte      packageVersion;

    public MpVersion() {}

    public MpVersion(MpVersion value) {
        this.templateId = value.templateId;
        this.userVersion = value.userVersion;
        this.userDesc = value.userDesc;
        this.createTime = value.createTime;
        this.delFlag = value.delFlag;
        this.currentInUse = value.currentInUse;
        this.sourceMiniprogramAppid = value.sourceMiniprogramAppid;
        this.sourceMiniprogram = value.sourceMiniprogram;
        this.developer = value.developer;
        this.packageVersion = value.packageVersion;
    }

    public MpVersion(
        Integer   templateId,
        String    userVersion,
        String    userDesc,
        Timestamp createTime,
        Byte      delFlag,
        Byte      currentInUse,
        String    sourceMiniprogramAppid,
        String    sourceMiniprogram,
        String    developer,
        Byte      packageVersion
    ) {
        this.templateId = templateId;
        this.userVersion = userVersion;
        this.userDesc = userDesc;
        this.createTime = createTime;
        this.delFlag = delFlag;
        this.currentInUse = currentInUse;
        this.sourceMiniprogramAppid = sourceMiniprogramAppid;
        this.sourceMiniprogram = sourceMiniprogram;
        this.developer = developer;
        this.packageVersion = packageVersion;
    }

    public Integer getTemplateId() {
        return this.templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public String getUserVersion() {
        return this.userVersion;
    }

    public void setUserVersion(String userVersion) {
        this.userVersion = userVersion;
    }

    public String getUserDesc() {
        return this.userDesc;
    }

    public void setUserDesc(String userDesc) {
        this.userDesc = userDesc;
    }

    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Byte getDelFlag() {
        return this.delFlag;
    }

    public void setDelFlag(Byte delFlag) {
        this.delFlag = delFlag;
    }

    public Byte getCurrentInUse() {
        return this.currentInUse;
    }

    public void setCurrentInUse(Byte currentInUse) {
        this.currentInUse = currentInUse;
    }

    public String getSourceMiniprogramAppid() {
        return this.sourceMiniprogramAppid;
    }

    public void setSourceMiniprogramAppid(String sourceMiniprogramAppid) {
        this.sourceMiniprogramAppid = sourceMiniprogramAppid;
    }

    public String getSourceMiniprogram() {
        return this.sourceMiniprogram;
    }

    public void setSourceMiniprogram(String sourceMiniprogram) {
        this.sourceMiniprogram = sourceMiniprogram;
    }

    public String getDeveloper() {
        return this.developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public Byte getPackageVersion() {
        return this.packageVersion;
    }

    public void setPackageVersion(Byte packageVersion) {
        this.packageVersion = packageVersion;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("MpVersion (");

        sb.append(templateId);
        sb.append(", ").append(userVersion);
        sb.append(", ").append(userDesc);
        sb.append(", ").append(createTime);
        sb.append(", ").append(delFlag);
        sb.append(", ").append(currentInUse);
        sb.append(", ").append(sourceMiniprogramAppid);
        sb.append(", ").append(sourceMiniprogram);
        sb.append(", ").append(developer);
        sb.append(", ").append(packageVersion);

        sb.append(")");
        return sb.toString();
    }
}
