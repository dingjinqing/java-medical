package com.vpu.mp.controller.admin;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.vpu.mp.db.shop.tables.records.UploadedImageRecord;
import com.vpu.mp.service.auth.AdminAuth.ShopLoginParam;
import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.foundation.Util;
import com.vpu.mp.service.shop.image.ImageService.CropImageParam;
import com.vpu.mp.service.shop.image.ImageService.ImageListQueryParam;
import com.vpu.mp.service.shop.image.ImageService.UploadPath;
import com.vpu.mp.support.LineConvertHump;

import net.coobird.thumbnailator.Thumbnails;

/**
 * 
 * @author 新国
 *
 */
@Controller
public class AdminImageController extends AdminBaseController {

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/admin/manage/image/list")
	public ModelAndView getList(@LineConvertHump ImageListQueryParam param) {

		float size = this.shop().image.getAllSize() / 1024 / 1024;
		Map<String, Object> versionNumberMap = saas.shop.version.versionNumShow("picture_num", this.shopId());
		Map<String, Object> self = (Map<String, Object>) versionNumberMap.get("self");
		self.put("use", String.format("%.2f", size));

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
		model.addAttribute("input_map", inputMap());
		model.addAttribute("version", versionNumberMap);
		model.addAttribute("message", message);
		return view("admin/image_manager_list", model);
	}

	final protected String prefixImgId = "IMG_ID:";

	@PostMapping(value = "/admin/ajax/image/upload")
	@ResponseBody
	public JsonResult upload() {
		String name = this.input("elname");
		String names = name + "[]";
		Collection<Part> parts;
		try {
			parts = this.request.getParts();
		} catch (IOException | ServletException e1) {
			e1.printStackTrace();
			return JsonResult.fail("上传失败");
		}
		List<Integer> imageIds = new ArrayList<Integer>();
		String lastError = "";
		for (Part part : parts) {
			if (part.getName().equals(name) || part.getName().equals(names)) {
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
		}
		if (imageIds.size() > 0) {
			return JsonResult.success(imageIds);
		} else {
			return JsonResult.fail(lastError);
		}
	}

	/**
	 * 上传一个图片
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
	 * @param param
	 * @return
	 */
	@PostMapping(value = "/admin/ajax/image/crop")
	@ResponseBody
	public JsonResult cropImage(@LineConvertHump CropImageParam param) {
		try {
			UploadPath uploadPath = shop().image.makeCrop(param);
			UploadedImageRecord record = shop().image.getImageById(param.remoteImgId);
			UploadedImageRecord cropImage = shop().image.addImageToDb(uploadPath.relativeFilePath, record.getImgName(), record.getImgOrigFname(), param.imgCatId);
			// 兼容前端输出数组形式
			List<Map<String,Object>> list= new ArrayList<Map<String,Object>>();
			list.add(cropImage.intoMap());
			return JsonResult.success(list);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.fail("裁剪失败");
		}
	}
}
