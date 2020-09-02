package com.vpu.mp.service.pojo.shop.auth;

import com.vpu.mp.common.foundation.data.DelFlag;

/**
 * 
 * @author zhaojianqiang
 * @date 2020年5月20日下午2:10:43
 */
public class AuthConstant {
	public static final String KEY_MENU = "authority";
	public static final String KEY_AUTHORITY = "privilegePass";
	public static final String KEY_VERSION = "version";

	/** 下面是文件名*/
	public static final String FILE_MENUJSON = "admin.authorityNew.json";
	public static final String FILE_AUTHORITYJSON = "admin.privilegePassNew.json";
	public static final String FILE_VERSIONJSON = "admin.versionNew.json";

    /**
     * 用户登录角色
     */
    /**
     * 普通用户
     */
	public static final Byte AUTH_TYPE_NORMAL_USER = 0;
    /**
     * 医师
     */
	public static final Byte AUTH_TYPE_DOCTOR_USER = 1;
    /**
     * 药师
     */
	public static final Byte AUTH_TYPE_PHARMACIST_USER = 2;
}
