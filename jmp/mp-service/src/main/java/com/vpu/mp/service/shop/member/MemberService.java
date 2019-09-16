package com.vpu.mp.service.shop.member;

import static com.vpu.mp.db.shop.Tables.MEMBER_CARD;
import static com.vpu.mp.db.shop.Tables.ORDER_VERIFIER;
import static com.vpu.mp.db.shop.Tables.STORE;
import static com.vpu.mp.db.shop.Tables.USER;
import static com.vpu.mp.db.shop.Tables.TAG;
import static com.vpu.mp.db.shop.Tables.USER_CARD;
import static com.vpu.mp.db.shop.Tables.USER_DETAIL;
import static com.vpu.mp.db.shop.Tables.ORDER_INFO;
import static com.vpu.mp.db.shop.Tables.ORDER_GOODS;
import static com.vpu.mp.db.shop.Tables.USER_CART_RECORD;
import static com.vpu.mp.db.shop.Tables.USER_LOGIN_RECORD;
import static com.vpu.mp.db.shop.Tables.USER_TAG;
import static com.vpu.mp.db.shop.Tables.USER_IMPORT_DETAIL;
import static com.vpu.mp.db.shop.Tables.CHANNEL;
import static org.jooq.impl.DSL.count;
import static org.jooq.impl.DSL.date;

import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.CARD_USING;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.FOREVER;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.FIX_DATETIME;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.DURING_TIME;
import static com.vpu.mp.service.pojo.shop.member.SourceNameEnum.SCAN_QRCODE;
import static com.vpu.mp.service.pojo.shop.member.SourceNameEnum.NOT_ACQUIRED;
import static com.vpu.mp.service.pojo.shop.member.SourceNameEnum.BACK_STAGE;
import static com.vpu.mp.service.pojo.shop.member.SourceNameEnum.CHANNAL_PAGE;
import static com.vpu.mp.service.pojo.shop.order.OrderConstant.ORDER_WAIT_DELIVERY;
import static com.vpu.mp.service.pojo.shop.member.MemberConstant.DELETE_YES;
import static com.vpu.mp.service.pojo.shop.member.MemberConstant.INVITE_USERNAME;
import static com.vpu.mp.service.pojo.shop.member.MemberConstant.MONTH_DAYS;
import static com.vpu.mp.service.pojo.shop.member.MemberConstant.YEAR_DAYS;
import static com.vpu.mp.service.pojo.shop.member.MemberConstant.DAY_FLAG;
import static com.vpu.mp.service.pojo.shop.member.MemberConstant.MONTH_FLAG;
import static com.vpu.mp.service.pojo.shop.member.MemberConstant.ONE_MONTH_FLAG;
import static com.vpu.mp.service.pojo.shop.member.MemberConstant.YEAR_FLAG;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.jooq.Field;
import org.jooq.InsertValuesStep2;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.jooq.SelectField;
import org.jooq.SelectJoinStep;
import org.jooq.SelectWhereStep;
import org.jooq.tools.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.User;
import com.vpu.mp.db.shop.tables.records.DistributionWithdrawRecord;
import com.vpu.mp.db.shop.tables.records.UserRecord;
import com.vpu.mp.db.shop.tables.records.UserTagRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.BigDecimalUtil;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.FieldsUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.area.AreaCityVo;
import com.vpu.mp.service.pojo.shop.area.AreaDistrictVo;
import com.vpu.mp.service.pojo.shop.area.AreaProvinceVo;
import com.vpu.mp.service.pojo.shop.distribution.DistributorListParam;
import com.vpu.mp.service.pojo.shop.distribution.DistributorListVo;
import com.vpu.mp.service.pojo.shop.market.MarketAnalysisParam;
import com.vpu.mp.service.pojo.shop.member.CommonMemberPageListQueryParam;
import com.vpu.mp.service.pojo.shop.member.CommonMemberPageListQueryVo;
import com.vpu.mp.service.pojo.shop.member.MemberBasicInfoVo;
import com.vpu.mp.service.pojo.shop.member.MemberDetailsVo;
import com.vpu.mp.service.pojo.shop.member.MemberEducationEnum;
import com.vpu.mp.service.pojo.shop.member.MemberIndustryEnum;
import com.vpu.mp.service.pojo.shop.member.MemberInfoVo;
import com.vpu.mp.service.pojo.shop.member.MemberPageListParam;
import com.vpu.mp.service.pojo.shop.member.MemberParam;
import com.vpu.mp.service.pojo.shop.member.MemberTransactionStatisticsVo;
import com.vpu.mp.service.pojo.shop.member.MememberLoginStatusParam;
import com.vpu.mp.service.pojo.shop.member.tag.TagVo;
import com.vpu.mp.service.pojo.shop.member.tag.UserTagParam;
import com.vpu.mp.service.saas.area.AreaSelectService;
import com.vpu.mp.service.shop.distribution.DistributorListService;
import com.vpu.mp.service.shop.distribution.DistributorWithdrawService;
import com.vpu.mp.service.shop.member.dao.MemberDaoService;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import com.vpu.mp.service.shop.store.store.StoreService;
import jodd.util.StringUtil;

/**
 * 
 * @author 黄壮壮 2019-07-08 16:22
 */
@Service
public class MemberService extends ShopBaseService {

	
	private static final String USER_NAME = "userName";

	public static final String INVITE_SOURCE_GROUPBUY = "groupbuy";
	public static final String INVITE_SOURCE_BARGAIN = "bargain";
	public static final String INVITE_SOURCE_INTEGRAL = "integral";
	public static final String INVITE_SOURCE_SECKILL = "seckill";
	public static final String INVITE_SOURCE_LOTTERY = "lottery";
	public static final String INVITE_SOURCE_GOODS = "goods";
	public static final String INVITE_SOURCE_MEMBERCARD = "membercard";
	public static final String INVITE_SOURCE_SCANQRCODE = "scanqrcode";
	public static final String INVITE_SOURCE_CHANNEL = "channel";
	public static final String INVITE_SOURCE_PROMOTE="promote";
	public static final String ZERO = "0";
	public static final String NEG_ONE = "-1";
	public static final Integer WEEK = 7;
	public static final Integer MONTH = 30;
	public static final Integer YEAR = 365;
	public static final Byte YES_DISTRIBUTOR = 1;

	@Autowired
	public AccountService account;
	@Autowired
	public StoreService store;
	@Autowired
	public ScoreService score;
	@Autowired
	public MemberCardService card;
	@Autowired
	public OrderInfoService order;
	@Autowired
	public AddressService address;
	@Autowired
	public DistributorListService distributorListService;
	@Autowired
	public DistributorWithdrawService distributorWithdrawService;
	@Autowired
	public MemberDaoService memberDao;
	@Autowired
	public AreaSelectService area;
	/**
	 * 会员列表分页查询
	 * 
	 * @param param
	 * @return
	 */
	public PageResult<MemberInfoVo> getPageList(MemberPageListParam param, String language) {
		
		/** 获取会员列表的基本信息 */
		PageResult<MemberInfoVo> memberList = getMemberList(param);

		LocalDate now = DateUtil.getLocalDate();

		DayOfWeek dayOfWeek = now.getDayOfWeek();
		List<Integer> inData = new ArrayList<>(Arrays.asList(new Integer[] { 0, 1 }));

		if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
			inData.clear();
			inData.addAll(Arrays.asList(new Integer[] { 0, 2 }));
		}

		for (MemberInfoVo member : memberList.dataList) {
			Integer userId = member.getUserId();

			/** 只需要一张会员卡的信息即可 */
			Record recordInfo = memberDao.getOneMemberCard(inData, userId);

				if(recordInfo != null) {
					String cardName = recordInfo.get(MEMBER_CARD.CARD_NAME);
					logger().info(cardName);
					member.setCardName(cardName);
				}
			/** 处理来源信息 */
			String sourceName = getSourceName(language, member);

			logger().info(sourceName);
			member.setSourceName(sourceName);
		}

		return memberList;
	}
	


	/**
	 * 获取会员列表的基本信息 
	 * @param param
	 * @return
	 */
	private PageResult<MemberInfoVo> getMemberList(MemberPageListParam param) {
		User u = USER.as("u");
		User n = USER.as("n");

		/** 自查寻 */
		Field<?> inviteUserName = this.db().select(n.USERNAME).from(n).where(n.USER_ID.eq(u.INVITE_ID))
				.asField(INVITE_USERNAME);
	
		SelectJoinStep<?> from = this.db().selectDistinct(u.USER_ID,
				u.USERNAME.as(USER_NAME), inviteUserName, u.MOBILE, u.ACCOUNT, u.SCORE, u.SOURCE, u.CREATE_TIME,u.DEL_FLAG,USER_DETAIL.REAL_NAME)
				.from(u.leftJoin(USER_DETAIL).on(USER_DETAIL.USER_ID.eq(u.USER_ID)));
		
		/** 动态构建连表 */
		buildOptionsForTable(param, u, from);
		
		SelectWhereStep<? extends Record> select = (SelectWhereStep<? extends Record>) from;
		
		/** -构建查询条件 */
		select = this.buildOptions(select, u, param);
		PageResult<MemberInfoVo> memberList = this.getPageResult(select, param.getCurrentPage(), param.getPageRows(),
				MemberInfoVo.class);
		return memberList;
	}

	/**
	 * 动态连表
	 * @param param
	 * @param user
	 * @param from
	 */
	private void buildOptionsForTable(MemberPageListParam param, User user, SelectJoinStep<?> from) {
		if(param.getCardId() != null) {
			from.leftJoin(USER_CARD).on(user.USER_ID.eq(USER_CARD.USER_ID));
		}
		if(!StringUtil.isBlank(param.getTagName())) {
			/** -标签处理 */
			from.leftJoin(USER_TAG).on(user.USER_ID.eq(USER_TAG.USER_ID));
		}
	}

	/**
	 * 获取用户来源
	 * @param language
	 * @param member
	 * @return
	 */
	private String getSourceName(String language, MemberInfoVo member) {
		String sourceName = null;
		if (NOT_ACQUIRED.getCode().equals(member.getSource())
				&& !(INVITE_SOURCE_CHANNEL.equals(member.getInviteSource()))
				&& !(SCAN_QRCODE.getCode().equals(member.getSource()))) {
			/** 未获取 */
			sourceName = Util.translateMessage(language, NOT_ACQUIRED.getName(), "member");
			logger().info(sourceName);
		} else if (BACK_STAGE.getCode().equals(member.getSource())) {
			/** 后台 */
			sourceName = Util.translateMessage(language, BACK_STAGE.getName(), "member");
		} else if (INVITE_SOURCE_CHANNEL.equals(member.getInviteSource())) {
			/** 渠道页-- */
			String channelName = db().select(CHANNEL.CHANNEL_NAME).from(CHANNEL)
					.where(CHANNEL.ID.eq(member.getInviteActId())).fetchOne().into(String.class);
			sourceName = Util.translateMessage(language, CHANNAL_PAGE.getName(), "member") + channelName;
		} else if (SCAN_QRCODE.getCode().equals(member.getSource())
				|| INVITE_SOURCE_SCANQRCODE.equals(member.getInviteSource())) {
			/** 扫码进入 */
			sourceName = Util.translateMessage(language, SCAN_QRCODE.getName(), "member");
		} else {
			/** 门店名称 */
			if(member.getSource() != null) {
				 Record record = store.getStoreName(new Integer(member.getSource()));
				 if(record != null) {
					 sourceName = record.into(String.class);
				 }
			}
		}
		return sourceName;
	}
	

	/**
	 * 多条件查询
	 * 
	 * @param select
	 * @param param
	 * @return
	 */
	private SelectWhereStep<? extends Record> buildOptions(SelectWhereStep<? extends Record> select, User u,
			MemberPageListParam param) {

		if (param == null) {

			return select;
		}
		
		/** -会员id */
		if(param.getUserId() != null) {
			select.where(u.USER_ID.eq(param.getUserId()));
		}
		/** - 手机号 */
		if (!StringUtils.isEmpty(param.getMobile())) {
			select.where(u.MOBILE.eq(param.getMobile()));
		}
		/** - 昵称 */
		if (!StringUtils.isEmpty(param.getUsername())) {
			logger().info("用户昵称模糊查询");
			String likeValue = likeValue(param.getUsername());
			select.where(u.USERNAME.like(likeValue));
		}
		/** - 来源 */
		if (param.getSource() != null) {
			select.where(u.SOURCE.eq(param.getSource()));
		}
		/** -邀请人 */
		if (!StringUtils.isBlank(param.getInviteUserName())) {
			logger().info("处理邀请人模糊查询");
			String likeValue = likeValue(param.getInviteUserName());
			List<Integer> ids = db().select(u.USER_ID).from(u).where(u.USERNAME.like(likeValue)).fetch().into(Integer.class);
			select.where(u.INVITE_ID.in(ids));
		}
		
		/** - 会员卡 */
		if(param.getCardId() != null) {
			logger().info("测试会员卡查询");
			select.where(USER_CARD.CARD_ID.eq(param.getCardId())).and(USER_CARD.FLAG.eq(CARD_USING));
		}

		/** -注册时间 */
		if (!StringUtils.isEmpty(param.getCreateTime())) {
			select.where(u.CREATE_TIME.ge(DateUtil.convertToTimestamp(param.getCreateTime())));
		}

		/** -结束时间 */
		if (!StringUtils.isEmpty(param.getEndTime())) {
			select.where(u.CREATE_TIME.le(DateUtil.convertToTimestamp(param.getEndTime())));
		}
		
		/** -标签处理 */
		if(!StringUtil.isBlank(param.getTagName())) {
			logger().info("正在构建标签查询页");
			String tagName = param.getTagName();
			List<Integer> ids = db().select(TAG.TAG_ID).from(TAG).where(TAG.TAG_NAME.eq(tagName)).fetch().into(Integer.class);
			select.where(USER_TAG.TAG_ID.in(ids));
		}
		

		/**  -指定时间内有登录 - 开始时间 */
		if(!StringUtil.isBlank(param.getLoginStartTime())) {
			logger().info("正在构建指定时间内登录-开始时间");
			Result<Record1<Integer>> record = memberDao.getLoginStartTime(param.getLoginStartTime());
			if(record != null) {
			List<Integer> ids = record.into(Integer.class);
			select.where(u.USER_ID.in(ids));
			}
		}
		
		/**  -指定时间内有登录 - 结束时间 */
		if(!StringUtil.isBlank(param.getLoginEndTime())) {
			logger().info("正在构建指定时间内登录-结束时间");
			Result<Record1<Integer>> record = memberDao.getLoginEndTime(param.getLoginEndTime());
			if(record != null) {
			List<Integer> ids = record.into(Integer.class);
			select.where(u.USER_ID.in(ids));
			}
		}
		
		
		/**  -指定时间内有加购行为 */
		if(!StringUtil.isBlank(param.getCartStartTime())) {
			logger().info("正在构建指定时间内有加购行为-开始时间");
			/** -用户添加购物车商品记录表 */
			Result<Record1<Integer>> record = memberDao.getCartStartTime(param.getCartStartTime());
			if(record != null) {
				List<Integer> ids = record.into(Integer.class);
				select.where(u.USER_ID.in(ids));
			}
		}
		
		/**  -指定时间内有加购行为 */
		if(!StringUtil.isBlank(param.getCartEndTime())) {
			logger().info("正在构建指定时间内有加购行为-结束时间");
			Result<Record1<Integer>> record = memberDao.getCartEndTime(param.getCartEndTime());
			/** -用户添加购物车商品记录表 */
			if(record != null) {
				List<Integer> ids = record.into(Integer.class);
				select.where(u.USER_ID.in(ids));
			}
		}
		
		
		/** -指定时间内有交易记录 */
		if(!StringUtil.isBlank(param.getBuyStartTime())) {
			logger().info("正在构建指定时间内有交易记录-开始时间");
			Result<Record1<Integer>> record = memberDao.getBuyStartTime(param.getBuyStartTime());
			/** -订单表 */
			if(record != null) {
				List<Integer> ids = record.into(Integer.class);
				select.where(u.USER_ID.in(ids));
			}
		}
		
		
		/** -指定时间内有交易记录 */
		if(!StringUtil.isBlank(param.getBuyEndTime())) {
			logger().info("正在构建指定时间内有交易记录-结束时间");
			Result<Record1<Integer>> record = memberDao.getBuyEndTime(param.getBuyEndTime());
			/** -订单表 */
			if(record != null) {
				List<Integer> ids = record.into(Integer.class);
				select.where(u.USER_ID.in(ids));
			}
		}
		
		
		/** -客单价 -最低 */
		if(param.getUnitPriceLow() != null) {
			select.where(u.UNIT_PRICE.ge(param.getUnitPriceLow()));
		}
		
		/** -客单价 -最高  */
		if(param.getUnitPriceHight() != null) {
			select.where(u.UNIT_PRICE.le(param.getUnitPriceHight()));
		}
		
		
			
		/** -累计购买次数 - 最低次数 */
		if(param.getBuyCountLow()!=null) {
			logger().info("正在构建累计购买次数-最低次数");
			Result<Record1<Integer>> record = memberDao.getBuyCountLow(param.getBuyCountLow());
			if(record != null) {
				List<Integer> ids = record.into(Integer.class);
				select.where(u.USER_ID.in(ids));
			}
		}
		
		/** -累计购买次数 - 最高次数 */
		if(param.getBuyCountHight() != null) {
			logger().info("正在构建累计购买次数-最高次数");
			Result<Record1<Integer>> record = memberDao.getBuyCountHight(param.getBuyCountHight());
			if(record != null) {
				List<Integer> ids = record.into(Integer.class);
				select.where(u.USER_ID.in(ids));
			}
		}
		
		/**  -购买指定商品 */
		if(param.getGoodsId()!=null && param.getGoodsId().size()>0) {
			logger().info("正在构建购买指定商品");
			Result<Record1<Integer>> record = memberDao.getMemberIdByGoodsId(param);
			if(record!=null) {
				List<Integer> ids = record.into(Integer.class);
				select.where(u.USER_ID.in(ids));
			}
		}

		/** -是否有手机号  */
		if(param.getHasMobile()) {
			select.where(u.MOBILE.isNotNull());
		}
		
		/** -是否有积分 */
		if(param.getHasScore()) {
			select.where(u.SCORE.greaterThan(Integer.parseInt(ZERO)));
		}
		
		/** -是否有余额 */
		if(param.getHasBalance()) {
			select.where(u.ACCOUNT.greaterThan(BigDecimal.ZERO));
		}
		
		/** -是否持有会员卡 */
		if(param.getHasCard()) {
			logger().info("正在处理持有会员卡的会员");
			Result<Record1<Integer>> record = memberDao.getUserIdByCardExist();
			if(record != null) {
			List<Integer> ids = record.into(Integer.class);
			select.where(u.USER_ID.in(ids));
			}
		}
		
		/** -是否已经禁止登录 */
		if(param.getHasDelete()) {
			select.where(u.DEL_FLAG.eq(DELETE_YES));
		}
		
		/** -是否是导入会员 */
		if(param.getHasImport()) {
			logger().info("正在构建导入会员");
			SelectConditionStep<Record1<Integer>> subSelect = db().select(USER_IMPORT_DETAIL.ID).from(USER_IMPORT_DETAIL).where(USER_IMPORT_DETAIL.MOBILE.eq(u.MOBILE));
			select.whereExists(subSelect);
		}
		/** 真实姓名 */
		if(!StringUtil.isBlank(param.getRealName())) {
			select.where(USER_DETAIL.REAL_NAME.eq(param.getRealName()));
		}
		
		return select;
	}



	/**
	 * 通用会员列表弹窗分页查询
	 * 
	 * @param param
	 * @return
	 */
	public PageResult<CommonMemberPageListQueryVo> getCommonPageList(CommonMemberPageListQueryParam param) {
		SelectJoinStep<? extends Record> select = db().select(USER.USER_ID, USER.USERNAME, USER.MOBILE).from(USER);
		select = this.buildCommonPageListQueryOptions(select, param);
		select.where(USER.DEL_FLAG.eq(DelFlag.NORMAL.getCode())).orderBy(USER.CREATE_TIME);
		return this.getPageResult(select, param.getCurrentPage(), param.getPageRows(),
				CommonMemberPageListQueryVo.class);
	}

	/**
	 * 通用会员选择弹窗的指定规则过滤条件构造
	 * 
	 * @return
	 */
	private SelectJoinStep<? extends Record> buildCommonPageListQueryOptions(SelectJoinStep<? extends Record> select,
			CommonMemberPageListQueryParam param) {
		if (param == null) {
			return select;
		}

		if (null != param.getUserId() && param.getUserId() > 0) {
			select.where(USER.USER_ID.eq(param.getUserId()));
		}
		if (!StringUtils.isEmpty(param.getMobile())) {
			select.where(USER.MOBILE.contains(param.getMobile()));
		}
		if (!StringUtils.isEmpty(param.getUsername())) {
			select.where(USER.USERNAME.contains(param.getUsername()));
		}

		/**
		 * 过滤已经是该门店核销员的用户，用于为该门店添加核销员
		 */
		if (null != param.getStoreId() && param.getStoreId() > 0) {
			select.leftJoin(ORDER_VERIFIER).on(USER.USER_ID.eq(ORDER_VERIFIER.USER_ID))
					.where(ORDER_VERIFIER.STORE_ID.ne(param.getStoreId()));
		}

		return select;
	}

	public UserRecord getUserRecordById(Integer userId) {
		UserRecord user = db().selectFrom(USER).where(USER.USER_ID.eq(userId)).fetchOne();
		return user;
	}

	public <T> T getUserFieldById(Integer userId, SelectField<T> field) {
		return db().select(field).from(USER).where(USER.USER_ID.eq(userId)).fetchOne().component1();
	}

	/**
	 * 通过活动新增用户
	 *
	 * @param param
	 * @param source
	 * @param actId
	 * @return
	 */
	public PageResult<MemberInfoVo> getSourceActList(MemberPageListParam param, String source, int actId) {
		User a = USER.as("a");
		User b = USER.as("b");
		SelectWhereStep<? extends Record> select = (SelectWhereStep<? extends Record>) db()
				.select(a.USER_ID, a.USERNAME.as(USER_NAME), a.MOBILE, a.CREATE_TIME, a.INVITE_ID,
						b.USERNAME.as(INVITE_USERNAME))
				.from(a).leftJoin(b).on(a.INVITE_ID.eq(b.USER_ID)).where(a.INVITE_SOURCE.eq(source))
				.and(a.INVITE_ACT_ID.eq(actId)).orderBy(a.CREATE_TIME.desc());
		select = this.buildOptions(select, a, param);

		return this.getPageResult(select, param.getCurrentPage(), param.getPageRows(), MemberInfoVo.class);
	}

	/**
	 * 分裂营销活动拉新用户数据分析
	 * 
	 * @param param
	 * @return
	 */
	public Map<Date, Integer> getMarketSourceUserAnalysis(MarketAnalysisParam param) {
		Map<Date, Integer> map;
		SelectWhereStep<? extends Record> select = (SelectWhereStep<? extends Record>) db()
				.select(date(USER.CREATE_TIME).as("date"), count().as("number")).from(USER)
				.where(USER.INVITE_ACT_ID.eq(param.getActId()));
		if (!StringUtils.isEmpty(param.getInviteSource())) {
			select.where(USER.INVITE_SOURCE.eq(param.getInviteSource()));
		}
		map = select.where(USER.CREATE_TIME.between(param.getStartTime(), param.getEndTime()))
				.groupBy(date(USER.CREATE_TIME)).fetch()
				.intoMap(date(USER.CREATE_TIME).as("date"), count().as("number"));
		return map;
	}

	/**
	 * 批量设置用户的登录状态 ： 禁止登录-恢复登录
	 */
	public void changeLoginStatus(MememberLoginStatusParam param) {

		int result = db().update(USER).set(USER.DEL_FLAG, param.getIsDelete())
				.where(USER.USER_ID.in(param.getUserIdList())).execute();

		logger().info("更新  " + result + " 条数据");
	}

	/**
	 * 为会员用户打标签
	 */
	public void setTagForMember(UserTagParam param) {

		/** 在事务中 */
		this.transaction(() -> {
			/** 构建insert sql语句 */
			InsertValuesStep2<UserTagRecord, Integer, Integer> insert = db().insertInto(USER_TAG)
					.columns(USER_TAG.USER_ID, USER_TAG.TAG_ID);
			/** 将值放入insert 语句 */
			param.getUserIdList().forEach(userId -> {
				param.getTagIdList().forEach(tagId -> insert.values(userId, tagId));
			});

			/** 删除原来的标签 */
			int before = db().delete(USER_TAG).where(USER_TAG.USER_ID.in(param.getUserIdList())).execute();
			int after = insert.execute();
			logger().info("删除 " + before + " 条记录。添加 " + after + " 条记录。");
		});
	}

	
	/**
	 * 查询会员所持有标签列表
	 * @param param
	 * @return
	 */
	public List<TagVo> getTagForMember(MemberParam param) {
		 return db().select(TAG.TAG_NAME,TAG.TAG_ID).from(USER_TAG.innerJoin(TAG).on(USER_TAG.TAG_ID.eq(TAG.TAG_ID)))
			.where(USER_TAG.USER_ID.eq(param.getUserId()))
			.fetch()
			.into(TagVo.class);
	}
	
	/** 根据用户id获取用户详情 
	 * @throws MpException */
	public MemberDetailsVo getMemberInfoById(Integer userId,String language) {
		MemberDetailsVo vo = new MemberDetailsVo();
		MemberTransactionStatisticsVo transStatistic = new MemberTransactionStatisticsVo();

		/** 用户基本信息 */
		MemberBasicInfoVo memberBasicInfoVo = dealWithUserBasicInfo(userId, transStatistic,language);
		
		/** -查询不到用户 */
		if(memberBasicInfoVo == null) {
			return vo;
		}
		/** 分销统计 */
		dealWithDistributorsInfo(userId, transStatistic, memberBasicInfoVo);

		vo.setMemberBasicInfo(memberBasicInfoVo);
		vo.setTransStatistic(transStatistic);
		return vo;
	}

	/**
	 * 处理会员用户的底本信息
	 * 
	 * @param userId
	 * @param transStatistic
	 * @return
	 * @throws MpException 
	 */
	private MemberBasicInfoVo dealWithUserBasicInfo(Integer userId, MemberTransactionStatisticsVo transStatistic,String language){
		/** 会员用户基本信息 */
		logger().info("正在处理会员基本信息");

		
		MemberBasicInfoVo memberBasicInfoVo = getMemberInfo(userId);
		if(memberBasicInfoVo == null) {
			return memberBasicInfoVo;
		}
		logger().info("生日: " + memberBasicInfoVo.getBirthdayYear());

		/** 处理省市区 */
		changeProvinceCodeToName(memberBasicInfoVo);
		/** 最近浏览时间 */
		Record2<Timestamp, Timestamp> loginTime = memberDao.getRecentBrowseTime(userId);

		/** 最近浏览时间如果updateTime 为null，则设置为createTime */
		if(loginTime != null) {
			if (loginTime.get(USER_LOGIN_RECORD.UPDATE_TIME) != null) {
				memberBasicInfoVo.setUpdateTime(loginTime.get(USER_LOGIN_RECORD.UPDATE_TIME));
			} else {
				memberBasicInfoVo.setUpdateTime(loginTime.get(USER_LOGIN_RECORD.CREATE_TIME));
			}
		}
		/** 累计积分 */
		BigDecimal totalScore = score.getTotalScore(userId);
		memberBasicInfoVo.setTotalScore(totalScore);

		/** 订单相关信息 */
		getOrderInfo(userId, transStatistic, memberBasicInfoVo);

		/** 详细地址 */
		List<String> addressList = address.getUserAddressById(userId);
		memberBasicInfoVo.setAddressList(addressList);

		/** 受教育程度 */
		Byte eduCode = memberBasicInfoVo.getEducation();
		if(eduCode != null) {
			memberBasicInfoVo.setEducationStr(MemberEducationEnum.valueOf(eduCode).getName());
			logger().info("受教育程度" + MemberEducationEnum.valueOf(eduCode).getName());
		}
		/** 来源 */
		
		MemberInfoVo memberInfoVo = new MemberInfoVo();
		FieldsUtil.assignNotNull(memberBasicInfoVo, memberInfoVo);
		String sourceName = getSourceName(language,memberInfoVo);
		memberBasicInfoVo.setSourceName(sourceName);
		
		
		/** 行业 */
		if(memberBasicInfoVo.getIndustryInfo() != null) {
			String name = MemberIndustryEnum.getNameByCode(Integer.parseInt(memberBasicInfoVo.getIndustryInfo()));
			memberBasicInfoVo.setIndustryInfo(name);
			
		}

		/** ---统计信息--- */
		/** 最近下单的订单信息 */
		setRecentOrderInfo(userId, transStatistic);
		return memberBasicInfoVo;
	}

	/**
	 * 将用户的住址省市区code转化为name
	 * @param memberBasicInfoVo
	 */
	public void changeProvinceCodeToName(MemberBasicInfoVo memberBasicInfoVo) {
		List<AreaProvinceVo> allArea = area.getAllArea();
		/** 省代码 */
		Integer provinceCode = memberBasicInfoVo.getProvinceCode();
		/** 市代码 */
		Integer cityCode = memberBasicInfoVo.getCityCode();
		/** 区代码 */
		Integer districtCode = memberBasicInfoVo.getDistrictCode();
		
		if(provinceCode != null) {
			for(AreaProvinceVo province: allArea) {
				
				if(provinceCode.equals(province.getProvinceId())){
					/** 设置省名称 */
					String provinceName = province.getProvinceName();
					memberBasicInfoVo.setProvinceName(provinceName);
					List<AreaCityVo> allCity = province.getAreaCity();
					if(cityCode == null || allCity.size()<1) {
						return;
					}
					/** 设置城市名称 */
					for(AreaCityVo city: allCity) {
						if(cityCode.equals(city.getCityId())) {
							String cityName = city.getCityName();
							memberBasicInfoVo.setCityName(cityName);
							
							/** 获取区|县 */
							List<AreaDistrictVo> areaDistrict = city.getAreaDistrict();
							if(districtCode == null || areaDistrict.size()<1) {
								return;
							}
							for(AreaDistrictVo district: areaDistrict) {
								if(districtCode.equals(district.getDistrictId())){
									/** 设置区 | 县 */
									String districtName = district.getDistrictName();
									memberBasicInfoVo.setDistictName(districtName);
									return;
								}
							}
							
						}
					}
					
					
				}
			}
		}
		
	}



	/**
	 * 最近下单的订单信息
	 * @param userId
	 * @param transStatistic
	 */
	public  void setRecentOrderInfo(Integer userId, MemberTransactionStatisticsVo transStatistic) {
		Timestamp createTime = order.getRecentOrderInfoByUserId(userId);
		if (createTime != null) {
			LocalDate now = LocalDate.now();
			LocalDate tmp = createTime.toLocalDateTime().toLocalDate();
			long days = Duration.between(tmp.atStartOfDay(), now.atStartOfDay()).toDays();
			StringBuilder lastAddOrder = new StringBuilder();
			if (days < WEEK) {
				lastAddOrder.append(days + DAY_FLAG );
			} else if (days < MONTH) {
				lastAddOrder.append(ONE_MONTH_FLAG);
			} else if (days < YEAR) {
				lastAddOrder.append((days / MONTH_DAYS) + MONTH_FLAG);
			} else {
				lastAddOrder.append((days / YEAR_DAYS) + YEAR_FLAG);
			}
			logger().info("最近下单距离现在 " + lastAddOrder.toString());
			transStatistic.setLastAddOrder(lastAddOrder.toString());
		} else {
			transStatistic.setLastAddOrder("0");
		}
	}

	/**
	 * 获得门店信息
	 * @param source
	 * @return
	 */
	public String getStoreName(String source) {
		Record storeName = memberDao.getStoreName(source);
		if(storeName != null) {
			return storeName.into(String.class);
		}
		return null;
	}

	


	/**
	 * 获取会员用户的信息
	 * @param userId
	 * @return
	 */
	private MemberBasicInfoVo getMemberInfo(Integer userId) {
		Record record = memberDao.getMemberInfo(userId);
		if(record != null) {
			return record.into(MemberBasicInfoVo.class);
		}else {
			return null;
		}
	}

	/**
	 * 获取分销信息
	 * 
	 * @param userId
	 * @param transStatistic
	 * @param memberBasicInfoVo
	 */
	private void dealWithDistributorsInfo(Integer userId, MemberTransactionStatisticsVo transStatistic,
			MemberBasicInfoVo memberBasicInfoVo) {
		
		if(memberBasicInfoVo == null) {
			return;
		}
		logger().info("正在获取分销统计信息");
		/** 分销统计 */
		/** 判断是不是分销员 */
		
		if (YES_DISTRIBUTOR.equals(memberBasicInfoVo.getIsDistributor())) {
			
			/** 用户的分销信息 */
			DistributorListVo distributor = getDistributor(userId, memberBasicInfoVo);
			if (distributor != null) {

				/** 返利商品总金额(元) */
				transStatistic.setTotalCanFanliMoney(distributor.getTotalCanFanliMoney());

				/** 获返利佣金总额(元) */
				transStatistic.setRebateMoney(BigDecimalUtil.add(distributor.getTotalFanliMoney(), distributor.getWaitFanliMoney()));


				/** 分销员分组 */
				transStatistic.setGroupName(distributor.getGroupName());

				/** 分销员等级 */
				transStatistic.setLevelName(distributor.getLevelName());

				/** 下级用户数 */
				transStatistic.setSublayerNumber(distributor.getSublayerNumber());

				/** 获返利订单数量 */
				transStatistic.setRebateOrderNum(distributorListService.getRebateOrderNum(userId));
			}
			/**-获取分销提现 */
			DistributionWithdrawRecord distributionWithdraw = distributorWithdrawService.getWithdrawByUserId(userId);
			if(distributionWithdraw != null) {
				/** -已提现佣金总额(元) */
				transStatistic.setWithdrawCash(distributionWithdraw.getWithdrawCash());
			}
			
		}
	}

	/**
	 * 获取对应Id的分销员信息
	 * 
	 * @param userId
	 * @param memberBasicInfoVo
	 * @return
	 */
	private DistributorListVo getDistributor(Integer userId, MemberBasicInfoVo memberBasicInfoVo) {
		/** 通过调用DistributorListService 服务的分页信息获取该分销员的信息 */
		DistributorListParam param = new DistributorListParam();
		param.setUsername(memberBasicInfoVo.getUsername());
		PageResult<DistributorListVo> pageList = distributorListService.getPageList(param);
		/** 找到userId相同的数据 */
		for (DistributorListVo distributor : pageList.dataList) {
			if (distributor.getUserId().equals(userId)) {
				return distributor;
			}
		}
		return null;
	}

	/** 获取用户详情关于订单的信息 */
	private void getOrderInfo(Integer userId, MemberTransactionStatisticsVo transStatistic,
			MemberBasicInfoVo memberBasicInfoVo) {
		/** 累计消费金额 */
		BigDecimal totalConsumpAmount = order.getAllConsumpAmount(userId);
		memberBasicInfoVo.setTotalConsumpAmount(totalConsumpAmount);

		/** 累计消费订单数 */
		Integer orderNum = order.getAllOrderNum(userId);
		transStatistic.setOrderNum(orderNum);
		logger().info("累计消费订单数" + orderNum);

		/** 累计下单金额 */
		BigDecimal orderMoney = order.getAllOrderMoney(userId);
		transStatistic.setOrderMoney(orderMoney);

		/** 累计退款金额 */
		BigDecimal returnOrderMoney = order.getAllReturnOrderMoney(userId);
		transStatistic.setReturnOrderMoney(returnOrderMoney);
		logger().info("累计退款金额 " + returnOrderMoney);

		/** 累计退款订单数 */
		Integer returnOrderNum = order.getAllReturnOrderNum(userId);
		transStatistic.setReturnOrderNum(returnOrderNum);
		logger().info("累计退款订单数 " + returnOrderNum);
	}
}
