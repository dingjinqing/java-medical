package com.vpu.mp.service.foundation.exception;

import com.vpu.mp.service.foundation.data.JsonResultCode;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.Objects;

/**
 * The type Assert.
 *
 * @author liufei
 * @date 11 /5/19
 */
public class Assert {
    /**
     * Is null.
     *
     * @param o    the o
     * @param code the code
     */
    public static void isNull(Object o, JsonResultCode code) {
        if (Objects.nonNull(o)) {
            throw new BusinessException(code);
        }
    }

    /**
     * Not null.
     *
     * @param o    the o
     * @param code the code
     */
    public static void notNull(Object o, JsonResultCode code) {
        if (Objects.isNull(o)) {
            throw new BusinessException(code);
        }
    }

    /**
     * Is empty.
     *
     * @param <E>        the type parameter
     * @param collection the collection
     * @param code       the code
     */
    public static <E> void isEmpty(Collection<E> collection, JsonResultCode code) {
        if (CollectionUtils.isNotEmpty(collection)) {
            throw new BusinessException(code);
        }
    }

    /**
     * Not empty.
     *
     * @param <E>        the type parameter
     * @param collection the collection
     * @param code       the code
     */
    public static <E> void notEmpty(Collection<E> collection, JsonResultCode code) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new BusinessException(code);
        }
    }
}
