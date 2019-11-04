
package com.vpu.mp.service.pojo.saas.shop;

import java.sql.Timestamp;

import com.vpu.mp.service.pojo.saas.shop.version.VersionConfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopVersionVo {
	private Integer id;
	private String versionName;
	private String level;
	private VersionConfig content;
	private Timestamp created;
	private Timestamp updateTime;
	private String desc;
	private Byte flag;
}
