package com.vpu.mp.service.pojo.shop.member;

import lombok.Getter;
import lombok.Setter;

/**
 * admin系统店铺会员入参参数 TODO
 * 
 * @author 黄壮壮 2019-07-05 18:06
 */
@Getter
@Setter
public class MemberPageListParam extends BaseMemberPojo {

	/** -手机号 */
	private String mobile;
	/** - 微信昵称 */
	private String username;
	/** -来源 ： -1 未录入 0后台>0为门店  {@link com.vpu.mp.service.pojo.shop.member.SourceNameEnum.SCAN_QRCODE }*/
	private Integer source;
	/** - 会员卡id  */
	private Integer cardId;
	/** -邀请人*/
	private String inviteUserName;
	/** - 创建时间 */
	private String createTime;
	/** -结束时间*/
	private String endTime;
	

}
