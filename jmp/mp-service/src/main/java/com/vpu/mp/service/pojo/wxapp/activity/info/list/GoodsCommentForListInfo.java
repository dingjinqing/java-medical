package com.vpu.mp.service.pojo.wxapp.activity.info.list;

import com.vpu.mp.service.pojo.wxapp.activity.info.ActivityForListInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 李晓冰
 * @date 2019年11月04日
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GoodsCommentForListInfo extends ActivityForListInfo {
    public GoodsCommentForListInfo() {
    }

    public GoodsCommentForListInfo(Integer commentNum) {
        super();
        this.commentNum = commentNum;
    }

    private Integer commentNum;
}
