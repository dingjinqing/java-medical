package com.vpu.mp.service.pojo.wxapp.store;

import com.vpu.mp.db.shop.tables.records.StoreOrderRecord;
import com.vpu.mp.service.pojo.shop.member.account.AccountParam;
import com.vpu.mp.service.pojo.shop.member.account.ScoreParam;
import com.vpu.mp.service.pojo.shop.member.card.CardConsumpData;
import lombok.Builder;
import lombok.Data;

/**
 * @author liufei
 * @date 11/28/19
 */
@Data
@Builder
public class StoreOrderTran {
    private AccountParam account;
    private CardConsumpData cardConsumpData;
    private ScoreParam scoreParam;
    private StoreOrderRecord storeOrder;
}
