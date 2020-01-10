package com.vpu.mp.service.shop.market.packagesale;

import com.vpu.mp.db.shop.tables.records.PackageSaleRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsView;
import com.vpu.mp.service.pojo.shop.market.packagesale.PackSaleConstant;
import com.vpu.mp.service.pojo.shop.market.packagesale.PackSaleConstant.ActivityStatus;
import com.vpu.mp.service.pojo.shop.market.packagesale.PackSaleConstant.Status;
import com.vpu.mp.service.pojo.shop.market.packagesale.PackSaleDefineVo;
import com.vpu.mp.service.pojo.shop.market.packagesale.PackSaleDefineVo.GoodsGroupVo;
import com.vpu.mp.service.pojo.shop.market.packagesale.PackSaleDetailParam;
import com.vpu.mp.service.pojo.shop.market.packagesale.PackSaleDetailVo;
import com.vpu.mp.service.pojo.shop.market.packagesale.PackSaleOrderPageParam;
import com.vpu.mp.service.pojo.shop.market.packagesale.PackSalePageParam;
import com.vpu.mp.service.pojo.shop.market.packagesale.PackSalePageVo;
import com.vpu.mp.service.pojo.shop.market.packagesale.PackSaleParam;
import com.vpu.mp.service.pojo.shop.market.packagesale.PackSaleShareVo;
import com.vpu.mp.service.pojo.shop.order.OrderListInfoVo;
import com.vpu.mp.service.pojo.shop.order.OrderPageListQueryParam;
import com.vpu.mp.service.pojo.shop.qrcode.QrCodeTypeEnum;
import com.vpu.mp.service.shop.goods.GoodsService;
import com.vpu.mp.service.shop.image.QrCodeService;
import com.vpu.mp.service.shop.order.OrderReadService;
import com.vpu.mp.service.shop.order.info.AdminMarketOrderInfoService;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import org.jooq.SelectConditionStep;
import org.jooq.tools.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.vpu.mp.db.shop.tables.OrderInfo.ORDER_INFO;
import static com.vpu.mp.db.shop.tables.PackageSale.PACKAGE_SALE;
import static com.vpu.mp.db.shop.tables.User.USER;

/**
 * @author huangronggang
 * @date 2019年8月12日
 * 打包一口价
 */
@Service
public class PackSaleService extends ShopBaseService {
	
	@Autowired public AdminMarketOrderInfoService orderInfo;
	@Autowired public QrCodeService qrCodeService;
	@Autowired public GoodsService goodsService;
	@Autowired public OrderReadService orderReadService;
	
	/**
	 * 分页查询一口价活动列表
	 * @param param
	 * @return
	 */
	public PageResult<PackSalePageVo> getPageList(PackSalePageParam param){
		SelectConditionStep<?> step = db().selectFrom(PACKAGE_SALE).where(PACKAGE_SALE.DEL_FLAG.eq(DelFlag.NORMAL_VALUE));
		step = buildOptions(step,param);
		PageResult<PackageSaleRecord> pageResult = getPageResult(step, param.getCurrentPage(), param.getPageRows(),PackageSaleRecord.class);
		List<PackageSaleRecord> dataList = pageResult.dataList;
		PageResult<PackSalePageVo> result = new PageResult<PackSalePageVo>();
		result.setPage(pageResult.getPage());
		if(dataList == null) {
			return result;
		}
		List<PackSalePageVo> list = new ArrayList<PackSalePageVo>(dataList.size());
		result.setDataList(list);
		for (PackageSaleRecord record : dataList) {
			PackSalePageVo packSalePageVo = PackSalePageVo.from(record);
			Integer activityId = packSalePageVo.getId();
			packSalePageVo.setPurchasedNum(orderInfo.getPackageSaleGoodsNum(activityId));
			packSalePageVo.setOrderNum(orderInfo.getPackageSaleOrderNum(activityId));
			packSalePageVo.setUserNum(orderInfo.getPackageSaleUserNum(activityId));
			list.add(packSalePageVo);
		}
		return result;
	}
	
	
	/**
	 * @param step
	 * @param param
	 * @return 
	 */
	private SelectConditionStep<?> buildOptions(SelectConditionStep<?> step, PackSalePageParam param) {
		Timestamp currentTime = Timestamp.valueOf(LocalDateTime.now());
		switch(param.getActivityStatus()) {
			case ActivityStatus.UNSTARTED:
				step.and(PACKAGE_SALE.START_TIME.ge(currentTime));
				break;
			case ActivityStatus.UNDER_WAY:
				step.and(PACKAGE_SALE.START_TIME.le(currentTime));
				step.and(PACKAGE_SALE.END_TIME.ge(currentTime));
				break;
			case ActivityStatus.OVERDUE:
				step.and(PACKAGE_SALE.END_TIME.le(currentTime));
				break;
			default:break;
		}
		if(!StringUtils.isBlank(param.getName())) {
			step.and(PACKAGE_SALE.PACKAGE_NAME.like(this.likeValue(param.getName())));
		}
		if(param.getStartTime() != null) {
			if(param.getEndTime() != null) {
				step.and(PACKAGE_SALE.START_TIME.le(param.getEndTime()))
					.and(PACKAGE_SALE.END_TIME.ge(param.getStartTime()));
			}else {
				step.and(PACKAGE_SALE.START_TIME.ge(param.getStartTime()));
			}
		}else if(param.getEndTime() != null){
			step.and(PACKAGE_SALE.END_TIME.le(param.getEndTime()));
		}
		return step;
	}

	public PackageSaleRecord selectRecord(Integer id) {
		if(id == null) {
			return null;
		}
		return db().selectFrom(PACKAGE_SALE).where(PACKAGE_SALE.ID.eq(id)).fetchOne();
	}
	public int insert(PackageSaleRecord record) {
		if(record == null) {
			return 0;
		}
		return db().executeInsert(record);
	}
	public int delete(Integer id) {
		if(id == null) {
			return 0;
		}
		return db().update(PACKAGE_SALE)
			.set(PACKAGE_SALE.DEL_FLAG, DelFlag.DISABLE_VALUE)
			.where(PACKAGE_SALE.ID.eq(id))
			.and(PACKAGE_SALE.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))
			.execute();
	}


	/**
	 * 添加
	 * @param param
	 * @return
	 */
	public int insert(PackSaleParam param) {
		PackageSaleRecord record = param.convert2Record();
		return insert(record);
	}


	/**
	 * 更新一条活动
	 * @param param
	 * @return
	 */
	public int update(PackSaleParam param) {
		PackageSaleRecord record = param.convert2Record();
		return db().update(PACKAGE_SALE).set(record)
			.where(PACKAGE_SALE.ID.eq(param.getId()))
			.and(PACKAGE_SALE.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))
			.execute();
	}


	/**
	 * 获取分享码
	 * @param id
	 * @return 
	 */
	public PackSaleShareVo getMpQrCode(Integer id) {
		String param = "package_id="+id;
		String imgUrl = qrCodeService.getMpQrCode(QrCodeTypeEnum.BUY_NOW_PRICE_INFO, param);
		String pathUrl = QrCodeTypeEnum.BUY_NOW_PRICE_INFO.getPathUrl(param);
		return new PackSaleShareVo(pathUrl,imgUrl);
	}
	/**
	 * 启用活动
	 * @param id
	 * @return
	 */
	public int enableStatus(Integer id) {
		if(id == null) {
			return 0;
		}
		return changeStatus(id, Status.NORMAL);
	}
	/**
	 * 停用活动
	 * @param id
	 * @return
	 */
	public int disableStatus(Integer id) {
		if(id == null) {
			return 0;
		}
		return changeStatus(id, Status.STOPED);
	}
	
	private int changeStatus(Integer id,Byte status) {
		return db().update(PACKAGE_SALE)
				.set(PACKAGE_SALE.STATUS, status)
				.where(PACKAGE_SALE.ID.eq(id))
				.and(PACKAGE_SALE.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))
				.and(PACKAGE_SALE.STATUS.ne(status))
				.execute();
	}


	/**
	 * 查一个活动详情
	 * @param id
	 */
	public PackSaleDefineVo selectDefine(Integer id) {
		PackageSaleRecord record = selectRecord(id);
		if(record == null || DelFlag.DISABLE_VALUE.equals(record.getDelFlag())){
			return null;
		}
		return convert2PackSaleDefineVo(record);
	}


	/**
	 * @param record
	 * @return
	 */
	private PackSaleDefineVo convert2PackSaleDefineVo(PackageSaleRecord record) {
		if(record == null) {
			return null;
		}
		PackSaleDefineVo defineVo = new PackSaleDefineVo(record.getId(), record.getPackageName(), record.getStartTime(), record.getEndTime(), record.getTotalMoney(),null, null, null);		

		GoodsGroupVo groupVo = convert2GoodsGroupVo(defineVo, record.getGroupName_1(), record.getGoodsNumber_1(), record.getGoodsIds_1(), record.getCatIds_1(), record.getSortIds_1());
		defineVo.setGroup1(groupVo);
		
		if(record.getGoodsGroup_2() != Status.NORMAL) {
			return defineVo;
		}
		groupVo = convert2GoodsGroupVo(defineVo, record.getGroupName_2(), record.getGoodsNumber_2(), record.getGoodsIds_2(), record.getCatIds_2(), record.getSortIds_2());
		defineVo.setGroup2(groupVo);
		
		if(record.getGoodsGroup_3() != Status.NORMAL) {
			return defineVo;
		}
		groupVo = convert2GoodsGroupVo(defineVo, record.getGroupName_3(), record.getGoodsNumber_3(), record.getGoodsIds_3(), record.getCatIds_3(), record.getSortIds_3());
		defineVo.setGroup3(groupVo);
		
		return defineVo;
	}
	GoodsGroupVo convert2GoodsGroupVo(PackSaleDefineVo defineVo,String groupName,Integer goodsNumber,String goodsIds,String catIds,String sortIds) {
		GoodsGroupVo groupVo = defineVo.new GoodsGroupVo();
		groupVo.setGroupName(groupName);
		groupVo.setGoodsNumber(goodsNumber);
		
		List<Integer> idList = transformIdList(goodsIds);
		List<GoodsView> goodsList = goodsService.selectGoodsViewList(idList);
		groupVo.setGoodsList(goodsList);
		
		List<Integer> catIdList = transformIdList(catIds);
		groupVo.setCatIdList(catIdList);
		
		List<Integer> sortIdList = transformIdList(sortIds);
		groupVo.setSortIdList(sortIdList);
		
		return groupVo;
	}
	private List<Integer> transformIdList(String ids){
		if(StringUtils.isBlank(ids)) {
			return new ArrayList<Integer>(0);
		}
		String[] idsArray = ids.split(PackSaleConstant.ID_DELIMITER);
		List<Integer> list = Util.valueOf(idsArray);
		return list;
	}


	/**
	 * 查询一口价 活动订单
	 * @param param
	 */
	public PageResult<? extends OrderListInfoVo> getOrderList(PackSaleOrderPageParam param) {
		OrderPageListQueryParam orderParam = param.convert2OrderParam();
		PageResult<? extends OrderListInfoVo> pageList = orderReadService.getPageList(orderParam).getList();
		return  pageList;
	}


	/**
	 * 一口价 活动明细
	 * @param param
	 * @return
	 */
	public PageResult<PackSaleDetailVo> getPackSaleDetail(PackSaleDetailParam param) {
		SelectConditionStep<?> step = db().select(ORDER_INFO.ORDER_SN,ORDER_INFO.CREATE_TIME,ORDER_INFO.MONEY_PAID,ORDER_INFO.ACTIVITY_ID,USER.USER_ID,USER.USERNAME,USER.MOBILE)
				.from(ORDER_INFO)
				.leftJoin(USER).on(ORDER_INFO.USER_ID.eq(USER.USER_ID))
				.where(ORDER_INFO.GOODS_TYPE.likeRegex(OrderInfoService.getGoodsTypeToSearch(new Byte[] {BaseConstant.ACTIVITY_TYPE_PACKAGE_SALE})))
				.and(ORDER_INFO.ACTIVITY_ID.eq(param.getActivityId()))
				.and(ORDER_INFO.DEL_FLAG.eq(DelFlag.NORMAL_VALUE));
		step = buildDetailOptions(step,param);
		return getPageResult(step, param.getCurrentPage(), param.getPageRows(), PackSaleDetailVo.class);
	}


	/**
	 * @param step
	 * @param param
	 * @return 
	 */
	private SelectConditionStep<?> buildDetailOptions(SelectConditionStep<?> step, PackSaleDetailParam param) {
		if(!StringUtils.isBlank(param.getMobile())) {
			step.and(USER.MOBILE.like(this.likeValue(param.getMobile())));
		}
		if(!StringUtils.isBlank(param.getOrderSn())) {
			step.and(ORDER_INFO.ORDER_SN.like(this.likeValue(param.getOrderSn())));
		}
		if(!StringUtils.isBlank(param.getUserName())) {
			step.and(USER.USERNAME.like(this.likeValue(param.getUserName())));
		}
		step.orderBy(ORDER_INFO.CREATE_TIME);
		return step;
	}
}

