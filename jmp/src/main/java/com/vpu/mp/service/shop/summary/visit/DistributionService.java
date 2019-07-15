package com.vpu.mp.service.shop.summary.visit;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vpu.mp.db.shop.tables.records.MpDistributionVisitRecord;
import com.vpu.mp.service.foundation.Util;
import com.vpu.mp.service.pojo.shop.summary.*;
import org.jooq.Result;

import java.util.*;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.tables.MpDistributionVisit.MP_DISTRIBUTION_VISIT;
import static com.vpu.mp.service.pojo.shop.summary.DistributionIndex.*;

/**
 * 访问分布
 *
 * @author 郑保乐
 */
public class DistributionService extends BaseVisitService {

    public VisitDistributionVo getVisitDistribution(VisitDistributionParam param) {
        VisitDistributionVo vo = new VisitDistributionVo();
        /* 访问来源 */
        Map<String, Integer> sourceMap = new TreeMap<>();
        /* 停留时长 */
        Map<String, Integer> stayTimeMap = new TreeMap<>();
        /*  平均访问深度 */
        Map<String, Integer> depthMap = new TreeMap<>();
        String startDate = param.getStartDate();
        String endDate = param.getEndDate();
        Result<MpDistributionVisitRecord> result = getDistributionRecord(startDate, endDate);
        for (MpDistributionVisitRecord record : result) {
            String list = record.getList();
            /* 转换统计 JSON */
            List<DistributionIndex> indexes = Util.parseJson(list, new TypeReference<List<DistributionIndex>>() {});
            for (DistributionIndex index : Objects.requireNonNull(indexes)) {
                String indexName = index.getName();
                switch (indexName) {
                    case ACCESS_SOURCE:
                        groupingIndex(sourceMap, index);
                        break;
                    case VISIT_DURATION:
                        groupingIndex(stayTimeMap, index);
                        break;
                    case VISIT_DEPTH:
                        groupingIndex(depthMap, index);
                        break;
                }
            }
        }
        vo.setVisitSource(xKeyYValueVo(sourceMap));
        vo.setVisitDepth(yKeyXValueVo(depthMap));
        vo.setVisitStayTime(yKeyXValueVo(stayTimeMap));
        vo.setAccessSourceSessionCnt(getInfoDict(sourceMap, AccessSource.values()));
        vo.setAccessDepthInfo(getInfoDict(depthMap, AccessDepth.values()));
        vo.setAccessStayTimeInfo(getInfoDict(stayTimeMap, VisitDuration.values()));
        return vo;
    }

    /**
     * 由 map 转换到 chart (x: key, y: value)
     */
    private ChartXKeyYValue xKeyYValueVo(Map<String, Integer> map) {
        ChartXKeyYValue chart = new ChartXKeyYValue();
        fillChart(map, chart);
        return chart;
    }

    /**
     * 由 map 转换到 chart (y: key, x: value)
     */
    private ChartXValueYKey yKeyXValueVo(Map<String, Integer> map) {
        ChartXValueYKey chart = new ChartXValueYKey();
        fillChart(map, chart);
        return chart;
    }

    /**
     * 填充数据，生成图表
     */
    private void fillChart(Map<String, Integer> map, ChartData chart) {
        List<String> keys = new ArrayList<>();
        List<Integer> values = new ArrayList<>();
        map.forEach((k,v) -> {
            keys.add(k);
            values.add(v);
        });
        chart.setKeys(keys);
        chart.setValues(values);
    }

    /**
     * 将统计数据分组
     */
    private void groupingIndex(Map<String, Integer> map, DistributionIndex index) {
        List<DistributionIndexItem> items = index.getItems();
        items.forEach(i -> {
            Integer key = i.getKey();
            AccessSource kv = Arrays.stream(AccessSource.values())
                    .filter(k -> k.getIndex().equals(key)).findFirst().orElseThrow(RuntimeException::new);
            String sourceName = kv.getSource();
            map.computeIfPresent(sourceName, (k, ov) -> ov + i.getValue());
        });
    }

    /**
     * 生成同时包含 name、index 和 value 的统计数据
     *
     * @param map 存放 name 和 value 的统计数据
     * @param chartInfo 图表图例
     * @param <T> 具体的枚举类
     */
    private <T extends ChartInfo> List<VisitInfoItem> getInfoDict(Map<String, Integer> map, T[] chartInfo) {
        return Arrays.stream(chartInfo)
                .map(s -> {
                    VisitInfoItem item = new VisitInfoItem();
                    item.setName(s.getName());
                    item.setKey(s.getKey());
                    item.setValue(map.get(s.getName()));
                    return item;
                }).collect(Collectors.toList());
    }

    private Result<MpDistributionVisitRecord> getDistributionRecord(String startDate, String endDate) {
        return db().select(MP_DISTRIBUTION_VISIT.REF_DATE, MP_DISTRIBUTION_VISIT.LIST)
                .from(MP_DISTRIBUTION_VISIT)
                .where(MP_DISTRIBUTION_VISIT.REF_DATE.between(startDate).and(endDate))
                .fetch().into(MP_DISTRIBUTION_VISIT);
    }
}
