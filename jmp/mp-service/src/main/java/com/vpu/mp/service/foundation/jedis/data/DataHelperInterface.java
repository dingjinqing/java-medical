package com.vpu.mp.service.foundation.jedis.data;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public interface DataHelperInterface<T> {


    List<T> get(List<Integer> ids);


    void update(T t);


    void update(List<Integer> id);

    void batchUpdate(List<T> values);

    void delete(Integer id);

    default void delete(List<Integer> ids){
        for (Integer id : ids) {
            delete(id);
        }
    }

    String getKey();

}
