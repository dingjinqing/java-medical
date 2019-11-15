package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsListMpBo;

import java.util.List;

/**
 * @author 李晓冰
 * @date 2019年10月29日
 */
public interface ActivityGoodsListProcessor{

    /**
     * 商品列表处理函数
     * @param capsules 待处理商品对象 {@link com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsListMpBo}
     * @param userId 用id
     */
    void processForList(List<GoodsListMpBo> capsules, Integer userId);
}
