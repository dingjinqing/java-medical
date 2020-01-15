package com.vpu.mp.service.shop.market.integration;

import static com.vpu.mp.db.shop.tables.GroupIntegrationDefine.GROUP_INTEGRATION_DEFINE;
import static com.vpu.mp.db.shop.tables.GroupIntegrationList.GROUP_INTEGRATION_LIST;

import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.decoration.module.ModuleGroupIntegration;
import com.vpu.mp.service.pojo.shop.market.integration.*;
import org.jooq.SelectConditionStep;
import org.jooq.SelectWhereStep;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.GroupIntegrationDefineRecord;
import com.vpu.mp.db.shop.tables.records.GroupIntegrationListRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.qrcode.QrCodeTypeEnum;
import com.vpu.mp.service.shop.image.QrCodeService;

import lombok.Data;


/**
 * @author huangronggang
 * @date 2019年8月5日
 * 组团瓜分积分
 */
@Service
public class GroupIntegrationService extends ShopBaseService {
	
	@Autowired public GroupIntegrationListService groupIntegrationList;
    @Autowired public QrCodeService qrCode;

    /**是否开团24小时自动开奖*/
    public static final Byte IS_DAY_DIVIDE_Y = 1;
    public static final Byte IS_DAY_DIVIDE_N = 0;
    /**活动状态：启用*/
    public static final Byte STATUS_NORMAL = 1;

    public List<ActSelectList> getActSelectList() {
        List<ActSelectList> result = db().select(GROUP_INTEGRATION_DEFINE.ID,GROUP_INTEGRATION_DEFINE.NAME)
            .from(GROUP_INTEGRATION_DEFINE)
            .where(GROUP_INTEGRATION_DEFINE.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))
            .and(GROUP_INTEGRATION_DEFINE.STATUS.eq(STATUS_NORMAL))
            .and(GROUP_INTEGRATION_DEFINE.START_TIME.lessThan(Util.currentTimeStamp())
                .and(GROUP_INTEGRATION_DEFINE.END_TIME.greaterThan(Util.currentTimeStamp())))
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
	public GroupIntegrationDefineEditVo selectGroupIntegrationDefineById(Integer id) {
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
		return fetchOneInto;
		
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
	public int insertDefine(GroupIntegrationDefineParam param) {
		Double paramNum = calculateParamNum(param.getInteGroup(),param.getLimitAmount());
		
		GroupIntegrationDefineRecord record = new GroupIntegrationDefineRecord();
		record.setId(param.getId());
		record.setShopId(getShopId());
		record.setName(param.getName());
		record.setInteTotal(param.getInteTotal());
		record.setInteGroup(param.getInteGroup());
		record.setLimitAmount(param.getLimitAmount());
		record.setJoinLimit(param.getJoinLimit());
		record.setDivideType(param.getDivideType());
		record.setStartTime(param.getStartTime());
		record.setEndTime(param.getEndTime());
		record.setStatus(GroupIntegrationDefineEnums.Status.NORMAL.value());
		record.setDelFlag(DelFlag.NORMAL_VALUE);
		record.setInteRemain(param.getInteTotal());
		record.setIsDayDivide(param.getIsDayDivide());
		record.setIsContinue(GroupIntegrationDefineEnums.IsContinue.TRUE.value());
		record.setParamN(paramNum);
		record.setAdvertise(param.getAdvertise());
		db().executeInsert(record);
		return 0;
		
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
	public GroupIntegrationShareQrCodeVo getMaQrCode(Integer actId) {
		GroupIntegrationDefineRecord record = selectDefineById(actId);
		GroupIntegrationShareQrCodeVo qrCodeVo = null;
		if(record != null) {
			String pathParam="pinInte_id="+actId+"&invite_user=&group_id=";
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
		if(record.getStatus().equals(status)) {
			return 0;
		}
		if(GroupIntegrationDefineEnums.Status.NORMAL.value().equals(status)) {
			if(record.getEndTime().before(Timestamp.valueOf(LocalDateTime.now()))){
//				这个活动已经过期了不能再启用了
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
        GroupIntegrationDefineRecord groupIntegrationDefine = db().selectFrom(GROUP_INTEGRATION_DEFINE).where(GROUP_INTEGRATION_DEFINE.ID.eq(moduleGroupIntegration.getActId())).fetchAny();
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
}
@Data
class ActivityInfo{
	/**活动Id*/
	private Integer inteActivityId;
	/** 消耗积分 */
	private Integer useIntegration;
	/**参与人数 */
	private Integer inteUserSum;
	/** 	团数量 */
	private Integer inteGroupSum;
	/**
	 * @param inteActivityId
	 * @param useIntegration
	 * @param inteUserSum
	 * @param inteGroupSum
	 */
	public ActivityInfo(Integer inteActivityId, Integer useIntegration, Integer inteUserSum, Integer inteGroupSum) {
		super();
		this.inteActivityId = inteActivityId;
		this.useIntegration = useIntegration;
		this.inteUserSum = inteUserSum;
		this.inteGroupSum = inteGroupSum;
	}
	/**
	 * 
	 */
	public ActivityInfo() {
		super();
	}
	
}

