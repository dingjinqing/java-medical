package com.vpu.mp.controller.admin;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.servlet.http.Part;
import javax.validation.Valid;

import com.vpu.mp.service.pojo.shop.image.UploadedImageVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.db.main.tables.records.ShopUploadedImageRecord;
import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.saas.shop.image.ShopBatchMoveImageParam;
import com.vpu.mp.service.pojo.saas.shop.image.ShopCropImageParam;
import com.vpu.mp.service.pojo.saas.shop.image.ShopImageCategoryIDParam;
import com.vpu.mp.service.pojo.saas.shop.image.ShopImageCategoryParam;
import com.vpu.mp.service.pojo.saas.shop.image.ShopImageListQueryParam;
import com.vpu.mp.service.pojo.saas.shop.image.ShopUploadImageCatNameVo;
import com.vpu.mp.service.pojo.saas.shop.image.ShopUploadImageParam;
import com.vpu.mp.service.pojo.saas.shop.image.ShopUploadedImageCategoryParam;
import com.vpu.mp.service.pojo.saas.shop.image.ShopUploadedImageVo;
import com.vpu.mp.service.pojo.shop.auth.AdminTokenAuthInfo;
import com.vpu.mp.service.pojo.shop.image.BatchDeleteImageParam;
import com.vpu.mp.service.pojo.shop.image.UploadPath;
import com.vpu.mp.service.pojo.shop.image.category.ImageCategoryRenameParam;

/**
 * @author 孔德成
 * @date 2019/7/16 16:17
 */
@RequestMapping("/api")
@RestController
public class AdminShopImageController extends  AdminBaseController{


    /**
     * 保存
     *
     * @param param
     * @return
     */
    @PostMapping("/admin/account/image/category/add")
    public JsonResult addImageCategory(@RequestBody @Valid ShopImageCategoryParam param) {
        AdminTokenAuthInfo adminInfo = adminAuth.user();
        if (adminInfo == null) {
            return fail(JsonResultCode.CODE_ACCOUNT_LOGIN_EXPIRED);
        }
        saas.shop.image.addCategory(param,adminInfo.getSysId());
        return success();
    }


    /**
     * 删除
     *
     * @param param
     * @return
     */
    @PostMapping("/admin/account/image/category/delete")
    public JsonResult deleteImageCategory(@RequestBody @Valid ShopImageCategoryIDParam param) {
        AdminTokenAuthInfo adminInfo = adminAuth.user();
        if (adminInfo == null) {
            return fail(JsonResultCode.CODE_ACCOUNT_LOGIN_EXPIRED);
        }
        saas.shop.image.removeCategory(param.getImgCatId(),adminInfo.getSysId());
        return success();
    }


    /**
     * 更新图片分组节点
     *
     * @return
     */
    @PostMapping("/admin/account/image/category/move")
    public JsonResult moveImageCategory(@RequestBody @Valid ShopUploadedImageCategoryParam param) {
        AdminTokenAuthInfo adminInfo = adminAuth.user();
        if (adminInfo == null) {
            return fail(JsonResultCode.CODE_ACCOUNT_LOGIN_EXPIRED);
        }
        saas.shop.image.moveCategory(param.getImgCatId(),param.getImgCatParentId(),adminInfo.getSysId());
        return success();
    }

    /**
     * 更新分组名称
     *
     * @param param
     * @return
     */
    @PostMapping("/admin/account/image/category/rename")
    public JsonResult renameImageCategory(@RequestBody @Valid ImageCategoryRenameParam param) {
        AdminTokenAuthInfo adminInfo = adminAuth.user();
        if (adminInfo == null) {
            return fail(JsonResultCode.CODE_ACCOUNT_LOGIN_EXPIRED);
        }
        saas.shop.image.renameImageCategory(param.getImgCatId(), param.getImgCatName(),adminInfo.getSysId());
        return success();
    }


    /**
     * 查询图片分组列表
     *
     * @return
     */
    @GetMapping("/admin/account/image/category/list")
    public JsonResult getImageCategoryList() {
        AdminTokenAuthInfo adminInfo = adminAuth.user();
        if (adminInfo == null) {
            return fail(JsonResultCode.CODE_ACCOUNT_LOGIN_EXPIRED);
        }
        return success(saas.shop.image.getImageCategoryForTree(adminInfo.getSysId()));
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
    @PostMapping(value = "/admin/account/image/uploadOneImgae")
    protected JsonResult uploadOneFile(ShopUploadImageParam param, Part file) throws IOException, Exception {
        AdminTokenAuthInfo adminInfo = adminAuth.user();
        if (adminInfo == null) {
            return fail(JsonResultCode.CODE_ACCOUNT_LOGIN_EXPIRED);
        }
        //校验
        Object[] jsonResultCode = saas.shop.image.validImageParam(param, file);
        if (jsonResultCode!=null){
            this.fail(Arrays.asList(jsonResultCode));
        }
        UploadPath uploadPath = saas.shop.image.getImageWritableUploadPath(file.getContentType(),file.getName(),adminInfo.getSysId());
        //上传又拍云
        boolean ret = saas.shop.image.uploadToUpYunBySteam(uploadPath.relativeFilePath, file.getInputStream());
        if (ret) {
            ShopUploadedImageRecord record = saas.shop.image.addImageToDb(param, file, uploadPath, adminInfo.sysId);
            return this.success(record.into(ShopUploadedImageVo.class));
        }
        return fail(JsonResultCode.CODE_IMGAE_UPLOAD_FAILED);
    }




    /**
     * 图片列表
     *
     * @param  param
     * @return
     */
    @PostMapping(value = "/admin/account/image/list")
    public JsonResult getImageList(@RequestBody ShopImageListQueryParam param) {
        AdminTokenAuthInfo adminInfo = adminAuth.user();
        if (adminInfo == null) {
            return fail(JsonResultCode.CODE_ACCOUNT_LOGIN_EXPIRED);
        }
        PageResult<ShopUploadImageCatNameVo> imageList = saas.shop.image.getPageList(param,adminInfo.getSysId());
        return this.success(imageList);
    }

    /**
     * 图片裁剪
     *
     * @param  param
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/admin/account/image/makeCrop")
    public JsonResult makeCrop(@RequestBody ShopCropImageParam param) throws Exception {
        AdminTokenAuthInfo adminInfo = adminAuth.user();
        if (adminInfo == null) {
            return fail(JsonResultCode.CODE_ACCOUNT_LOGIN_EXPIRED);
        }
        UploadPath  uploadPath =saas.shop.image.makeCrop(param,adminInfo.getSysId());
        if (uploadPath==null){
            return  fail();
        }
        ShopUploadedImageRecord record = saas.shop.image.addImageToDb(param, uploadPath, adminInfo.sysId);
        return success(record.into(ShopUploadedImageVo.class));
    }

    /**
     * 批量移动分组
     *
     * @param  param
     * @return
     */
    @PostMapping("/admin/account/image/batch/move")
    public JsonResult batchMoveImage(@RequestBody @Valid ShopBatchMoveImageParam param) {
        AdminTokenAuthInfo adminInfo = adminAuth.user();
        if (adminInfo == null) {
            return fail(JsonResultCode.CODE_ACCOUNT_LOGIN_EXPIRED);
        }
        saas.shop.image.setCatId(param.getImageIds().toArray(new Integer[0]), param.getImageCatId(),adminInfo.getSysId());
        return success();
    }

    /**
     * 批量删除图片
     *
     * @param  param
     * @return
     */
    @PostMapping("/admin/account/image/batch/delete")
    public JsonResult batchDeleteImage(@RequestBody BatchDeleteImageParam param) {
        AdminTokenAuthInfo adminInfo = adminAuth.user();
        if (adminInfo == null) {
            return fail(JsonResultCode.CODE_ACCOUNT_LOGIN_EXPIRED);
        }
        saas.shop.image.removeRows(param.getImageIds(),adminInfo.getSysId());
        return success();
    }






}

