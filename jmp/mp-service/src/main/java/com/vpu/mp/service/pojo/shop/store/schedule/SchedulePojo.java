package com.vpu.mp.service.pojo.shop.store.schedule;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 黄荣刚
 * @date 2019年7月16日
 *
 */
@Data
@NoArgsConstructor
public class SchedulePojo {
	private Byte scheduleId;
	private Integer storeId;
	private String scheduleName;
	private String begcreateTime;
	private String endTime;
}
