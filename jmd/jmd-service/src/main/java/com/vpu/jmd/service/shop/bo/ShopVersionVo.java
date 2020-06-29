
package com.vpu.jmd.service.shop.bo;

import com.vpu.jmd.service.shop.bo.version.VersionConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 *
 * @author zhaojianqiang
 *
 * 2019年11月14日 上午10:29:34
 */
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
