package com.vpu.mp.service.shop.member;

import static com.vpu.mp.db.shop.tables.ShopCfg.SHOP_CFG;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.BUTTON_ON;
import static com.vpu.mp.db.shop.tables.UserScoreSet.USER_SCORE_SET;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jooq.InsertValuesStep3;
import org.jooq.Record2;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vpu.mp.db.shop.tables.records.ShopCfgRecord;
import com.vpu.mp.db.shop.tables.records.UserScoreSetRecord;
import com.vpu.mp.db.shop.tables.records.XcxCustomerPageRecord;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.member.score.ScoreCfgVo;
import com.vpu.mp.service.pojo.shop.member.score.ScoreFrontParam;
import com.vpu.mp.service.pojo.shop.member.score.ScoreFrontVo;
import com.vpu.mp.service.pojo.shop.member.ShopCfgParam;
import com.vpu.mp.service.pojo.shop.member.score.UserScoreSetValue;
import com.vpu.mp.service.shop.config.BaseShopConfigService;
import com.vpu.mp.service.shop.decoration.ShopMpDecorationService;

/**
 * 积分配置Service
 * 
 * @author 黄壮壮 2019-07-15 14:13
 */
@Service

public class ScoreCfgService extends BaseScoreCfgService {

	@Autowired
	public ShopMpDecorationService mpDecoration;
	final public static String ZERO = "0";
	final public static String ONE = "1";
	final public static String TWO = "2";
	
	/**
	 * 购物送积分
	 */
	final public static String BUY="buy";
	final public static String BUY_EACH="buy_each";
	
	
	// 积分国际化信息
	final public static String SCORE_CFG_TITLE="member.score.cfg.title";

	public int setShopCfg(ShopCfgParam param) {

		// 设置积分有效规则
		int result = 1;
		String scoreLimit = param.getScoreLimit();
		if (ZERO.equals(scoreLimit)) {
			// 永久积分
			setScoreLimit(scoreLimit);

		} else if (ONE.equals(scoreLimit)) {
			// 截止日期
			setScoreLimit(scoreLimit);
			setScoreDay(param.getScoreDay());
			setScoreMonth(param.getScoreMonth());
			setScoreYear(param.getScoreYear());
		} else if (TWO.equals(scoreLimit)) {
			// 时间度量
			setScoreLimit(scoreLimit);
			setScoreLimitNumber(param.getScoreLimitNumber());
			setScorePeriod(param.getScorePeriod());
		} else {
			//return -1;
			System.out.println("debug");
		}

		// 积分支付限制
		
		String scorePayLimit = param.getScorePayLimit();
		if (ZERO.equals(scorePayLimit)) {
			// 不限制
			setScorePayLimit(scorePayLimit);
		} else if (ONE.equals(scorePayLimit)) {
			// 自定义积分
			setScorePayLimit(scorePayLimit);
			setScorePayNum(param.getScorePayNum());
		} else {
			//return -1;
			System.out.println("debug");
		}
		
		
		/** 购物送积分 */
		/** 积分开关 */
		String shoppingScore = (BUTTON_ON.equals(param.getShoppingScore()))?ONE:ZERO;
		setShoppingScore(shoppingScore);
		
		/** 购物送积分的类型 0 购物多少送多少 1 购买每多少送多少 */
		String scoreType = param.getScoreType();
		setScoreType(scoreType);
		
		if(ONE.equals(shoppingScore)) {
			if(ZERO.equals(scoreType)) {
				/** 更新满多少送多少积分 */
				updateRecord(param,BUY);
			}
			
			if(ONE.equals(scoreType)) {
				/** 更新每满多少送多少积分 */
				updateRecord(param,BUY_EACH);
			}
		}
		
		//门店买单返送积分开关 on 1 
		String storeScore = BUTTON_ON.equals(param.getStoreScore()) ? ONE:ZERO;
		setStoreScore(storeScore);
		
		//登录送给积分
		String loginScore = BUTTON_ON.equals(param.getLoginScore())? ONE:ZERO;
		setLoginScore(loginScore);
		if(ONE.equals(loginScore)) {
			//登录送积分开关on
			setScoreLogin(param.getScoreLogin());
		}
		
		
		//签到送积分
		String signInScore = BUTTON_ON.equals(param.getSignInScore()) ? ONE:ZERO;
		setSignInScore(signInScore);
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
			UserScoreSetValue userScore = getScoreValueThird(SIGN_IN_SCORE);
			if(userScore != null){
				userScore.setEnable(enable);
				value = Util.toJson(userScore);
			}
		}
		deleteRecord(SIGN_IN_SCORE);
		setSignInScore(value);
		setJsonObject(SIGN_IN_SCORE,status,value);
	}

	/**
	 * 获取数据库中set_val3的值
	 * @param scoreName
	 * @return
	 */
	public UserScoreSetValue getScoreValueThird(String scoreName) {
		UserScoreSetRecord  record = this.db()
						.selectFrom(USER_SCORE_SET)
						.where(USER_SCORE_SET.SCORE_NAME.eq(scoreName))
						.fetchAny();
		UserScoreSetValue userScore=null;
		if(record!=null) {
			userScore = Util.parseJson(record.getSetVal3(),UserScoreSetValue.class);
		}
		return userScore;
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

		ScoreCfgVo vo = new ScoreCfgVo();
		
		// 代码优化
		// 查询配置文件的key-value
		Map<String, String> intoMap = db().select(SHOP_CFG.K,SHOP_CFG.V).from(SHOP_CFG).fetch().intoMap(SHOP_CFG.K, SHOP_CFG.V);
		ObjectMapper objectMapper = new ObjectMapper();
		// 将查询的结果赋值到pojo
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
		
		// 处理签到积分
		UserScoreSetValue userScore = getScoreValueThird("sign_in_score");
		if(!StringUtils.isBlank(userScore.getEnable()) && ONE.equals(userScore.getEnable())) {
			vo.setSignInScore(BUTTON_ON);
		}
		
		
		vo.setSignScore(userScore.getScore());
		
		// 模板名称
		if(!StringUtils.isBlank(vo.getScorePageId())) {
			XcxCustomerPageRecord xcxCustomerPage = mpDecoration.getPageById(Integer.parseInt(vo.getScorePageId()));
			if(xcxCustomerPage != null) {
				vo.setPageName(xcxCustomerPage.getPageName());
			}
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
	
	
	/**
	 * 查询某种活动对应积分
	 * @param k
	 * @return 
	 */
	public ShopCfgRecord getScoreNum(String k) {
		return db().selectFrom(SHOP_CFG).where(SHOP_CFG.K.eq(k)).fetchAny();
	}

	/**
	 * 保存文档
	 * @param param
	 */
	public void saveScoreDocument(ScoreFrontParam param) {
		param.setUpdateTime(DateUtil.getLocalDateTime());
		String value = Util.toJson(param);
		logger().info(value);
		setScoreDocument(value);
		this.set(SCORE_DOCUMENT, value);
	}

	/**
	 * 获取文档
	 * @return
	 */
	public ScoreFrontVo scoreCopywriting() {
		String value = db().select(SHOP_CFG.V).from(SHOP_CFG)
							.where(SHOP_CFG.K.eq(SCORE_DOCUMENT))
							.fetchAnyInto(String.class);
		
		ScoreFrontVo vo = new ScoreFrontVo();
		vo.setTitle(SCORE_CFG_TITLE);
		if(value != null) {
			ScoreFrontParam param = Util.parseJson(value, ScoreFrontParam.class);
			vo.setDocument(param.getDocument());
			return vo;
		}else {
			return null;
		}
	}
	
	/**
	 * 积分模板页添加 addScore 
	 * @param scorePageId
	 */
	public void addScoreCfgForDecoration(ShopCfgParam param) {
		// 通过前端调用多个接口api而不是将数据全部塞满
		setScorePageId(""+param.getScorePageId());
	}
	
	
}
