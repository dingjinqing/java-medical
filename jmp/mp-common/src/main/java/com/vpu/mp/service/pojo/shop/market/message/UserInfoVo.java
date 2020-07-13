package com.vpu.mp.service.pojo.shop.market.message;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoVo extends UserInfoByRedis {

    private String username;

    private String mobile;

    private Byte subscribe;
}
