package com.vpu.mp.service.shop.summary.portrait;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vpu.mp.db.shop.tables.records.MpUserPortraitRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.summary.KeyValueChart;
import com.vpu.mp.service.pojo.shop.summary.portrait.*;
import org.jooq.Record2;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.vpu.mp.db.main.tables.DictCity.DICT_CITY;
import static com.vpu.mp.db.main.tables.DictProvince.DICT_PROVINCE;
import static com.vpu.mp.db.shop.tables.MpUserPortrait.MP_USER_PORTRAIT;
import static com.vpu.mp.db.shop.tables.UserDetail.USER_DETAIL;

/**
 * 用户画像
 *
 * @author 郑保乐
 */
@Service
public class PortraitService extends ShopBaseService {

    public PortraitVo getPortrait(PortraitParam param) {
        Integer type = param.getType();
        MpUserPortraitRecord portraitResult = getPortraitResult(type);
        Portrait visitUv = parseVisitJson(portraitResult.getVisitUv());
        Portrait visitUvNew = parseVisitJson(portraitResult.getVisitUvNew());
        KeyValueChart activeUser = getChart(visitUv);
        KeyValueChart activeUserNew = getChart(visitUvNew);
        visitUv.setAgesFirst(activeUser);
        visitUvNew.setAgesFirst(activeUserNew);
        PortraitVo vo = new PortraitVo();
        vo.setActiveUser(visitUv);
        vo.setNewAddUser(visitUvNew);
        PortraitSum activeUserSum = portraitSumObject(visitUv);
        PortraitSum newAddUserSum = portraitSumObject(visitUvNew);
        vo.setActiveUserSum(activeUserSum);
        vo.setNewAddUserSum(newAddUserSum);
        return vo;
    }

    public ProvinceVo getProvincePortrait(ProvinceParam param) {
        String province = param.getProvince();
        Result<Record2<Integer, Integer>> result = getProvinceSumResult(province);
        Map<Integer, String> cityMap = cityMap(province);
        List<PortraitItem> items = result.map(r -> {
            PortraitItem item = new PortraitItem();
            Integer cityId = (Integer) r.get(0);
            item.setId(cityId);
            item.setName(cityMap.get(cityId));
            item.setValue((Integer) r.getValue(1));
            return item;
        });
        ProvinceVo vo = new ProvinceVo();
        vo.setList(items);
        return vo;
    }

    /**
     * 将字段存储的 json 转换成 Portrait 对象
     */
    private Portrait parseVisitJson(String json) {
        return Util.parseJson(json, new TypeReference<Portrait>() {
        });
    }

    /**
     * 生成图表对象
     */
    private KeyValueChart getChart(Portrait portrait) {
        List<PortraitItem> ages = portrait.getAges();
        List<String> names = ages.stream().map(PortraitItem::getName).collect(Collectors.toList());
        List<Integer> values = ages.stream().map(PortraitItem::getValue).collect(Collectors.toList());
        KeyValueChart chart = new KeyValueChart();
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
     * 计算各个指标的总和（设备）
     */
    private Integer portraitDeivceSum(List<PortraitDeviceItem> items) {
        return items.parallelStream().mapToInt(PortraitDeviceItem::getValue).sum();
    }

    /**
     * 生成总和对象
     */
    private PortraitSum portraitSumObject(Portrait portrait) {
        PortraitSum sum = new PortraitSum();
        sum.setProvince(portraitSum(portrait.getProvince()));
        sum.setAges(portraitSum(portrait.getAges()));
        sum.setCity(portraitSum(portrait.getCity()));
        sum.setDevices(portraitDeivceSum(portrait.getDevices()));
        sum.setGenders(portraitSum(portrait.getGenders()));
        sum.setPlatforms(portraitSum(portrait.getPlatforms()));
        return sum;
    }

    private MpUserPortraitRecord getPortraitResult(Integer type) {
        return db().select(MP_USER_PORTRAIT.VISIT_UV, MP_USER_PORTRAIT.VISIT_UV_NEW)
                .from(MP_USER_PORTRAIT)
                .where(MP_USER_PORTRAIT.CREATE_TIME.lessOrEqual(Timestamp.from(Instant.now()))
                        .and(MP_USER_PORTRAIT.TYPE.equal(type.byteValue())))
                .limit(1)
                .fetchOne()
                .into(MP_USER_PORTRAIT);
    }

    /**
     * 按省份统计人数查询
     *
     * @param provinceName 省份名称
     *
     * @return province_id count
     */
    private Result<Record2<Integer, Integer>> getProvinceSumResult(String provinceName) {
        Integer provinceId = mainDb().select(DICT_PROVINCE.PROVINCE_ID)
                .from(DICT_PROVINCE)
                .where(DICT_PROVINCE.NAME.equal(provinceName)).fetchOne().into(Integer.class);
        return db().select(USER_DETAIL.CITY_CODE, DSL.count(USER_DETAIL.ID))
                .from(USER_DETAIL)
                .where(USER_DETAIL.PROVINCE_CODE.equal(provinceId))
                .and(USER_DETAIL.CITY_CODE.notEqual(0))
                .groupBy(USER_DETAIL.CITY_CODE)
                .fetch();
    }

    /**
     * 获取城市 id 名称 map
     *
     * @param provinceName 省份名称
     */
    private Map<Integer, String> cityMap(String provinceName) {
        Integer provinceId = mainDb().select(DICT_PROVINCE.PROVINCE_ID)
                .from(DICT_PROVINCE)
                .where(DICT_PROVINCE.NAME.equal(provinceName)).fetchOne().into(Integer.class);
        Result<Record2<Integer, String>> cityIdName =
                mainDb().select(DICT_CITY.CITY_ID, DICT_CITY.NAME)
                        .from(DICT_CITY)
                        .where(DICT_CITY.PROVINCE_ID.equal(provinceId))
                        .fetch();
        return cityIdName.intoMap(DICT_CITY.CITY_ID, DICT_CITY.NAME);
    }
}