package com.vpu.mp.controller.admin;


import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.pojo.shop.image.category.UploadedImageCategoryParam;
import org.springframework.web.bind.annotation.*;

/**
 * 图片分组列表
 *
 * @author 孔德成
 * @date 2019/7/8 13:59
 */
@RequestMapping("/api")
@RestController
public class AdminImageCategoryController extends AdminBaseController {


    /**
     * 保存
     *
     * @param param
     * @return
     */
    @PostMapping("/admin/image/category/add")
    public JsonResult addImageCategory(@RequestBody UploadedImageCategoryParam param) {
        shop().imageCatgory.addCategory(param);
        return success();
    }


    /**
     * 删除
     *
     * @param param
     * @return
     */
    @PostMapping("/admin/image/category/delete")
    public JsonResult deleteImageCategory(UploadedImageCategoryParam param) {
        shop().imageCatgory.removeCategory(param.getImgCatId());
        return success();
    }


    /**
     * 更新图片分组节点
     *
     * @return
     */
    @PostMapping("/admin/image/category/move")
    public JsonResult moveImageCategory(@RequestBody  UploadedImageCategoryParam param) {
        shop().imageCatgory.moveCategory(param.getImgCatId(),param.getImgCatParentId());
        return success();
    }

    /**
     * 更新分组名称
     *
     * @param param
     * @return
     */
    @PostMapping("/admin/image/category/rename")
    public JsonResult renameImageCategory(@RequestBody UploadedImageCategoryParam param) {
        shop().imageCatgory.setCategoryName(param.getImgCatId(), param.getImgCatName());
        return success();
    }


    /**
     * 查询图片分组列表
     *
     * @return
     */
    @GetMapping("/admin/image/category/list")
    public JsonResult getImageCategoryList() {
        return success(shop().imageCatgory.getImageCategoryForTree(0));
    }


}
