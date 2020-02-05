package com.vpu.mp.service.pojo.wxapp.distribution;

import com.vpu.mp.service.pojo.shop.config.distribution.DistributionParam;
import com.vpu.mp.service.pojo.shop.member.data.EducationVo;
import com.vpu.mp.service.pojo.shop.member.data.IndustryVo;
import com.vpu.mp.service.pojo.shop.member.data.MarriageData;
import lombok.Data;

import java.util.List;

/**
 * @Author 常乐
 * @Date 2020-01-07
 * 提交申请页面数据出参
 */
@Data
public class ActivationInfoVo {
    private UserBaseInfoVo userBaseInfo;
    private List<IndustryVo> industryList;
    private List<EducationVo> educationList;
    private  List<MarriageData> MarriageData;
    private DistributionParam cfg;
}
