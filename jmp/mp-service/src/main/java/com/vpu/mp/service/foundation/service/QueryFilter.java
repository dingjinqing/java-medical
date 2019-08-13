package com.vpu.mp.service.foundation.service;

import com.vpu.mp.service.foundation.database.SqlExcuteListener;
import lombok.extern.slf4j.Slf4j;
import org.jooq.ExecuteContext;

/**
 * SQL 监听器
 * <p>
 * 对不含 where 的 update 语句进行拦截，防止批量误操作
 *
 * @author 郑保乐
 */
@Slf4j
public class QueryFilter extends SqlExcuteListener {

    @Override
    public void renderEnd(ExecuteContext ctx) {
        super.renderEnd(ctx);
        if (ctx.sql().matches("^(?i:(UPDATE|DELETE)(?!.* WHERE ).*)$")) {
            throw new DeleteOrUpdateWithoutWhereException();
        }
    }

    private class DeleteOrUpdateWithoutWhereException extends RuntimeException {

        @Override
        public String getMessage() {
            return "Delete or update operation without where cannot be executed.";
        }
    }
}
