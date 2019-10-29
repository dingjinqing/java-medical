package com.vpu.mp.service.pojo.wxapp.activity.param;

import com.vpu.mp.service.foundation.util.DateUtil;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author 李晓冰
 * @date 2019年10月29日
 */
@Data
public class ActivityGoodsListMpParam implements ActivityParam {
    private List<Integer> goodsIds;
    private Timestamp date = DateUtil.getLocalDateTime();
}
