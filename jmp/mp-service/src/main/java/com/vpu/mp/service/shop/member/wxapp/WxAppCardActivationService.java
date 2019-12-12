package com.vpu.mp.service.shop.member.wxapp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.main.tables.records.DictCityRecord;
import com.vpu.mp.db.main.tables.records.DictDistrictRecord;
import com.vpu.mp.db.main.tables.records.DictProvinceRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.member.MemberEducationEnum;
import com.vpu.mp.service.pojo.shop.member.MemberIndustryEnum;
import com.vpu.mp.service.pojo.shop.member.account.UserCardVo;
import com.vpu.mp.service.pojo.shop.member.ucard.ActivateCardParam;
import com.vpu.mp.service.pojo.shop.member.ucard.ActivateCardVo;
import com.vpu.mp.service.pojo.wxapp.account.UserInfo;
import com.vpu.mp.service.shop.member.CardVerifyService;
import com.vpu.mp.service.shop.member.UserCardService;
import com.vpu.mp.service.shop.user.user.UserService;
/**
 * @author 黄壮壮
 * 	激活会员卡服务
 */
@Service
public class WxAppCardActivationService extends ShopBaseService {
	@Autowired
	private UserCardService userCardService;
	@Autowired
	private CardVerifyService cardVerifyService;
	@Autowired
	private UserService userService;
	
	/**
	 * 	获取会员卡激活数据
	 */
	public ActivateCardVo getActivationCard(ActivateCardParam param,String lang) {
		UserCardVo uCard = userCardService.getUserCardByCardNo(param.getCardNo());		
		List<String> fields = cardVerifyService.getActiveRequiredFieldWithHump(uCard.getActivationCfg());
		UserInfo user = userService.getUserInfo(param.getUserId());
		if(user == null) {
			return null;
		}
		Map<String, Object> userMap = Util.convertPojoToMap(user);
		userMap.entrySet().removeIf(e->!fields.contains(e.getKey()));
		dealWithAddressCode(userMap);
		
		List<String> allEducation = MemberEducationEnum.getAllEducation(lang);
		List<String> allIndustryName = MemberIndustryEnum.getAllIndustryName(lang);
		
		
		//TODO 订阅消息
		
		return ActivateCardVo
				.builder()
				.education(allEducation)
				.industryInfo(allIndustryName)
				.data(userMap)
				.fields(fields)
				.build();
	}
	
	private void dealWithAddressCode(Map<String, Object> userMap) {
		logger().info("处理用户地址信息");
		String provinceCode = "provinceCode";
		String cityCode = "cityCode";
		String districtCode = "districtCode";
		// 100000 110000 110100在省，市，区中都对应无效值
		Integer provinceId = userMap.get(provinceCode)==null? 100000:(Integer)userMap.get(provinceCode);
		Integer cityId = userMap.get(cityCode)==null?110000:(Integer)userMap.get(cityCode);
		Integer districtId = userMap.get(districtCode)==null?110100:(Integer)userMap.get(districtCode);	
		
		DictProvinceRecord provinceName = saas.region.province.getProvinceName(provinceId);	
		DictCityRecord cityName = saas.region.city.getCityName(cityId);
		DictDistrictRecord districtName = saas.region.district.getDistrictName(districtId);
		
		if(provinceName!=null) {
			userMap.put(provinceCode, provinceName.getName());
		}
		if(cityName != null) {
			userMap.put(cityCode, cityName.getName());
		}
		if(districtName != null) {
			userMap.put(districtCode, districtName.getName());
		}
	}
	
	

	/**
	 * 	设置会员卡激活数据
	 */
	public void setActivationCard(ActivateCardParam param) {
		
	}
	


}
