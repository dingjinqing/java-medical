package com.vpu.mp.service.pojo.wxapp.store;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * The type Location.
 *
 * @author liufei
 * @date 10 /16/19
 */
@Data
public class Location {
    /**
     * The Latitude.维度
     */
    @NotNull
    public Double latitude;
    /**
     * The Longitude.经度
     */
    @NotNull
    public Double longitude;

    /* todo 预留字段,暂时未使用
    public String speed;
    public String accuracy;
    public String verticalAccuracy;
    public String horizontalAccuracy;
    public String errMsg;*/
}
