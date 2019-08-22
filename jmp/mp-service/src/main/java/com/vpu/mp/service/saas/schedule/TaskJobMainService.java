package com.vpu.mp.service.saas.schedule;

import com.vpu.mp.db.main.tables.records.TaskJobContentRecord;
import com.vpu.mp.db.main.tables.records.TaskJobMainRecord;
import com.vpu.mp.service.foundation.mq.RabbitmqSendService;
import com.vpu.mp.service.foundation.service.MainBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.saas.schedule.BaseTaskJob;
import com.vpu.mp.service.pojo.saas.schedule.TaskJobInfo;
import com.vpu.mp.service.pojo.saas.schedule.TaskJobsConstant;
import org.jooq.Record2;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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



}
