package com.vpu.mp.service.pojo.shop.overview.useranalysis;

import lombok.Data;

import java.util.List;

/**
 * 用户活跃最终出参
 * @author liangchen
 * @date 2019年7月18日
 */
@Data
public class OverviewUserAnalysisActiveTotalVo {
    /** 每日数据 */
    List<OverviewUserAnalysisActiveVo> activeVo;
    /** 访问会员数 */
	private Integer loginDataTotal;
    /** 领券会员数 */
	private Integer couponDataTotal;
    /** 加购会员数 */
	private Integer cartDataTotal;
    /** 成交会员数 */
	private Integer orderUserDataTotal;
    /** 累计用户数 */
	private Integer userDataTotal;
	/** 访问会员数占比 */
    private Double loginDataRate;
    /** 领券会员数占比 */
    private Double couponDataRate;
    /** 加购会员数占比 */
    private Double cartDataRate;
    /** 成交会员数占比 */
    private Double orderUserDataRate;
}
