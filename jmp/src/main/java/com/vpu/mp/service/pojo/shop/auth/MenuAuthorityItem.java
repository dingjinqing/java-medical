package com.vpu.mp.service.pojo.shop.auth;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class MenuAuthorityItem {
	String enName;
	String name;
	String linkUrl;
	List<String> includeApi;
}
