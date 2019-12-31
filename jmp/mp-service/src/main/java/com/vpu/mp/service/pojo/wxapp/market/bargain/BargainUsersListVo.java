package com.vpu.mp.service.pojo.wxapp.market.bargain;

import com.vpu.mp.service.foundation.util.PageResult;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author: 王兵兵
 * @create: 2019-12-31 14:15
 **/
@Getter
@Setter
public class BargainUsersListVo {

    private Timestamp timestamp;
    private PageResult<BargainUsers> bargainUsers;

    @Setter
    @Getter
    public static class BargainUsers{
        private Integer id;
        private Integer recordId;
        private Integer userId;
        private Timestamp createTime;
        private BigDecimal bargainMoney;

        private String username;
        private String wxOpenid;
        private String mobile;
        private String userAvatar;
    }

}
