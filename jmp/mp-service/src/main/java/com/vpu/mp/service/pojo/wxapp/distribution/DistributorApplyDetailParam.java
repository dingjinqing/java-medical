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

    }
}
