package com.vpu.mp.service.foundation.vpuexcel.exception.handler;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * @author 李晓冰
 * @date 2019年07月19日
 */
public class IllegalExcelBinder {
     private IllegalExcelEnum illegalExcelEnum;

     private IllegalExcelBinder(){

     }


    public static IllegalExcelBinder createIllegalSheetBinder(Integer expectSheetIndex, Workbook userWorkbook){
        IllegalExcelBinder illegalExcelBinder = new IllegalExcelBinder();
        illegalExcelBinder.expectSheetIndex=expectSheetIndex;
        illegalExcelBinder.userWorkbook=userWorkbook;
        illegalExcelBinder.illegalExcelEnum=IllegalExcelEnum.ILLEGEL_SHEET_POSITION;
        return illegalExcelBinder;
    }
    /**
     * sheet位置错误信息
     */
    private Integer expectSheetIndex;
    private Workbook userWorkbook;


    public static IllegalExcelBinder createHeadIsNullInfo() {
        IllegalExcelBinder illegalExcelBinder = new IllegalExcelBinder();
        illegalExcelBinder.illegalExcelEnum=IllegalExcelEnum.SHEET_HEAD_NULL;
        return illegalExcelBinder;
    }

    public static IllegalExcelBinder createIllegalHeadInfo(Row userHeadRow){
        IllegalExcelBinder illegalExcelBinder = new IllegalExcelBinder();
        illegalExcelBinder.userDataRow=userHeadRow;
        illegalExcelBinder.illegalExcelEnum=IllegalExcelEnum.ILLEGAL_SHEET_HEAD;
        return illegalExcelBinder;
    }
    /**
     * sheet head信息错误
     */
    private Row userHeadRow;


    public static IllegalExcelBinder createIllegalDataInfo(Row userDataRow){
        IllegalExcelBinder illegalExcelBinder = new IllegalExcelBinder();
        illegalExcelBinder.userDataRow=userDataRow;
        illegalExcelBinder.illegalExcelEnum=IllegalExcelEnum.ILLEGAL_SHEET_DATA;
        return illegalExcelBinder;
    }
    /**
     * sheet 映射数据存在错误
     */
    private Row userDataRow;


    public IllegalExcelEnum getIllegalExcelEnum() {
        return illegalExcelEnum;
    }

    public void setIllegalExcelEnum(IllegalExcelEnum illegalExcelEnum) {
        this.illegalExcelEnum = illegalExcelEnum;
    }

    public Integer getExpectSheetIndex() {
        return expectSheetIndex;
    }

    public void setExpectSheetIndex(Integer expectSheetIndex) {
        this.expectSheetIndex = expectSheetIndex;
    }

    public Workbook getUserWorkbook() {
        return userWorkbook;
    }

    public void setUserWorkbook(Workbook userWorkbook) {
        this.userWorkbook = userWorkbook;
    }

    public Row getUserHeadRow() {
        return userHeadRow;
    }

    public void setUserHeadRow(Row userHeadRow) {
        this.userHeadRow = userHeadRow;
    }

    public Row getUserDataRow() {
        return userDataRow;
    }

    public void setUserDataRow(Row userDataRow) {
        this.userDataRow = userDataRow;
    }
}
