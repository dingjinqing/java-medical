package com.vpu.mp.service.shop.image.postertraits;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author zhaojianqiang
 * 2019年10月17日 下午5:19:04
 */
@Service
public class PictorialService extends ShopBaseService {

    @Autowired
    public GroupBuyPictorialService groupBuyPictorialService;

    /**
     * 拼团还报下载
     * @return
     */
    public String getGroupBuyShareBase64Pictorial(){
//        return groupBuyPictorialService.getGroupBuyShareBase64Pictorial();
        // TODO:拼团海报生成
        return null;
    }

    /**
     * 拼团分享信息生成
     */
    public void getGroupBuyShareInfo(){
        // TODO:拼团分享信息生成
    }
}
