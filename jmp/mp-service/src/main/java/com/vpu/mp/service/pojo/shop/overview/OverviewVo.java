package com.vpu.mp.service.pojo.shop.overview;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author:liufei
 * @Date:2019/7/18
 * @Description: 商城概览综合出参
 */
@Data
@Component
public class OverviewVo {
    @Autowired
    private ShopBaseInfoVo shopBaseInfoVo;
    @Autowired
    private List<FixedAnnouncementVo> announcementVoList;
    @Autowired
    private DataDemonstrationVo dataDemonstrationVo;
    @Autowired
    private ToDoItemVo toDoItemVo;
    @Autowired
    private ShopAssistantVo shopAssistantVo;
}
