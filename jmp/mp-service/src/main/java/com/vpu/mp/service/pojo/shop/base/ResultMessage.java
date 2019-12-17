package com.vpu.mp.service.pojo.shop.base;

import com.vpu.mp.service.foundation.data.JsonResultCode;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.List;

/**
 * 用于传递可变的消息
 *
 * @author 孔德成
 * @date 2019/10/16 16:01
 */
@Getter
@Builder(builderMethodName = "builder")
public class ResultMessage {

    @Builder.Default
    private Boolean flag = false;
    private JsonResultCode jsonResultCode;
    @Singular
    private List<Object> messages;
}
