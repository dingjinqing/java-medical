package com.vpu.mp.controller.admin;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.Part;
import javax.validation.Valid;

import com.vpu.mp.service.pojo.shop.image.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.db.shop.tables.records.UploadedImageRecord;
import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.JsonResultCode;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.foundation.Util;
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
	public JsonResult imageUpload(UploadImageParam param) throws IOException, Exception {
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
		ShopApplication shop = shop();
		Integer maxSize = 5 * 1024 * 1024;
		if (file.getSize() > maxSize) {
			return this.fail(JsonResultCode.CODE_IMGAE_UPLOAD_GT_5M);
		}
		if (!shop().image.validImageType(file.getContentType())) {
			return this.fail(JsonResultCode.CODE_IMGAE_FORMAT_INVALID);
		}
		if (param.needImgWidth != null || param.needImgHeight != null) {
			BufferedImage bufferImage;
			bufferImage = ImageIO.read(file.getInputStream());
			if (param.needImgWidth != null && param.needImgWidth != bufferImage.getWidth()) {
				return this.fail(JsonResultCode.CODE_IMGAE_UPLOAD_EQ_WIDTH, param.needImgWidth);
			}
			if (param.needImgHeight != null && param.needImgHeight != bufferImage.getWidth()) {
				return this.fail(JsonResultCode.CODE_IMGAE_UPLOAD_EQ_HEIGHT, param.needImgHeight);
			}
		}
		UploadPath uploadPath = shop.image.getImageWritableUploadPath(file.getContentType());
		file.write(uploadPath.fullPath);

		boolean ret = shop.image.uploadToUpYun(uploadPath.relativeFilePath, new File(uploadPath.fullPath));
		if (ret) {
			UploadedImageRecord record = shop.image.addImageToDb(uploadPath.relativeFilePath,
					shop.image.baseFilename(file.getSubmittedFileName()), file.getSubmittedFileName(), param.imgCatId);
			shop.image.rmFile(uploadPath.fullPath);
			return this.success(record.into(UploadedImageVo.class));
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
	public JsonResult getImageList(ImageListQueryParam param) {

		ShopApplication shop = shop();
		PageResult<UploadImageCatNameVo> imageList = shop.image.getPageList(param);
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
	public boolean makeCrop(CropImageParam param) throws Exception {
//		System.out.println(param.remoteImgPath);
		ShopApplication shop = shop();
		UploadPath uploadPath = shop.image.makeCrop(param);
		System.out.println(uploadPath);
		return true;
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
	public JsonResult batchDeleteImage(BatchDeleteImageParam param) {
		shop().image.removeRows(param.getImageIds());
		return success();
	}

}
