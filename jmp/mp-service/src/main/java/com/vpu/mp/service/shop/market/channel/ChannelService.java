package com.vpu.mp.service.shop.market.channel;

import static com.vpu.mp.db.shop.Tables.GOODS;
import static com.vpu.mp.db.shop.Tables.ORDER_INFO;
import static com.vpu.mp.db.shop.Tables.USER;
import static com.vpu.mp.db.shop.tables.Channel.CHANNEL;
import static com.vpu.mp.db.shop.tables.XcxCustomerPage.XCX_CUSTOMER_PAGE;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jooq.SelectJoinStep;
import org.jooq.SelectOnConditionStep;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vpu.mp.db.shop.tables.records.ChannelRecord;
import com.vpu.mp.db.shop.tables.records.ChannelStatisticalRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.market.channel.ChannelConstant;
import com.vpu.mp.service.pojo.shop.market.channel.ChannelPageInfo;
import com.vpu.mp.service.pojo.shop.market.channel.ChannelPageParam;
import com.vpu.mp.service.pojo.shop.market.channel.ChannelPageVo;
import com.vpu.mp.service.pojo.shop.market.channel.ChannelParam;
import com.vpu.mp.service.pojo.shop.market.channel.QrCodeShareVo;
import com.vpu.mp.service.pojo.shop.qrcode.QrCodeTypeEnum;
import com.vpu.mp.service.shop.decoration.ShopMpDecorationService;
import com.vpu.mp.service.shop.goods.GoodsService;
import com.vpu.mp.service.shop.image.QrCodeService;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huangronggang
 * @date 2019年8月14日 渠道分析
 */
@Service
public class ChannelService extends ShopBaseService {

	public static final String PAGE_PATH_PARAM_FORMAT = "page=%d&channel=%s";
	public static final String GOODS_PATH_PARAM_FORMAT = "goods_id=%d&channel=%s";
	@Autowired
	ShopMpDecorationService shopMpDecoration;
	@Autowired
	GoodsService goodsService;
	@Autowired
	ChannelStatisticalService channelStatistic;
	@Autowired
	QrCodeService qrCode;

	/**
	 * 渠道页面分析 分页查询
	 * 
	 * @param param
	 * @return
	 */
	public PageResult<ChannelPageVo> getPageList(ChannelPageParam param) {
		SelectOnConditionStep<?> step = db()
				.select(CHANNEL.ID, CHANNEL.PAGE_ID,
						DSL.iif(CHANNEL.SOURCE_TYPE.eq(ChannelConstant.SOURCETYPE_CUSTOMIZE),
								XCX_CUSTOMER_PAGE.PAGE_NAME, GOODS.GOODS_NAME).as("pageName"),
						CHANNEL.GOODS_ID, CHANNEL.CHANNEL_NAME, CHANNEL.SOURCE_TYPE, CHANNEL.SHARE, CHANNEL.DEL_FLAG,
						CHANNEL.CREATE_TIME)
				.from(CHANNEL).leftJoin(XCX_CUSTOMER_PAGE).on(CHANNEL.PAGE_ID.eq(XCX_CUSTOMER_PAGE.PAGE_ID))
				.leftJoin(GOODS).on(CHANNEL.GOODS_ID.eq(GOODS.GOODS_ID));
		buildOptions(step, param);
		PageResult<ChannelPageVo> result = getPageResult(step, param.getCurrentPage(), param.getPageRows(),
				ChannelPageVo.class);
		if (result.dataList == null) {
			return result;
		}
		for (ChannelPageVo vo : result.dataList) {
			vo.setOrderNum(getOrderNum(vo.getId(), ChannelConstant.INVITESOURCE));
			vo.setNewUserNum(getUserNum(vo.getId(), ChannelConstant.INVITESOURCE));
			setYesterdayInfo(vo);
		}
		return result;
	}

	/**
	 * 设置昨日访问次数和访问人数信息
	 * 
	 * @param vo
	 */
	private void setYesterdayInfo(ChannelPageVo vo) {
		ChannelStatisticalRecord statisticalRecord = channelStatistic.selectYesterDayRecord(vo.getId(), vo.getPageId(),
				vo.getGoodsId());
		if (statisticalRecord == null) {
			vo.setYesterdayAccessNum(0);
			vo.setYesterdayAccessTimes(0);
			return;
		}
		vo.setYesterdayAccessTimes(getMapJsonValue(statisticalRecord.getChannelAllPv(), vo.getId()));
		vo.setYesterdayAccessNum(getMapJsonValue(statisticalRecord.getChannelAllUv(), vo.getId()));

	}

	private int getMapJsonValue( String json,Integer channelId) {
		if (json == null) {
			return 0;
		}
		Map<String, Integer> pvMap = Util.parseJson(json, new TypeReference<Map<String, Integer>>() {
		});
		if (pvMap == null) {
			return 0;
		}
		return pvMap.get("" + channelId);
	}

	/**
	 * 渠道用户数量
	 * 
	 * @param id
	 * @param invitesource
	 * @return
	 */
	private Integer getUserNum(Integer id, String invitesource) {
		return db().select(DSL.count()).from(USER).where(USER.INVITE_SOURCE.eq(invitesource))
				.and(USER.INVITE_ACT_ID.eq(id)).fetchOneInto(Integer.class);
	}

	/**
	 * 渠道订单数量
	 * 
	 * @param inviteActId
	 * @param inviteSource
	 * @return
	 */
	private int getOrderNum(Integer inviteActId, String inviteSource) {
		return db().select(DSL.count()).from(USER).leftJoin(ORDER_INFO).on(USER.USER_ID.eq(ORDER_INFO.USER_ID))
				.where(USER.INVITE_SOURCE.eq(inviteSource)).and(USER.INVITE_ACT_ID.eq(inviteActId))
				.fetchOneInto(Integer.class);
	}

	/**
	 * @param step
	 * @param param
	 * @return
	 */
	private void buildOptions(SelectJoinStep<?> step, ChannelPageParam param) {
		if (!StringUtils.isBlank(param.getChannelName())) {
			step.where(CHANNEL.CHANNEL_NAME.like(this.likeValue(param.getChannelName())));
		}
		if (param.getSourceType() != null) {
			step.where(CHANNEL.SOURCE_TYPE.eq(param.getSourceType()));
		}
		if (param.getStartTime() != null) {
			step.where(CHANNEL.CREATE_TIME.ge(param.getStartTime()));
		}
		if (param.getEndTime() != null) {
			step.where(CHANNEL.CREATE_TIME.le(param.getEndTime()));
		}

		if (!StringUtils.isBlank(param.getSourcePage())) {
			List<Integer> idList = shopMpDecoration.getIdByName(param.getSourcePage());
			if (idList == null || idList.isEmpty()) {
				return;
			}
			step.where(CHANNEL.PAGE_ID.in(idList));
		}
		step.orderBy(CHANNEL.CREATE_TIME);
		return;
	}

	/**
	 * 停用渠道页面
	 * 
	 * @param id
	 * @return
	 */
	public int disableChannel(Integer id) {
		return db().update(CHANNEL).set(CHANNEL.DEL_FLAG, DelFlag.DISABLE_VALUE).where(CHANNEL.ID.eq(id))
				.and(CHANNEL.DEL_FLAG.eq(DelFlag.NORMAL_VALUE)).execute();
	}

	/**
	 * 启用渠道页面
	 * 
	 * @param id
	 * @return
	 */
	public int enableChannel(Integer id) {
		return db().update(CHANNEL).set(CHANNEL.DEL_FLAG, DelFlag.NORMAL_VALUE).where(CHANNEL.ID.eq(id))
				.and(CHANNEL.DEL_FLAG.eq(DelFlag.DISABLE_VALUE)).execute();
	}

	public QrCodeShareVo shareQrCode(Integer channelId) {
		ChannelRecord record = selectChannelRecord(channelId);
		QrCodeShareVo qrCodeVo = null;
		String pathParam =null;
		String imageUrl =null;
		if (record != null) {
			if(ChannelConstant.SOURCETYPE_CUSTOMIZE.equals(record.getSourceType())) {
				pathParam = String.format(PAGE_PATH_PARAM_FORMAT, record.getPageId(),record.getShare());
				imageUrl = qrCode.getMpQrCode(QrCodeTypeEnum.INDEX, pathParam);
			}else {
				pathParam = String.format(GOODS_PATH_PARAM_FORMAT, record.getGoodsId(),record.getShare());
				imageUrl = qrCode.getMpQrCode(QrCodeTypeEnum.GOODS_ITEM, pathParam);
			}

			qrCodeVo = new QrCodeShareVo(imageUrl,pathParam);
		}
		return qrCodeVo;
	}

	public ChannelRecord selectChannelRecord(Integer id) {
		if (id == null) {
			return null;
		}
		return db().selectFrom(CHANNEL).where(CHANNEL.ID.eq(id)).fetchOne();
	}

	/**
	 * 添加渠道页面 
	 * @param param
	 * @return
	 */
	public int insert(ChannelParam param) {
		ChannelRecord record = new ChannelRecord();
		if(ChannelConstant.SOURCETYPE_CUSTOMIZE.equals(param.getSourceType())){
			record.setPageId(param.getContentId());
		}else {
			record.setGoodsId(param.getContentId());
		}
		record.setSourceType(param.getSourceType());
		record.setShare(createShare());
		return db().executeInsert(record);
	}
	/**
	 * 生成唯一的渠道码 ：uuid 转62进制。
	 * @return
	 */
	public String createShare() {
		BigInteger hex =new BigInteger(Util.randomId(),16);
		return convert62(hex);
	}
	
	private String convert62(BigInteger from) {
		final String digit = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		final BigInteger scale = new BigInteger("62");
		StringBuilder dest = new StringBuilder();
		String sign = from.signum() ==-1?"-":"";
		while(true) {
			BigInteger[] result = from.divideAndRemainder(scale);
			from= result[0];
			dest.append(digit.charAt(result[1].abs().intValue()));
			if(from.equals(BigInteger.ZERO)) {
				break;
			}
		}
		dest.append(sign);
		return dest.reverse().toString();
	}
	public Map<Integer, String> selectChannelName(List<Integer> idList) {
		if(idList.isEmpty()) {
			return Collections.emptyMap();
		}
		return db().selectDistinct(CHANNEL.ID,CHANNEL.CHANNEL_NAME).from(CHANNEL).where(CHANNEL.ID.in(idList)).fetchMap(CHANNEL.ID, CHANNEL.CHANNEL_NAME);
	}
	public String selectChannelName(Integer id) {
		if(id == null) {
			return null;
		}
		return db().select(CHANNEL.CHANNEL_NAME).from(CHANNEL).where(CHANNEL.ID.eq(id)).fetchAnyInto(String.class);
	}

	public List<ChannelPageInfo> selectAllPageInfo() {
		List<GoodsChannel> goodsChannelList = db().select(CHANNEL.ID, CHANNEL.GOODS_ID, GOODS.GOODS_NAME).from(CHANNEL).innerJoin(GOODS)
				.on(CHANNEL.GOODS_ID.eq(GOODS.GOODS_ID)).where(CHANNEL.SOURCE_TYPE.eq(ChannelConstant.SOURCETYPE_GOODS))
				.and(CHANNEL.DEL_FLAG.eq(DelFlag.NORMAL_VALUE)).and(GOODS.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))
				.fetchInto(GoodsChannel.class);
		List<ChannelPageInfo> result = new ArrayList<>();
//		根据商品ID去重
		Map<Integer,ChannelPageInfo> temp = new HashMap<>();
		for (GoodsChannel goodsChannel : goodsChannelList) {
			temp.put(goodsChannel.getGoodsId(), new ChannelPageInfo(goodsChannel.getId(), goodsChannel.getGoodsName()));
		}
		result.addAll(temp.values());
		
		List<PageChannel> pageChannelList = db().select(CHANNEL.ID, CHANNEL.PAGE_ID, XCX_CUSTOMER_PAGE.PAGE_NAME).from(CHANNEL).innerJoin(XCX_CUSTOMER_PAGE)
				.on(CHANNEL.PAGE_ID.eq(XCX_CUSTOMER_PAGE.PAGE_ID)).where(CHANNEL.SOURCE_TYPE.eq(ChannelConstant.SOURCETYPE_CUSTOMIZE))
				.and(CHANNEL.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))
				.fetchInto(PageChannel.class);
//		根据页面ID去重
		temp.clear();
		for(PageChannel pageChannel:pageChannelList) {
			temp.put(pageChannel.getPageId(),new ChannelPageInfo(pageChannel.getId(),pageChannel.getPageName()));
		}
		result.addAll(temp.values());
		return result;

	}

	
}
@Data
@NoArgsConstructor
class GoodsChannel{
	private Integer id;
	private Integer goodsId;
	private String goodsName;
}
@Data
@NoArgsConstructor
class PageChannel{
	private Integer id;
	private Integer pageId;
	private String pageName;
}
