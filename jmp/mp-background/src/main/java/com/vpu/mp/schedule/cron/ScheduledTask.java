package com.vpu.mp.schedule.cron;

import java.util.concurrent.ScheduledFuture;

/**
 * @author liufei
 * @date 12/20/19
 * 定时任务启动线程，可随时取消该定时任务线程
 */
public final class ScheduledTask {

    volatile ScheduledFuture<?> future;

    /**
     * 取消定时任务
     */
    void cancel() {
        ScheduledFuture<?> future = this.future;
        if (future != null) {
            future.cancel(true);
        }
    }
}
