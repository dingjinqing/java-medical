package com.vpu.mp.service.shop.task.wechat;

import cn.binarywang.wx.miniapp.api.WxMaAnalysisService;
import cn.binarywang.wx.miniapp.bean.analysis.*;

import com.vpu.mp.db.main.tables.records.MpAuthShopRecord;
import com.vpu.mp.db.shop.tables.records.*;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.summary.portrait.MaPortraitResult;
import com.vpu.mp.service.wechat.api.WxGetWeAnalysService;
import com.vpu.mp.service.wechat.api.WxOpenAccountService;

import me.chanjar.weixin.common.error.WxErrorException;
import org.jooq.Field;
import org.jooq.Table;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.vpu.mp.db.shop.tables.MpDailyRetain.MP_DAILY_RETAIN;
import static com.vpu.mp.db.shop.tables.MpDailyVisit.MP_DAILY_VISIT;
import static com.vpu.mp.db.shop.tables.MpDistributionVisit.MP_DISTRIBUTION_VISIT;
import static com.vpu.mp.db.shop.tables.MpMonthlyRetain.MP_MONTHLY_RETAIN;
import static com.vpu.mp.db.shop.tables.MpMonthlyVisit.MP_MONTHLY_VISIT;
import static com.vpu.mp.db.shop.tables.MpSummaryTrend.MP_SUMMARY_TREND;
import static com.vpu.mp.db.shop.tables.MpUserPortrait.MP_USER_PORTRAIT;
import static com.vpu.mp.db.shop.tables.MpVisitPage.MP_VISIT_PAGE;
import static com.vpu.mp.db.shop.tables.MpWeeklyRetain.MP_WEEKLY_RETAIN;
import static com.vpu.mp.db.shop.tables.MpWeeklyVisit.MP_WEEKLY_VISIT;


/**
 * 获取微信统计数据
 * @author: 卢光耀
 * @date: 2019-07-23 14:38
 *
*/
@Service
public class WechatTaskService extends ShopBaseService {

    Logger logger= LoggerFactory.getLogger(WechatTaskService.class);
    private static final String CONTENT = "wechat-context";
    private static final ThreadLocal<String> local = ThreadLocal.withInitial(() -> {
        LocalDate date = LocalDate.now().minusDays(1);
        DateTimeFormatter faDateTimeFormatter = DateTimeFormatter.ofPattern(DateUtil.DATE_FORMAT_SHORT);
        return date.format(faDateTimeFormatter);
    });

    private WxMaAnalysisService getServiceByShopId(Integer shopId) {
        return saas().shop.mp.getMaServiceByShopId(shopId).getAnalysisService();
    }
    
    private String getAppId(Integer shopId) {
    	return saas().shop.mp.getAppIdByShopId(shopId);
    }

    public void beginDailyTask(){
        Date date = java.sql.Date.valueOf(LocalDate.now().minusDays(1));
        WxMaAnalysisService service = getServiceByShopId(getShopId());
        WxGetWeAnalysService maService=open().getMaExtService();

        this.getDailyRetainInfo(service,date);

        this.getDailyVisitTrend(service,date);

        this.getVisitDistribution(service,date);

        this.getDailySummaryTrend(service,date);

        this.getVisitPage(service,date);

        this.getUserPortrait(maService,date);
    }

    public void beginWeeklyTask(){
        Date date = java.sql.Date.valueOf(LocalDate.now());
        WxMaAnalysisService service = getServiceByShopId(getShopId());

        this.getWeeklyRetainInfo(service,date);

        this.getWeeklyVisitTrend(service,date);

    }

    public void beginMonthlyTask(){
        Date date = java.sql.Date.valueOf(LocalDate.now());
        WxMaAnalysisService service = getServiceByShopId(getShopId());

        this.getMonthlyRetainInfo(service,date);

        this.getMonthlyVisitTrend(service,date);

    }
    /**
     * 查取用户访问情况
     * @param service
     * @param date
     */
    private void getVisitDistribution(WxMaAnalysisService service,Date date){
        try {
            WxMaVisitDistribution result = service.getVisitDistribution(date,date);
            if(validationData(result, MP_DISTRIBUTION_VISIT)){
                return ;
            }
            MpDistributionVisitRecord record = db().newRecord(MP_DISTRIBUTION_VISIT);
            record.setRefDate(result.getRefDate());
            record.setList(Util.toJson(result.getList()));
            record.insert();
        } catch (WxErrorException e) {
            logger.error(CONTENT,e);
        }
    }
    /**
     * 查取用户访问情况
     * @param service
     * @param date
     */
    private void getDailySummaryTrend(WxMaAnalysisService service,Date date){
        try {
            List<WxMaSummaryTrend> result = service.getDailySummaryTrend(date,date);
            if(validationData(result, MP_SUMMARY_TREND)){
                return ;
            }
            List<MpSummaryTrendRecord> list = new ArrayList<>(result.size());
            result.forEach(v->list.add(db().newRecord(MP_SUMMARY_TREND,v)));
            db().batchInsert(list);
        } catch (WxErrorException e) {
            logger.error(CONTENT,e);
        }
    }
    /**
     * 查取访问页面
     * @param service
     * @param date
     */
    private void getVisitPage(WxMaAnalysisService service,Date date){
        try {
            List<WxMaVisitPage> result = service.getVisitPage(date,date);
            if(validationData(result, MP_VISIT_PAGE)){
                return ;
            }
            List<MpVisitPageRecord> list = new ArrayList<>(result.size());
            result.forEach(v->list.add(db().newRecord(MP_VISIT_PAGE,v)));
            db().batchInsert(list);
        } catch (WxErrorException e) {
            logger.error(CONTENT,e);
        }
    }

    /**
     * 查取用户画像
     * @param service
     * @param date
     */
    private void getUserPortrait(WxGetWeAnalysService service,Date date){
        try {
            MaPortraitResult info = service.getUserPortrait(getAppId(getShopId()),date,date);
            if(validationData(info, MP_USER_PORTRAIT)){
                return ;
            }
            MpUserPortraitRecord record = db().newRecord(MP_USER_PORTRAIT);
            record.setRefDate(info.getRefDate());
            record.setVisitUvNew(Util.toJson(info.getVisitUvNew()));
            record.setVisitUv(Util.toJson(info.getVisitUv()));
            record.insert();
        } catch (WxErrorException e) {
            logger.error(CONTENT,e);
        }
    }

    /**
     * 查取日访问趋势
     * @param service
     * @param date
     */
    private void getDailyVisitTrend(WxMaAnalysisService service, Date date) {
        try {
            List<WxMaVisitTrend> result = service.getDailyVisitTrend(date,date);
            if(validationData(result, MP_DAILY_VISIT)){
                return;
            }
            List<MpDailyVisitRecord> list = new ArrayList<>(result.size());
            result.forEach(v->{
                MpDailyVisitRecord record = db().newRecord(MP_DAILY_VISIT,v);
                list.add(record);
            });
            db().batchInsert(list);
        } catch (WxErrorException e) {
            logger.error(CONTENT,e);
        }
    }
    /**
     * 查取周访问趋势
     * @param service
     * @param date
     */
    private void getWeeklyVisitTrend(WxMaAnalysisService service, Date date) {
        try {
            LocalDate startDate = LocalDate.parse(local.get(),
                    DateTimeFormatter.ofPattern(DateUtil.DATE_FORMAT_SHORT)).minusDays(6);
            List<WxMaVisitTrend> result = service.getWeeklyVisitTrend(
                    Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),date);
            if(validationData(result, MP_WEEKLY_VISIT)){
                return;
            }
            List<MpWeeklyVisitRecord> list = new ArrayList<>(result.size());
            result.forEach(v->{
                MpWeeklyVisitRecord record = db().newRecord(MP_WEEKLY_VISIT,v);
                list.add(record);
            });
            db().batchInsert(list);
        } catch (WxErrorException e) {
            logger.error(CONTENT,e);
        }
    }
    /**
     * 查取月访问趋势
     * @param service
     * @param date
     */
    private void getMonthlyVisitTrend(WxMaAnalysisService service, Date date) {
        try {
            LocalDate startDate = LocalDate.parse(local.get(),
                    DateTimeFormatter.ofPattern(DateUtil.DATE_FORMAT_SHORT)).withDayOfMonth(1);
            List<WxMaVisitTrend> result = service.getMonthlyVisitTrend(
                    Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),date);
            if(validationData(result, MP_MONTHLY_VISIT)){
                return;
            }
            List<MpMonthlyVisitRecord> list = new ArrayList<>(result.size());
            result.forEach(v->{
                MpMonthlyVisitRecord record = db().newRecord(MP_MONTHLY_VISIT,v);
                list.add(record);
            });
            db().batchInsert(list);
        } catch (WxErrorException e) {
            logger.error(CONTENT,e);
        }
    }

    /**
     * 查取日留存
     * @param service
     * @param date
     */
    private void getDailyRetainInfo(WxMaAnalysisService service,Date date){
        try {
            WxMaRetainInfo info = service.getDailyRetainInfo(date,date);
            if(validationData(info, MP_DAILY_RETAIN)){
                return ;
            }
            MpDailyRetainRecord record = db().newRecord(MP_DAILY_RETAIN);
            record.setRefDate(info.getRefDate());
            record.setVisitUvNew(Util.toJson(info.getVisitUvNew()));
            record.setVisitUv(Util.toJson(info.getVisitUv()));
            record.insert();
        } catch (WxErrorException e) {
            logger.error(CONTENT,e);
        }
    }
    /**
     * 查取周留存
     * @param service
     * @param date
     */
    private void getWeeklyRetainInfo(WxMaAnalysisService service,Date date){
        try {
            LocalDate startDate = LocalDate.parse(local.get(),
                    DateTimeFormatter.ofPattern(DateUtil.DATE_FORMAT_SHORT)).minusDays(6);
            WxMaRetainInfo info = service.getWeeklyRetainInfo(
                    java.sql.Date.valueOf(startDate),date);
            if(validationData(info, MP_WEEKLY_RETAIN)){
                return ;
            }
            MpWeeklyRetainRecord record = db().newRecord(MP_WEEKLY_RETAIN);
            record.setRefDate(info.getRefDate());
            record.setVisitUvNew(Util.toJson(info.getVisitUvNew()));
            record.setVisitUv(Util.toJson(info.getVisitUv()));
            record.insert();
        } catch (WxErrorException e) {
            logger.error(CONTENT,e);
        }
    }
    /**
     * 查取月留存
     * @param service
     * @param date
     */
    private void getMonthlyRetainInfo(WxMaAnalysisService service,Date date){
        try {
            LocalDate startDate = LocalDate.parse(local.get(),
                    DateTimeFormatter.ofPattern(DateUtil.DATE_FORMAT_SHORT)).withDayOfMonth(1);
            WxMaRetainInfo info = service.getWeeklyRetainInfo(
                    java.sql.Date.valueOf(startDate),date);
            if(validationData(info, MP_MONTHLY_RETAIN)){
                return ;
            }
            MpMonthlyRetainRecord record = db().newRecord(MP_MONTHLY_RETAIN);
            record.setRefDate(info.getRefDate());
            record.setVisitUvNew(Util.toJson(info.getVisitUvNew()));
            record.setVisitUv(Util.toJson(info.getVisitUv()));
            record.insert();
        } catch (WxErrorException e) {
            logger.error(CONTENT,e);
        }
    }
    private boolean validationData(Object o,Table<?> table){
        if( o == null ){
            return false;
        }
        Field<String> data = DSL.val(local.get());
        return isHavingData(table, data);
    }
    private boolean isHavingData(Table<?> table,Field<String> date){
        int count =db().selectCount().from(table).where(table.field("ref_date",String.class).eq(date)).fetchOneInto(Integer.class);
        return count > 0;
    }
}
