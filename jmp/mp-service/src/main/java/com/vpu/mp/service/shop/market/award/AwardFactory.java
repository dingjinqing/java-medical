package com.vpu.mp.service.shop.market.award;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import static com.vpu.mp.service.pojo.shop.market.increasepurchase.PurchaseConstant.BYTE_THREE;
import static com.vpu.mp.service.shop.store.store.StoreWxService.BYTE_TWO;
import static org.apache.commons.lang3.math.NumberUtils.BYTE_ONE;

/**
 * The type Award factory.
 *
 * @author liufei
 * @date 1 /14/20
 */
@Service
public class AwardFactory {
    private static ConcurrentHashMap<Byte, Award> awardFactory;

    @PostConstruct
    private void init() {
        awardFactory = new ConcurrentHashMap<>(8);
        awardFactory.put(BYTE_ONE, new ScoreAward());
        awardFactory.put(BYTE_TWO, new CouponAward());
        awardFactory.put(BYTE_THREE, new LotteryAward());

    }

    /**
     * Gets award.
     *
     * @param awardType the award type
     * @return the award
     */
    public static Award getAward(Byte awardType) {
        Award award = awardFactory.get(awardType);
        if (Objects.isNull(award)) {
            award = newAward(awardType);
            awardFactory.put(awardType, award);
        }
        return award;
    }

    private static Award newAward(Byte awardType) {
        switch (awardType) {
            case 0:
                return null;
            case 1:
                return new ScoreAward();
            case 2:
                return new CouponAward();
            case 3:
                return new LotteryAward();
            default:
                throw new IllegalStateException("Unexpected value: " + awardType);
        }
    }
}
