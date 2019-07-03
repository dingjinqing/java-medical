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
public class MpMonthlyVisit implements Serializable {

    private static final long serialVersionUID = -160124972;

    private String    refDate;
    private Integer   sessionCnt;
    private Integer   visitPv;
    private Integer   visitUv;
    private Integer   visitUvNew;
    private Double    stayTimeUv;
    private Double    stayTimeSession;
    private Double    visitDepth;
    private Timestamp addTime;

    public MpMonthlyVisit() {}

    public MpMonthlyVisit(MpMonthlyVisit value) {
        this.refDate = value.refDate;
        this.sessionCnt = value.sessionCnt;
        this.visitPv = value.visitPv;
        this.visitUv = value.visitUv;
        this.visitUvNew = value.visitUvNew;
        this.stayTimeUv = value.stayTimeUv;
        this.stayTimeSession = value.stayTimeSession;
        this.visitDepth = value.visitDepth;
        this.addTime = value.addTime;
    }

    public MpMonthlyVisit(
        String    refDate,
        Integer   sessionCnt,
        Integer   visitPv,
        Integer   visitUv,
        Integer   visitUvNew,
        Double    stayTimeUv,
        Double    stayTimeSession,
        Double    visitDepth,
        Timestamp addTime
    ) {
        this.refDate = refDate;
        this.sessionCnt = sessionCnt;
        this.visitPv = visitPv;
        this.visitUv = visitUv;
        this.visitUvNew = visitUvNew;
        this.stayTimeUv = stayTimeUv;
        this.stayTimeSession = stayTimeSession;
        this.visitDepth = visitDepth;
        this.addTime = addTime;
    }

    public String getRefDate() {
        return this.refDate;
    }

    public void setRefDate(String refDate) {
        this.refDate = refDate;
    }

    public Integer getSessionCnt() {
        return this.sessionCnt;
    }

    public void setSessionCnt(Integer sessionCnt) {
        this.sessionCnt = sessionCnt;
    }

    public Integer getVisitPv() {
        return this.visitPv;
    }

    public void setVisitPv(Integer visitPv) {
        this.visitPv = visitPv;
    }

    public Integer getVisitUv() {
        return this.visitUv;
    }

    public void setVisitUv(Integer visitUv) {
        this.visitUv = visitUv;
    }

    public Integer getVisitUvNew() {
        return this.visitUvNew;
    }

    public void setVisitUvNew(Integer visitUvNew) {
        this.visitUvNew = visitUvNew;
    }

    public Double getStayTimeUv() {
        return this.stayTimeUv;
    }

    public void setStayTimeUv(Double stayTimeUv) {
        this.stayTimeUv = stayTimeUv;
    }

    public Double getStayTimeSession() {
        return this.stayTimeSession;
    }

    public void setStayTimeSession(Double stayTimeSession) {
        this.stayTimeSession = stayTimeSession;
    }

    public Double getVisitDepth() {
        return this.visitDepth;
    }

    public void setVisitDepth(Double visitDepth) {
        this.visitDepth = visitDepth;
    }

    public Timestamp getAddTime() {
        return this.addTime;
    }

    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("MpMonthlyVisit (");

        sb.append(refDate);
        sb.append(", ").append(sessionCnt);
        sb.append(", ").append(visitPv);
        sb.append(", ").append(visitUv);
        sb.append(", ").append(visitUvNew);
        sb.append(", ").append(stayTimeUv);
        sb.append(", ").append(stayTimeSession);
        sb.append(", ").append(visitDepth);
        sb.append(", ").append(addTime);

        sb.append(")");
        return sb.toString();
    }
}
