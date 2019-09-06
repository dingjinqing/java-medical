package com.vpu.mp.service.foundation.excel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;

import com.vpu.mp.service.foundation.excel.bean.ClassList;
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
    
    
    /**
     * 可操作合并单元格  测试类见ExcelTest.excelExportDataByMergedRegion()
     * @param <T>
     * @param dataArray
     * @param cList
     * @throws Exception
     */
	public <T> void writeModelListByRegion(List<T> dataArray, ClassList cList) throws Exception {
		Class<?> clazz = cList.getUpClazz();
		Class<?> innerClazz = cList.getInnerClazz();
		ExcelSheetBean sheetBean = initSheet(clazz);
		ExcelSheetBean initSheet = initSheet(innerClazz);
		Sheet sheet = creatTargetSheet(sheetBean);
		createExcelTemplate(clazz, sheetBean, sheet);
		Map<Integer, CellStyle> styleMap = new HashMap<>(sheetBean.columnMap.size());
		Map<Integer, CellType> typeMap = new HashMap<>(sheetBean.columnMap.size());
		// 设置单元格样式和类型缓存
		String key = null;
		for (Map.Entry<String, ExcelColumnBean> entry : sheetBean.columnMap.entrySet()) {
			ExcelColumnBean columnBean = entry.getValue();
			if (List.class.equals(columnBean.fieldClazz)) {
				key = entry.getKey();
				for (Map.Entry<String, ExcelColumnBean> entrySet : initSheet.columnMap.entrySet()) {
					ExcelColumnBean innerColumnBean = entrySet.getValue();
					CellStyle cellStyle = ExcelUtil.getCellStyle(innerColumnBean.fieldClazz, workbook);
					CellType cellType = ExcelUtil.convertJavaType2CellType(innerColumnBean.fieldClazz);

					styleMap.put(innerColumnBean.columnIndex, cellStyle);
					typeMap.put(innerColumnBean.columnIndex, cellType);

				}
			} else {

				CellStyle cellStyle = ExcelUtil.getCellStyle(columnBean.fieldClazz, workbook);
				CellType cellType = ExcelUtil.convertJavaType2CellType(columnBean.fieldClazz);
				styleMap.put(columnBean.columnIndex, cellStyle);
				typeMap.put(columnBean.columnIndex, cellType);
			}
		}

		int beginDataIndex = sheetBean.beginDataNum;
		// 将model数据转换为对应的row
		for (int i = 0; i < dataArray.size(); i++) {
			// 合并的行对应行数的统计，要顺序，所以用LinkedList
			List<Integer> rowList = new LinkedList<Integer>();
			// 合并的列对应的列数 单个的时候元素会重
			List<Integer> columnList = new LinkedList<Integer>();
			// 没有进行合并的列的列数
			Set<Integer> cNo = new HashSet<Integer>();
			// 合并的列的数量
			Set<Integer> innerRow = new HashSet<Integer>();
			int rowIndex = beginDataIndex + i;
			Row row = sheet.createRow(rowIndex);
			for (Map.Entry<String, ExcelColumnBean> entry : sheetBean.columnMap.entrySet()) {
				ExcelColumnBean columnBean = entry.getValue();
				String fieldName = entry.getKey();
				Object value = null;
				Cell cell = row.createCell(columnBean.columnIndex);
				cell.setCellType(typeMap.get(columnBean.columnIndex));
				cell.setCellStyle(styleMap.get(columnBean.columnIndex));
				try {
					value = ExcelUtil.getFieldValue(fieldName, dataArray.get(i));
					int tempRowIndex = rowIndex;
					Row temoRow = row;
					if (fieldName.equals(key)) {
						rowList.add(tempRowIndex);
						for (Map.Entry<String, ExcelColumnBean> entrySet : initSheet.columnMap.entrySet()) {
							tempRowIndex = rowIndex;
							ExcelColumnBean innerColumnBean = entrySet.getValue();
							String innerFieldName = entrySet.getKey();
							for (Object o : (List<?>) value) {
								temoRow = sheet.getRow(tempRowIndex);
								if (temoRow == null) {
									beginDataIndex++;
									temoRow = sheet.createRow(tempRowIndex);
								}
								Object value1 = ExcelUtil.getFieldValue(innerFieldName, o);
								Cell cell1 = temoRow.createCell(innerColumnBean.columnIndex);
								cell1.setCellType(typeMap.get(innerColumnBean.columnIndex));
								cell1.setCellStyle(styleMap.get(innerColumnBean.columnIndex));
								cNo.add(innerColumnBean.columnIndex);
								innerRow.add(tempRowIndex);
								System.out.println(
										"行：" + tempRowIndex + " 列：" + innerColumnBean.columnIndex + "   " + value1);
								ExcelUtil.setCellValue(cell1, value1);
								tempRowIndex = tempRowIndex + 1;
							}
						}
						rowList.add(tempRowIndex - 1);
					} else {
						System.out.println("行：" + rowIndex + " 列：" + columnBean.columnIndex + "   " + value);
						columnList.add(columnBean.columnIndex);
						ExcelUtil.setCellValue(cell, value);
					}
				} catch (NoSuchFieldException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			// 因为翻译时候有站位，要去掉不是合并单元格的列的列数
			List<Integer> cnList = new ArrayList<Integer>(cNo);
			columnList.removeAll(cnList);
			if (innerRow.size() > 1) {
				// List中的类如果是一行的就不要合并
				for (int e = 0; e < columnList.size(); e++) {
					// 合并日期占两行(4个参数，分别为起始行，结束行，起始列，结束列)
					CellRangeAddress region = new CellRangeAddress(rowList.get(0), rowList.get(1), columnList.get(e),
							columnList.get(e));
					sheet.addMergedRegion(region);
					// 考虑是否合并单元格，
					// sheet.autoSizeColumn(1);
					sheet.autoSizeColumn(columnList.get(e), true);
					// 格式化后设置边框
					setBorderStyle(BorderStyle.THIN, region, sheet);

				}
				for (Integer coum : cNo) {
					sheet.autoSizeColumn(coum);
				}
			} else {
				for (Map.Entry<String, ExcelColumnBean> entry : sheetBean.columnMap.entrySet()) {
					ExcelColumnBean columnBean = entry.getValue();
					sheet.autoSizeColumn(columnBean.columnIndex);
				}
			}
			System.out.println();
		}
	}
	
	
	/**
	 * 合并单元格以后加边框
	 * @param border
	 * @param region
	 * @param sheet
	 */
	 public void setBorderStyle(BorderStyle border, CellRangeAddress region, Sheet sheet){
	        RegionUtil.setBorderBottom(border, region, sheet);//下边框
	        RegionUtil.setBorderLeft(border, region, sheet);     //左边框
	        RegionUtil.setBorderRight(border, region, sheet);    //右边框
	        RegionUtil.setBorderTop(border, region, sheet);      //上边框
	    }
}
