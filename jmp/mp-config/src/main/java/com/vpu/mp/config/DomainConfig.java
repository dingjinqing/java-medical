package com.vpu.mp.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * 域名配置
 * 
 * @author lixinguo
 *
 */
@Configuration
@Data
public class DomainConfig {

	@Value(value = "${domain.main}")
	protected String mainDomain;

	@Value(value = "${domain.image}")
	protected String imageDomain;

	/**
	 * 图片路径
	 * 
	 * @param relativePath
	 * @return
	 */
	public String imageUrl(String relativePath) {
		return this.imageUrl(relativePath, null);
	}

	/**
	 * 图片路径
	 * 
	 * @param relativePath
	 * @return
	 */
	public String imageUrl(String relativePath, String schema) {
		schema = StringUtils.isBlank(schema) ? "http" : schema;
		return String.format("%s://%s/%s", schema, imageDomain, relativePath);
	}

	/**
	 * 主域名路径
	 * 
	 * @param relativePath
	 * @return
	 */
	public String mainUrl(String relativePath) {
		return this.mainUrl(relativePath, null);
	}

	/**
	 * 主域名路径
	 * 
	 * @param relativePath
	 * @return
	 */
	public String mainUrl(String relativePath, String schema) {
		schema = schema == null ? "http" : schema;
		return String.format("%s://%s%s", schema, mainDomain, relativePath);
	}
}
