package com.vpu.mp.service.shop.coupon;

import static com.vpu.mp.db.shop.Tables.CART;
import static com.vpu.mp.db.shop.Tables.CUSTOMER_AVAIL_COUPONS;
import static com.vpu.mp.db.shop.Tables.GIVE_VOUCHER;
import static com.vpu.mp.db.shop.Tables.MRKING_VOUCHER;
import static com.vpu.mp.db.shop.Tables.ORDER_GOODS;
import static com.vpu.mp.db.shop.Tables.ORDER_INFO;
import static com.vpu.mp.db.shop.Tables.USER;
import static com.vpu.mp.db.shop.Tables.USER_CARD;
import static com.vpu.mp.db.shop.Tables.USER_LOGIN_RECORD;
import static com.vpu.mp.db.shop.Tables.USER_TAG;
import static com.vpu.mp.db.shop.Tables.MEMBER_CARD;
import static com.vpu.mp.db.shop.Tables.TAG;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.vpu.mp.service.foundation.data.DelFlag;
import jodd.util.StringUtil;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.util.StringUtils;
import com.vpu.mp.config.mq.RabbitConfig;
import com.vpu.mp.db.shop.tables.MrkingVoucher;
import com.vpu.mp.db.shop.tables.records.CustomerAvailCouponsRecord;
import com.vpu.mp.db.shop.tables.records.MrkingVoucherRecord;
import com.vpu.mp.service.foundation.mq.RabbitmqSendService;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.coupon.give.CouponGiveDeleteParam;
import com.vpu.mp.service.pojo.shop.coupon.give.CouponGiveDetailParam;
import com.vpu.mp.service.pojo.shop.coupon.give.CouponGiveGrantParam;
import com.vpu.mp.service.pojo.shop.coupon.give.CouponGiveListConditionVo;
import com.vpu.mp.service.pojo.shop.coupon.give.CouponGiveListParam;
import com.vpu.mp.service.pojo.shop.coupon.give.CouponGiveListVo;
import com.vpu.mp.service.pojo.shop.coupon.give.CouponGivePopParam;
import com.vpu.mp.service.pojo.shop.coupon.give.CouponGivePopVo;
import com.vpu.mp.service.pojo.shop.coupon.give.CouponGiveQueueParam;
import com.vpu.mp.service.pojo.shop.coupon.hold.CouponHoldListParam;
import com.vpu.mp.service.pojo.shop.coupon.hold.CouponHoldListVo;

/**
 * 优惠券管理
 *
 * @author liangchen
 * @date 2019年7月29日
 */
@Service

public class CouponGiveService extends ShopBaseService {

	@Autowired
    private CouponHoldService couponHold;

	@Autowired
    private RabbitmqSendService rabbitmqSendService;
	
    private static final MrkingVoucher MV = MrkingVoucher.MRKING_VOUCHER.as("MV");

    /** 是否有最低消费使用限制，0不限制，1有限制 */
    public static final byte USE_CONSUME_RESTRICT_EFFECTIVE = 1;
    public static final byte USE_CONSUME_RESTRICT_INVALID = 0;

    /** 优惠券类型，0普通优惠券；1分裂优惠券 */
    public static final byte COUPON_TYPE_NORMAL = 0;
    public static final byte COUPON_TYPE_SPLIT = 1;

    /** 优惠券有效期类型标记，1领取后开始指定时间段内有效，0固定时间段有效 */
    public static final byte VALIDITY_TYPE_FIXED = 0;
    public static final byte VALIDITY_TYPE_FLEXIBLE = 1;

    /** 普通优惠券类型，voucher指定金额券，discount折扣券 */
    public static final String ACT_CODE_VOUCHER = "voucher";
    public static final String ACT_CODE_DISCOUNT = "discount";

    /**
     * 优惠券发放情况分页列表
     *
     * @param param
     * @return listVo
     */
    public PageResult<CouponGiveListVo> getCouponGiveList(CouponGiveListParam param) {
        try {
            /* 查询活动信息 */
            SelectLimitStep<Record> couponGiveListVo = db().select().from(GIVE_VOUCHER);
            /* 模糊查询 */
            if (!StringUtils.isNullOrEmpty(param.getActName())) {
                couponGiveListVo = ( (SelectWhereStep<Record>) couponGiveListVo).where(GIVE_VOUCHER.ACT_NAME.like(this.likeValue(param.getActName())));
            }
            /* 整合分页信息 */
            PageResult<CouponGiveListVo> listVo = this.getPageResult(couponGiveListVo, param.getCurrentPage(), param.getPageRows(), CouponGiveListVo.class);
            /* 整合活动对应优惠券信息 */
            for (CouponGiveListVo vo : listVo.getDataList()) {
                /* 解析得到活动中包含的优惠券 */
                String dataList = vo.getSendCondition();
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode;
                jsonNode = objectMapper.readTree(dataList);
                String voucherId = jsonNode.get("coupon_ids").toString();
                String id = voucherId.replace("\"", "");
                String[] idArray = id.split(",");
                /* 优惠券信息 */
                List<CouponGiveListConditionVo> tempListVo = new ArrayList<CouponGiveListConditionVo>();
                for (String selectId : idArray) {
                    Optional<CouponGiveListConditionVo> couponVo = db().select(MRKING_VOUCHER.ID.as("couponId"),MRKING_VOUCHER.ACT_NAME.as("coupon_name"), MRKING_VOUCHER.LEAST_CONSUME, 
                    		MRKING_VOUCHER.DENOMINATION,MRKING_VOUCHER.START_TIME,MRKING_VOUCHER.END_TIME, MRKING_VOUCHER.VALIDITY_TYPE,
                    		MRKING_VOUCHER.VALIDITY,MRKING_VOUCHER.VALIDITY_HOUR,MRKING_VOUCHER.VALIDITY_MINUTE).from(MRKING_VOUCHER)
                    		.where(MRKING_VOUCHER.ID.eq(Integer.valueOf(selectId)))
                    		.fetchOptionalInto(CouponGiveListConditionVo.class);
                    tempListVo.add(couponVo.isPresent() ? couponVo.get() : null);
                }
                
                /** 完善某一活动对应的优惠券信息 */
                vo.setCouponGiveListConditionVo(tempListVo);
               //会员卡信息
                if (!StringUtils.isNullOrEmpty(vo.getCardId())) {
                	String[] cardId = vo.getCardId().split(",");
                	List<String> cardIdList = new ArrayList<String>();
                	for (String cId : cardId) {
						cardIdList.add(cId);
					}
                	 List<String> cardName = db().select(MEMBER_CARD.CARD_NAME).from(MEMBER_CARD)
                     		.where(MEMBER_CARD.ID.in(cardIdList))
                     		.fetchInto(String.class);
                	 //将信息返回
                	 vo.setCardName(cardName);
                }
              //标签信息
                if (!StringUtils.isNullOrEmpty(vo.getTagId())) {
                	String[] tagId = vo.getTagId().split(",");
                	List<String> tagIdList = new ArrayList<String>();
                	for (String tId : tagId) {
						tagIdList.add(tId);
					}
                	 List<String> tagName = db().select(TAG.TAG_NAME).from(TAG)
                     		.where(TAG.TAG_ID.in(tagIdList))
                     		.fetchInto(String.class);
                	 //将信息返回
                	 vo.setTagName(tagName);
                }
            }

            return listVo;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

	/**
	 * 优惠券明细
	 * 
	 * @param param
	 * @return
	 */
	public PageResult<CouponHoldListVo> getDetail(CouponGiveDetailParam param) {

		CouponHoldListParam couponParam  =new CouponHoldListParam();
		couponParam.setActId(param.getActId());
		couponParam.setMobile(param.getMobile());
		couponParam.setUsername(param.getUsername());
		couponParam.setStatus(param.getIsUsed().byteValue());
		couponParam.setCurrentPage(param.getCurrentPage());
		couponParam.setPageRows(param.getPageRows());
		return   couponHold.getCouponHoldList(couponParam);

	}

    /**
     * 发放优惠券
     *
     * @param param
     * @return
     */
    public void insertGrant(CouponGiveGrantParam param) {

        try {
            /* 完成一次发券活动 */
            ObjectMapper objectMapper = new ObjectMapper();
            String condition = objectMapper.writeValueAsString(param.getCouponGiveGrantInfoParams());

            db().insertInto(GIVE_VOUCHER, GIVE_VOUCHER.ACT_NAME, GIVE_VOUCHER.SEND_CONDITION, GIVE_VOUCHER.CARD_ID, GIVE_VOUCHER.TAG_ID, GIVE_VOUCHER.USER, GIVE_VOUCHER.HAVE_PAY, GIVE_VOUCHER.NO_PAY, GIVE_VOUCHER.MAX_COUNT, GIVE_VOUCHER.MIN_COUNT, GIVE_VOUCHER.MAX_AVE_PRICE, GIVE_VOUCHER.MIN_AVE_PRICE, GIVE_VOUCHER.SEND_ACTION, GIVE_VOUCHER.START_TIME).values(param.getActName(), condition, param.getCardId(), param.getTagId(), param.getUser(), param.getHavePay(), param.getNoPay(), param.getMaxCount(), param.getMinCount(), param.getMaxAvePrice(), param.getMinAvePrice(), param.getSendAction(), param.getStartTime()).execute();
            int actId = db().select(GIVE_VOUCHER.ID).from(GIVE_VOUCHER).orderBy(GIVE_VOUCHER.CREATE_TIME.desc()).limit(1).fetchOptionalInto(Integer.class).orElse(0);
            Set<Integer> userIds = new HashSet<Integer>();
            /* 将发券活动写入用户-优惠券对应表 */

            /* 加购人群 */
            Date today = new Date();
            Timestamp cartDay = Util.getEarlyTimeStamp(today, -30);
            if (param.getCouponGiveGrantInfoParams().getCartBox().equals(1)) {
                List<Record1<Integer>> cartUserIds = db().select(CART.USER_ID).from(CART).where(CART.CREATE_TIME.greaterOrEqual(cartDay)).fetch();
                for (Record1<Integer> cartUserId : cartUserIds) {
                    userIds.add(cartUserId.value1());
                }
            }


            /* 购买指定商品人群 */
            if (param.getCouponGiveGrantInfoParams().getGoodsBox().equals(1)) {
                String goodsIds = param.getCouponGiveGrantInfoParams().getGoodsIds().toString();
                String[] goodArray = goodsIds.split(",");

                for (String goodId : goodArray) {
                    List<Record1<Integer>> orderIds = db().select(ORDER_GOODS.ORDER_ID).from(ORDER_GOODS).where(ORDER_GOODS.GOODS_ID.eq(Integer.valueOf(goodId))).fetch();
                    for (Record1<Integer> orderId : orderIds) {
                        int userId = db().select(ORDER_INFO.USER_ID).from(ORDER_INFO).where(ORDER_INFO.ORDER_ID.eq(orderId.value1())).fetchOptionalInto(Integer.class).orElse(0);
                        userIds.add(userId);
                    }
                }
            }

            /* 持有会员卡人群 */
            if (param.getCouponGiveGrantInfoParams().getCardBox().equals(1)) {
                String cardIds = param.getCardId();
                String[] cardArray = cardIds.split(",");
                for (String cardId : cardArray) {
                    List<Record1<Integer>> cardUserIds = db().select(USER_CARD.USER_ID).from(USER_CARD).where(USER_CARD.FLAG.eq((byte) 0)).and(USER_CARD.CARD_ID.eq(Integer.valueOf(cardId))).fetch();
                    for (Record1<Integer> userId : cardUserIds) {
                        userIds.add(userId.value1());
                    }
                }
            }

            /* 属于标签人群 */
            if (param.getCouponGiveGrantInfoParams().getTagBox().equals(1)) {
                String tagIds = param.getTagId();
                String[] tagArray = tagIds.split(",");
                for (String tagId : tagArray) {
                    List<Record1<Integer>> tagUserIds = db().select(USER_TAG.USER_ID).from(USER_TAG).where(USER_TAG.IS_DELETE.eq((byte) 0)).and(USER_TAG.TAG_ID.eq(Integer.valueOf(tagId))).fetch();
                    for (Record1<Integer> userId : tagUserIds) {
                        userIds.add(userId.value1());
                    }
                }
            }

            /* 选择指定的会员 */
            if (param.getCouponGiveGrantInfoParams().getMemberBox().equals(1)) {
                String memberIds = param.getUser();
                String[] memberArray = memberIds.split(",");
                for (String memberId : memberArray) {
                    userIds.add(Integer.valueOf(memberId));
                }
            }

            /* N天内有交易记录 */
            if (param.getCouponGiveGrantInfoParams().getCustomBox().equals(1)) {
                Timestamp havePayDay = Util.getEarlyTimeStamp(today, -param.getHavePay());
                List<Record1<Integer>> havePayUserIds = db().select(ORDER_INFO.USER_ID).from(ORDER_INFO).where(ORDER_INFO.ORDER_STATUS.greaterOrEqual((byte) 2)).and(ORDER_INFO.CREATE_TIME.greaterOrEqual(havePayDay)).fetch();

                for (Record1<Integer> havePayUserId : havePayUserIds) {
                    userIds.add(havePayUserId.value1());
                }
            }


            /* N天内无交易记录 */
            if (param.getCouponGiveGrantInfoParams().getCustomBox().equals(1)) {
                Timestamp noPayDay = Util.getEarlyTimeStamp(today, -param.getNoPay());
                List<Record1<Integer>> noPayUserIds = db().select(ORDER_INFO.USER_ID).from(ORDER_INFO).where(ORDER_INFO.ORDER_STATUS.greaterOrEqual((byte) 2)).and(ORDER_INFO.CREATE_TIME.greaterOrEqual(noPayDay)).fetch();
                for (Record1<Integer> noId : noPayUserIds) {
                    List<Record1<Integer>> noPayId = db().select(USER.USER_ID).from(USER).where(USER.USER_ID.notEqual(noId.value1())).fetch();
                    for (Record1<Integer> noPayUserId : noPayId) {
                        userIds.add(noPayUserId.value1());
                    }
                }
            }


            /* 累计购买次数大于N次 min */
            if (param.getCouponGiveGrantInfoParams().getCustomBox().equals(1)) {
                List<Record1<Integer>> minCountUserIds = db().select(ORDER_INFO.USER_ID).from(ORDER_INFO).where(ORDER_INFO.ORDER_STATUS.greaterOrEqual((byte) 2)).groupBy(ORDER_INFO.USER_ID).having(DSL.count(ORDER_INFO.ORDER_ID).greaterThan(param.getMinCount())).fetch();
                for (Record1<Integer> minCountUserId : minCountUserIds) {
                    userIds.add(minCountUserId.value1());
                }
            }

            /* 累计购买次数小于N次 max */
            if (param.getCouponGiveGrantInfoParams().getCustomBox().equals(1)) {
                List<Record1<Integer>> maxCountUserIds = db().select(ORDER_INFO.USER_ID).from(ORDER_INFO).where(ORDER_INFO.ORDER_STATUS.greaterOrEqual((byte) 2)).groupBy(ORDER_INFO.USER_ID).having(DSL.count(ORDER_INFO.ORDER_ID).lessThan(param.getMaxCount())).fetch();
                for (Record1<Integer> maxCountUserId : maxCountUserIds) {
                    userIds.add(maxCountUserId.value1());
                }
            }


            /* 购买商品均价大于N元 min */
            if (param.getCouponGiveGrantInfoParams().getCustomBox().equals(1)) {
                List<Record1<Integer>> minAvePriceUserIds = db().select(ORDER_INFO.USER_ID).from(ORDER_INFO).where(ORDER_INFO.ORDER_STATUS.greaterOrEqual((byte) 2)).groupBy(ORDER_INFO.USER_ID).having((DSL.sum(ORDER_INFO.ORDER_AMOUNT).divide(DSL.sum(ORDER_INFO.GOODS_AMOUNT))).greaterThan(param.getMinAvePrice())).fetch();
                for (Record1<Integer> minAvePriceUserId : minAvePriceUserIds) {
                    userIds.add(minAvePriceUserId.value1());
                }
            }

            /* 购买商品均价小于N元 max */
            if (param.getCouponGiveGrantInfoParams().getCustomBox().equals(1)) {
                List<Record1<Integer>> maxAvePriceUserIds = db().select(ORDER_INFO.USER_ID).from(ORDER_INFO).where(ORDER_INFO.ORDER_STATUS.greaterOrEqual((byte) 2)).groupBy(ORDER_INFO.USER_ID).having((DSL.sum(ORDER_INFO.ORDER_AMOUNT).divide(DSL.sum(ORDER_INFO.GOODS_AMOUNT))).lessThan(param.getMaxAvePrice())).fetch();
                for (Record1<Integer> maxAvePriceUserId : maxAvePriceUserIds) {
                    userIds.add(maxAvePriceUserId.value1());
                }
            }


            /* 指定时间内有登陆记录 */
            if (param.getCouponGiveGrantInfoParams().getCustomBox().equals(1)) {
                List<Record1<Integer>> loginRecordUserIds = db().select(USER_LOGIN_RECORD.USER_ID).from(USER_LOGIN_RECORD).where(USER_LOGIN_RECORD.CREATE_TIME.between(Timestamp.valueOf(param.getCouponGiveGrantInfoParams().getPointStartTime()), Timestamp.valueOf(param.getCouponGiveGrantInfoParams().getPointEndTme()))).fetch();
                for (Record1<Integer> loginRecordUserId : loginRecordUserIds) {
                    userIds.add(loginRecordUserId.value1());
                }
            }

            /* 队列 */
            List<Integer> userIdList = new ArrayList<Integer>(userIds);
            String couponIds = param.getCouponGiveGrantInfoParams().getCouponIds().toString();
            String[] couponArray = couponIds.split(",");
            rabbitmqSendService.sendMessage(RabbitConfig.EXCHANGE_MARKETING, RabbitConfig.BINDING_EXCHANGE_COUPON_KEY, new CouponGiveQueueParam(getShopId(), userIdList, actId, couponArray));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    /**
     * 定向发券，后台异步执行
     * @param param
     */
    public void handlerGouonGive(CouponGiveQueueParam param) {
            /* 插入user-coupon关联表 */
            for (String couponId : param.getCouponArray()) {
                String couponName = db().select(MRKING_VOUCHER.ACT_NAME).from(MRKING_VOUCHER).where(MRKING_VOUCHER.ID.eq(Integer.valueOf(couponId))).fetchOptionalInto(String.class).orElse(null);
                BigDecimal denomination = db().select(MRKING_VOUCHER.DENOMINATION).from(MRKING_VOUCHER).where(MRKING_VOUCHER.ID.eq(Integer.valueOf(couponId))).fetchOptionalInto(BigDecimal.class).orElse(null);
                for (Integer userId : param.getUserIds()) {
                    db().insertInto(CUSTOMER_AVAIL_COUPONS, CUSTOMER_AVAIL_COUPONS.ACT_ID, CUSTOMER_AVAIL_COUPONS.USER_ID, CUSTOMER_AVAIL_COUPONS.ACT_DESC, CUSTOMER_AVAIL_COUPONS.AMOUNT, CUSTOMER_AVAIL_COUPONS.COUPON_SN).values(param.getActId(), userId, couponName, denomination, getCouponSn()).execute();
                }
            }
    }

    /** 生成优惠券编号 */
    public static String getCouponSn() {
        return  "C" + System.currentTimeMillis();
    }

    /**
     * 优惠券弹窗
     *
     * @param param
     * @return popListVo
     */
    public List<CouponGivePopVo> popWindows(CouponGivePopParam param) {
        /* 查询，并筛选出正确的使用限制条件 */
        SelectWhereStep<? extends Record> select = db().select(MRKING_VOUCHER.ID,MRKING_VOUCHER.ACT_CODE,
            MRKING_VOUCHER.ACT_NAME, MRKING_VOUCHER.DENOMINATION, MRKING_VOUCHER.LEAST_CONSUME, MRKING_VOUCHER.USE_CONSUME_RESTRICT,MRKING_VOUCHER.SURPLUS,MRKING_VOUCHER.VALIDITY_TYPE,MRKING_VOUCHER.VALIDITY,MRKING_VOUCHER.VALIDITY_HOUR,MRKING_VOUCHER.VALIDITY_MINUTE,MRKING_VOUCHER.START_TIME,MRKING_VOUCHER.END_TIME,MRKING_VOUCHER.RECOMMEND_GOODS_ID,MRKING_VOUCHER.RECOMMEND_CAT_ID,MRKING_VOUCHER.RECOMMEND_SORT_ID).from(MRKING_VOUCHER);
        select.where(MRKING_VOUCHER.TYPE.eq(COUPON_TYPE_NORMAL)).and(MRKING_VOUCHER.DEL_FLAG.eq(DelFlag.NORMAL_VALUE));
        if(StringUtil.isNotEmpty(param.getActName())){
            select.where(MRKING_VOUCHER.ACT_NAME.contains(param.getActName()));
        }
        select.orderBy(MRKING_VOUCHER.CREATE_TIME.desc());
        List<CouponGivePopVo> popVo = select.fetchInto(CouponGivePopVo.class);

        return popVo;

    }

    /**
     * 废除优惠券
     *
     * @param param
     * @return
     */
    public void deleteCoupon(CouponGiveDeleteParam param) {
        /* 假删除实现废除某个用户的某张优惠券 */
        db().update(CUSTOMER_AVAIL_COUPONS).set(CUSTOMER_AVAIL_COUPONS.DEL_FLAG, (byte) 1).where(CUSTOMER_AVAIL_COUPONS.ID.eq(param.getId())).execute();
    }

    /**
     * 优惠券类型，打折还是优惠;1为减价，2为打折
     */
    private static final String DISCOUNT = "discount";
    private static final String VOUCHER = "voucher";

    /**
     * 参与表单反馈领取优惠券
     *
     * @param couponId 优惠券活动id
     * @param userId   用户id
     * @return 优惠券编号
     */
    public String collectingCoupons(Integer couponId, Integer userId) {
        Optional<MrkingVoucherRecord> result = db().selectFrom(MV).where(MV.ID.eq(couponId)).fetchOptional();
        MrkingVoucherRecord record = result.orElseThrow(()->new RuntimeException("Coupon_id Info doesn't exist"));
        CustomerAvailCouponsRecord couponsRecord = new CustomerAvailCouponsRecord();
        couponsRecord.setUserId(userId);
        couponsRecord.setAccessId(couponId);
        couponsRecord.setAccessMode((byte) 1);
        couponsRecord.setActDesc(record.getActName());
        couponsRecord.setActId(couponId);
        couponsRecord.setAmount(record.getDenomination());
        couponsRecord.setStartTime(record.getStartTime());
        couponsRecord.setEndTime(record.getEndTime());
        //1表示表单送券
        couponsRecord.setGetSource((byte) 1);
        //优惠券类型，打折还是优惠;1为减价，2为打折
        if (DISCOUNT.equals(record.getActCode())) {
            couponsRecord.setActType(2);
            couponsRecord.setType((byte) 2);
        } else if (VOUCHER.equals(record.getActCode())) {
            couponsRecord.setActType(1);
            couponsRecord.setType((byte) 1);
        }
        //0：未使用
        couponsRecord.setIsUsed((byte) 0);
        //0：未删除
        couponsRecord.setDelFlag((byte) 0);
        String couponSn = getCouponSn();
        couponsRecord.setCouponSn(couponSn);
        db().executeInsert(couponsRecord);
        return couponSn;
    }
}
