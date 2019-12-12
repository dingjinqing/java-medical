package com.vpu.mp.service.foundation.util;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vpu.mp.db.shop.tables.records.MemberCardRecord;
import com.vpu.mp.service.pojo.shop.member.card.CardConstant;
/**
* @author 黄壮壮
* @Date: 2019年11月28日
* @Description: 抽离出的关于会员卡的工具类
*/
public class CardUtil {
	
	/**
	 * 	会员卡是颜色背景吗
	 *	@return true: 是，false: 不是
	 */
	public static boolean isBgColorType(Byte type) {
		return CardConstant.MCARD_BGT_COLOR.equals(type);
	}
	
	/**
	 * 	会员卡是图片背景吗
	 *	@return true: 是，false: 不是
	 */
	public static boolean isBgImgType(Byte type) {
		return CardConstant.MCARD_BGT_IMG.equals(type);
	}
	
	/**
	 * 是否等级会员卡
	 */
	public static boolean isGradeCard(Byte cardType) {
		return CardConstant.MCARD_TP_GRADE.equals(cardType);
	}
	/**
	 * 	是否为限次卡
	 */
	public static boolean isLimitCard(Byte cardType) {
		return CardConstant.MCARD_TP_LIMIT.equals(cardType);
	}
	/**
	 * 是否为普通卡
	 */
	public static boolean isNormalCard(Byte cardType) {
		return  CardConstant.MCARD_TP_NORMAL.equals(cardType);
	}
	
	/**
	 * 卡是否需要购买
	 */
	public static boolean isNeedToBuy(Byte isPay) {
		return CardConstant.MCARD_ISP_BUY.equals(isPay);
	}
	
	/**
	 * 卡领取是否需要码
	 * @return true: 需要；false: 不需要
	 */
	public static boolean isNeedReceiveCode(Byte isPay) {
		return CardConstant.MCARD_ISP_CODE.equals(isPay);
	}
	/**
	 * 卡领取需要领取码
	 * @return true: 需要 ; false: 不需要
	 */
	public static boolean isReceiveByCode(Byte type) {
		return CardConstant.MCARD_REA_CODE.equals(type);
	}
	
	/**
	 * 卡领取需要密码
	 */
	public static boolean isReceiveByPwd(Byte type) {
		return CardConstant.MCARD_REA_PWD.equals(type);
	}
	
	/**
	 * 卡是否永久有效
	 * @return true: 需要 ; false: 不需要
	 */
	public static boolean isCardTimeForever(Byte expireType) {
		return CardConstant.MCARD_ET_FOREVER.equals(expireType);
	}
	
	/**
	 * 卡固定日期有效
	 */
	public static boolean isCardFixTime(Byte expireType) {
		return CardConstant.MCARD_ET_FIX.equals(expireType);
	}
	
	/**
	 * 卡是否过期
	 * @return true: 已经过期，false: 未过期
	 */
	public static boolean isCardExpired(Timestamp endTime) {
		return DateUtil.getLocalDateTime().after(endTime);
	}
	
	/**
	 * 是否可以兑换商品
	 * @return true: 是，false: 否
	 */
	public static boolean canExchangGoods(Byte isExchang) {
		return !CardConstant.MCARD_ISE_NON.equals(isExchang);
	}
	
	/**
	 * 是否可以再门店使用
	 * @return true: 可以，false: 不可以
	 */
	public static boolean canUseInStore(Byte type) {
		return NumberUtils.BYTE_ZERO.equals(type);
	}
	
	/**
	 * 是否开卡送券
	 * @return true: 开卡送优惠券；false: 开卡不送优惠券
	 */
	public static boolean isOpenCardSendCoupon(Byte type) {
		return CardConstant.MCARD_SEND_COUPON_ON.equals(type);
	}
	
	/**
	 * 送优惠券
	 */
	public static boolean isSendCoupon(Byte type) {
		return CardConstant.MCARD_COUPON_TYPE.equals(type);
	}
	/**
	 * 送优惠卷礼包
	 */
	public static boolean isSendCouponPack(Byte type) {
		return CardConstant.MCARD_COUPON_PACK_TYPE.equals(type);
	}
	
	/**
	 * 解析卡的使用门店
	 */
	public static List<Integer> parseStoreList(String storeList){
		return Util.json2Object(storeList, new TypeReference<List<Integer>>() {
        }, false);
	}
	
	/**
	 * 解析优惠券id
	 */
	public static List<Integer> parseCouponList(String couponList){
		if(StringUtils.isBlank(couponList)) {
			return new ArrayList<Integer>();
		}
		return Util.splitValueToList(couponList);
	}
	
	/**
	 * 卡是否可用
	 * @reture true: 可用；false: 不可用
	 */
	public static boolean isCardAvailable(Byte flag) {
		return CardConstant.MCARD_FLAG_USING.equals(flag);
	}
	
	/**
	 * 卡是否已经删除
	 * @return true: 已删除；false: 未删除
	 */
	public static boolean isCardDeleted(Byte type) {
		return CardConstant.MCARD_DF_YES.equals(type);
	}
	
	/**
	 * 卡是否有效
	 * @return true: 有效；false: 无效
	 */
	public static boolean isValidCard(MemberCardRecord card) {
		if(card==null) {
			return false;
		}
		if(isCardFixTime(card.getExpireType()) && isCardExpired(card.getEndTime())) {
			return false;
		}
		if(!isCardAvailable(card.getFlag())) {
			return false;
		}
		return true;
	}
}
