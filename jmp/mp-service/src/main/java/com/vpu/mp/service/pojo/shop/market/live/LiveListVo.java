package com.vpu.mp.service.pojo.shop.market.live;

import java.sql.Timestamp;

import lombok.Data;

/**
 * 
 * @author zhaojianqiang
 * @time 下午4:04:17
 */
@Data
public class LiveListVo {
	private Integer id;
	private Integer room_id;
	private String name;
	private Short live_status;
	private Timestamp start_time;
	private Timestamp end_time;
	private String anchor_name;
	private String cover_img;
	private String anchor_img;
	private Timestamp add_time;
	private Timestamp update_time;
	private Byte del_flag;
	private Timestamp del_time;

}
