package com.vpu.mp.service.shop.market.groupdraw;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.market.groupdraw.group.GroupListParam;
import com.vpu.mp.service.pojo.shop.market.groupdraw.group.GroupListVo;
import org.jooq.Record10;
import org.jooq.Record9;
import org.jooq.SelectConditionStep;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

import static com.vpu.mp.db.shop.tables.JoinGroupList.JOIN_GROUP_LIST;
import static com.vpu.mp.db.shop.tables.OrderGoods.ORDER_GOODS;
import static com.vpu.mp.db.shop.tables.User.USER;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static org.jooq.impl.DSL.count;
import static org.jooq.impl.DSL.select;

/**
 * 拼团抽奖 - 开团明细
 *
 * @author 郑保乐
 */
@Service
public class GroupDrawGroupService extends ShopBaseService {

    /** 嵌套查询内外层表别名 **/
    private static final String ALIAS_INSIDE = "i";
    private static final String ALIAS_OUTSIDE = "o";

    /**
     * 拼团明细列表
     */
    public PageResult<GroupListVo> getGroupList(GroupListParam param) {
        SelectConditionStep<Record10<Integer, Integer, String, String, Timestamp, Timestamp, Object, Object, Object, Object>>
            select = db().select(
                JOIN_GROUP_LIST.as(ALIAS_OUTSIDE).GROUP_ID,
                count(JOIN_GROUP_LIST.as(ALIAS_OUTSIDE).USER_ID).as("userCount"), ORDER_GOODS.GOODS_NAME,
                ORDER_GOODS.GOODS_IMG, JOIN_GROUP_LIST.as(ALIAS_OUTSIDE).OPEN_TIME, JOIN_GROUP_LIST.as(ALIAS_OUTSIDE).END_TIME,
                // 三个嵌套查询
                select(JOIN_GROUP_LIST.as(ALIAS_INSIDE).USER_ID).from(JOIN_GROUP_LIST.as(ALIAS_INSIDE))
                    .where(JOIN_GROUP_LIST.as(ALIAS_INSIDE).IS_GROUPER.eq((byte) 1)
                        .and(JOIN_GROUP_LIST.as(ALIAS_INSIDE).GROUP_ID.eq(JOIN_GROUP_LIST.as(ALIAS_OUTSIDE).GROUP_ID)))
                    .asField("userId"),
                select(USER.USERNAME).from(JOIN_GROUP_LIST.as(ALIAS_INSIDE))
                    .leftJoin(USER).on(JOIN_GROUP_LIST.as(ALIAS_INSIDE).USER_ID.eq(USER.USER_ID))
                    .where(JOIN_GROUP_LIST.as(ALIAS_INSIDE).IS_GROUPER.eq((byte) 1)
                        .and(JOIN_GROUP_LIST.as(ALIAS_INSIDE).GROUP_ID.eq(JOIN_GROUP_LIST.as(ALIAS_OUTSIDE).GROUP_ID)))
                    .asField("grouperName"),
                select(USER.USER_ID).from(JOIN_GROUP_LIST.as(ALIAS_INSIDE))
                    .leftJoin(USER).on(JOIN_GROUP_LIST.as(ALIAS_INSIDE).USER_ID.eq(USER.USER_ID))
                    .where(JOIN_GROUP_LIST.as(ALIAS_INSIDE).IS_GROUPER.eq((byte) 1)
                        .and(JOIN_GROUP_LIST.as(ALIAS_INSIDE).GROUP_ID.eq(JOIN_GROUP_LIST.as(ALIAS_OUTSIDE).GROUP_ID)))
                    .asField("grouperId"),
                select(USER.MOBILE).from(JOIN_GROUP_LIST.as(ALIAS_INSIDE))
                    .leftJoin(USER).on(JOIN_GROUP_LIST.as(ALIAS_INSIDE).USER_ID.eq(USER.USER_ID))
                    .where(JOIN_GROUP_LIST.as(ALIAS_INSIDE).IS_GROUPER.eq((byte) 1)
                        .and(JOIN_GROUP_LIST.as(ALIAS_INSIDE).GROUP_ID.eq(JOIN_GROUP_LIST.as(ALIAS_OUTSIDE).GROUP_ID)))
                    .asField("mobile")
            )
            .from(JOIN_GROUP_LIST.as(ALIAS_OUTSIDE))
            .leftJoin(USER).on(JOIN_GROUP_LIST.as(ALIAS_OUTSIDE).USER_ID.eq(USER.USER_ID))
            .leftJoin(ORDER_GOODS).on(JOIN_GROUP_LIST.as(ALIAS_OUTSIDE).GOODS_ID.eq(ORDER_GOODS.GOODS_ID))
            .where();
        buildOptions(select, param);
        select.orderBy(JOIN_GROUP_LIST.as(ALIAS_OUTSIDE).END_TIME.desc());
        return getPageResult(select, param, GroupListVo.class);
    }

    private void buildOptions(SelectConditionStep<Record10<Integer, Integer, String, String, Timestamp, Timestamp,
        Object, Object, Object, Object>> select, GroupListParam param) {
        Integer groupDrawId = param.getGroupDrawId();
        String username = param.getUsername();
        String mobile = param.getMobile();
        Boolean grouped = param.getGrouped();
        Timestamp startTime = param.getStartTime();
        Timestamp endTime = param.getEndTime();
        Integer groupId = param.getGroupId();
        select.and(JOIN_GROUP_LIST.as(ALIAS_OUTSIDE).GROUP_DRAW_ID.eq(groupDrawId));
        if (isNotEmpty(username)) {
            select.and(USER.USERNAME.like(likeValue(username)));
        }
        if (isNotEmpty(mobile)) {
            select.and(USER.MOBILE.like(likeValue(mobile)));
        }
        if (null != grouped) {
            select.and(JOIN_GROUP_LIST.as(ALIAS_OUTSIDE).STATUS.eq((byte) (grouped ? 1 : 2)));
        }
        if (null != startTime) {
            select.and(JOIN_GROUP_LIST.as(ALIAS_OUTSIDE).OPEN_TIME.ge(startTime));
        }
        if (null != endTime) {
            select.and(JOIN_GROUP_LIST.as(ALIAS_OUTSIDE).END_TIME.le(endTime));
        }
        if (null != groupId) {
            select.and(JOIN_GROUP_LIST.as(ALIAS_OUTSIDE).GROUP_ID.eq(groupId));
        }
        select.groupBy(JOIN_GROUP_LIST.as(ALIAS_OUTSIDE).GROUP_ID, ORDER_GOODS.GOODS_NAME, ORDER_GOODS.GOODS_IMG,
            JOIN_GROUP_LIST.as(ALIAS_OUTSIDE).OPEN_TIME, JOIN_GROUP_LIST.as(ALIAS_OUTSIDE).END_TIME);
    }
}
