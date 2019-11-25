package com.vpu.mp.service.pojo.shop.decoration;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ActivityVo {
	public Integer id;
	public String actName;
	public String type;
	public Timestamp startTime;
	public Timestamp endTime;
}
