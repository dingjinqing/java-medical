package com.vpu.mp.service.shop.market.integration;

import static com.vpu.mp.db.shop.tables.GroupIntegrationDefine.GROUP_INTEGRATION_DEFINE;
import static com.vpu.mp.db.shop.tables.GroupIntegrationList.GROUP_INTEGRATION_LIST;

import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.jooq.SelectConditionStep;
import org.jooq.SelectWhereStep;
import org.jooq.impl.DSL;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.GroupIntegrationDefineRecord;
import com.vpu.mp.db.shop.tables.records.GroupIntegrationListRecord;
import com.vpu.mp.db.shop.tables.records.UserRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.decoration.module.ModuleGroupIntegration;
import com.vpu.mp.service.pojo.shop.market.integration.ActSelectList;
import com.vpu.mp.service.pojo.shop.market.integration.ActivityCopywriting;
import com.vpu.mp.service.pojo.shop.market.integration.ActivityInfo;
import com.vpu.mp.service.pojo.shop.market.integration.CanApplyPinInteVo;
import com.vpu.mp.service.pojo.shop.market.integration.GroupIntegrationAnalysisListVo;
import com.vpu.mp.service.pojo.shop.market.integration.GroupIntegrationAnalysisParam;
import com.vpu.mp.service.pojo.shop.market.integration.GroupIntegrationAnalysisVo;
import com.vpu.mp.service.pojo.shop.market.integration.GroupIntegrationDefineEditVo;
import com.vpu.mp.service.pojo.shop.market.integration.GroupIntegrationDefineEnums;
import com.vpu.mp.service.pojo.shop.market.integration.GroupIntegrationDefinePageParam;
import com.vpu.mp.service.pojo.shop.market.integration.GroupIntegrationDefineParam;
import com.vpu.mp.service.pojo.shop.market.integration.GroupIntegrationDefineVo;
import com.vpu.mp.service.pojo.shop.market.integration.GroupIntegrationListPojo;
import com.vpu.mp.service.pojo.shop.market.integration.GroupIntegrationMaVo;
import com.vpu.mp.service.pojo.shop.market.integration.GroupIntegrationPojo;
import com.vpu.mp.service.pojo.shop.market.integration.GroupIntegrationShareQrCodeVo;
import com.vpu.mp.service.pojo.shop.market.integration.GroupIntegrationVo;
import com.vpu.mp.service.pojo.shop.member.account.ScoreParam;
import com.vpu.mp.service.pojo.shop.operation.RecordContentTemplate;
import com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum;
import com.vpu.mp.service.pojo.shop.qrcode.QrCodeTypeEnum;
import com.vpu.mp.service.pojo.wxapp.market.GroupIntegration.CanPinInte;
import com.vpu.mp.service.pojo.wxapp.market.GroupIntegration.GroupStartParam;
import com.vpu.mp.service.pojo.wxapp.market.GroupIntegration.GroupStartVo;
import com.vpu.mp.service.shop.image.QrCodeService;
import com.vpu.mp.service.shop.member.ScoreService;
import com.vpu.mp.service.shop.operation.RecordAdminActionService;


/**
 * @author huangronggang
 * @date 2019年8月5日
 * 组团瓜分积分
 */
@Service
public class GroupIntegrationService extends ShopBaseService {
	
	@Autowired
	public GroupIntegrationListService groupIntegrationList;
	@Autowired
	public QrCodeService qrCode;
	@Autowired
	private RecordAdminActionService recordService;
	@Autowired
	private ScoreService scoreService;

    /**是否开团24小时自动开奖*/
    public static final Byte IS_DAY_DIVIDE_Y = 1;
    public static final Byte IS_DAY_DIVIDE_N = 0;
    /**活动状态：启用*/
    public static final Byte STATUS_NORMAL = 1;
    
    /** 正常*/
	public static final Byte STATUS_ZERO = 0;
	/** 该活动不存在 */
	public static final Byte STATUS_ONE = 1;
	/** 该活动已停用 */
	public static final Byte STATUS_TWO = 2;
	/** 该活动未开始 */
	public static final Byte STATUS_THREE = 3;
	/** 该活动已结束 */
	public static final Byte STATUS_FOUR = 4;
	/** 该团已结束 */
	public static final Byte STATUS_FIVE = 5;
	/** 该团已满员 */
	public static final Byte STATUS_SIX = 6;
	/** 您已经参与过x个活动，达到上限了哦！ */
	public static final Byte STATUS_SEVEN = 7;
	/**参团失败 */
	public static final Byte STATUS_EIGHT = 7;
    

    public List<ActSelectList> getActSelectList() {
        List<ActSelectList> result = db().select(GROUP_INTEGRATION_DEFINE.ID,GROUP_INTEGRATION_DEFINE.NAME)
            .from(GROUP_INTEGRATION_DEFINE)
            .where(GROUP_INTEGRATION_DEFINE.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))
            .and(GROUP_INTEGRATION_DEFINE.STATUS.eq(STATUS_NORMAL))
            .and(GROUP_INTEGRATION_DEFINE.START_TIME.lessThan(Util.currentTimeStamp())
                .and(GROUP_INTEGRATION_DEFINE.END_TIME.greaterThan(Util.currentTimeStamp()))).orderBy(GROUP_INTEGRATION_DEFINE.ID.desc())
            .fetchInto(ActSelectList.class);
        return result;
    }

	/**
	 * 分页查询瓜分积分活动列表
	 * @param pageParam
	 * @return
	 */
	public PageResult<GroupIntegrationDefineVo> getPageList(GroupIntegrationDefinePageParam pageParam){
		SelectWhereStep<?> selectFrom = db().selectFrom(GROUP_INTEGRATION_DEFINE);
		SelectConditionStep<?> step = buildOptions(selectFrom,pageParam);
		PageResult<GroupIntegrationDefineVo> pageResult = getPageResult(step, pageParam.getCurrentPage(), pageParam.getPageRows(), GroupIntegrationDefineVo.class);
		List<GroupIntegrationDefineVo> dataList = pageResult.getDataList();
		if(dataList == null) {
			return pageResult;
		}
		//填充 团数量、参与人数 、消耗积分 
		Map<Integer, ActivityInfo> activictyInfoMap = getGroupIntegrationActivictyInfo(dataList);
		passInfo(dataList,activictyInfoMap);
		
		return pageResult;
	}
	/**
	 * 根据iD 查询指定的瓜分积分活动
	 * @param id
	 * @return
	 */
	public GroupIntegrationVo selectGroupIntegrationDefineById(Integer id) {
		if(id == null) {
			return null;
		}
		GroupIntegrationDefineEditVo fetchOneInto = db().selectFrom(GROUP_INTEGRATION_DEFINE)
			.where(GROUP_INTEGRATION_DEFINE.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))
			.and(GROUP_INTEGRATION_DEFINE.ID.eq(id))
			.fetchOneInto(GroupIntegrationDefineEditVo.class);
		if(fetchOneInto == null) {
			return null;
		}
		String activityCopywriting = fetchOneInto.getActivityCopywriting();
		ActivityCopywriting parseJson=null;
		if(StringUtils.isNotEmpty(activityCopywriting)) {
			parseJson = Util.parseJson(activityCopywriting, ActivityCopywriting.class);
		}
		GroupIntegrationVo vo=new GroupIntegrationVo();
		BeanUtils.copyProperties(fetchOneInto, vo);
		vo.setActivityCopywriting(parseJson);
		return vo;
		
	}
	/**
	 * 根据iD 查询指定的瓜分积分活动
	 * @param id
	 * @return
	 */
	public GroupIntegrationDefineRecord selectDefineById(Integer id) {
		if(id == null) {
			return null;
		}
		GroupIntegrationDefineRecord fetchOne = db().selectFrom(GROUP_INTEGRATION_DEFINE)
			.where(GROUP_INTEGRATION_DEFINE.ID.eq(id))
			.and(GROUP_INTEGRATION_DEFINE.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))
			.fetchOne();
		
		return fetchOne;
		
	}
	
	/**
	 *  新增一个瓜分积分活动 
	 * @param param
	 * @return
	 */
	public JsonResultCode insertDefine(GroupIntegrationDefineParam param) {
		Integer inteGroup = param.getInteGroup();
		Short limitAmount = param.getLimitAmount();
		Integer inteTotal = param.getInteTotal();
		if(inteGroup<limitAmount) {
			//瓜分积分数需要大于成团人数
			return JsonResultCode.GROUP_INTEGRATION_INTE;
		}
		if(inteTotal>0&&inteGroup>inteTotal) {
			//单团瓜分积分数不能大于总积分数
			return JsonResultCode.GROUP_INTEGRATION_TOTAL;
		}
		Double paramNum = calculateParamNum(inteGroup,param.getLimitAmount());
		ActivityCopywriting activityCopywriting = param.getActivityCopywriting();
		String json=null;
		if(activityCopywriting!=null) {
			json = Util.toJson(activityCopywriting);
		}
		GroupIntegrationDefineRecord record = db().newRecord(GROUP_INTEGRATION_DEFINE,param);
		record.setStatus(GroupIntegrationDefineEnums.Status.NORMAL.value());
		record.setDelFlag(DelFlag.NORMAL_VALUE);
		record.setInteRemain(param.getInteTotal());
		record.setIsContinue(GroupIntegrationDefineEnums.IsContinue.TRUE.value());
		record.setParamN(paramNum);
		record.setShopId(getShopId());
		record.setActivityCopywriting(json);
		int executeInsert = db().executeInsert(record);
		if(executeInsert>0) {
			logger().info("【组队瓜分积分】 添加活动"+param.getName()+" 创建成功");
			recordService.insertRecord(Arrays.asList(new Integer[] { RecordContentTemplate.DIVIDE_INTEGRATION_ADD.code }), new String[] {param.getName()});
			return JsonResultCode.CODE_SUCCESS;
		}
		logger().info("【组队瓜分积分】 添加活动"+param.getName()+" 创建失败");
		return JsonResultCode.CODE_FAIL;
		
	}
	/**
	 * 根据ID删除一个瓜分积分活动
	 * @param id
	 * @return
	 */
	public int deleteDefine(Integer id) {
		int execute = db().update(GROUP_INTEGRATION_DEFINE)
			.set(GROUP_INTEGRATION_DEFINE.DEL_FLAG, DelFlag.DISABLE_VALUE)
			.set(GROUP_INTEGRATION_DEFINE.DEL_TIME, Timestamp.valueOf(LocalDateTime.now()))
			.where(GROUP_INTEGRATION_DEFINE.ID.eq(id))
			.execute();
		return execute;
	}
	
	/**
	 * 更新指定ID的瓜分积分活动
	 * @param param
	 * @return
	 */
	public int updateDefine(GroupIntegrationDefineParam param) {
		int execute = db().update(GROUP_INTEGRATION_DEFINE)
			.set(GROUP_INTEGRATION_DEFINE.NAME,param.getName())
			.set(GROUP_INTEGRATION_DEFINE.ADVERTISE,param.getAdvertise())
			.set(GROUP_INTEGRATION_DEFINE.START_TIME,param.getStartTime())
			.set(GROUP_INTEGRATION_DEFINE.END_TIME,param.getEndTime())
			.set(GROUP_INTEGRATION_DEFINE.INTE_TOTAL,param.getInteTotal())
			.set(GROUP_INTEGRATION_DEFINE.INTE_GROUP,param.getInteGroup())
			.set(GROUP_INTEGRATION_DEFINE.LIMIT_AMOUNT,param.getLimitAmount())
			.set(GROUP_INTEGRATION_DEFINE.JOIN_LIMIT,param.getJoinLimit())
			.set(GROUP_INTEGRATION_DEFINE.DIVIDE_TYPE,param.getDivideType())
			.set(GROUP_INTEGRATION_DEFINE.IS_DAY_DIVIDE,param.getIsDayDivide())
			.where(GROUP_INTEGRATION_DEFINE.ID.eq(param.getId()))
			.and(GROUP_INTEGRATION_DEFINE.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))
			.execute();
		return execute;	
	}
	/**
	 * 获取分享的小程序码url
	 * @param actId
	 * @return
	 */
	public GroupIntegrationShareQrCodeVo getMaQrCode(Integer actId,Integer inviteUser,Integer groupId) {
		GroupIntegrationDefineRecord record = selectDefineById(actId);
		GroupIntegrationShareQrCodeVo qrCodeVo = null;
		if(record != null) {
			String pathParam=null;
			if(inviteUser!=null) {
				pathParam=String.format("pid=%d&iuser=%d&gid=%d", actId, inviteUser, groupId);				
			}else {
				pathParam="pid="+actId+"&iuser=&gid=";
			}
			logger().info("pathParam："+pathParam);
			String imageUrl=qrCode.getMpQrCode(QrCodeTypeEnum.PARTATION_INTEGRAL,pathParam);
			qrCodeVo = new GroupIntegrationShareQrCodeVo();
			qrCodeVo.setImgUrl(imageUrl);
			qrCodeVo.setPageUrl(QrCodeTypeEnum.PARTATION_INTEGRAL.getPathUrl(pathParam));
		}
		return qrCodeVo;
	}
	
	/**
	 * 更新指定ID的瓜分积分活动
	 * @param param
	 * @return
	 */
	public int updateDefine(GroupIntegrationDefineRecord param) {
		return db().executeUpdate(param);
	}
	/**
	 * 启用活动，或者停止活动
	 * @param id
	 * @param status
	 * @return
	 */
	public int changDefineStatus(Integer id,Byte status) {
		GroupIntegrationDefineRecord record = selectDefineById(id);
		if(record==null) {
			logger().info("瓜分积分活动id：{}，不存在",id);
			return 0;
		}
		if(record.getStatus().equals(status)) {
			logger().info("瓜分积分活动id：{}，状态{}相同",id,status);
			return 0;
		}
		if(GroupIntegrationDefineEnums.Status.NORMAL.value().equals(status)) {
			if(record.getEndTime().before(Timestamp.valueOf(LocalDateTime.now()))){
//				这个活动已经过期了不能再启用了
				logger().info("瓜分积分活动id：{}，这个活动已经过期",id);
				return 0;
			}
		}
		record.setStatus(status);
		int result = db().executeUpdate(record);
		//停用操作需要进行奖池分配
		List<GroupIntegrationListRecord> list = groupIntegrationList.getOnGoingGrouperInfo(id);
		list.forEach(item->groupIntegrationList.asyncSuccessGroupIntegration(item.getGroupId(), item.getInteActivityId()));
		return result;
	}

	/**
	 * 刷新剩余积分 ：剩余积分 = 当前剩余积分 - 团的积分
	 * @param actId
	 */
	public void refreshRemainInte(Integer actId) {
		db().update(GROUP_INTEGRATION_DEFINE)
			.set(GROUP_INTEGRATION_DEFINE.INTE_REMAIN,GROUP_INTEGRATION_DEFINE.INTE_REMAIN.minus(GROUP_INTEGRATION_DEFINE.INTE_GROUP))
			.where(GROUP_INTEGRATION_DEFINE.ID.eq(actId))
			.execute();
	}
	/**
	 * 
	 * @param inteGroup  每一个团的总积分
	 * @param limitAmount    成团人数
	 * @return
	 */
	private Double calculateParamNum(Integer inteGroup, double limitAmount) {
		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(2);
		nf.setRoundingMode(RoundingMode.HALF_UP);
		double temp = Math.pow(inteGroup,1/limitAmount );
		String formatParamNum = nf.format(temp);
		return Double.parseDouble(formatParamNum);
	}

	/**
	 * 将团数量、参与人数 、消耗积分 信息填充到传入的对象中，并设置活动是否过期标志位
	 * @param dataList
	 * @param activictyInfoMap
	 */
	private void passInfo(List<GroupIntegrationDefineVo> dataList, Map<Integer, ActivityInfo> activictyInfoMap) {
		for (GroupIntegrationDefineVo defineVo : dataList) {
//			设置活动是否过期
			defineVo.isExpired();
			
			ActivityInfo info = activictyInfoMap.get(defineVo.getId());
			if(info == null) {
				continue;
			}
			defineVo.setUseIntegration(info.getUseIntegration());
			defineVo.setInteUserSum(info.getInteUserSum());
			defineVo.setInteGroupSum(info.getInteGroupSum());
		}
	}
	


	/**
	 * 查找 某些活动 的团数量、参与人数 、消耗积分信息 
	 * @param dataList
	 * @return key为活动ID，vlaue 为活动信息
	 */
	private Map<Integer, ActivityInfo> getGroupIntegrationActivictyInfo(List<GroupIntegrationDefineVo> dataList) {
		if(dataList == null||dataList.size()==0) {
			return new HashMap<Integer,ActivityInfo>(0);
		}
		List<Integer> groupIntegrationDefineIdList = new ArrayList<>(dataList.size());
		dataList.forEach(item->groupIntegrationDefineIdList.add(item.getId()));
		List<ActivityInfo> result = db().select(GROUP_INTEGRATION_LIST.INTE_ACTIVITY_ID.as("inteActivityId"), DSL.sum(GROUP_INTEGRATION_LIST.INTEGRATION).as("useIntegration"), DSL.count(GROUP_INTEGRATION_LIST.ID).as("inteUserSum"), DSL.countDistinct(GROUP_INTEGRATION_LIST.GROUP_ID).as("inteGroupSum"))
			.from(GROUP_INTEGRATION_LIST)
			.where(GROUP_INTEGRATION_LIST.INTE_ACTIVITY_ID.in(groupIntegrationDefineIdList))
			.groupBy(GROUP_INTEGRATION_LIST.INTE_ACTIVITY_ID)
			.fetchInto(ActivityInfo.class);
		
		HashMap<Integer,ActivityInfo> fetchMap =new HashMap<Integer, ActivityInfo>(result.size());
		result.forEach(item->fetchMap.put(item.getInteActivityId(),item));
		return fetchMap;
	}
	
	/**
	 * @param selectFrom
	 * @param pageParam
	 * @return
	 */
	private SelectConditionStep<?> buildOptions(SelectWhereStep<?> selectFrom, GroupIntegrationDefinePageParam pageParam) {
		SelectConditionStep<?> step = selectFrom.where(GROUP_INTEGRATION_DEFINE.DEL_FLAG.eq(DelFlag.NORMAL_VALUE));
		step.orderBy(GROUP_INTEGRATION_DEFINE.ID.desc());
		switch(pageParam.getType()) {
			case GroupIntegrationDefineEnums.QueryType.UNSTARTED :
				step.and(GROUP_INTEGRATION_DEFINE.START_TIME.gt(Timestamp.valueOf(LocalDateTime.now())));
				break;
			case GroupIntegrationDefineEnums.QueryType.OVERDUE:
				step.and(GROUP_INTEGRATION_DEFINE.END_TIME.lt(Timestamp.valueOf(LocalDateTime.now())));
				break;
			case GroupIntegrationDefineEnums.QueryType.STOPPED:
				step.and(GROUP_INTEGRATION_DEFINE.STATUS.eq(GroupIntegrationDefineEnums.Status.STOPPED.value()));
				break;
			case GroupIntegrationDefineEnums.QueryType.UNDER_WAY:
				step.and(GROUP_INTEGRATION_DEFINE.START_TIME.le(Timestamp.valueOf(LocalDateTime.now())))
					.and(GROUP_INTEGRATION_DEFINE.END_TIME.ge(Timestamp.valueOf(LocalDateTime.now())));
				break;
			default :break;
		}
		return step;
	}

    /**
     * 小程序装修瓜分积分模块显示异步调用
     * @param moduleGroupIntegration
     * @return
     */
    public ModuleGroupIntegration getPageIndexGroupIntegration(ModuleGroupIntegration moduleGroupIntegration,int userId){
        GroupIntegrationDefineRecord groupIntegrationDefine = getOneInfoByIdNoInto(moduleGroupIntegration.getActId());
        if(groupIntegrationDefine != null){
            moduleGroupIntegration.setName(groupIntegrationDefine.getName());
            moduleGroupIntegration.setLimitAmount(groupIntegrationDefine.getLimitAmount());
            moduleGroupIntegration.setInteTotal(groupIntegrationDefine.getInteTotal());
            moduleGroupIntegration.setInteGroup(groupIntegrationDefine.getInteGroup());
            moduleGroupIntegration.setStartTime(groupIntegrationDefine.getStartTime());
            moduleGroupIntegration.setEndTime(groupIntegrationDefine.getEndTime());
        }
        moduleGroupIntegration.setHideTime(moduleGroupIntegration.getHideTime() == null ? 0 :moduleGroupIntegration.getHideTime());
        moduleGroupIntegration.setHideActive(moduleGroupIntegration.getHideActive() == null ? 0 : moduleGroupIntegration.getHideActive());

        moduleGroupIntegration.setCanPin(canApplyPinInte(groupIntegrationDefine,userId));
        return moduleGroupIntegration;
    }

    /**
     * 校验活动userId可用状态
     * @param groupIntegrationDefine
     * @param userId
     * @return 0正常，1活动不存在，2活动已停用，3活动未开始，4活动已结束
     */
    private byte canApplyPinInte(GroupIntegrationDefineRecord groupIntegrationDefine,int userId){
        if (groupIntegrationDefine == null){
            return 1;
        }
        if (groupIntegrationDefine.getStatus().equals(BaseConstant.ACTIVITY_STATUS_DISABLE)){
            return 2;
        }
        if (groupIntegrationDefine.getStartTime().after(DateUtil.getLocalDateTime())){
            return 3;
        }
        if (groupIntegrationDefine.getEndTime().before(DateUtil.getLocalDateTime())
            || (groupIntegrationDefine.getInteRemain() < groupIntegrationDefine.getInteGroup() && groupIntegrationDefine.getInteTotal() > 0 && groupIntegrationDefine.getIsDayDivide().equals(IS_DAY_DIVIDE_N))
            || (groupIntegrationDefine.getInteRemain() <= 0 && groupIntegrationDefine.getInteTotal() > 0 && groupIntegrationDefine.getIsDayDivide().equals(IS_DAY_DIVIDE_Y) && groupIntegrationList.getExistGroup(userId,groupIntegrationDefine.getId()) == 0)){
            return 4;
        }
        return 0;
    }
    
	public CanApplyPinInteVo canApplyPinInte(Integer pinInteId, Integer groupId, Integer userId, Integer type) {
		GroupIntegrationDefineRecord pinInteInfo = getOneInfoByIdNoInto(pinInteId);
		CanApplyPinInteVo vo = null;
		if (pinInteInfo == null) {
			return new CanApplyPinInteVo(STATUS_ONE, "该活动不存在");
		}
		if (pinInteInfo.getStatus().equals(IS_DAY_DIVIDE_N)) {
			return new CanApplyPinInteVo(STATUS_TWO, "该活动已停用");
		}
		if (pinInteInfo.getStartTime().after(DateUtil.getLocalDateTime())) {
			return new CanApplyPinInteVo(STATUS_THREE, "该活动未开始");
		}
		if(groupId!=null) {
			if (pinInteInfo.getEndTime().before(DateUtil.getLocalDateTime())
					|| pinInteInfo.getIsContinue().equals(IS_DAY_DIVIDE_N)
					|| (pinInteInfo.getInteRemain().equals(0) && pinInteInfo.getInteTotal() > 0)) {
				return new CanApplyPinInteVo(STATUS_FOUR, "该活动已结束");
			}
			
			List<GroupIntegrationMaVo> pinInteGroupInfo = groupIntegrationList.getPinIntegrationGroupDetail(pinInteId, groupId);
			if (Objects.equals(Integer.parseInt(String.valueOf(pinInteInfo.getLimitAmount())),
					pinInteGroupInfo.size())) {
				return new CanApplyPinInteVo(STATUS_SIX, "该团已满员");
			}
			if(pinInteGroupInfo.get(0).getStatus()>0) {
				return new CanApplyPinInteVo(STATUS_FIVE, "该团已结束");
			}
			int joinNum = groupIntegrationList.getJoinNum(pinInteId, userId);
			if(joinNum==pinInteInfo.getJoinLimit()&&pinInteInfo.getJoinLimit()>0) {
				return new CanApplyPinInteVo(STATUS_SEVEN, "您已经参与过"+joinNum+"个活动，达到上限了哦！");
			}
		}else {
			Integer inteRemain = pinInteInfo.getInteRemain();
			Integer inteGroup = pinInteInfo.getInteGroup();
			Integer inteTotal = pinInteInfo.getInteTotal();
			Byte isDayDivide = pinInteInfo.getIsDayDivide();
			int existGroup = groupIntegrationList.getExistGroup(userId, pinInteId);
			if(type!=null) {
				if (pinInteInfo.getEndTime().before(DateUtil.getLocalDateTime())
						|| ((inteRemain < inteGroup && inteTotal > 0 && isDayDivide == IS_DAY_DIVIDE_N)
								|| (inteRemain <= 0 && inteTotal > 0 && isDayDivide == IS_DAY_DIVIDE_Y))
								&& existGroup == 0) {
					return new CanApplyPinInteVo(STATUS_FOUR, "该活动已结束");
				}
			}else {
				if (pinInteInfo.getEndTime().before(DateUtil.getLocalDateTime())
						|| ((inteRemain < inteGroup && inteTotal > 0 && isDayDivide == IS_DAY_DIVIDE_N)
								|| (inteRemain <= 0 && inteTotal > 0 && isDayDivide == IS_DAY_DIVIDE_Y))) {
					return new CanApplyPinInteVo(STATUS_FOUR, "该活动已结束");
				}
			}
		}
		return new CanApplyPinInteVo(STATUS_ZERO, null);
	}
    
    
	public GroupIntegrationAnalysisVo getAnalysis(GroupIntegrationAnalysisParam param) {
		Integer actId = param.getActId();
		Timestamp startTime = param.getStartTime();
		Timestamp endTime = param.getEndTime();
		GroupIntegrationPojo fetch = getOneInfoById(actId);
		if (fetch == null) {
			return null;
		}
		if (null == startTime) {
			startTime = fetch.getStartTime();
		}
		if (null == endTime) {
			endTime = fetch.getEndTime();
			if (endTime.after(DateUtil.getLocalDateTime())) {
				// 结束日期晚于今天
				endTime = DateUtil.getLocalDateTime();
			}
		}

		return getPinIntegrationInfo(actId, startTime, endTime);
	}

	public GroupIntegrationPojo getOneInfoById(Integer actId) {
		GroupIntegrationPojo fetch = db().selectFrom(GROUP_INTEGRATION_DEFINE)
				.where(GROUP_INTEGRATION_DEFINE.ID.eq(actId)).fetchOneInto(GroupIntegrationPojo.class);
		return fetch;
	}
    
	public GroupIntegrationDefineRecord getOneInfoByIdNoInto(Integer actId) {
		GroupIntegrationDefineRecord fetch = db().selectFrom(GROUP_INTEGRATION_DEFINE)
				.where(GROUP_INTEGRATION_DEFINE.ID.eq(actId)).fetchAny();
		return fetch;
	}
	public GroupIntegrationAnalysisVo getPinIntegrationInfo(Integer actId, Timestamp startTime,
			Timestamp endTime) {
		GroupIntegrationAnalysisVo gbaVo = new GroupIntegrationAnalysisVo();
		gbaVo.setEndTime(endTime);
		gbaVo.setStartTime(startTime);
		List<GroupIntegrationListPojo> recordList = db().selectFrom(GROUP_INTEGRATION_LIST)
				.where(GROUP_INTEGRATION_LIST.INTE_ACTIVITY_ID.eq(actId)
						.and(GROUP_INTEGRATION_LIST.START_TIME.between(startTime, endTime)))
				.fetchInto(GroupIntegrationListPojo.class);
		if(recordList.size()==0) {
			logger().info("没有数据");
			return gbaVo;
		}
		String format = DateUtil.DATE_FORMAT_SIMPLE;
		for (GroupIntegrationListPojo groupIntegrationListPojo : recordList) {
			groupIntegrationListPojo.setStartDate(DateUtil.dateFormat(format, groupIntegrationListPojo.getStartTime()));
		}
		byte one = 1;
		int integrationNum=0;
		int joinNum=0;
		int successUserNum=0;
		int newUser=0;
		List<String> betweenTime = getBetweenTime(startTime, endTime);
		List<GroupIntegrationAnalysisListVo> returnVo = new ArrayList<GroupIntegrationAnalysisListVo>();
		for (String date : betweenTime) {
			GroupIntegrationAnalysisListVo vo = new GroupIntegrationAnalysisListVo();
			vo.setDateTime(date);
			for (GroupIntegrationListPojo pojo : recordList) {
				if(pojo.getStartDate().equals(date)) {
					if (pojo.getIsNew().equals(one)) {
						// 是新用户
						vo.setNewUser(vo.getNewUser() == 0 ? 1 : vo.getNewUser() + 1);
					}
					if (pojo.getStatus().equals(one)) {
						// 1:拼团成功
						vo.setJoinNum(vo.getJoinNum() == 0 ? 1 : vo.getJoinNum() + 1);
						vo.setSuccessUserNum(vo.getSuccessUserNum() == 0 ? 1 : vo.getSuccessUserNum() + 1);
					} else {
						// 0: 拼团中 2:拼团失败
						vo.setJoinNum(vo.getJoinNum() == 0 ? 1 : vo.getJoinNum() + 1);
					}
					vo.setIntegrationNum(vo.getIntegrationNum() + pojo.getIntegration());
				}
			}
			integrationNum=integrationNum+vo.getIntegrationNum();
			joinNum=joinNum+vo.getJoinNum();
			successUserNum=successUserNum+vo.getSuccessUserNum();
			newUser=newUser+vo.getNewUser();
			returnVo.add(vo);
		}
		gbaVo.setList(returnVo);
		gbaVo.setIntegrationNum(integrationNum);
		gbaVo.setJoinNum(joinNum);
		gbaVo.setSuccessUserNum(successUserNum);
		gbaVo.setNewUser(newUser);
		return gbaVo;
	}
	
	private List<String> getBetweenTime(Timestamp startTime,Timestamp endTime){
		String format = DateUtil.DATE_FORMAT_SIMPLE;
		String startDate = DateUtil.dateFormat(format,startTime);
		String endDate = DateUtil.dateFormat(format,endTime);
		logger().info("开始时间："+startDate+"  结束时间："+endDate);
		List<String> list=new ArrayList<String>();
		long add=24*60*60*1000L;
		list.add(startDate);
		while (!startDate.equals(endDate)) {
			//System.out.println("插入："+startDate);
			long time = startTime.getTime();
			time=time+add;
			startTime = new Timestamp(time);
			startDate = DateUtil.dateFormat(format,startTime);
			list.add(startDate);
		}
		return list;
	}
	
	
	
	public GroupStartVo startPinIntegrationGroup(GroupStartParam param,Integer userId) {
		Integer pinInteId = param.getPinInteId();
		Integer groupId = param.getGroupId()== null ? 0 : param.getGroupId();
		Integer inviteUser = param.getInviteUser()== null ? 0 : param.getInviteUser();
		GroupIntegrationDefineRecord record = getOneInfoByIdNoInto(pinInteId);
		if(record==null) {
			return null;
		}
		GroupIntegrationPojo pinInteInfo = record.into(GroupIntegrationPojo.class);
		GroupStartVo vo=new GroupStartVo();
		BeanUtils.copyProperties(pinInteInfo, vo);
		CanPinInte canPinInte = new CanPinInte();
		long endTime = pinInteInfo.getEndTime().getTime();
		long nowTime = DateUtil.getLocalDateTime().getTime();
		long remainingTime = endTime > nowTime ? endTime - nowTime : 0L;
		logger().info("剩余时间：{}", remainingTime);
		canPinInte.setRemainingTime(remainingTime);
		if (groupId != 0 && inviteUser != 0) {
			logger().info("参加拼团，groupId：{}，inviteUser：{}", groupId, inviteUser);
			List<GroupIntegrationMaVo> groupInfo = groupIntegrationList.getPinIntegrationGroupDetail(pinInteId, groupId);
			for (GroupIntegrationMaVo gIntegrationMaVo : groupInfo) {
				if(gIntegrationMaVo.getUserId().equals(userId)) {
					vo.setGroupId(gIntegrationMaVo.getGroupId());
					canPinInte.setStatus(IS_DAY_DIVIDE_N);
					//TODO 国际化
					canPinInte.setMsg("已在团中");
					vo.setCanPin(canPinInte);
					logger().info("已在团中");
					return vo;
				}
			}
			logger().info("进入参加活动");
			UserRecord userPinInfo = groupIntegrationList.getinviteUser(pinInteId, userId);
			boolean haveJoinGroup = groupIntegrationList.haveJoinGroup(userId);
			int addPinGroup=0;
			if (userPinInfo != null && !haveJoinGroup) {
				logger().info("用户id:{},第一次参加活动",userId);
				canPinInte.setIsNew(IS_DAY_DIVIDE_Y);
				 addPinGroup = groupIntegrationList.addPinGroup(groupId, userId, pinInteId, IS_DAY_DIVIDE_N, IS_DAY_DIVIDE_Y, inviteUser);
			}else {
				canPinInte.setIsNew(IS_DAY_DIVIDE_N);
				 addPinGroup = groupIntegrationList.addPinGroup(groupId, userId, pinInteId, IS_DAY_DIVIDE_N, IS_DAY_DIVIDE_Y, inviteUser);
			}
			if (addPinGroup==0) {
				canPinInte.setStatus(IS_DAY_DIVIDE_N);
				//TODO 国际化
				canPinInte.setMsg("参团失败");
				vo.setCanPin(canPinInte);
				return vo;
			} else {
				logger().info("用户：{}，参加活动INTE_ACTIVITY_ID：{}，groupId：{}", userId, pinInteId, groupId);
				// 存取新的can_integration
				GroupIntegrationListRecord inviteInfo = groupIntegrationList.getUserIntegrationInfo(userId, pinInteId,groupId);
				Short inviteNum = inviteInfo.getInviteNum();
				inviteInfo.setInviteNum(inviteNum++);
				int update = inviteInfo.update();
				logger().info("更新inviNum，结果：{}", update);
				int num = groupInfo.size()+1;
				int canIntegration=0;
				if(num<pinInteInfo.getLimitAmount()) {
					//floor(pow($pinInte_info['param_n'],$num)-$pinInte_info['param_n']);
					Double paramN = pinInteInfo.getParamN();
					double floor = Math.floor(Math.pow(paramN, Double.parseDouble(String.valueOf(num)))-paramN);
					canIntegration = new Double(floor).intValue();
				}else {
					canIntegration=pinInteInfo.getInteGroup();
				}
				Byte isDayDivide = pinInteInfo.getIsDayDivide();
				if (isDayDivide.equals(IS_DAY_DIVIDE_Y)
						&& (canIntegration - groupInfo.get(0).getCanIntegration()) > pinInteInfo.getInteRemain()
						&& pinInteInfo.getInteTotal() > 0) {
					canIntegration=groupInfo.get(0).getCanIntegration()+canIntegration+pinInteInfo.getInteRemain();
				}
				int execute = db().update(GROUP_INTEGRATION_LIST)
						.set(GROUP_INTEGRATION_LIST.CAN_INTEGRATION, canIntegration)
						.where(GROUP_INTEGRATION_LIST.GROUP_ID.eq(groupId)
								.and(GROUP_INTEGRATION_LIST.INTE_ACTIVITY_ID.eq(pinInteId)))
						.execute();
				logger().info("活动id：{},团id：{}，更新可瓜分积分数为：{};结果：{}", pinInteId, groupId, canIntegration, execute);
				int inteRemain=-1;
				if (isDayDivide.equals(IS_DAY_DIVIDE_Y) && pinInteInfo.getInteTotal() > 0) {
					inteRemain = pinInteInfo.getInteRemain() - (canIntegration - groupInfo.get(0).getCanIntegration());
					int execute2 = db().update(GROUP_INTEGRATION_DEFINE).set(GROUP_INTEGRATION_DEFINE.INTE_REMAIN,inteRemain).where(GROUP_INTEGRATION_DEFINE.ID.eq(pinInteId)).execute();
					logger().info("活动：{}更新剩余积分为：{};结果：{}",pinInteId,inteRemain,execute2);
				}
				if ((int) pinInteInfo.getLimitAmount() == num
						|| (isDayDivide.equals(IS_DAY_DIVIDE_Y) && inteRemain == 0 && pinInteInfo.getInteTotal() > 0)) {
					
				}
			}
			
		}else {
			int existGroup = groupIntegrationList.getExistGroup(userId, pinInteId);
			if(groupId.equals(existGroup)) {
				logger().info("user：{}，参团失败",userId);
				canPinInte.setStatus(STATUS_EIGHT);
				//TODO 国际化
				canPinInte.setMsg("参团失败");
			}
			
		}
		//0正常，1活动不存在，2活动已停用，3活动未开始，4活动已结束
		CanApplyPinInteVo canApplyPinInte = canApplyPinInte(pinInteId, groupId, userId, null);
		if(canApplyPinInte.getStatus()>0) {
			//vo.setGroupId(gIntegrationMaVo.getGroupId());
			canPinInte.setStatus(canApplyPinInte.getStatus());
			//TODO 国际化
			canPinInte.setMsg(canApplyPinInte.getMsg());
			vo.setCanPin(canPinInte);
			return vo;
		}
		return vo;
		
	}
	
	public boolean successPinIntegration(Integer groupId,Integer pinInteId) {
		GroupIntegrationDefineRecord pinInteInfo = getOneInfoByIdNoInto(pinInteId);
		List<GroupIntegrationMaVo> groupInfo = groupIntegrationList.getPinIntegrationGroupDetail(pinInteId, groupId);
		int userNum=groupInfo.size();
		int canIntegration=groupInfo.get(0).getCanIntegration();
		int haveDivide=0;
		int execute = db().update(GROUP_INTEGRATION_LIST)
				.set(GROUP_INTEGRATION_LIST.END_TIME, DateUtil.getLocalDateTime()).where(GROUP_INTEGRATION_LIST.GROUP_ID
						.eq(groupId).and(GROUP_INTEGRATION_LIST.INTE_ACTIVITY_ID.eq(pinInteId)))
				.execute();
		logger().info("活动id：{},团id：{}，更新结束时间结果：{}", pinInteId, groupId, execute);
		if(pinInteInfo.getIsDayDivide().equals(IS_DAY_DIVIDE_N)&&userNum<pinInteInfo.getLimitAmount()||canIntegration==0) {
			int execute2 = db().update(GROUP_INTEGRATION_LIST)
					.set(GROUP_INTEGRATION_LIST.STATUS, STATUS_TWO).where(GROUP_INTEGRATION_LIST.GROUP_ID
							.eq(groupId).and(GROUP_INTEGRATION_LIST.INTE_ACTIVITY_ID.eq(pinInteId)))
					.execute();
			logger().info("活动id：{},团id：{}，更新状态为：{}；结果：{}", pinInteId, groupId, STATUS_TWO, execute2);
			logger().info("发送拼团失败的通知");
			//TODO 公众号拼团失败通知
		}else {
			if(pinInteInfo.getDivideType().equals(IS_DAY_DIVIDE_N)) {
				logger().info("DivideType为0");
				int getNumTotal=0;
				Map<GroupIntegrationMaVo,Integer> map=new HashMap<GroupIntegrationMaVo, Integer>();
				for (GroupIntegrationMaVo item : groupInfo) {
					int inviteNum = groupIntegrationList.getInviteNum(groupId, item.getUserId(), pinInteId);
					int inviteNewNum = groupIntegrationList.getInviteNewNum(groupId, item.getUserId());
					int selfNum=item.getIsNew()==STATUS_ONE?2:1;
					int getNum=selfNum+inviteNewNum+inviteNum;
					map.put(item, getNum);
					getNumTotal+=getNum;
				}
				int preInte=canIntegration/getNumTotal;
				for (GroupIntegrationMaVo item : groupInfo) {
					if (item.getIsGrouper().equals(STATUS_ZERO)) {
						int integration = preInte * map.get(item);
						haveDivide+=integration;
						execute = groupIntegrationList.updateIntegration(item.getId(), integration);
						logger().info("更新id:{}；的INTEGRATION为{}",item.getId(),integration);
						if(execute==0) {
							return false;
						}
					}
				}
				int grouperInte = canIntegration-haveDivide;
				execute = groupIntegrationList.updateGroupperIntegration(pinInteId, groupId, grouperInte);
				logger().info("更新活动：{}；团：{}的团长的积分为{}",pinInteId, groupId, grouperInte);
				if(execute==0) {
					return false;
				}
				
			}else if(pinInteInfo.getDivideType().equals(IS_DAY_DIVIDE_Y)) {
				logger().info("DivideType为1");
				int preInte= canIntegration/userNum;
				int grouperInte = canIntegration-preInte*(userNum-1);
				transaction(() -> {
					groupIntegrationList.batchUpdateIntegeration(pinInteId, groupId, preInte);
					groupIntegrationList.updateGroupperIntegration(pinInteId, groupId, grouperInte);
				});
			}else {
				List<Integer> numbers = range(0, canIntegration-1);
				//随机排序
				Collections.shuffle(numbers);
				List<Integer> result = numbers.subList(0, userNum-1);
				//升序
				Collections.sort(result);
				for (int i = 0; i < groupInfo.size(); i++) {
					int integration = 0;
					if (i == 0) {
						integration = result.get(i);
					}
					else if(i==userNum-1) {
						integration=canIntegration-result.get(i-1);
					}else {
						integration=result.get(i)-result.get(i-1);
					}
					execute = groupIntegrationList.updateIntegration(groupInfo.get(i).getId(), integration);
					if(execute==0) {
						return false;
					}
					logger().info("更新某一个团员{}参团获取的积分为{}",groupInfo.get(i).getId(),integration);
				}
			}
			execute = groupIntegrationList.setIntegrationListResult(pinInteId, groupId, STATUS_ONE);
			logger().info("更新拼团活动{}；组{}；状态为{}", pinInteId, groupId, STATUS_ONE);
			if(pinInteInfo.getDivideType().equals(IS_DAY_DIVIDE_N)&&pinInteInfo.getInteTotal()>0) {
				int num = pinInteInfo.getInteRemain() - pinInteInfo.getInteGroup();
				execute = db().update(GROUP_INTEGRATION_DEFINE).set(GROUP_INTEGRATION_DEFINE.INTE_REMAIN,num).where(GROUP_INTEGRATION_DEFINE.ID.eq(pinInteId)).execute();
				logger().info("更新剩余积分为{}；结果{}", num, execute);
			}
			List<GroupIntegrationMaVo> groupInfoNew = groupIntegrationList.getPinIntegrationGroupDetail(pinInteId, groupId);
			for (GroupIntegrationMaVo vo : groupInfoNew) {
				ScoreParam param=new ScoreParam();
				param.setUserId(vo.getUserId());
				param.setScore(vo.getIntegration());
				param.setRemarkData("瓜分积分");
				param.setDesc("pin_score");
				param.setChangeWay(36);
				try {
					scoreService.updateMemberScore(param, 0, RecordTradeEnum.TYPE_SCORE_GROUP_DIVIDING.val(), RecordTradeEnum.TRADE_FLOW_OUT.val());
				} catch (MpException e) {
					e.printStackTrace();
				}
			}
			//TODO 发送拼团成功通知
		}
		GroupIntegrationDefineRecord pinInteInfoNew = getOneInfoByIdNoInto(pinInteId);
		if ((pinInteInfoNew.getIsDayDivide().equals(IS_DAY_DIVIDE_Y) && pinInteInfoNew.getInteRemain().equals(0))
				|| (pinInteInfoNew.getIsDayDivide().equals(IS_DAY_DIVIDE_N)
						&& pinInteInfoNew.getInteRemain() < pinInteInfoNew.getInteGroup())&&pinInteInfoNew.getInteTotal()>0) {
			pinInteInfoNew.setIsContinue(IS_DAY_DIVIDE_N);
			int update = pinInteInfoNew.update();
			logger().info("活动{}更新为结束，结果{}",pinInteId,update);
		}
		
		return false;
	}
	
	private List<Integer> range(int start ,int end){
		if(end-start <= 0) {
			return null;
		}
		List<Integer> list = new ArrayList<Integer>();
		for (int i = start; i <=end; i++) {
			list.add(i);
		}
		return list;
	}
}