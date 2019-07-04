package com.vpu.mp.controller.admin;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.Part;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vpu.mp.db.shop.tables.records.UploadedImageRecord;
import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.JsonResultCode;
import com.vpu.mp.service.foundation.Util;
import com.vpu.mp.service.pojo.shop.image.UploadImageParam;
import com.vpu.mp.service.pojo.shop.image.UploadPath;
import com.vpu.mp.service.pojo.shop.image.UploadedImagePojo;
import com.vpu.mp.service.shop.ShopApplication;

/**
 * 
 * @author 新国
 *
 */
@Controller
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
	@ResponseBody
	public JsonResult imageUpload(UploadImageParam param) throws IOException, Exception {
		List<UploadedImagePojo> uploadImages = new ArrayList<UploadedImagePojo>();
		JsonResult result = null;
		List<Part> files = Util.getFilePart(request, param.uploadFileId);
		int success = 0;
		for (int i = 0; i < files.size(); i++) {
			Part file = files.get(i);
			result = this.uploadOneFile(param, file);
			if (result.getError() == JsonResultCode.CODE_SUCCESS.getCode()) {
				uploadImages.add((UploadedImagePojo) result.getContent());
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
	protected JsonResult uploadOneFile(UploadImageParam param, Part file) throws IOException, Exception {
		ShopApplication shop = shop();
		Integer maxSize = 5 * 1024 * 1024;
		if (file.getSize() > maxSize) {
			return this.fail(JsonResultCode.CODE_IMGAE_UPLOAD_GT_5M);
		}
		if (!shop().image.validImageType(file.getContentType())) {
			return this.fail(JsonResultCode.CODE_IMGAE_FORMAT_INVALID);
		}
		if (param.needImgWidth != null && param.needImgWidth > 0
				|| param.needImgHeight != null && param.needImgHeight > 0) {
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
		if(ret) {
			UploadedImageRecord record = shop.image.addImageToDb(uploadPath.relativeFilePath,
					shop.image.baseFilename(file.getSubmittedFileName()), file.getSubmittedFileName(), param.imgCatId);
			shop.image.rmFile(uploadPath.fullPath);
			return this.success(record.into(UploadedImagePojo.class));
		}
		return fail(JsonResultCode.CODE_IMGAE_UPLOAD_FAILED);
	}
}
