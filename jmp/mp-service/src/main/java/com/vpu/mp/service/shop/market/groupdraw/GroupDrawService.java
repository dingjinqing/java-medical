package com.vpu.mp.service.shop.market.groupdraw;

import com.vpu.mp.db.shop.tables.records.GroupDrawRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.market.groupdraw.*;
import com.vpu.mp.service.shop.qrcode.QRCodeService;
import lombok.extern.slf4j.Slf4j;
import org.jooq.Record17;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.tables.GroupDraw.GROUP_DRAW;
import static com.vpu.mp.db.shop.tables.JoinDrawList.JOIN_DRAW_LIST;
import static com.vpu.mp.db.shop.tables.JoinGroupList.JOIN_GROUP_LIST;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * 拼团抽奖
 *
 * @author 郑保乐
 */
@Service
@Slf4j
public class GroupDrawService extends ShopBaseService {

    /** 启用 **/
    private static final byte GROUP_DRAW_ENABLED = 1;
    /** 禁用 **/
    private static final byte GROUP_DRAW_DISABLED = 0;

    /** 活动页面 **/
    private static final String GROUP_DRAW_SHARE_PATH = "pages/pinlotterylist/pinlotterylist";

    private final QRCodeService qrCode;

    public GroupDrawService(QRCodeService qrCode) {
        this.qrCode = qrCode;
    }

    /**
     * 获取小程序码
     */
    public GroupDrawShareVo getMpQRCode(GroupDrawShareParam param) throws Exception {
        Integer groupDrawId = param.getGroupDrawId();
        String pagePath = GROUP_DRAW_SHARE_PATH + "?group_draw_id=" + groupDrawId;
        String imageUrl = qrCode.getMpQRCode(pagePath, (short) 23, groupDrawId);
        GroupDrawShareVo vo = new GroupDrawShareVo();
        vo.setImageUrl(imageUrl);
        vo.setPagePath(pagePath);
        return vo;
    }

    /**
     * 停用活动
     */
    public void disableGroupDraw(Integer id) {
        shopDb().update(GROUP_DRAW).set(GROUP_DRAW.STATUS, GROUP_DRAW_DISABLED)
            .where(GROUP_DRAW.ID.eq(id)).execute();
    }

    /**
     * 拼团结算
     * @param groupDrawId 活动id
     */
    public void settleOrders(Integer groupDrawId) {
        
    }

    /**
     * 更新活动
     */
    public void updateGroupDraw(Integer id, GroupDrawUpdateParam param) {
        shopDb().update(GROUP_DRAW).set(GROUP_DRAW.NAME, param.getName()).set(GROUP_DRAW.START_TIME,
            param.getStartTime()).set(GROUP_DRAW.END_TIME, param.getEndTime()).set(GROUP_DRAW.JOIN_LIMIT,
            param.getJoinLimit()).set(GROUP_DRAW.LIMIT_AMOUNT, param.getLimitAmount()).set(GROUP_DRAW.OPEN_LIMIT,
            param.getOpenLimit()).set(GROUP_DRAW.MIN_JOIN_NUM, param.getMinJoinNum()).set(GROUP_DRAW.PAY_MONEY,
            param.getPayMoney()).set(GROUP_DRAW.TO_NUM_SHOW, param.getToNumShow())
            .where(GROUP_DRAW.ID.eq(id)).execute();
    }

    /**
     * 活动明细
     */
    public GroupDrawListVo getGroupDrawById(Integer id) {
        GroupDrawListParam param = new GroupDrawListParam();
        param.setId(id);
        SelectConditionStep<Record17<Integer, String, Timestamp, Timestamp, Byte, Short, BigDecimal, Short, Short,
            Short, Byte, Short, Integer, Integer, Integer, String, Integer>> select = createSelect(param);
        GroupDrawListVo vo = select.fetchOne().into(GroupDrawListVo.class);
        transformStatus(vo);
        return vo;
    }

    /**
     * 列表查询
     */
    public PageResult<GroupDrawListVo> getGroupDrawList(GroupDrawListParam param) {
        SelectConditionStep<Record17<Integer, String, Timestamp, Timestamp, Byte, Short, BigDecimal, Short, Short,
            Short, Byte, Short, Integer, Integer, Integer, String, Integer>> select = createSelect(param);
        PageResult<GroupDrawListVo> result = getPageResult(select, param, GroupDrawListVo.class);
        List<GroupDrawListVo> dataList = result.getDataList();
        transformStatus(dataList);
        return result;
    }

    /**
     * 通用查询
     */
    private SelectConditionStep<Record17<Integer, String, Timestamp, Timestamp, Byte, Short, BigDecimal, Short, Short,
        Short, Byte, Short, Integer, Integer, Integer, String, Integer>> createSelect(GroupDrawListParam param) {
        SelectConditionStep<Record17<Integer, String, Timestamp, Timestamp, Byte, Short, BigDecimal, Short, Short, Short,
            Byte, Short, Integer, Integer, Integer, String, Integer>> select = shopDb().select(GROUP_DRAW.ID, GROUP_DRAW.NAME,
            GROUP_DRAW.END_TIME, GROUP_DRAW.START_TIME, GROUP_DRAW.IS_DRAW, GROUP_DRAW.JOIN_LIMIT, GROUP_DRAW.PAY_MONEY,
            GROUP_DRAW.LIMIT_AMOUNT, GROUP_DRAW.MIN_JOIN_NUM, GROUP_DRAW.OPEN_LIMIT, GROUP_DRAW.STATUS,
            GROUP_DRAW.TO_NUM_SHOW, DSL.count(JOIN_DRAW_LIST.USER_ID).as("joinUserCount"),
            DSL.count(JOIN_GROUP_LIST.USER_ID).filterWhere(JOIN_GROUP_LIST.STATUS.eq((byte) 1)).as("groupUserCount"),
            DSL.countDistinct(JOIN_GROUP_LIST.GROUP_ID).as("groupCount"), GROUP_DRAW.GOODS_ID,
            DSL.countDistinct(JOIN_DRAW_LIST.USER_ID).filterWhere(JOIN_DRAW_LIST.IS_WIN_DRAW.eq((byte) 1)).as("drawUserCount"))
            .from(GROUP_DRAW).leftJoin(JOIN_GROUP_LIST).on(GROUP_DRAW.ID.eq(JOIN_GROUP_LIST.GROUP_DRAW_ID))
            .leftJoin(JOIN_DRAW_LIST).on(GROUP_DRAW.ID.eq(JOIN_DRAW_LIST.GROUP_DRAW_ID)).where();
        buildOptions(select, param);
        select.groupBy(GROUP_DRAW.ID);
        return select;
    }

    /**
     * 查询条件
     */
    private void buildOptions(SelectConditionStep<Record17<Integer, String, Timestamp, Timestamp, Byte, Short,
        BigDecimal, Short, Short, Short, Byte, Short, Integer, Integer, Integer, String, Integer>> select,
                              GroupDrawListParam param) {
        String name = param.getActivityName();
        LocalDate startTime = param.getStartTime();
        LocalDate endTime = param.getEndTime();
        Byte status = param.getStatus();
        Integer id = param.getId();
        if (null != id) {
            select.and(GROUP_DRAW.ID.eq(id));
        }
        if (isNotEmpty(name)) {
            select.and(GROUP_DRAW.NAME.like(format("%s%%", name)));
        }
        if (null != startTime) {
            select.and(DSL.date(GROUP_DRAW.START_TIME).eq(Date.valueOf(startTime)));
        }
        if (null != endTime) {
            select.and(DSL.date(GROUP_DRAW.END_TIME).eq(Date.valueOf(endTime)));
        }
        if (null != status) {
            switch (status) {
                case GroupDrawListVo.ONGOING:
                    select.and(GROUP_DRAW.START_TIME.le(currentTimeStamp()))
                        .and(GROUP_DRAW.END_TIME.ge(currentTimeStamp()));
                    break;
                case GroupDrawListVo.NOT_STARTED:
                    select.and(GROUP_DRAW.START_TIME.greaterThan(currentTimeStamp()));
                    break;
                case GroupDrawListVo.FINISHED:
                    select.and(GROUP_DRAW.END_TIME.lessThan(currentTimeStamp()));
                    break;
                case GroupDrawListVo.DISABLED:
                    select.and(GROUP_DRAW.STATUS.eq(GROUP_DRAW_DISABLED));
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected status: " + status);
            }
            if (GroupDrawListVo.DISABLED != status) {
                select.and(GROUP_DRAW.STATUS.eq(GROUP_DRAW_ENABLED));
            }
        }
        select.and(GROUP_DRAW.DEL_FLAG.eq((byte) 0));
        select.orderBy(GROUP_DRAW.CREATE_TIME.desc());
    }

    /**
     * 状态转换
     */
    private void transformStatus(List<GroupDrawListVo> dataList) {
        dataList.parallelStream().forEach(this::transformStatus);
    }

    /**
     * 状态转换
     */
    private void transformStatus(GroupDrawListVo vo) {
        Byte status = vo.getStatus();
        Timestamp startTime = vo.getStartTime();
        Timestamp endTime = vo.getEndTime();
        String goodsId = vo.getGoodsId();
        // 活动状态判断
        switch (status) {
            case GROUP_DRAW_ENABLED:
                if (startTime.after(currentTimeStamp())) {
                    vo.setStatus(GroupDrawListVo.NOT_STARTED);
                } else if (startTime.before(currentTimeStamp()) && endTime.after(currentTimeStamp())) {
                    vo.setStatus(GroupDrawListVo.ONGOING);
                } else {
                    vo.setStatus(GroupDrawListVo.FINISHED);
                }
                break;
            case GROUP_DRAW_DISABLED:
                vo.setStatus(GroupDrawListVo.DISABLED);
                break;
        }
        // 商品数量
        int goodsCount = goodsId.split(",").length;
        vo.setGoodsCount(goodsCount);
        vo.setGoodsIds(stringToList(goodsId));
    }

    /**
     * 添加活动
     */
    public void addGroupDraw(GroupDrawAddParam param) {
        List<Integer> goodsIds = param.getGoodsIds();
        List<Integer> rewardCouponIds = param.getRewardCouponIds();
        if (null == goodsIds || goodsIds.isEmpty()) {
            throw new IllegalArgumentException("Goods ids is required");
        }
        if (null != rewardCouponIds && (!rewardCouponIds.isEmpty())) {
            param.setRewardCouponId(listToString(rewardCouponIds));
        }
        param.setGoodsId(listToString(goodsIds));
        shopDb().insertInto(GROUP_DRAW).set(createGroupDrawRecord(param)).execute();
    }

    /**
     * 获取活动record
     */
    private GroupDrawRecord createGroupDrawRecord(GroupDrawAddParam param) {
        return new GroupDrawRecord(null, param.getName(), param.getStartTime(),
            param.getEndTime(), param.getGoodsId(), param.getMinJoinNum(), param.getPayMoney(), param.getJoinLimit(),
            param.getOpenLimit(), param.getLimitAmount(), param.getToNumShow(), GROUP_DRAW_ENABLED, (byte) 1, null,
            null, (byte) 0, null, param.getRewardCouponId());
    }

    /**
     * 删除活动
     */
    public void deleteGroupDraw(Integer id) {
        shopDb().update(GROUP_DRAW).set(GROUP_DRAW.DEL_FLAG, (byte) 1).where(GROUP_DRAW.ID.eq(id)).execute();
    }

    /**
     * List 转 String
     */
    private String listToString(List<Integer> rewardCouponIds) {
        return rewardCouponIds.stream().map(String::valueOf).collect(Collectors.joining(","));
    }

    /**
     * String 转 List
     */
    private List<Integer> stringToList(String goodsId) {
        return Arrays.stream(goodsId.split(",")).map(Integer::valueOf).collect(Collectors.toList());
    }

    /**
     * 当前时间戳
     */
    private Timestamp currentTimeStamp() {
        return new Timestamp(new java.util.Date().getTime());
    }
}
