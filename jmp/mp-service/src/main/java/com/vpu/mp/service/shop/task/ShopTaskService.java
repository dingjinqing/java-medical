package com.vpu.mp.service.shop.task;

import com.vpu.mp.service.shop.task.market.SeckillTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.service.shop.task.wechat.WechatTaskService;

@Service
public class ShopTaskService {

    @Autowired
    public WechatTaskService wechatTaskService;

    @Autowired
    public SeckillTaskService seckillTaskService;
}

