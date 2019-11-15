package com.vpu.mp.service.pojo.shop.overview;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import static com.vpu.mp.service.pojo.shop.overview.OverviewConstant.*;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;

/**
 * The interface Pending rule.
 *
 * @param <R> the type parameter
 * @author liufei
 * @date 11 /1/19
 */
public interface PendingRule<R> {
    /**
     * The constant PENDING_TEMP.
     */
    @JsonIgnore
    ThreadLocal<AtomicInteger> PENDING_TEMP = ThreadLocal.withInitial(() -> ATOMIC_INTEGER_ZERO);

    /**
     * Rule handler r.
     *
     * @return the r
     */
    R ruleHandler();

    /**
     * Increase.
     */
    default void increase() {
        PENDING_TEMP.get().incrementAndGet();
    }

    /**
     * addNum.
     */
    default void addNum(int num) {
        PENDING_TEMP.get().addAndGet(num);
    }

    /**
     * Get total pending int.
     *
     * @return the int
     */
    default int getTotalPending() {
        return PENDING_TEMP.get().get();
    }

    /**
     * Reset pending.
     */
    default void resetPending() {
        PENDING_TEMP.get().set(INTEGER_ZERO);
    }

    /**
     * Handler 1.规则一
     *
     * @param <T> the type parameter
     * @param t   the t
     */
    default <T> void handler1(T... t) {
        Arrays.stream(t).filter(Objects::nonNull).forEach(e -> {
            if (!STRING_ZERO.equals(e.toString())) {
                increase();
            }
        });
    }

    /**
     * Handler 2.规则二
     *
     * @param <T> the type parameter
     * @param t   the t
     */
    default <T> void handler2(T... t) {
        Arrays.stream(t).filter(Objects::nonNull).forEach(e -> {
            if (!STRING_ONE.equals(e.toString())) {
                increase();
            }
        });
    }

    /**
     * Handler 3.规则三
     *
     * @param <T> the type parameter
     * @param t   the t
     */
    default <T> void handler3(T... t) {
        Arrays.stream(t).filter(Objects::nonNull).forEach(e -> {
            if (STRING_ONE.equals(e.toString())) {
                addNum(2);
            } else if (STRING_TWO.equals(e.toString())) {
                addNum(5);
            }
        });
    }

    /**
     * Handler 4.规则四
     *
     * @param <T> the type parameter
     * @param t   the t
     */
    default <T> void handler4(Collection<T>... t) {
        Arrays.stream(t).forEach(e -> {
            if (CollectionUtils.isNotEmpty(e)) {
                addNum(1);
            }
        });
    }
}
