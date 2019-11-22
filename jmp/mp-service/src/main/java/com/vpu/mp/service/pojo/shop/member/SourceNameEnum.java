package com.vpu.mp.service.pojo.shop.member;
/**
* @author 黄壮壮
* @Date: 2019年9月2日
* @Description: 来源名称国际化
*/
public enum SourceNameEnum {
	
	/** 后台 */
	SRC_BACK_STAGE(0,"member.source.back_stage"),
	/** 未获取 */
	SRC_NOT_ACQUIRED(-1,"member.source.not_acquired"),
	/** 渠道页-- */
	SRC_CHANNAL_PAGE(1,"member.source.channal_page"),
	/** 全部门店 */
	SRC_ALL(-2,"member.source.all"),
	/** 扫码进入 */
	SRC_SCAN_QRCODE(-3,"member.source.scan_qrcode");

	
	/** 数字代号 */
	private Integer code;
	/**来源名称*/
	private String name;
	private SourceNameEnum(Integer code,String name) {
		this.code = code;
		this.name = name;
	} 
	
	public Integer getCode() {
		return code;
	}
	
	public String getName() {
		return name;
	}
}
