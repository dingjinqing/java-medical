package com.vpu.mp.service.shop.market.groupdraw;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.market.groupdraw.group.GroupListParam;
import com.vpu.mp.service.pojo.shop.market.groupdraw.group.GroupListVo;
import org.jooq.Record6;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

import static com.vpu.mp.db.shop.tables.JoinGroupList.JOIN_GROUP_LIST;
import static com.vpu.mp.db.shop.tables.OrderGoods.ORDER_GOODS;
import static com.vpu.mp.db.shop.tables.User.USER;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * 拼团抽奖 - 开团明细
 *
 * @author 郑保乐
 */
@Service
public class GroupDrawGroupService extends ShopBaseService {

    /**
     * todo 嵌套查团长信息
     */
    public PageResult<GroupListVo> getGroupList(GroupListParam param) {
        SelectConditionStep<Record6<Integer, Integer, String, String, Timestamp, Timestamp>>
            select = shopDb().select(JOIN_GROUP_LIST.GROUP_ID, DSL.count(JOIN_GROUP_LIST.USER_ID).as("userCount"),
            ORDER_GOODS.GOODS_NAME,
            ORDER_GOODS.GOODS_IMG, JOIN_GROUP_LIST.OPEN_TIME, JOIN_GROUP_LIST.END_TIME)
            .from(JOIN_GROUP_LIST)
            .leftJoin(USER).on(JOIN_GROUP_LIST.USER_ID.eq(USER.USER_ID))
            .leftJoin(ORDER_GOODS).on(JOIN_GROUP_LIST.GOODS_ID.eq(ORDER_GOODS.GOODS_ID))
            .where();
        buildOptions(select, param);
        return getPageResult(select, param, GroupListVo.class);
    }

    private void buildOptions(SelectConditionStep<Record6<Integer, Integer, String, String, Timestamp,
        Timestamp>> select, GroupListParam param) {
        String username = param.getUsername();
        String mobile = param.getMobile();
        Boolean grouped = param.getGrouped();
        Timestamp startTime = param.getStartTime();
        Timestamp endTime = param.getEndTime();
        Integer groupId = param.getGroupId();
        if (isNotEmpty(username)) {
            select.and(USER.USERNAME.like(format("%s%%", username)));
        }
        if (isNotEmpty(mobile)) {
            select.and(USER.MOBILE.like(format("%s%%", mobile)));
        }
        if (null != grouped) {
            select.and(JOIN_GROUP_LIST.STATUS.eq((byte) (grouped ? 1 : 2)));
        }
        if (null != startTime) {
            select.and(JOIN_GROUP_LIST.OPEN_TIME.ge(startTime));
        }
        if (null != endTime) {
            select.and(JOIN_GROUP_LIST.END_TIME.le(endTime));
        }
        if (null != groupId) {
            select.and(JOIN_GROUP_LIST.GROUP_ID.eq(groupId));
        }
        select.groupBy(JOIN_GROUP_LIST.GROUP_ID, ORDER_GOODS.GOODS_NAME,
            ORDER_GOODS.GOODS_IMG, JOIN_GROUP_LIST.OPEN_TIME, JOIN_GROUP_LIST.END_TIME);
    }
}
