package com.vpu.mp.controller.admin;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.Part;
import javax.validation.Valid;

import com.vpu.mp.db.main.tables.records.ShopUploadedImageRecord;
import com.vpu.mp.service.pojo.saas.shop.image.ShopUploadedImageVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.db.shop.tables.records.UploadedImageRecord;
import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.image.BatchDeleteImageParam;
import com.vpu.mp.service.pojo.shop.image.BatchMoveImageParam;
import com.vpu.mp.service.pojo.shop.image.CropImageParam;
import com.vpu.mp.service.pojo.shop.image.ImageListQueryParam;
import com.vpu.mp.service.pojo.shop.image.UploadImageCatNameVo;
import com.vpu.mp.service.pojo.shop.image.UploadImageParam;
import com.vpu.mp.service.pojo.shop.image.UploadPath;
import com.vpu.mp.service.pojo.shop.image.UploadedImageVo;
import com.vpu.mp.service.shop.ShopApplication;

/**
 *
 * @author 新国
 *
 */
@RestController
@RequestMapping("/api")
public class AdminImageController extends AdminBaseController {

	/**
	 * 上传多张图片
	 *
	 * @param  param
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	@PostMapping(value = "/admin/image/upload")
	public JsonResult imageUpload( UploadImageParam param) throws IOException, Exception {
		List<UploadedImageVo> uploadImages = new ArrayList<UploadedImageVo>();
		JsonResult result = null;
		List<Part> files = Util.getFilePart(request, param.uploadFileId);
		int success = 0;
		for (int i = 0; i < files.size(); i++) {
			Part file = files.get(i);
			result = this.uploadOneFile(param, file);
			if (result.getError() == JsonResultCode.CODE_SUCCESS.getCode()) {
				uploadImages.add((UploadedImageVo) result.getContent());
				success++;
			}
		}
		if (success > 0) {
			return this.success(uploadImages);
		} else {
			return result != null ? result : fail(JsonResultCode.CODE_IMGAE_UPLOAD_FAILED);
		}
	}

	/**
	 * 上传单张图片
	 *
	 * @param  param
	 * @param  file
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	@PostMapping(value = "/admin/image/uploadOneImgae")
	protected JsonResult uploadOneFile(UploadImageParam param, Part file) throws IOException, Exception {
		//校验
		Object[] jsonResultCode = shop().image.validImageParam(param, file);
		if (jsonResultCode!=null){
			this.fail(Arrays.asList(jsonResultCode));
		}
		UploadPath uploadPath = shop().image.getImageWritableUploadPath(file.getContentType());
		//上传又拍云
	    boolean ret=  shop().image.uploadToUpYunBySteam(uploadPath.relativeFilePath,file.getInputStream());
		if (ret) {
			//保存记录
			UploadedImageVo uploadedImageVo = shop().image.addImageToDb(param, file, uploadPath).into(UploadedImageVo.class);
			return this.success(uploadedImageVo);
		}
		return fail(JsonResultCode.CODE_IMGAE_UPLOAD_FAILED);
	}

	/**
	 * 图片列表
	 *
	 * @param  param
	 * @return
	 */
	@PostMapping(value = "/admin/image/list")
	public JsonResult getImageList(@RequestBody ImageListQueryParam param) {
		PageResult<UploadImageCatNameVo> imageList = shop().image.getPageList(param);
		return this.success(imageList);
	}

	/**
	 * 图片裁剪
	 *
	 * @param  param
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/admin/image/makeCrop")
	public JsonResult makeCrop(@RequestBody CropImageParam param) throws Exception {
		UploadPath uploadPath = shop().image.makeCrop(param);
		if (uploadPath==null){
		    return fail();
        }
        UploadedImageRecord record = shop().image.addImageToDb(param, uploadPath);
		return success(record.into(ShopUploadedImageVo.class));
	}

	/**
	 * 批量移动分组
	 *
	 * @param  param
	 * @return
	 */
	@PostMapping("/admin/image/batch/move")
	public JsonResult batchMoveImage(@RequestBody @Valid BatchMoveImageParam param) {
		shop().image.setCatId(param.getImageIds().toArray(new Integer[0]), param.getImageCatId());
		return success();
	}

	/**
	 * 批量删除图片
	 *
	 * @param  param
	 * @return
	 */
	@PostMapping("/admin/image/batch/delete")
	public JsonResult batchDeleteImage(@RequestBody BatchDeleteImageParam param) {
		shop().image.removeRows(param.getImageIds());
		return success();
	}

}
