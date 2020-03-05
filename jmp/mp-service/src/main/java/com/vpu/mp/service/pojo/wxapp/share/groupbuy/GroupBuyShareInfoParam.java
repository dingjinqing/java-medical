package com.vpu.mp.service.pojo.wxapp.share.groupbuy;

import com.vpu.mp.service.pojo.wxapp.share.GoodsShareBaseParam;
import lombok.Getter;
import lombok.Setter;

/**
 * 拼团活动-分享小程序-入参
 * @author 李晓冰
 * @date 2019年12月27日
 */
@Getter
@Setter
public class GroupBuyShareInfoParam extends GoodsShareBaseParam {
    private Byte pageType;
}
