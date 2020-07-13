package com.vpu.mp.service.pojo.shop.member.account;
import java.sql.Timestamp;

import lombok.Data;
@Data
public class WxAppCardExamineVo {
	private Byte status;
	private Timestamp passTime;
	private Timestamp refuseTime;
	private String refuseDesc;
}
