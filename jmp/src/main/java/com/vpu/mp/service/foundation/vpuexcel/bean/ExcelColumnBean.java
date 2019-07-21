package com.vpu.mp.service.foundation.vpuexcel.bean;

/**
 * @author 李晓冰
 * @date 2019年07月18日
 */
public class ExcelColumnBean {
    public String columnName;
    public int columnIndex = -1;
    public String formatStr;
    public boolean notNull=false;
    public Class<?> fieldClazz;

    @Override
    public String toString() {
        return "ExcelColumnBean{" +
                "columnName='" + columnName + '\'' +
                ", columnIndex=" + columnIndex +
                ", formatStr='" + formatStr + '\'' +
                ", notNull=" + notNull +
                ", fieldClazz=" + fieldClazz +
                '}';
    }
}
