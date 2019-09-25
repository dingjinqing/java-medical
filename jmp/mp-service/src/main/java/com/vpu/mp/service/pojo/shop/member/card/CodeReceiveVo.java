package com.vpu.mp.service.pojo.shop.member.card;

import java.sql.Timestamp;

import lombok.Data;

/**
* @author 黄壮壮
* @Date: 2019年9月25日
* @Description: 会员卡领取详情出参数
*/
@Data
public class CodeReceiveVo {
	/** - 批次名称 */
	private String name;
	/** - 会员卡领取码表id */
	private Integer id;
	/** - 用户id */
	private Integer userId;
	/** - 用户名 */
	private String username;
	/** -手机号 */
	private String mobile;
	/** - 领取时间 */
	private Timestamp receiveTime;
	/** - 卡号 */
	private String cardNo;
	/** -领取码 */
	private String code;
	/** -卡密码 */
	private String cardPwd;
}
