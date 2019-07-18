package com.vpu.mp.service.shop.member;

import static com.vpu.mp.db.shop.tables.UserScoreSet.USER_SCORE_SET;
import java.lang.reflect.Field;
import java.util.Map;

import static com.vpu.mp.db.shop.tables.ShopCfg.SHOP_CFG;

import org.jooq.InsertValuesStep3;

import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Result;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vpu.mp.db.shop.tables.records.UserScoreSetRecord;
import com.vpu.mp.service.foundation.Util;
import com.vpu.mp.service.pojo.shop.member.ScoreCfgVo;
import com.vpu.mp.service.pojo.shop.member.ShopCfgParam;
import com.vpu.mp.service.shop.config.BaseShopConfigService;
import com.vpu.mp.service.pojo.shop.member.UserScoreSetValue;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * 积分配置Service
 * 
 * @author 黄壮壮 2019-07-15 14:13
 */
@Service
@Scope("prototype")
public class ScoreCfgService extends BaseShopConfigService {

	final public static String ZERO = "0";
	final public static String ONE = "1";
	final public static String TWO = "2";
	final public static String BUTTON_ON="on";
	
	/**
	 * 购物送积分
	 */
	final public static String BUY="buy";
	final public static String BUY_EACH="buy_each";
	
	/**
	 * key
	 */
	final public static String SCORE_LIMIT = "score_limit";
	final public static String SCORE_DAY = "score_day";
	final public static String SCORE_MONTH = "score_month";
	final public static String SCORE_YEAR = "score_year";
	final public static String SCORE_LIMIT_NUMBER = "score_limit_number";
	final public static String SCORE_PERIOD = "score_period";
	final public static String SCORE_PAY_LIMIT = "score_pay_limit";
	final public static String SCORE_PAY_NUM = "score_pay_num";
	final public static String SHOPPING_SCORE ="shopping_score";
	final public static String SCORE_TYPE = "score_type";
	final public static String STORE_SCORE = "store_score";
	final public static String LOGIN_SCORE = "login_score";
	final public static String SCORE_LOGIN = "score_login";
	final public static String SIGN_IN_SCORE = "sign_in_score";
	

	public int setShopCfg(ShopCfgParam param) {

		// 设置积分有效规则
		int result = 1;
		String scoreLimit = param.getScoreLimit();
		if (ZERO.equals(scoreLimit)) {
			// 永久积分
			this.set(SCORE_LIMIT, scoreLimit);

		} else if (ONE.equals(scoreLimit)) {
			// 截止日期
			this.set(SCORE_LIMIT, scoreLimit);
			this.set(SCORE_DAY, param.getScoreDay());
			this.set(SCORE_MONTH, param.getScoreMonth());
			this.set(SCORE_YEAR, param.getScoreYear());

		} else if (TWO.equals(scoreLimit)) {

			this.set(SCORE_LIMIT, scoreLimit);
			this.set(SCORE_LIMIT_NUMBER, param.getScoreLimitNumber());
			this.set(SCORE_PERIOD, param.getScorePeriod());

		} else {
			return -1;
		}

		// 积分支付限制
		
		String scorePayLimit = param.getScorePayLimit();
		if (ZERO.equals(scorePayLimit)) {
			// 不限制
			this.set(SCORE_PAY_LIMIT, scorePayLimit);
		} else if (ONE.equals(scorePayLimit)) {
			// 自定义积分
			this.set(SCORE_PAY_LIMIT, scorePayLimit);
			this.set(SCORE_PAY_NUM, param.getScorePayNum());
		} else {
			return -1;
		}
		
		
		//购物送积分
		//积分开关
		String shoppingScore = (BUTTON_ON.equals(param.getShoppingScore()))?"1":"0";
		this.set(SHOPPING_SCORE, shoppingScore);
		
		//购物送积分的类型 0 购物多少送多少 1 购买每多少送多少
		String scoreType = param.getScoreType();
		this.set(SCORE_TYPE, scoreType);
		
		if(ONE.equals(shoppingScore)) {
			if(ZERO.equals(scoreType)) {
				//更新满多少送多少积分
				updateRecord(param,BUY);
			}
			
			if(ONE.equals(scoreType)) {
				//更新每满多少送多少积分
				updateRecord(param,BUY_EACH);
			}
		}
		
		//门店买单返送积分开关 on 1 
		String storeScore = BUTTON_ON.equals(param.getStoreScore()) ? ONE:ZERO;
		this.set(STORE_SCORE, storeScore);
		
		//登录送给积分
		String loginScore = BUTTON_ON.equals(param.getLoginScore())? ONE:ZERO;
		this.set(LOGIN_SCORE,loginScore);
		if(ONE.equals(loginScore)) {
			//登录送积分开关on
			this.set(SCORE_LOGIN, param.getScoreLogin());
		}
		
		
		//签到送积分
		String signInScore = BUTTON_ON.equals(param.getSignInScore()) ? ONE:ZERO;
		this.set(SIGN_IN_SCORE, signInScore);
		setSignScore(signInScore,param.getSignScore());
	

		return result;
	}

	/**
	 * 更新设置签到积分
	 * @param enable
	 * @param signScore
	 */
	private void setSignScore(String signInScore,String[] signScore) {
		String enable = signInScore;
		Byte status = Byte.parseByte(enable);
		String value=null;
		if(ONE.equals(signInScore)) {
			//积分开关打开
			value = Util.toJson(new UserScoreSetValue(enable,signScore));
		}else {
			//从数据库中获取json值
			String json = getValue("sign_in_score");
			UserScoreSetValue userScore = Util.parseJson(json,UserScoreSetValue.class);
			userScore.setEnable(enable);
			value = Util.toJson(userScore);
		}
		deleteRecord(SIGN_IN_SCORE);
		this.setJsonObject(SIGN_IN_SCORE,status,value);
	}

	/**
	 * 获取数据库中set_val3的值
	 * @param scoreName
	 * @return
	 */
	private String getValue(String scoreName) {
		Result<Record1<String>>  record = this.db()
						.select(USER_SCORE_SET.SET_VAL3)
						.from(USER_SCORE_SET)
						.where(USER_SCORE_SET.SCORE_NAME.eq(scoreName))
						.fetch();
		
		if(record.get(0) == null) {
			return null;
		}
		return (String) record.get(0).get(0);
	}
	/**
	 * 插入到数据库
	 * @param scoreName
	 * @param status
	 * @param value
	 */
	private void setJsonObject(String scoreName,Byte status,String value) {
		this.db().insertInto(USER_SCORE_SET,USER_SCORE_SET.SCORE_NAME,USER_SCORE_SET.STATUS,USER_SCORE_SET.SET_VAL3)
				.values(scoreName, status,value).execute();
	}

	/**
	 * 清空之前的记录
	 * @param name
	 * @return
	 */
	public int deleteRecord(String name) {
		return this.db()
					.delete(USER_SCORE_SET)
					.where(USER_SCORE_SET.SCORE_NAME.eq(name))
					.execute();
	}
	

	
	
	/**
	 * 更新积分
	 * @param param
	 * @return
	 */
	public void updateRecord(ShopCfgParam param,String scoreName) {
		
			// 清空之前的积分
			deleteRecord(scoreName);
			
			//插入新的积分
			 InsertValuesStep3<UserScoreSetRecord, String, String, String> insert = this.db()
					 							.insertInto(USER_SCORE_SET,USER_SCORE_SET.SCORE_NAME,USER_SCORE_SET.SET_VAL,USER_SCORE_SET.SET_VAL2);
			
			 String[] buy;
			 String[] score;
			 
			 if(BUY.equals(scoreName)) {
				 buy = param.getBuy();
				 score = param.getScore();
			 }else {
				 buy = param.getBuyEach();
				 score = param.getScoreEach();
			 }
			 
			 int length = buy.length<score.length?buy.length:score.length;
			 for(int i=0;i<length;i++) {
				 insert = insert.values(scoreName, buy[i], score[i]);
			 }
			insert.execute();
		
	}

	/**
	 * 获取积分配置信息
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public ScoreCfgVo getShopScoreCfg() {
		String[] fieldsStr = ScoreCfgVo.getFields();
		Field[] fields = ScoreCfgVo.class.getFields();
		ScoreCfgVo vo = new ScoreCfgVo();
		
		//代码优化
		//查询配置文件的key-value
		Map<String, String> intoMap = db().select(SHOP_CFG.K,SHOP_CFG.V).from(SHOP_CFG).fetch().intoMap(SHOP_CFG.K, SHOP_CFG.V);
		ObjectMapper objectMapper = new ObjectMapper();
		//将查询的结果赋值到pojo
		vo = objectMapper.convertValue(intoMap, ScoreCfgVo.class);

		
		//查询buy
		Result<Record2<String, String>> result = getValFromUserScoreSet(BUY);
		for(int i=0;i<result.size();i++) {
			Record2<String, String> record = result.get(i);
			vo.getBuy().add((String) record.get(0));
			vo.getBuyScore().add((String)record.get(1));
		}
		
		//查询buyEach
		Result<Record2<String, String>> resultEach = getValFromUserScoreSet(BUY_EACH);
		for(int i=0;i<resultEach.size();i++) {
			Record2<String, String> record = resultEach.get(i);
			vo.getBuyEach().add((String) record.get(0));
			vo.getBuyEachScore().add((String)record.get(1));
		}
		return vo;
	}

	/**
	 * 获取数据
	 * @return
	 */
	private Result<Record2<String, String>> getValFromUserScoreSet(String value) {
		Result<Record2<String, String>> result = db().select(USER_SCORE_SET.SET_VAL,USER_SCORE_SET.SET_VAL2)
													.from(USER_SCORE_SET)
													.where(USER_SCORE_SET.SCORE_NAME.eq(value)).fetch();
		return result;
	}
	
}
