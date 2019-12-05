package com.vpu.mp.service.shop.user.message;

import static com.vpu.mp.db.shop.tables.SubscribeMessage.SUBSCRIBE_MESSAGE;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.vpu.mp.db.main.tables.records.MpAuthShopRecord;
import com.vpu.mp.db.shop.tables.records.SubscribeMessageRecord;
import com.vpu.mp.db.shop.tables.records.UserRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.wxapp.subscribe.TemplateVo;
import com.vpu.mp.service.pojo.wxapp.subscribe.UpdateTemplateParam;
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
	private String getMaAppId() {
		MpAuthShopRecord authShop = saas.shop.mp.getAuthShopByShopId(getShopId());
		if (null == authShop) {
			// 没有小程序，报错
			throw new RuntimeException("ShopId:" + getShopId() + "没有绑定小程序");
		}
		return authShop.getAppId();
	}

	/**
	 * 获得类目列表
	 * @return
	 * @throws WxErrorException
	 */
	private List<Integer> getcategoryList() throws WxErrorException {
		WxOpenMaSubScribeGetCategoryResult templateCategory = open.getMaExtService().getTemplateCategory(getMaAppId());
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
	 * 获取所需的类目Id ,找第一个符合的
	 * @return
	 * @throws WxErrorException 
	 */
	public Integer getcategoryId(String templateName) throws WxErrorException {
		List<Integer> getcategoryList = getcategoryList();
		if (getcategoryList == null) {
			// 抛错
			logger().info("AppId：" + getMaAppId() + " 获取类目失败");
			return 0;
		}
		//已经定义的类目ID
		Set<Integer> idList = SubscribeMessageConfig.getSecondIdList();
		int id=0;
		for (Integer ids : getcategoryList) {
			for (Integer haveId:idList) {
				if(ids.equals(haveId)) {
					id=haveId;
				}
			}
		}
		return id;
		
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
						.and(SUBSCRIBE_MESSAGE.STATUS.eq((byte) 1).and(SUBSCRIBE_MESSAGE.CAN_USE_NUM.gt(0))))
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
	public Boolean sendMessage(Integer userId,String templateName, Map<String, Map<String, String>> data,
			String page) throws WxErrorException {
		UserRecord user = userService.getUserByUserId(userId);
		if (null == user) {
			logger().info("userId：" + userId + " 在店铺：" + getShopId() + "不存在");
			return false;
		}
		
		
		// 类目ID
		Integer secondId = getcategoryId(templateName);
		if (secondId==0) {
			// 没有在现在版本定义的订阅消息中，直接发公众号
			logger().info("AppId：" + getMaAppId() + " 的账号对应类目ID：" + secondId + "不在当前版本定义的订阅消息中，准备发送公众号");
			// TODO发送到公众号
			return false;
		}
		// 获取当前帐号下的个人模板列表
		WxOpenMaSubScribeGetTemplateListResult templateList = open.getMaExtService().getTemplateList(getMaAppId());
		if (!templateList.isSuccess()) {
			logger().info("获取AppId：" + getMaAppId() + " 的账号下的个人模板失败，准备发送公众号");
			// TODO发送到公众号
			return false;
		}
		
		SubscribeMessageConfig config = SubscribeMessageConfig.getByTempleName(secondId, templateName);
		// 发送用的TemplateId
		SubscribeMessageRecord templateIdRecord = getCommonTemplateId(user, String.valueOf(config.getTid()));
		if (StringUtils.isEmpty(templateIdRecord)) {
			return false;
		}
		String templateId = templateIdRecord.getTemplateId();
		// 小程序中是否配置了这个模板
		templateId = addTemplate(templateIdRecord.getTemplateId(), config);

		WxOpenResult sendResult = open.getMaExtService().sendTemplate(getMaAppId(), user.getWxOpenid(), templateId, page,
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
	
	/**
	 * 返回前端需要的TemplateId
	 * @param data
	 * @return
	 * @throws WxErrorException
	 */
	public TemplateVo[] getTemplateId(String[] data) throws WxErrorException {
		//获取所有类目id
		List<Integer> getcategoryList = getcategoryList();
		SubscribeMessageConfig[] titleList=new SubscribeMessageConfig[data.length];
		for (Integer category : getcategoryList) {
			for (int i = 0; i < data.length; i++) {
				SubscribeMessageConfig byTempleName = SubscribeMessageConfig.getByTempleName(category, data[i]);
				if (byTempleName == null) {
					logger().info("appid：" + getMaAppId() + "。数据：" + data[i] + "在类目" + category + "暂时未定义");
				} else {
					logger().info("添加的TempleName为：" + byTempleName.getTitle()+"。类目为"+category);
					titleList[i]=byTempleName;
				}
			}
		}
		logger().info("titleList大小为"+titleList.length);
		logger().info(titleList.toString());
		TemplateVo[] results=new TemplateVo[data.length];
		WxOpenMaSubScribeGetTemplateListResult templateList = open.getMaExtService().getTemplateList(getMaAppId());
		List<WxOpenSubscribeTemplate> data2 = templateList.getData();
		if (data2.size() != 0) {
			for (int i = 0; i < titleList.length; i++) {
				for (WxOpenSubscribeTemplate template : data2) {
					boolean contains = template.getTitle().contains(titleList[i].getTitle());
					if (contains) {
						// 存在，直接赋值
						results[i]=new TemplateVo(template.getPriTmplId(), titleList[i].getId());
					} else {
						// 不存在，新建
						results[i]=new TemplateVo(addTemplate(titleList[i]), titleList[i].getId());
					}
				}
			}
		}else {
			for(int i=0;i<titleList.length;i++) {
				results[i]=new TemplateVo(addTemplate(titleList[i]), titleList[i].getId());
			}	
		}
		return results;
	}
	
	

	/**
	 * 更新表中可用数量
	 * @param userId
	 * @param param
	 */
	public void updateStatusAndNum(Integer userId,UpdateTemplateParam param) {
		List<TemplateVo> successs = param.getAccept();
		List<TemplateVo> rejects = param.getReject();
		List<TemplateVo> bans = param.getBan();
		for(TemplateVo success:successs) {
			SubscribeMessageRecord record = db().selectFrom(SUBSCRIBE_MESSAGE)
					.where(SUBSCRIBE_MESSAGE.USER_ID.eq(userId))
					.and(SUBSCRIBE_MESSAGE.TEMPLATE_ID.eq(success.getTemplateId())
							.and(SUBSCRIBE_MESSAGE.TEMPLATE_NO.eq(String.valueOf(success.getId()))))
					.fetchAny();
			if(record==null) {
				SubscribeMessageRecord insertRecord=db().newRecord(SUBSCRIBE_MESSAGE);
				insertRecord.setUserId(userId);
				UserRecord user = userService.getUserByUserId(userId);
				insertRecord.setWxOpenid(user.getWxOpenid());
				insertRecord.setTemplateId(success.getTemplateId());
				insertRecord.setTemplateNo(String.valueOf(success.getId()));
				int insert = insertRecord.insert();
				logger().info("成功的templateId："+success.getTemplateId()+"插入结果"+insert);		
			}else {
				record.setStatus((byte)1);
				record.setCanUseNum(record.getCanUseNum()+1);
				int update = record.update();
				logger().info("成功的templateId："+success.getTemplateId()+"更新结果"+update);				
			}
		}
		for (TemplateVo reject:rejects) {
			SubscribeMessageRecord rejrecord = db().selectFrom(SUBSCRIBE_MESSAGE)
					.where(SUBSCRIBE_MESSAGE.USER_ID.eq(userId))
					.and(SUBSCRIBE_MESSAGE.TEMPLATE_ID.eq(reject.getTemplateId())
							.and(SUBSCRIBE_MESSAGE.TEMPLATE_NO.eq(String.valueOf(reject.getId()))))
					.fetchAny();
			if(rejrecord!=null) {
				rejrecord.setStatus((byte)1);
				int update = rejrecord.update();
				logger().info("拒绝的templateId："+reject.getTemplateId()+"更新结果"+update);	
			}
		}
		
		for (TemplateVo ban:bans) {
			SubscribeMessageRecord rejrecord = db().selectFrom(SUBSCRIBE_MESSAGE)
					.where(SUBSCRIBE_MESSAGE.USER_ID.eq(userId))
					.and(SUBSCRIBE_MESSAGE.TEMPLATE_ID.eq(ban.getTemplateId())
							.and(SUBSCRIBE_MESSAGE.TEMPLATE_NO.eq(String.valueOf(ban.getId()))))
					.fetchAny();
			if(rejrecord!=null) {
				rejrecord.setStatus((byte)2);
				int update = rejrecord.update();
				logger().info("已被后台封禁的templateId："+ban.getTemplateId()+"更新结果"+update);	
			}
		}
	}
	/**
	 *  带校验的添加模板
	 * @param templateId
	 * @param config
	 * @return
	 * @throws WxErrorException
	 */
	public String addTemplate(String templateId, SubscribeMessageConfig config) throws WxErrorException {
		if (!checkTemplate(templateId)) {
			return addTemplate(config);
		}
		return templateId;
	}

	/**
	 * 添加模板
	 * @param config
	 * @return
	 * @throws WxErrorException
	 */
	private String addTemplate(SubscribeMessageConfig config) throws WxErrorException {
		WxOpenMaSubscribeAddTemplateResult addTemplate = open.getMaExtService().addTemplate(getMaAppId(),
				String.valueOf(config.getTid()), config.getKidList(), config.getTitle());
		logger().info("创建模板" + addTemplate.getErrmsg() + "  " + addTemplate.getErrcode());
		if (addTemplate.isSuccess()) {
			
			return addTemplate.getPriTmplId();
		}
		return null;
	}
	/**
	 * 当前帐号下的个人模板列表中是否有要用的templateId，有：true；没有false
	 * 
	 * @param templateId
	 * @return
	 * @throws WxErrorException
	 */
	public boolean checkTemplate(String templateId) throws WxErrorException {
		WxOpenMaSubScribeGetTemplateListResult templateList = open.getMaExtService().getTemplateList(getMaAppId());
		if (templateList.isSuccess()) {
			List<WxOpenSubscribeTemplate> data = templateList.getData();
			for (WxOpenSubscribeTemplate template : data) {
				if (template.getPriTmplId().equals(templateId)) {
					return true;
				}
			}
			return false;
		} else {
			logger().info("获取当前AppId：" + getMaAppId() + "下的个人模板列表失效");
			return false;
		}
	}

	/**
	 * 减少用户的订阅数
	 * 
	 * @param templateIdRecord
	 */
	public void decrementSubscribeNum(SubscribeMessageRecord templateIdRecord) {
		Integer canUseNum = templateIdRecord.getCanUseNum() - 1;
		templateIdRecord.setCanUseNum(canUseNum);
		if(canUseNum==0) {
			templateIdRecord.setStatus((byte)0);
		}
		int update = templateIdRecord.update();
		logger().info("减少用户订阅数" + update);
	}

	/**
	 * 增加用户的发送成功数
	 * 
	 * @param templateIdRecord
	 */
	public void incrementSubscribeNum(SubscribeMessageRecord templateIdRecord) {
		templateIdRecord.setSuccessNum(templateIdRecord.getSuccessNum() == null ? 0 : templateIdRecord.getSuccessNum() + 1);
		int update = templateIdRecord.update();
		logger().info("增加用户的发送成功数"+update);
	}

	/**
	 * 更新消息订阅状态
	 * 
	 * @param templateIdRecord
	 */
	public void modifySubscribeStatus(SubscribeMessageRecord templateIdRecord) {
		templateIdRecord.setStatus((byte) 0);
		int update = templateIdRecord.update();
		logger().info(" 更新消息订阅状态"+update);
	}
}
