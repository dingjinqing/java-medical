package com.vpu.mp.service.shop.member;

import static com.vpu.mp.db.shop.tables.UserScoreSet.USER_SCORE_SET;
import org.jooq.InsertValuesStep3;

import com.vpu.mp.db.shop.tables.records.UserScoreSetRecord;
import com.vpu.mp.service.pojo.shop.member.ShopCfgParam;
import com.vpu.mp.service.shop.config.BaseShopConfigService;

/**
 * 积分配置Service
 * 
 * @author 黄壮壮 2019-07-15 14:13
 */
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
				updateRecord(param,BUY_EACH);
			}
		}

		
		return result;
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
	
}
