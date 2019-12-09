package com.vpu.mp.service.shop.market.message;


import static com.vpu.mp.db.shop.tables.MessageTemplate.MESSAGE_TEMPLATE;
import static com.vpu.mp.db.shop.tables.ServiceMessageRecord.SERVICE_MESSAGE_RECORD;
import static com.vpu.mp.db.shop.tables.TemplateConfig.TEMPLATE_CONFIG;
import static com.vpu.mp.db.shop.tables.User.USER;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.Record3;
import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.TemplateConfigRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.MathUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.saas.schedule.TaskJobInfo;
import com.vpu.mp.service.pojo.saas.schedule.TaskJobsConstant;
import com.vpu.mp.service.pojo.shop.coupon.give.CouponGiveQueueParam;
import com.vpu.mp.service.pojo.shop.market.message.MessageOutputVo;
import com.vpu.mp.service.pojo.shop.market.message.MessageStatisticsVo;
import com.vpu.mp.service.pojo.shop.market.message.MessageTemplateDetailVo;
import com.vpu.mp.service.pojo.shop.market.message.MessageTemplateParam;
import com.vpu.mp.service.pojo.shop.market.message.MessageTemplateQuery;
import com.vpu.mp.service.pojo.shop.market.message.MessageTemplateVo;
import com.vpu.mp.service.pojo.shop.market.message.MessageUserQuery;
import com.vpu.mp.service.pojo.shop.market.message.RabbitMessageParam;
import com.vpu.mp.service.pojo.shop.market.message.RabbitParamConstant;
import com.vpu.mp.service.pojo.shop.market.message.SendUserVo;
import com.vpu.mp.service.pojo.shop.market.message.UserInfoByRedis;
import com.vpu.mp.service.pojo.shop.market.message.UserInfoQuery;
import com.vpu.mp.service.pojo.shop.market.message.UserInfoVo;
import com.vpu.mp.service.pojo.shop.market.message.content.ContentMessageParam;
import com.vpu.mp.service.pojo.shop.market.message.content.ContentMessageVo;
import com.vpu.mp.service.pojo.shop.official.message.MpTemplateConfig;
import com.vpu.mp.service.pojo.shop.official.message.MpTemplateData;
import com.vpu.mp.service.pojo.shop.user.message.MaTemplateConfig;
import com.vpu.mp.service.pojo.shop.user.message.MaTemplateData;
import com.vpu.mp.service.saas.schedule.TaskJobMainService;
import com.vpu.mp.service.shop.user.user.SendUserService;

/**
 * 营销管理--推送消息实现类
 * @author 卢光耀
 * @date 2019-08-12 14:19
 *
*/
@Service
public class MessageTemplateService extends ShopBaseService {

    @Autowired
    private SendUserService sendUserService;

    private TaskJobMainService taskJobMainService;


    @PostConstruct
    private void init(){
        taskJobMainService = saas().taskJobMainService;
    }




    public SendUserVo getSendUsersSize(UserInfoQuery query){
        SendUserVo vo = new SendUserVo();
        String key = StringUtils.isNotBlank(query.getUserKey())?
            query.getUserKey():sendUserService.getKeyWithRedisBySendUser();
        vo.setUserKey(key);
        vo.setUserNumber(sendUserService.getSendUserByQuery(query,key));
        return vo;
    }


    public PageResult<UserInfoVo> getUserVoPage(MessageUserQuery query){
        List<UserInfoByRedis> list = sendUserService.getSendUserInfoByRedisKey(query.getUserKey());
        Map<Integer,UserInfoByRedis> map = list
            .stream()
            .collect(Collectors.toMap(UserInfoByRedis::getUserId,x->x));
        List<Integer> userIds = list.stream().map(UserInfoByRedis::getUserId).collect(Collectors.toList());
        PageResult<UserInfoVo> resultPage = sendUserService.getUserInfoByIds(userIds,query);
        assemblyUserPage(map,resultPage);
        return resultPage;
    }
    public void updateClickStatus(MessageUserQuery query){

        List<UserInfoByRedis> list = sendUserService.getSendUserInfoByRedisKey(query.getUserKey());
        List<UserInfoByRedis> newList = new ArrayList<>(list.size());
        Map<Integer,UserInfoByRedis> map = list
            .stream()
            .collect(Collectors.toMap(UserInfoByRedis::getUserId,x->x));
        query.getUserIds().stream().forEach(x->{
            UserInfoByRedis r = map.get(x);
            if (r.getIsChecked()) {
                r.setIsChecked(Boolean.FALSE);
            } else {
                r.setIsChecked(Boolean.TRUE);
            }
            map.put(x,r);
        });
        for(Map.Entry<Integer,UserInfoByRedis> entry:map.entrySet()){
            newList.add(entry.getValue());
        }
        sendUserService.setUserToJedis(query.getUserKey(),newList);
    }

    private void assemblyUserPage(Map<Integer,UserInfoByRedis> map,PageResult<UserInfoVo> page){
        for( UserInfoVo vo:page.getDataList() ){
            UserInfoByRedis r = map.get(vo.getUserId());
            vo.setIsChecked(r.getIsChecked());
        }
    }

    public void insertMessageTemplate(MessageTemplateParam param){
        String userIdStr = sendUserService.getAndDeleteSendUserIdByRedisKey(param.getUserKey())
            .stream()
            .map(UserInfoByRedis::getUserId)
            .map(Objects::toString)
            .collect(Collectors.joining(","));
        Integer shopId = getShopId();
//        String userIdStr = "12,";
        String sendConditionStr = Util.toJson(param.getUserInfo());
        TemplateConfigRecord record = db().newRecord(TEMPLATE_CONFIG,param);
        record.setToUser(userIdStr);
        record.setSendCondition(sendConditionStr);
        TemplateConfigRecord templateConfigRecord =db().insertInto(TEMPLATE_CONFIG)
            .set(record)
            .returning(TEMPLATE_CONFIG.ID,TEMPLATE_CONFIG.PAGE_LINK,TEMPLATE_CONFIG.TITLE,TEMPLATE_CONFIG.CONTENT)
            .fetchOne();
        //TODO 需要改
       // createTaskJob(shopId, assemblyRabbitMessageParam(templateConfigRecord,userIdStr,shopId),param);
    }

    /**
     * 封装MQ传参
     * @param templateConfigRecord 消息推送的相关信息
     * @param userIdStr 接收人
     * @param shopId 门店ID
     * @return {@link RabbitMessageParam}
     */
//    private RabbitMessageParam assemblyRabbitMessageParam(TemplateConfigRecord templateConfigRecord,String userIdStr,Integer shopId ){
//        return RabbitMessageParam.builder()
//            .shopId(shopId)
//            .type(RabbitParamConstant.Type.GENERAL_TYPE)
//            .page(templateConfigRecord.getPageLink())
//            .messageTemplateId(templateConfigRecord.getId())
//            .userIdList(Arrays.stream(userIdStr.split(",")).map(Integer::parseInt).collect(Collectors.toList()))
//            .maTemplateData(MaTemplateData.builder()
//                .config(MaTemplateConfig.ACTIVITY_CONFIG)
//                .data(new String[][]{
//                    {templateConfigRecord.getTitle()},
//                    {templateConfigRecord.getContent()}
//                })
//                .build())
//            .mpTemplateData(MpTemplateData.builder()
//                .config(MpTemplateConfig.ACTIVITY_CONFIG)
//                .data(new String[][]{
//                    {""},
//                    {templateConfigRecord.getTitle()},
//                    {templateConfigRecord.getContent()},
//                    {DateUtil.getLocalDateTime().toString()},
//                    {"点击查看详情"}
//                })
//                .build())
//            .build();
//    }

    /**
     * 创建TaskJob
     * @param shopId 门店ID
     * @param messageTemplateParam 消息内容
     * @param param 消息的一些配置参数
     */
    private void createTaskJob(Integer shopId,RabbitMessageParam messageTemplateParam,MessageTemplateParam param){
        TaskJobInfo  info = TaskJobInfo.builder(shopId)
            .type(param.getSenAction())
            .content(messageTemplateParam)
            .className(messageTemplateParam.getClass().getName())
            .startTime(param.getStartTime())
            .endTime(param.getEndTime())
            .executionType(TaskJobsConstant.TaskJobEnum.SEND_MESSAGE)
            .builder();
        taskJobMainService.dispatch(info);
    }

    public PageResult<MessageTemplateVo> getPageByParam(MessageTemplateQuery param) {
        PageResult<MessageTemplateVo> resultPage = new PageResult<>();
        SelectConditionStep<Record> select  = db().select()
            .from(TEMPLATE_CONFIG)
            .where(buildParams(param))
            .and(TEMPLATE_CONFIG.DEL_FLAG.eq((byte)0));
        PageResult<TemplateConfigRecord> templatePage = getPageResult(select,param.getCurrentPage(),param.getPageRows(),TemplateConfigRecord.class);
        BeanUtils.copyProperties(templatePage,resultPage);
        return buildPageVo(resultPage,templatePage);
    }

    /**
     * vo转换
     * @param resultPage voPage
     * @param templatePage  sourcePage
     * @return voPage
     */
    private PageResult<MessageTemplateVo> buildPageVo(PageResult<MessageTemplateVo> resultPage,PageResult<TemplateConfigRecord> templatePage){
        List<TemplateConfigRecord> templateList = templatePage.getDataList();
        List<Integer> templateIdList = templateList.stream().map(TemplateConfigRecord::getId).collect(Collectors.toList());
        Map<String,Integer> sendMap = getSentPersonByTemplateId(templateIdList);
        Map<String,Integer> visitMap = getVisitedPersonByTemplateId(templateIdList);
        List<MessageTemplateVo> resultVoList = new ArrayList<>();
        for(TemplateConfigRecord record : templateList  ){
            MessageTemplateVo vo = new MessageTemplateVo();
            int sentNumber = sendMap.getOrDefault(record.getId(), 0);
            int visitNumber = visitMap.getOrDefault(record.getId(), 0);
            BeanUtils.copyProperties(record,vo);
            vo.setSentNumber(sentNumber);
            vo.setClickedNumber(visitNumber);
            if( sendMap.containsKey(record.getId()) ){
                vo.setPercentage(MathUtil.deciMal(visitNumber,sentNumber)*100);
            }else{
                vo.setPercentage(0D);
            }

            resultVoList.add(vo);
        }
        resultPage.setDataList(resultVoList);
        return resultPage;
    }

    /**
     * 根据推送消息id获取已发送人数
     * @param templateIdList 推送消息id集合
     * @return 推送消息id和对应的发送人数
     */
    private Map<String,Integer> getSentPersonByTemplateId(List<Integer> templateIdList){
        return db()
            .select(SERVICE_MESSAGE_RECORD.LINK_IDENTITY, DSL.count(SERVICE_MESSAGE_RECORD.LINK_IDENTITY).as("number"),SERVICE_MESSAGE_RECORD.CREATE_TIME)
            .from(SERVICE_MESSAGE_RECORD)
            .where(SERVICE_MESSAGE_RECORD.LINK_IDENTITY.in(templateIdList))
            .groupBy(SERVICE_MESSAGE_RECORD.LINK_IDENTITY,SERVICE_MESSAGE_RECORD.CREATE_TIME)
            .orderBy(SERVICE_MESSAGE_RECORD.CREATE_TIME.desc())
            .fetch()
            .stream()
            .collect(Collectors.toMap(x->x.get(SERVICE_MESSAGE_RECORD.LINK_IDENTITY),x->Integer.parseInt(x.get("number").toString())));
    }

    /**
     * 根据推送消息id获取对应的访问人数
     * @param templateIdList 推送消息id集合
     * @return 推送消息id和对应的访问人数
     */
    private Map<String,Integer> getVisitedPersonByTemplateId( List<Integer> templateIdList ){
        return db()
            .select(SERVICE_MESSAGE_RECORD.LINK_IDENTITY, DSL.count(SERVICE_MESSAGE_RECORD.LINK_IDENTITY).as("number"))
            .from(SERVICE_MESSAGE_RECORD)
            .where(SERVICE_MESSAGE_RECORD.LINK_IDENTITY.in(templateIdList))
            .and(SERVICE_MESSAGE_RECORD.IS_VISIT.eq((byte)1))
            .groupBy(SERVICE_MESSAGE_RECORD.LINK_IDENTITY)
            .fetch()
            .stream()
            .collect(Collectors.toMap(x->x.get(SERVICE_MESSAGE_RECORD.LINK_IDENTITY),x->Integer.parseInt(x.get("number").toString())));
    }
    private List<Condition> buildParams(MessageTemplateQuery param){
        List<Condition> result = new ArrayList<>();
        if( StringUtils.isNotBlank(param.getMessageName()) ){
            result.add(TEMPLATE_CONFIG.NAME.contains(param.getMessageName()));
        }
        if( StringUtils.isNotBlank(param.getBusinessTitle()) ){
            result.add(TEMPLATE_CONFIG.TITLE.contains(param.getBusinessTitle()));
        }
        if( param.getStartTime() != null ){
            result.add(TEMPLATE_CONFIG.START_TIME.greaterOrEqual(param.getStartTime() ));
        }
        if( param.getEndTime() != null  ){
            result.add(TEMPLATE_CONFIG.END_TIME.lessOrEqual(param.getEndTime()));
        }
        if( param.getTemplateId() != null){
            result.add(SERVICE_MESSAGE_RECORD.LINK_IDENTITY.eq(param.getTemplateId().toString()));
        }
        if( StringUtils.isNotBlank(param.getUserName()) ){
            result.add(USER.USERNAME.contains(param.getUserName()));
        }
        return result;
    }

    public void deleteById(Integer id) {
        db().update(TEMPLATE_CONFIG)
            .set(TEMPLATE_CONFIG.DEL_FLAG,(byte)1)
            .set(TEMPLATE_CONFIG.DEL_TIME,DateUtil.getLocalDateTime())
            .where(TEMPLATE_CONFIG.ID.eq(id))
            .execute();
    }

    public MessageTemplateDetailVo getMessageDetail(Integer id) {
        MessageTemplateDetailVo vo = new MessageTemplateDetailVo();
        TemplateConfigRecord record = getById(id);
        UserInfoQuery userInfo = Util.parseJson(record.getSendCondition(),UserInfoQuery.class);
        BeanUtils.copyProperties(record,vo);
        vo.setUserInfo(userInfo);
        return vo;
    }
    private TemplateConfigRecord getById(Integer id){
        return db().selectFrom(TEMPLATE_CONFIG)
            .where(TEMPLATE_CONFIG.ID.eq(id))
            .fetchAny();
    }
    public PageResult<MessageOutputVo> getSendRecord(MessageTemplateQuery query){
        SelectConditionStep<Record> select  = db().select(SERVICE_MESSAGE_RECORD.fields()).from(SERVICE_MESSAGE_RECORD)
            .leftJoin(USER).on(USER.USER_ID.eq(SERVICE_MESSAGE_RECORD.USER_ID))
            .where(buildParams(query));
        return getPageResult(select,query.getCurrentPage(),MessageOutputVo.class);
    }

    public MessageStatisticsVo queryStatisticsData(MessageTemplateQuery query){
        MessageStatisticsVo vo = new MessageStatisticsVo();
        Integer allSendNum = 0,allSentNum = 0,allVisitNum = 0;
        List<MessageStatisticsVo.StatisticsByDay> allStatistics = new ArrayList<>();
        List<LocalDate> allDate = DateUtil
            .getAllDatesBetweenTwoDates(query.getStartTime().toLocalDateTime().toLocalDate(),query.getEndTime().toLocalDateTime().toLocalDate());
        Map<String,Integer> messageMap = db()
            .select(dateFormat(TEMPLATE_CONFIG.START_TIME,"%Y-%m-%d").as("everyDate"),DSL.count().as("numbers"))
            .from(TEMPLATE_CONFIG)
            .where(TEMPLATE_CONFIG.START_TIME.lessThan(query.getEndTime()))
            .and(TEMPLATE_CONFIG.START_TIME.greaterThan(query.getStartTime()))
            .groupBy(DSL.field("everyDate"))
            .fetch()
            .stream()
            .collect(Collectors.toMap(x->x.get("everyDate").toString(),x->Integer.parseInt(x.get("numbers").toString())));
        Result<Record3<String,Byte,Byte>> serviceMessageResult = db()
            .select(
                dateFormat(SERVICE_MESSAGE_RECORD.CREATE_TIME,"%Y-%m-%d").as("everyDate"),
                SERVICE_MESSAGE_RECORD.SEND_STATUS,
                SERVICE_MESSAGE_RECORD.IS_VISIT)
            .from(SERVICE_MESSAGE_RECORD)
            .where(SERVICE_MESSAGE_RECORD.CREATE_TIME.lessThan(query.getEndTime()))
            .and(SERVICE_MESSAGE_RECORD.CREATE_TIME.greaterThan(query.getStartTime()))
            .fetch();
        Map<String,Integer> sentMap = serviceMessageResult.stream()
            .filter(x->x.get(SERVICE_MESSAGE_RECORD.SEND_STATUS).equals((byte)1))
            .collect(Collectors.toMap(x->x.get("everyDate").toString(),x->{
                int i = 0;
                return i++;
            }));
        Map<String,Integer> visitMap = serviceMessageResult.stream()
            .filter(x->x.get(SERVICE_MESSAGE_RECORD.IS_VISIT).equals((byte)1))
            .collect(Collectors.toMap(x->x.get("everyDate").toString(),x->{
                int i = 0;
                return i++;
            }));

        for(LocalDate date: allDate  ){
            String localDate = date.toString();
            Integer sentNum = sentMap.getOrDefault(localDate,0);
            Integer msgNum = messageMap.getOrDefault(localDate,0);
            Integer visitNum = visitMap.getOrDefault(localDate,0);
            allSendNum+=msgNum;
            allSentNum+=sentNum;
            allVisitNum+=visitNum;
            MessageStatisticsVo.StatisticsByDay day = vo.new StatisticsByDay();
            day.setDate(date);
            day.setSendNumber(msgNum);
            day.setSendSuccessNumber(sentNum);
            day.setVisitNumber(visitNum);
            if( sentNum != 0){
                day.setVisitPercentage(MathUtil.deciMal(visitNum,sentNum));
            }else{
                if( visitNum != 0 ){
                    day.setVisitPercentage(100.00);
                }else{
                    day.setVisitPercentage(0.00);
                }
            }
            allStatistics.add(day);
        }
        vo.setAllStatistics(allStatistics);
        vo.setSendNumber(allSendNum);
        vo.setSendSuccessNumber(allSentNum);
        vo.setVisitNumber(allVisitNum);
        if( allSentNum != 0){
            vo.setVisitPercentage(MathUtil.deciMal(allVisitNum,allSentNum));
        }else{
            if( allVisitNum != 0 ){
                vo.setVisitPercentage(100.00);
            }else{
                vo.setVisitPercentage(0.00);
            }
        }
        return vo;
    }

    public List<ContentMessageVo> getContentTemplate(ContentMessageParam param) {
        return db().select(MESSAGE_TEMPLATE.CONTENT,MESSAGE_TEMPLATE.ID)
            .from(MESSAGE_TEMPLATE)
            .where(MESSAGE_TEMPLATE.ACTION.eq(param.getAction()))
            .fetchInto(ContentMessageVo.class);
    }
    public void addContentTemplate(ContentMessageParam param) {
        db().newRecord(MESSAGE_TEMPLATE,param).insert();
    }
    /**
     * 创建定向发券TaskJob
     * @param shopId 门店ID
     * @param messageTemplateParam 消息内容
     * @param param 消息的一些配置参数
     */
    public void createCouponTaskJob(Integer shopId,CouponGiveQueueParam couponGiveQueueParam,Timestamp startTime){
        TaskJobInfo  info = TaskJobInfo.builder(shopId)
            .type(TaskJobsConstant.EXECUTION_TIMING)
            .content(couponGiveQueueParam)
            .className(CouponGiveQueueParam.class.getName())
            .startTime(startTime)
            .executionType(TaskJobsConstant.TaskJobEnum.GIVE_COUPON)
            .builder();
        taskJobMainService.dispatch(info);
    }
}
