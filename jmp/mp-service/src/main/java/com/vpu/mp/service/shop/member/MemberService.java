package com.vpu.mp.service.shop.member;

import static com.vpu.mp.db.shop.Tables.CHANNEL;
import static com.vpu.mp.db.shop.Tables.MEMBER_CARD;
import static com.vpu.mp.db.shop.Tables.ORDER_VERIFIER;
import static com.vpu.mp.db.shop.Tables.TAG;
import static com.vpu.mp.db.shop.Tables.USER;
import static com.vpu.mp.db.shop.Tables.USER_CARD;
import static com.vpu.mp.db.shop.Tables.USER_IMPORT_DETAIL;
import static com.vpu.mp.db.shop.Tables.USER_LOGIN_RECORD;
import static com.vpu.mp.db.shop.Tables.USER_TAG;
import static com.vpu.mp.service.pojo.shop.member.MemberConstant.DAY_FLAG;
import static com.vpu.mp.service.pojo.shop.member.MemberConstant.MONTH_DAYS;
import static com.vpu.mp.service.pojo.shop.member.MemberConstant.MONTH_FLAG;
import static com.vpu.mp.service.pojo.shop.member.MemberConstant.ONE_MONTH_FLAG;
import static com.vpu.mp.service.pojo.shop.member.MemberConstant.YEAR_DAYS;
import static com.vpu.mp.service.pojo.shop.member.MemberConstant.YEAR_FLAG;
import static org.jooq.impl.DSL.count;
import static org.jooq.impl.DSL.date;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.jooq.InsertValuesStep2;
import org.jooq.Record;
import org.jooq.Record2;
import org.jooq.Result;
import org.jooq.SelectField;
import org.jooq.SelectJoinStep;
import org.jooq.SelectWhereStep;
import org.jooq.UpdateSetMoreStep;
import org.jooq.tools.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.DistributionWithdrawRecord;
import com.vpu.mp.db.shop.tables.records.TagRecord;
import com.vpu.mp.db.shop.tables.records.UserDetailRecord;
import com.vpu.mp.db.shop.tables.records.UserImportDetailRecord;
import com.vpu.mp.db.shop.tables.records.UserRecord;
import com.vpu.mp.db.shop.tables.records.UserTagRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.excel.ExcelFactory;
import com.vpu.mp.service.foundation.excel.ExcelTypeEnum;
import com.vpu.mp.service.foundation.excel.ExcelWriter;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.BigDecimalUtil;
import com.vpu.mp.service.foundation.util.CardUtil;
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
import com.vpu.mp.service.pojo.shop.member.MemberRecordExportVo;
import com.vpu.mp.service.pojo.shop.member.MemberTransactionStatisticsVo;
import com.vpu.mp.service.pojo.shop.member.MememberLoginStatusParam;
import com.vpu.mp.service.pojo.shop.member.SourceNameEnum;
import com.vpu.mp.service.pojo.shop.member.card.AvailableMemberCardVo;
import com.vpu.mp.service.pojo.shop.member.card.UserCardDetailParam;
import com.vpu.mp.service.pojo.shop.member.card.UserCardDetailVo;
import com.vpu.mp.service.pojo.shop.member.order.UserCenterNumBean;
import com.vpu.mp.service.pojo.shop.member.order.UserOrderBean;
import com.vpu.mp.service.pojo.shop.member.tag.TagVo;
import com.vpu.mp.service.pojo.shop.member.tag.UserTagParam;
import com.vpu.mp.service.pojo.shop.operation.RecordContentTemplate;
import com.vpu.mp.service.saas.area.AreaSelectService;
import com.vpu.mp.service.shop.coupon.CouponService;
import com.vpu.mp.service.shop.distribution.DistributorListService;
import com.vpu.mp.service.shop.distribution.DistributorWithdrawService;
import com.vpu.mp.service.shop.member.dao.MemberDaoService;
import com.vpu.mp.service.shop.member.dao.UserCardDaoService;
import com.vpu.mp.service.shop.operation.RecordAdminActionService;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import com.vpu.mp.service.shop.order.refund.ReturnOrderService;
import com.vpu.mp.service.shop.store.store.StoreService;


/**
 * 
 * @author 黄壮壮 2019-07-08 16:22
 */
@Service
public class MemberService extends ShopBaseService {

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
	public ReturnOrderService returnOrderSvc;
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
	@Autowired
	public UserCardService userCardService;
	@Autowired
	public UserCardDaoService userCardDao;
	@Autowired
	public UserImportService userImportService;
	@Autowired
	public UserExportService userExpSvc;
	@Autowired
	public RecordAdminActionService recordAdminActionSvc;
	@Autowired
	public CouponService couponSvc;
	
	/**
	 * 导出会员
	 */
	public Workbook exportUser(MemberPageListParam param,String lang) {

		List<UserRecord> userList = getExportUserList(param);
		List<MemberRecordExportVo> resList = new ArrayList<>();
		
		for(UserRecord user: userList) {
			MemberRecordExportVo vo = new MemberRecordExportVo();
			FieldsUtil.assignNotNull(user, vo);
			//TODO
			UserCenterNumBean userCenterOrder = order.getUserCenterNum(user.getUserId(), 0, new Integer[] {6,8,10}, new Integer[] {});
			UserCenterNumBean userCenterReturnOrder = order.getUserCenterNum(user.getUserId(), 4, new Integer[] {7,9}, new Integer[] {5});
			
			// 邀请人姓名
			vo.setInviteUserName(userCardDao.getUserName(user.getInviteId()));
			
			resList.add(vo);
		}
		 Workbook workbook= ExcelFactory.createWorkbook(ExcelTypeEnum.XLSX);
	     ExcelWriter excelWriter = new ExcelWriter(lang,workbook);
		
	     excelWriter.writeModelList(resList,MemberRecordExportVo.class);
	     return workbook;
	}
	
	
	
	public List<UserRecord> getExportUserList(MemberPageListParam param) {
		return memberDao.getExportUserList(param);
	}
	
	
	/**
	 * 会员列表分页查询
	 * 
	 * @param param
	 * @return
	 */
	public PageResult<MemberInfoVo> getPageList(MemberPageListParam param, String language) {
		
		/** 获取会员列表的基本信息 */
		PageResult<MemberInfoVo> memberList = getMemberList(param);
		logger().info("获取会员列表成功");
		for (MemberInfoVo member : memberList.dataList) {
			Integer userId = member.getUserId();

			/** 只需要一张会员卡的信息即可 */
			Record recordInfo = memberDao.getOneMemberCard(userId);

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
		logger().info("获取会员成功");

		return memberList;
	}
	


	/**
	 * 获取会员列表的基本信息 
	 */
	private PageResult<MemberInfoVo> getMemberList(MemberPageListParam param) {
		PageResult<MemberInfoVo> memberList = memberDao.getMemberList(param);
		logger().info("积分检查");
		for(MemberInfoVo vo: memberList.dataList) {
			// 积分缓存
			Integer currentScore = vo.getScore();
			// 实际积分
			Integer actualScore = score.getTotalAvailableScoreById(vo.getUserId());
			if(!currentScore.equals(actualScore)) {
				// 缓存积分失效需要更新
				vo.setScore(actualScore);
				score.updateUserScore(vo.getUserId(), actualScore);
			}
		}
		return memberList;
	}

	/**
	 * 获取用户来源
	 */
	public String getSourceName(String language, MemberInfoVo member) {
		logger().info("正在获取用户来源信息");
		// 微信后台相关来源
		String sourceName = SourceNameEnum.getI18NameByCode(member.getScene(), language);
		final String SYMBOL = "; ";
		if(INVITE_SOURCE_CHANNEL.equals(member.getInviteSource())) {
			// 渠道页
			StringBuilder tmp = new StringBuilder();
			String channelName = db().select(CHANNEL.CHANNEL_NAME).from(CHANNEL)
					.where(CHANNEL.ID.eq(member.getInviteActId())).fetchOne().into(String.class);
			if(!StringUtils.isBlank(sourceName)) {
				tmp.append(sourceName);
				tmp.append(SYMBOL);
			}
			tmp.append(Util.translateMessage(language, "member.channal.page", "member"));
			tmp.append(channelName);
			sourceName =  tmp.toString();
		}
		
		if(member.getSource()!=null && member.getSource()>0) {
			// 门店
			StringBuilder tmp = new StringBuilder();
			if(!StringUtils.isBlank(sourceName)) {
				tmp.append(sourceName);
				tmp.append(SYMBOL);
			}
			tmp.append(store.getStoreName(new Integer(member.getSource())));
			sourceName = tmp.toString();
		}

		return sourceName;
	}
	


	/**
	 * 通用会员列表弹窗分页查询
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
					.where(ORDER_VERIFIER.STORE_ID.ne(param.getStoreId()).or(ORDER_VERIFIER.STORE_ID.isNull()));
		}

		return select;
	}
	
	/**
	 *  获取用户信息
	 */
	public UserRecord getUserRecordById(Integer userId) {
		UserRecord user = db().selectFrom(USER).where(USER.USER_ID.eq(userId)).fetchAny();
		// 积分缓存
		Integer currentScore = user.getScore();
		// 实际积分
		Integer actualScore = score.getTotalAvailableScoreById(userId);
		if(!currentScore.equals(actualScore)) {
			// 缓存积分失效需要更新
			logger().info("缓存积分失效需要更新");
			user.setScore(actualScore);
			score.updateUserScore(userId, actualScore);
		}
		
		return user;
	}

	public <T> T getUserFieldById(Integer userId, SelectField<T> field) {
		return db().select(field).from(USER).where(USER.USER_ID.eq(userId)).fetchOne().component1();
	}

	/**
	 * 通过活动新增用户
	 */
	public PageResult<MemberInfoVo> getSourceActList(MemberPageListParam param, String source, int actId) {
		return memberDao.getSourceActList(param,source,actId);
	}

	/**
	 * 分裂营销活动拉新用户数据分析
	 */
	public Map<Date, Integer> getMarketSourceUserAnalysis(MarketAnalysisParam param) {
		Map<Date, Integer> map;
		@SuppressWarnings("unchecked")
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
	 * 	批量设置用户的登录状态 ： 禁止登录-恢复登录
	 */
	public void changeLoginStatus(MememberLoginStatusParam param) {
		logger().info("设置用户登录权限");
		final List<Integer> ids;
		if(param.getUserIdList() == null || param.getUserIdList().size()==0) {
			return;
		}
		boolean allUserFlag = MememberLoginStatusParam.ALL_USER_ID.equals(param.getUserIdList().get(0));
		if(allUserFlag && param.getSearchCnt() != null) {
			ids = getAllUserIdBySeachCond(param.getSearchCnt());
		}else {
			ids = param.getUserIdList();
		}
		
		transaction(()->{
			int num = updateUserLoginPermission(param, ids);
			if(num > 0) {
				addLoginOptAdminRec(param, ids, num);
			}
		});
	}


	/**
	 * 	更新用户登录权限
	 * @return num 更新权限的用户数
	 */
	private int updateUserLoginPermission(MememberLoginStatusParam param, final List<Integer> ids) {
		logger().info("更新用户登录权限");
		UpdateSetMoreStep<UserRecord> setSql = db().update(USER).set(USER.DEL_FLAG,(byte)param.getPermission().ordinal());
		int num = 0;
		if(MememberLoginStatusParam.ALL_USER_ID.equals(ids.get(0))) {
			logger().info("更新系统中筛选出来的用户的状态");
			num = setSql.where(USER.USER_ID.in(ids))
						.and(USER.DEL_FLAG.notEqual((byte)param.getPermission().ordinal()))
						.execute();
		}else {
			logger().info("更新指定用户登录状态");
			num = setSql.where(USER.USER_ID.in(ids)).execute();
		}
		return num;
	}


	/**
	 * 	记录admin管理员操作用户登录权限的记录
	 */
	private void addLoginOptAdminRec(MememberLoginStatusParam param, final List<Integer> ids, int num) {
		logger().info("记录admin的改变用户登录权限的日志");
		boolean flag = MememberLoginStatusParam.LoginPermission.on.equals(param.getPermission());
		if(num == 1) {
			MemberBasicInfoVo user = getMemberInfo(ids.get(0));
			if(flag) {
				logger().info("允许用户"+user.getUsername()+"登录");
				recordAdminActionSvc.insertRecord(Collections.singletonList(RecordContentTemplate.MEMBER_LOGIN_ON.code),
							String.valueOf(user.getUserId()),user.getUsername());
			}else {
				logger().info("禁止用户"+user.getUsername()+"登录");
				recordAdminActionSvc.insertRecord(Collections.singletonList(RecordContentTemplate.MEMBER_LOGIN_OFF.code),
						String.valueOf(user.getUserId()),user.getUsername());
			}
		}else {
			Integer code;
			if(flag) {
				logger().info("批量允许"+num+"名用户登录");
				code = RecordContentTemplate.MEMBER_BATCH_LOGIN_ON.code;
			}else {
				logger().info("批量禁止"+num+"名用户登录");
				code = RecordContentTemplate.MEMBER_BATCH_LOGIN_OFF.code;
			}
			recordAdminActionSvc.insertRecord(Collections.singletonList(code), String.valueOf(num));
		}
	}


	/**
	 * 	通过筛选条件获得用户Id列表
	 * @param param
	 * @return List<Integer>用户ID列表
	 */
	private List<Integer> getAllUserIdBySeachCond(MemberPageListParam searchParam) {
		logger().info("处理筛选出全部用户IDs");
		PageResult<MemberInfoVo> memberList = this.memberDao.getMemberList(searchParam);
		return memberList.dataList.stream().map(r->r.getUserId()).collect(Collectors.toList());
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

	public boolean addUserTag(Integer tagId,Integer userId) {
		TagRecord tagRecord = db().selectFrom(TAG).where(TAG.TAG_ID.eq(tagId)).fetchAny();
		if(tagRecord==null) {
			logger().info("userId："+userId+"添加tag"+tagId+"不存在");
			return false;
		}
		UserTagRecord record = db().newRecord(USER_TAG);
		record.setTagId(tagId);
		record.setUserId(userId);
		int insert = record.insert();
		if(insert>0) {
			return true;
		}
		return false;
	}
	/**
	 * 查询会员所持有标签列表
	 */
	public List<TagVo> getTagForMember(Integer userId) {
		 return db().select(TAG.TAG_NAME,TAG.TAG_ID).from(USER_TAG.innerJoin(TAG).on(USER_TAG.TAG_ID.eq(TAG.TAG_ID)))
			.where(USER_TAG.USER_ID.eq(userId))
			.fetchInto(TagVo.class);
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
		Integer totalScore = score.getAccumulationScore(userId);
		memberBasicInfoVo.setTotalScore(totalScore);

		/** 订单相关信息 */
		getOrderInfo(userId, transStatistic, memberBasicInfoVo);

		/** 优惠券 */
		memberBasicInfoVo.setCanUseCouponNum(couponSvc.getCanUseCouponNum(userId));
		
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
			int industryCode = Integer.parseInt(memberBasicInfoVo.getIndustryInfo());
			memberBasicInfoVo.setIndustryId(industryCode);
			String name = MemberIndustryEnum.getNameByCode(industryCode);
			memberBasicInfoVo.setIndustryInfo(name);
		}
		memberBasicInfoVo.setUserId(userId);
		
		/** 邀请人分销分组名称 */
		memberBasicInfoVo.setInviteGroupName(distributorListService.getGroupName(userId));
		
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
	 * @return 用户信息，不会为null
	 */
	public MemberBasicInfoVo getMemberInfo(Integer userId) {
		return memberDao.getMemberInfo(userId);
	}
	
	/**
	 * 	获取用户积分
	 */
	public Integer getUserScore(Integer userId) {
		MemberBasicInfoVo member = getMemberInfo(userId);
		if(member != null && member.getScore() != null) {
			return member.getScore();
		}else {
			return NumberUtils.INTEGER_ZERO;
		}
		
	}
	
	/**
	 * 	获取用户余额
	 */
	public BigDecimal getUserAccount(Integer userId) {
		MemberBasicInfoVo member = getMemberInfo(userId);
		if(member != null && member.getAccount() != null) {
			return member.getAccount();
		}else {
			return BigDecimal.ZERO;
		}
	}
	/**
	 * 	获取用户微信openid
	 */
	public String getUserWxOpenId(Integer userId) {
		MemberBasicInfoVo member = getMemberInfo(userId);
		return member.getWxOpenid();
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
		UserOrderBean userOrder = order.getAllConsumeOrder(userId);
		memberBasicInfoVo.setTotalConsumpAmount(userOrder.getTotalMoneyPaid());
		
		/** 客单价 */
		memberBasicInfoVo.setUnitPrice(userOrder.getUnitPrice());
		
		/** 累计消费订单数 */
		transStatistic.setOrderNum(userOrder.getOrderNum());
		logger().info("累计消费订单数" + userOrder.getOrderNum());

		UserOrderBean returnOrder = returnOrderSvc.getReturnOrder(userId);
		/** 累计退款金额 */
		transStatistic.setReturnOrderMoney(returnOrder.getTotalMoneyPaid());
		logger().info("累计退款金额 " + returnOrder.getTotalMoneyPaid());

		/** 累计退款订单数 */
		transStatistic.setReturnOrderNum(returnOrder.getOrderNum());
		logger().info("累计退款订单数 " + returnOrder.getOrderNum());
	}


	/**
	 * 更新会员的信息
	 * @param param
	 */
	public void updateMemberInfo(MemberParam param) {
		/** 更新用户邀请人*/
		if(param.getInviteId()!=null) {
			/** 更新user表*/
			memberDao.updateMemberInviteId(param.getUserId(),param.getInviteId());
		}
		
		/** 更新user_detail */
		memberDao.updateMemberInfoSql(param);

	}


	/**
	 * 获取用户的所有可用会员卡
	 * @param userId
	 */
	public List<AvailableMemberCardVo> getAllAvailableMemberCard(Integer userId) {
		Result<Record> allAvailableMemberCard = memberDao.getAllAvailableMemberCard(userId);
		List<AvailableMemberCardVo> cardList = new ArrayList<>();
		allAvailableMemberCard.stream()
							  .forEach(
									  record->{
										  Timestamp expireTime = record.get(USER_CARD.EXPIRE_TIME);
										  if(expireTime==null || !CardUtil.isCardExpired(expireTime)) {
											  cardList.add(new AvailableMemberCardVo(record.get(MEMBER_CARD.ID),record.get(MEMBER_CARD.CARD_TYPE),record.get(MEMBER_CARD.CARD_NAME)));
										  }
										  }
									  );
		return cardList;
	}


	/**
	 * 处理会员获取会员卡详情
	 * @param userId
	 * @param param
	 * @return
	 */
	public List<UserCardDetailVo> getAllUserCardDetail(UserCardDetailParam param) {
		Result<Record> allUserCardDetail = memberDao.getAllUserCardDetailSql(param);
		return allUserCardDetail.into(UserCardDetailVo.class);
		
	}
	
	/**
	 * 获得用户手机号
	 * @param userId
	 * @return
	 */
	public UserImportDetailRecord getUserByMobile(String mobile) {
		
		return db().selectFrom(USER_IMPORT_DETAIL)
				.where(USER_IMPORT_DETAIL.MOBILE.eq(mobile)
						.and(USER_IMPORT_DETAIL.ERROR_MSG.isNull().or(USER_IMPORT_DETAIL.ERROR_MSG.eq(""))))
				.orderBy(USER_IMPORT_DETAIL.ID.desc()).fetchAny();
	}
	
	public void updateUserDetail(UserDetailRecord record) {
		memberDao.updateUserDetail(record);
	}
	

}
