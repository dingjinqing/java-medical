package com.vpu.mp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * 
 * @author lixinguo
 *
 */
@Configuration
@Data
public class UpYunConfig {

	@Value(value = "${uyun.image.sv}")
	protected String server;

	@Value(value = "${uyun.image.op.name}")
	protected String name;

	@Value(value = "${uyun.image.op.pwd}")
	protected String password;

	@Value(value = "${uyun.video.sv}")
	protected String videoServer;

	@Value(value = "${uyun.video.op.name}")
	protected String videoOpName;

	@Value(value = "${uyun.video.op.pwd}")
	protected String videoOpPassword;

	@Value(value = "${uyun.video.domain}")
	protected String videoDomain;

	public String videoUrl(String path) {
		return "http://" + this.videoDomain + path;
	}
	
	public String imageUrl(String path) {
		return "http://" + this.videoDomain + path;
	}

}
