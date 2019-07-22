package com.vpu.mp.service.shop.market.goupbuy;

import com.vpu.mp.db.shop.tables.records.GroupBuyDefineRecord;
import com.vpu.mp.db.shop.tables.records.GroupBuyProductDefineRecord;
import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.DelFlag;
import com.vpu.mp.service.foundation.Util;
import com.vpu.mp.service.pojo.shop.market.groupbuy.*;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.vpu.mp.db.shop.Tables.*;

/**
 * @author 孔德成
 * @date 2019/7/18 15:55
 */
@Service
@Scope("prototype")
public class GroupBuyService extends BaseService {


    private static final byte USE_STATUS=1;
    private static final byte STOP_STATUS=0;


    /**
     * 添加拼团活动
     *
     * @param groupBuy
     */
    public int addGroupBuy(GroupBuyParam groupBuy) {
        db().transaction((configuration)->{
            DSLContext db = DSL.using(configuration);

            //分享配置转json
            GroupBuyShareConfigParam a =  groupBuy.getShare();
            groupBuy.setShareConfig(Util.toJson(groupBuy.getShare()));
            groupBuy.setStatus((byte) USE_STATUS);
            //拼团信息
            GroupBuyDefineRecord groupBuyDefineRecord =db.newRecord(GROUP_BUY_DEFINE,groupBuy);
            groupBuyDefineRecord.insert();
            //拼团商品规格价格信息
            for (GroupBuyProductParam product :groupBuy.getProduct()){
                GroupBuyProductDefineRecord productDefineRecord =db.newRecord(GROUP_BUY_PRODUCT_DEFINE,product);
                productDefineRecord.setActivityId(groupBuyDefineRecord.getId());
                productDefineRecord.insert();
            }
        });

        return 0;
    }


    public void getListGroupBuy(GroupBuyParam param) {


    }

    /**
     * 删除
     * @param id
     */
    public int deleteGroupBuy(Integer  id) {
        return  db().update(GROUP_BUY_DEFINE)
                .set(GROUP_BUY_DEFINE.DEL_FLAG, DelFlag.DISABLE.getCode())
                .where(GROUP_BUY_DEFINE.ID.eq(id))
                .execute();
    }

    /**
     * 编辑测试
     * @param param
     * @return
     */
    public int editGroupBuy(GroupBuyEditParam param) {
        param.setShareConfig(Util.toJson(param.getShare()));
       return db().update(GROUP_BUY_DEFINE)
                .set(GROUP_BUY_DEFINE.NAME,param.getName())
                .set(GROUP_BUY_DEFINE.SHARE_CONFIG,param.getShareConfig())
                .execute();

    }

    public void shareGroupBuy() {
    }

    /**
     * 停用或者启用
     * @param param
     */
    public void stopGroupBuy(GroupBuyIdParam param) {
        if (param.getStatus()==1){
            db().update(GROUP_BUY_DEFINE)
                    .set(GROUP_BUY_DEFINE.STATUS,(byte)0)
                    .where(GROUP_BUY_DEFINE.ID.eq(param.getId()))
                    .and(GROUP_BUY_DEFINE.STATUS.eq((byte) 1))
                    .execute();
        }else if (param.getStatus()==0){
            db().update(GROUP_BUY_DEFINE)
                    .set(GROUP_BUY_DEFINE.STATUS, (byte)1)
                    .where(GROUP_BUY_DEFINE.ID.eq(param.getId()))
                    .and(GROUP_BUY_DEFINE.STATUS.eq((byte) 0))
                    .execute();
        }

    }


    public GroupBuyDetailVo detailGroupBuy(Integer id) {
        GroupBuyDetailVo groupBuy= db().fetchOne(GROUP_BUY_DEFINE,
                        GROUP_BUY_DEFINE.ID.eq(id)
                        .and(GROUP_BUY_DEFINE.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
                        .and(GROUP_BUY_DEFINE.STATUS.eq(STOP_STATUS))
                        ).into(GroupBuyDetailVo.class);
        List<GroupBuyProductVo> products=db().fetch(GROUP_BUY_PRODUCT_DEFINE,
                GROUP_BUY_PRODUCT_DEFINE.ACTIVITY_ID.eq(id)
                ).into(GroupBuyProductVo.class);
        groupBuy.setProduct(products);
        groupBuy.setShare(Util.parseJson(groupBuy.getShareConfig(),GroupBuyShareConfigVo.class));
        groupBuy.setShareConfig(null);
        return groupBuy;
    }

    /**
     * 校验商品是否有叠加
     * @param parm
     * @return 0
     */
    public Boolean validGroupGoods(GroupBuyParam parm) {
         return db().fetchCount(GROUP_BUY_DEFINE,
                GROUP_BUY_DEFINE.GOODS_ID.eq(parm.getGoodsId())
                        .and(GROUP_BUY_DEFINE.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
                        .and(GROUP_BUY_DEFINE.START_TIME.gt(parm.getEndTime())
                        .or(GROUP_BUY_DEFINE.END_TIME.lt(parm.getStartTime())))) == 0;

    }
}
