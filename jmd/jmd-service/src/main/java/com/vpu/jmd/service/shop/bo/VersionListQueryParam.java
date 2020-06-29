package com.vpu.jmd.service.shop.bo;

import lombok.Data;

/**
 *
 * @author 新国
 *
 */
@Data
public class VersionListQueryParam {
	public Integer currentPage;
	public Integer pageRows;
	public String versionName;
}
