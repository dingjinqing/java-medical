package com.vpu.mp.service.saas.schedule;

import com.vpu.mp.db.main.tables.records.TaskJobContentRecord;
import com.vpu.mp.db.main.tables.records.TaskJobMainRecord;
import com.vpu.mp.service.foundation.mq.RabbitmqSendService;
import com.vpu.mp.service.foundation.service.MainBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.saas.schedule.BaseTaskJob;
import com.vpu.mp.service.pojo.saas.schedule.TaskJobInfo;
import com.vpu.mp.service.pojo.saas.schedule.TaskJobsConstant;
import org.jooq.Condition;
import org.jooq.Record2;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.vpu.mp.db.main.tables.TaskJobContent.TASK_JOB_CONTENT;
import static com.vpu.mp.db.main.tables.TaskJobMain.TASK_JOB_MAIN;


/**
 * 定时任务实现的部分逻辑
 * @author 卢光耀
 * @date 2019-08-13 14:13
 *
*/
@Service
public class TaskJobMainService extends MainBaseService {

    @Autowired
    private RabbitmqSendService rabbitmqSendService;

    /**
     * 通用定时任务job处理（BaseTaskJob 自己定义）
     * @param job
     */
    public void dispatch(BaseTaskJob job) {
        db().transaction(configuration->{
            TaskJobMainRecord mainRecord= DSL.using(configuration).newRecord(TASK_JOB_MAIN,job);
            TaskJobContentRecord record = DSL.using(configuration).insertInto(TASK_JOB_CONTENT)
                .set(TASK_JOB_CONTENT.CONTENT,job.getContent())
                .returning(TASK_JOB_CONTENT.ID)
                .fetchOne();
            mainRecord.setContentId(record.getId());
            if( job.getType().equals(TaskJobsConstant.TYPE_ONCE) ){
                TaskJobsConstant.TaskJobEnum jobEnum = TaskJobsConstant.TaskJobEnum
                    .getTaskJobEnumByExecutionType(job.getExecutionType());
                mainRecord.setType(TaskJobsConstant.STATUS_EXECUTING);
                rabbitmqSendService.sendMessage(jobEnum.getExchangeName(),
                    jobEnum.getRoutingKey(),job.getContent(),job.getClassName());
            }
            mainRecord.insert();

        });
    }

    /**
     *
     * @param param MQ传参类
     * @param className MQ传参类全称class.getName()
     * @param shopId 门店id
     * @param executionType 指定传输路由{@link TaskJobsConstant.TaskJobEnum}
     */
    public void dispatchImmediately(Object param,String className,Integer shopId,Integer executionType){
        TaskJobInfo  info = TaskJobInfo.builder(shopId)
            .type(TaskJobsConstant.TYPE_ONCE)
            .content(param)
            .className(className)
            .startTime(DateUtil.getLocalDateTime())
            .endTime(DateUtil.getLocalDateTime())
            .executionType(TaskJobsConstant.TaskJobEnum
                .getTaskJobEnumByExecutionType(executionType))
            .builder();
        dispatch(info);
    }

    public void  getAndSendMessage(TaskJobsConstant.TaskJobEnum job){
        List<Condition> filters = new ArrayList<>();
        filters.add(TASK_JOB_MAIN.STATUS.eq(TaskJobsConstant.STATUS_NEW));
        filters.add(TASK_JOB_MAIN.NEXT_EXECUTE_TIME.lessOrEqual(DateUtil.getLocalDateTime()));
        filters.add(TASK_JOB_MAIN.EXECUTION_TYPE.eq(job.getExecutionType()));
        sendMessage(getNeedMessage(filters),job);
    }
    private Result<TaskJobMainRecord>  getNeedMessage(List<Condition> conditionList){
        return db().select(TASK_JOB_MAIN.CONTENT_ID,TASK_JOB_MAIN.SHOP_ID)
            .from(TASK_JOB_MAIN)
            .where(conditionList)
            .fetchInto(TASK_JOB_MAIN);
    }
    private void sendMessage(Result<TaskJobMainRecord> result,TaskJobsConstant.TaskJobEnum job){
        result.forEach(r->
            rabbitmqSendService.sendMessage(
                job.getExchangeName(),job.getRoutingKey(),r.get(TASK_JOB_MAIN.SHOP_ID),r.get(TASK_JOB_MAIN.CONTENT_ID)
            )
        );
    }

}
