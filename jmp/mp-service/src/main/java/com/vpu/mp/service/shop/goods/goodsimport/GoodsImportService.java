package com.vpu.mp.service.shop.goods.goodsimport;

import com.upyun.UpException;
import com.vpu.mp.db.shop.tables.records.GoodsImportDetailRecord;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.excel.ExcelFactory;
import com.vpu.mp.service.foundation.excel.ExcelReader;
import com.vpu.mp.service.foundation.excel.exception.handler.IllegalExcelBinder;
import com.vpu.mp.service.foundation.jedis.JedisKeyConstant;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.lock.annotation.RedisLock;
import com.vpu.mp.service.foundation.util.lock.annotation.RedisLockKeys;
import com.vpu.mp.service.pojo.shop.goods.goodsimport.GoodsExcelImportBase;
import com.vpu.mp.service.pojo.shop.goods.goodsimport.GoodsImportDataIIllegalEnum;
import com.vpu.mp.service.pojo.shop.goods.goodsimport.vpu.GoodsVpuExcelImportBo;
import com.vpu.mp.service.pojo.shop.goods.goodsimport.vpu.GoodsVpuExcelImportModel;
import com.vpu.mp.service.pojo.shop.goods.goodsimport.vpu.GoodsVpuExcelImportMqParam;
import com.vpu.mp.service.pojo.shop.goods.goodsimport.vpu.GoodsVpuExcelImportParam;
import com.vpu.mp.service.shop.goods.GoodsService;
import com.vpu.mp.service.shop.goods.GoodsSpecProductService;
import com.vpu.mp.service.shop.image.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 商品导入
 *
 * @author 李晓冰
 * @date 2020年03月18日
 */
@Service
@Slf4j
public class GoodsImportService extends ShopBaseService {

    @Autowired
    ImageService imageService;
    @Autowired
    GoodsService goodsService;
    @Autowired
    GoodsSpecProductService specProductService;
    @Autowired
    GoodsImportRecordService importRecordService;
    /**
     * 最大导入数量
     */
    final Integer MAX_IMPORT_NUM = 2000;

    /**
     * 微铺宝商品excel模板导入
     *
     * @param param 导入参数
     * @return 导入直接
     */
    public JsonResultCode goodsVpuExcelImport(GoodsVpuExcelImportParam param) {
        Workbook workbook = null;
        String filePath;
        try (InputStream in = param.getFile().getInputStream()) {
            workbook = ExcelFactory.createWorkbook(in, param.getExcelTypeEnum());
            filePath = createFilePath(getShopId(), param.getFile().getName());
            imageService.getUpYunClient().writeFile(filePath, in, true, null);
        } catch (IOException e) {
            log.debug("微铺宝excel商品导入创建workbook失败：" + e.getMessage());
            return JsonResultCode.GOODS_EXCEL_IMPORT_WORKBOOK_CREATE_FAIL;
        } catch (UpException e) {
            log.debug("微铺宝excel商品导入excel上传upYun失败：" + e.getMessage());
            return JsonResultCode.GOODS_EXCEL_UPLOAD_UPYUN_WRONG;
        }

        // 创建handler读取对应的excel数据
        GoodsExcelIllegalFormatterHandler handler = new GoodsExcelIllegalFormatterHandler();
        ExcelReader excelReader = new ExcelReader(param.getLang(), workbook, handler);
        List<GoodsVpuExcelImportModel> goodsVpuExcelImportModels = excelReader.readModelList(GoodsVpuExcelImportModel.class);

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
        } else if (goodsVpuExcelImportModels.size() > MAX_IMPORT_NUM) {
            return JsonResultCode.GOODS_EXCEL_IMPORT_NUM_OUT_OF_SIZE;
        } else {
            code = JsonResultCode.CODE_SUCCESS;
            Integer batchId = importRecordService.insertGoodsImportInfo(goodsVpuExcelImportModels.size(), filePath, param.getIsUpdate());
            /**excel model 对象转换为对应的业务对象*/
            List<GoodsVpuExcelImportBo> goodsList = goodsVpuExcelImportModels.stream().map(GoodsVpuExcelImportBo::new).collect(Collectors.toList());
            GoodsVpuExcelImportMqParam mqParam = new GoodsVpuExcelImportMqParam(goodsList, param.getLang(), batchId, getShopId(), null);
            // 调用消息队列
//            saas.taskJobMainService.dispatchImmediately(mqParam, UserImportMqParam.class.getName(), getShopId(),
//                TaskJobsConstant.TaskJobEnum.GOODS_VPU_EXCEL_IMPORT.getExecutionType());
            goodsVpuExcelImportMqCallback(mqParam);
        }
        return code;
    }

    /**
     * 微铺宝商品excel模板导入mq 消费者回调方法
     *
     * @param param
     */
    public void goodsVpuExcelImportMqCallback(GoodsVpuExcelImportMqParam param) {
        List<GoodsVpuExcelImportBo> readyToImportGoodsList = Optional.ofNullable(param.getGoodsVpuExcelImportBos()).orElse(new ArrayList<>());
        List<GoodsImportDetailRecord> readyToRecord = filterIllegalGoodsListForNull(readyToImportGoodsList, param.getBatchId());

    }

    /**
     * 初步根据非空字段，将不合法的数据剔除，会修改readyToImportGoodsList集合
     *
     * @param readyToImportGoodsList 待插入数据，方法会将不合法数据项剔除
     * @param batchId                批量处理id
     * @return 不合法的数据集合
     */
    private List<GoodsImportDetailRecord> filterIllegalGoodsListForNull(List<GoodsVpuExcelImportBo> readyToImportGoodsList, Integer batchId) {
        List<GoodsImportDetailRecord> illegalGoods = new ArrayList<>(10);

        readyToImportGoodsList.removeIf(goodsBo -> {
            if (StringUtils.isBlank(goodsBo.getGoodsName())) {
                illegalGoods.add(importRecordService.convertVpuExcelImportBoToImportDetail(goodsBo, GoodsImportDataIIllegalEnum.GOODS_NAME_NULL, batchId));
                return true;
            }
            if (StringUtils.isBlank(goodsBo.getGoodsSn())) {
                illegalGoods.add(importRecordService.convertVpuExcelImportBoToImportDetail(goodsBo, GoodsImportDataIIllegalEnum.GOODS_SN_NULL, batchId));
                return true;
            }
            if (StringUtils.isBlank(goodsBo.getPrdSn())) {
                illegalGoods.add(importRecordService.convertVpuExcelImportBoToImportDetail(goodsBo, GoodsImportDataIIllegalEnum.GOODS_PRD_SN_NULL, batchId));
                return true;
            }
            if (StringUtils.isBlank(goodsBo.getGoodsImgsStr())) {
                illegalGoods.add(importRecordService.convertVpuExcelImportBoToImportDetail(goodsBo, GoodsImportDataIIllegalEnum.GOODS_PRD_SN_NULL, batchId));
                return true;
            }
            return false;
        });

        return illegalGoods;
    }


    /**
     * 真正数据插入处理
     */
    private void goodsImportIterateInsert(List<GoodsVpuExcelImportBo> readyToImportGoodsList, List<GoodsVpuExcelImportBo> illegalGoodsList, boolean isUpdate) {
        Map<String, List<GoodsVpuExcelImportBo>> goodsMap = readyToImportGoodsList.stream().collect(Collectors.groupingBy(GoodsVpuExcelImportBo::getGoodsSn));

        for (Map.Entry<String, List<GoodsVpuExcelImportBo>> entry : goodsMap.entrySet()) {
            List<GoodsVpuExcelImportBo> value = entry.getValue();
            goodsImportInsert(getShopId(), value, illegalGoodsList, isUpdate);
        }
    }

    @RedisLock(prefix = JedisKeyConstant.GOODS_LOCK)
    @SuppressWarnings("unchecked")
    private void goodsImportInsert(@RedisLockKeys Integer shopId, List<GoodsVpuExcelImportBo> goodsSkus, List<GoodsVpuExcelImportBo> illegalGoodsList, boolean isUpdate) {
        // 检查prdSn码是否会有内部自重复的
        List<? extends GoodsExcelImportBase> illegalPrdSnRepeated = getSkuPrdSnRepeated(goodsSkus);
        List<GoodsVpuExcelImportBo> v = (List<GoodsVpuExcelImportBo>) illegalPrdSnRepeated;
        illegalGoodsList.addAll(v);
        // 无有效数据直接返回
        if (goodsSkus.size() == 0) {
            return;
        }

        if (!isUpdate) {
            // 检查prdSn码数据库是否存在 只有在插入操做的时候需要检查
            List<? extends GoodsExcelImportBase> illegalPrdSnDbExist = getSkuPrdSnDbExist(goodsSkus);
            List<GoodsVpuExcelImportBo> e = (List<GoodsVpuExcelImportBo>) illegalPrdSnDbExist;
            illegalGoodsList.addAll(e);
            // 无有效数据直接返回
            if (goodsSkus.size() == 0) {
                return;
            }
        } else {

        }
    }

    /**
     * 剔除掉带插入的同一商品内部规格编码重复的数据，此方法会修改goodsSkus对象本身
     *
     * @param goodsSkus 同一个商品的sku的集合
     * @return
     */
    private List<? extends GoodsExcelImportBase> getSkuPrdSnRepeated(List<? extends GoodsExcelImportBase> goodsSkus) {
        Map<String, List<GoodsExcelImportBase>> prdSnMap = goodsSkus.stream().collect(Collectors.groupingBy(GoodsExcelImportBase::getPrdSn));
        List<GoodsExcelImportBase> returnList = new ArrayList<>(2);
        goodsSkus.removeIf(bo -> {
            if (prdSnMap.get(bo.getPrdSn()).size() > 1) {
                returnList.addAll(prdSnMap.get(bo.getPrdSn()));
                return true;
            } else {
                return false;
            }
        });
        return returnList;
    }

    /**
     * 判断规格编码是否在数据库中重复
     *
     * @param goodsSkus
     * @return
     */
    private List<? extends GoodsExcelImportBase> getSkuPrdSnDbExist(List<? extends GoodsExcelImportBase> goodsSkus) {
        List<GoodsExcelImportBase> returnList = new ArrayList<>();
        List<String> prdSnList = goodsSkus.stream().map(GoodsExcelImportBase::getPrdSn).collect(Collectors.toList());

        List<String> existPrdsn = specProductService.findSkuPrdSnExist(prdSnList);
        if (existPrdsn.size() != 0) {
            goodsSkus.removeIf(sku -> {
                if (existPrdsn.contains(sku.getPrdSn())) {
                    returnList.add(sku);
                    return true;
                } else {
                    return false;
                }
            });
        }
        return returnList;
    }

    /**
     * 生成文件存储在yupYun上的文件位置
     *
     * @param shopId   店铺id
     * @param fileName 文件名
     * @return 文件相对位置
     */
    private String createFilePath(Integer shopId, String fileName) {
        return new StringBuilder().append("upload/").append("excel/").append(shopId).append("/")
            .append(DateUtil.dateFormat(DateUtil.DATE_FORMAT_FULL_NO_UNDERLINE))
            .append("_").append(fileName).toString();
    }

}
