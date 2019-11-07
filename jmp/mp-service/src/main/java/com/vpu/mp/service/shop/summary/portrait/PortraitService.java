package com.vpu.mp.service.shop.summary.portrait;

import static com.vpu.mp.db.main.tables.DictCity.DICT_CITY;
import static com.vpu.mp.db.shop.tables.MpUserPortrait.MP_USER_PORTRAIT;
import static com.vpu.mp.db.shop.tables.UserDetail.USER_DETAIL;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jooq.Record2;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vpu.mp.db.main.tables.records.DictCityRecord;
import com.vpu.mp.db.main.tables.records.DictProvinceRecord;
import com.vpu.mp.db.shop.tables.records.MpUserPortraitRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.summary.KeyValueChart;
import com.vpu.mp.service.pojo.shop.summary.portrait.Portrait;
import com.vpu.mp.service.pojo.shop.summary.portrait.PortraitDeviceItem;
import com.vpu.mp.service.pojo.shop.summary.portrait.PortraitItem;
import com.vpu.mp.service.pojo.shop.summary.portrait.PortraitParam;
import com.vpu.mp.service.pojo.shop.summary.portrait.PortraitSum;
import com.vpu.mp.service.pojo.shop.summary.portrait.PortraitVo;
import com.vpu.mp.service.pojo.shop.summary.portrait.ProvinceParam;
import com.vpu.mp.service.pojo.shop.summary.portrait.ProvinceVo;

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
        vo.setStartDate(showDate(type));
        vo.setEndDate(showDate(0));
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
    private Integer portraitDeviceSum(List<PortraitDeviceItem> items) {
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
        sum.setDevices(portraitDeviceSum(portrait.getDevices()));
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
    	DictProvinceRecord province = saas.region.province.getProvinceName(provinceName);

        return db().select(USER_DETAIL.CITY_CODE, DSL.count(USER_DETAIL.ID))
                .from(USER_DETAIL)
                .where(USER_DETAIL.PROVINCE_CODE.equal(province.getProvinceId()))
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
    	DictProvinceRecord province = saas.region.province.getProvinceName(provinceName);
    	Result<DictCityRecord> cityList = saas.region.city.getCityList(province.getProvinceId());
    	return cityList.intoMap(DICT_CITY.CITY_ID, DICT_CITY.NAME);
    }
    
    /**
     * 根据类型显示开始时间和结束时间
     * @param type
     * @return
     */
	private static String showDate(Integer type) {
		Timestamp time = null;
		switch (type) {
		case 0:
			// 昨天
			time = DateUtil.geTimeStampPlus(-1, ChronoUnit.DAYS);
			break;
		case 1:
			// 最近七天
			time = DateUtil.geTimeStampPlus(-7, ChronoUnit.DAYS);
			break;
		case 2:
			// 最近三十天
			time = DateUtil.geTimeStampPlus(-30, ChronoUnit.DAYS);
			break;
		default:
			time = Timestamp.valueOf(LocalDateTime.now());
			break;
		}
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime localDateTime2=time.toLocalDateTime();
		String format2 = df.format(localDateTime2);
		return format2;

	}
}