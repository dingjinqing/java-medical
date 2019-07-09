package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.JsonResultCode;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.pojo.shop.applets.page.PageClassificationParam;
import com.vpu.mp.service.pojo.shop.decoration.PageCategoryListQueryParam;
import com.vpu.mp.service.pojo.shop.decoration.PageClassificationPojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;


/**
 * @description 小程序管理-页面分类
 * @author lixinguo,liufei
 * @date 2019-07-03
 */
@RestController
public class AdminPageClassificationController extends AdminBaseController {
    private Logger logger = LoggerFactory.getLogger(AdminPageClassificationController.class);

    /**
     * 添加页面分类
     * @param pageIn
     * @return
     */
    @PostMapping("/api/admin/applets/pageclassification/add")
    public JsonResult insert(@RequestBody PageClassificationParam pageIn){
        if(shop().pageClassification.checkExist(-1,pageIn.getPageName())){
            return fail(JsonResultCode.CODE_PAGE_CLASSIFICATION_EXIST);
        }
        return shop().pageClassification.addRow(pageIn.getPageName()) > 0 ? success() : fail(JsonResultCode.CODE_PAGE_CLASSIFICATION_INSERT_FAILED);
    }

    /**
     * 修改页面分类名称，id码值不变
     * @param pageIn
     * @return
     */
    @PostMapping("/api/admin/applets/pageclassification/updateCategoryName")
    public JsonResult updateCategoryName(@RequestBody PageClassificationParam pageIn){
        if(!shop().pageClassification.checkExist(pageIn.getPageId(),"")){
            return fail(JsonResultCode.CODE_PAGE_CLASSIFICATION_NOT_EXIST);
        }
        return shop().pageClassification.setName(pageIn.getPageId(),pageIn.getPageName()) > 0 ? success() : fail(JsonResultCode.CODE_PAGE_CLASSIFICATION_UPDATE_FAILED);
    }

    /**
     * 根据ID删除分类，同时将该分类下的页面重新归属于未分类类别，码值为0
     * @param pageIn
     * @return
     */
    @PostMapping("/api/admin/applets/pageclassification/deleteCategoryById")
    public JsonResult deleteCategoryById(@RequestBody PageClassificationParam pageIn){
        if(!shop().pageClassification.checkExist(pageIn.getPageId(),"")){
            return fail(JsonResultCode.CODE_PAGE_CLASSIFICATION_NOT_EXIST);
        }
        return shop().pageClassification.rmAndResetCategory(pageIn.getPageId()) ? success() : fail(JsonResultCode.CODE_PAGE_CLASSIFICATION_DELETE_FAILED);
    }

    /**
     * 页面分类分页查询，支持模糊查询
     * @param pageListParam
     * @return
     */
    @PostMapping("/api/admin/applets/pageclassification/getListByPage")
    public JsonResult getListByPage(@RequestBody  @Valid PageCategoryListQueryParam pageListParam, BindingResult result){
        PageResult<PageClassificationPojo> pageResult = shop().pageClassification.getPageList(pageListParam);
        setSubPageCount(pageResult);
        return success(pageResult);
    }

    /**
     * 设置各个类目下包含的页面数
     * @param pageResult
     */
    public void setSubPageCount(PageResult<PageClassificationPojo> pageResult){
        for (PageClassificationPojo pojo : pageResult.dataList){
            try {
                PropertyDescriptor descriptorId = new PropertyDescriptor("id",pojo.getClass());
                int pageId = (int)descriptorId.getReadMethod().invoke(pojo,null);
                int subPageCount = shop().pageClassification.getPageCountByCategory(pageId);
                PropertyDescriptor desSubPageCount = new PropertyDescriptor("subPageCount",pojo.getClass());
                desSubPageCount.getWriteMethod().invoke(pojo,subPageCount);
            }catch(IntrospectionException e ){
                e.printStackTrace();
            }catch(IllegalAccessException e){
                e.printStackTrace();
            }catch (InvocationTargetException e){
                e.printStackTrace();
            }
        }
    }
}
