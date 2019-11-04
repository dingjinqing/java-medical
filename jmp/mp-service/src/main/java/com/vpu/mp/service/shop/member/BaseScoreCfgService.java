package com.vpu.mp.service.shop.member;

import com.vpu.mp.service.shop.config.BaseShopConfigService;

/**
* @author 黄壮壮
* @Date: 2019年10月25日
* @Description: 积分配置
*/
public class BaseScoreCfgService extends BaseShopConfigService {
	
	// 积分有效期  0: 永久积分
	final public static String SCORE_LT_FOREVER = "0";
	// 积分有效期  1: 从获得开始至 年-月-日
	final public static String SCORE_LT_YMD = "1";
	// 2: 从获得积分当天起-内有效
	final public static String SCORE_LT_NOW = "2";
	// 积分有效期  0: 永久积分; 1: 从获得开始至 年-月-日; 2: 从获得积分当天起-内有效
	final public static String SCORE_LIMIT = "score_limit";
	// 积分有效期-从获得开始至 ：日
	final public static String SCORE_DAY = "score_day";
	// 积分有效期-从获得开始至 ：月
	final public static String SCORE_MONTH = "score_month";
	// 积分有效期-从获得开始至 ：年
	final public static String SCORE_YEAR = "score_year";
	// 积分有效期-从获得积分当天起 多少数额
	final public static String SCORE_LIMIT_NUMBER = "score_limit_number";
	// 积分有效期-从获得积分当天起  数额单位 
	final public static String SCORE_PERIOD = "score_period";
	// 积分支付限制 0： 不限制；1：自定义
	final public static String SCORE_PAY_LIMIT = "score_pay_limit";
	// 每单支付的积分数量少于 score_pay_num 积分，不可使用积分支付
	final public static String SCORE_PAY_NUM = "score_pay_num";
	// 购物送积分开关： 0： 关闭； 1： 开启
	final public static String SHOPPING_SCORE ="shopping_score";
	// 购物送积分类型： 0： 购物满；1：购物每满
	final public static String SCORE_TYPE = "score_type";
	// 门店买单送积分： 0： 关闭； 1： 开启
	final public static String STORE_SCORE = "store_score";
	// 登录送积分： 0： 关闭；1： 开启
	final public static String LOGIN_SCORE = "login_score";
	// 登录送 score_login 积分
	final public static String SCORE_LOGIN = "score_login";
	// 签到送积分： json字符数据，包括开关和数据
	final public static String SIGN_IN_SCORE = "sign_in_score";
	// 模板页面id
	final public static String SCORE_PAGE_ID = "score_page_id";
	// 积分说明
	final public static String SCORE_DOCUMENT = "score_document";
	
	
	
	//-------------------------------------------------------
	
	public void setScoreLimit(String value){
		set(SCORE_LIMIT, value);
	}
	
	public String getScoreLimit(){
		return get(SCORE_LIMIT);
	}
	
	public void setScoreDay(String value){
		set(SCORE_DAY, value);
	}
	public String getScoreDay(){
		return get(SCORE_DAY);
	}
	
	public void setScoreMonth(String value){
		set(SCORE_MONTH, value);
	}
	public String getScoreMonth(){
		return get(SCORE_MONTH);
	}
	
	public void setScoreYear(String value){
		set(SCORE_YEAR, value);
	}
	public String getScoreYear(){
		return get(SCORE_YEAR);
	}
	
	public void setScoreLimitNumber(String value){
		set(SCORE_LIMIT_NUMBER, value);
	}
	public String getScoreLimitNumber(){
		return get(SCORE_LIMIT_NUMBER);
	}
	
	public void setScorePeriod(String value){
		set(SCORE_PERIOD, value);
	}
	public String getScorePeriod(){
		return get(SCORE_PERIOD);
	}
	
	public void setScorePayLimit(String value){
		set(SCORE_PAY_LIMIT, value);
	}
	public String getScorePayLimit(){
		return get(SCORE_PAY_LIMIT);
	}

	public void setScorePayNum(String value){
		set(SCORE_PAY_NUM, value);
	}
	public String getScorePayNum(){
		return get(SCORE_PAY_NUM);
	}
	
	public void setShoppingScore(String value){
		set(SHOPPING_SCORE, value);
	}
	public String getShoppingScore(){
		return get(SHOPPING_SCORE);
	}
	
	public void setScoreType(String value){
		set(SCORE_TYPE, value);
	}
	public String getScoreType(){
		return get(SCORE_TYPE);
	}
	
	public void setStoreScore(String value){
		set(STORE_SCORE, value);
	}
	public String getStoreScore(){
		return get(STORE_SCORE);
	}
	
	public void setLoginScore(String value){
		set(LOGIN_SCORE, value);
	}
	public String getLoginScore(){
		return get(LOGIN_SCORE);
	}
	
	public void setScoreLogin(String value){
		set(SCORE_LOGIN, value);
	}
	public String getScoreLogin(){
		return get(SCORE_LOGIN);
	}
	
	public void setSignInScore(String value){
		set(SIGN_IN_SCORE, value);
	}
	public String getSignInScore(){
		return get(SIGN_IN_SCORE);
	}
	
	public void setScorePageId(String value){
		set(SCORE_PAGE_ID, value);
	}
	public String getScorePageId(){
		return get(SCORE_PAGE_ID);
	}
	
	public void setScoreDocument(String value){
		set(SCORE_DOCUMENT, value);
	}
	public String getScoreDocument(){
		return get(SCORE_DOCUMENT);
	}
	
}
