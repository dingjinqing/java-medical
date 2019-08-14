package com.vpu.mp.service.foundation.excel;

import java.lang.reflect.Field;

import org.apache.commons.lang3.StringUtils;

import com.vpu.mp.service.foundation.excel.annotation.ExcelColumn;
import com.vpu.mp.service.foundation.excel.annotation.ExcelColumnNotNull;
import com.vpu.mp.service.foundation.excel.annotation.ExcelIgnore;
import com.vpu.mp.service.foundation.excel.annotation.ExcelSheet;
import com.vpu.mp.service.foundation.excel.bean.ExcelColumnBean;
import com.vpu.mp.service.foundation.excel.bean.ExcelSheetBean;
import com.vpu.mp.service.foundation.excel.exception.IllegalExcelDataException;
import com.vpu.mp.service.foundation.excel.exception.IllegalExcelHeaderException;
import com.vpu.mp.service.foundation.excel.exception.IllegalSheetPositionException;
import com.vpu.mp.service.foundation.excel.exception.NotExcelModelException;
import com.vpu.mp.service.foundation.util.Util;

/**
 * @author 李晓冰
 * @date 2019年07月20日
 */
public abstract class AbstractExcelDisposer {

    public static final String LANGUAGE_TYPE_EXCEL="excel";

    public static final String DEFAULT_LANGUAGE="zh_CN";

    public String language;

    public AbstractExcelDisposer() {
    }

    public AbstractExcelDisposer(String language) {
        this.language = StringUtils.isBlank(language) ?AbstractExcelDisposer.DEFAULT_LANGUAGE: language;
    }

    /**
     *  初始化ExcelSheetBean，映射model类和excel
     * @param clazz
     * @return
     * @throws IllegalSheetPositionException
     * @throws IllegalExcelHeaderException
     * @throws IllegalExcelDataException
     */
    protected ExcelSheetBean initSheet(Class<?> clazz){
        if (!clazz.isAnnotationPresent(ExcelSheet.class)) {
            throw new NotExcelModelException();
        }

        ExcelSheet sheetAnnotation = clazz.getAnnotation(ExcelSheet.class);

        ExcelSheetBean sheetBean = new ExcelSheetBean();

        sheetBean.sheetNum = sheetAnnotation.sheetNum();

        sheetBean.beginDataNum = sheetAnnotation.beginDataNum();

        sheetBean.headLineNum = sheetAnnotation.headLineNum();

        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(ExcelIgnore.class)) {
                continue;
            }

            ExcelColumnBean columnBean = new ExcelColumnBean();

            if (field.isAnnotationPresent(ExcelColumnNotNull.class)) {
                columnBean.notNull = true;
            }

            String filedName = field.getName();

            columnBean.columnName = filedName;

            if (field.isAnnotationPresent(ExcelColumn.class)) {
                ExcelColumn columnAnnotation = field.getAnnotation(ExcelColumn.class);

                if (columnAnnotation.args().length > 0) {
                    columnBean.args=columnAnnotation.args();
                }

                if (columnAnnotation.columnName().length() > 0) {
                    columnBean.columnName = columnAnnotation.columnName();
                }

                columnBean.columnName =Util.translateMessage(language, columnBean.columnName, LANGUAGE_TYPE_EXCEL, (Object[])columnBean.args);

                if (columnAnnotation.columnIndex() != -1) {
                    columnBean.columnIndex = columnAnnotation.columnIndex();
                }

                columnBean.fieldClazz=field.getType();

            }

            sheetBean.columnMap.put(filedName, columnBean);
        }

        return sheetBean;
    }

}
