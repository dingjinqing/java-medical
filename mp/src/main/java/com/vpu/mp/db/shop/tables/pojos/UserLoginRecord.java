/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.types.ULong;


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
public class UserLoginRecord implements Serializable {

    private static final long serialVersionUID = 2116745162;

    private ULong     id;
    private Integer   userId;
    private Timestamp addTime;
    private String    userIp;
    private Integer   count;
    private Timestamp updateTime;
    private String    provinceCode;
    private String    province;
    private String    cityCode;
    private String    city;
    private String    districtCode;
    private String    district;
    private String    lat;
    private String    lng;

    public UserLoginRecord() {}

    public UserLoginRecord(UserLoginRecord value) {
        this.id = value.id;
        this.userId = value.userId;
        this.addTime = value.addTime;
        this.userIp = value.userIp;
        this.count = value.count;
        this.updateTime = value.updateTime;
        this.provinceCode = value.provinceCode;
        this.province = value.province;
        this.cityCode = value.cityCode;
        this.city = value.city;
        this.districtCode = value.districtCode;
        this.district = value.district;
        this.lat = value.lat;
        this.lng = value.lng;
    }

    public UserLoginRecord(
        ULong     id,
        Integer   userId,
        Timestamp addTime,
        String    userIp,
        Integer   count,
        Timestamp updateTime,
        String    provinceCode,
        String    province,
        String    cityCode,
        String    city,
        String    districtCode,
        String    district,
        String    lat,
        String    lng
    ) {
        this.id = id;
        this.userId = userId;
        this.addTime = addTime;
        this.userIp = userIp;
        this.count = count;
        this.updateTime = updateTime;
        this.provinceCode = provinceCode;
        this.province = province;
        this.cityCode = cityCode;
        this.city = city;
        this.districtCode = districtCode;
        this.district = district;
        this.lat = lat;
        this.lng = lng;
    }

    public ULong getId() {
        return this.id;
    }

    public void setId(ULong id) {
        this.id = id;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Timestamp getAddTime() {
        return this.addTime;
    }

    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }

    public String getUserIp() {
        return this.userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public Integer getCount() {
        return this.count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Timestamp getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getProvinceCode() {
        return this.provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvince() {
        return this.province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCityCode() {
        return this.cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrictCode() {
        return this.districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getDistrict() {
        return this.district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getLat() {
        return this.lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return this.lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("UserLoginRecord (");

        sb.append(id);
        sb.append(", ").append(userId);
        sb.append(", ").append(addTime);
        sb.append(", ").append(userIp);
        sb.append(", ").append(count);
        sb.append(", ").append(updateTime);
        sb.append(", ").append(provinceCode);
        sb.append(", ").append(province);
        sb.append(", ").append(cityCode);
        sb.append(", ").append(city);
        sb.append(", ").append(districtCode);
        sb.append(", ").append(district);
        sb.append(", ").append(lat);
        sb.append(", ").append(lng);

        sb.append(")");
        return sb.toString();
    }
}
