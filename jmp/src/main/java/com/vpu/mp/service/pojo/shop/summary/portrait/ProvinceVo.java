package com.vpu.mp.service.pojo.shop.summary.portrait;

import lombok.Data;

import java.util.List;

/**
 * 按省份统计出参
 *
 * @author 郑保乐
 */
@Data
public class ProvinceVo {

    private List<PortraitItem> list;
    private String sum;

    public void setList(List<PortraitItem> list) {
        this.list = list;
        setSum(String.valueOf(list.parallelStream().mapToInt(PortraitItem::getValue).sum()));
    }
}
