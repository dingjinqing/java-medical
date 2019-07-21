package com.vpu.mp.service.foundation.vpuexcel;

import com.vpu.mp.service.foundation.vpuexcel.annotation.ExcelColumn;
import com.vpu.mp.service.foundation.vpuexcel.annotation.ExcelColumnNotNull;
import com.vpu.mp.service.foundation.vpuexcel.annotation.ExcelIgnore;
import com.vpu.mp.service.foundation.vpuexcel.annotation.ExcelSheet;
import com.vpu.mp.service.foundation.vpuexcel.bean.ExcelColumnBean;
import com.vpu.mp.service.foundation.vpuexcel.bean.ExcelSheetBean;
import com.vpu.mp.service.foundation.vpuexcel.exception.IllegalExcelDataException;
import com.vpu.mp.service.foundation.vpuexcel.exception.IllegalExcelHeaderException;
import com.vpu.mp.service.foundation.vpuexcel.exception.IllegalSheetPositionException;
import com.vpu.mp.service.foundation.vpuexcel.exception.NotExcelModelException;

import java.lang.reflect.Field;

/**
 * @author 李晓冰
 * @date 2019年07月20日
 */
public abstract class AbstractExcelDisposer {

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

                if (columnAnnotation.columnName().length() > 0) {
                    columnBean.columnName = columnAnnotation.columnName();
                }

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
