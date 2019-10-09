package com.vpu.mp.controller.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.Part;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.db.shop.tables.records.UploadedVideoRecord;
import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.video.BatchDeleteVideoParam;
import com.vpu.mp.service.pojo.shop.video.BatchMoveVideoParam;
import com.vpu.mp.service.pojo.shop.video.UploadVideoCatNameVo;
import com.vpu.mp.service.pojo.shop.video.UploadVideoParam;
import com.vpu.mp.service.pojo.shop.video.UploadedVideoVo;
import com.vpu.mp.service.pojo.shop.video.VideoListQueryParam;

/**
 *
 * @author 新国
 *
 */
@RestController
@RequestMapping("/api")
public class AdminVideoController extends AdminBaseController {

	/**
	 * 上传多张视频
	 *
	 * @param param 上传视频参数
	 * @return 上传视频记录信息
	 * @throws Exception 例外
	 */
	@PostMapping(value = "/admin/video/upload")
	public JsonResult imageUpload(UploadVideoParam param) throws Exception {
		List<UploadedVideoVo> uploadVideos = new ArrayList<UploadedVideoVo>();
        JsonResult result = null;
        List<Part> files = Util.getFilePart(request, param.uploadFileId);
		int success = 0;
		for (int i = 0; i < files.size(); i++) {
			Part file = files.get(i);
			result = this.uploadOneFile(param, file);
			if (result.getError() == JsonResultCode.CODE_SUCCESS.getCode()) {
				uploadVideos.add((UploadedVideoVo) result.getContent());
				success++;
			}
		}
		if (0 < success) {
			return this.success(uploadVideos);
		} else {
			return result != null ? result : fail(JsonResultCode.CODE_VIDEO_UPLOAD_FAILED);
		}
	}

	/**
	 * 上传单张视频
	 *
	 * @param param
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	@PostMapping(value = "/admin/video/upload/one")
	protected JsonResult uploadOneFile(UploadVideoParam param, Part file) throws IOException, Exception {
		// 校验
		Object[] jsonResultCode = shop().video.validVideoParam(param, file);
		if (jsonResultCode != null) {
			this.fail(Arrays.asList(jsonResultCode));
		}
		// 上传又拍云
		String filename= "";
		String dispostion = file.getHeader("content-disposition");
		String[] segs = dispostion.split(";");
		for(String seg : segs) {
			String[] values = seg.trim().split("=");
			if(values[0].equals("filename")) {
				filename = values[1].trim();
				filename = filename.substring(1,filename.length()-1);
				break;
			}
		}
		String ext = shop().video.getFileType(filename);
		UploadedVideoRecord record = shop().video.uploadVideoFile(filename,
				(new Long(file.getSize())).intValue(),
				file.getInputStream(), ext, param.getVideoCatId(), 0);
		UploadedVideoVo uploadedVideoVo = record.into(UploadedVideoVo.class);
		return this.success(uploadedVideoVo);
	}

	/**
	 * 视频列表
	 *
	 * @param param
	 * @return
	 */
	@PostMapping(value = "/admin/video/list")
	public JsonResult getVideoList(@RequestBody VideoListQueryParam param) {
		PageResult<UploadVideoCatNameVo> videoList = shop().video.getPageList(param);
		return this.success(videoList);
	}

	/**
	 * 批量移动分组
	 *
	 * @param param
	 * @return
	 */
	@PostMapping("/admin/video/batch/move")
	public JsonResult batchMoveVideo(@RequestBody @Valid BatchMoveVideoParam param) {
		shop().video.setCatId(param.getVideoIds().toArray(new Integer[0]), param.getVideoCatId());
		return success();
	}

	/**
	 * 批量删除视频
	 *
	 * @param param
	 * @return
	 */
	@PostMapping("/admin/video/batch/delete")
	public JsonResult batchDeleteVideo(@RequestBody BatchDeleteVideoParam param) {
		shop().video.removeRows(param.getVideoIds().toArray(new Integer[0]));
		return success();
	}

}
