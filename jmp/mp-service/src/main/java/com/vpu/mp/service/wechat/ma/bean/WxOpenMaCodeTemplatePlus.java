package com.vpu.mp.service.wechat.ma.bean;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * 
 * @author zhaojianqiang
 *
 * 2019年8月7日 下午3:10:44
 */
@Data
public class WxOpenMaCodeTemplatePlus implements Serializable{
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	   * 草稿id
	   */
	  @SerializedName(value = "draftId", alternate = "draft_id")
	  private Long draftId;
	  /**
	   * 模版id
	   */
	  @SerializedName(value = "templateId", alternate = "template_id")
	  private Long templateId;
	  /**
	   * 模版版本号，开发者自定义字段
	   */
	  @SerializedName(value = "userVersion", alternate = "user_version")
	  private String userVersion;
	  /**
	   * 模版描述 开发者自定义字段
	   */
	  @SerializedName(value = "userDesc", alternate = "user_desc")
	  private String userDesc;
	  /**
	   * 开发者上传草稿时间 / 被添加为模版的时间
	   */
	  @SerializedName(value = "createTime", alternate = "create_time")
	  private Long createTime;
	  
	  /**
	   * 小程序id ，接口返回了，文档没写
	   */
	  private String source_miniprogram_appid;
	  /**
	   * 小程序名字
	   */
	  private String source_miniprogram;
	  
	  private String developer;

}
