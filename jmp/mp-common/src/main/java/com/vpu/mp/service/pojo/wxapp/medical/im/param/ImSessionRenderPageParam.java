package com.vpu.mp.service.pojo.wxapp.medical.im.param;

import com.vpu.mp.common.pojo.shop.base.BasePageParam;
import lombok.Getter;
import lombok.Setter;

/**
 * 聊天详情记录分页查询
 * @author 李晓冰
 * @date 2020年08月03日
 */
@Getter
@Setter
public class ImSessionRenderPageParam extends BasePageParam {
    private Integer sessionId;
    private Boolean isDoctor = false;
    private Boolean isFirstTime = false;
}
