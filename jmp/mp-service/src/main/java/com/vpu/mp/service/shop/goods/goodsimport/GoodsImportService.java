package com.vpu.mp.service.shop.goods.goodsimport;

import com.vpu.mp.db.shop.tables.records.GoodsImportDetailRecord;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.excel.ExcelFactory;
import com.vpu.mp.service.foundation.excel.ExcelReader;
import com.vpu.mp.service.foundation.excel.exception.handler.IllegalExcelBinder;
import com.vpu.mp.service.foundation.jedis.JedisKeyConstant;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.RegexUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.foundation.util.lock.annotation.RedisLock;
import com.vpu.mp.service.foundation.util.lock.annotation.RedisLockKeys;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.shop.goods.goods.Goods;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsDataIIllegalEnum;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsDataIllegalEnumWrap;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsSharePostConfig;
import com.vpu.mp.service.pojo.shop.goods.goodsimport.GoodsExcelImportBase;
import com.vpu.mp.service.pojo.shop.goods.goodsimport.vpu.GoodsVpuExcelImportBo;
import com.vpu.mp.service.pojo.shop.goods.goodsimport.vpu.GoodsVpuExcelImportModel;
import com.vpu.mp.service.pojo.shop.goods.goodsimport.vpu.GoodsVpuExcelImportMqParam;
import com.vpu.mp.service.pojo.shop.goods.goodsimport.vpu.GoodsVpuExcelImportParam;
import com.vpu.mp.service.pojo.shop.goods.spec.GoodsSpec;
import com.vpu.mp.service.pojo.shop.goods.spec.GoodsSpecProduct;
import com.vpu.mp.service.pojo.shop.goods.spec.GoodsSpecVal;
import com.vpu.mp.service.pojo.shop.image.DownloadImageBo;
import com.vpu.mp.service.shop.goods.GoodsService;
import com.vpu.mp.service.shop.goods.GoodsSortService;
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
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    GoodsImportRecordService importRecordService;
    @Autowired
    GoodsService goodsService;
    @Autowired
    GoodsSortService goodsSortService;
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
        try (InputStream in1 = param.getFile().getInputStream(); InputStream in2 = param.getFile().getInputStream()) {
            workbook = ExcelFactory.createWorkbook(in1, param.getExcelTypeEnum());
            filePath = createFilePath(getShopId(), param.getFile().getName());
            imageService.getUpYunClient().writeFile(filePath, in2, true, null);
        } catch (IOException e) {
            log.debug("微铺宝excel商品导入创建workbook失败：" + e.getMessage());
            return JsonResultCode.GOODS_EXCEL_IMPORT_WORKBOOK_CREATE_FAIL;
        } catch (Exception e) {
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
            GoodsVpuExcelImportMqParam mqParam = new GoodsVpuExcelImportMqParam(goodsList, param.getLang(), param.getIsUpdate(), batchId, getShopId(), null);
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
        List<GoodsImportDetailRecord> illegalGoodsList = filterIllegalGoodsListForNull(readyToImportGoodsList, param.getBatchId());
        goodsImportIterateOperate(readyToImportGoodsList, illegalGoodsList, param.isUpdate(), param.getBatchId());
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
                illegalGoods.add(importRecordService.convertVpuExcelImportBoToImportDetail(goodsBo, GoodsDataIIllegalEnum.GOODS_NAME_NULL, batchId));
                return true;
            }
            if (StringUtils.isBlank(goodsBo.getGoodsSn())) {
                illegalGoods.add(importRecordService.convertVpuExcelImportBoToImportDetail(goodsBo, GoodsDataIIllegalEnum.GOODS_SN_NULL, batchId));
                return true;
            }
            if (StringUtils.isBlank(goodsBo.getPrdSn())) {
                illegalGoods.add(importRecordService.convertVpuExcelImportBoToImportDetail(goodsBo, GoodsDataIIllegalEnum.GOODS_PRD_SN_NULL, batchId));
                return true;
            }
            if (StringUtils.isBlank(goodsBo.getGoodsImgsStr())) {
                illegalGoods.add(importRecordService.convertVpuExcelImportBoToImportDetail(goodsBo, GoodsDataIIllegalEnum.GOODS_IMG_IS_NULL, batchId));
                return true;
            }
            if (goodsBo.getShopPrice() == null) {
                illegalGoods.add(importRecordService.convertVpuExcelImportBoToImportDetail(goodsBo, GoodsDataIIllegalEnum.GOODS_SHOP_PRICE_IS_NULL, batchId));
                return true;
            }
            return false;
        });

        return illegalGoods;
    }


    /**
     * 商品待导入数据进行迭代处理
     *
     * @param readyToImportGoodsList 准备被处理的目标数据集合
     * @param illegalGoodsList       不合法的数据集合
     * @param isUpdate               是否是更细操作
     * @param batchId                本次批量操作id
     */
    private void goodsImportIterateOperate(List<GoodsVpuExcelImportBo> readyToImportGoodsList, List<GoodsImportDetailRecord> illegalGoodsList, Boolean isUpdate, Integer batchId) {
        Map<String, List<GoodsVpuExcelImportBo>> goodsMap = readyToImportGoodsList.stream().collect(Collectors.groupingBy(GoodsVpuExcelImportBo::getGoodsSn));
        List<GoodsImportDetailRecord> successGoodsList = new ArrayList<>(readyToImportGoodsList.size() / 2);
        List<Integer> goodsIds = new ArrayList<>(successGoodsList.size());

        for (Map.Entry<String, List<GoodsVpuExcelImportBo>> entry : goodsMap.entrySet()) {
            List<GoodsVpuExcelImportBo> value = entry.getValue();
            Integer goodsId = goodsImportOperate(getShopId(), value, successGoodsList, illegalGoodsList, isUpdate, batchId);
            goodsIds.add(goodsId);
        }
        int successNum = readyToImportGoodsList.size() - illegalGoodsList.size();
        importRecordService.updateGoodsImportSuccessNum(successNum, batchId);
        successGoodsList.addAll(illegalGoodsList);
        importRecordService.insertGoodsImportDetailBatch(successGoodsList);
    }

    /**
     * 导入每一个数据时琐lock:goods:shopId，避免admin端商品新增和修改
     * 单条商品数据处理，对应多条sku
     *
     * @param shopId
     * @param goodsSkus
     * @param illegalGoodsList
     * @param isUpdate
     * @return 返回受影响商品id
     */
    @RedisLock(prefix = JedisKeyConstant.GOODS_LOCK)
    @SuppressWarnings("unchecked")
    private Integer goodsImportOperate(@RedisLockKeys Integer shopId, List<GoodsVpuExcelImportBo> goodsSkus, List<GoodsImportDetailRecord> successGoodsList, List<GoodsImportDetailRecord> illegalGoodsList, boolean isUpdate, Integer batchId) {
        // 检查prdSn码是否会有内部自重复的
        List<? extends GoodsExcelImportBase> illegalPrdSnRepeated = getSkuPrdSnRepeated(goodsSkus);
        List<GoodsImportDetailRecord> records = importRecordService.convertVpuExcelImportBosToImportDetails((List<GoodsVpuExcelImportBo>) illegalPrdSnRepeated, GoodsDataIIllegalEnum.GOODS_PRD_SN_INNER_REPEATED, batchId);
        illegalGoodsList.addAll(records);
        // 无有效数据直接返回
        if (goodsSkus.size() == 0) {
            return null;
        }

        if (!isUpdate) {
            // 检查prdSn码数据库是否存在 只有在插入操做的时候需要这样检测，速度会稍微快一些
            List<? extends GoodsExcelImportBase> illegalPrdSnDbExist = getSkuPrdSnDbExist(goodsSkus);
            records = importRecordService.convertVpuExcelImportBosToImportDetails((List<GoodsVpuExcelImportBo>) illegalPrdSnDbExist, GoodsDataIIllegalEnum.GOODS_PRD_SN_EXIST, batchId);
            illegalGoodsList.addAll(records);
            // 如果不存在可用sku直接退出
            if (goodsSkus.size() == 0) {
                return null;
            }
            // 执行真正的插入操作
            GoodsDataIllegalEnumWrap codeWrap = goodsImportInsert(goodsSkus);
            if (!GoodsDataIIllegalEnum.GOODS_OK.equals(codeWrap.getIllegalEnum())) {
                records = importRecordService.convertVpuExcelImportBosToImportDetails(goodsSkus, codeWrap.getIllegalEnum(), batchId);
                illegalGoodsList.addAll(records);
            } else {
                records = importRecordService.convertVpuExcelImportBosToImportDetails(goodsSkus, codeWrap.getIllegalEnum(), batchId, true);
                successGoodsList.addAll(records);
            }
            return codeWrap.getGoodsId();
        } else {

        }
        return null;
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

        List<String> existPrdsn = goodsService.goodsSpecProductService.findSkuPrdSnExist(prdSnList);
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
     * 商品插入操作
     *
     * @return
     */
    private GoodsDataIllegalEnumWrap goodsImportInsert(List<GoodsVpuExcelImportBo> importBos) {
        GoodsDataIllegalEnumWrap resultCode = new GoodsDataIllegalEnumWrap();

        Goods goods = convertGoodsExcelImportBosToGoodsWithSku(importBos);
        // 检查规格名称是否存在重复
        boolean isOk = goodsService.goodsSpecProductService.isSpecNameOrValueRepeat(goods.getGoodsSpecs());
        if (!isOk) {
            resultCode.setIllegalEnum(GoodsDataIIllegalEnum.GOODS_SPEC_K_V_REPEATED);
            return resultCode;
        }
        // 校验输入的规格组是否正确
        isOk = goodsService.goodsSpecProductService.isGoodsSpecProductDescRight(goods.getGoodsSpecProducts(), goods.getGoodsSpecs());
        if (!isOk) {
            resultCode.setIllegalEnum(GoodsDataIIllegalEnum.GOODS_PRD_DESC_WRONG);
            return resultCode;
        }
        // 处理图片
        List<DownloadImageBo> downloadImageBos = filterGoodsImages(importBos);
        if (downloadImageBos.size() == 0) {
            resultCode.setIllegalEnum(GoodsDataIIllegalEnum.GOODS_IMG_IS_NULL);
            return resultCode;
        }
        GoodsVpuExcelImportBo bo = importBos.get(0);
        List<DownloadImageBo> descDownloadImageBos = new ArrayList<>(6);
        if (StringUtils.isNotBlank(bo.getGoodsDesc())) {
            String desc = filterGoodsDesc(bo.getGoodsDesc(), descDownloadImageBos);
            goods.setGoodsDesc(desc);
        }

        transaction(() -> {
            try {
                // 处理商家分类
                int sortId = goodsSortService.fixGoodsImportGoodsSort(bo.getFirstSortName(), bo.getSecondSortName());
                goods.setSortId(sortId);

                // 这个地方是为了避免报异常
                goods.setCatId(0);

                // 处理用户指定的商品图片
                List<String> imgs = imageService.addImageToDbBatch(downloadImageBos);
                goods.setGoodsImg(imgs.remove(0));
                goods.setGoodsImgs(imgs);
                // 处理商品描述中包含的图片
                imageService.addImageToDbBatch(descDownloadImageBos);

                GoodsDataIllegalEnumWrap insertResult = goodsService.insert(goods);
                resultCode.setIllegalEnum(insertResult.getIllegalEnum());
                resultCode.setGoodsId(insertResult.getGoodsId());
            } catch (Exception e) {
                resultCode.setIllegalEnum(GoodsDataIIllegalEnum.GOODS_FAIL);
            }
        });

        return resultCode;
    }

    /**
     * 将GoodsVpuExcelImportBo转换为Goods 准备进行插入或更新操作
     *
     * @param importBos
     * @return
     */
    private Goods convertGoodsExcelImportBosToGoodsWithSku(List<GoodsVpuExcelImportBo> importBos) {
        GoodsVpuExcelImportBo goodsInfo = importBos.get(0);
        // 提取商品信息
        Goods goods = convertGoodsExcelImportBoToGoods(goodsInfo);
        // 提取sku信息
        List<GoodsSpecProduct> goodsSpecProducts = convertGoodsExcelImportBosToSku(importBos);
        goods.setGoodsSpecProducts(goodsSpecProducts);
        // 提取规格组规格值
        List<GoodsSpec> goodsSpecs = convertGoodsExcelImportBosToGoodsSpecs(importBos);
        goods.setGoodsSpecs(goodsSpecs);

        return goods;
    }

    /**
     * 转换为对应的sku
     *
     * @param importBos
     * @return
     */
    private List<GoodsSpecProduct> convertGoodsExcelImportBosToSku(List<GoodsVpuExcelImportBo> importBos) {
        List<GoodsSpecProduct> skuList = new ArrayList<>(importBos.size());
        for (GoodsVpuExcelImportBo importBo : importBos) {
            GoodsSpecProduct product = new GoodsSpecProduct();
            product.setPrdPrice(importBo.getShopPrice());
            product.setPrdMarketPrice(importBo.getMarketPrice());
            product.setPrdNumber(importBo.getStock());
            product.setPrdSn(importBo.getPrdSn());
            if (importBo.getPrdDesc() != null) {
                importBo.setPrdDesc(importBo.getPrdDesc().replaceAll("：", GoodsSpecProductService.PRD_VAL_DELIMITER).replaceAll("；", GoodsSpecProductService.PRD_DESC_DELIMITER));
            }
            product.setPrdDesc(importBo.getPrdDesc());
            skuList.add(product);
        }
        return skuList;
    }

    /**
     * 转换对应的GoodsSpec
     *
     * @param importBos
     * @return
     */
    private List<GoodsSpec> convertGoodsExcelImportBosToGoodsSpecs(List<GoodsVpuExcelImportBo> importBos) {
        GoodsVpuExcelImportBo base = importBos.get(0);
        // 使用的是默认规格
        if (StringUtils.isBlank(base.getPrdDesc())) {
            return null;
        }
        // 解析对应的规格组K
        String[] specKVs = base.getPrdDesc().split(GoodsSpecProductService.PRD_DESC_DELIMITER);
        List<GoodsSpec> goodsSpecs = new ArrayList<>(6);
        for (String specKV : specKVs) {
            String[] kvs = specKV.split(GoodsSpecProductService.PRD_VAL_DELIMITER);
            GoodsSpec spec = new GoodsSpec(kvs[0], new ArrayList<>());
            goodsSpecs.add(spec);
        }
        Map<String, GoodsSpec> goodsSpecsMap = goodsSpecs.stream().collect(Collectors.toMap(GoodsSpec::getSpecName, Function.identity()));
        // 解析规格组V
        for (GoodsVpuExcelImportBo importBo : importBos) {
            String desc = importBo.getPrdDesc();
            String[] kvStrs = desc.split(GoodsSpecProductService.PRD_DESC_DELIMITER);
            for (String kvStr : kvStrs) {
                String[] kv = kvStr.split(GoodsSpecProductService.PRD_VAL_DELIMITER);
                GoodsSpec goodsSpec = goodsSpecsMap.get(kv[0]);
                if (goodsSpec == null) {
                    continue;
                }
                boolean b = goodsSpec.getGoodsSpecVals().stream().anyMatch(specVal -> StringUtils.equals(specVal.getSpecValName(), kv[1]));
                if (!b) {
                    goodsSpec.getGoodsSpecVals().add(new GoodsSpecVal(kv[1]));
                }
            }
        }
        return goodsSpecs;
    }

    /**
     * GoodsVpuExcelImportBo提取商品表使用信息，并转换为Goods类
     *
     * @param goodsInfo GoodsVpuExcelImportBo
     * @return
     */
    private Goods convertGoodsExcelImportBoToGoods(GoodsVpuExcelImportBo goodsInfo) {
        Goods goods = new Goods();
        goods.setShareConfig(Util.toJson(new GoodsSharePostConfig()));
        goods.setGoodsName(goodsInfo.getGoodsName());
        goods.setGoodsSn(goodsInfo.getGoodsSn());
        goods.setGoodsAd(goodsInfo.getGoodsAd());
        goods.setIsOnSale(goodsInfo.getIsOnSale());
        if (GoodsConstant.OFF_SALE.equals(goodsInfo.getIsOnSale())) {
            goods.setSaleType(GoodsConstant.IN_STOCK);
        }
        goods.setLimitBuyNum(goodsInfo.getLimitBuyNum());
        goods.setGoodsWeight(goodsInfo.getGoodsWeight());
        goods.setUnit(goodsInfo.getUnit());
        goods.setGoodsImg(goodsInfo.getGoodsImgsStr());
        goods.setDeliverPlace(goodsInfo.getDeliverPlace());
        goods.setGoodsDesc(goodsInfo.getGoodsDesc());
        return goods;
    }

    /**
     * 处理外链图片集
     * @param importBos 待处理导入对象
     * @return 待入库
     */
    private List<DownloadImageBo> filterGoodsImages(List<GoodsVpuExcelImportBo> importBos) {
        List<DownloadImageBo> downloadImageBos = new ArrayList<>(5);

        for (GoodsVpuExcelImportBo importBo : importBos) {
            String goodsImgsStr = importBo.getGoodsImgsStr();
            if (goodsImgsStr == null) {
                continue;
            }
            String[] imgUrls = goodsImgsStr.replaceAll("；", ";").split(";");
            for (String imgUrl : imgUrls) {
                DownloadImageBo bo = downLoadImg(imgUrl);
                if (bo != null) {
                    downloadImageBos.add(bo);
                }
            }
            if (downloadImageBos.size() > 5) {
                downloadImageBos = downloadImageBos.subList(0, 4);
            }
        }
        return downloadImageBos;
    }

    /**
     * 过滤商品描述信息
     * @param goodsDesc 商品描述信息
     * @return
     */
    private String filterGoodsDesc(String goodsDesc,List<DownloadImageBo> downloadImageBos) {
        String retStr = RegexUtil.cleanBodyContent(goodsDesc);
        if (retStr == null) {
            return retStr;
        }
        retStr = filterImgTag(goodsDesc, downloadImageBos);
        retStr = filterBgUrlImg(retStr, downloadImageBos);
        return retStr;
    }

    /**
     * 过滤img标签
     * @param goodsDesc
     * @param downloadImageBos
     * @return
     */
    private String filterImgTag(String goodsDesc,List<DownloadImageBo> downloadImageBos){
        Pattern imgTagPattern = RegexUtil.getImgTagPattern();
        Matcher matcher = imgTagPattern.matcher(goodsDesc);
        StringBuilder sb = new StringBuilder();
        int index = 0;
        while (matcher.find()) {
            int groupStart = matcher.start();
            int groupEnd = matcher.end();
            String outerImgLink = matcher.group(1);
            String imgTagStr = matcher.group();

            if (outerImgLink != null) {
                DownloadImageBo bo = downLoadImg(outerImgLink);
                if (bo != null) {
                    downloadImageBos.add(bo);
                    imgTagStr = imgTagStr.replace(outerImgLink,bo.getImgUrl());
                } else {
                    imgTagStr = "";
                }
            } else {
                imgTagStr = "";
            }
            sb.append(goodsDesc,index,groupStart);
            sb.append(imgTagStr);
            index = groupEnd;
        }
        sb.append(goodsDesc,index,goodsDesc.length());
        return sb.toString();
    }

    /**
     * 过滤background-image 和background 的 url(xxxx)
     * @param goodsDesc
     * @param downloadImageBos
     * @return
     */
    private String filterBgUrlImg(String goodsDesc, List<DownloadImageBo> downloadImageBos) {
        Pattern bgUrlPattern = RegexUtil.getBgUrlPattern();
        Matcher matcher = bgUrlPattern.matcher(goodsDesc);
        StringBuilder sb = new StringBuilder();
        int index = 0;
        while (matcher.find()) {
            int groupStart = matcher.start();
            int groupEnd = matcher.end();
            String bgUrlStr = matcher.group();
            String outerImgLink = matcher.group(1);
            if (outerImgLink == null) {
                outerImgLink = matcher.group(2);
            }
            if (outerImgLink != null) {
                DownloadImageBo bo = downLoadImg(outerImgLink);
                if (bo != null) {
                    downloadImageBos.add(bo);
                    bgUrlStr = bgUrlStr.replace(outerImgLink,bo.getImgUrl());
                } else {
                    bgUrlStr = "";
                }
            } else {
                bgUrlStr = "";
            }
            sb.append(goodsDesc,index,groupStart);
            sb.append(bgUrlStr);
            index = groupEnd;
        }
        sb.append(goodsDesc,index,goodsDesc.length());
        return sb.toString();
    }
    /**
     * 下载并上传外链图片
     * @param imgUrl 外链地址
     * @return null 无法处理
     */
    private DownloadImageBo downLoadImg(String imgUrl){
        if (StringUtils.isBlank(imgUrl)) {
            return null;
        }
        imgUrl = imgUrl.replaceAll("\\n", "").trim();
        if (0 != imgUrl.indexOf("http")) {
            imgUrl = "http://" + imgUrl;
        }
        try {
            DownloadImageBo downloadImageBo = imageService.downloadImgAndUpload(imgUrl);
            return downloadImageBo;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
