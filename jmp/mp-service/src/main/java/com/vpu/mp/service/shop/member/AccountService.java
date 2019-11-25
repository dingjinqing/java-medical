package com.vpu.mp.service.shop.member;

import static com.vpu.mp.db.shop.tables.RecordAdminAction.RECORD_ADMIN_ACTION;
import static com.vpu.mp.db.shop.tables.User.USER;
import static com.vpu.mp.db.shop.tables.UserAccount.USER_ACCOUNT;
import static com.vpu.mp.service.foundation.data.JsonResultCode.CODE_MEMBER_ACCOUNT_UPDATE_FAIL;
import static com.vpu.mp.service.pojo.shop.member.MemberOperateRecordEnum.ADMIN_OPERATION;
import static com.vpu.mp.service.pojo.shop.member.MemberOperateRecordEnum.DEFAULT_FLAG;
import static com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.UACCOUNT_CONSUMPTION;
import static com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.UACCOUNT_RECHARGE;
import static com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.TRADE_CONTENT_CASH;
import static com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.TRADE_FLOW_IN;

import java.math.BigDecimal;
import java.util.Arrays;

import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.TradesRecordRecord;
import com.vpu.mp.db.shop.tables.records.UserRecord;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.BigDecimalUtil;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.auth.AdminTokenAuthInfo;
import com.vpu.mp.service.pojo.shop.member.account.AccountPageListParam;
import com.vpu.mp.service.pojo.shop.member.account.AccountPageListVo;
import com.vpu.mp.service.pojo.shop.member.account.AccountParam;
import com.vpu.mp.service.pojo.shop.operation.RecordContentTemplate;
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
	@Autowired private RecordTradeService tradeService;
	@Autowired private UserAccountDao uAccountDao;
	/**
	 * 会员余额变动
	 * @param param 余额对象参数
	 * @param adminUser 操作员id
	 * @param tradeType  交易类型 {@link com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum}
	 * @param tradeFlow  资金流向 {@link com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum}
	 * @param language 语言
	 * @return
	 */
	public void  addUserAccount(AccountParam param, int adminUser, Byte tradeType, Byte tradeFlow,String language) throws MpException {
		logger().info("正在进行余额更新");
		logger().info(param.toString());
		if (isNull(param.getUserId()) || isNull(param.getAmount())) {
			logger().info("用户id或用户卡余额不能为空");
			throw new MpException(CODE_MEMBER_ACCOUNT_UPDATE_FAIL);
		}
		
		UserRecord user = memberService.getUserRecordById(param.getUserId());
		logger().info(user.toString());
		/** 1-用户是否存在 是否有账户余额 */
		if (isNull(user) || isNull(user.getAccount())) {
			throw new MpException( CODE_MEMBER_ACCOUNT_UPDATE_FAIL);
		}
		logger().info("余额判断成功");

		/** 3.备注默认-处理国际化 */
		String remark ;
		if (StringUtils.isBlank(param.getRemark())) {
			/** -默认管理员操作 国际化*/
			remark = DEFAULT_FLAG.val()+ADMIN_OPERATION.val();
		}else {
			remark = param.getRemark();
		}	
		param.setRemark(remark);
		logger().info("备注判断成功");
		/** -支付类型  */
		if(isConsump(param)) {
			logger().info("消费");
			/** -消费 */
			if(isNotConsumpAvailable(user,param)) {
				logger().info("余额不足");
				throw new MpException( CODE_MEMBER_ACCOUNT_UPDATE_FAIL);
			}
			param.setIsPaid(UACCOUNT_CONSUMPTION.val());

		}else {
			logger().info("充值");
			/** -充值 */
			param.setIsPaid(UACCOUNT_RECHARGE.val());

		}

		logger().info("消费判断成功");

		/** -支付类型 不能为null */
		if(isNull(param.getPayment())) {
			param.setPayment("");
		}
		logger().info("支付类型");
		this.transaction(() ->{
			logger().info("事务处理中");
			/** 插入要更新的数据到user_account表 */
			addRow(param, adminUser);
			/** 更新用户余额user表  */
			updateUserAccount(param, user);
			/** 插入交易明细数据 到trades_record */
			addTradeRecord(param, tradeType, tradeFlow);
			/** 添加操作记录到b2c_record_admin_action*/
			//TODO目前只是对单个的，后期优化需要批量
			logger().info("amount 为： "+param.getAmount());
			String account = param.getAmount().compareTo(BigDecimal.ZERO)<0 ? param.getAmount().toString():"+"+param.getAmount().toString();
			saas().getShopApp(getShopId()).record.insertRecord(Arrays.asList(new Integer[] {RecordContentTemplate.MEMBER_ACCOUNT.code}), String.valueOf(user.getUserId()),user.getUsername(),account);
		});
		logger().info("余额更新成功");
		return ;
	}
	
	private boolean isNotConsumpAvailable(UserRecord user,AccountParam param) {
		return !isConsumpAvailable(user,param);
	}

	private boolean isConsumpAvailable(UserRecord user,AccountParam param) {
		BigDecimal val = param.getAmount().add(user.getAccount());
		return !isLessThanZero(val);
	}
	private boolean isConsump(AccountParam param) {
		return isLessThanZero(param.getAmount());
	}
	private boolean isLessThanZero(BigDecimal val) {
		boolean g = BigDecimalUtil.compareTo(val, BigDecimal.ZERO)<0;
		return g;
	}
	private void addTradeRecord(AccountParam param, Byte tradeType, Byte tradeFlow) {
		String tradeSn = param.getOrderSn() == null ? "" : param.getOrderSn();
		
		TradesRecordRecord record = new TradesRecordRecord();
		record.setTradeTime(DateUtil.getLocalDateTime());
		record.setTradeNum(param.getAmount());
		record.setTradeSn(tradeSn);
		record.setUserId(param.getUserId());
		record.setTradeContent(TRADE_CONTENT_CASH.val());
		record.setTradeType(tradeType);
		record.setTradeFlow(tradeFlow);
		tradeFlow = tradeFlow == 2 ? TRADE_FLOW_IN.val() : tradeFlow;
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
		/** 应该从user_account表中计算余额 */
		logger().info("计算用户余额");
		BigDecimal account = db().select(DSL.sum(USER_ACCOUNT.AMOUNT))
			.from(USER_ACCOUNT)
			.where(USER_ACCOUNT.USER_ID.eq(user.getUserId()))
			.fetchAnyInto(BigDecimal.class);
		logger().info("计算用户余额； "+account);
		account = account != null?account:BigDecimal.ZERO;
		db().update(USER).set(USER.ACCOUNT,account).where(USER.USER_ID.eq(user.getUserId())).execute();
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

	

	
	private boolean isNull(Object obj) {
		return obj == null;
	}


}
