package com.vpu.mp.service.shop.summary.portrait;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vpu.mp.db.shop.tables.records.MpUserPortraitRecord;
import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.Util;
import com.vpu.mp.service.pojo.shop.summary.ChartXKeyYValue;
import com.vpu.mp.service.pojo.shop.summary.portrait.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.tables.MpUserPortrait.MP_USER_PORTRAIT;

/**
 * 用户画像
 *
 * @author 郑保乐
 */
public class PortraitService extends BaseService {

    public PortraitVo getPortrait(PortraitParam param) {
        Integer type = param.getType();
        MpUserPortraitRecord portraitResult = getPortraitResult(type);
        Portrait visitUv = parseVisitJson(portraitResult.getVisitUv());
        Portrait visitUvNew = parseVisitJson(portraitResult.getVisitUvNew());
        ChartXKeyYValue activeUser = getChart(visitUv);
        ChartXKeyYValue activeUserNew = getChart(visitUvNew);
        Portrait portrait = new Portrait();
        Portrait portraitNew = new Portrait();
        portrait.setAgesFirst(activeUser);
        portraitNew.setAgesFirst(activeUserNew);
        PortraitVo vo = new PortraitVo();
        vo.setActiveUser(portrait);
        vo.setNewAddUser(portraitNew);
        PortraitSum activeUserSum = portraitSumObject(portrait);
        PortraitSum newAddUserSum = portraitSumObject(portraitNew);
        vo.setActiveUserSum(activeUserSum);
        vo.setNewAddUserSum(newAddUserSum);
        return vo;
    }

    /**
     * 将字段存储的 json 转换成 Portrait 对象
     */
    private Portrait parseVisitJson(String json) {
        return Util.parseJson(json, new TypeReference<Portrait>() {});
    }

    /**
     * 生成图表对象
     */
    private ChartXKeyYValue getChart(Portrait portrait) {
        List<PortraitItem> ages = portrait.getAges();
        List<String> names = ages.stream().map(PortraitItem::getName).collect(Collectors.toList());
        List<Integer> values = ages.stream().map(PortraitItem::getValue).collect(Collectors.toList());
        ChartXKeyYValue chart = new ChartXKeyYValue();
        chart.setKeys(names);
        chart.setValues(values);
        return chart;
    }

    /**
     * 计算各个指标的总和
     */
    private Integer portraitSum(List<PortraitItem> items) {
        return items.parallelStream().mapToInt(PortraitItem::getValue).sum();
    }

    /**
     * 生成总和对象
     */
    private PortraitSum portraitSumObject(Portrait portrait) {
        PortraitSum sum = new PortraitSum();
        sum.setProvince(portraitSum(portrait.getProvince()));
        sum.setAges(portraitSum(portrait.getAges()));
        sum.setCity(portraitSum(portrait.getCity()));
        sum.setDevices(portraitSum(portrait.getDevices()));
        sum.setGenders(portraitSum(portrait.getGenders()));
        sum.setPlatforms(portraitSum(portrait.getPlatforms()));
        return sum;
    }

    private MpUserPortraitRecord getPortraitResult(Integer type) {
        return db().select(MP_USER_PORTRAIT.VISIT_UV, MP_USER_PORTRAIT.VISIT_UV_NEW)
                .from(MP_USER_PORTRAIT)
                .where(MP_USER_PORTRAIT.CREATE_TIME.lessOrEqual(Timestamp.from(Instant.now()))
                        .and(MP_USER_PORTRAIT.TYPE.equal(type.byteValue())))
                .fetchOne()
                .into(MP_USER_PORTRAIT);
    }
}