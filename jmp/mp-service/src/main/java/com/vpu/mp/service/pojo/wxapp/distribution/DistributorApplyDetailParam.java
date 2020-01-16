package com.vpu.mp.service.pojo.wxapp.distribution;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Auther 常乐
 * @Date 2019-12-10
 */
@Data
public class DistributorApplyDetailParam {
    public Integer id;
    /**
     * 用户ID
     */
    public Integer userId;
    /**
     * 审核状态 0：审核中；1：通过；2：拒绝
     */
    public Integer status;
    /**
     * 个人信息
     */
    public String activationFields;

    @Data
    public static class InfoField{
        /**
         * 真实姓名
         */
        @JsonProperty(value = "real_name")
        public String realName;
        /**
         * 手机号
         */
        @JsonProperty(value = "mobile")
        public String mobile;
        /**
         * 身份证号
         */
        @JsonProperty(value = "cid")
        public String cid;
        /**
         * 地址
         */
        @JsonProperty(value = "address")
        public String address;
        /**
         *性别
         */
        @JsonProperty(value = "sex")
        public String sex;
        /**
         *出生日
         */
        @JsonProperty(value = "birthday_day")
        public String birthdayDay;
        /**
         * 出生月
         */
        @JsonProperty(value = "birthday_month")
        public String birthdayMonth;
        /**
         * 出生年
         */
        @JsonProperty(value = "birthday_year")
        public String birthdayYear;
        /**
         * 省
         */
        @JsonProperty(value = "province_code")
        public Integer provinceCode;
        /**
         * 城市
         */
        @JsonProperty(value = "city_code")
        public Integer cityCode;
        /**
         * 区
         */
        @JsonProperty(value = "district_code")
        public Integer districtCode;
        /**
         * 婚姻状况
         */
        @JsonProperty(value = "marital_status")
        public Integer maritalStatus;
        /**
         * 教育程度
         */
        @JsonProperty(value = "education")
        public Integer education;
        /**
         * 所属行业
         */
        @JsonProperty(value = "industry_info")
        public Integer industryInfo;
        /**
         * 分销分组
         */
        @JsonProperty(value = "rebate_group")
        public Integer rebateGroup;
        /**
         * 备注
         */
        @JsonProperty(value = "remarks")
        public String remarks;
        /**
         * 上传图片
         */
        @JsonProperty(value = "upload_image")
        public String uploadImage;
        /**
         * 邀请码
         */
        @JsonProperty(value = "invitation_code")
        public String invitationCode;
        /**
         * 自定义激活项
         */
        @JsonProperty(value = "custom_options")
        public String customOptions;

    }
}
