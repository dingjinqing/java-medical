package com.vpu.mp.service.shop.task;

import com.vpu.mp.db.shop.tables.ReducePrice;
import com.vpu.mp.service.shop.task.market.ReducePriceTaskService;
import com.vpu.mp.service.shop.task.market.SeckillTaskService;
import com.vpu.mp.service.shop.task.overview.GoodsTaskService;
import com.vpu.mp.service.shop.task.wechat.WechatTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopTaskService {

    @Autowired
    public WechatTaskService wechatTaskService;

    @Autowired
    public SeckillTaskService seckillTaskService;

    @Autowired
    public ReducePriceTaskService reducePriceTaskService;

    /**
     * The Goods task service.概况模块-商品统计
     */
    @Autowired
    public GoodsTaskService goodsTaskService;
}

