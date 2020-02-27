package com.vpu.mp.service.pojo.shop.market.friendpromote;

import lombok.Data;

/**
 * 是否可以助力
 * @author liangchen
 * @date 2020.02.27
 */
@Data
public class CanPromote {
    /** 是否可 0否 1是 */
    private Byte code;
    /** 文字信息 */
    private String msg;
}
