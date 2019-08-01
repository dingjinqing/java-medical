package com.vpu.mp.service.pojo.shop.store.store;

import java.sql.Timestamp;

import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.pojo.shop.store.validated.AddValidatedGroup;
import com.vpu.mp.service.pojo.shop.store.validated.CodingCheckValidatedGroup;
import com.vpu.mp.service.pojo.shop.store.validated.UpdateValidatedGroup;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author 王兵兵
 *
 * 2019年7月8日
 */
@Data
@NoArgsConstructor
public class StorePojo {
    @NotNull(groups = {UpdateValidatedGroup.class},message = JsonResultMessage.MSG_PARAM_ERROR)
	private Integer   storeId;
    @NotNull(groups = {AddValidatedGroup.class},message = JsonResultMessage.MSG_PARAM_ERROR)
    private String    storeName;
    @NotNull(groups = {AddValidatedGroup.class},message = JsonResultMessage.MSG_PARAM_ERROR)
    private String    manager;
    @NotNull(groups = {AddValidatedGroup.class},message = JsonResultMessage.MSG_PARAM_ERROR)
    private String    mobile;
    @NotNull(groups = {AddValidatedGroup.class},message = JsonResultMessage.MSG_PARAM_ERROR)
    private String    storeImgs;
    private Byte      businessState;
    private Byte      businessType;
    @NotNull(groups = {AddValidatedGroup.class},message = JsonResultMessage.MSG_PARAM_ERROR)
    private String    openingTime;
    @NotNull(groups = {AddValidatedGroup.class},message = JsonResultMessage.MSG_PARAM_ERROR)
    private String    closeTime;
    @NotNull(groups = {AddValidatedGroup.class},message = JsonResultMessage.MSG_PARAM_ERROR)
    private String    provinceCode;
    @NotNull(groups = {AddValidatedGroup.class},message = JsonResultMessage.MSG_PARAM_ERROR)
    private String    cityCode;
    @NotNull(groups = {AddValidatedGroup.class},message = JsonResultMessage.MSG_PARAM_ERROR)
    private String    districtCode;
    @NotNull(groups = {AddValidatedGroup.class},message = JsonResultMessage.MSG_PARAM_ERROR)
    private String    latitude;
    @NotNull(groups = {AddValidatedGroup.class},message = JsonResultMessage.MSG_PARAM_ERROR)
    private String    longitude;
    @NotNull(groups = {AddValidatedGroup.class},message = JsonResultMessage.MSG_PARAM_ERROR)
    private String    address;
    private Integer   group;
    private String    service;
    private String    content;
    @NotNull(groups = {CodingCheckValidatedGroup.class},message = JsonResultMessage.MSG_PARAM_ERROR)
    private Integer   posShopId;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Short     autoPick;
    private Byte      delFlag;
}
