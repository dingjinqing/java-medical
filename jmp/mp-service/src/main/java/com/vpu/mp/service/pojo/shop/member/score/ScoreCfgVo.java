package com.vpu.mp.service.pojo.shop.member.score;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 黄壮壮 2019-07-16 19:11
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScoreCfgVo {
	
	@JsonAlias("score_limit")
	public String scoreLimit;
	
	@JsonAlias("score_protect")
	public String scoreProtect;
	
	@JsonAlias("score_day")
	public String scoreDay;
	
	@JsonAlias("score_month")
	public String scoreMonth;
	
	@JsonAlias("score_year")
	public String scoreYear;
	
	@JsonAlias("score_type")
	public String scoreType;
	
	@JsonAlias("score_login")
	public String scoreLogin;
	
	@JsonAlias("store_score")
	public String storeScore;
	
	@JsonAlias("shopping_score")
	public String shoppingScore;
	
	@JsonAlias("login_score")
	public String loginScore;
	
	@JsonAlias("score_limit_number")
	public String scoreLimitNumber;
	
	@JsonAlias("score_period")
	public String scorePeriod;
	
	@JsonAlias("score_pay_limit")
	public String scorePayLimit;
	
	@JsonAlias("score_pay_num")
	public String scorePayNum;
	
	public ArrayList<String> buy = new ArrayList<>();
	public ArrayList<String> buyScore = new ArrayList<>();
	public ArrayList<String> buyEach = new ArrayList<>();
	public ArrayList<String> buyEachScore = new ArrayList<>();
	
	// 签到积分开关
	public String signInScore;
	// 签到积分数据 如第一天签到送多少积分
	public String[] signScore;

}
