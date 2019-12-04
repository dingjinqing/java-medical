package com.vpu.mp.service.shop.user.message;

import static com.vpu.mp.db.shop.tables.SubscribeMessage.SUBSCRIBE_MESSAGE;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.vpu.mp.db.main.tables.records.MpAuthShopRecord;
import com.vpu.mp.db.shop.tables.records.SubscribeMessageRecord;
import com.vpu.mp.db.shop.tables.records.UserRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.shop.user.message.maConfig.SubscribeMessageConfig;
import com.vpu.mp.service.shop.user.user.UserService;
import com.vpu.mp.service.wechat.OpenPlatform;
import com.vpu.mp.service.wechat.api.WxOpenMaSubscribeService;
import com.vpu.mp.service.wechat.bean.open.WxOpenMaSubScribeGetCategoryResult;
import com.vpu.mp.service.wechat.bean.open.WxOpenMaSubScribeGetCategoryResult.WxOpenSubscribeCategory;
import com.vpu.mp.service.wechat.bean.open.WxOpenMaSubScribeGetTemplateListResult;
import com.vpu.mp.service.wechat.bean.open.WxOpenMaSubScribeGetTemplateListResult.WxOpenSubscribeTemplate;
import com.vpu.mp.service.wechat.bean.open.WxOpenMaSubscribeAddTemplateResult;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.bean.result.WxOpenResult;

/**
 * 小程序订阅消息
 * 
 * @author zhaojianqiang
 *
 *         2019年12月4日 上午9:26:12
 */
@Service
public class SubscribeMessageService extends ShopBaseService {

	@Autowired
	protected OpenPlatform open;

	@Autowired
	private UserService userService;

	// 获取小程序的AppId
	public String getMaInfo() {
		MpAuthShopRecord authShop = saas.shop.mp.getAuthShopByShopId(getShopId());
		if (null == authShop) {
			// 没有小程序，报错
			throw new RuntimeException("ShopId:" + getShopId() + "没有绑定小程序");
		}
		return authShop.getAppId();
	}

	// 获得类目
	public List<Integer> getcategory() throws WxErrorException {
		WxOpenMaSubScribeGetCategoryResult templateCategory = open.getMaExtService().getTemplateCategory(getMaInfo());
		if (templateCategory != null) {
			List<Integer> list = new ArrayList<Integer>();
			for (WxOpenSubscribeCategory wxcat : templateCategory.getData()) {
				list.add(wxcat.getId());
			}
			return list;
		}
		return null;
	}

	/**
	 * 获取发送时候要的template_id
	 * 
	 * @param user
	 * @param templateNo
	 * @return
	 */
	public SubscribeMessageRecord getCommonTemplateId(UserRecord user, String templateNo) {
		SubscribeMessageRecord sRecord = db().selectFrom(SUBSCRIBE_MESSAGE)
				.where(SUBSCRIBE_MESSAGE.USER_ID.eq(user.getUserId())
						.and(SUBSCRIBE_MESSAGE.WX_OPENID.eq(user.getWxOpenid())
								.and(SUBSCRIBE_MESSAGE.TEMPLATE_NO.eq(templateNo)))
						.and(SUBSCRIBE_MESSAGE.STATUS.eq((byte) 1)))
				.fetchAny();
		if (sRecord != null) {
			// 存在，取
			return sRecord;
		}
		logger().info("userId:" + user.getUserId() + "的templateNo：" + templateNo + "没有记录，不发送信息");
		return null;
	}

	/**
	 * 发送订阅消息
	 * @param userId
	 * @param config
	 * @param data
	 * @param page
	 * @return
	 * @throws WxErrorException
	 */
	public Boolean sendMessage(Integer userId, SubscribeMessageConfig config, Map<String, Map<String, String>> data,
			String page) throws WxErrorException {
		UserRecord user = userService.getUserByUserId(userId);
		if (null == user) {
			logger().info("userId：" + userId + " 在店铺：" + getShopId() + "不存在");
			return false;
		}
		List<Integer> templateCategory = getcategory();
		if (templateCategory == null) {
			// 抛错
			logger().info("AppId：" + getMaInfo() + " 获取类目失败");
			return false;
		}
		// 类目ID
		Integer secondId = config.getId();
		if (!templateCategory.contains(secondId)) {
			// 没有在现在版本定义的订阅消息中，直接发公众号
			logger().info("AppId：" + getMaInfo() + " 的账号对应类目ID：" + secondId + "不在当前版本定义的订阅消息中，准备发送公众号");
			// TODO发送到公众号
			return false;
		}
		// 获取当前帐号下的个人模板列表
		WxOpenMaSubScribeGetTemplateListResult templateList = open.getMaExtService().getTemplateList(getMaInfo());
		if (templateList != null) {
			logger().info("获取AppId：" + getMaInfo() + " 的账号下的个人模板失败，准备发送公众号");
			// TODO发送到公众号
			return false;
		}
		// 发送用的TemplateId
		SubscribeMessageRecord templateIdRecord = getCommonTemplateId(user, String.valueOf(config.getTid()));
		if (StringUtils.isEmpty(templateIdRecord)) {
			return false;
		}
		String templateId = templateIdRecord.getTemplateId();
		// 小程序中是否配置了这个模板
		templateId = addTemplate(templateIdRecord.getTemplateId(), config);

		WxOpenResult sendResult = open.getMaExtService().sendTemplate(getMaInfo(), user.getWxOpenid(), templateId, page,
				data);
		boolean success = sendResult.isSuccess();
		logger().info("发送结果" + success);
		if (success) {
			decrementSubscribeNum(templateIdRecord);
			incrementSubscribeNum(templateIdRecord);
			return true;
		}
		// 用户拒绝接受消息，如果用户之前曾经订阅过，则表示用户取消了订阅关系
		if (sendResult.getErrcode().equals("43101")) {
			modifySubscribeStatus(templateIdRecord);
			return false;
		}
		return false;

	}

	// 添加模板
	public String addTemplate(String templateId, SubscribeMessageConfig config) throws WxErrorException {
		if (!checkTemplate(templateId)) {
			// 创建模板
			WxOpenMaSubscribeAddTemplateResult addTemplate = open.getMaExtService().addTemplate(getMaInfo(),
					String.valueOf(config.getTid()), config.getKidList(), config.getTitle());
			logger().info("创建模板" + addTemplate.getErrmsg() + "  " + addTemplate.getErrcode());
			if (addTemplate.isSuccess()) {
				return addTemplate.getPriTmplId();
			}
		}
		return templateId;
	}

	/**
	 * 当前帐号下的个人模板列表中是否有要用的templateId，有：true；没有false
	 * 
	 * @param templateId
	 * @return
	 * @throws WxErrorException
	 */
	public boolean checkTemplate(String templateId) throws WxErrorException {
		WxOpenMaSubScribeGetTemplateListResult templateList = open.getMaExtService().getTemplateList(getMaInfo());
		if (templateList.isSuccess()) {
			List<WxOpenSubscribeTemplate> data = templateList.getData();
			for (WxOpenSubscribeTemplate template : data) {
				if (template.getPriTmplId().equals(templateId)) {
					return true;
				}
			}
			return false;
		} else {
			logger().info("获取当前AppId：" + getMaInfo() + "下的个人模板列表失效");
			return false;
		}
	}

	/**
	 * 减少用户的订阅数
	 * 
	 * @param templateIdRecord
	 */
	public void decrementSubscribeNum(SubscribeMessageRecord templateIdRecord) {
		if (templateIdRecord.getCanUseNum() >= 1) {
			templateIdRecord.setCanUseNum(templateIdRecord.getCanUseNum() - 1);
			templateIdRecord.update();
		}
	}

	/**
	 * 增加用户的发送成功数
	 * 
	 * @param templateIdRecord
	 */
	public void incrementSubscribeNum(SubscribeMessageRecord templateIdRecord) {
		templateIdRecord.setCanUseNum(templateIdRecord.getCanUseNum() + 1);
		templateIdRecord.update();
	}

	/**
	 * 更新消息订阅状态
	 * 
	 * @param templateIdRecord
	 */
	public void modifySubscribeStatus(SubscribeMessageRecord templateIdRecord) {
		templateIdRecord.setStatus((byte) 0);
		templateIdRecord.update();
	}
}