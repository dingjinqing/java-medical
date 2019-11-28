package com.vpu.mp.service.foundation.util;
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
	
}
