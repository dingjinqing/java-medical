package com.vpu.mp.service.shop.market.groupdraw;

import com.vpu.mp.db.shop.tables.records.GroupDrawInviteRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import org.springframework.stereotype.Service;

import static com.vpu.mp.db.shop.tables.GroupDrawInvite.GROUP_DRAW_INVITE;

import java.util.Map;

/**
 * 拼团抽奖邀请用户
 *
 * @author 郑保乐
 */
@Service
public class GroupDrawInviteService extends ShopBaseService {

    /** 拼团抽奖 **/
    private static final int ACTION_GROUP_DRAW = 1;

    /**
     * 获取可用邀请用户信息
     */
    GroupDrawInviteRecord getAvailableInviteUser(Integer groupDrawId, Integer goodsId, Integer userId) {
        return db().selectFrom(GROUP_DRAW_INVITE).where(GROUP_DRAW_INVITE.ACTION.eq(ACTION_GROUP_DRAW)
            .and(GROUP_DRAW_INVITE.IDENTITY_ID.eq(groupDrawId))
            .and(GROUP_DRAW_INVITE.GOODS_ID.eq(goodsId))
            .and(GROUP_DRAW_INVITE.USER_ID.eq(userId))
            .and(GROUP_DRAW_INVITE.IS_USED.eq((byte) 0))).fetchOneInto(GROUP_DRAW_INVITE);
    }
    
    
    
    public void createInviteRecord(String path,Map<String, String> query,Byte isNew) {
    	GroupDrawInviteRecord newRecord = db().newRecord(GROUP_DRAW_INVITE);
    	newRecord.setIdentityId(Integer.parseInt(query.get("group_draw_id")));
    	newRecord.setPath(path);
    	newRecord.setGoodsId(Integer.parseInt(query.get("goods_id")));
    	newRecord.setGroupId(Integer.parseInt(query.get("group_id")));
    	newRecord.setInviteUserId(Integer.parseInt(query.get("invite_id")));
    	newRecord.setUserId(Integer.parseInt(query.get("user_id")));
    	newRecord.setIsNew(isNew);
    	newRecord.update();
    }
}
