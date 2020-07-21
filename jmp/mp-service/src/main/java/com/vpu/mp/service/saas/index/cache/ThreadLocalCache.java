package com.vpu.mp.service.saas.index.cache;

import java.sql.Timestamp;
import java.time.LocalDate;

/**
 * @author lixinguo
 */
public class ThreadLocalCache {

    public static ThreadLocal<LocalDate> timestampThreadLocal = new ThreadLocal<>();
    public static ThreadLocal<Timestamp> sevenDayTimestamp = new ThreadLocal<>();

    public static LocalDate getTimestampThreadLocal() {
        return timestampThreadLocal.get();
    }

    public static void setTimestampThreadLocal(LocalDate localDate) {
        timestampThreadLocal.set(localDate);
    }

    public static Timestamp getSevenDayTimestamp() {
        return sevenDayTimestamp.get();
    }

    public static void setSevenDayTimestamp(Timestamp timestamp) {
        sevenDayTimestamp.set(timestamp);
    }


}
