package com.vpu.mp.service.pojo.wxapp.share.bargain;

import com.vpu.mp.service.pojo.wxapp.share.GoodsShareBaseParam;
import lombok.Getter;
import lombok.Setter;

/**
 * 砍价活动-分享小程序-入参
 * @author 李晓冰
 * @date 2020年01月03日
 */
@Getter
@Setter
public class BargainShareInfoParam extends GoodsShareBaseParam {
    /**需要跳转的页面类型*/
    private Byte pageType;
}
