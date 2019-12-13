package com.vpu.mp.service.shop.image.postertraits;

import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.PictorialRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import static com.vpu.mp.db.shop.tables.Pictorial.PICTORIAL;

/**
 *
 * @author zhaojianqiang
 *
 *         2019年10月17日 下午5:19:04
 */
@Service
public class PictorialService extends ShopBaseService {

	public PictorialRecord getCanUserPictorial(Integer userId, Integer goodsId, Byte action) {
		return db()
				.selectFrom(PICTORIAL).where(
						PICTORIAL.USER_ID.eq(userId)
								.and(PICTORIAL.IDENTITY_ID.eq(goodsId)
										.and(PICTORIAL.ACTION.eq(action).and(PICTORIAL.DEL_FLAG.eq((byte) 0)))))
				.fetchAny();
	}

}
