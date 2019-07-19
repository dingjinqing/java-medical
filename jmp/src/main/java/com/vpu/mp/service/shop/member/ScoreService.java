package com.vpu.mp.service.shop.member;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.vpu.mp.service.shop.member.MemberService;
import com.vpu.mp.db.shop.tables.records.UserRecord;
import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.JsonResultCode;
import com.vpu.mp.service.pojo.shop.member.account.ScoreParam;
import static com.vpu.mp.db.shop.Tables.USER_SCORE;

/**
 * @author 黄壮壮 2019-07-19 15:03
 */
@Service
@Scope("prototype")
public class ScoreService extends BaseService {
	private MemberService member;

	public JsonResultCode updateMemberScore(ScoreParam param, Integer subAccountId, Integer userId, Byte tradeType,
			Byte tradeFlow) {

		// 1. 校验userId是否存在数据库中
		if (userId <= 0) {
			return JsonResultCode.CODE_MEMEBER_NOT_EXIST;
		}
		// 1.1 查询用户
		UserRecord dbUser = member.getUserRecordById(userId);

		if (dbUser == null) {
			return JsonResultCode.CODE_MEMEBER_NOT_EXIST;
		}

		// 2. 验证积分与数据库保持一致
		Integer score = param.getScore();
		Integer dBScore = dbUser.getScore();
		if (score != dBScore) {
			return JsonResultCode.CODE_MEMBER_SCORE_NOT_SAME;
		}

		String remark = param.getRemark();
		if (StringUtils.isEmpty(remark)) {
			remark = "管理员操作";
		}

		String flowOn = generateFlowNo();

		return JsonResultCode.CODE_SUCCESS;
	}

	/**
	 * 生成流水号
	 * 
	 * @return string
	 */
	private String generateFlowNo() {
		DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		String result = null;
		Random random = new Random();
		while (true) {
			LocalDateTime now = LocalDateTime.now();
			// 生成四位随机数字
			int num = random.nextInt(9000) + 1000;
			result = "S" + now.format(f) + num;

			// 确保流水号的唯一性
			int count = db().fetchCount(USER_SCORE, USER_SCORE.FLOW_NO.eq(result));

			if (count == 0) {
				break;
			}
		}

		return result;
	}

}
