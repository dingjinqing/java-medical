package com.vpu.mp.service.saas.schedule;

import com.vpu.mp.db.main.tables.TaskJobContent;
import com.vpu.mp.db.main.tables.TaskJobMain;
import com.vpu.mp.db.main.tables.records.TaskJobContentRecord;
import com.vpu.mp.db.main.tables.records.TaskJobMainRecord;
import com.vpu.mp.service.foundation.jedis.JedisKeyConstant;
import com.vpu.mp.service.foundation.jedis.JedisManager;
import com.vpu.mp.service.foundation.mq.RabbitmqSendService;
import com.vpu.mp.service.foundation.service.MainBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.MathUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.saas.schedule.BaseTaskJob;
import com.vpu.mp.service.pojo.saas.schedule.TaskJobInfo;
import com.vpu.mp.service.pojo.saas.schedule.TaskJobsConstant;
import org.jooq.*;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Autowired
    private JedisManager jedisManager;

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
            }
            TaskJobMainRecord idRecord = DSL.using(configuration)
                .insertInto(TASK_JOB_MAIN)
                .set(mainRecord).returning(TASK_JOB_MAIN.ID)
                .fetchOne();
            if( job.getType().equals(TaskJobsConstant.TYPE_ONCE) ){
                TaskJobsConstant.TaskJobEnum jobEnum = TaskJobsConstant.TaskJobEnum
                    .getTaskJobEnumByExecutionType(job.getExecutionType());
                rabbitmqSendService.sendMessage(jobEnum.getExchangeName(),
                    jobEnum.getRoutingKey(),setTaskJobId(job.getContent(),job.getClassName(),idRecord.getId()),job.getClassName());
            }
        });
    }
    private String setTaskJobId(String jsonStr,String clzName,Integer jobId){
        try {
            if(  !jsonStr.contains("taskJobId")&&Class.forName(clzName).getMethod("setTaskJobId",Integer.class)!= null ){
                String jobIdStr = "\"taskJobId\":"+jobId+",";
                return  jsonStr.replaceFirst("\\[\\{","[{"+jobIdStr);
            }else{
                return jsonStr;
            }
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            return jsonStr;
        }
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
            .executionType(Objects.requireNonNull(TaskJobsConstant.TaskJobEnum
                .getTaskJobEnumByExecutionType(executionType)))
            .builder();
        dispatch(info);
    }

    /**
     * 查询并发送待执行的任务给各自的队列（加入排他锁，防重入）
     */
    public  void  getAndSendMessage(){
        String uuid = Util.randomId();
        if( jedisManager.addLock(JedisKeyConstant.TASK_JOB_LOCK,uuid,60*60) ){
            db().transaction(configuration -> {

                Result<Record4<Integer,String,String,Integer>> result = DSL.using(configuration)
                    .select(TASK_JOB_MAIN.ID,TASK_JOB_CONTENT.CONTENT,TASK_JOB_MAIN.CLASS_NAME,TASK_JOB_MAIN.EXECUTION_TYPE)
                    .from(TASK_JOB_MAIN)
                    .leftJoin(TASK_JOB_CONTENT).on(TASK_JOB_CONTENT.ID.eq(TASK_JOB_MAIN.CONTENT_ID))
                    .where(TASK_JOB_MAIN.STATUS.eq(TaskJobsConstant.STATUS_NEW))
                    .and(TASK_JOB_MAIN.NEXT_EXECUTE_TIME.lessOrEqual(DateUtil.getLocalDateTime()))
                    .fetch();
                result.forEach(r->{
                    TaskJobsConstant.TaskJobEnum job =
                        TaskJobsConstant.TaskJobEnum.getTaskJobEnumByExecutionType(r.get(TASK_JOB_MAIN.EXECUTION_TYPE));
                    if (job != null) {
                        rabbitmqSendService.sendMessage(job.getExchangeName(),job.getRoutingKey(),
                            r.get(TASK_JOB_CONTENT.CONTENT),r.get(TASK_JOB_MAIN.CLASS_NAME));
                    }
                });
                db().update(TASK_JOB_MAIN)
                    .set(TASK_JOB_MAIN.STATUS,TaskJobsConstant.STATUS_EXECUTING)
                    .where(TASK_JOB_MAIN.ID.in(result.stream().map(x->x.get(TASK_JOB_CONTENT.ID)).collect(Collectors.toList())));
            });
            jedisManager.releaseLock(JedisKeyConstant.TASK_JOB_LOCK,uuid);
        }
    }

    public TaskJobMainRecord getTaskJobMainRecordById(Integer taskJobId){
        return db().selectFrom(TASK_JOB_MAIN).where(TASK_JOB_MAIN.ID.eq(taskJobId)).fetchOne();
    }
    public TaskJobContentRecord getTaskJobContentRecordById(Integer taskContentId){
        return db().selectFrom(TASK_JOB_CONTENT).where(TASK_JOB_CONTENT.ID.eq(taskContentId)).fetchOne();
    }

    /**
     * 更新进度和状态
     * @param content
     * @param taskJobId
     * @param failSize
     * @param allSize
     */
    public void updateProgress(String content,Integer taskJobId, int failSize ,int allSize) {
        TaskJobMainRecord record = getTaskJobMainRecordById(taskJobId);
        if( record != null ){
            TaskJobContentRecord contentRecord = getTaskJobContentRecordById(record.getContentId());
            contentRecord.setContent(content);
            contentRecord.update();
            record.setProgress((byte)(MathUtil.deciMal(allSize-failSize,allSize)*100));
            if( record.getType().equals(TaskJobsConstant.TYPE_CYCLE_ONCE) ){
                Timestamp next = DateUtil.getDalyedDateTime(record.getCycle());
                if( next.before(record.getEndTime()) ){
                    record.setNextExecuteTime(next);
                    record.setStatus(TaskJobsConstant.STATUS_EXECUTING);
                }else{
                    record.setStatus(TaskJobsConstant.STATUS_COMPLETE);
                }
            }else{
                record.setStatus(TaskJobsConstant.STATUS_COMPLETE);
            }
            record.update();
        }
    }
}
