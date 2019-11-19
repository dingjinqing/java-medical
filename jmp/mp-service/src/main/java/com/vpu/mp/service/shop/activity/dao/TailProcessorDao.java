package com.vpu.mp.service.shop.activity.dao;

import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.config.pledge.PledgeBo;
import com.vpu.mp.service.pojo.shop.config.pledge.PledgeInfo;
import com.vpu.mp.service.shop.config.PledgeConfigService;
import com.vpu.mp.service.shop.config.pledge.ShopPledgeService;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.vpu.mp.db.shop.Tables.USER_COLLECTION;

/**
 *小程序商品tail处理器获取数据库信息类
 * @author 李晓冰
 * @date 2019年11月19日
 */
@Service
public class TailProcessorDao extends ShopBaseService {

        @Autowired
        private ShopPledgeService pledgeService;
        @Autowired
        private PledgeConfigService pledgeConfigService;

    public boolean isCollectedGoods(Integer userId,Integer goodsId){
        int i = db().fetchCount(USER_COLLECTION, USER_COLLECTION.USER_ID.eq(userId).and(USER_COLLECTION.GOODS_ID.eq(goodsId)));
        return i>0;
    }


    /**
     * 商户信息
     * @param shopId
     * @return
     */
    public ShopRecord  shopInfo(Integer shopId){
        return saas.shop.getShopById(shopId);
    }

    /**
     * 服务承诺
     * @return PledgeBo
     */
    public PledgeBo getPledgeList() {
        PledgeBo pledgeBo =new PledgeBo();
        // 得到当前配置开关信息
        String pledgeSwitch = pledgeConfigService.getPledgeConfig();
        pledgeBo.setPledgeSwitch(pledgeSwitch);
        // 若开关状态为0-关闭
        if (PledgeConfigService.V_PLEDGE_OPEN.equals(pledgeSwitch)) {
            List<PledgeInfo> pledgeList = pledgeService.getPledgeList();
            pledgeBo.setPledgeList(pledgeList);
        }
        return pledgeBo;
    }
}
