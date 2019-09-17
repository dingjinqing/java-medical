package com.vpu.mp.service.pojo.shop.market.message;

import com.vpu.mp.service.pojo.shop.base.BasePageParam;
import lombok.Data;

import java.util.List;

@Data
public class MessageUserQuery extends BasePageParam {

    private String userKey;

    private Integer id;

    private String userName;

    private String phone;

    private Byte isVisit;

    private List<Integer> userIds;
}
