package com.vpu.mp.service.saas.index.cache;

import java.sql.Timestamp;
import java.time.LocalDate;

public class ThreadLocalCache {

    public static ThreadLocal<LocalDate> timestampThreadLocal = new ThreadLocal<>();
}
