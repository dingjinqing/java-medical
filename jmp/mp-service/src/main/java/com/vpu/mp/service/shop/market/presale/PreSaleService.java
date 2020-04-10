package com.vpu.mp.service.shop.market.presale;

import com.vpu.mp.config.DomainConfig;
import com.vpu.mp.db.shop.tables.OrderGoods;
import com.vpu.mp.db.shop.tables.OrderInfo;
import com.vpu.mp.db.shop.tables.Presale;
import com.vpu.mp.db.shop.tables.PresaleProduct;
import com.vpu.mp.db.shop.tables.records.PresaleProductRecord;
import com.vpu.mp.db.shop.tables.records.PresaleRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.image.ShareQrCodeVo;
import com.vpu.mp.service.pojo.shop.image.share.ShareConfig;
import com.vpu.mp.service.pojo.shop.market.presale.*;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.qrcode.QrCodeTypeEnum;
import com.vpu.mp.service.shop.image.QrCodeService;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import jodd.util.StringUtil;
import org.jooq.Record;
import org.jooq.Record2;
import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.tables.Goods.GOODS;
import static com.vpu.mp.db.shop.tables.GoodsSpecProduct.GOODS_SPEC_PRODUCT;
import static com.vpu.mp.db.shop.tables.OrderInfo.ORDER_INFO;
import static com.vpu.mp.db.shop.tables.Presale.PRESALE;
import static com.vpu.mp.db.shop.tables.PresaleProduct.PRESALE_PRODUCT;
import static com.vpu.mp.service.foundation.data.BaseConstant.*;
import static com.vpu.mp.service.pojo.shop.market.presale.PresaleConstant.PRE_SALE_ONE_PHASE;
import static com.vpu.mp.service.pojo.shop.market.presale.PresaleConstant.PRE_SALE_TWO_PHASE;
import static java.lang.String.format;
import static org.springframework.util.StringUtils.isEmpty;

/**
 * 定金膨胀活动
 *
 * @author 郑保乐
 */
@Service
@Primary
public class PreSaleService extends ShopBaseService {

    @Autowired
    private DomainConfig domainConfig;
    @Autowired
    private QrCodeService qrCode;





    public static final Presale TABLE = PRESALE;
    public static final PresaleProduct SUB_TABLE = PRESALE_PRODUCT;
    private static final OrderInfo ORDER = ORDER_INFO;
    private static final OrderGoods ORDER_GOODS = OrderGoods.ORDER_GOODS;



    /** 已购商品数量 **/
    private static final String BOUGHT_QUANTITY = "boughtGoodsQuantity";
    /** 订单数 **/
    private static final String ORDER_QUANTITY = "orderQuantity";
    /** 已付定金订单数 **/
    private static final String BARGAIN_PAID_QUANTITY = "bargainPaidOrderQuantity";
    /** 已付尾款订单数 **/
    private static final String TAIL_PAID_QUANTITY = "tailPaidOrderQuantity";
    /** 下单用户数 **/
    private static final String ORDER_USER_QUANTITY = "orderUserQuantity";

    private static final String SHARE_PAGE_PATH = "pages/presaleitem/presaleitem?goods_id=%s&presale_id=%s";

    /**
     * 获取定金膨胀活动列表
     */
    public PageResult<PreSaleListVo> getPageList(PreSaleListParam param) {
        SelectConditionStep<? extends Record> query =
            db().select(TABLE.ID,TABLE.PRESALE_TYPE, TABLE.PRESALE_NAME, TABLE.FIRST, TABLE.PRE_START_TIME, TABLE.PRE_END_TIME,TABLE.PRE_PAY_STEP,
                TABLE.START_TIME, TABLE.END_TIME, TABLE.STATUS,TABLE.PRE_START_TIME_2.as("preStartTime2"),TABLE.PRE_END_TIME_2.as("preEndTime2"),
                //订单数
                DSL.count(ORDER.ORDER_ID).as(ORDER_QUANTITY),
                //已付定金订单数
                DSL.count(ORDER.ORDER_ID).filterWhere(ORDER.ORDER_PAY_WAY.eq(OrderConstant.PAY_WAY_DEPOSIT)).as(BARGAIN_PAID_QUANTITY),
                //已付尾款订单数
                DSL.count(ORDER.ORDER_ID).filterWhere(ORDER.ORDER_PAY_WAY.eq(OrderConstant.PAY_WAY_DEPOSIT).and(ORDER.BK_ORDER_PAID.gt((byte) 0))).as(TAIL_PAID_QUANTITY),
                //下单用户数
                DSL.countDistinct(ORDER.USER_ID).as(ORDER_USER_QUANTITY),
                //已购商品数量
                DSL.coalesce(DSL.sum(ORDER_GOODS.GOODS_NUMBER), 0).as(BOUGHT_QUANTITY))
                .from(TABLE)
                .leftJoin(ORDER).on(ORDER.GOODS_TYPE.likeRegex(OrderInfoService.getGoodsTypeToSearch(new Byte[] {BaseConstant.ACTIVITY_TYPE_PRE_SALE})).and(ORDER.ACTIVITY_ID.eq(TABLE.ID)))
                .leftJoin(ORDER_GOODS).on(ORDER_GOODS.ORDER_ID.eq(ORDER.ORDER_ID))
                .where(TABLE.DEL_FLAG.eq(DelFlag.NORMAL_VALUE));
        buildOptions(query, param);
        query.groupBy(TABLE.ID, TABLE.PRESALE_TYPE,TABLE.PRESALE_NAME, TABLE.PRE_START_TIME, TABLE.PRE_END_TIME, TABLE.PRE_PAY_STEP,TABLE.START_TIME,
            TABLE.END_TIME, TABLE.STATUS, TABLE.PRE_START_TIME_2, TABLE.PRE_END_TIME_2);
        query.orderBy(TABLE.FIRST.desc(),TABLE.CREATE_TIME.desc());
        return getPageResult(query, param, PreSaleListVo.class);
    }

    /**
     * 条件查询
     */
    private void buildOptions(SelectConditionStep<? extends Record> query, PreSaleListParam param) {
        String name = param.getName();
        Timestamp preStartTime = param.getPreStartTime();
        Timestamp preEndTime = param.getPreEndTime();
        Timestamp startTime = param.getStartTime();
        Timestamp endTime = param.getEndTime();
        Byte status = param.getStatus();
        if (!isEmpty(name)) {
            query.and(TABLE.PRESALE_NAME.like(format("%s%%", name)));
        }
        if (null != preStartTime) {
            query.and(
                (TABLE.PRE_PAY_STEP.eq(PRE_SALE_TWO_PHASE).and(TABLE.PRE_END_TIME_2.gt(preStartTime)))
                    .or(TABLE.PRE_PAY_STEP.eq(PRE_SALE_ONE_PHASE).and(TABLE.PRE_END_TIME.gt(preStartTime)))
            );
        }
        if (null != preEndTime) {
            query.and(TABLE.PRE_START_TIME.le(preEndTime));
        }
        if (null != startTime) {
            query.and(TABLE.START_TIME.ge(startTime));
        }
        if (null != endTime) {
            query.and(TABLE.END_TIME.le(endTime));
        }
        if (null != status && status > 0) {
            andStatus(query, status);
        }
    }

    /**
     * 状态转换
     */
    private void andStatus(SelectConditionStep<? extends Record> query, Byte status) {
        Timestamp now = Util.currentTimeStamp();
        switch (status) {
            case NAVBAR_TYPE_ONGOING:
                query.and(
                    (TABLE.PRE_PAY_STEP.eq(PRE_SALE_TWO_PHASE).and(TABLE.PRE_START_TIME.le(now).and(TABLE.PRE_END_TIME_2.gt(now))))
                    .or(TABLE.PRE_PAY_STEP.eq(PRE_SALE_ONE_PHASE).and(TABLE.PRE_START_TIME.le(now).and(TABLE.PRE_END_TIME.gt(now))))
                ).and(TABLE.STATUS.eq(BaseConstant.ACTIVITY_STATUS_NORMAL));
                break;
            case NAVBAR_TYPE_NOT_STARTED:
                query.and(TABLE.PRE_START_TIME.gt(now)).and(TABLE.STATUS.eq(BaseConstant.ACTIVITY_STATUS_NORMAL));
                break;
            case NAVBAR_TYPE_FINISHED:
                query.and(
                    ((TABLE.PRE_PAY_STEP.eq(PRE_SALE_TWO_PHASE).and(TABLE.PRE_END_TIME_2.le(now)))
                        .or(TABLE.PRE_PAY_STEP.eq(PRE_SALE_ONE_PHASE).and(TABLE.PRE_END_TIME.lt(now)))
                    )
                    .and(TABLE.STATUS.eq(BaseConstant.ACTIVITY_STATUS_NORMAL)));
                break;
            case NAVBAR_TYPE_DISABLED:
                query.and(TABLE.STATUS.eq(BaseConstant.ACTIVITY_STATUS_DISABLE));
                break;
            default:
                throw new IllegalArgumentException("Unexpected status: " + status);
        }
    }

    /**
     * 添加活动
     */
    public void addPreSale(PreSaleParam param) {
        validateParam(param);
        transaction(()->{
            this.insertPresale(param);
        });
    }

    /**
     * 保存活动
     */
    private void insertPresale(PreSaleParam param) {
        PresaleRecord presale = db().newRecord(TABLE, param);
        String config = shareConfigJson(param);
        presale.setShareConfig(config);
        if(param.getPreStartTime2() != null && param.getPreEndTime2() != null){
            presale.setPreStartTime_2(param.getPreStartTime2());
            presale.setPreEndTime_2(param.getPreEndTime2());
        }
        presale.insert();
        Integer id = presale.getId();
        this.insertPresaleProduct(param, id);
    }

    /**
     * 保存活动产品
     */
    private void insertPresaleProduct(PreSaleParam param, Integer presaleId) {
        List<ProductParam> products = param.getProducts();
        List<PresaleProductRecord> productRecords =
            products.stream().map(product -> {
                product.setPresaleId(presaleId);
                PresaleProductRecord r = db().newRecord(PRESALE_PRODUCT);
                assign(product,r);
                if(product.getPreDiscountMoney1() != null){
                    r.setPreDiscountMoney_1(product.getPreDiscountMoney1());
                }
                if(product.getPreDiscountMoney2() != null){
                    r.setPreDiscountMoney_2(product.getPreDiscountMoney2());
                }
                return r;
            }).collect(Collectors.toList());
        db().batchInsert(productRecords).execute();
    }

    /**
     * 获取分享配置 json
     */
    private String shareConfigJson(PreSaleParam param) {
        ShareConfig config = createShareConfig(param);
        return Util.toJson(config);
    }

    /**
     * 分享配置
     */
    private ShareConfig createShareConfig(PreSaleParam param) {
        ShareConfig shareConfig = new ShareConfig();
        shareConfig.setShareAction(param.getShareAction());
        shareConfig.setShareDoc(param.getShareDoc());
        shareConfig.setShareImgAction(param.getShareImgAction());
        shareConfig.setShareImg(param.getShareImg());
        return shareConfig;
    }

    /**
     * 校验活动商品参数
     */
    private void validateParam(PreSaleParam param) {
        validateTime(param);
        validateDeliverTime(param);
        param.getProducts().forEach(product -> this.validateProduct(product, param));
    }

    /**
     * 校验支付时间
     */
    private void validateTime(PreSaleParam param) {
        byte prePayStep = param.getPrePayStep();
        switch (prePayStep) {
            case 1:
                if (param.getPreStartTime().after(param.getPreEndTime())) {
                    throw new IllegalArgumentException("PreEndTime earlier than preStartTime");
                }
                break;
            case 2:
                Assert.notNull(param.getPreStartTime2(), "Missing parameter preStartTime2");
                Assert.notNull(param.getPreEndTime2(), "Missing parameter preEndTime2");
                if (param.getPreStartTime2().after(param.getPreEndTime2())) {
                    throw new IllegalArgumentException("PreEndTime2 earlier than preStartTime2");
                }
                if (param.getPreStartTime2().before(param.getPreEndTime())) {
                    throw new IllegalArgumentException("PreEndTime2 earlier than preEndTime");
                }
                break;
            default:
                throw new IllegalArgumentException("Unexpected prePayStep: " + prePayStep);
        }
    }


    /**
     * 校验发货时间
     */
    private void validateDeliverTime(PreSaleParam param) {
        byte deliverType = param.getDeliverType();
        switch (deliverType) {
            case PresaleConstant.DELIVER_SPECIFIC:
                Assert.notNull(param.getDeliverTime(), "Missing parameter deliverTime");
                if (param.getPresaleType() == PresaleConstant.PRESALE && param.getDeliverTime().before(param.getEndTime())) {
                    throw new IllegalArgumentException("DeliverTime earlier than endTime");
                }
                break;
            case PresaleConstant.DELIVER_POSTPONE:
                Assert.notNull(param.getDeliverDays(), "Missing parameter deliverDays");
                break;
            default:
                throw new IllegalArgumentException("Unexpected deliverType: " + deliverType);
        }
    }

    /**
     * 校验活动产品参数
     */
    private void validateProduct(ProductParam product, PreSaleParam param) {
        Byte prePayStep = param.getPrePayStep();
        if (2 == prePayStep) {
            Assert.notNull(product.getPreDiscountMoney2(), "Missing parameter preDiscountMoney2");
        }
        BigDecimal presalePrice = product.getPresalePrice();
        BigDecimal presaleMoney = product.getPresaleMoney();
        BigDecimal preDiscountMoney1 = product.getPreDiscountMoney1();
        BigDecimal preDiscountMoney2 = product.getPreDiscountMoney2();
        if (param.getPresaleType() == PresaleConstant.PRESALE && (preDiscountMoney1.compareTo(presaleMoney) < 0 || preDiscountMoney1.compareTo(presalePrice) > 0)) {
            logger().error("预售--抵扣金额异常");
            throw new IllegalArgumentException("Discount money error");
        }
        if (null != preDiscountMoney2&&(preDiscountMoney2.compareTo(presaleMoney) < 0 || preDiscountMoney2.compareTo(presalePrice) > 0)) {
            logger().error("预售--抵扣金额异常");
            throw new IllegalArgumentException("Discount money error");
        }
    }

    /**
     * 删除活动
     */
    public void deletePreSale(Integer id) {
        db().update(TABLE).set(TABLE.DEL_FLAG, DelFlag.DISABLE_VALUE).where(TABLE.ID.eq(id)).execute();
    }

    /**
     * 停用活动
     */
    public void disablePreSale(Integer id) {
        db().update(TABLE).set(TABLE.STATUS, ACTIVITY_STATUS_DISABLE).where(TABLE.ID.eq(id)).execute();
    }

    /**
     * 启用活动
     */
    public void enablePreSale(Integer id) {
        db().update(TABLE).set(TABLE.STATUS, ACTIVITY_STATUS_NORMAL).where(TABLE.ID.eq(id)).execute();
    }

    /**
     * 获取活动明细
     */
    public PreSaleVo getDetail(Integer preSaleId) {
        PreSaleVo preSaleVo =
            db().select(TABLE.ID, TABLE.PRE_START_TIME,
                TABLE.PRE_END_TIME, TABLE.START_TIME, TABLE.END_TIME, TABLE.PRESALE_NAME, TABLE.BUY_NUMBER,
                TABLE.BUY_TYPE, TABLE.DELIVER_DAYS, TABLE.DELIVER_TIME, TABLE.DELIVER_TYPE, TABLE.GOODS_ID,
                TABLE.DISCOUNT_TYPE, TABLE.PRE_PAY_STEP, TABLE.PRESALE_TYPE, TABLE.RETURN_TYPE, TABLE.SHARE_CONFIG,
                TABLE.SHOW_SALE_NUMBER, TABLE.STATUS, TABLE.DEL_FLAG,TABLE.FIRST,TABLE.PRE_TIME)
                .select(TABLE.PRE_START_TIME_2.as("preStartTime2"))
                .select(TABLE.PRE_END_TIME_2.as("preEndTime2"))
                .from(TABLE)
                .where(TABLE.ID.eq(preSaleId))
                .fetchOneInto(PreSaleVo.class);
        List<ProductVo> productVos = db().select(SUB_TABLE.ID, SUB_TABLE.PRESALE_ID, SUB_TABLE.PRODUCT_ID,
            SUB_TABLE.PRESALE_MONEY, SUB_TABLE.PRESALE_NUMBER, SUB_TABLE.PRESALE_PRICE, SUB_TABLE.GOODS_ID,
            GOODS_SPEC_PRODUCT.PRD_ID, GOODS_SPEC_PRODUCT.PRD_DESC, GOODS_SPEC_PRODUCT.PRD_NUMBER,
            GOODS_SPEC_PRODUCT.PRD_PRICE,GOODS.GOODS_NAME)
            .select(SUB_TABLE.PRE_DISCOUNT_MONEY_1.as("preDiscountMoney1"))
            .select(SUB_TABLE.PRE_DISCOUNT_MONEY_2.as("preDiscountMoney2"))
            .from(SUB_TABLE)
            .leftJoin(GOODS_SPEC_PRODUCT).on(GOODS_SPEC_PRODUCT.PRD_ID.eq(SUB_TABLE.PRODUCT_ID))
            .leftJoin(GOODS).on(GOODS.GOODS_ID.eq(SUB_TABLE.GOODS_ID))
            .where(SUB_TABLE.PRESALE_ID.eq(preSaleId)).fetchInto(ProductVo.class);
        preSaleVo.setProducts(productVos);
        ShareConfig shareConfig = shareConfig(preSaleVo);
        preSaleVo.setShareAction(shareConfig.getShareAction());
        preSaleVo.setShareDoc(shareConfig.getShareDoc());
        preSaleVo.setShareImgAction(shareConfig.getShareImgAction());
        preSaleVo.setShareImg(shareConfig.getShareImg());
        preSaleVo.setStatus(preSaleVo.getStatus());
        return preSaleVo;
    }

    /**
     * 获取分享配置
     */
    private ShareConfig shareConfig(PreSaleVo preSaleVo) {
        ShareConfig shareConfig =  Util.parseJson(preSaleVo.getShareConfig(),ShareConfig.class);
        if(StringUtil.isNotBlank(shareConfig.getShareImg())){
            shareConfig.setShareImg(domainConfig.imageUrl(shareConfig.getShareImg()));
        }
        return shareConfig;
    }

    /**
     * 更新活动
     */
    public void updatePreSale(PreSaleParam param) {
        logger().info("预售活动-更新ID:{}",param.getId());
        Integer presaleId = param.getId();
        Assert.notNull(presaleId, "Missing parameter id");
        PresaleRecord presale = db().newRecord(TABLE, param);
        String config = shareConfigJson(param);
        presale.setShareConfig(config);
        validateParam(param);
        if(param.getPreStartTime2() != null && param.getPreEndTime2() != null){
            presale.setPreStartTime_2(param.getPreStartTime2());
            presale.setPreEndTime_2(param.getPreEndTime2());
        }
        presale.update();
        transaction(()->{
            List<Integer> preProductIdRecordIds = new ArrayList<>(param.getProducts().size());
            param.getProducts().forEach(product -> {
                product.setPresaleId(presaleId);
                PresaleProductRecord presaleProductRecord = db().newRecord(SUB_TABLE, product);
                presaleProductRecord.setPreDiscountMoney_1(product.getPreDiscountMoney1());
                if(product.getPreDiscountMoney2() != null){
                    presaleProductRecord.setPreDiscountMoney_2(product.getPreDiscountMoney2());
                }
                if (presaleProductRecord.getId()==null){
                    presaleProductRecord.insert();
                }else {
                    presaleProductRecord.update();
                }
                preProductIdRecordIds.add(presaleProductRecord.getId());
            });
            db().delete(SUB_TABLE).where(SUB_TABLE.PRESALE_ID.eq(presaleId).and(SUB_TABLE.ID.notIn(preProductIdRecordIds))).execute();
        });
    }


    /**
     * 查询活动有效时间区间
     * @param id
     * @return Record2<START_TIME, END_TIME>
     */
    public Record2<Timestamp, Timestamp> getTimeInterval(Integer id) {
    	return db().select(TABLE.START_TIME,TABLE.END_TIME).from(TABLE).where(TABLE.ID.eq(id)).fetchOne();
    }


    public Optional<Record2<Integer,BigDecimal>> getPresaleProductRecordByGoodsId(Integer goodsId, Timestamp date){
        return db().select(TABLE.ID,SUB_TABLE.PRESALE_PRICE).from(TABLE)
            .leftJoin(SUB_TABLE).on(SUB_TABLE.PRESALE_ID.eq(TABLE.ID))
            .where(SUB_TABLE.GOODS_ID.eq(goodsId))
            .and(TABLE.START_TIME.lessThan(date))
            .and(TABLE.END_TIME.greaterThan(date))
            .and(TABLE.STATUS.eq((byte)1))
            .fetchOptional();
    }
    public Map<Integer,BigDecimal> getPresaleProductRecordByGoodsIds(List<Integer> goodsIds, Timestamp date){
        return db().select(SUB_TABLE.GOODS_ID,SUB_TABLE.PRESALE_PRICE).from(TABLE)
            .leftJoin(SUB_TABLE).on(SUB_TABLE.PRESALE_ID.eq(TABLE.ID))
            .where(SUB_TABLE.GOODS_ID.in(goodsIds))
            .and(TABLE.START_TIME.lessThan(date))
            .and(TABLE.END_TIME.greaterThan(date))
            .and(TABLE.STATUS.eq((byte)1))
            .fetchMap(SUB_TABLE.GOODS_ID,SUB_TABLE.PRESALE_PRICE);
    }

	/**
	 * N小时前后的要结束的定金膨胀列表
	 *
	 * @param hours
	 * @param type 0:还没开始；1：开始
	 * @return
	 */
	public List<PreSaleVo> getPreSaleListByHour(Integer hours,Byte type) {
		Timestamp timeStampPlus = DateUtil.getTimeStampPlus(hours, ChronoUnit.HOURS);
		String date = DateUtil.dateFormat("yyyy-MM-dd HH:mm", timeStampPlus);
		SelectConditionStep<PresaleRecord> fetch = db().selectFrom(PRESALE)
				.where(PRESALE.DEL_FLAG.eq(DelFlag.NORMAL_VALUE));
		//还没开始
		if(type.equals((byte) 0)) {
			fetch.and(dateFormat(PRESALE.END_TIME, DateUtil.DATE_MYSQL_DAY).eq(date));
		}
		//快开始
		if(type.equals((byte) 1)) {
			fetch.and(dateFormat(PRESALE.START_TIME, DateUtil.DATE_MYSQL_DAY).eq(date));
		}
		Result<PresaleRecord> fetch2 = fetch.fetch();
		List<PreSaleVo> into = new ArrayList<PreSaleVo>();
		if (fetch != null) {
			into = fetch2.into(PreSaleVo.class);
		}
		return into;

	}

    /**
     * 根据活动id获取预售活动record信息
     * @param activityId 活动id
     * @return record信息
     */
	public PresaleRecord getPresaleRecord(Integer activityId){
        return db().selectFrom(PRESALE).where(PRESALE.DEL_FLAG.eq(DelFlag.NORMAL_VALUE).and(PRESALE.ID.eq(activityId)))
            .fetchAny();
    }

    /**
     * 获取小程序码
     */
    public ShareQrCodeVo getMpQrCode(Integer id) {
        int goodsId = db().select(PRESALE.GOODS_ID).from(PRESALE).where(PRESALE.ID.eq(id)).fetchAny().into(Integer.class);
        String pathParam=String.format("gid=%d&aid=%d&atp=%d", goodsId, id, BaseConstant.ACTIVITY_TYPE_PRE_SALE);
        String imageUrl = qrCode.getMpQrCode(QrCodeTypeEnum.GOODS_ITEM, pathParam);

        ShareQrCodeVo vo = new ShareQrCodeVo();
        vo.setImageUrl(imageUrl);
        vo.setPagePath(QrCodeTypeEnum.GOODS_ITEM.getPathUrl(pathParam));
        return vo;
    }

}
