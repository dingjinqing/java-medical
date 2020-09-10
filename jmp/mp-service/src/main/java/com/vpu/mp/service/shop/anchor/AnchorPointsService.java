package com.vpu.mp.service.shop.anchor;

import cn.hutool.core.date.DateUtil;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.pojo.shop.table.AnchorPointsDo;
import com.vpu.mp.dao.shop.anchor.AnchorPointsDao;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.anchor.AnchorPointsChartReportVo;
import com.vpu.mp.service.pojo.shop.anchor.AnchorPointsListParam;
import com.vpu.mp.service.pojo.shop.anchor.AnchorPointsListVo;
import com.vpu.mp.service.pojo.shop.anchor.AnchorPointsParam;
import com.vpu.mp.service.pojo.shop.anchor.AnchorPointsReportVo;
import com.vpu.mp.service.pojo.shop.anchor.AnchorPotionEventBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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


    public PageResult<AnchorPointsListVo> list(AnchorPointsListParam param) {
       return anchorPointsDao.list(param);
    }

    public List<AnchorPotionEventBo> eventKeyMap() {
        return AnchorPointsEvent.eventKeyMap();
    }

    /**
     * 报表
     * @param param
     * @return
     */
    public AnchorPointsChartReportVo report(AnchorPointsListParam param) {
        Map<Date, List<AnchorPointsReportVo>> countMap = anchorPointsDao.countReport(param);
        //时间轴
        List<String> datalist = countMap.keySet().stream().map(DateUtil::formatDate).collect(Collectors.toList());
        AnchorPointsChartReportVo option =new AnchorPointsChartReportVo();
        AnchorPointsChartReportVo.SeriesData seriesData;
        List<AnchorPointsChartReportVo.SeriesData> seriesDataList =new ArrayList<>();
        Set<String> valueSet =new HashSet<>();
        for (Map.Entry<Date, List<AnchorPointsReportVo>> entry : countMap.entrySet()) {
            Date k = entry.getKey();
            List<AnchorPointsReportVo> v = entry.getValue();
            for (AnchorPointsReportVo item : v) {
                seriesData = new AnchorPointsChartReportVo.SeriesData();
                seriesData.setName(item.getValue());
                seriesData.setStack(item.getKey());
                seriesData.getDataList().add(item.getCount().toString());
                seriesDataList.add(seriesData);
                valueSet.add(item.getValue());
            }
        }
        option.setXAxisData(datalist);
        option.setSeriesList(seriesDataList);
        option.setLegendData(new ArrayList<>(valueSet));
        anchorPointsDao.moneyReport(param);
        return option;

    }
}
