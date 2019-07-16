package com.vpu.mp.service.pojo.shop.member;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 黄壮壮
 * 2019-07-16 19:11
 */
@Getter
@Setter
public class ScoreCfgVo {
	
	public String scoreLimit;
	public String scoreProtect;
	public String scoreDay;
	public String scoreMonth;
	public String scoreYear;
	public String scoreType;
	public String scoreLogin;
	public String storeScore;
	public String shoppingScore;
	public String loginScore;
	public String scoreLimitNumber;
	public String scorePeriod;
	public String scorePayLimit;
	public String scorePayNum;
	

	public ArrayList<String> buy = new ArrayList<>();
	public ArrayList<String> buyScore = new ArrayList<>();
	public ArrayList<String> buyEach = new ArrayList<>();
	public ArrayList<String> buyEachScore = new ArrayList<>();
	
	public static String[] getFields() {
		return new String[] {"score_limit", "score_protect", "score_day", "score_month", "score_year", "score_type",
	            "score_login","store_score","shopping_score","login_score", "score_limit_number", "score_period",
	            "score_pay_limit", "score_pay_num"};
	}
}
