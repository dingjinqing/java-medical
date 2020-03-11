package com.vpu.mp.service.saas.index.param;

import java.sql.Timestamp;

public class ShopViewParam {

    private Timestamp startTime;

    private Timestamp endTime;

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }
}
