package com.vpu.mp.service.pojo.wxapp.market.bargain;

import com.vpu.mp.service.foundation.util.Page;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author: 王兵兵
 * @create: 2019-10-24 11:36
 **/
@Data
public class BargainRecordListQueryParam {
    /**
     *    砍价状态过滤 ：0砍价中，1砍价成功，2砍价失败
     */
    @Max(2)
    @Min(0)
    private Byte status = (byte)0;

    /**
     * 	分页信息
     */
    private Integer currentPage = Page.DEFAULT_CURRENT_PAGE;
    private Integer pageRows = Page.DEFAULT_PAGE_ROWS;
}
