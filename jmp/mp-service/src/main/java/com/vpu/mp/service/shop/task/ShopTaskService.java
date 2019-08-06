package com.vpu.mp.service.shop.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.service.shop.task.wechat.WechatTaskService;

@Service
public class ShopTaskService {

    @Autowired
    public WechatTaskService wechatTaskService;
}

