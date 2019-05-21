/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.pojos;


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
public class DecorateLink implements Serializable {

    private static final long serialVersionUID = 786349186;

    private Integer   id;
    private Integer   shopId;
    private Byte      linkAction;
    private String    title;
    private String    pathName;
    private String    linkPath;
    private String    appid;
    private Timestamp addTime;
    private Timestamp upTime;
    private Byte      delFlag;
    private Integer   delTime;

    public DecorateLink() {}

    public DecorateLink(DecorateLink value) {
        this.id = value.id;
        this.shopId = value.shopId;
        this.linkAction = value.linkAction;
        this.title = value.title;
        this.pathName = value.pathName;
        this.linkPath = value.linkPath;
        this.appid = value.appid;
        this.addTime = value.addTime;
        this.upTime = value.upTime;
        this.delFlag = value.delFlag;
        this.delTime = value.delTime;
    }

    public DecorateLink(
        Integer   id,
        Integer   shopId,
        Byte      linkAction,
        String    title,
        String    pathName,
        String    linkPath,
        String    appid,
        Timestamp addTime,
        Timestamp upTime,
        Byte      delFlag,
        Integer   delTime
    ) {
        this.id = id;
        this.shopId = shopId;
        this.linkAction = linkAction;
        this.title = title;
        this.pathName = pathName;
        this.linkPath = linkPath;
        this.appid = appid;
        this.addTime = addTime;
        this.upTime = upTime;
        this.delFlag = delFlag;
        this.delTime = delTime;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getShopId() {
        return this.shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Byte getLinkAction() {
        return this.linkAction;
    }

    public void setLinkAction(Byte linkAction) {
        this.linkAction = linkAction;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPathName() {
        return this.pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public String getLinkPath() {
        return this.linkPath;
    }

    public void setLinkPath(String linkPath) {
        this.linkPath = linkPath;
    }

    public String getAppid() {
        return this.appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public Timestamp getAddTime() {
        return this.addTime;
    }

    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }

    public Timestamp getUpTime() {
        return this.upTime;
    }

    public void setUpTime(Timestamp upTime) {
        this.upTime = upTime;
    }

    public Byte getDelFlag() {
        return this.delFlag;
    }

    public void setDelFlag(Byte delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getDelTime() {
        return this.delTime;
    }

    public void setDelTime(Integer delTime) {
        this.delTime = delTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("DecorateLink (");

        sb.append(id);
        sb.append(", ").append(shopId);
        sb.append(", ").append(linkAction);
        sb.append(", ").append(title);
        sb.append(", ").append(pathName);
        sb.append(", ").append(linkPath);
        sb.append(", ").append(appid);
        sb.append(", ").append(addTime);
        sb.append(", ").append(upTime);
        sb.append(", ").append(delFlag);
        sb.append(", ").append(delTime);

        sb.append(")");
        return sb.toString();
    }
}
