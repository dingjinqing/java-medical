package com.vpu.mp.service.pojo.shop.overview;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The interface Pending rule.
 *
 * @param <R> the type parameter
 * @author liufei
 * @date 11 /1/19
 */
public interface PendingRule<R> {
    /**
     * The constant ZERO.
     */
    String ZERO = "0";
    /**
     * The constant ONE.
     */
    String ONE = "1";
    /**
     * The constant TWO.
     */
    String TWO = "2";
    /**
     * The constant PENDING_TEMP.
     */
    @JsonIgnore
    AtomicInteger PENDING_TEMP = new AtomicInteger(0);

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
        PENDING_TEMP.incrementAndGet();
    }

    /**
     * addNum.
     */
    default void addNum(int num) {
        PENDING_TEMP.addAndGet(num);
    }

    /**
     * Get total pending int.
     *
     * @return the int
     */
    default int getTotalPending() {
        return PENDING_TEMP.get();
    }

    /**
     * Reset pending.
     */
    default void resetPending() {
        PENDING_TEMP.set(0);
    }

    /**
     * Handler 1.规则一
     *
     * @param <T> the type parameter
     * @param t   the t
     */
    default <T> void handler1(T... t) {
        Arrays.stream(t).filter(Objects::nonNull).forEach(e -> {
            if (!ZERO.equals(e.toString())) {
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
            if (!ONE.equals(e.toString())) {
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
            if (ONE.equals(e.toString())) {
                addNum(2);
            } else if (TWO.equals(e.toString())) {
                addNum(5);
            }
        });
    }
}
