package com.vpu.mp.service.foundation.service;

import java.sql.Timestamp;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import com.vpu.mp.service.foundation.base.MainBaseDao;
import com.vpu.mp.service.foundation.excel.ExcelFactory;
import com.vpu.mp.service.foundation.excel.ExcelTypeEnum;
import com.vpu.mp.service.foundation.excel.ExcelWriter;
import com.vpu.mp.service.saas.SaasApplication;
import com.vpu.mp.service.wechat.OpenPlatform;
import org.apache.poi.ss.usermodel.Workbook;
import org.jooq.Configuration;
import org.jooq.ContextTransactionalRunnable;
import org.jooq.Field;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.SQLDataType;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author lixinguo
 *
 */
public class MainBaseService extends MainBaseDao {

    @Autowired
    protected OpenPlatform open;

    @Autowired
    protected SaasApplication saas;


    protected OpenPlatform open() {
        return open;
    }

    protected SaasApplication saas() {
        return saas;
    }

    protected <T> Workbook export(List<T> list, Class<T> clazz){
        Workbook workbook = ExcelFactory.createWorkbook(ExcelTypeEnum.XLSX);
        ExcelWriter excelWriter = new ExcelWriter(workbook);
        excelWriter.writeModelList(list, clazz);
        return workbook;
    }
}
