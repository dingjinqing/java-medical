package com.vpu.mp.service.pojo.shop.overview;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author:liufei
 * @Date:2019/7/18
 * @Description:
 */
@Data
@Component
public class OverviewParam {
    @Autowired
    private ShopBaseInfoParam shopBaseInfoParam;
    @Autowired
    private FixedAnnouncementParam fixedAnnouncementParam;
    @Autowired
    private DataDemonstrationParam dataDemonstrationParam;
    @Autowired
    private ShopAssistantParam shopAssistantParam;
}
