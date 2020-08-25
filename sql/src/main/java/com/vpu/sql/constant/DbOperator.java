package com.vpu.sql.constant;

/**
 * sql操作
 * @author 卢光耀
 * @date 2020-01-06 14:03
 */
public enum DbOperator {
    /**
     * 删除，创建，修改结构，修改数据，新增数据，截断
     */
    DROP("drop"), CREATE("create"), ALTER("alter"), UPDATE("update"), INSERT("insert"), TRUNCATE("truncate");

    private String operator;

    DbOperator(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return this.operator;
    }

    public static DbOperator getDBOperator(String operator) {
        for (DbOperator dbOperator : values()) {
            if (dbOperator.getOperator().equals(operator)) {
                return dbOperator;
            }
        }
        return null;
    }
}
