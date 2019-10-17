package com.vpu.mp.service.pojo.wxapp.store;

import lombok.Data;

/**
 * @author liufei
 * @date 10/16/19
 */
@Data
public class Location {
    public Double latitude;
    public Double longitude;
    public String speed;
    public String accuracy;
    public String verticalAccuracy;
    public String horizontalAccuracy;
    public String errMsg;
}
