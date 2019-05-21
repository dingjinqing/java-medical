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
public class MpDailyRetain implements Serializable {

    private static final long serialVersionUID = -1336550434;

    private String    refDate;
    private String    visitUvNew;
    private String    visitUv;
    private Timestamp addTime;

    public MpDailyRetain() {}

    public MpDailyRetain(MpDailyRetain value) {
        this.refDate = value.refDate;
        this.visitUvNew = value.visitUvNew;
        this.visitUv = value.visitUv;
        this.addTime = value.addTime;
    }

    public MpDailyRetain(
        String    refDate,
        String    visitUvNew,
        String    visitUv,
        Timestamp addTime
    ) {
        this.refDate = refDate;
        this.visitUvNew = visitUvNew;
        this.visitUv = visitUv;
        this.addTime = addTime;
    }

    public String getRefDate() {
        return this.refDate;
    }

    public void setRefDate(String refDate) {
        this.refDate = refDate;
    }

    public String getVisitUvNew() {
        return this.visitUvNew;
    }

    public void setVisitUvNew(String visitUvNew) {
        this.visitUvNew = visitUvNew;
    }

    public String getVisitUv() {
        return this.visitUv;
    }

    public void setVisitUv(String visitUv) {
        this.visitUv = visitUv;
    }

    public Timestamp getAddTime() {
        return this.addTime;
    }

    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("MpDailyRetain (");

        sb.append(refDate);
        sb.append(", ").append(visitUvNew);
        sb.append(", ").append(visitUv);
        sb.append(", ").append(addTime);

        sb.append(")");
        return sb.toString();
    }
}
