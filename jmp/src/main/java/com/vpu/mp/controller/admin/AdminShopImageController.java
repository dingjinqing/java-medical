package com.vpu.mp.controller.admin;

import com.vpu.mp.db.main.tables.records.ShopUploadedImageRecord;
import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.saas.shop.image.*;
import com.vpu.mp.service.pojo.shop.auth.AdminTokenAuthInfo;
import com.vpu.mp.service.pojo.shop.image.*;
import com.vpu.mp.service.pojo.shop.image.category.ImageCategoryRenameParam;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.Part;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
            if (param.needImgHeight != null && param.needImgHeight != bufferImage.getHeight()) {
                return this.fail(JsonResultCode.CODE_IMGAE_UPLOAD_EQ_HEIGHT, param.needImgHeight);
            }
        }
        UploadPath uploadPath = saas.shop.image.getImageWritableUploadPath(file.getContentType(),adminInfo.getSysId());
        file.write(uploadPath.fullPath);
        boolean ret = shop().image.uploadToUpYun(uploadPath.relativeFilePath, new File(uploadPath.fullPath));
        if (ret) {
            ShopUploadedImageRecord record = saas.shop.image.addImageToDb(uploadPath.relativeFilePath,
                    shop().image.baseFilename(file.getSubmittedFileName()),
                    file.getSubmittedFileName(),
                    param.imgCatId,
                    adminInfo.getSysId());
            shop().image.rmFile(uploadPath.fullPath);
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
        File file =new  File(uploadPath.fullPath);
        boolean ret = shop().image.uploadToUpYun(uploadPath.relativeFilePath, file);
        if (ret) {
            ShopUploadedImageRecord record = saas.shop.image.addImageToDb(uploadPath.relativeFilePath,
                    uploadPath.getFilname(),
                    file.getName(),
                    param.imgCatId,
                    adminInfo.getSysId());
            shop().image.rmFile(uploadPath.fullPath);
            return this.success(record.into(ShopUploadedImageVo.class));
        }
        return fail();
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

