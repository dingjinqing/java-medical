package com.vpu.mp.service.shop.market.groupdraw;

import static com.vpu.mp.db.shop.tables.GoodsSpecProduct.GOODS_SPEC_PRODUCT;
import static com.vpu.mp.db.shop.tables.GroupDraw.GROUP_DRAW;
import static com.vpu.mp.db.shop.tables.JoinDrawList.JOIN_DRAW_LIST;
import static com.vpu.mp.db.shop.tables.JoinGroupList.JOIN_GROUP_LIST;
import static com.vpu.mp.db.shop.tables.OrderGoods.ORDER_GOODS;
import static com.vpu.mp.db.shop.tables.OrderInfo.ORDER_INFO;
import static com.vpu.mp.db.shop.tables.UserDetail.USER_DETAIL;
import static com.vpu.mp.db.shop.tables.Goods.GOODS;
import static com.vpu.mp.db.shop.tables.User.USER;
import static com.vpu.mp.service.foundation.data.BaseConstant.ACTIVITY_STATUS_DISABLE;
import static com.vpu.mp.service.foundation.data.BaseConstant.ACTIVITY_STATUS_NORMAL;
import static com.vpu.mp.service.foundation.data.BaseConstant.NAVBAR_TYPE_DISABLED;
import static com.vpu.mp.service.foundation.data.BaseConstant.NAVBAR_TYPE_FINISHED;
import static com.vpu.mp.service.foundation.data.BaseConstant.NAVBAR_TYPE_NOT_STARTED;
import static com.vpu.mp.service.foundation.data.BaseConstant.NAVBAR_TYPE_ONGOING;
import static com.vpu.mp.service.foundation.util.Util.currentTimeStamp;
import static com.vpu.mp.service.foundation.util.Util.listToString;
import static com.vpu.mp.service.foundation.util.Util.stringToList;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.substring;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jooq.Record;
import org.jooq.Record18;
import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Objects;
import com.vpu.mp.config.DomainConfig;
import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import com.vpu.mp.db.shop.tables.records.GoodsSpecProductRecord;
import com.vpu.mp.db.shop.tables.records.GroupDrawInviteRecord;
import com.vpu.mp.db.shop.tables.records.GroupDrawRecord;
import com.vpu.mp.db.shop.tables.records.JoinDrawListRecord;
import com.vpu.mp.db.shop.tables.records.JoinGroupListRecord;
import com.vpu.mp.db.shop.tables.records.OrderGoodsRecord;
import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.db.shop.tables.records.UserRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.saas.schedule.TaskJobsConstant.TaskJobEnum;
import com.vpu.mp.service.pojo.shop.decoration.module.ModuleGroupDraw;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsSmallVo;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsView;
import com.vpu.mp.service.pojo.shop.image.ShareQrCodeVo;
import com.vpu.mp.service.pojo.shop.market.groupdraw.GroupDrawAddParam;
import com.vpu.mp.service.pojo.shop.market.groupdraw.GroupDrawListParam;
import com.vpu.mp.service.pojo.shop.market.groupdraw.GroupDrawListVo;
import com.vpu.mp.service.pojo.shop.market.groupdraw.GroupDrawShareParam;
import com.vpu.mp.service.pojo.shop.market.groupdraw.GroupDrawUpdateParam;
import com.vpu.mp.service.pojo.shop.market.groupdraw.analysis.GroupDrawAnalysisInfo;
import com.vpu.mp.service.pojo.shop.market.groupdraw.analysis.GroupDrawAnalysisMap;
import com.vpu.mp.service.pojo.shop.market.groupdraw.analysis.GroupDrawAnalysisParam;
import com.vpu.mp.service.pojo.shop.market.groupdraw.analysis.GroupDrawAnalysisStatus;
import com.vpu.mp.service.pojo.shop.market.groupdraw.analysis.GroupDrawAnalysisVo;
import com.vpu.mp.service.pojo.shop.market.message.RabbitMessageParam;
import com.vpu.mp.service.pojo.shop.market.message.RabbitParamConstant;
import com.vpu.mp.service.pojo.shop.official.message.MpTemplateConfig;
import com.vpu.mp.service.pojo.shop.official.message.MpTemplateData;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.qrcode.QrCodeTypeEnum;
import com.vpu.mp.service.pojo.shop.user.message.MaTemplateData;
import com.vpu.mp.service.pojo.wxapp.goods.groupDraw.DrawUser;
import com.vpu.mp.service.pojo.wxapp.goods.groupDraw.GroupDrawBotton;
import com.vpu.mp.service.pojo.wxapp.goods.groupDraw.GroupDrawInfoParam;
import com.vpu.mp.service.pojo.wxapp.goods.groupDraw.GroupDrawInfoReturnVo;
import com.vpu.mp.service.pojo.wxapp.goods.groupDraw.GroupDrawInfoVo;
import com.vpu.mp.service.pojo.wxapp.goods.groupDraw.GroupDrawList;
import com.vpu.mp.service.pojo.wxapp.goods.groupDraw.GroupDrawReturn;
import com.vpu.mp.service.pojo.wxapp.goods.groupDraw.GroupDrawVo;
import com.vpu.mp.service.pojo.wxapp.goods.groupDraw.GroupJoinDetailVo;
import com.vpu.mp.service.shop.image.ImageService;
import com.vpu.mp.service.shop.image.QrCodeService;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import com.vpu.mp.service.shop.user.message.maConfig.SubcribeTemplateCategory;

import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 拼团抽奖
 *
 * @author 郑保乐
 */
@Service
@Slf4j
public class GroupDrawService extends ShopBaseService {

	@Autowired
	public GroupDrawJoinUserService groupDrawUsers;
	@Autowired
	public GroupDrawOrderService groupDrawOrders;
	@Autowired
	public GroupDrawGroupService groupDrawGroups;
	@Autowired
	public GroupDrawInviteService groupDrawInvite;
	@Autowired
	public GroupDrawUserService groupDrawUser;

	@Autowired
	public DomainConfig domainConfig;

	private final QrCodeService qrCode;

	@Autowired
	private ImageService imageService;
	@Autowired
	private OrderInfoService orderInfoService;

	public GroupDrawService(QrCodeService qrCode) {
		this.qrCode = qrCode;
	}

	private static final byte ZERO = 0;
	private static final byte ONE = 1;
	private static final byte TWO = 2;
	private static final byte THREE = 3;
	private static final byte FOUR = 4;
	private static final byte[] LIST = { 2, 12, 13 };

	/**
	 * 获取小程序码
	 */
	public ShareQrCodeVo getMpQRCode(GroupDrawShareParam param) {
		Integer groupDrawId = param.getGroupDrawId();
		String pathParam = "group_draw_id=" + groupDrawId;
		String imageUrl = qrCode.getMpQrCode(QrCodeTypeEnum.PIN_LOTTERY, pathParam);
		ShareQrCodeVo vo = new ShareQrCodeVo();
		vo.setImageUrl(imageUrl);
		vo.setPagePath(QrCodeTypeEnum.PIN_LOTTERY.getPathUrl(pathParam));
		return vo;
	}

	/**
	 * 停用活动
	 */
	public void disableGroupDraw(Integer id) {
		int result = db().update(GROUP_DRAW).set(GROUP_DRAW.STATUS, ACTIVITY_STATUS_DISABLE)
				.where(GROUP_DRAW.ID.eq(id).and(GROUP_DRAW.STATUS.ne(ACTIVITY_STATUS_DISABLE))).execute();
		if (0 == result) {
			throw new IllegalStateException("Invalid group draw id or it has already been disabled.");
		}
	}

	/**
	 * 更新活动
	 */
	public void updateGroupDraw(GroupDrawUpdateParam param) {
		db().update(GROUP_DRAW).set(GROUP_DRAW.NAME, param.getName()).set(GROUP_DRAW.START_TIME, param.getStartTime())
				.set(GROUP_DRAW.END_TIME, param.getEndTime()).set(GROUP_DRAW.JOIN_LIMIT, param.getJoinLimit())
				.set(GROUP_DRAW.LIMIT_AMOUNT, param.getLimitAmount()).set(GROUP_DRAW.OPEN_LIMIT, param.getOpenLimit())
				.set(GROUP_DRAW.MIN_JOIN_NUM, param.getMinJoinNum()).set(GROUP_DRAW.PAY_MONEY, param.getPayMoney())
				.set(GROUP_DRAW.TO_NUM_SHOW, param.getToNumShow()).where(GROUP_DRAW.ID.eq(param.getId())).execute();
	}

	/**
	 * 活动明细
	 */
	public GroupDrawListVo getGroupDrawById(Integer id) {
		GroupDrawListParam param = new GroupDrawListParam();
		param.setId(id);
		SelectConditionStep<Record18<Integer, String, Timestamp, Timestamp, Byte, Short, BigDecimal, Short, Short, Short, Byte, Short, Integer, Integer, Integer, String, String, Integer>> select = createSelect(
				param);
		GroupDrawListVo vo = select.fetchOne().into(GroupDrawListVo.class);
		transformStatus(vo);
		return vo;
	}

	/**
	 * 列表查询
	 */
	public PageResult<GroupDrawListVo> getGroupDrawList(GroupDrawListParam param) {
		SelectConditionStep<Record18<Integer, String, Timestamp, Timestamp, Byte, Short, BigDecimal, Short, Short, Short, Byte, Short, Integer, Integer, Integer, String, String, Integer>> select = createSelect(
				param);
		PageResult<GroupDrawListVo> result = getPageResult(select, param, GroupDrawListVo.class);
		List<GroupDrawListVo> dataList = result.getDataList();
		transformStatus(dataList);
		return result;
	}

	/**
	 * 通用查询
	 */
	private SelectConditionStep<Record18<Integer, String, Timestamp, Timestamp, Byte, Short, BigDecimal, Short, Short, Short, Byte, Short, Integer, Integer, Integer, String, String, Integer>> createSelect(
			GroupDrawListParam param) {
		SelectConditionStep<Record18<Integer, String, Timestamp, Timestamp, Byte, Short, BigDecimal, Short, Short, Short, Byte, Short, Integer, Integer, Integer, String, String, Integer>> select = db()
				.select(GROUP_DRAW.ID, GROUP_DRAW.NAME, GROUP_DRAW.END_TIME, GROUP_DRAW.START_TIME, GROUP_DRAW.IS_DRAW,
						GROUP_DRAW.JOIN_LIMIT, GROUP_DRAW.PAY_MONEY, GROUP_DRAW.LIMIT_AMOUNT, GROUP_DRAW.MIN_JOIN_NUM,
						GROUP_DRAW.OPEN_LIMIT, GROUP_DRAW.STATUS, GROUP_DRAW.TO_NUM_SHOW,
						DSL.count(JOIN_DRAW_LIST.USER_ID).as("joinUserCount"),
						DSL.count(JOIN_GROUP_LIST.USER_ID).filterWhere(JOIN_GROUP_LIST.STATUS.eq((byte) 1))
								.as("groupUserCount"),
						DSL.countDistinct(JOIN_GROUP_LIST.GROUP_ID).as("groupCount"), GROUP_DRAW.GOODS_ID,
						GROUP_DRAW.REWARD_COUPON_ID,
						DSL.countDistinct(JOIN_DRAW_LIST.USER_ID).filterWhere(JOIN_DRAW_LIST.IS_WIN_DRAW.eq((byte) 1))
								.as("drawUserCount"))
				.from(GROUP_DRAW).leftJoin(JOIN_GROUP_LIST).on(GROUP_DRAW.ID.eq(JOIN_GROUP_LIST.GROUP_DRAW_ID))
				.leftJoin(JOIN_DRAW_LIST).on(GROUP_DRAW.ID.eq(JOIN_DRAW_LIST.GROUP_DRAW_ID)).where();
		buildOptions(select, param);
		select.groupBy(GROUP_DRAW.ID, GROUP_DRAW.NAME, GROUP_DRAW.END_TIME, GROUP_DRAW.START_TIME, GROUP_DRAW.IS_DRAW,
				GROUP_DRAW.JOIN_LIMIT, GROUP_DRAW.PAY_MONEY, GROUP_DRAW.LIMIT_AMOUNT, GROUP_DRAW.MIN_JOIN_NUM,
				GROUP_DRAW.OPEN_LIMIT, GROUP_DRAW.STATUS, GROUP_DRAW.TO_NUM_SHOW, GROUP_DRAW.GOODS_ID,
				GROUP_DRAW.REWARD_COUPON_ID);
		return select;
	}

	/**
	 * 查询条件
	 */
	private void buildOptions(
			SelectConditionStep<Record18<Integer, String, Timestamp, Timestamp, Byte, Short, BigDecimal, Short, Short, Short, Byte, Short, Integer, Integer, Integer, String, String, Integer>> select,
			GroupDrawListParam param) {
		String name = param.getActivityName();
		LocalDate startTime = param.getStartTime();
		LocalDate endTime = param.getEndTime();
		Byte status = param.getStatus();
		Integer id = param.getId();
		if (null != id) {
			select.and(GROUP_DRAW.ID.eq(id));
		}
		if (isNotEmpty(name)) {
			select.and(GROUP_DRAW.NAME.like(this.likeValue(name)));
		}
		if (null != startTime) {
			select.and(DSL.date(GROUP_DRAW.START_TIME).eq(Date.valueOf(startTime)));
		}
		if (null != endTime) {
			select.and(DSL.date(GROUP_DRAW.END_TIME).eq(Date.valueOf(endTime)));
		}
		if (null != status) {
			switch (status) {
			case NAVBAR_TYPE_ONGOING:
				select.and(GROUP_DRAW.START_TIME.le(currentTimeStamp()))
						.and(GROUP_DRAW.END_TIME.ge(currentTimeStamp()));
				break;
			case NAVBAR_TYPE_NOT_STARTED:
				select.and(GROUP_DRAW.START_TIME.greaterThan(currentTimeStamp()));
				break;
			case NAVBAR_TYPE_FINISHED:
				select.and(GROUP_DRAW.END_TIME.lessThan(currentTimeStamp()));
				break;
			case NAVBAR_TYPE_DISABLED:
				select.and(GROUP_DRAW.STATUS.eq(ACTIVITY_STATUS_DISABLE));
				break;
			default:
			}
			if (NAVBAR_TYPE_DISABLED != status) {
				select.and(GROUP_DRAW.STATUS.eq(ACTIVITY_STATUS_NORMAL));
			}
		}
		select.and(GROUP_DRAW.DEL_FLAG.eq(ZERO));
		select.orderBy(GROUP_DRAW.CREATE_TIME.desc());
	}

	/**
	 * 状态转换
	 */
	private void transformStatus(List<GroupDrawListVo> dataList) {
		dataList.parallelStream().forEach(this::transformStatus);
	}

	/**
	 * 状态转换
	 */
	private void transformStatus(GroupDrawListVo vo) {
		Byte status = vo.getStatus();
		Timestamp startTime = vo.getStartTime();
		Timestamp endTime = vo.getEndTime();
		String goodsId = vo.getGoodsId();
		String couponId = vo.getRewardCouponId();
		// 活动状态判断
		if (status.equals(ACTIVITY_STATUS_NORMAL)) {
			if (startTime.after(currentTimeStamp())) {
				vo.setStatus(NAVBAR_TYPE_NOT_STARTED);
			} else if (startTime.before(currentTimeStamp()) && endTime.after(currentTimeStamp())) {
				vo.setStatus(NAVBAR_TYPE_ONGOING);
			} else {
				vo.setStatus(NAVBAR_TYPE_FINISHED);
			}
		} else if (status.equals(ACTIVITY_STATUS_DISABLE)) {
			vo.setStatus(NAVBAR_TYPE_DISABLED);
		}
		// 商品数量
		int goodsCount = goodsId.split(",").length;
		vo.setGoodsCount(goodsCount);
		vo.setGoodsIds(stringToList(goodsId));
		if (null != couponId) {
			vo.setCouponIds(stringToList(couponId));
		}
	}

	/**
	 * 添加活动
	 */
	public void addGroupDraw(GroupDrawAddParam param) {
		List<Integer> goodsIds = param.getGoodsIds();
		List<Integer> rewardCouponIds = param.getRewardCouponIds();
		if (null == goodsIds || goodsIds.isEmpty()) {
			throw new IllegalArgumentException("Goods ids is required");
		}
		if (null != rewardCouponIds && (!rewardCouponIds.isEmpty())) {
			param.setRewardCouponId(listToString(rewardCouponIds));
		}
		param.setGoodsId(listToString(goodsIds));
		db().insertInto(GROUP_DRAW).set(createGroupDrawRecord(param)).execute();
	}

	/**
	 * 获取活动record
	 */
	private GroupDrawRecord createGroupDrawRecord(GroupDrawAddParam param) {
		return new GroupDrawRecord(null, param.getName(), param.getStartTime(), param.getEndTime(), param.getGoodsId(),
				param.getMinJoinNum(), param.getPayMoney(), param.getJoinLimit(), param.getOpenLimit(),
				param.getLimitAmount(), param.getToNumShow(), ACTIVITY_STATUS_NORMAL, (byte) 1, null, null, ZERO, null,
				param.getRewardCouponId());
	}

	/**
	 * 删除活动
	 */
	public void deleteGroupDraw(Integer id) {
		db().update(GROUP_DRAW).set(GROUP_DRAW.DEL_FLAG, (byte) 1).where(GROUP_DRAW.ID.eq(id)).execute();
	}

	/**
	 * 拼团抽奖数据效果展示
	 * 
	 * @param param 拼团抽奖id 起止时间
	 * @return 单天数据与总数 {@link GroupDrawAnalysisVo}
	 */
	public GroupDrawAnalysisVo groupDrawAnalysis(GroupDrawAnalysisParam param) {
		// 设置时间段
		Timestamp startTime = db().select(GROUP_DRAW.START_TIME).from(GROUP_DRAW)
				.where(GROUP_DRAW.ID.eq(param.getGroupDrawId())).fetchOptionalInto(Timestamp.class).orElse(null);
		Timestamp endTime = db().select(GROUP_DRAW.END_TIME).from(GROUP_DRAW)
				.where(GROUP_DRAW.ID.eq(param.getGroupDrawId())).fetchOptionalInto(Timestamp.class).orElse(null);
		GroupDrawAnalysisVo vo = new GroupDrawAnalysisVo();
		vo.setStartTime(param.getStartTime());
		vo.setEndTime(param.getEndTime());
		if (null == param.getStartTime()) {
			vo.setStartTime(startTime);
		}
		if (null == param.getEndTime()) {
			if (Util.currentTimeStamp().before(endTime)) {
				vo.setEndTime(Util.currentTimeStamp());
			} else {
				vo.setEndTime(endTime);
			}
		}
		// 获取展示数据
		GroupDrawAnalysisMap dataAnalysis = getGroupDrawInfo(param.getGroupDrawId(), vo.getStartTime(),
				vo.getEndTime());
		String tempTime = substring(vo.getStartTime().toString(), 0, 10);
		Map<String, Integer> tempOrderMap = new HashMap<>();
		Map<String, Integer> tempJoinMap = new HashMap<>();
		Map<String, Integer> tempSuccessMap = new HashMap<>();
		Map<String, Integer> tempNewMap = new HashMap<>();
		while (tempTime.compareTo(substring(vo.getEndTime().toString(), 0, 10)) < 0) {
			// 单天数据
			tempOrderMap.put(tempTime,
					dataAnalysis.getOrderNumber().get(tempTime) != null ? dataAnalysis.getOrderNumber().get(tempTime)
							: 0);
			tempJoinMap.put(tempTime,
					dataAnalysis.getJoinNum().get(tempTime) != null ? dataAnalysis.getJoinNum().get(tempTime) : 0);
			tempSuccessMap.put(tempTime,
					dataAnalysis.getSuccessUserNum().get(tempTime) != null
							? dataAnalysis.getSuccessUserNum().get(tempTime)
							: 0);
			tempNewMap.put(tempTime,
					dataAnalysis.getNewUser().get(tempTime) != null ? dataAnalysis.getNewUser().get(tempTime) : 0);
			// 总数据
			vo.setTotalOrderNumber(vo.getTotalOrderNumber() + tempOrderMap.get(tempTime));
			vo.setTotalJoinNum(vo.getTotalJoinNum() + tempJoinMap.get(tempTime));
			vo.setTotalSuccessUserNum(vo.getTotalSuccessUserNum() + tempSuccessMap.get(tempTime));
			vo.setTotalNewUser(vo.getTotalNewUser() + tempNewMap.get(tempTime));
			// 日期加一天
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date tempTimeDate = sdf.parse(tempTime);
				Calendar c = Calendar.getInstance();
				c.setTime(tempTimeDate);
				c.add(Calendar.DAY_OF_MONTH, 1);
				java.util.Date tomorrow = c.getTime();
				tempTime = sdf.format(tomorrow);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		vo.setOrderNumber(sortMap(tempOrderMap));
		vo.setJoinNum(sortMap(tempJoinMap));
		vo.setSuccessUserNum(sortMap(tempSuccessMap));
		vo.setNewUser(sortMap(tempNewMap));
		return vo;
	}

	/**
	 * 获取拼团活动效果展示数据
	 * 
	 * @param id        拼团抽奖活动id
	 * @param startTime 统计开始时间
	 * @param endTime   统计结束时间
	 * @return 付款订单数 拉新用户数 参与用户数 成团用户数 {@link GroupDrawAnalysisMap}
	 */
	public GroupDrawAnalysisMap getGroupDrawInfo(Integer id, Timestamp startTime, Timestamp endTime) {
		SelectConditionStep builder = db().select(ORDER_INFO.CREATE_TIME, ORDER_INFO.USER_ID).from(ORDER_INFO)
				.where(ORDER_INFO.ACTIVITY_ID.eq(id))
				.and(ORDER_INFO.GOODS_TYPE.likeRegex(
						OrderInfoService.getGoodsTypeToSearch(new Byte[] { BaseConstant.ACTIVITY_TYPE_GROUP_DRAW })))
				.and(ORDER_INFO.ORDER_STATUS.notIn(new Byte[] { 0, 2 }));
		if (null != startTime && null != endTime) {
			builder.and(ORDER_INFO.CREATE_TIME.between(startTime, endTime));
		}
		List<GroupDrawAnalysisInfo> analysisInfos = builder.fetchInto(GroupDrawAnalysisInfo.class);
		// 付款订单数
		Map<String, Integer> orderNumber = new HashMap();
		// 拉新用户数
		Map<String, Integer> newUser = new HashMap();
		// 控制跳出当前遍历
		List<Integer> userIds = new ArrayList<>();
		for (GroupDrawAnalysisInfo item : analysisInfos) {
			String date = substring(item.getCreateTime().toString(), 0, 10);
			orderNumber.put(date, (orderNumber.get(date) != null ? orderNumber.get(date) : 0) + 1);
			if (userIds.contains(item.getUserId())) {
				continue;
			}
			userIds.add(item.getUserId());
			Integer oldOrderNumber = db().select(DSL.count(ORDER_INFO.ORDER_ID).as("old_order_number")).from(ORDER_INFO)
					.where(ORDER_INFO.USER_ID.eq(item.getUserId())).and(ORDER_INFO.ORDER_STATUS.greaterThan(TWO))
					.and(ORDER_INFO.CREATE_TIME.lessThan(item.getCreateTime())).fetchOneInto(Integer.class);
			if (oldOrderNumber == 0) {
				newUser.put(date, (newUser.get(date) != null ? newUser.get(date) : 0) + 1);
			}
		}
		List<GroupDrawAnalysisStatus> analysisStatus = db().select(JOIN_GROUP_LIST.STATUS, JOIN_GROUP_LIST.OPEN_TIME)
				.from(JOIN_GROUP_LIST).where(JOIN_GROUP_LIST.GROUP_DRAW_ID.eq(id))
				.and(JOIN_GROUP_LIST.STATUS.greaterOrEqual(NumberUtils.BYTE_ZERO))
				.fetchInto(GroupDrawAnalysisStatus.class);
		// 参与用户数
		Map<String, Integer> joinNum = new HashMap();
		// 成团用户数
		Map<String, Integer> successUserNum = new HashMap();
		for (GroupDrawAnalysisStatus item : analysisStatus) {
			String date = substring(item.getOpenTime().toString(), 0, 10);
			if (item.getStatus() == 1) {
				joinNum.put(date, (joinNum.get(date) != null ? joinNum.get(date) : 0) + 1);
				successUserNum.put(date, (successUserNum.get(date) != null ? successUserNum.get(date) : 0) + 1);
			} else {
				joinNum.put(date, (joinNum.get(date) != null ? joinNum.get(date) : 0) + 1);
			}
		}
		GroupDrawAnalysisMap result = new GroupDrawAnalysisMap();
		result.setJoinNum(joinNum);
		result.setNewUser(newUser);
		result.setOrderNumber(orderNumber);
		result.setSuccessUserNum(successUserNum);
		return result;
	}

	/**
	 * map排序
	 * 
	 * @param disorderMap 无序map
	 * @return key升序map
	 */
	private Map<String, Integer> sortMap(Map<String, Integer> disorderMap) {
		List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(disorderMap.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			@Override
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return o1.getKey().compareTo(o2.getKey());
			}
		});
		Map result = new LinkedHashMap();
		for (Map.Entry<String, Integer> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}

	/**
	 * 小程序装修拼团抽奖模块显示异步调用
	 * 
	 * @param moduleGroupDraw
	 * @return
	 */
	public ModuleGroupDraw getPageIndexGroupDraw(ModuleGroupDraw moduleGroupDraw) {
		GroupDrawRecord groupDraw = db().selectFrom(GROUP_DRAW)
				.where(GROUP_DRAW.ID.eq(moduleGroupDraw.getGroupDrawId())).fetchAny();
		if (groupDraw != null) {
			moduleGroupDraw.setName(groupDraw.getName());
			moduleGroupDraw.setStatus(groupDraw.getStatus());
			moduleGroupDraw.setStartTime(groupDraw.getStartTime());
			moduleGroupDraw.setEndTime(groupDraw.getEndTime());
			moduleGroupDraw.setToNumShow(groupDraw.getToNumShow());
		}

		if (groupDraw.getStatus().equals(ACTIVITY_STATUS_DISABLE)) {
			moduleGroupDraw.setState(TWO);
		} else if (groupDraw.getEndTime().before(DateUtil.getLocalDateTime())) {
			moduleGroupDraw.setState(FOUR);
		} else if (groupDraw.getStartTime().after(DateUtil.getLocalDateTime())) {
			moduleGroupDraw.setState(THREE);
		} else {
			moduleGroupDraw.setState(ZERO);
			moduleGroupDraw.setSurplusSecond(
					(groupDraw.getEndTime().getTime() - Calendar.getInstance().getTimeInMillis()) / 1000);
		}

		int joinUserNumber = groupDrawUsers.getJoinGroupNumByGroupDraw(moduleGroupDraw.getGroupDrawId());
		if (groupDraw.getToNumShow() <= joinUserNumber) {
			moduleGroupDraw.setJoinUserNum(joinUserNumber);
		}

		if (StringUtil.isNotEmpty(moduleGroupDraw.getModuleImg())) {
			moduleGroupDraw.setModuleImg(domainConfig.imageUrl(moduleGroupDraw.getModuleImg()));
		}

		return moduleGroupDraw;
	}

	/**
	 * 小程序端获取列表
	 * 
	 * @param groupDrawId
	 * @return
	 */
	public GroupDrawVo groupDrawList(Integer groupDrawId) {
		GroupDrawRecord groupDraw = db().selectFrom(GROUP_DRAW).where(GROUP_DRAW.ID.eq(groupDrawId)).fetchAny();
		if (groupDraw == null) {
			// 活动不存在
			logger().info("活动不存在1");
			return null;
		}
		Timestamp endTime = groupDraw.getEndTime();
		Byte status = groupDraw.getStatus();
		Byte delFlag = groupDraw.getDelFlag();
		Timestamp nowTime = DateUtil.getLocalDateTime();
		if (endTime.before(nowTime) || status.equals(ACTIVITY_STATUS_DISABLE) || delFlag.equals(ONE)) {
			// 活动不存在
			logger().info("活动已结束，活动不存在");
			return null;
		}
		Timestamp startTime = groupDraw.getStartTime();
		GroupDrawVo vo = new GroupDrawVo();
		if (startTime.after(nowTime)) {
			// 活动还没开始
			logger().info("活动还没开始");
			vo.setStartTimeDoc(startTime);
		} else {
			vo.setSurplusSecond((endTime.getTime() - nowTime.getTime()) / 1000);
		}
		String goodsId = groupDraw.getGoodsId();
		if (StringUtil.isNotEmpty(goodsId)) {
			String[] goodsIds = goodsId.split(",");
			List<Integer> idList = new ArrayList<Integer>();
			for (String string : goodsIds) {
				idList.add(Integer.parseInt(string));
			}
			List<GoodsSmallVo> goodsList = saas.getShopApp(getShopId()).goods.getGoodsList(idList, true);
			if (goodsList.size() <= 0) {
				logger().info("没有可参与的活动商品");
				return null;
			}
			for (GoodsSmallVo goodsSmallVo : goodsList) {
				goodsSmallVo.setGoodsImg(imageService.imageUrl(goodsSmallVo.getGoodsImg()));
			}
			vo.setList(goodsList);
			logger().info("返回");
			return vo;
		}
		// return 没有可参与的活动商品
		logger().info("goodsId没有可参与的活动商品");
		return null;
	}

	/**
	 * 校验活动是否存在
	 * 
	 * @param groupDrawId
	 * @return
	 * @return
	 */
	public GroupDrawReturn checkGroupDraw(GroupDrawInfoParam param, Integer userId) {
		Integer groupDrawId = param.getGroupDrawId();
		Integer goodsId = param.getGoodsId();
		Integer groupId = param.getGroupId();
		GroupDrawReturn result = new GroupDrawReturn();
		GroupDrawRecord groupDrawRecord = db().selectFrom(GROUP_DRAW).where(GROUP_DRAW.ID.eq(groupDrawId)).fetchAny();
		if (groupDrawRecord == null || groupDrawRecord.getStatus().equals(ZERO)
				|| groupDrawRecord.getDelFlag().equals(ONE)) {
			// 活动不存在
			logger().info("活动不存在");
			result.setCode(JsonResultCode.ACTIVITY_NOT_EXIST);
			return result;
		}
		GroupDrawInfoVo groupDraw = groupDrawRecord.into(GroupDrawInfoVo.class);
		GoodsRecord goodsRecord = saas.getShopApp(getShopId()).goods.getGoodsRecordById(goodsId);
		if (goodsRecord == null || goodsRecord.getDelFlag().equals(DelFlag.DISABLE.getCode())) {
			logger().info("商品不存在");
			result.setCode(JsonResultCode.PRODUCT_NOT_EXIST);
			return result;

		}
		GoodsView goods = goodsRecord.into(GoodsView.class);
		goods.setGoodsImg(imageService.imageUrl(goods.getGoodsImg()));
		JoinGroupListRecord groupInfo = getGroupInfo(groupDrawId, groupId);
		if (groupInfo == null) {
			logger().info("拼团信息不存在");
			result.setCode(JsonResultCode.INFORMATION_NOT_EXIST);
			return result;
		}
		logger().info("options处理");
		Map<String, String> options = param.getOptions();
		if (!options.isEmpty() && StringUtils.isNotEmpty(options.get("group_draw_id"))
				&& StringUtils.isNotEmpty(options.get("goods_id"))
				&& StringUtils.isNotEmpty(options.get("invite_id"))) {
			options.put("user_id", String.valueOf(userId));
			groupDrawInvite.createInviteRecord("pages1/pinlotteryinfo/pinlotteryinfo",
					Integer.valueOf(options.get("group_draw_id")), options, ZERO);
		}
		String orderSn = groupInfo.getOrderSn();
		logger().info("获取订单信息");
		OrderGoodsRecord orderGoods = db().selectFrom(ORDER_GOODS).where(ORDER_GOODS.ORDER_SN.eq(orderSn)).fetchAny();
		Integer productId = orderGoods.getProductId();
		groupDraw.setProductId(productId == null ? 0 : productId);
		GoodsSpecProductRecord productInfo = db().selectFrom(GOODS_SPEC_PRODUCT)
				.where(GOODS_SPEC_PRODUCT.PRD_ID.eq(productId)).fetchAny();
		if (productInfo != null) {
			goods.setShopPrice(productInfo.getPrdPrice());
		}
		Timestamp endTime = groupDraw.getEndTime();
		Timestamp nowTime = DateUtil.getLocalDateTime();
		long surplusSecond = 0;
		if (nowTime.before(endTime)) {
			surplusSecond = (endTime.getTime() - nowTime.getTime()) / 1000;
		} else {
			groupDraw.setStatus(TWO);
		}
		logger().info("参团详情");
		GroupJoinDetailVo groupJoinDetail = getGroupJoinDetail(userId, groupDrawId, groupId);
		List<GoodsSmallVo> drawGoods = new ArrayList<GoodsSmallVo>();
		String goodsId2 = groupDraw.getGoodsId();
		if (StringUtil.isNotEmpty(goodsId2)) {
			String[] goodsIds = goodsId2.split(",");
			List<Integer> idList = new ArrayList<Integer>();
			for (String string : goodsIds) {
				idList.add(Integer.parseInt(string));
			}
			drawGoods = saas.getShopApp(getShopId()).goods.getGoodsList(idList, true);
		}
		JoinGroupListRecord userGroupInfoRecord = getUserJoinGroupInfo(userId, groupDrawId, groupId, true);
		GroupDrawList userGroupInfo = new GroupDrawList();
		if (userGroupInfoRecord != null) {
			userGroupInfo = userGroupInfoRecord.into(GroupDrawList.class);
		}
		String rewardCouponId = groupDraw.getRewardCouponId();
		Byte groupStatus = groupJoinDetail.getGroupStatus();

		if (StringUtils.isNotEmpty(rewardCouponId) || check(groupStatus)) {
			String[] split = rewardCouponId.split(",");
			groupDraw.setRewardNum(split.length);
		}
		GroupDrawInfoReturnVo vo = new GroupDrawInfoReturnVo(goods, groupDraw, drawGoods, groupJoinDetail,
				surplusSecond, userGroupInfo);
		result.setCode(JsonResultCode.CODE_SUCCESS);
		result.setVo(vo);
		return result;
	}

	private boolean check(Byte groupStatus) {
		for (Byte b : LIST) {
			if (b.equals(groupStatus)) {
				return true;
			}
		}
		return false;
	}

	private JoinGroupListRecord getGroupInfo(Integer groupDrawId, Integer groupId) {
		return db().selectFrom(JOIN_GROUP_LIST).where(JOIN_GROUP_LIST.GROUP_DRAW_ID.eq(groupDrawId)
				.and(JOIN_GROUP_LIST.GROUP_ID.eq(groupId)).and(JOIN_GROUP_LIST.IS_GROUPER.eq(ONE))).fetchAny();
	}

	/**
	 * 拼团详情
	 * 
	 * @param userId
	 * @param groupDrawId
	 * @param groupId
	 * @return
	 */
	public GroupJoinDetailVo getGroupJoinDetail(Integer userId, Integer groupDrawId, Integer groupId) {
		GroupDrawRecord groupDraw = db().selectFrom(GROUP_DRAW).where(GROUP_DRAW.ID.eq(groupDrawId)).fetchAny();
		JoinGroupListRecord groupInfo = getGroupInfo(groupDrawId, groupId);
		List<GroupDrawList> groupList = getGroupList(groupDrawId, groupId, ZERO);
		JoinGroupListRecord userJoinGroup = getUserJoinGroupInfo(userId, groupDrawId, groupId, true);
		GroupDrawBotton botton = new GroupDrawBotton(ZERO, ONE, ONE);
		if (userJoinGroup != null) {
			botton.setIsJoinDraw(ZERO);
			botton.setIsOpenDraw(ZERO);
			botton.setIsToInvite(ONE);
		}
		GroupJoinDetailVo vo = new GroupJoinDetailVo();
		Byte status = groupInfo.getStatus();
		if (status.equals(ZERO)) {
			logger().info("拼团中");
			int surplusGroupNum = groupDraw.getLimitAmount() - groupList.size();
			vo.setButton(botton);
			vo.setGroupStatus(ZERO);
			vo.setSurplusGroupNum(surplusGroupNum);
		}
		if (status.equals(ONE)) {
			logger().info("已成团");
			Byte drawStatus = groupInfo.getDrawStatus();
			if (drawStatus.equals(ONE)) {
				logger().info("已开奖");
				botton.setIsJoinDraw(ZERO);
				botton.setIsOpenDraw(ZERO);
				botton.setIsToInvite(ZERO);
				if (groupInfo.getIsWinDraw().equals(ONE)) {
					logger().info("已中奖");
					vo.setGroupStatus((byte) 11);
					vo.setButton(botton);
				} else {
					logger().info("未中奖");
					GroupDrawList drawUser = getDrawUser(groupDrawId, groupId);
					if (drawUser != null) {
						vo.setGroupStatus((byte) 12);
						vo.setDrawUser(new DrawUser(drawUser.getUserAvatar(), drawUser.getUserName()));
					} else {
						logger().info("没有中奖用户");
					}
				}
			}
			if (drawStatus.equals(TWO)) {
				logger().info("未达到开奖条件-未中奖");
				botton.setIsToInvite(ZERO);
				botton.setIsJoinDraw(ZERO);
				vo.setGroupStatus((byte) 12);
				vo.setButton(botton);
			} else {
				logger().info("未开奖");
				vo.setGroupStatus((byte) 10);
				vo.setButton(botton);
			}
		}
		if (status.equals(TWO)) {
			logger().info("拼团失败");
			botton.setIsJoinDraw(ZERO);
			botton.setIsOpenDraw(ZERO);
			botton.setIsToInvite(ZERO);
			vo.setGroupStatus(TWO);
			vo.setButton(botton);
		}
		vo.setUserList(groupList);
		return vo;
	}

	/*
	 * 获得中奖用户
	 */
	public GroupDrawList getDrawUser(Integer groupDrawId, Integer groupId) {
		GroupDrawList fetchAnyInto = db()
				.select(JOIN_GROUP_LIST.asterisk(), USER_DETAIL.USERNAME, USER_DETAIL.USER_AVATAR)
				.from(JOIN_GROUP_LIST, USER_DETAIL)
				.where(JOIN_GROUP_LIST.USER_ID.eq(USER_DETAIL.USER_ID)
						.and(JOIN_GROUP_LIST.GROUP_DRAW_ID.eq(groupDrawId).and(JOIN_GROUP_LIST.GROUP_ID.eq(groupId)))
						.and(JOIN_GROUP_LIST.IS_WIN_DRAW.eq(ONE)))
				.fetchAnyInto(GroupDrawList.class);
		if (fetchAnyInto != null) {
			fetchAnyInto.setUserAvatar(imageService.imageUrl(fetchAnyInto.getUserAvatar()));
		}
		return fetchAnyInto;
	}

	/**
	 * 获得用户参团信息
	 * 
	 * @param userId
	 * @param groupDrawId
	 * @param groupId
	 * @param isHasNotPay 0:true; 1:false php不传时候输入true
	 * @return
	 */
	public JoinGroupListRecord getUserJoinGroupInfo(Integer userId, Integer groupDrawId, Integer groupId,
			Boolean isHasNotPay) {
		SelectConditionStep<JoinGroupListRecord> selectConditionStep = db().selectFrom(JOIN_GROUP_LIST)
				.where(JOIN_GROUP_LIST.GROUP_DRAW_ID.eq(groupDrawId)
						.and(JOIN_GROUP_LIST.GROUP_ID.eq(groupId).and(JOIN_GROUP_LIST.USER_ID.eq(userId))));
		if (isHasNotPay) {
			selectConditionStep.and(JOIN_GROUP_LIST.STATUS.ge(ZERO));
		}
		return selectConditionStep.fetchOne();
	}

	/**
	 * 获得参团数量
	 * 
	 * @param userId
	 * @param groupDrawId
	 * @return
	 */
	public int getJoinGroupNumber(Integer userId, Integer groupDrawId) {
		Result<JoinGroupListRecord> fetch = db().selectFrom(JOIN_GROUP_LIST)
				.where(JOIN_GROUP_LIST.GROUP_DRAW_ID.eq(groupDrawId)
						.and(JOIN_GROUP_LIST.USER_ID.eq(userId)
								.and(JOIN_GROUP_LIST.STATUS.eq(ZERO).and(JOIN_GROUP_LIST.IS_GROUPER.eq(ZERO)))))
				.fetch();
		if (fetch != null) {
			return fetch.size();
		}
		return 0;
	}

	/**
	 * 获得开团数量
	 * 
	 * @param userId
	 * @param groupDrawId
	 * @return
	 */
	public int getOpenGroupNumber(Integer userId, Integer groupDrawId) {
		Result<JoinGroupListRecord> fetch = db()
				.selectFrom(JOIN_GROUP_LIST).where(
						JOIN_GROUP_LIST.GROUP_DRAW_ID.eq(groupDrawId)
								.and(JOIN_GROUP_LIST.USER_ID.eq(userId)
										.and(JOIN_GROUP_LIST.STATUS.eq(ZERO).and(JOIN_GROUP_LIST.IS_GROUPER.eq(ONE)))))
				.fetch();
		if (fetch != null) {
			return fetch.size();
		}
		return 0;
	}

	/**
	 * 通过商品获得用户参团信息
	 * 
	 * @param userId
	 * @param groupDrawId
	 * @param goodsId
	 * @return
	 */
	public JoinGroupListRecord getUserJoinGroupInfoByGoodsId(Integer userId, Integer groupDrawId, Integer goodsId) {
		SelectConditionStep<JoinGroupListRecord> selectConditionStep = db().selectFrom(JOIN_GROUP_LIST)
				.where(JOIN_GROUP_LIST.GROUP_DRAW_ID.eq(groupDrawId)
						.and(JOIN_GROUP_LIST.GOODS_ID.eq(goodsId).and(JOIN_GROUP_LIST.USER_ID.eq(userId))));
		selectConditionStep.and(JOIN_GROUP_LIST.STATUS.ge(ZERO));
		return selectConditionStep.fetchOne();
	}

	/**
	 * 获得团列表
	 * 
	 * @param groupDrawId
	 * @param groupId
	 * @param status
	 * @return
	 */
	public List<GroupDrawList> getGroupList(Integer groupDrawId, Integer groupId, Byte status) {
		SelectConditionStep<Record> selectConditionStep = db()
				.select(JOIN_GROUP_LIST.asterisk(), USER_DETAIL.USERNAME, USER_DETAIL.USER_AVATAR)
				.from(JOIN_GROUP_LIST, USER_DETAIL).where(JOIN_GROUP_LIST.USER_ID.eq(USER_DETAIL.USER_ID)
						.and(JOIN_GROUP_LIST.GROUP_DRAW_ID.eq(groupDrawId).and(JOIN_GROUP_LIST.GROUP_ID.eq(groupId))));
		if (status != null) {
			selectConditionStep.and(JOIN_GROUP_LIST.STATUS.eq(status));
		} else {
			selectConditionStep.and(JOIN_GROUP_LIST.STATUS.ge(status));
		}
		List<GroupDrawList> list = selectConditionStep.orderBy(JOIN_GROUP_LIST.IS_GROUPER.desc())
				.fetchInto(GroupDrawList.class);
		for (GroupDrawList groupDrawList : list) {
			String userName = groupDrawList.getUserName();
			if (StringUtils.isEmpty(userName)) {
				groupDrawList.setUserName("神秘小伙伴");
			}
			String userAvatar = groupDrawList.getUserAvatar();
			if (StringUtils.isEmpty(userAvatar)) {
				groupDrawList.setUserAvatar(imageService.imageUrl("/image/admin/head_icon.png"));
			}
		}
		return list;
	}

	/**
	 * 检查是否可创建拼团抽奖订单
	 * 
	 * @param userId
	 * @param groupDrawId
	 * @param goodsId
	 * @param groupId
	 * @param isFromNotPay 0 true;1 false
	 * @return
	 */
	public GroupDrawReturn canCreateGroupOrder(Integer userId, Integer groupDrawId, Integer goodsId, Integer groupId,
			Boolean isFromNotPay) {
		GroupDrawRecord groupDraw = db().selectFrom(GROUP_DRAW).where(GROUP_DRAW.ID.eq(groupDrawId)).fetchAny();
		GroupDrawReturn vo = new GroupDrawReturn();
		if (groupDraw == null) {
			// 活动不存在
			logger().info("活动不存在");
			vo.setCode(JsonResultCode.ACTIVITY_NOT_EXIST);
			return vo;
		}
		Timestamp endTime = groupDraw.getEndTime();
		Byte status = groupDraw.getStatus();
		Byte delFlag = groupDraw.getDelFlag();
		Timestamp nowTime = DateUtil.getLocalDateTime();
		if (endTime.before(nowTime) || status.equals(ACTIVITY_STATUS_DISABLE) || delFlag.equals(ONE)) {
			// 活动不存在
			logger().info("活动已结束");
			vo.setCode(JsonResultCode.EVENT_IS_OVER);
			return vo;
		}
		Timestamp startTime = groupDraw.getStartTime();
		if (startTime.after(nowTime)) {
			// 活动还没开始
			logger().info("活动还没开始");
			vo.setCode(JsonResultCode.EVENT_NOT_STARTED);
			return vo;
		}
		if (isFromNotPay) {
			OrderInfoRecord orderInfo = orderInfoService.getWaitPayOrderByActivityId(userId, goodsId,
					BaseConstant.ACTIVITY_TYPE_GROUP_DRAW, groupDrawId);
			if (orderInfo != null) {
				logger().info("存在未支付订单");
				vo.setCode(JsonResultCode.HAVE_UNPAID_ORDERS);
				return vo;
			}
		}

		JoinGroupListRecord joinGroupInfo = getUserJoinGroupInfoByGoodsId(userId, groupDrawId, goodsId);
		if (joinGroupInfo != null) {
			logger().info("已参与该活动");
			vo.setCode(JsonResultCode.PARTICIPATED_IN_EVENT);
			return vo;
		}
		JoinGroupListRecord userJoinGroupInfo = getUserJoinGroupInfo(userId, groupDrawId, groupId, false);
		// 不是团长
		if (userJoinGroupInfo != null && userJoinGroupInfo.getIsGrouper().equals(ZERO)) {
			int joinGroupNumber = getJoinGroupNumber(userId, groupDrawId);
			Short joinLimit = groupDraw.getJoinLimit();
			if (joinGroupNumber >= Integer.valueOf(String.valueOf(joinLimit))) {
				logger().info("参团已达上限");
				vo.setCode(JsonResultCode.PARTICIPANTS_IS_MAX);
				return vo;
			}
		}
		if (userJoinGroupInfo != null && userJoinGroupInfo.getIsGrouper().equals(ONE)) {
			int openGroupNumber = getOpenGroupNumber(userId, groupDrawId);
			Short openLimit = groupDraw.getOpenLimit();
			if (openGroupNumber >= Integer.valueOf(String.valueOf(openLimit))) {
				logger().info("开团已达上限");
				vo.setCode(JsonResultCode.GROUP_UPPER_LIMIT);
				return vo;
			}
		}
		logger().info("成功");
		vo.setCode(JsonResultCode.CODE_SUCCESS);
		GroupDrawInfoReturnVo vo2 = new GroupDrawInfoReturnVo();
		vo2.setGroupDraw(groupDraw.into(GroupDrawInfoVo.class));
		vo.setVo(vo2);
		return vo;

	}

	public JoinGroupListRecord getGroupInfoByOrderSn(String orderSn) {
		return db().selectFrom(JOIN_GROUP_LIST).where(JOIN_GROUP_LIST.ORDER_SN.eq(orderSn)).fetchAny();
	}

	/**
	 * 生成团分组Id
	 * 
	 * @return
	 */
	public int generateGroupId() {
		return db().select(DSL.max(JOIN_GROUP_LIST.GROUP_ID)).from(JOIN_GROUP_LIST).fetchAnyInto(Integer.class) + 1;
	}

	/**
	 * 生成抽奖码
	 * 
	 * @param groupDrawId
	 * @param goodsId
	 * @return
	 */
	public int generateGroupId(Integer groupDrawId, Integer goodsId) {
		return db().select(DSL.max(JOIN_DRAW_LIST.DRAW_ID)).from(JOIN_DRAW_LIST)
				.where(JOIN_DRAW_LIST.GROUP_DRAW_ID.eq(groupDrawId).and(JOIN_DRAW_LIST.GOODS_ID.eq(goodsId)))
				.fetchAnyInto(Integer.class) + 1;
	}

	/**
	 * 生成抽奖码记录
	 * 
	 * @param userId
	 * @param groupDrawId
	 * @param goodsId
	 * @param groupId
	 */
	public void generateDrawRecord(Integer userId, Integer groupDrawId, Integer goodsId, Integer groupId) {
		JoinDrawListRecord newRecord = db().newRecord(JOIN_DRAW_LIST);
		newRecord.setUserId(userId);
		newRecord.setGroupDrawId(groupDrawId);
		newRecord.setGoodsId(goodsId);
		newRecord.setGroupId(groupId);
		newRecord.setDrawId(generateGroupId(groupDrawId, goodsId));
		int insert = newRecord.insert();
		log.info("生成抽奖码记录" + insert);

	}

	/**
	 * 增加邀请用户数
	 * 
	 * @param userId
	 * @param groupDrawId
	 * @param groupId
	 */
	public void increaseUserNum(Integer userId, Integer groupDrawId, Integer groupId) {
		JoinGroupListRecord record = db().selectFrom(JOIN_GROUP_LIST).where(JOIN_GROUP_LIST.GROUP_DRAW_ID
				.eq(groupDrawId).and(JOIN_GROUP_LIST.GROUP_ID.eq(groupId).and(JOIN_GROUP_LIST.USER_ID.eq(userId))))
				.fetchAny();
		if (record != null) {
			record.setInviteUserNum(record.getInviteUserNum() + 1);
			int update = record.update();
			log.info("增加邀请用户数" + update);
		}
	}

	/**
	 * 生成团记录
	 * 
	 * @param order
	 * @param groupId
	 * @param status
	 */
	public void generateGroupRecord(OrderInfoRecord order, Integer groupId, Byte status) {
		OrderGoodsRecord orderGoods = db().selectFrom(ORDER_GOODS).where(ORDER_GOODS.ORDER_SN.eq(order.getOrderSn()))
				.fetchAny();
		Byte isGrouper = groupId == null ? ONE : ZERO;
		groupId = groupId == null ? generateGroupId() : groupId;
		Integer groupDrawId = order.getActivityId();
		// 记录邀请用户
		GroupDrawInviteRecord inviteUserInfo = groupDrawInvite.getAvailableInviteUser(groupDrawId,
				orderGoods.getGoodsId(), order.getUserId());
		Integer inviteUserId = inviteUserInfo.getInviteUserId();
		inviteUserId = inviteUserId == null ? 0 : inviteUserId;

		Integer goodsId = orderGoods.getGoodsId();
		Integer userId = order.getUserId();

		JoinGroupListRecord newRecord = db().newRecord(JOIN_GROUP_LIST);
		newRecord.setGroupDrawId(groupDrawId);
		newRecord.setGoodsId(goodsId);
		newRecord.setGroupId(groupId);
		newRecord.setUserId(userId);
		newRecord.setIsGrouper(isGrouper);
		newRecord.setInviteUserId(inviteUserId);
		newRecord.setOrderSn(order.getOrderSn());
		newRecord.setStatus(status);

		int insert = newRecord.insert();
		log.info("插入结果" + insert);
		if (status.equals(ZERO)) {
			generateDrawRecord(userId, groupDrawId, goodsId, groupId);
			if (inviteUserId != 0) {
				generateDrawRecord(inviteUserId, groupDrawId, goodsId, groupId);
				Byte isNew = inviteUserInfo.getIsNew();
				if (Objects.equal(ONE, isNew)) {
					generateDrawRecord(userId, groupDrawId, goodsId, groupId);
					generateDrawRecord(inviteUserId, groupDrawId, goodsId, groupId);
				}
				groupDrawInvite.updateRow(inviteUserInfo.getId(), ONE);
				increaseUserNum(userId, groupDrawId, groupId);
			}
			successGroupDraw(groupDrawId, groupId);
		}
	}

	public void successGroupDraw(Integer groupDrawId, Integer groupId) {
		GroupDrawRecord groupDraw = db().selectFrom(GROUP_DRAW).where(GROUP_DRAW.ID.eq(groupDrawId)).fetchAny();
		List<GroupDrawList> groupDrawUserList = getGroupList(groupDrawId, groupId, null);
		int size = groupDrawUserList.size();
		Short limitAmount = groupDraw.getLimitAmount();
		if (size >= Integer.valueOf(String.valueOf(limitAmount))) {
			// 修改订单状态
			List<GroupDrawList> groupUserList = getGroupList(groupDrawId, groupId, ZERO);
			String[] s = new String[] {};
			for (int i = 0; i < groupUserList.size(); i++) {
				String orderSn = groupUserList.get(i).getOrderSn();
				s[i] = orderSn;
			}
			// order_info
			int execute = db().update(ORDER_INFO).set(ORDER_INFO.ORDER_STATUS, OrderConstant.ORDER_PIN_SUCCESSS)
					.set(ORDER_INFO.ORDER_STATUS_NAME, "已成团").where(ORDER_INFO.ORDER_SN.in(s)).execute();
			log.info("已成团" + s + "结果" + execute);
			// 修改参团状态
			int execute2 = db().update(JOIN_GROUP_LIST).set(JOIN_GROUP_LIST.STATUS, ONE)
					.set(JOIN_GROUP_LIST.END_TIME, DateUtil.getLocalDateTime())
					.where(JOIN_GROUP_LIST.GROUP_DRAW_ID.eq(groupDrawId)
							.and(JOIN_GROUP_LIST.GROUP_ID.eq(groupId).and(JOIN_GROUP_LIST.STATUS.eq(ZERO))))
					.execute();
			logger().info("修改参团状态" + execute2);
			// 发送模板消息
			sendMsg(groupDrawUserList, groupDraw);
		}
	}

	public void sendMsg(List<GroupDrawList> groupDrawUserList, GroupDrawRecord groupDraw) {
		log.info("发送模板消息");
		String first = "您好，您有新的拼团成功订单";
		for (GroupDrawList groupDrawList : groupDrawUserList) {
			Integer userId = groupDrawList.getUserId();
			Integer goodsId = groupDrawList.getGoodsId();
			GoodsRecord goodsInfo = getGood(goodsId);
			log.info("获取商品信息：" + goodsInfo);
			String page = "pages1/pinlotteryinfo/pinlotteryinfo?group_id=" + groupDrawList.getGroupId()
					+ "&group_draw_id=" + groupDrawList.getGroupDrawId() + "&goods_id=" + goodsId;
			JoinGroupListRecord groupInfo = getGroupInfo(groupDrawList.getGroupDrawId(), groupDrawList.getGroupId());
			UserRecord grouper = db().selectFrom(USER).where(USER.USER_ID.eq(groupInfo.getUserId())).fetchAny();
			List<Integer> userIdList = new ArrayList<Integer>();
			userIdList.add(userId);
			logger().info("userIdList" + userIdList);
			sendMp(groupDraw, first, userId, goodsInfo, page, grouper, userIdList);
			sendMa(groupDraw, first, userId, goodsInfo, page, grouper, userIdList);
		}
	}

	protected GoodsRecord getGood(Integer goodsId) {
		return db().selectFrom(GOODS).where(GOODS.GOODS_ID.eq(goodsId)).fetchAny();
	}

	/**
	 * 发送公众号
	 * 
	 * @param groupDraw
	 * @param first
	 * @param userId
	 * @param goodsInfo
	 * @param page
	 * @param grouper
	 * @param userIdList
	 */
	private void sendMp(GroupDrawRecord groupDraw, String first, Integer userId, GoodsRecord goodsInfo, String page,
			UserRecord grouper, List<Integer> userIdList) {
		String[][] data = new String[][] { { first, "#173177" }, { goodsInfo.getGoodsName(), "#173177" },
				{ grouper.getUsername(), "#173177" }, { String.valueOf(groupDraw.getLimitAmount()), "#173177" },
				{ "", "#173177" } };
		RabbitMessageParam param = RabbitMessageParam.builder()
				.mpTemplateData(MpTemplateData.builder().config(MpTemplateConfig.GROUP_SUCCESS).data(data).build())
				.page(page).shopId(getShopId()).userIdList(userIdList).type(RabbitParamConstant.Type.MP_TEMPLE_TYPE)
				.build();
		logger().info("userId：" + userId + "mp准备发拼团成功订单");
		saas.taskJobMainService.dispatchImmediately(param, RabbitMessageParam.class.getName(), getShopId(),
				TaskJobEnum.SEND_MESSAGE.getExecutionType());
	}

	/**
	 * 发送小程序
	 * 
	 * @param groupDraw
	 * @param first
	 * @param userId
	 * @param goodsInfo
	 * @param page
	 * @param grouper
	 * @param userIdList
	 */
	private void sendMa(GroupDrawRecord groupDraw, String first, Integer userId, GoodsRecord goodsInfo, String page,
			UserRecord grouper, List<Integer> userIdList) {
		String prizeName = "已成团，等待开奖";
		String[][] data = new String[][] { { groupDraw.getName() }, { prizeName },
				{ Util.getdate("yyyy-MM-dd HH:mm:ss") } };
		RabbitMessageParam param = RabbitMessageParam.builder()
				.maTemplateData(
						MaTemplateData.builder().config(SubcribeTemplateCategory.INVITE_SUCCESS).data(data).build())
				.page(page).shopId(getShopId()).userIdList(userIdList)
				.type(RabbitParamConstant.Type.MA_SUBSCRIBEMESSAGE_TYPE).build();
		saas.taskJobMainService.dispatchImmediately(param, RabbitMessageParam.class.getName(), getShopId(),
				TaskJobEnum.SEND_MESSAGE.getExecutionType());
	}

	/**
	 * 通过订单号更新团信息
	 * 
	 * @param orderSn
	 * @param status
	 */
	public void updateGroupInfoByOrderSn(String orderSn, Byte status) {
		log.info("orderSn"+orderSn+"通过订单号更新团信息");
		int execute = db().update(JOIN_GROUP_LIST).set(JOIN_GROUP_LIST.STATUS, status)
				.where(JOIN_GROUP_LIST.ORDER_SN.eq(orderSn)).execute();
		log.info("订单号：" + orderSn + "；更新状态为：" + status + "；结果：" + execute);
		if (status.equals(ZERO)) {
			JoinGroupListRecord groupInfo = getGroupInfoByOrderSn(orderSn);
			Integer groupDrawId = groupInfo.getGroupDrawId();
			Integer goodsId = groupInfo.getGoodsId();
			Integer userId = groupInfo.getUserId();
			Integer groupId = groupInfo.getGroupId();
			GroupDrawInviteRecord inviteUserInfo = groupDrawInvite.getAvailableInviteUser(groupDrawId, goodsId, userId);
			generateDrawRecord(userId, groupDrawId, goodsId, groupId);
			Integer inviteUserId = inviteUserInfo.getInviteUserId();
			if (groupDrawId != null && inviteUserId != null) {
				generateDrawRecord(inviteUserId, groupDrawId, goodsId, groupId);
				Byte isNew = inviteUserInfo.getIsNew();
				if (Objects.equal(ONE, isNew)) {
					generateDrawRecord(userId, groupDrawId, goodsId, groupId);
					generateDrawRecord(inviteUserId, groupDrawId, goodsId, groupId);
				}
				groupDrawInvite.updateRow(inviteUserInfo.getId(), ONE);
				increaseUserNum(userId, groupDrawId, groupId);
			}
			successGroupDraw(groupDrawId, groupId);
		}
	}
	
}
