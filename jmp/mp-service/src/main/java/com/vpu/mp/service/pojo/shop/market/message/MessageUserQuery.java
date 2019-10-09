package com.vpu.mp.service.pojo.shop.market.message;

import java.util.List;

import com.vpu.mp.service.pojo.shop.base.BasePageParam;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageUserQuery extends BasePageParam {

    private String userKey;

    private Integer id;

    private String userName;

    private String phone;

    private Byte isVisit;

    private List<Integer> userIds;
}
