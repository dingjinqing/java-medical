package com.vpu.mp.service.foundation.excel;

import com.vpu.mp.service.foundation.excel.annotation.ExcelColumn;
import com.vpu.mp.service.foundation.excel.annotation.ExcelColumnNotNull;
import com.vpu.mp.service.foundation.excel.annotation.ExcelIgnore;
import com.vpu.mp.service.foundation.excel.annotation.ExcelSheet;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author 李晓冰
 * @date 2019年07月21日
 */

/**
 * 表明本javabean 映射一个excel中的sheet,此bean可以用来读取或写入对应的sheet
 *
 * sheetNum:sheet下标，0开始,默认是0
 * headLineNum：表示sheet中表头字段所在的行默认0
 * beginDataNum：sheet中内容数据开始行 默认1
 */
@ExcelSheet
@Data
public class Person {

    /**
     * @ExcelColumn 表明此javabean的字段和excel中列的对应关系
     * columnIndex 通过指定列索引进行对应，如果未设置则会根据columnName寻找对应字段
     * columnName 通过指定列明进行对应，同时也是在生成excel时使用的列头，如果不写默认是字段名
     *
     * @ExcelColumnNotNull 表示此字段为必填项
     */
    @ExcelColumn(columnIndex = 0,columnName = "姓名")
    @ExcelColumnNotNull
    private String name;

    @ExcelColumn(columnIndex =1,columnName = "地址")
    private String address;

    @ExcelColumn(columnIndex = 2,columnName = "年龄")
    private Integer age;

    @ExcelColumn(columnIndex = 3,columnName = "高度")
    private Double height;

    @ExcelColumn(columnIndex = 4,columnName = "工资")
    private BigDecimal money;

    /**
     * 什么都不写则在读取时会自动根据字段名称去尝试和excle的列进行匹配，
     * 在写出时会用字段名作为列标题，所在列号会排在已设置列号的最后面
     */
    @ExcelColumn(columnIndex = 5,columnName = "性别")
    private Character sex;

    @ExcelColumn(columnIndex = 6,columnName = "生日")
    private Timestamp birth;

    /**
     *忽略字段
     */
    @ExcelIgnore
    private String work;
}
