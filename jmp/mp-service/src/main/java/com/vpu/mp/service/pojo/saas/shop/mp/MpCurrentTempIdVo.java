package com.vpu.mp.service.pojo.saas.shop.mp;

import lombok.Data;
import org.jooq.Condition;
import org.jooq.impl.DSL;

import static com.vpu.mp.db.main.tables.MpAuthShop.MP_AUTH_SHOP;

/**
 * 
 * @author zhaojianqiang
 *
 * 2019年8月9日 下午5:06:50
 */
@Data
public class MpCurrentTempIdVo {

	private Integer currentUseTemplateId;

}
