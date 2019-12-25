package com.vpu.mp.service.shop.task.wechat;

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

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jooq.Field;
import org.jooq.Table;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.MpDailyRetainRecord;
import com.vpu.mp.db.shop.tables.records.MpDailyVisitRecord;
import com.vpu.mp.db.shop.tables.records.MpDistributionVisitRecord;
import com.vpu.mp.db.shop.tables.records.MpMonthlyRetainRecord;
import com.vpu.mp.db.shop.tables.records.MpMonthlyVisitRecord;
import com.vpu.mp.db.shop.tables.records.MpSummaryTrendRecord;
import com.vpu.mp.db.shop.tables.records.MpUserPortraitRecord;
import com.vpu.mp.db.shop.tables.records.MpVisitPageRecord;
import com.vpu.mp.db.shop.tables.records.MpWeeklyRetainRecord;
import com.vpu.mp.db.shop.tables.records.MpWeeklyVisitRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.summary.portrait.MaPortraitResult;
import com.vpu.mp.service.wechat.api.WxGetWeAnalysService;

import cn.binarywang.wx.miniapp.api.WxMaAnalysisService;
import cn.binarywang.wx.miniapp.bean.analysis.WxMaRetainInfo;
import cn.binarywang.wx.miniapp.bean.analysis.WxMaSummaryTrend;
import cn.binarywang.wx.miniapp.bean.analysis.WxMaVisitDistribution;
import cn.binarywang.wx.miniapp.bean.analysis.WxMaVisitPage;
import cn.binarywang.wx.miniapp.bean.analysis.WxMaVisitTrend;
import me.chanjar.weixin.common.error.WxErrorException;


/**
 * 获取微信统计数据
 * @author: 卢光耀
 * @date: 2019-07-23 14:38
 *
*/
@Service
public class WechatTaskService extends ShopBaseService {

    Logger logger= LoggerFactory.getLogger(WechatTaskService.class);
	private static final byte ZERO = 0;
	private static final byte ONE = 1;
	private static final byte TWO = 2;
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

        this.getUserPortrait(maService);
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
    private void getUserPortrait(WxGetWeAnalysService service){
    	//昨天日期
    	Date endDate = extractedDate(-1);
    	//昨天
    	logger().info("昨天");
    	Date beginDate = extractedDate(-1);
    	recordManage(service, beginDate, endDate,ZERO);
    	//7天前
    	logger().info("7天");
    	beginDate = extractedDate(-7);
    	recordManage(service, beginDate, endDate,ONE);
    	//30天前
    	logger().info("30天");
    	beginDate = extractedDate(-30);
    	recordManage(service, beginDate, endDate,TWO);
    }

	private void recordManage(WxGetWeAnalysService service, Date beginDate, Date endDate,Byte type) {
		try {
            MaPortraitResult info = service.getUserPortrait(getAppId(getShopId()),beginDate,endDate);
            MpUserPortraitRecord record = db().selectFrom(MP_USER_PORTRAIT).where(MP_USER_PORTRAIT.REF_DATE.eq(info.getRefDate())).fetchAny();
            
            if(record!=null) {
            	logger().info("更新");
            	record=assignment(type, info, record);
            	record.update();
            }else {
            	logger().info("插入");
            	record = db().newRecord(MP_USER_PORTRAIT);
            	record = assignment(type, info, record);
            	record.insert();            	
            }
        } catch (WxErrorException e) {
            logger.error(CONTENT,e);
        }
	}
	/**
	 * 赋值
	 * @param type
	 * @param info
	 * @param record
	 * @return
	 */
	private MpUserPortraitRecord assignment(Byte type, MaPortraitResult info, MpUserPortraitRecord record) {
		record.setRefDate(info.getRefDate());
		record.setVisitUvNew(Util.toJson(info.getVisitUvNew()));
		record.setVisitUv(Util.toJson(info.getVisitUv()));
		record.setType(type);
		String refDate = info.getRefDate();
		String date = refDate.substring(0,8);
		Timestamp startTime = extracted(date);
		record.setStartTime(startTime);
		return  record;
	}
	
	/**
	 * startTime日期处理
	 * @param date
	 * @return
	 */
	private Timestamp extracted(String date) {
		LocalDate ld=LocalDate.now();
		DateTimeFormatter  dtf2=DateTimeFormatter.ofPattern("yyyyMMdd");
		LocalDate date2=ld.parse(date,dtf2);
		LocalDateTime localDateTime=LocalDateTime.of(date2, java.time.LocalTime.MIN);
		Timestamp startTime = Timestamp.valueOf(localDateTime);
		return startTime;
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
    
    /**
     * 获取对应的日期
     * @param num
     * @return
     */
	private Date extractedDate(Integer num) {
		LocalDateTime localDateTime =LocalDateTime.now().plus(num,ChronoUnit.DAYS);
		ZoneId zone = ZoneId.systemDefault();
		Instant instant = localDateTime.atZone(zone).toInstant();
		Date from = Date.from(instant);
		return from;
	}
}
