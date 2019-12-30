package com.vpu.mp.service.shop.member;

import static com.vpu.mp.db.shop.Tables.SHOP_CFG;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.ShopCfgRecord;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.member.userImp.SetNoticeJson;
import com.vpu.mp.service.pojo.shop.member.userImp.SetNoticeParam;

/**
 * 会员导入
 * 
 * @author zhaojianqiang
 * @time 下午1:51:15
 */
@Service
public class UserImportService extends ShopBaseService {

	private static final String USER_IMPORT_NOTICE = "user_import_notice";

	/**
	 * 设置用户导入通知
	 * 
	 * @param param
	 * @return
	 */
	public JsonResultCode setActivationNotice(SetNoticeParam param) {
		String explain = param.getExplain();
		String score = param.getScore();
		int[] couponIds = param.getCouponIds();
		if (StringUtils.isEmpty(explain)) {
			// 请设置通知说明
			return JsonResultCode.CODE_EXPLAIN_MUST;
		}
		if (StringUtils.isEmpty(score) && couponIds.length == 0) {
			// 至少选择一种激活奖励
			return JsonResultCode.CODE_NEED_ONE;
		}
		SetNoticeJson json = new SetNoticeJson();
		json.setExplain(explain);
		json.setMrkingVoucherId(setMrkingVoucher(couponIds));
		json.setScore(StringUtils.isEmpty(score) ? "" : score);

		String json2 = Util.toJson(json);
		int setShopCfg = setShopCfg(USER_IMPORT_NOTICE, json2);
		return setShopCfg > 0 ? JsonResultCode.CODE_SUCCESS : JsonResultCode.CODE_FAIL;
	}

	private String setMrkingVoucher(int[] num) {
		int length = num.length;
		if (length != 0) {
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < length; i++) {
				builder.append(num[i]);
				if (i != length - 1) {
					builder.append(",");
				}
			}
			return builder.toString();
		}
		return "";

	}

	public int setShopCfg(String key, String value) {
		ShopCfgRecord record = db().selectFrom(SHOP_CFG).where(SHOP_CFG.K.eq(key)).fetchAny();
		int result = 0;
		if (record != null) {
			record.setV(value);
			result = record.update();
		} else {
			record = db().newRecord(SHOP_CFG);
			record.setK(key);
			record.setV(value);
			result = record.insert();
		}
		return result;
	}
}
