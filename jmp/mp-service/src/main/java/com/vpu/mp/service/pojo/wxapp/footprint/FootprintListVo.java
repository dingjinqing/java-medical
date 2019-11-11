package com.vpu.mp.service.pojo.wxapp.footprint;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 我的足迹列表
 * @author 孔德成
 * @date 2019/11/4 14:50
 */
@Getter
@Setter
public class FootprintListVo {

    List<FootprintDayVo> footprintDay;

    /**
     * 	分页信息
     */
    private Integer currentPage;
    private Integer pageRows;

}
