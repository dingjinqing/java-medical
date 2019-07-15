package com.vpu.mp.service.pojo.shop.summary;

import lombok.Data;

import java.util.List;

/**
 * 留存统计出参
 *
 * @author 郑保乐
 */
@Data
public class AccessRetainVo {

    /**
     * 新增留存
     */
    public static final int ACTION_NEW_RETAIN = 1;

    /**
     * 活跃留存
     */
    public static final int ACTION_RETAIN = 2;

    private List<AccessRetain> data;
}
