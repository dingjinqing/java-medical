package com.vpu.mp.service.pojo.shop.overview;

/**
 * @author liufei
 * @date 10/31/19
 */
public interface Builder<T> {

    /**
     * reset
     */
    void reset();

    /**
     * build
     *
     * @return T
     */
    T build();
}
