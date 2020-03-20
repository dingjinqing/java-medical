package com.vpu.mp.service.shop.goods.goodsimport;

import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.excel.ExcelFactory;
import com.vpu.mp.service.foundation.excel.ExcelReader;
import com.vpu.mp.service.foundation.excel.exception.handler.IllegalExcelBinder;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.goods.goodsimport.vpu.GoodsVpuExcelImportBo;
import com.vpu.mp.service.pojo.shop.goods.goodsimport.vpu.GoodsVpuExcelImportMqParam;
import com.vpu.mp.service.pojo.shop.goods.goodsimport.vpu.GoodsVpuExcelImportParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 商品导入
 *
 * @author 李晓冰
 * @date 2020年03月18日
 */
@Service
@Slf4j
public class GoodsImportService extends ShopBaseService {

    /**
     * 微铺宝商品excel模板导入
     * @param param 导入参数
     * @return 导入直接
     */
    public JsonResultCode goodsVpuExcelImport(GoodsVpuExcelImportParam param) {
        Workbook workbook = null;
        try (InputStream in = param.getFile().getInputStream()) {
            workbook = ExcelFactory.createWorkbook(in, param.getExcelTypeEnum());
        } catch (IOException e) {
            log.debug("微铺宝excel商品导入创建workbook失败：" + e.getMessage());
            return JsonResultCode.GOODS_EXCEL_IMPORT_WORKBOOK_CREATE_FAIL;
        }

        // 创建handler读取对应的excel数据
        GoodsExcelIllegalFormatterHandler handler = new GoodsExcelIllegalFormatterHandler();
        ExcelReader excelReader = new ExcelReader(param.getLang(), workbook, handler);
        List<GoodsVpuExcelImportBo> goodsVpuExcelImportBos = excelReader.readModelList(GoodsVpuExcelImportBo.class);

        IllegalExcelBinder wrongBinderInfo = handler.getWrongBinderInfo();
        JsonResultCode code;
        if (wrongBinderInfo != null) {
            switch (wrongBinderInfo.getIllegalExcelEnum()) {
                case ILLEGEL_SHEET_POSITION: // sheet位置错误
                    code = JsonResultCode.GOODS_EXCEL_IMPORT_SHEET_HEADER_WRONG_INDEX;
                    break;
                case SHEET_HEAD_NULL:// sheet header位置错误
                    code = JsonResultCode.GOODS_EXCEL_IMPORT_SHEET_HEADER_WRONG_INDEX;
                    break;
                case ILLEGAL_SHEET_HEAD:// sheet 数据列和model定义的列位置不一致或列名错误
                    code = JsonResultCode.GOODS_EXCEL_IMPORT_SHEET_COLUMN_NOT_MAP_POJO;
                    break;
                default:
                    code = JsonResultCode.CODE_FAIL;
            }
        } else {
            code = JsonResultCode.CODE_SUCCESS;
            GoodsVpuExcelImportMqParam mqParam = new GoodsVpuExcelImportMqParam(goodsVpuExcelImportBos,param.getLang(), getShopId(),null);
            // 调用消息队列
//            saas.taskJobMainService.dispatchImmediately(mqParam, UserImportMqParam.class.getName(), getShopId(),
//                TaskJobsConstant.TaskJobEnum.GOODS_VPU_EXCEL_IMPORT.getExecutionType());
            goodsVpuExcelImportMqCallback(mqParam);
        }
        return code;
    }

    /**
     * 微铺宝商品excel模板导入mq 消费者回调方法
     * @param param
     */
    public void goodsVpuExcelImportMqCallback(GoodsVpuExcelImportMqParam param) {
        List<GoodsVpuExcelImportBo> readyToImportGoodsList = Optional.ofNullable(param.getGoodsVpuExcelImportBos()).orElse(new ArrayList<>());
    }

    private List<GoodsVpuExcelImportBo> filterIllegalGoodsList(List<GoodsVpuExcelImportBo> readyToImportGoodsList){
        List<GoodsVpuExcelImportBo> illegalGoods = new ArrayList<>(10);
        return null;
    }
}
