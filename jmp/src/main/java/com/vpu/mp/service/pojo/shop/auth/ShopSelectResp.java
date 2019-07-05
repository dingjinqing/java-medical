package com.vpu.mp.service.pojo.shop.auth;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.jooq.Record;

import com.vpu.mp.service.foundation.Page;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author zhaojianqiang
 *
 */
@Data
@NoArgsConstructor
public class ShopSelectResp{

	public List<?> dataList;
	public Map<String, String> versionMap;
	

}
