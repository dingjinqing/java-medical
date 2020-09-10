package com.vpu.mp.service.shop.anchor;

import cn.hutool.core.date.DateField;
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
import org.elasticsearch.common.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
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
    public AnchorPointsChartReportVo countReport(AnchorPointsListParam param) {
        Map<Date, List<AnchorPointsReportVo>> countMap = anchorPointsDao.countReport(param);
        //时间轴
        List<String> datalist = countMap.keySet().stream().map(DateUtil::formatDate).collect(Collectors.toList());
        AnchorPointsChartReportVo option =new AnchorPointsChartReportVo();
        Set<String> valueSet =new HashSet<>();
        AnchorPointsChartReportVo.SeriesData seriesData;
        List<AnchorPointsChartReportVo.SeriesData> seriesDataList =new ArrayList<>();
        Map<String,AnchorPointsChartReportVo.SeriesData> map =new HashMap<>();
        // 初始化 legend-data
        countMap.forEach((k,v)->{
            v.forEach(item->{
                valueSet.add(item.getValue());
            });
        });
        // 初始化 series
        for (String value : valueSet) {
            seriesData = new AnchorPointsChartReportVo.SeriesData();
            seriesData.setName(value);
            seriesData.setStack(param.getKey());
            seriesDataList.add(seriesData);
        }
        // series放入data数据
        for (AnchorPointsChartReportVo.SeriesData data : seriesDataList) {
            Timestamp startDate = param.getStartTime();
            Timestamp endDate = param.getEndTime();
            Integer count =0;
            while (endDate.compareTo(startDate) >= 0) {
                List<AnchorPointsReportVo> list = countMap.get(DateUtil.date(startDate).toSqlDate());
                count =0;
                if (list!=null){
                    for (AnchorPointsReportVo report : list) {
                        if (data.getName().equals(report.getValue())) {
                            count = report.getCount();
                        }
                    }
                }
                data.getDataMap().put(DateUtil.formatDate(startDate),count.toString());
                startDate = DateUtil.offset(startDate, DateField.DAY_OF_YEAR, 1).toTimestamp();
            }
            if (Strings.isEmpty(data.getName())){
                data.setName(param.getKey());
                datalist.add(param.getKey());
            }
            data.setDataList(new ArrayList<>(data.getDataMap().values()));
        }
        option.setXAxisData(datalist);
        option.setSeriesList(seriesDataList);
        option.setLegendData(new ArrayList<>(valueSet));
        return option;
    }

    public AnchorPointsChartReportVo moneyReport(AnchorPointsListParam param){
        Map<Date, AnchorPointsReportVo> countMap = anchorPointsDao.moneyReport(param);
        AnchorPointsChartReportVo option =new AnchorPointsChartReportVo();



        return option;
    }


}
