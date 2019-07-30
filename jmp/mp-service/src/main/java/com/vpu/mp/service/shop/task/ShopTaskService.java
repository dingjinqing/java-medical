package com.vpu.mp.service.shop.task;

import com.vpu.mp.service.shop.task.wechat.WechatTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopTaskService {

    @Autowired
    public WechatTaskService wechatTaskService;
}

