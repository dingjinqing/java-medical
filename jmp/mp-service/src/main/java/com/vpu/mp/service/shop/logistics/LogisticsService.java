package com.vpu.mp.service.shop.logistics;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.BusinessException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.config.trade.LogisticsAccountInfo;
import com.vpu.mp.service.wechat.api.impl.WxOpenMaServiceExtraImpl;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.bean.result.WxOpenResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.vpu.mp.service.pojo.shop.market.form.FormConstant.MAPPER;

/**
 * The type Logistics service.
 *
 * @author liufei
 * @date 20190920
 */
@Slf4j
@Service
public class LogisticsService extends ShopBaseService {

    /**
     * 获取支持的快递公司列表
     *
     * @return 微信支持的快递公司列表
     * <p> eg:
     * [
     *     {
     *       "delivery_id": "BEST",
     *       "delivery_name": "百世快递"
     *     },
     *     {
     *       "delivery_id": "EMS",
     *       "delivery_name": "中国邮政速递物流"
     *     }
     *   ]
     * @throws WxErrorException 微信api调用异常
     */
    public List<Map<String, String>> getAllDelivery() throws WxErrorException {
        WxOpenMaServiceExtraImpl maService = open.getMaExtService();
        String jsonResult = maService.getAllDelivery(getAppId());
        List<Map<String, String>> deliveryList;
        try {
            JsonNode node = MAPPER.readTree(jsonResult);
            JsonNode dataNode = node.get("data");
            Assert.notNull(dataNode, "微信api调用失败，获取data节点数据失败！");
            deliveryList = MAPPER.readValue(dataNode.traverse(), new TypeReference<List<Map<String, String>>>() {
            });
        } catch (IOException e) {
            log.error("data节点数据[{}]反序列化失败：{}", jsonResult, e.getMessage());
            throw new BusinessException(JsonResultCode.CODE_FAIL);
        }
        return deliveryList;
    }

    /**
     * 绑定物流公司
     *
     * @param bindAccountParam 入参json字符串
     * @return 微信api调用结果封装实例 wx open result
     * @throws WxErrorException 微信api调用异常
     * @see com.vpu.mp.service.pojo.shop.config.trade.BindAccountParam
     */
    public WxOpenResult bindAccount(String bindAccountParam) throws WxErrorException {
        WxOpenMaServiceExtraImpl maService = open.getMaExtService();
        return maService.bindAccount(getAppId(), bindAccountParam);
    }

    /**
     * 拉取已绑定账号
     *
     * @return 已绑定账号列表
     * @throws WxErrorException the wx error exception
     * @see com.vpu.mp.service.pojo.shop.config.trade.LogisticsAccountInfo
     */
    public List<LogisticsAccountInfo> getAllAccount() throws WxErrorException {
        List<LogisticsAccountInfo> accountInfos = null;
        WxOpenMaServiceExtraImpl maService = open.getMaExtService();
        String jsonResult = maService.getAllAccount(getAppId());
        if (StringUtils.isBlank(jsonResult)) {
            return null;
        }
        try {
            JsonNode rootNode = MAPPER.readTree(jsonResult);
            JsonNode listNode = rootNode.get("list");
            Assert.notNull(listNode, "list节点不存在！");
            accountInfos = MAPPER.readValue(listNode.traverse(), new TypeReference<List<LogisticsAccountInfo>>() {
            });
        } catch (IOException e) {
            log.error("jackson反序列化时读取生成对象实例时失败：{}", e.getMessage());
            throw new BusinessException(JsonResultCode.CODE_FAIL);
        }
        return accountInfos;
    }

    /**
     * 获取接口调用凭证appid
     */
    private String getAppId() {
        return saas.shop.mp.getAuthShopByShopId(getShopId()).getAppId();
    }
}
