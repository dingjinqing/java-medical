package com.vpu.mp.service.pojo.saas.schedule;

import java.sql.Timestamp;

import com.vpu.mp.common.foundation.util.DateUtil;
import com.vpu.mp.common.foundation.util.Util;

public class TaskJobInfo extends BaseTaskJob {

    public static Builder builder(Integer shopId){
        return new TaskJobInfo.Builder(shopId);
    }

    public static class Builder{
        private Integer shopId;

        private String className;
        private Byte progress = 0;
        private Integer executionType;
        private Integer cycle;
        private Byte type ;
        private Timestamp startTime;
        private Timestamp endTime;
        private Timestamp nextExecuteTime;
        private String content;

        public Builder(Integer shopId){
            this.shopId = shopId;
        }
        public TaskJobInfo.Builder executionType(TaskJobsConstant.TaskJobEnum jobEnum){
            this.executionType = jobEnum.getExecutionType();
            return this;
        }
        public TaskJobInfo.Builder type(Byte type){
            this.type = type;
            return this;
        }
        public TaskJobInfo.Builder cycle(Integer cycle){
            this.cycle = cycle;
            this.nextExecuteTime = DateUtil.getDalyedDateTime(cycle);
            return this;
        }
        public TaskJobInfo.Builder startTime(Timestamp startTime){
            this.startTime = startTime;
            return this;
        }
        public TaskJobInfo.Builder endTime(Timestamp endTime){
            this.endTime = endTime;
            return this;
        }

        /**
         * 这个值如果是自定义的的实体类才需要赋值，jdk自带的类似List的这种不需要赋值
         * @param className
         * @return
         */
        public TaskJobInfo.Builder className(String className){
            this.className = className;
            return this;
        }
        public TaskJobInfo.Builder content(Object content){
            this.content = Util.toJson(content);
            return this;
        }
        public TaskJobInfo builder(){
            return new TaskJobInfo(this);
        }
    }
    private TaskJobInfo(TaskJobInfo.Builder builder){
        this.shopId = builder.shopId;
        this.progress = builder.progress;
        this.executionType = builder.executionType;
        this.type = builder.type;

        if( builder.type.equals(TaskJobsConstant.TYPE_CYCLE_ONCE) ){
            if( builder.cycle!=null ){
                this.cycle = builder.cycle;
            }
            if( builder.startTime!=null ){
                this.nextExecuteTime = builder.startTime;
            }else{
                this.nextExecuteTime = DateUtil.getLocalDateTime();
            }
        }else if( builder.type.equals(TaskJobsConstant.TYPE_ONCE) ){
            this.nextExecuteTime = DateUtil.getLocalDateTime();
        }else if( builder.type.equals(TaskJobsConstant.EXECUTION_TIMING) ){
            this.nextExecuteTime = builder.startTime;
        }

        if( builder.startTime!=null ){
            this.startTime = builder.startTime;
        }
        if( builder.startTime!=null ){
            this.endTime = builder.endTime;
        }
        this.content = builder.content;
        this.className = builder.className;
    }
}
