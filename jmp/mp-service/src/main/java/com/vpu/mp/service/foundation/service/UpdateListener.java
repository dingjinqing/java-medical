package com.vpu.mp.service.foundation.service;

import lombok.extern.slf4j.Slf4j;
import org.jooq.ExecuteContext;
import org.jooq.ExecuteType;
import org.jooq.Query;
import org.jooq.UpdateQuery;
import org.jooq.impl.DefaultExecuteListener;

/**
 * Update SQL 监听器
 * <p>
 * 对不含 where 的 update 语句进行拦截，防止批量误操作
 *
 * @author 郑保乐
 */
@Slf4j
public class UpdateListener extends DefaultExecuteListener {

    @Override
    public void start(ExecuteContext ctx) {
        ExecuteType type = ctx.type();
        if (type == ExecuteType.WRITE) {
            Query query = ctx.query();
            String sql = query.getSQL();
            boolean update = UpdateQuery.class.isAssignableFrom(query.getClass());
            if (update) {
                if (!sql.contains("where")) {
                    query.close();
                    log.error("Danger operation without where condition !!! Original sql: {}", sql);
                    throw new IllegalArgumentException("Update sql without any condition cannot be executed. ");
                }
            }
        }
    }
}
