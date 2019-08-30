package com.vpu.mp.service.shop.market.message;

import com.vpu.mp.db.main.tables.TaskJobMain;
import com.vpu.mp.db.shop.tables.MpTemplateFormId;
import com.vpu.mp.db.shop.tables.TemplateConfig;
import com.vpu.mp.db.shop.tables.records.MpTemplateFormIdRecord;
import com.vpu.mp.db.shop.tables.records.TemplateConfigRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.saas.schedule.*;
import com.vpu.mp.service.pojo.shop.market.message.MessageTemplateParam;
import com.vpu.mp.service.pojo.shop.market.message.RabbitMessageParam;
import com.vpu.mp.service.pojo.shop.market.message.SendUserVo;
import com.vpu.mp.service.pojo.shop.market.message.UserInfoQuery;
import com.vpu.mp.service.pojo.shop.official.message.MpTemplateConfig;
import com.vpu.mp.service.pojo.shop.official.message.MpTemplateData;
import com.vpu.mp.service.pojo.shop.user.message.MaTemplateConfig;
import com.vpu.mp.service.pojo.shop.user.message.MaTemplateData;
import com.vpu.mp.service.saas.schedule.TaskJobMainService;
import com.vpu.mp.service.saas.shop.official.message.MpOfficialAccountMessageService;
import com.vpu.mp.service.shop.user.user.SendUserService;
import org.apache.commons.lang3.StringUtils;
import org.jooq.UpdateSetMoreStep;
import org.jooq.UpdateSetStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.tables.MpTemplateFormId.MP_TEMPLATE_FORM_ID;
import static com.vpu.mp.db.shop.tables.TemplateConfig.TEMPLATE_CONFIG;

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
        String key = StringUtils.isNotBlank(query.getRedisKey())?
            query.getRedisKey():sendUserService.getKeyWithRedisBySendUser();
        vo.setUserKey(key);
        vo.setUserNumber(sendUserService.getSendUserByQuery(query,key));
        return vo;
    }

    public void insertMessageTemplate(MessageTemplateParam param){
//        String userIdStr = sendUserService.getAndDeleteSendUserIdByRedisKey(param.getUserKey())
//            .stream().map(Object::toString)
//            .collect(Collectors.joining(","));
        Integer shopId = getShopId();
        String userIdStr = "12,";
        String sendConditionStr = Util.toJson(param.getUserInfo());
        TemplateConfigRecord record = db().newRecord(TEMPLATE_CONFIG,param);
        record.setToUser(userIdStr);
        record.setSendCondition(sendConditionStr);
        TemplateConfigRecord templateConfigRecord =db().insertInto(TEMPLATE_CONFIG)
            .set(record)
            .returning(TEMPLATE_CONFIG.ID,TEMPLATE_CONFIG.PAGE_LINK,TEMPLATE_CONFIG.TITLE,TEMPLATE_CONFIG.CONTENT)
            .fetchOne();
        createTaskJob(shopId, assemblyRabbitMessageParam(templateConfigRecord,userIdStr,shopId),param);
    }

    /**
     * 封装MQ传参
     * @param templateConfigRecord 消息推送的相关信息
     * @param userIdStr 接收人
     * @param shopId 门店ID
     * @return {@link RabbitMessageParam}
     */
    private RabbitMessageParam assemblyRabbitMessageParam(TemplateConfigRecord templateConfigRecord,String userIdStr,Integer shopId ){
        return RabbitMessageParam.builder()
            .shopId(shopId)
            .page(templateConfigRecord.getPageLink())
            .messageTemplateId(templateConfigRecord.getId())
            .userIdList(Arrays.stream(userIdStr.split(",")).map(Integer::parseInt).collect(Collectors.toList()))
            .maTemplateData(MaTemplateData.builder()
                .config(MaTemplateConfig.ACTIVITY_CONFIG)
                .data(new String[][]{
                    {templateConfigRecord.getTitle()},
                    {templateConfigRecord.getContent()}
                })
                .build())
            .mpTemplateData(MpTemplateData.builder()
                .config(MpTemplateConfig.ACTIVITY_CONFIG)
                .data(new String[][]{
                    {""},
                    {templateConfigRecord.getTitle()},
                    {templateConfigRecord.getContent()},
                    {DateUtil.getLocalDateTime().toString()},
                    {"点击查看详情"}
                })
                .build())
            .build();
    }

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

}
