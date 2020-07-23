package com.vpu.mp.service.pojo.wxapp.medical.im;

import java.util.List;

/**
 * @author 李晓冰
 * @date 2020年07月23日
 */
public class SessionTest{
    private Integer type;
    private List<String> orderSns;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<String> getOrderSns() {
        return orderSns;
    }

    public void setOrderSns(List<String> orderSns) {
        this.orderSns = orderSns;
    }
}
