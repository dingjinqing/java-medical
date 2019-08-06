package com.vpu.mp.service.foundation.excel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.vpu.mp.service.foundation.excel.bean.ExcelColumnBean;
import com.vpu.mp.service.foundation.excel.bean.ExcelSheetBean;
import com.vpu.mp.service.foundation.excel.exception.IllegalExcelDataException;
import com.vpu.mp.service.foundation.excel.exception.IllegalExcelHeaderException;
import com.vpu.mp.service.foundation.excel.exception.IllegalSheetPositionException;

/**
 * @author 李晓冰
 * @date 2019年07月19日
 */
public class ExcelWriter extends AbstractExcelDisposer {
    private Workbook workbook;
    private String sheetName;

    public ExcelWriter(Workbook workbook, String sheetName) {
        this(AbstractExcelDisposer.DEFAULT_LANGUAGE,workbook,sheetName);
    }

    public ExcelWriter(Workbook workbook) {
        this(AbstractExcelDisposer.DEFAULT_LANGUAGE,workbook);
    }

    public ExcelWriter(String language, Workbook workbook, String sheetName) {
        super(language);
        this.workbook = workbook;
        this.sheetName = sheetName;
    }

    public ExcelWriter(String language, Workbook workbook) {
        super(language);
        this.workbook = workbook;
    }

    /**
     * 将数据写入到excel中
     *
     * @param dataArray
     * @param clazz
     * @param <T>
     * @throws IllegalExcelHeaderException
     * @throws IllegalSheetPositionException
     * @throws IllegalExcelDataException
     */
    public <T> void writeModelList(List<T> dataArray, Class<T> clazz) {
        ExcelSheetBean sheetBean = initSheet(clazz);

        Sheet sheet = creatTargetSheet(sheetBean);

        createExcelTemplate(clazz, sheetBean, sheet);


        Map<Integer, CellStyle> styleMap = new HashMap<>(sheetBean.columnMap.size());
        Map<Integer, CellType> typeMap = new HashMap<>(sheetBean.columnMap.size());
        //设置单元格样式和类型缓存
        for (Map.Entry<String, ExcelColumnBean> entry : sheetBean.columnMap.entrySet()) {
            ExcelColumnBean columnBean = entry.getValue();

            CellStyle cellStyle = ExcelUtil.getCellStyle(columnBean.fieldClazz, workbook);
            CellType cellType = ExcelUtil.convertJavaType2CellType(columnBean.fieldClazz);

            styleMap.put(columnBean.columnIndex, cellStyle);
            typeMap.put(columnBean.columnIndex, cellType);
        }

        int beginDataIndex = sheetBean.beginDataNum;
        //将model数据转换为对应的row
        for (int i = 0; i < dataArray.size(); i++) {
            int rowIndex = beginDataIndex + i;
            Row row = sheet.createRow(rowIndex);
            for (Map.Entry<String, ExcelColumnBean> entry : sheetBean.columnMap.entrySet()) {
                ExcelColumnBean columnBean = entry.getValue();
                String fieldName = entry.getKey();

                Cell cell = row.createCell(columnBean.columnIndex);
                cell.setCellType(typeMap.get(columnBean.columnIndex));
                cell.setCellStyle(styleMap.get(columnBean.columnIndex));

                Object value = null;
                try {
                    value = ExcelUtil.getFieldValue(fieldName, dataArray.get(i));
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                ExcelUtil.setCellValue(cell, value);
            }
        }

        //设置列宽自适应
        for (Map.Entry<String, ExcelColumnBean> entry : sheetBean.columnMap.entrySet()) {
            ExcelColumnBean columnBean = entry.getValue();
            sheet.autoSizeColumn(columnBean.columnIndex);
        }
    }


    private void createExcelTemplate(Class<?> clazz, ExcelSheetBean sheetBean, Sheet sheet) {

        int headLineNum = sheetBean.headLineNum;

        int maxColumnIndex = -1;

        for (Map.Entry<String, ExcelColumnBean> entry : sheetBean.columnMap.entrySet()) {
            ExcelColumnBean value = entry.getValue();
            if (maxColumnIndex < value.columnIndex) {
                maxColumnIndex = value.columnIndex;
            }
        }

        for (Map.Entry<String, ExcelColumnBean> entry : sheetBean.columnMap.entrySet()) {
            ExcelColumnBean value = entry.getValue();
            if (value.columnIndex == -1) {
                value.columnIndex = ++maxColumnIndex;
            }
        }

        Row row = sheet.createRow(headLineNum);
        CellStyle cellStyle = ExcelStyleFactory.createCommonHeaderCellStyle(workbook);

        for (Map.Entry<String, ExcelColumnBean> entry : sheetBean.columnMap.entrySet()) {
            ExcelColumnBean value = entry.getValue();
            Cell cell = row.createCell(value.columnIndex);

            cell.setCellType(ExcelUtil.convertJavaType2CellType(String.class));

            ExcelUtil.setCellValue(cell, value.columnName);

            cell.setCellStyle(cellStyle);

            sheet.autoSizeColumn(value.columnIndex);
        }
    }

    /**
     * 对外创建excel模板
     *
     * @param clazz
     */
    public void createExcelTemplate(Class<?> clazz) {
        ExcelSheetBean sheetBean = initSheet(clazz);

        Sheet sheet = creatTargetSheet(sheetBean);

        createExcelTemplate(clazz, sheetBean, sheet);
    }

    private Sheet creatTargetSheet(ExcelSheetBean sheetBean) {
        int sheetPosition = sheetBean.sheetNum;
        if (sheetPosition < 0) {
            sheetPosition = 0;
        }
        for (int i = 0; i < sheetPosition; i++) {
            workbook.createSheet();
        }

        Sheet sheet = null;
        if (sheetName != null) {
            sheet = workbook.createSheet(sheetName);
        } else {
            sheet = workbook.createSheet();
        }
        workbook.setActiveSheet(sheetPosition);

        return sheet;
    }

}
