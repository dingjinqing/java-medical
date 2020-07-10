package com.vpu.mp.service.pojo.shop.member.userImp;

import java.sql.Timestamp;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

/**
 * 文件上传
 * 
 * @author zhaojianqiang
 * @time 下午4:34:06
 */
@Data
public class UIGetListParam {
	/** 批次Id */
	private Integer batchId;
	private Timestamp startTime;
	private Timestamp endTime;
	/** -每页总数 */
	public Integer pageRows;
	/** -当前页 */
	public Integer currentPage;
}
