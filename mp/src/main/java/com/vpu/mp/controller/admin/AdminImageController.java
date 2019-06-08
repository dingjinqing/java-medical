package com.vpu.mp.controller.admin;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.Part;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.vpu.mp.db.shop.tables.records.UploadedImageRecord;
import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.foundation.Util;
import com.vpu.mp.service.shop.image.ImageService.CropImageParam;
import com.vpu.mp.service.shop.image.ImageService.ImageListQueryParam;
import com.vpu.mp.service.shop.image.ImageService.UploadPath;
import com.vpu.mp.support.LineConvertHump;

/**
 * 
 * @author 新国
 *
 */
@Controller
public class AdminImageController extends AdminBaseController {

	@RequestMapping(value = "/admin/manage/image/list")
	public ModelAndView getList(@LineConvertHump ImageListQueryParam param) {
		Map<String, Object> version = shop().version.getPictureNumConfig();
		String message = this.shop().image.processPostRequest(param);
		PageResult page = this.shop().image.getPageList(param);

		ModelMap model = new ModelMap();
		model.addAttribute("title", "图片空间管理");
		model.addAttribute("data_list", page.dataList);
		model.addAttribute("page", page.page);
		model.addAttribute("nav_type", 0);
		model.addAttribute("img_cat_arr", Util.toJSON(this.shop().image.category.getImageCategoryForZTree(null)));
		model.addAttribute("upload_sort_list", this.shop().image.getUploadSortList());
		model.addAttribute("img_cat_list", this.shop().image.category.getCategoryTree((byte) -1).intoMaps());
		model.addAttribute("version", version);
		model.addAttribute("message", message);

		return view("admin/image_manager_list", model);
	}

	final protected String prefixImgId = "IMG_ID:";

	@PostMapping(value = "/admin/ajax/image/upload")
	@ResponseBody
	public JsonResult upload() {
		String name = this.input("elname");
		List<Part> uploadParts = null;
		try {
			uploadParts = Util.getFilePart(request, name);
		} catch (IOException | ServletException e1) {
			e1.printStackTrace();
			return JsonResult.fail("上传失败");
		}
		List<Integer> imageIds = new ArrayList<Integer>();
		String lastError = "";
		for (Part part : uploadParts) {
			try {
				String message = this.uploadOneFile(part);
				if (message.startsWith(prefixImgId)) {
					imageIds.add(Util.getInteger(message.substring(prefixImgId.length(), message.length())));
				} else {
					lastError = message;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return JsonResult.fail("无效图片");
			}

		}
		if (imageIds.size() > 0) {
			return JsonResult.success(imageIds);
		} else {
			return JsonResult.fail(lastError);
		}
	}

	/**
	 * 上传一个图片
	 * 
	 * @param part
	 * @return
	 * @throws Exception
	 */
	protected String uploadOneFile(Part part) throws Exception {
		if (part.getSize() > 5 * 1024 * 1024) {
			return "上传图片大于5M";
		}
		if (!shop().image.validImageType(part.getContentType())) {
			return "上传图片格式不正确";
		}

		Integer needWidth = Util.getInteger(this.input("need_width"));
		Integer needHeight = Util.getInteger(this.input("need_height"));
		if (needWidth > 0 || needHeight > 0) {
			BufferedImage bufferImage;
			bufferImage = ImageIO.read(part.getInputStream());
			if (needWidth > 0 && needWidth != bufferImage.getWidth()) {
				return String.format("上传图片宽度必须为%dpx", needWidth);
			}
			if (needHeight > 0 && needHeight != bufferImage.getHeight()) {
				return String.format("上传图片宽度必须为%dpx", needHeight);
			}
		}
		UploadPath uploadPath = shop().image.getImageWritableUploadPath(part.getContentType());
		part.write(uploadPath.fullPath);
		UploadedImageRecord record = shop().image.addImageToDb(uploadPath.relativeFilePath,
				shop().image.baseFilename(part.getSubmittedFileName()),
				part.getSubmittedFileName(), Util.getInteger(this.input("img_cat_id")));

		return "IMG_ID:" + record.getImgId();
	}

	/**
	 * 剪裁图片
	 * 
	 * @param param
	 * @return
	 */
	@PostMapping(value = "/admin/ajax/image/crop")
	@ResponseBody
	public JsonResult cropImage(@LineConvertHump CropImageParam param) {
		try {
			UploadPath uploadPath = shop().image.makeCrop(param);
			UploadedImageRecord record = shop().image.getImageById(param.remoteImgId);
			UploadedImageRecord cropImage = shop().image.addImageToDb(uploadPath.relativeFilePath, record.getImgName(),
					record.getImgOrigFname(), param.imgCatId);
			// 兼容前端输出数组形式
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			list.add(cropImage.intoMap());
			return JsonResult.success(list);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.fail("裁剪失败");
		}
	}

	/**
	 * 多图片选择框
	 * 
	 * @return
	 */
	@RequestMapping(value = "/admin/frame/image/dialog/select")
	public ModelAndView selectImageDialog() {
		String accountManagePath = "admin/account/manage";
		ModelMap model = new ModelMap();
		model.addAttribute("account", this.request.getHeader("Referer").indexOf(accountManagePath) != -1);
		return view("admin/select_img_dlg", model);
	}

	/**
	 * 图片列表对话框
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/admin/frame/image/dialog")
	public ModelAndView imageListDialog(@LineConvertHump ImageListQueryParam param) {
		Map<String, Object> version = shop().version.getPictureNumConfig();
		String message = this.shop().image.processPostRequest(param);
		PageResult page = this.shop().image.getPageList(param);

		ModelMap model = new ModelMap();
		model.addAttribute("title", "图片空间管理");
		model.addAttribute("data_list", page.dataList);
		model.addAttribute("page", page.page);
		model.addAttribute("nav_type", 0);
		model.addAttribute("img_cat_arr", Util.toJSON(this.shop().image.category.getImageCategoryForZTree(null)));
		model.addAttribute("upload_sort_list", this.shop().image.getUploadSortList());
		model.addAttribute("img_cat_list", this.shop().image.category.getCategoryTree((byte) -1).intoMaps());
		model.addAttribute("version", version);
		model.addAttribute("message", message);
		model.addAttribute("action", "/admin/frame/image/dialog");
		return view("admin/image_manager_dlg", model);
	}

	@RequestMapping(value = "/admin/image/keditor/upload")
	public ModelAndView kEditorUpload() {
		List<Part> uploadParts = null;
		try {
			uploadParts = Util.getFilePart(request, "imgFile");
		} catch (IOException | ServletException e1) {
			e1.printStackTrace();
			return this.KEditorResponse("上传失败", null);
		}
		if (uploadParts.size() == 0) {
			return this.KEditorResponse("未找到上传文件", null);
		}
		String message;
		try {
			message = this.uploadOneFile(uploadParts.get(0));
			if (message.startsWith(prefixImgId)) {
				Integer imageId = Util.getInteger(message.substring(prefixImgId.length(), message.length()));
				UploadedImageRecord record = shop().image.getImageById(imageId);
				return this.KEditorResponse(null,record.getImgUrl());
			} else {
				return this.KEditorResponse(message, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return this.KEditorResponse("上传失败", null);
		}

	}

	protected ModelAndView KEditorResponse(String message, String url) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (message != null) {
			result.put("error", 1);
			result.put("message", message);
		} else {
			result.put("error", 0);
			result.put("url", url);
		}
		return json(result);
	}

}
