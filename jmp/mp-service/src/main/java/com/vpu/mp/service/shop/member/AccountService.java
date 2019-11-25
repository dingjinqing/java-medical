package com.vpu.mp.service.shop.member;

import static com.vpu.mp.db.shop.tables.RecordAdminAction.RECORD_ADMIN_ACTION;
import static com.vpu.mp.db.shop.tables.UserAccount.USER_ACCOUNT;
import static com.vpu.mp.service.foundation.data.JsonResultCode.CODE_MEMBER_ACCOUNT_UPDATE_FAIL;
import static com.vpu.mp.service.pojo.shop.member.MemberOperateRecordEnum.ADMIN_OPERATION;
import static com.vpu.mp.service.pojo.shop.member.MemberOperateRecordEnum.DEFAULT_FLAG;
import static com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.TRADE_CONTENT_CASH;
import static com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.TRADE_FLOW_IN;
import static com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.TRADE_FLOW_OUT;
import static com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.UACCOUNT_CONSUMPTION;
import static com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.UACCOUNT_RECHARGE;

import java.math.BigDecimal;
import java.util.Arrays;

import org.jooq.tools.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.UserRecord;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.BigDecimalUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.auth.AdminTokenAuthInfo;
import com.vpu.mp.service.pojo.shop.member.account.AccountPageListParam;
import com.vpu.mp.service.pojo.shop.member.account.AccountPageListVo;
import com.vpu.mp.service.pojo.shop.member.account.AccountParam;
import com.vpu.mp.service.pojo.shop.member.builder.UserAccountRecordBuilder;
import com.vpu.mp.service.pojo.shop.operation.RecordContentTemplate;
import com.vpu.mp.service.pojo.shop.operation.TradeOptParam;
import com.vpu.mp.service.shop.member.dao.AccountDao;
import com.vpu.mp.service.shop.member.dao.UserAccountDao;
import com.vpu.mp.service.shop.operation.RecordTradeService;
/**
 * 余额管理
 * @author 黄壮壮
 * @Date:  2019年8月26日
 * @Description: 会员余额管理
 */
@Service
public class AccountService extends ShopBaseService {
	@Autowired private MemberService memberService;
	@Autowired private RecordTradeService recordTradeService;
	@Autowired private UserAccountDao uAccountDao;
	@Autowired private AccountDao accountDao;

	
	/**
	 * 会员余额变动
	 * @param param 余额对象参数
	 * @param tradeOpt 交易操作数据
	 */
	public void  addUserAccount(AccountParam param, TradeOptParam tradeOpt) throws MpException {
		if (isNull(param.getUserId()) || isNull(param.getAmount())) {
			logger().info("用户id或用户卡余额不能为空");
			throw new MpException(CODE_MEMBER_ACCOUNT_UPDATE_FAIL);
		}
		
		UserRecord user = memberService.getUserRecordById(param.getUserId());
		checkForUserExistAndHaveAccount(user);
		BigDecimal newAccount = calcAccount(user, param);
		dealWithRemark(param);
		dealWithPayType(param, newAccount,tradeOpt);
		
		
		/** -支付类型 不能为null */
		if(isNull(param.getPayment())) {
			param.setPayment("");
		}
		
		this.transaction(() ->{
			logger().info("事务处理中");
			
			addRow(param, tradeOpt.getAdminUserId());
			/** 更新用户余额user表  */
			updateUserAccount(newAccount,user.getUserId());
			/** 插入交易明细数据 到trades_record */
			addTradeRecord(param, tradeOpt);
			String account = param.getAmount().compareTo(BigDecimal.ZERO)<0 ? param.getAmount().toString():"+"+param.getAmount().toString();
			saas().getShopApp(getShopId()).record.insertRecord(Arrays.asList(new Integer[] {RecordContentTemplate.MEMBER_ACCOUNT.code}), String.valueOf(user.getUserId()),user.getUsername(),account);
		});
		
	}

	/**
	 * 检测确保用户存在并有用余额
	 */
	private void checkForUserExistAndHaveAccount(UserRecord user) throws MpException {
		if (isNull(user) || isNull(user.getAccount())) {
			logger().info("此用户不存在或余额不存在");
			throw new MpException( CODE_MEMBER_ACCOUNT_UPDATE_FAIL);
		}
	}

	private void dealWithPayType(AccountParam param, BigDecimal newAccount,TradeOptParam tradeOpt) throws MpException {
		/** -支付类型  */
		if(isConsump(param)) {
			/** -消费 */
			if(isNotConsumpAvailable(newAccount)) {
				logger().info("余额不足");
				throw new MpException( CODE_MEMBER_ACCOUNT_UPDATE_FAIL);
			}
			param.setIsPaid(UACCOUNT_CONSUMPTION.val());
			tradeOpt.setTradeFlow(TRADE_FLOW_OUT.val());
		}else {
			/** -充值 */
			param.setIsPaid(UACCOUNT_RECHARGE.val());
			tradeOpt.setTradeFlow(TRADE_FLOW_IN.val());
		}
	}

	/**
	 * 处理备注
	 */
	private void dealWithRemark(AccountParam param) {
		String remark ;
		if (StringUtils.isBlank(param.getRemark())) {
			/** -默认管理员操作 国际化*/
			remark = DEFAULT_FLAG.val()+ADMIN_OPERATION.val();
		}else {
			remark = param.getRemark();
		}	
		param.setRemark(remark);
	}
	
	private BigDecimal calcAccount(UserRecord user,AccountParam param) {
		return BigDecimalUtil.add(param.getAmount(), user.getAccount());
	}
	
	/**
	 * 余额是否不满足消费
	 * @return true: 不满足；false: 满足
	 */
	private boolean isNotConsumpAvailable(BigDecimal val) {
		return !isConsumpAvailable(val);
	}
	
	/**
	 * 余额是否满足消费
	 * @return true: 满足；false: 不满足
	 */
	private boolean isConsumpAvailable(BigDecimal val) {
		return !isLessThanZero(val);
	}
	/**
	 * 是否为消费
	 * @return true: 是；false: 不是
	 */
	private boolean isConsump(AccountParam param) {
		return isLessThanZero(param.getAmount());
	}
	private boolean isLessThanZero(BigDecimal val) {
		return BigDecimalUtil.compareTo(val, BigDecimal.ZERO)<0;
	}
	
	private boolean isGreatOrEqualThanZero(BigDecimal val) {
		return BigDecimalUtil.compareTo(val, BigDecimal.ZERO)>=0;
	}
	
	/*
	 * 插入交易记录
	 */
	private void addTradeRecord(AccountParam param,TradeOptParam tradeOpt) {
		logger().info("记录用户余额到交易tradeRecord表");
		recordTradeService.insertTradeRecord(TradeOptParam.builder()
				.tradeNum(param.getAmount())
				.tradeSn(param.getOrderSn())
				.userId(param.getUserId())
				.tradeContent(TRADE_CONTENT_CASH.val())
				.tradeType(tradeOpt.getTradeType())
				.tradeFlow(tradeOpt.getTradeFlow())
				.tradeStatus(tradeOpt.getTradeFlow() == 2 ? TRADE_FLOW_IN.val() : tradeOpt.getTradeFlow())
				.build());
	}
	/**
	 * 更新user表的account字段
	 */
	private void updateUserAccount(BigDecimal account, Integer userId) {
		assert isGreatOrEqualThanZero(account):"余额不能为负数";
		if(isNotNull(account) && isGreatOrEqualThanZero(account)) {
			accountDao.updateUserAccount(account, userId);
		}
	}



	/**
	 * 将数据插入到user_account
	 */
	public void addRow(AccountParam param, int adminUser) {
		logger().info("插入userAccount记录");
		UserAccountRecordBuilder
			.create(db().newRecord(USER_ACCOUNT))
			.userId(param.getUserId())
			.amount(param.getAmount())
			.remark(param.getRemark())
			.adminUser(String.valueOf(adminUser))
			.isPaid(param.getIsPaid())
			.payment(param.getPayment())
			.build()
			.insert();
	}

	/**
	 * 添加操作记录到b2c_record_admin_action
	 */
	public void addActionRecord(AccountParam param, UserRecord user, AdminTokenAuthInfo admin) {
		Integer userId = user.getUserId();
		String name = user.getUsername();
		BigDecimal zero = new BigDecimal(0);
		String account = param.getAmount().compareTo(zero)<0 ? param.getAmount().toString():"+"+param.getAmount().toString();
		String actionDesc = String.format("修改\"ID: %d  昵称: %s \"的余额 %s", userId, name, account);
		
		Byte actionType=4;
		Integer subAccountId = admin.getSubAccountId()==null?0:admin.getSubAccountId();
		String userName = admin.getUserName();

		db().insertInto(RECORD_ADMIN_ACTION,RECORD_ADMIN_ACTION.SYS_ID,RECORD_ADMIN_ACTION.ACCOUNT_ID
							,RECORD_ADMIN_ACTION.ACTION_TYPE,RECORD_ADMIN_ACTION.TEMPLATE_DATA,RECORD_ADMIN_ACTION.USER_NAME)
			.values(admin.getSysId(),subAccountId,actionType,actionDesc,userName)
			.execute();
	}
	
	
	public BigDecimal getUserAccount(Integer userId) {
		UserRecord user = memberService.getUserRecordById(userId);
		if (user == null || user.getAccount() == null) {
			return BigDecimal.ZERO;
		}else {
			return user.getAccount();
		}
	}

	
	/**
	 * 分页查询会员用户余额详情
	 */
	public PageResult<AccountPageListVo> getPageListOfAccountDetails(AccountPageListParam param,String language) {
		PageResult<AccountPageListVo> res = uAccountDao.getPageListOfAccountDetails(param);
		// deal with 国际化
		for(AccountPageListVo vo: res.dataList) {
			dealWithRemarkI18n(vo,language);
		}
		
		return res;
	}

	private void dealWithRemarkI18n(AccountPageListVo vo,String language) {
		if(isNeedI18n(vo)) {
			String msg = vo.getRemark().split(":")[1];
			String languageType = msg.split("\\.")[0];
			String transMsg = Util.translateMessage(language, msg, languageType);
			vo.setRemark(transMsg);
		}
	}

	/**
	 * 是否需要国际化
	 */
	private boolean isNeedI18n(AccountPageListVo vo) {
		String noWhiteSpaceRemark = vo.getRemark().replaceAll("\\s+","");
		boolean startFlag = noWhiteSpaceRemark.startsWith(DEFAULT_FLAG.val());
		String[] arr = noWhiteSpaceRemark.split(":");
		return startFlag  &&  arr.length==2;
	}

	
	private boolean isNotNull(BigDecimal account) {
		return account != null;
	}
	
	private boolean isNull(Object obj) {
		return obj == null;
	}


}


