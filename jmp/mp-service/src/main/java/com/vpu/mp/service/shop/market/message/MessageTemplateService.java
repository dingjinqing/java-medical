package com.vpu.mp.service.shop.market.message;

import com.vpu.mp.db.main.tables.TaskJobMain;
import com.vpu.mp.db.shop.tables.MpTemplateFormId;
import com.vpu.mp.db.shop.tables.TemplateConfig;
import com.vpu.mp.db.shop.tables.records.MpTemplateFormIdRecord;
import com.vpu.mp.db.shop.tables.records.TemplateConfigRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.saas.schedule.*;
import com.vpu.mp.service.pojo.shop.market.message.MessageTemplateParam;
import com.vpu.mp.service.pojo.shop.market.message.RabbitMessageParam;
import com.vpu.mp.service.pojo.shop.market.message.SendUserVo;
import com.vpu.mp.service.pojo.shop.market.message.UserInfoQuery;
import com.vpu.mp.service.saas.schedule.TaskJobMainService;
import com.vpu.mp.service.shop.user.user.SendUserService;
import org.apache.commons.lang3.StringUtils;
import org.jooq.UpdateSetMoreStep;
import org.jooq.UpdateSetStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @Autowired
    private TaskJobMainService taskJobMainService;




    public SendUserVo getSendUsersSize(UserInfoQuery query){
        SendUserVo vo = new SendUserVo();
        String key = StringUtils.isNotBlank(query.getRedisKey())?
            query.getRedisKey():sendUserService.getKeyWithRedisBySendUser();
        vo.setUserKey(key);
        vo.setUserNumber(sendUserService.getSendUserByQuery(query,key));
        return vo;
    }

    public void insertMessageTemplate(MessageTemplateParam param){
        String userIdStr = sendUserService.getAndDeleteSendUserIdByRedisKey(param.getUserKey())
            .stream().map(Object::toString)
            .collect(Collectors.joining(","));
        String sendConditionStr = Util.toJson(param.getUserInfo());
        TemplateConfigRecord record = db().newRecord(TEMPLATE_CONFIG,param);
        record.setToUser(userIdStr);
        record.setSendCondition(sendConditionStr);
        TemplateConfigRecord templateConfigRecord =db().insertInto(TEMPLATE_CONFIG)
            .set(record)
            .returning(TEMPLATE_CONFIG.ID)
            .fetchOne();
//        RabbitMessageParam messageParam = RabbitMessageParam.builder()
//            .shopId(getShopId())
//            .messageTemplateId(templateConfigRecord.getId())
//            .userIdList(Arrays.stream(userIdStr.split(",")).map(Integer::parseInt).collect(Collectors.toList()))
//            .build();
//        createTaskJob(getShopId(), messageParam,param);
    }
    private void createTaskJob(Integer shopId,RabbitMessageParam messageTemplateParam,MessageTemplateParam param){
        TaskJobInfo  info = TaskJobInfo.initTaskJob(shopId)
            .type(param.getSenAction())
            .content(messageTemplateParam)
            .className(messageTemplateParam.getClass().getName())
            .startTime(param.getStartTime())
            .endTime(param.getEndTime())
            .executionType(TaskJobsConstant.TaskJobEnum.SEND_MESSAGE)
            .builder();
        taskJobMainService.dispatch(info);
    }

    public List<String> getNeedSendUserOpenIdArray(RabbitMessageParam messageTemplateParam){
//        MpTemplateFormIdReco
        List<Integer> userId = db().select(MP_TEMPLATE_FORM_ID.USER_ID,MP_TEMPLATE_FORM_ID.OPEN_ID)
            .from(MP_TEMPLATE_FORM_ID)
            .where(MP_TEMPLATE_FORM_ID.MP_LINK_IDENTITY.eq(messageTemplateParam.getMessageTemplateId().toString()))
            .and(MP_TEMPLATE_FORM_ID.STATUS.eq((byte)1))
            .fetchInto(Integer.class);
        List<Integer> needSendList = messageTemplateParam.getUserIdList().stream()
            .filter(x->!userId.contains(x))
            .collect(Collectors.toList());
        db().update(MP_TEMPLATE_FORM_ID)
            .set(MP_TEMPLATE_FORM_ID.USE_STATE,(byte)2)
            .where(MP_TEMPLATE_FORM_ID.USER_ID.in(needSendList))
            .returning(MP_TEMPLATE_FORM_ID.FORM_ID,MP_TEMPLATE_FORM_ID.OPEN_ID)
            .fetch();
        return null;
    }

    private List<Integer> getNeedSendUser(){
        return null;
    }
}
