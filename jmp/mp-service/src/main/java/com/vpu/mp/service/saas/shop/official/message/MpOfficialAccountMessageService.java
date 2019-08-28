package com.vpu.mp.service.saas.shop.official.message;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.service.foundation.jedis.JedisManager;
import com.vpu.mp.service.foundation.service.MainBaseService;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.official.message.MpTemplateConfig;
import com.vpu.mp.service.saas.shop.official.MpOfficialAccountService;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpTemplateMsgService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplate;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateIndustry;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

@Service
public class MpOfficialAccountMessageService extends MainBaseService {

	protected static final String needIndustryId1 = "1";
	protected static final String needIndustryId2 = "2";
	protected static final String industryFirst = "IT科技";
	protected static final String industrySecond = "互联网/电子商务";



	@Autowired
	protected MpOfficialAccountService accountService;

	@Autowired
	protected JedisManager jedis;

	/**
	 * 设置行业
	 * 
	 * @param appId
	 * @throws WxErrorException
	 */
	public void setNeededIndustry(String appId) throws WxErrorException {
		WxMpTemplateMsgService service = accountService.getOfficialAccountClient(appId).getTemplateMsgService();
		WxMpTemplateIndustry originIndustry = service.getIndustry();
		if (industryFirst.equals(originIndustry.getPrimaryIndustry().getFirstClass())
				&& industrySecond.equals(originIndustry.getPrimaryIndustry().getSecondClass())
				|| industryFirst.equals(originIndustry.getSecondIndustry().getFirstClass())
						&& industrySecond.equals(originIndustry.getSecondIndustry().getSecondClass())

		) {
			return;
		}
		WxMpTemplateIndustry wxMpIndustry = new WxMpTemplateIndustry();
		wxMpIndustry.setPrimaryIndustry(new WxMpTemplateIndustry.Industry(needIndustryId1));
		wxMpIndustry.setSecondIndustry(new WxMpTemplateIndustry.Industry(needIndustryId2));
		service.setIndustry(wxMpIndustry);
	}

	/**
	 * 得到行业
	 * 
	 * @param appId
	 * @return
	 * @throws WxErrorException
	 */
	public WxMpTemplateIndustry getIndustry(String appId) throws WxErrorException {
		WxMpTemplateMsgService service = accountService.getOfficialAccountClient(appId).getTemplateMsgService();
		return service.getIndustry();
	}

	/**
	 * 发送模板消息
	 * 
	 * @param appId
	 * @param toUser
	 * @param keywordValues
	 * @param templateConfig
	 * @param mpAppId        对应小程序AppId
	 * @param page           小程序路径
	 * @param url            网页路径
	 */
	public void sendMpTemplateMessage(String appId, String toUser, List<WxMpTemplateData> keywordValues,
			MpTemplateConfig templateConfig, String mpAppId,
			String page, String url) throws WxErrorException {
        WxMpTemplateMessage.WxMpTemplateMessageBuilder messageBuilder = null;
		WxMpTemplateMsgService service = accountService.getOfficialAccountClient(appId).getTemplateMsgService();

		String key = "MP_TEMPLATE_KEY_" + Util.md5(Util.toJson(templateConfig) + "_" + appId);
		Integer timeOut = 3600 * 24 * 365;
		String templateId = jedis.get(key);
		if (templateId == null) {
			templateId = this.getTemplate(service, templateConfig.getTitle(), templateConfig.getContent());
			if (templateId == null) {
				templateId = this.addTemplate(service, templateConfig.getTemplateNo());
			}
			jedis.set(key, templateId, timeOut);
		}
        if( StringUtils.isNotBlank(url) ){
            messageBuilder  = WxMpTemplateMessage.builder().toUser(toUser).url(url)
                .templateId(templateId).data(keywordValues);
        }
		if (StringUtils.isNotBlank(page) && StringUtils.isNotBlank(mpAppId)) {
            page = page + ((page.indexOf("?") != -1 ? "&" : "?") + "rnd=" + Util.randomId());
            messageBuilder.miniProgram(new WxMpTemplateMessage.MiniProgram(mpAppId,page,true));
		}
		try {
			service.sendTemplateMsg(messageBuilder.build());
		} catch (WxErrorException e) {
			// template_id不正确，移除缓存，重新发送模板消息
			if (e.getError().getErrorCode() == 40037) {
				jedis.delete(key);
				sendMpTemplateMessage(appId, toUser, keywordValues, templateConfig, mpAppId,
						page, url);
			}else {
				throw new WxErrorException(e.getError(), e);
			}
		}
		/**
		 * TODO: 记录发送日志
		 * 		shop($this->getShopId())->serviceRequest->messageRecord->addSendMessageRecord($toUser, $templateConfig['id'], 1, $page, $templateType, $mpLinkIdentity, $template_content,0);
		 */
	}

	/**
	 * 得到title content对应的模板
	 * 
	 * @param service
	 * @param title
	 * @param content
	 * @return
	 * @throws WxErrorException
	 */
	public String getTemplate(WxMpTemplateMsgService service, String title, String content) throws WxErrorException {
		List<WxMpTemplate> list = service.getAllPrivateTemplate();
		for (WxMpTemplate template : list) {
			if (title.equals(template.getTitle())
					&& content.equals(template.getContent().replaceAll("\\r|\\n| ", ""))) {
				return template.getTemplateId();
			}
		}
		return null;
	}

	/**
	 * 添加模板
	 * 
	 * @param service
	 * @param templateNo
	 * @return
	 * @throws WxErrorException
	 */
	public String addTemplate(WxMpTemplateMsgService service, String templateNo) throws WxErrorException {
		return service.addTemplate(templateNo);
	}
}
