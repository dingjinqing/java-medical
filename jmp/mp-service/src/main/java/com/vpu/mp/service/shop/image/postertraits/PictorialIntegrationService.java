package com.vpu.mp.service.shop.image.postertraits;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.wxapp.share.GoodsShareInfo;
import com.vpu.mp.service.pojo.wxapp.share.bargain.BargainShareInfoParam;
import com.vpu.mp.service.pojo.wxapp.share.groupbuy.GroupBuyShareInfoParam;
import com.vpu.mp.service.pojo.wxapp.share.groupbuy.GroupBuyShareInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 画报整合类，整合了商品和活动的图片生成service类，便于调用
 * @author 李晓冰
 * @date 2019年12月31日
 */
@Service
public class PictorialIntegrationService extends ShopBaseService {
    @Autowired
    private GroupBuyPictorialService groupBuyPictorialService;

    @Autowired
    private BargainPictorialService bargainPictorialService;

    /**
     * 拼团分享信息生成
     * @param param 拼团活动信息
     * @return 拼团活动分享信息
     */
    public GroupBuyShareInfoVo getGroupBuyShareInfo(GroupBuyShareInfoParam param){
        return groupBuyPictorialService.getGroupBuyShareInfo(param);
    }

    /**
     * 拼团还报下载
     * @param param 拼团活动信息
     * @return base64图片信息
     */
    public String getGroupBuyPictorialInfo(GroupBuyShareInfoParam param){
        return groupBuyPictorialService.getGroupBuyPictorialInfo(param);
    }

    /**
     * 砍价分享信息生成
     * @param param 砍价活动信息
     * @return 砍价活动分享信息
     */
    public GoodsShareInfo getBargainShareInfo(BargainShareInfoParam param){
        return bargainPictorialService.getBargainShareInfo(param);
    }

    public void getBargainPictorialInfo(BargainShareInfoParam param) {

    }
}
