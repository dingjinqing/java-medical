package com.vpu.mp.service.shop.image.postertraits;

import com.vpu.mp.service.foundation.service.ShopBaseService;
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
    public GroupBuyPictorialService groupBuyPictorialService;

    /**
     * 拼团分享信息生成
     * @param param 拼团活动信息
     */
    public GroupBuyShareInfoVo getGroupBuyShareInfo(GroupBuyShareInfoParam param){
        return groupBuyPictorialService.getGroupBuyShareInfo(param);
    }

    /**
     * 拼团还报下载
     * @return
     */
    public String getGroupBuyShareBase64Pictorial(){
//        return groupBuyPictorialService.getGroupBuyShareBase64Pictorial();
        // TODO:拼团海报生成
        return null;
    }
}
