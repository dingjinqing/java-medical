package com.vpu.mp.service.pojo.saas.shop.mp;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Condition;
import org.jooq.impl.DSL;

import static com.vpu.mp.db.main.Tables.MP_OPERATE_LOG;
import static com.vpu.mp.db.main.Tables.MP_VERSION;

/**
 * @author 李晓冰
 * @date 2019年08月07日
 */
@Data
public class MpOperateListParam {

    private String userVersion;
    private String appId;

    private Integer currentPage;
    private Integer pageRows;

    public Condition buildOption() {
        Condition condition = DSL.noCondition();

        if (!StringUtils.isBlank(userVersion)) {
            condition = condition.and(MP_VERSION.USER_VERSION.eq(userVersion));
        }

        if (!StringUtils.isBlank(appId)) {
            condition = condition.and(MP_OPERATE_LOG.APP_ID.eq(appId));
        }

        return condition;
    }
}
