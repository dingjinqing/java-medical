package com.vpu.mp.service.pojo.shop.overview.transaction;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;
import java.util.Map;

import com.vpu.mp.common.foundation.util.PageResult;

/**
 * @author liufei
 * @date 11/19/19
 */
@Data
@Builder
public class LabelAnalysisVoo {
    private PageResult<LabelAnalysisVo> pageData;
    private Map<String, Date> dateMap;
}
