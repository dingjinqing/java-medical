package com.vpu.mp.service.pojo.shop.summary;

/**
 * 平均访问深度 key 对应关系
 *
 * @author 郑保乐
 */
public enum AccessDepth {

    L1(1, "1 页"),
    L2(2, "2 页"),
    L3(3, "3 页"),
    L4(4, "4 页"),
    L5(5, "5 页"),
    L6(6, "6-10 页"),
    L7(7, ">10 页");

    AccessDepth(Integer index, String duration) { }
}
