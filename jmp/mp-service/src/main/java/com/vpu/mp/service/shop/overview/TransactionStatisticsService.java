package com.vpu.mp.service.shop.overview;

import com.vpu.mp.db.shop.tables.OrderInfo;
import com.vpu.mp.db.shop.tables.UserLoginRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.overview.transaction.GeographicalCollVo;
import com.vpu.mp.service.pojo.shop.overview.transaction.GeographicalParam;
import com.vpu.mp.service.pojo.shop.overview.transaction.GeographicalVo;
import org.jooq.Condition;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import static com.vpu.mp.service.shop.overview.RealTimeOverviewService.div;
import static org.jooq.impl.DSL.*;

/**
 * @author liufei
 * @date 2019/7/31
 * @description 交易统计
 */
@Service
public class TransactionStatisticsService extends ShopBaseService {

    private static OrderInfo oi = OrderInfo.ORDER_INFO.as("oi");
    private static UserLoginRecord ulr = UserLoginRecord.USER_LOGIN_RECORD.as("ulr");

    /**
     * 地域分布
     *
     * @param param 入参
     * @return 地域分布显示数据集
     */
    public GeographicalCollVo geographical(GeographicalParam param) {
        GeographicalCollVo collVo = new GeographicalCollVo();
        Optional<Date> screeningTime = Optional.of(param.getScreeningTime());
        Timestamp startTime = Util.currentMonthFirstDay(screeningTime.orElse(new Date()));
        Timestamp endTime = Util.nextMonthFirstDay(screeningTime.orElse(new Date()));
        List<GeographicalVo> voList = db().select(min(oi.PROVINCE_NAME).as("province"), min(oi.PROVINCE_CODE).as("provinceCode"), sum(oi.MONEY_PAID.add(oi.SCORE_DISCOUNT).add(oi.USE_ACCOUNT).add(oi.MEMBER_CARD_BALANCE)).as("totalDealMoney"), countDistinct(oi.USER_ID).as("orderUserNum"), count(oi.ORDER_ID).as("orderNum")).from(oi).where(commomCondition(startTime, endTime)).groupBy(oi.PROVINCE_CODE).fetchInto(GeographicalVo.class);
        for (GeographicalVo vo : voList) {
            vo.setUv(getDistrictUv(startTime, endTime, vo.getProvinceCode()));
            vo.setUv2paid(div(vo.getOrderUserNum(), vo.getUv()));
        }
        double maxMoney = voList.stream().max(Comparator.comparing(GeographicalVo::getTotalDealMoney)).orElse(new GeographicalVo()).getTotalDealMoney();
        double minMoney = voList.stream().min(Comparator.comparing(GeographicalVo::getTotalDealMoney)).orElse(new GeographicalVo()).getTotalDealMoney();
        collVo.setMaxTotalDealMoney(maxMoney);
        collVo.setMinTotalDealMoney(minMoney);
        collVo.setVos(voList.stream().sorted(Comparator.comparing(GeographicalVo::getTotalDealMoney)).collect(Collectors.toList()));
        //voList.stream().sorted(Comparator.comparing(GeographicalVo::getTotalDealMoney).reversed());

        return collVo;
    }

    /**
     * 构造公共查询条件，成交用户数，总成交金额，付款订单数
     */
    private Condition commomCondition(Timestamp startTime, Timestamp endTime) {
        return oi.CREATE_TIME.greaterOrEqual(startTime).and(oi.CREATE_TIME.lessThan(endTime).and(oi.ORDER_STATUS.greaterOrEqual((byte) 3).or(oi.ORDER_STATUS.eq((byte) 0).and(oi.BK_ORDER_PAID.greaterThan((byte) 0)))).and(oi.IS_COD.eq((byte) 0).or(oi.IS_COD.eq((byte) 1).and(oi.SHIPPING_TIME.isNotNull()))));
    }

    /**
     * 获得地区用户uv
     */
    private Integer getDistrictUv(Timestamp startTime, Timestamp endTime, String provinceCode) {
        Optional<Integer> optional = db().select(countDistinct(ulr.USER_ID)).from(ulr).where(ulr.CREATE_TIME.greaterOrEqual(startTime)).and(ulr.CREATE_TIME.lessThan(endTime)).and(ulr.PROVINCE_CODE.eq(provinceCode)).fetchOptionalInto(Integer.class);
        return optional.orElse(0);
    }
}
