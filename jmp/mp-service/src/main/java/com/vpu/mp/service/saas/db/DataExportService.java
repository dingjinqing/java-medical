package com.vpu.mp.service.saas.db;

import static com.vpu.mp.db.main.tables.TaskJobContent.TASK_JOB_CONTENT;
import static com.vpu.mp.db.main.tables.TaskJobMain.TASK_JOB_MAIN;

import java.util.ArrayList;
import java.util.List;

import org.jooq.Result;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.common.foundation.util.DateUtils;
import com.vpu.mp.common.foundation.util.FileUtil;
import com.vpu.mp.config.StorageConfig;
import com.vpu.mp.db.main.tables.records.TaskJobMainRecord;
import com.vpu.mp.service.foundation.service.MainBaseService;
import com.vpu.mp.service.pojo.saas.schedule.TaskJobsConstant;

import lombok.extern.slf4j.Slf4j;

/**
 * 数据导出
 * @author 卢光耀
 * @date 2019-09-05 10:35
 *
*/
@Slf4j
@Service
public class DataExportService extends MainBaseService {

    @Autowired
    StorageConfig storageConfig;

    private static final String TASK_JOB_FILE = "task_job_";

    public void exportData(){
        String filePath = storageConfig.storagePath("taskJob");
        String fileName = TASK_JOB_FILE + DateUtils.dateFormat(DateUtils.DATE_FORMAT_SIMPLE)+".json";
        String zipFileName = TASK_JOB_FILE + DateUtils.dateFormat(DateUtils.DATE_FORMAT_SIMPLE)+".zip";
        db().transaction(configuration -> {
            Result<TaskJobMainRecord> result = DSL.using(configuration)
                .select()
                .from(TASK_JOB_MAIN)
                .where(TASK_JOB_MAIN.STATUS.eq(TaskJobsConstant.STATUS_COMPLETE))
                .fetchInto(TASK_JOB_MAIN);
            List<Integer> mainIds = new ArrayList<>(result.size());
            List<Integer> contentIds = new ArrayList<>(result.size());
            for(TaskJobMainRecord job:result ){
                mainIds.add(job.getId());
                contentIds.add(job.getContentId());
            }
            String content = DSL.using(configuration)
                .select()
                .from(TASK_JOB_MAIN)
                .leftJoin(TASK_JOB_CONTENT).on(TASK_JOB_MAIN.CONTENT_ID.eq(TASK_JOB_CONTENT.ID))
                .where(TASK_JOB_MAIN.ID.in(mainIds))
                .fetch()
                .formatJSON();
            FileUtil.createZip(fileName,zipFileName,filePath,content);
            DSL.using(configuration).deleteFrom(TASK_JOB_MAIN).where(TASK_JOB_MAIN.ID.in(mainIds));
            DSL.using(configuration).deleteFrom(TASK_JOB_CONTENT).where(TASK_JOB_CONTENT.ID.in(contentIds));
        });
        log.info(DateUtils.getLocalDateTime()+"-数据导出完毕");

    }
}
