package com.vpu.mp.service.pojo.shop.summary;

import lombok.Data;

import java.util.List;

/**
 * 访问分布 json
 *
 * @author 郑保乐
 */
@Data
public class Distribution {

    private List<DistributionIndex> indexes;
}
