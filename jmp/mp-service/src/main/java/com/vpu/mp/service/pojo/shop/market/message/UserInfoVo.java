package com.vpu.mp.service.pojo.shop.market.message;

import lombok.Data;

@Data
public class UserInfoVo extends UserInfoByRedis {

    private String username;

    private String mobile;

    private Byte subscribe;
}
