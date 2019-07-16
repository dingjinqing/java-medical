package com.vpu.mp.service.pojo.shop.summary.visit;

import lombok.Data;

import java.util.List;

/**
 * 留存统计
 *
 * @author 郑保乐
 */
@Data
public class AccessRetain {

    private String refDate;
    private Integer sum;
    private List<RetainItem> list;

    public void setList(List<RetainItem> list) {
        this.list = list;
        setSum(list.stream().mapToInt(RetainItem::getValue).sum());
    }
}
