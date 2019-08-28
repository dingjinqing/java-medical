package com.vpu.mp.service.shop.member;

import static com.vpu.mp.db.shop.tables.RecordAdminAction.RECORD_ADMIN_ACTION;
import static com.vpu.mp.db.shop.tables.User.USER;
import static com.vpu.mp.db.shop.tables.UserAccount.USER_ACCOUNT;
import static com.vpu.mp.service.foundation.data.JsonResultCode.CODE_MEMBER_ACCOUNT_UPDATE_FAIL;
import static com.vpu.mp.service.pojo.shop.member.MemberOperateRecordEnum.ADMIN_OPERATION;
import static com.vpu.mp.service.pojo.shop.operation.RecordContentMessage.MSG_MEMBER_ACCOUNT;
import static com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.TRADE_CONTENT_BY_CASH;
import static com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.TRADE_FLOW_INCOME;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;

import org.jooq.Record6;
import org.jooq.SelectConditionStep;
import org.jooq.tools.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.TradesRecordRecord;
import com.vpu.mp.db.shop.tables.records.UserRecord;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.VoTranslator;
import com.vpu.mp.service.pojo.shop.auth.AdminTokenAuthInfo;
import com.vpu.mp.service.pojo.shop.member.account.AccountPageListParam;
import com.vpu.mp.service.pojo.shop.member.account.AccountPageListVo;
import com.vpu.mp.service.pojo.shop.member.account.AccountParam;
import com.vpu.mp.service.pojo.shop.operation.RecordContentTemplate;
import com.vpu.mp.service.shop.operation.RecordMemberTradeService;
/**
 * 余额管理
 * @author 黄壮壮
 * @Date:  2019年8月26日
 * @Description: 会员余额管理
 */
@Service

public class AccountService extends ShopBaseService {
	@Autowired private MemberService memberService;
	@Autowired private VoTranslator translator;
	@Autowired private RecordMemberTradeService tradeService;
	/**
	 * @param param 余额对象参数
	 * @param adminUser 操作员id
	 * @param tradeType  交易类型 {@link com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum}
	 * @param tradeFlow  资金流向 {@link com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum}
	 * @return
	 */
	public void  addUserAccount(AccountParam param, int adminUser, Byte tradeType, Byte tradeFlow) throws MpException {
		/** 鲁棒性检查 &*/
		if (param.getUserId() == null || param.getAmount() == null) {
			throw new MpException(CODE_MEMBER_ACCOUNT_UPDATE_FAIL);
		}
		UserRecord user = memberService.getUserRecordById(param.getUserId());

		/** 1-用户是否存在 是否有账户余额 */
		if (user == null || user.getAccount() == null) {
			throw new MpException( CODE_MEMBER_ACCOUNT_UPDATE_FAIL);
		}
		/** 2-检查提交的account与数据库中的account是否相等 */
		/** -因为余额变动的基数是基于数据库的account
		int ret = user.getAccount().compareTo(param.getAccount());
		if (ret != 0) {
			throw new MpException(CODE_MEMBER_ACCOUNT_UPDATE_FAIL);
		}
		*/
		/** 3.备注默认-处理国际化 */
		final String remark ;
		if (StringUtils.isEmpty(param.getRemark())) {
			/** -默认管理员操作 国际化*/
			String value = ADMIN_OPERATION.getValue();
			remark = translator.translate("member",value,value);
			logger().info("remark: "+remark);
		}else {
			remark = param.getRemark();
		}	
		param.setRemark(remark);

		/** 加事务 */
		this.transaction(() ->{
			/** 插入要更新的数据到user_account表 */
			addRow(param, adminUser);
			/** 更新用户余额user表  */
			updateUserAccount(param, user);
			/** 插入交易明细数据 到trades_record */
			addTradeRecord(param, tradeType, tradeFlow);
			
			
			/** 添加操作记录到b2c_record_admin_action*/
			//TODO目前只是对单个的，后期优化需要批量
			BigDecimal zero = new BigDecimal(0);
			String account = param.getAmount().compareTo(zero)<0 ? param.getAmount().toString():"+"+param.getAmount().toString();
			String data = String.format(MSG_MEMBER_ACCOUNT,user.getUserId(),user.getUsername(),account);
			saas().getShopApp(getShopId()).record.insertRecord(Arrays.asList(new Integer[] {RecordContentTemplate.MEMBER_ACCOUNT.code}), data);
			
		});

		return ;
	}

	private void addTradeRecord(AccountParam param, Byte tradeType, Byte tradeFlow) {
		String tradeSn = param.getOrderSn() == null ? "" : param.getOrderSn();
		
//		Byte zero = 0;
//		db().insertInto(TRADES_RECORD, TRADES_RECORD.TRADE_TIME, TRADES_RECORD.TRADE_NUM, TRADES_RECORD.TRADE_SN,
//				TRADES_RECORD.USER_ID, TRADES_RECORD.TRADE_TYPE, TRADES_RECORD.TRADE_FLOW, TRADES_RECORD.TRADE_STATUS,
//				TRADES_RECORD.TRADE_STATUS)
//				.values(DateUtil.getLocalDateTime(), param.getAmount(), tradeSn, param.getUserId(), zero, tradeType,
//						tradeFlow, tradeFlow == 2 ? zero : tradeFlow)
//				.execute();
		TradesRecordRecord record = new TradesRecordRecord();
		record.setTradeTime(DateUtil.getLocalDateTime());
		record.setTradeNum(param.getAmount());
		record.setTradeSn(tradeSn);
		record.setUserId(param.getUserId());
		record.setTradeContent(TRADE_CONTENT_BY_CASH.getValue());
		record.setTradeType(tradeType);
		record.setTradeFlow(tradeFlow);
		tradeFlow = tradeFlow == 2 ? TRADE_FLOW_INCOME.getValue() : tradeFlow;
		record.setTradeStatus(tradeFlow);
		tradeService.insertRecord(record);
		
	}

	
	
	/**
	 * 更新user表的account字段
	 * 
	 * @param param
	 * @param user
	 */
	private void updateUserAccount(AccountParam param, UserRecord user) {
		db().update(USER).set(USER.ACCOUNT, param.getAmount().add(user.getAccount()))
				.where(USER.USER_ID.eq(user.getUserId())).execute();
	}

	/**
	 * 将数据插入到user_account
	 * 
	 * @param param
	 * @param adminUser
	 */
	private void addRow(AccountParam param, int adminUser) {
		db().insertInto(USER_ACCOUNT, USER_ACCOUNT.USER_ID, USER_ACCOUNT.AMOUNT, USER_ACCOUNT.REMARK,
				USER_ACCOUNT.ADMIN_USER,USER_ACCOUNT.IS_PAID,USER_ACCOUNT.PAYMENT)
				.values(param.getUserId(), param.getAmount(), param.getRemark(), String.valueOf(adminUser),param.getIsPaid(),param.getPayment())
				.execute();
	}

	/**
	 * 添加操作记录到b2c_record_admin_action
	 * @param param
	 * @param user
	 * @param admin 
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
	public PageResult<AccountPageListVo> getPageListOfAccountDetails(AccountPageListParam param) {
		
		SelectConditionStep<Record6<String, String, String, BigDecimal, Timestamp, String>> select = db()
				.select(USER.USERNAME,USER.MOBILE,USER_ACCOUNT.ORDER_SN,USER_ACCOUNT.AMOUNT,USER_ACCOUNT.CREATE_TIME,USER_ACCOUNT.REMARK)
				.from(USER_ACCOUNT.join(USER).on(USER.USER_ID.eq(USER_ACCOUNT.USER_ID))).where(USER_ACCOUNT.USER_ID.eq(param.getUserId()));
		
		
		/** 查询条件的其他选项 */
		buildOptions(select,param);
		
		/** 以时间降序 */
		select.orderBy(USER_ACCOUNT.CREATE_TIME.desc());
		return getPageResult(select, param.getCurrentPage(), param.getPageRows(), AccountPageListVo.class);
	}

	

	
	/**
	 * 分页查询用户余额明细-积分明细时其他查询条件
	 */
	private void buildOptions(SelectConditionStep<Record6<String, String, String, BigDecimal, Timestamp, String>> select,
			AccountPageListParam param) {
		logger().info("正在构建查询条件");
		
		/** 订单号 */
		if(!StringUtils.isEmpty(param.getOrderSn())) {
			select.and(USER_ACCOUNT.ORDER_SN.eq(param.getOrderSn()));
		}
		
		/** 时间范围 */
		/** 开始时间 */
		if(param.getStartTime() != null) {
			select.and(USER_ACCOUNT.CREATE_TIME.ge(param.getStartTime()));
		}
		/** 结束时间 */
		if(param.getEndTime() != null) {
			select.and(USER_ACCOUNT.CREATE_TIME.le(param.getEndTime()));
		}
	}



}
