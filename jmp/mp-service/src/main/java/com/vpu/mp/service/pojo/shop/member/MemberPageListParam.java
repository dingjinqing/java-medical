package com.vpu.mp.service.pojo.shop.member;

import java.math.BigDecimal;
import java.util.List;

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
	/** 会员id */
	private Integer userId;
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
	/** -注册 创建时间 */
	private String createTime;
	/** -注册结束时间*/
	private String endTime;
	
	
	/** -标签名称 */
	private String tagName;
	/** -指定时间内登录有记录 - 开始时间 */
	private String loginStartTime;
	/** -指定时间内登录有记录 - 结束时间 */
	private String loginEndTime;
	/** -指定时间内有加购行为 - 开始时间 */
	private String cartStartTime;
	/** -指定时间内有加购行为 - 结束时间 */
	private String cartEndTime;
	/** -指定时间内有交易行为 - 开始时间  */
	private String buyStartTime;
	/** -指定时间内有交易行为 - 结束时间  */
	private String buyEndTime;
	/** -客单价 - 最低 */
	private BigDecimal unitPriceLow;
	/** -客单价 - 最高 */
	private BigDecimal unitPriceHight;
	/** -累计购买次数  - 最低 */
	private Integer buyCountLow; 
	/** -累计购买次数  - 最高 */
	private Integer buyCountHight;
	/** - 累计指定商品 */
	private List<Integer> goodsId;
	
	/*---------新字段---------------*/
	/** -是否有手机 */
	private Boolean hasMobile=false;
	/** -是否有积分  */
	private Boolean hasScore=false;
	/** -是否有余额 */
	private Boolean hasBalance=false;
	/** -是否有会员卡 */
	private Boolean hasCard=false;
	/** -是否已禁止登录  */
	private Boolean hasDelete=false;
	/** -是否为导入会员 */
	private Boolean hasImport=false;
}
