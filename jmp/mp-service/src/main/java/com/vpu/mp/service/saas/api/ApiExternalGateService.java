package com.vpu.mp.service.saas.api;

import com.vpu.mp.config.ApiExternalGateConfig;
import com.vpu.mp.db.main.tables.records.AppAuthRecord;
import com.vpu.mp.db.main.tables.records.AppRecord;
import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.db.shop.tables.records.OrderGoodsRecord;
import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.service.MainBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.foundation.util.api.ApiBasePageParam;
import com.vpu.mp.service.pojo.saas.api.ApiExternalGateParam;
import com.vpu.mp.service.pojo.saas.api.ApiJsonResult;
import com.vpu.mp.service.pojo.shop.goods.api.ApiGoodsDetailParam;
import com.vpu.mp.service.pojo.shop.goods.api.ApiGoodsDetailVo;
import com.vpu.mp.service.pojo.shop.goods.api.ApiGoodsPageResult;
import com.vpu.mp.service.pojo.shop.goods.api.ApiSyncStockParam;
import com.vpu.mp.service.pojo.shop.goods.pos.PosSyncProductParam;
import com.vpu.mp.service.pojo.shop.goods.pos.PosSyncStockParam;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.api.PosReturnGoodsParam;
import com.vpu.mp.service.pojo.shop.order.api.PosReturnGoodsVo;
import com.vpu.mp.service.pojo.shop.order.api.PosVerifyOrderParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderServiceCode;
import com.vpu.mp.service.pojo.shop.order.write.operate.refund.RefundParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.verify.verifyParam;
import com.vpu.mp.service.shop.order.action.base.ExecuteResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 小程序对接POS,ERP服务service层
 * @author 李晓冰
 * @date 2020年03月30日
 */
@Service
@Slf4j
public class ApiExternalGateService extends MainBaseService {
    @Autowired
    private ApiExternalGateConfig config;

    /**
     * 验证系统级参数
     * 'app_id', 'app_secret', 'session_key', 'service_name'
     * @param param {@link ApiExternalGateParam}
     * @return null 表示必要字段都存在，否则代表第一个空字段的参数名
     */
    public String checkSystemParam(ApiExternalGateParam param) {
        String nullKey = null;
        if (StringUtils.isBlank(param.getApp_id())) {
            nullKey = "app_id";
        } else if (StringUtils.isBlank(param.getApp_secret())) {
            nullKey = "app_secret";
        } else if (StringUtils.isBlank(param.getSession_key())) {
            nullKey = "session_key";
        } else if (StringUtils.isBlank(param.getService_name())) {
            nullKey = "service_name";
        } else {
            nullKey = null;
        }
        if (nullKey != null) {
            logPrinter(param.getApp_id(), ApiExternalGateConfig.ERROR_LACK_PARAM_MSG + ":" + nullKey);
        }
        return nullKey;
    }

    /**
     * 校验接口调用时间
     * @param param {@link ApiExternalGateParam}
     * @return false时间参数不合法 可能是null或者当前时间和改时间差超过30秒，true
     */
    public boolean checkTimeStamp(ApiExternalGateParam param) {
        if (param.getTimestamp() == null) {
            logPrinter(param.getApp_id(), "timestamp 为空");
            return false;
        }
        Timestamp timestamp = DateUtil.dateFormatToTimeStamp(DateUtil.DATE_FORMAT_API_EXTERNAL, param.getTimestamp());
        Timestamp now = DateUtil.getLocalDateTime();
        // 大于30秒
        if (now.getTime() - timestamp.getTime() > 30000) {
            logPrinter(param.getApp_id(), "timestamp 超时");
            return false;
        }
        return true;
    }

    /**
     * 校验签名是否正确
     * @param param {@link ApiExternalGateParam}
     * @return true合法 false 错误
     */
    public boolean checkSign(ApiExternalGateParam param) {
        List<String> list = new ArrayList<>(5);
        list.add(param.getApp_id());
        list.add(param.getApp_secret());
        list.add(param.getSession_key());
        list.add(param.getService_name());
        list.add(param.getContent());
        list.sort(Comparator.naturalOrder());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i != list.size() - 1) {
                sb.append("&");
            }
        }
        String s = Util.md5(sb.toString());
        s = s + param.getTimestamp() + config.getSignKey();
        s = Util.md5(s);
        if (!s.equals(param.getSign())) {
            logPrinter(param.getApp_id(), "签名错误");
            return false;
        } else {
            return true;
        }
    }

    /**
     * 根据sessionKey解析shopId 最后一个s字符后面的字符表示shopId
     * @param param {@link ApiExternalGateParam}
     * @return shopId -1 解析错误
     */
    public Integer parseShopId(ApiExternalGateParam param) {
        @NotNull String session_key = param.getSession_key();
        boolean ok = Pattern.matches(".*s\\d+", session_key);
        if (!ok) {
            return -1;
        }
        int si = session_key.lastIndexOf('s');
        String shopIdStr = session_key.substring(si + 1);
        return Integer.parseInt(shopIdStr);
    }

    /**
     * 验证店铺是否有效
     * @param param {@link ApiExternalGateParam}
     * @return true有效， false无效
     */
    public boolean checkShop(ApiExternalGateParam param) {
        if (param.getShopId() == null) {
            return false;
        }
        ShopRecord shop = saas.shop.getShopById(param.getShopId());
        if (shop == null) {
            logPrinter(param.getApp_id(), "店铺id无效");
            return false;
        }
        return true;
    }

    /**
     * 验证店铺是否存在有效授权
     * @param param
     * @return true有，false授权失效
     */
    public boolean checkAppAuth(ApiExternalGateParam param) {
        AppRecord appInfo = saas.shop.shopApp.getAppInfo(param.getApp_id(), param.getApp_secret());
        if (appInfo == null) {
            logPrinter(param.getApp_id(), "数据错误 appid：" + param.getApp_id() + "appSecret：" + param.getApp_secret());
            return false;
        }

        AppAuthRecord appAuthRecord = saas.shop.shopApp.getAppAuthInfoBySessionKey(param.getSession_key());
        if (appAuthRecord == null) {
            logPrinter(param.getApp_id(), "店铺无有效授权，sessionKey：" + param.getSession_key());
            return false;
        }
        return true;
    }

    /**
     * 判断是否是短信平台的请求
     * @param sessionKey
     * @return
     */
    public boolean isSmsPlatformApi(String sessionKey) {
        return config.getSmsPlatformKey().equals(sessionKey);
    }


    /**
     * 判断请求的服务名称是否有效
     * @param param {@link ApiExternalGateParam}
     * @return true有效服务 false无效服务
     */
    public boolean checkService(ApiExternalGateParam param) {
        if (ApiExternalGateConfig.E_INTERCEPTOR_SERVICE_NAMES.contains(param.getService_name())
            && !config.getSmsPlatformKey().equals(param.getSession_key())) {
            return false;
        }

        if (!ApiExternalGateConfig.SERVICE_NAMES.contains(param.getService_name())) {
            return false;
        }
        return true;
    }


    /**
     * 转发调用具体的服务提供方法
     * @param param {@link ApiExternalGateParam}
     * @return 服务返回结果
     */
    public ApiJsonResult serviceFunCall(ApiExternalGateParam param) {
        String service_name = param.getService_name();
        ApiJsonResult apiJsonResult = null;
        switch (service_name) {
            case ApiExternalGateConfig.SERVICE_POS_SYNC_PRODUCT:
                apiJsonResult = posSyncProduct(param);
                break;
            case ApiExternalGateConfig.SERVICE_POS_SYNC_STOCK:
                apiJsonResult = posSyncStock(param);
                break;
            case ApiExternalGateConfig.SERVICE_POS_RETURN_GOODS:
                apiJsonResult = posReturnGoods(param);
                break;
            case ApiExternalGateConfig.SERVICE_POS_VERIFY_ORDER:
                apiJsonResult = posVerifyOrder(param);
                break;
            case ApiExternalGateConfig.SERVICE_ORDER_LIST:
                //订单列表
                apiJsonResult = saas().getShopApp(param.getShopId()).readOrder.getPageList(param);
                break;
            case ApiExternalGateConfig.SERVICE_SINGLE_ORDER:
                //单个订单
                apiJsonResult = saas().getShopApp(param.getShopId()).readOrder.apiGet(param);
                break;
            case ApiExternalGateConfig.SERVICE_SYNC_LOGISTICS:
                //同步物流单号(发货)
                apiJsonResult = saas().getShopApp(param.getShopId()).orderApi.shipping(param);
                break;
            case ApiExternalGateConfig.SERVICE_REFUND_ORDER_LIST:
                //退款订单列表
                apiJsonResult = saas().getShopApp(param.getShopId()).readOrder.getReturnPageList(param);
                break;
            case ApiExternalGateConfig.SERVICE_SYNC_REFUND_ORDER_RESULT:
                //同步退款结果
                apiJsonResult = saas().getShopApp(param.getShopId()).orderApi.returnOrder(param);
                break;
            case ApiExternalGateConfig.SERVICE_DELIVER_EXCHANGE_GOODS:
                //发货（换货未实现）
                break;
            case ApiExternalGateConfig.SERVICE_GOODS_LIST:
                apiJsonResult = goodsList(param);
                break;
            case ApiExternalGateConfig.SERVICE_SINGLE_GOODS:
                // 单个商品信息获取
                apiJsonResult = singleGoods(param);
                break;
            case ApiExternalGateConfig.SERVICE_SYNC_STOCK:
                // 同步商品库存
                apiJsonResult = syncStock(param);
                break;
            default:
                apiJsonResult = new ApiJsonResult();
                apiJsonResult.setCode(ApiExternalGateConfig.ERROR_CODE_INVALID_SERVICE);
                apiJsonResult.setMsg(ApiExternalGateConfig.ERROR_CODE_INVALID_SERVICE_MSG);
        }
        return apiJsonResult;
    }

    /**
     * pos 同步商品信息(门店商品信息) 主要同步上下架和价格
     * @param gateParam
     * @return
     */
    private ApiJsonResult posSyncProduct(ApiExternalGateParam gateParam) {
        PosSyncProductParam param = Util.parseJson(gateParam.getContent(), PosSyncProductParam.class);
        if (param == null) {
            return contentErrorResult();
        }
        return saas().getShopApp(gateParam.getShopId()).apiGoodsService.posSyncProductMq(param);
    }

    /**
     * 同步门店库存
     * @param gateParam
     * @return
     */
    private ApiJsonResult posSyncStock(ApiExternalGateParam gateParam) {
        PosSyncStockParam param = Util.parseJson(gateParam.getContent(), PosSyncStockParam.class);
        if (param == null) {
            return contentErrorResult();
        }
        return saas().getShopApp(gateParam.getShopId()).apiGoodsService.posSyncStock(param);
    }

    private ApiJsonResult contentErrorResult() {
        ApiJsonResult apiJsonResult = new ApiJsonResult();
        apiJsonResult.setCode(ApiExternalGateConfig.ERROR_CODE_SYNC_FAIL);
        apiJsonResult.setMsg("content 内容参数错误");
        return apiJsonResult;
    }

    private void logPrinter(String app_id, String msg) {
        log.error("数据同步接口：" + ApiExternalGateConfig.APP_NAMES.get(app_id) + "：" + msg);
    }

    /**
     * pos核销订单
     * @param gateParam 参数
     * @return result
     */
    private ApiJsonResult posVerifyOrder(ApiExternalGateParam gateParam) {
        PosVerifyOrderParam param = Util.parseJson(gateParam.getContent(), PosVerifyOrderParam.class);
        ApiJsonResult result = new ApiJsonResult();
        if (param == null) {
            result.setCode(ApiExternalGateConfig.ERROR_LACK_PARAM);
            result.setMsg("content为空");
            return result;
        }
        if (StringUtils.isBlank(param.getOrderSn())) {
            result.setCode(ApiExternalGateConfig.ERROR_LACK_PARAM);
            result.setMsg("参数order_sn为空");
            return result;
        }
        if (param.getOrderStatus() == null || !param.getOrderStatus().equals(OrderConstant.ORDER_WAIT_DELIVERY)) {
            result.setCode(ApiExternalGateConfig.ERROR_LACK_PARAM);
            result.setMsg("参数order_status非法");
            return result;
        }
        OrderInfoRecord order = saas().getShopApp(gateParam.getShopId()).readOrder.orderInfo.getOrderByOrderSn(param.getOrderSn());
        if (order == null) {
            result.setCode(ApiExternalGateConfig.ERROR_LACK_PARAM);
            result.setMsg("核销订单不存在");
            return result;
        }
        verifyParam verifyParam = new verifyParam();
        verifyParam.setOrderId(order.getOrderId());
        verifyParam.setOrderSn(order.getOrderSn());
        verifyParam.setIsCheck(false);
        verifyParam.setAction((byte) OrderServiceCode.VERIFY.ordinal());
        verifyParam.setIsMp(OrderConstant.IS_MP_POS);
        ExecuteResult executeResult = saas().getShopApp(gateParam.getShopId()).orderActionFactory.orderOperate(verifyParam);
        if (executeResult == null || executeResult.isSuccess()) {
            result.setCode(ApiExternalGateConfig.ERROR_CODE_SUCCESS);
        } else {
            log.error("pos核销失败，executeResult：{}", executeResult);
            result.setCode(ApiExternalGateConfig.ERROR_LACK_PARAM);
            result.setMsg((String) executeResult.getResult());
        }
        return result;
    }

    private ApiJsonResult posReturnGoods(ApiExternalGateParam gateParam) {
        PosReturnGoodsParam param = Util.parseJson(gateParam.getContent(), PosReturnGoodsParam.class);
        ApiJsonResult result = new ApiJsonResult();
        if (param == null) {
            result.setCode(ApiExternalGateConfig.ERROR_LACK_PARAM);
            result.setMsg("content为空");
            return result;
        }
        if (StringUtils.isBlank(param.getOrderSn())) {
            result.setCode(ApiExternalGateConfig.ERROR_LACK_PARAM);
            result.setMsg("参数order_sn为空");
            return result;
        }
        //订单
        OrderInfoRecord order = saas().getShopApp(gateParam.getShopId()).readOrder.orderInfo.getOrderByOrderSn(param.getOrderSn());
        if (order == null) {
            result.setCode(ApiExternalGateConfig.ERROR_LACK_PARAM);
            result.setMsg("退款订单不存在");
            return result;
        }
        //校验商品构造ReturnGoods
        if (MapUtils.isEmpty(param.getGoods()) || MapUtils.isEmpty(param.getGift())) {
            result.setCode(ApiExternalGateConfig.ERROR_LACK_PARAM);
            result.setMsg("参数goods或gift为空");
            return result;
        }
        List<RefundParam.ReturnGoods> returnGoods = new ArrayList<>();
        Result<OrderGoodsRecord> orderGoods = saas().getShopApp(gateParam.getShopId()).readOrder.orderGoods.getByOrderId(order.getOrderId());
        for (Map.Entry<String, Integer> entry : param.getGoods().entrySet()) {
            //0普通商品,1赠品，2加价购
            Integer goodsType = param.getGift().get(entry.getKey());
            RefundParam.ReturnGoods oneReturnGoods = null;
            for (OrderGoodsRecord orderGoodsRecord : orderGoods) {
                if (orderGoodsRecord.getProductSn().equals(entry.getKey())) {
                    if (goodsType == 0 && orderGoodsRecord.getIsGift().equals(OrderConstant.IS_GIFT_N) && !(orderGoodsRecord.getActivityType().equals(BaseConstant.ACTIVITY_TYPE_PURCHASE_PRICE) && orderGoodsRecord.getActivityRule() > 0)) {
                        oneReturnGoods = new RefundParam.ReturnGoods();
                    } else if (goodsType == 1 && orderGoodsRecord.getIsGift().equals(OrderConstant.IS_GIFT_Y)) {
                        oneReturnGoods = new RefundParam.ReturnGoods();
                    } else if (goodsType == 2 && orderGoodsRecord.getActivityType().equals(BaseConstant.ACTIVITY_TYPE_PURCHASE_PRICE) && orderGoodsRecord.getActivityRule() > 0) {
                        oneReturnGoods = new RefundParam.ReturnGoods();
                    }
                    if (oneReturnGoods != null) {
                        oneReturnGoods.setRecId(orderGoodsRecord.getRecId());
                        oneReturnGoods.setReturnNumber(entry.getValue());
                        break;
                    }

                }
            }
            if (oneReturnGoods == null) {
                result.setCode(ApiExternalGateConfig.ERROR_LACK_PARAM);
                result.setMsg("退款订单商品不存在");
                return result;
            } else {
                returnGoods.add(oneReturnGoods);
            }

        }
        RefundParam executeParam = new RefundParam();
        executeParam.setOrderSn(order.getOrderSn());
        executeParam.setOrderId(order.getOrderId());
        executeParam.setAction((byte) OrderServiceCode.RETURN.ordinal());
        executeParam.setIsMp(OrderConstant.IS_MP_POS);
        executeParam.setReturnType(param.getReturnType());
        executeParam.setReturnMoney(param.getMoney());
        executeParam.setShippingFee(param.getShippingFee());
        executeParam.setReasonDesc(param.getReason());
        executeParam.setReturnGoods(returnGoods);
        ExecuteResult executeResult = saas().getShopApp(gateParam.getShopId()).orderActionFactory.orderOperate(executeParam);
        if (executeResult == null || executeResult.isSuccess()) {
            result.setCode(ApiExternalGateConfig.ERROR_CODE_SUCCESS);
            result.setData(Util.toJson(new PosReturnGoodsVo(executeParam.getReturnMoney(), executeParam.getShippingFee())));
        } else {
            log.error("pos退款失败，executeResult：{}", executeResult);
            result.setCode(ApiExternalGateConfig.ERROR_LACK_PARAM);
            result.setMsg((String) executeResult.getResult());
        }
        return result;
    }

    /**
     * erp-ekb 抓取商品信息
     * @param gateParam 参数
     * @return result
     */
    private ApiJsonResult goodsList(ApiExternalGateParam gateParam) {
        ApiBasePageParam param = Util.parseJson(gateParam.getContent(), ApiBasePageParam.class);
        if (param == null) {
            param = new ApiBasePageParam();
        }
        ApiGoodsPageResult goodsPageResult = saas().getShopApp(gateParam.getShopId()).apiGoodsService.apiGetGoodsList(param);

        ApiJsonResult result = new ApiJsonResult();
        result.setData(goodsPageResult);
        return result;
    }

    /**
     * erp-ekb 获取单个商品信息
     * @param gateParam 参数
     * @return result
     */
    private ApiJsonResult singleGoods(ApiExternalGateParam gateParam) {
        ApiGoodsDetailParam param = Util.parseJson(gateParam.getContent(), ApiGoodsDetailParam.class);
        if (param == null) {
            return contentErrorResult();
        }
        ApiJsonResult result = new ApiJsonResult();
        if (param.getGoodsId() == null) {
            result.setCode(ApiExternalGateConfig.ERROR_LACK_PARAM);
            result.setMsg(ApiExternalGateConfig.ERROR_LACK_PARAM_MSG + "：" + "goods_id");
            return result;
        }
        ApiGoodsDetailVo goodsDetailVo = saas().getShopApp(gateParam.getShopId()).apiGoodsService.apiGetSingleGoods(param);
        result.setData(goodsDetailVo);
        return result;
    }

    /**
     * erp-ekb 同步库存
     * @param gateParam param
     * @return result
     */
    private ApiJsonResult syncStock(ApiExternalGateParam gateParam){
        ApiSyncStockParam param = Util.parseJson(gateParam.getContent(), ApiSyncStockParam.class);
        if (param == null) {
            return contentErrorResult();
        }
        if (param.getSkuId() == null || param.getGoodsNum() == null) {
            ApiJsonResult result = new ApiJsonResult();
            result.setCode(ApiExternalGateConfig.ERROR_LACK_PARAM);
            result.setMsg(ApiExternalGateConfig.ERROR_LACK_PARAM_MSG + "：" + (param.getSkuId() == null ?"sku_id":"goods_num"));
            return result;
        }

        if (param.getGoodsNum() < 0) {
            ApiJsonResult result = new ApiJsonResult();
            result.setCode(ApiExternalGateConfig.ERROR_CODE_SYNC_FAIL);
            result.setMsg("库存数量小于0");
            return result;
        }

        return  saas().getShopApp(gateParam.getShopId()).apiGoodsService.syncStock(param);
    }
}
