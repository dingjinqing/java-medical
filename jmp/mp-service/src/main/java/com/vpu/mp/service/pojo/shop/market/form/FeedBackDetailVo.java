package com.vpu.mp.service.pojo.shop.market.form;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

/**
 * @author liufei
 * @date 2019/8/9
 * @description
 */
@Data
public class FeedBackDetailVo {
    @JsonIgnore
    private Integer submitId;
    @JsonIgnore
    private String moduleName;
    private String moduleType;
    /** moduleValue是个数值，表示moduleValueList中第几个页面该高亮显示，即表示当前值为用户值，其他为选项值 */
    private String moduleValue;
    private List<String> moduleValueList;
    @JsonIgnore
    private Integer curIdx;

}
