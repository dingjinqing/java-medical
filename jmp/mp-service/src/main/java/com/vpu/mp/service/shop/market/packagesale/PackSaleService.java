package com.vpu.mp.service.shop.market.packagesale;

import com.vpu.mp.db.shop.tables.records.PackageSaleRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsView;
import com.vpu.mp.service.pojo.shop.market.packagesale.*;
import com.vpu.mp.service.pojo.shop.market.packagesale.PackSaleConstant.ActivityStatus;
import com.vpu.mp.service.pojo.shop.market.packagesale.PackSaleConstant.Status;
import com.vpu.mp.service.pojo.shop.market.packagesale.PackSaleDefineVo.GoodsGroupVo;
import com.vpu.mp.service.pojo.shop.order.OrderListInfoVo;
import com.vpu.mp.service.pojo.shop.order.OrderPageListQueryParam;
import com.vpu.mp.service.pojo.shop.qrcode.QrCodeTypeEnum;
import com.vpu.mp.service.pojo.wxapp.market.packagesale.PackageSaleGoodsListParam;
import com.vpu.mp.service.pojo.wxapp.market.packagesale.PackageSaleGoodsListVo;
import com.vpu.mp.service.shop.config.ShopCommonConfigService;
import com.vpu.mp.service.shop.goods.GoodsService;
import com.vpu.mp.service.shop.image.QrCodeService;
import com.vpu.mp.service.shop.order.OrderReadService;
import com.vpu.mp.service.shop.order.info.AdminMarketOrderInfoService;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import jodd.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.jooq.SelectWhereStep;
import org.jooq.tools.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.vpu.mp.db.shop.tables.Goods.GOODS;
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
    @Autowired public PackageGoodsCartService packageGoodsCartService;
    @Autowired private ShopCommonConfigService shopCommonConfigService;
	
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
				step.and(PACKAGE_SALE.STATUS.le(BaseConstant.ACTIVITY_STATUS_NORMAL));
				break;
			case ActivityStatus.UNDER_WAY:
				step.and(PACKAGE_SALE.START_TIME.le(currentTime));
				step.and(PACKAGE_SALE.END_TIME.ge(currentTime));
				step.and(PACKAGE_SALE.STATUS.le(BaseConstant.ACTIVITY_STATUS_NORMAL));
				break;
			case ActivityStatus.OVERDUE:
				step.and(PACKAGE_SALE.END_TIME.le(currentTime));
				step.and(PACKAGE_SALE.STATUS.le(BaseConstant.ACTIVITY_STATUS_NORMAL));
				break;
			case ActivityStatus.STOPPED:
				step.and(PACKAGE_SALE.STATUS.le(BaseConstant.ACTIVITY_STATUS_DISABLE));
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
		return changeStatus(id, BaseConstant.ACTIVITY_STATUS_NORMAL);
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
		return changeStatus(id, BaseConstant.ACTIVITY_STATUS_DISABLE);
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
		PackSaleDefineVo defineVo = record.into(PackSaleDefineVo.class);

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

	private List<PackSaleParam.GoodsGroup> getPackageGroups(PackageSaleRecord packageSaleRecord){
	    List<PackSaleParam.GoodsGroup> res = new ArrayList<>();
        PackSaleParam.GoodsGroup g1 = new PackSaleParam.GoodsGroup();
        g1.setGroupId((byte)1);
        g1.setGroupName(packageSaleRecord.getGroupName_1());
        g1.setGoodsNumber(packageSaleRecord.getGoodsNumber_1());
        g1.setGoodsIdList(Util.splitValueToList(packageSaleRecord.getGoodsIds_1()));
        g1.setSortIdList(Util.splitValueToList(packageSaleRecord.getSortIds_1()));
        g1.setCatIdList(Util.splitValueToList(packageSaleRecord.getCatIds_1()));
        res.add(g1);

        if(packageSaleRecord.getGoodsGroup_2() == Status.NORMAL){
            PackSaleParam.GoodsGroup g2 = new PackSaleParam.GoodsGroup();
            g2.setGroupId((byte)2);
            g2.setGroupName(packageSaleRecord.getGroupName_2());
            g2.setGoodsNumber(packageSaleRecord.getGoodsNumber_2());
            g2.setGoodsIdList(Util.splitValueToList(packageSaleRecord.getGoodsIds_2()));
            g2.setSortIdList(Util.splitValueToList(packageSaleRecord.getSortIds_2()));
            g2.setCatIdList(Util.splitValueToList(packageSaleRecord.getCatIds_2()));
        }

        if(packageSaleRecord.getGoodsGroup_3() == Status.NORMAL){
            PackSaleParam.GoodsGroup g3 = new PackSaleParam.GoodsGroup();
            g3.setGroupId((byte)3);
            g3.setGroupName(packageSaleRecord.getGroupName_3());
            g3.setGoodsNumber(packageSaleRecord.getGoodsNumber_3());
            g3.setGoodsIdList(Util.splitValueToList(packageSaleRecord.getGoodsIds_3()));
            g3.setSortIdList(Util.splitValueToList(packageSaleRecord.getSortIds_3()));
            g3.setCatIdList(Util.splitValueToList(packageSaleRecord.getCatIds_3()));
        }

        return res;
    }

    /**
     * 小程序端活动页数据
     * @param param
     * @param userId
     * @return
     */
	public PackageSaleGoodsListVo getWxAppGoodsList(PackageSaleGoodsListParam param,Integer userId){
        PackageSaleGoodsListVo vo = new PackageSaleGoodsListVo();
        PackageSaleRecord packageSaleRecord = db().selectFrom(PACKAGE_SALE).where(PACKAGE_SALE.ID.eq(param.getPackageId())).fetchAny();
        if(packageSaleRecord == null || packageSaleRecord.getDelFlag().equals(DelFlag.DISABLE_VALUE)){
            vo.setState((byte)1);
            return vo;
        }else if(packageSaleRecord.getStartTime().after(DateUtil.getLocalDateTime())){
            vo.setState((byte)2);
            return vo;
        }else if(packageSaleRecord.getEndTime().before(DateUtil.getLocalDateTime())){
            vo.setState((byte)3);
            return vo;
        }
        vo.setState((byte)0);

        //填充tabList
        List<PackSaleParam.GoodsGroup> groups = getPackageGroups(packageSaleRecord);
        List<PackageSaleGoodsListVo.GoodsGroup> tabList = new ArrayList<>();
        groups.forEach(g->{
            PackageSaleGoodsListVo.GoodsGroup tab = new PackageSaleGoodsListVo.GoodsGroup();
            tab.setSelectNumber(packageGoodsCartService.getUserGroupGoodsNumber(userId,param.getPackageId(),g.getGroupId()));
            tab.setGroupName(g.getGroupName());
            tab.setGoodsNumber(g.getGoodsNumber());
            tabList.add(tab);
        });
        vo.setTabList(tabList);
        int allNumber = groups.stream().mapToInt(PackSaleParam.GoodsGroup::getGoodsNumber).sum();
        vo.setTotalGoodsNumber(allNumber);
        vo.setTotalSelectNumber(tabList.stream().mapToInt(PackageSaleGoodsListVo.GoodsGroup::getSelectNumber).sum());

        //填充title
        PackageSaleGoodsListVo.Title title = new PackageSaleGoodsListVo.Title();
        title.setPackageType(packageSaleRecord.getPackageType());
        title.setTotalGoodsNumber(vo.getTotalGoodsNumber());
        title.setDiscountTotalRatio(packageSaleRecord.getTotalRatio());
        title.setTotalMoney(packageSaleRecord.getTotalMoney());
        vo.setTitle(title);

        //填充goods
        PageResult<PackageSaleGoodsListVo.Goods> goods = getGoods(getPackageSaleGroupGoodsIds(groups.get(param.getGroupId() - 1)),param.getSortName(),param.getSortOrder(),param.getSearch(),param.getCurrentPage(),param.getPageRows());
        goods.getDataList().forEach(g->{
            g.setChooseNumber(packageGoodsCartService.getUserGroupGoodsNumber(userId,param.getPackageId(),param.getGroupId(),g.getGoodsId()));
        });
        vo.setGoods(goods);

        vo.setTotalMoney(packageGoodsCartService.getUserPackageMoney(userId,packageSaleRecord,allNumber));

	    return vo;
    }

    /**
     * 查出goods列表
     * @param inGoodsIds
     * @param search
     * @param currentPage
     * @param pageRows
     * @return
     */
    private PageResult<PackageSaleGoodsListVo.Goods> getGoods(List<Integer> inGoodsIds,Byte sortName,Byte sortOrder,String search,Integer currentPage,Integer pageRows){
        Byte soldOutGoods = shopCommonConfigService.getSoldOutGoods();
        SelectWhereStep<? extends Record> select = db().select(GOODS.GOODS_ID,GOODS.GOODS_NAME,GOODS.GOODS_IMG,GOODS.SHOP_PRICE,GOODS.MARKET_PRICE,GOODS.CAT_ID,GOODS.GOODS_TYPE,GOODS.SORT_ID,GOODS.IS_CARD_EXCLUSIVE).from(GOODS);
        select.where(GOODS.DEL_FLAG.eq(DelFlag.NORMAL_VALUE));
        select.where(GOODS.IS_ON_SALE.eq(GoodsConstant.ON_SALE));
        if(!NumberUtils.BYTE_ONE.equals(soldOutGoods)){
            select.where(GOODS.GOODS_NUMBER.gt(0));
        }
        if(StringUtil.isNotEmpty(search)){
            select.where(GOODS.GOODS_NAME.contains(search));
        }
        if(CollectionUtils.isNotEmpty(inGoodsIds)){
            select.where(GOODS.GOODS_ID.in(inGoodsIds));
        }
        if(sortName != null && sortName > 0){
            if(sortOrder != null && sortOrder > 0){
                if(sortName == (byte)1 && sortOrder == 1){
                    select.orderBy(GOODS.GOODS_SALE_NUM.desc());
                }else if(sortName == (byte)1 && sortOrder == 2) {
                    select.orderBy(GOODS.GOODS_SALE_NUM.asc());
                }else if(sortName == (byte)2 && sortOrder == 1) {
                    select.orderBy(GOODS.SHOP_PRICE.desc());
                }else if(sortName == (byte)2 && sortOrder == 2) {
                    select.orderBy(GOODS.SHOP_PRICE.asc());
                }
            }else {
                if(sortName == (byte)1){
                    select.orderBy(GOODS.GOODS_SALE_NUM.desc());
                }else if(sortName == (byte)2) {
                    select.orderBy(GOODS.SHOP_PRICE.desc());
                }
            }
        }
        return getPageResult(select,currentPage,pageRows,PackageSaleGoodsListVo.Goods.class);
    }

    /**
     * 一口价活动的一个分组包含的所有商品ID
     * @param group
     * @return
     */
    private List<Integer> getPackageSaleGroupGoodsIds(PackSaleParam.GoodsGroup group){
        List<Integer> res = new ArrayList<>();

        if(CollectionUtils.isNotEmpty(group.getGoodsIdList())){
            res.removeAll(group.getGoodsIdList());
            res.addAll(group.getGoodsIdList());
        }
        List<Integer> goodsIds = goodsService.getOnShelfGoodsIdList(group.getCatIdList(),group.getSortIdList(), Collections.emptyList());
        res.removeAll(goodsIds);
        res.addAll(goodsIds);

        return res;
    }
}

