package com.vpu.mp.service.pojo.shop.summary.portrait;

import com.vpu.mp.service.pojo.shop.summary.ChartXKeyYValue;
import lombok.Data;

import java.util.List;

/**
 * 用户画像数据
 *
 * @author 郑保乐
 */
@Data
public class Portrait {

    private List<PortraitItem> ages;
    private ChartXKeyYValue agesFirst;
    private List<PortraitItem> city;
    private List<PortraitItem> devices;
    private List<PortraitItem> genders;
    private List<PortraitItem> platforms;
    private List<PortraitItem> province;
}
