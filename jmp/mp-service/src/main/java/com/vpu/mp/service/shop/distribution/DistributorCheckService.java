package com.vpu.mp.service.shop.distribution;

import static com.vpu.mp.db.shop.Tables.DISTRIBUTOR_APPLY;
import static com.vpu.mp.db.shop.Tables.USER;
import static com.vpu.mp.db.shop.Tables.USER_DETAIL;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.distribution.DistributorCheckListParam;
import com.vpu.mp.service.pojo.shop.distribution.DistributorCheckListVo;

/**
 * 分销员审核service
 * @author 常乐
 * 2019年9月20日
 */
@Service
public class DistributorCheckService extends ShopBaseService{

	public List<DistributorCheckListVo> getDistributorCheckList(DistributorCheckListParam param) {
		List<DistributorCheckListVo> select = db().select(DISTRIBUTOR_APPLY.fields()).select(USER.USERNAME,USER.MOBILE,USER.IS_DISTRIBUTOR,USER.INVITE_ID,USER_DETAIL.REAL_NAME)
				.from(DISTRIBUTOR_APPLY.leftJoin(USER).on(DISTRIBUTOR_APPLY.USER_ID.eq(USER.USER_ID))
				.leftJoin(USER_DETAIL).on(USER.USER_ID.eq(USER_DETAIL.USER_ID))).fetch().into(DistributorCheckListVo.class);
		System.out.println(select);
		return select;

	}
}
