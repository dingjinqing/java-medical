package com.vpu.mp.service.pojo.shop.goods.es;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.util.List;

/**
 * es goods send to mq param
 * @author 卢光耀
 * @date 2019/10/23 11:00 上午
 *
*/
@Data
@Builder
public class EsTaskParam {

    private List<Integer> idList;

    private Integer shopId;
    @Tolerate
    public EsTaskParam(){

    }
}
