package com.vpu.mp.controller.admin;

import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.pojo.shop.medicalHistory.MedicalHistoryParam;
import com.vpu.mp.common.pojo.shop.medicalHistory.MedicalHistoryVo;
import com.vpu.mp.service.shop.ShopApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 赵晓东
 * @Date 2020-7-7 9:51
 * @description 病历管理控制层接口
 */
@RestController
@RequestMapping(value = "/api/admin/medicine/medicalHistory")
public class MedicalHistoryController extends AdminBaseController {

    @Override
    protected ShopApplication shop() {
        return saas.getShopApp(224462);
    }

    /**
     * 病历分页
     * @param param 病历入参
     * @return JsonResult
     */
    @PostMapping(value = "/list")
    public JsonResult listPageResult(MedicalHistoryParam param){
        PageResult<MedicalHistoryVo> medicalHistoryVoPageResult =
            shop().medicalHistoryService.listPageResult(param);
        return success(medicalHistoryVoPageResult);
    }

//    public JsonResult deleteMedicalHistory(H){
//        return success();
//    }
}
