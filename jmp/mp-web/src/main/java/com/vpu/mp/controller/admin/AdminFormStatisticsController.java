package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.market.form.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

/**
 * @author liufei
 * @date 2019/8/7
 */
@RestController
public class AdminFormStatisticsController extends AdminBaseController {
    /**
     * 分页查询表单信息
     *
     * @param param 筛选条件
     * @return 响应分页数据集
     */
    @PostMapping("/api/admin/formstatistics/selectforminfo")
    public JsonResult selectFormInfo(@RequestBody @Validated FormSearchParam param) {
        return success(shop().formService.selectFormInfo(param));
    }

    /**
     * 查看表单信息详情
     * 已发布的表单只可查看，不可编辑
     *
     * @param param 表单id
     * @return formpage表单详细信息
     */
    @PostMapping("/api/admin/formstatistics/getformdetailinfo")
    public JsonResult getFormDetailInfo(@RequestBody @Validated FormDetailParam param) {
        return success(shop().formService.getFormDetailInfo(param));
    }


    /**
     * 添加表单
     *
     * @param param 表单信息入参
     */
    @PostMapping("/api/admin/formstatistics/addforminfo")
    public JsonResult addFormInfo(@RequestBody @Validated FormAddParam param) {
        shop().formService.addFormInfo(param);
        return success();
    }

    /**
     * 更新表单
     *
     * @param param 表单信息入参
     */
    @PostMapping("/api/admin/formstatistics/updateforminfo")
    public JsonResult updateFormInfo(@RequestBody @Validated FormUpdateParam param) {
        shop().formService.updateFormInfo(param);
        return success();
    }

    /**
     * 表单反馈列表导出
     *
     * @param param 筛选条件
     */
    @PostMapping("/api/admin/formstatistics/export2Excel")
    public void export2Excel(@RequestBody FormFeedParam param, HttpServletResponse response) {
        try {
            Workbook workbook = shop().formService.exportFeedBack(param);
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            String fileName = "表单反馈" + System.currentTimeMillis() + ".xlsx";
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setLocale(Locale.ENGLISH);
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 表单复制
     */
    @PostMapping("/api/admin/formstatistics/copyForm")
    public JsonResult copyForm(@RequestBody @Validated FormDetailParam param) {
        return success(shop().formService.copyForm(param));
    }

    /**
     * 表单的发布/关闭/删除
     */
    @PostMapping("/api/admin/formstatistics/changeFormStatus")
    public JsonResult changeFormStatus(@RequestBody @Validated FormStatusParam param) {
        shop().formService.changeFormStatus(param);
        return success();
    }

    /**
     * 反馈列表
     *
     * @param param 默认必要条件pageId和shopId，可选条件时间和昵称
     * @return 分页反馈列表信息
     */
    @PostMapping("/api/admin/formstatistics/feedBackList")
    public JsonResult feedBackList(@RequestBody @Validated FormFeedParam param) {
        return success(shop().formService.feedBackList(param));
    }

    /**
     * 分享,获取小程序二维码
     */
    @PostMapping("/api/admin/formstatistics/shareForm")
    public JsonResult shareForm(@RequestBody @Validated FormDetailParam param) {
        return success(shop().formService.shareForm(param));
    }

    /**
     * 反馈信息详情
     *
     * @param param 表单id和用户id
     * @return 反馈信息详情列表
     */
    @PostMapping("/api/admin/formstatistics/feedBackDetail")
    public JsonResult feedBackDetail(@RequestBody @Validated FeedBackDetailParam param) {
        return success(shop().formService.feedBackDetail(param));
    }

    /**
     * 反馈统计
     *
     * @param param 表单id
     * @return 统计数据返回，只有性别，下拉，选项三项
     */
    @PostMapping("/api/admin/formstatistics/feedBackStatistics")
    public JsonResult feedBackStatistics(@RequestBody @Validated FormDetailParam param) {
        return success(shop().formService.feedBackStatistics(param));
    }

    /**
     * 添加反馈信息
     */
    @PostMapping("/api/admin/formstatistics/addFeedBackInfo")
    public JsonResult addFeedBackInfo(@RequestBody @Validated FeedBackInfoParam param) {
        shop().formService.addFeedBackInfo(param);
        return success();
    }
}