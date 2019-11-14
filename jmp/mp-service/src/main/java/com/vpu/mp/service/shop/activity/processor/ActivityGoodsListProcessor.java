package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsListMpBo;

import java.util.List;

/**
 * @author 李晓冰
 * @date 2019年10月29日
 */
public interface ActivityGoodsListProcessor{

    void processForList(List<GoodsListMpBo> capsules, Integer userId);
}
