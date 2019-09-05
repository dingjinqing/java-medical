package com.vpu.mp.service.saas.db;

import com.vpu.mp.config.StorageConfig;
import com.vpu.mp.service.foundation.service.MainBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import static com.vpu.mp.db.main.tables.TaskJobContent.TASK_JOB_CONTENT;
import static com.vpu.mp.db.main.tables.TaskJobMain.TASK_JOB_MAIN;

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
        String fileName = TASK_JOB_FILE + DateUtil.dateFormat(DateUtil.DATE_FORMAT_SIMPLE)+".json";
        String zipFileName = TASK_JOB_FILE + DateUtil.dateFormat(DateUtil.DATE_FORMAT_SIMPLE)+".zip";
        String content = db().select()
            .from(TASK_JOB_MAIN)
            .leftJoin(TASK_JOB_CONTENT).on(TASK_JOB_MAIN.CONTENT_ID.eq(TASK_JOB_CONTENT.ID))
            .fetch().formatJSON();
        FileUtil.createZip(fileName,zipFileName,filePath,content);
        log.info("数据导出完毕");

    }
}
