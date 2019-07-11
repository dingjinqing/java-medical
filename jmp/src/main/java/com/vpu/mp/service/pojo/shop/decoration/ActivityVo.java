package com.vpu.mp.service.pojo.shop.decoration;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ActivityVo {
	public Integer id;
	public String name;
	public String actName;
	public String lotteryName;
	public Timestamp startTime;
	public Timestamp endTime;
}
