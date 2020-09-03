package com.vpu.mp.service.shop.anchor;

import com.vpu.mp.common.pojo.shop.table.AnchorPointsDo;
import com.vpu.mp.dao.shop.anchor.AnchorPointsDao;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.anchor.AnchorPointsParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 锚点
 * @author 孔德成
 * @date 2020/8/28 16:43
 */
@Component
public class AnchorPointsService extends ShopBaseService {

    @Autowired
    private AnchorPointsDao anchorPointsDao;

    public void add(AnchorPointsParam param){
        param.getList().forEach(item->{
            AnchorPointsEvent event = AnchorPointsEvent.getInstance(param.getEvent(), item.getKey());
            if(event!=null){
                AnchorPointsDo anchorPointsDo = event.getDo();
                anchorPointsDo.setDevice(param.getDevice());
                anchorPointsDo.setValue(item.getValue());
                anchorPointsDo.setKey(item.getKey());
                anchorPointsDo.setPage(param.getPage());
                anchorPointsDo.setPlatform(param.getPlatform());
                anchorPointsDo.setStoreId(param.getStoreId());
                anchorPointsDo.setUserId(param.getUserId());
                anchorPointsDao.save(anchorPointsDo);
            }else {
                AnchorPointsDo anchorPointsDo =new AnchorPointsDo();
                anchorPointsDo.setEvent(param.getEvent());
                anchorPointsDo.setStoreId(param.getStoreId());
                anchorPointsDo.setKey(item.getKey());
                anchorPointsDo.setDevice(param.getDevice());
                anchorPointsDo.setValue(item.getValue());
                anchorPointsDo.setPage(param.getPage());
                anchorPointsDo.setPlatform(param.getPlatform());
                anchorPointsDo.setStoreId(param.getStoreId());
                anchorPointsDo.setUserId(param.getUserId());
                anchorPointsDao.save(anchorPointsDo);
            }
        });
    }




}
