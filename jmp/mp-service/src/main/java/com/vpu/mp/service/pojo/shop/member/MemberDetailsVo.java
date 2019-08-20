package com.vpu.mp.service.pojo.shop.member;

import lombok.Getter;
import lombok.Setter;
import com.vpu.mp.service.pojo.shop.member.MemberBasicInfoVo;
/**
* @author 黄壮壮
* @Date: 2019年8月14日
* @Description: 会员用户详情信息-出参
*/
@Getter
@Setter
public class MemberDetailsVo {
	MemberBasicInfoVo memberBasicInfo;
	MemberTransactionStatisticsVo transStatistic;
	
}
