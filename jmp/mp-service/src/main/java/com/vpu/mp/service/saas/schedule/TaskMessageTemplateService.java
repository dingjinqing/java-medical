package com.vpu.mp.service.saas.schedule;

import static com.vpu.mp.db.main.tables.TaskJobContent.TASK_JOB_CONTENT;
import static com.vpu.mp.db.main.tables.TaskJobMain.TASK_JOB_MAIN;

import org.jooq.Record2;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.common.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.mq.RabbitmqSendService;
import com.vpu.mp.service.foundation.service.MainBaseService;
import com.vpu.mp.service.pojo.saas.schedule.TaskJobsConstant;

/**
 * 消息发送TaskService
 * @author 卢光耀
 * @date 2019-08-20 15:43
 *
*/
@Service
public class TaskMessageTemplateService extends MainBaseService {
    @Autowired
    private RabbitmqSendService rabbitmqSendService;

    public Result<Record2<String,Integer>> getNeedMessage(Integer executionType){
        return db().select(TASK_JOB_CONTENT.CONTENT,TASK_JOB_MAIN.SHOP_ID)
            .from(TASK_JOB_MAIN)
            .leftJoin(TASK_JOB_CONTENT).on(TASK_JOB_MAIN.CONTENT_ID.eq(TASK_JOB_CONTENT.ID))
            .where(TASK_JOB_MAIN.EXECUTION_TYPE.eq(executionType))
            .and(TASK_JOB_MAIN.NEXT_EXECUTE_TIME.lessThan(DateUtil.getLocalDateTime()))
            .fetch();
    }
    public void sendMessage(Result<Record2<String,Integer>> result, TaskJobsConstant.TaskJobEnum job){
        result.forEach(r->
            rabbitmqSendService.sendMessage(
                job.getExchangeName(),job.getRoutingKey(),r.get(TASK_JOB_MAIN.SHOP_ID),r.get(TASK_JOB_CONTENT.CONTENT)
            )
        );
    }
}
