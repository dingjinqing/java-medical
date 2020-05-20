package com.vpu.mp.service.shop.member;

import static com.vpu.mp.db.shop.Tables.CARD_EXAMINE;
import static com.vpu.mp.db.shop.Tables.USER;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.REFUSED;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.VERIFIED;
import static com.vpu.mp.service.pojo.shop.member.card.CardVerifyConstant.VSTAT_REFUSED;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vpu.mp.db.shop.tables.records.CardExamineRecord;
import com.vpu.mp.db.shop.tables.records.MemberCardRecord;
import com.vpu.mp.db.shop.tables.records.UserDetailRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.CardUtil;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.member.MemberEducationEnum;
import com.vpu.mp.service.pojo.shop.member.MemberIndustryEnum;
import com.vpu.mp.service.pojo.shop.member.builder.ActiveOverDueVoBuilder;
import com.vpu.mp.service.pojo.shop.member.builder.UserCardRecordBuilder;
import com.vpu.mp.service.pojo.shop.member.card.ActiveAuditParam;
import com.vpu.mp.service.pojo.shop.member.card.ActiveAuditVo;
import com.vpu.mp.service.pojo.shop.member.card.ActiveOverDueVo;
import com.vpu.mp.service.pojo.shop.member.card.CardBasicVo;
import com.vpu.mp.service.pojo.shop.member.card.CardVerifyConstant;
import com.vpu.mp.service.pojo.shop.member.card.CardVerifyResultVo;
import com.vpu.mp.service.pojo.shop.member.card.export.examine.CardExamineDownVo;
import com.vpu.mp.service.pojo.wxapp.card.param.CardCustomActionParam;
import com.vpu.mp.service.shop.card.msg.CardMsgNoticeService;
import com.vpu.mp.service.shop.member.dao.CardDaoService;
import com.vpu.mp.service.shop.member.dao.CardVerifyDaoService;
import com.vpu.mp.service.shop.member.wxapp.WxAppCardActivationService;
import com.vpu.mp.service.shop.user.user.UserDetailService;


/**
* @author 黄壮壮
* @Date: 2019年10月30日
* @Description: 会员卡审核服务操作功能
*/
@Service
public class CardVerifyService extends ShopBaseService {

	@Autowired public CardVerifyDaoService verifyDao;
	@Autowired public MemberCardService memberCardService;
	@Autowired public UserCardService userCardService;
	@Autowired public UserDetailService userDetailService;
	@Autowired public CardDaoService cardDaoSvc;
	@Autowired public CardMsgNoticeService cardMsgNoticeSvc;
	@Autowired public WxAppCardActivationService wxCardActSvc;
	/**
	 *  分页查询
	 */
	public PageResult<? extends Record> getPageList(ActiveAuditParam param) {
		return verifyDao.getVerifyPageList(param);
	}

	/**
	 * 审核通过激活会员卡
	 */
	public void passCardVerify(Integer id,String cardNo) {

		this.transaction(()->{
			updateCardVerifyRecord(id);
			updateUserCardByNo(cardNo);
			updateUserDetailAccordToVerifyData(id);
		});

        //TODO 模板消息
	}

    /**
	 * 	根据激活需要的信息，更新用户详情
	 */
	private void updateUserDetailAccordToVerifyData(Integer id) {
		logger().info("更新用户详情");
		UserDetailRecord userDetailRecord = getUserActiveData(id);
		if(userDetailRecord != null) {
			userDetailService.updateRow(userDetailRecord);
		}
	}

	private UserDetailRecord getUserActiveData(Integer id) {
		MemberCardRecord card = getCard(id);
		List<String> keyL = getActiveRequiredField(card.getActivationCfg());

        CardExamineRecord cEx = getCardExamineRecordById(id);
		// 准备数据
		Map<String, Object> cExMap = cEx.intoMap();
		cExMap.entrySet().removeIf(e->!keyL.contains(e.getKey()));
		cExMap.values().removeIf(Objects::isNull);

        if(cExMap.size()>0) {
            UserDetailRecord userDetailRecord = new UserDetailRecord();
			userDetailRecord.fromMap(cExMap);
			userDetailRecord.setUserId(cEx.getUserId());
			return userDetailRecord;
		}else {
			return null;
		}

	}

    /**
	 * 获取需要激活的信息有下划线
	 */
	public List<String> getActiveRequiredField(String activationCfg) {
		List<String> keyL = CardUtil.parseActivationCfg(activationCfg);
		formatKeyToUnderline(keyL);
		dealWithActivateBirthday(keyL);
		dealWithActivateAddress(keyL);
		return keyL;
	}
	
    /**
	 * 获取需要激活的信息有下划线
	 */
	public List<String> getActiveRequiredFieldWithHump(String activationCfg) {
		List<String> keyL = CardUtil.parseActivationCfg(activationCfg);
		dealWithActivateBirthday(keyL);
		dealWithActivateAddress(keyL);
		formatKeyToHump(keyL);
		return keyL;
	}
	

    /**
	 *  驼峰到下划线
	 */
	private static void formatKeyToUnderline(List<String> keyL) {
		for(int i=0;i<keyL.size();i++) {
			String v = Util.humpToUnderline(keyL.get(i));
			keyL.set(i, v);
		}
	}
	/**
	 * 下划线到驼峰
	 * @param keyL
	 */
	private static void formatKeyToHump(List<String> keyL) {
		for(int i=0;i<keyL.size();i++) {
			String v = Util.underlineToHump(keyL.get(i));
			keyL.set(i, v);
		}
	}

    private  static void dealWithActivateBirthday(List<String> keylist) {
		String birthDay = "birthday";
		if(keylist.contains(birthDay)) {
			keylist.removeIf("birthday"::equals);
			keylist.addAll(Arrays.asList("birthday_year","birthday_month","birthday_day"));
		}
	}
    
    private static void dealWithActivateAddress(List<String> keyList) {
    	String address = "address";
    	if(keyList.contains(address)) {
    		keyList.removeIf(address::equals);
    		keyList.addAll(new ArrayList<String>(Arrays.asList("province_code","city_code","district_code")));
    	}
    }
    

	/**
	 * 获取未处理的激活审核信息
	 */
	public List<CardExamineRecord> getUndealVerifyMsg(Integer cardId) {
		return verifyDao.selectUndealVerifyRecord(cardId);
	}


    /**
	 * 获取未处理的激活审核人数
	 */
	public Integer getUndealUserNum(Integer cardId) {
		return verifyDao.countUndealUser(cardId);
	}

    /**
     * 获取未处理的激活审核订单集合
     */
    public Set<Integer> getUndealUserNumSet(Integer cardId) {
        return verifyDao.countUndealUserSet(cardId);
    }

    /**
	 * 根据卡号，获取当前卡的审核状态
	 * @param cardNo 卡号
	 */
	public Byte getCardVerifyStatus(String cardNo){
		if(StringUtils.isBlank(cardNo)) {
			return VSTAT_REFUSED;
		}
		CardVerifyResultVo cardVerifyDaoService = verifyDao.getCardVerifyResult(cardNo);
		return cardVerifyDaoService != null?cardVerifyDaoService.getStatus():VSTAT_REFUSED;
	}

    /**
	 * 获取会员卡激活审核超时的最早的一条记录
	 */
	public CardExamineRecord getLastRecord(ActiveAuditParam param) {
		 CardExamineRecord lastRecord = verifyDao.getLastRecord(param);
		 return lastRecord != null?lastRecord: new CardExamineRecord();
	}

    /**
     * 获取会员卡激活审核超时的最早的一条记录
     */
    public CardExamineRecord getLastRecordCanNull(ActiveAuditParam param) {
        return verifyDao.getLastRecord(param);
    }

	/**
	 * 取会员卡激活审核超时的记录
	 */
	public ActiveOverDueVo getCardActVerifyOverDueRecord(ActiveAuditParam param) {
		CardExamineRecord lastRecord = getLastRecord(param);
		if(lastRecord.getCardId()!=null) {
			return ActiveOverDueVoBuilder.create().cardNum(0).build();
		}
		param.setCardId(lastRecord.getCardId());
		return ActiveOverDueVoBuilder
				.create()
				.cardNum(verifyDao.getCountOverDueRecord(param))
				.cardName(memberCardService.getCardById(param.getCardId()).getCardName())
				.cardId(lastRecord.getCardId())
				.build();

	}

    public CardExamineRecord getCardExamineRecordById(Integer id) {
		CardExamineRecord rec = verifyDao.selectRecordById(id);
		return rec!=null?rec: new CardExamineRecord();
	}

    private void updateCardVerifyRecord(Integer id) {
		logger().info("更新记录状态");
		verifyDao.updateCardVerify(id);
	}

    private void updateUserCardByNo(String cardNo) {
		logger().info("更新激活");
        userCardService.updateUserCardByNo(cardNo,
					UserCardRecordBuilder.create().activationTime(DateUtil.getLocalDateTime()).build());
	}

    private MemberCardRecord getCard(Integer id) {
		CardExamineRecord cardEx = getCardExamineRecordById(id);
		return memberCardService.getCardById(cardEx.getCardId());
	}
    
    
    public CardExamineRecord getStatusByNo(String cardNo) {
    	return verifyDao.getStatusByNo(cardNo);
    }
    
    /**
     * 	获取待审核的会员卡列表
     */
    public List<CardBasicVo> getCardExamineList() {
    	List<Integer> cardIds = db().selectDistinct(CARD_EXAMINE.CARD_ID)
    		.from(CARD_EXAMINE)
    		.where(CARD_EXAMINE.STATUS.eq(CardVerifyConstant.VSTAT_CHECKING))
    		.fetch(CARD_EXAMINE.CARD_ID);
    	if(cardIds==null || cardIds.size()==0) {
    		return Collections.<CardBasicVo>emptyList();
    	}else {
    		return cardDaoSvc.getCardBasicInfoById(cardIds.toArray(new Integer[cardIds.size()]));
    	}
    }
    
    
    /**
	 * 审核不通过
	 * 
	 * @param param
	 */
	public void rejectActivateAudit(ActiveAuditParam param) {
		CardExamineRecord record = setRejectData(param);
		int res = cardDaoSvc.updateCardExamine(record);
		if(res>0) {
			logger().info("发送订阅消息");
			CardExamineRecord re = cardDaoSvc.getCardExamineRecordById(record.getId());
			cardMsgNoticeSvc.sendAuditSuccessMsg(re);		
		}
	}
    
    
	/**
	 * 审核不通过数据
	 * 
	 * @param
	 * @return
	 */
	private CardExamineRecord setRejectData(ActiveAuditParam param) {
		CardExamineRecord record = new CardExamineRecord();
		record.setId(param.getId());
		record.setRefuseTime(DateUtil.getSqlTimestamp());
		record.setRefuseDesc(param.getRefuseDesc());
		record.setStatus(REFUSED);
		return record;
	}
    
    
	/**
	 * 审核通过数据
	 * 
	 * @param id
	 * @return
	 */
	private CardExamineRecord setPassData(Integer id, Timestamp now) {
		CardExamineRecord record = new CardExamineRecord();
		record.setId(id);
		record.setPassTime(now);
		record.setStatus(VERIFIED);
		return record;
	}
	
	
	/**
	 * 审核通过
	 * 
	 * @param param
	 * @return
	 */
	public void passActivateAudit(ActiveAuditParam param) {
		this.transaction(() -> {
			Timestamp now = DateUtil.getSqlTimestamp();
			logger().info("申请激活会员卡通过: " + now);
			// 更新card_examine 信息
			CardExamineRecord record = setPassData(param.getId(), now);
			cardDaoSvc.updateCardExamine(record);
			// 更新激活
			cardDaoSvc.updateUserCardByCardNo(param.getCardNo(), now);
		});
	}
	
	
	/**
	 * 分页查询激活审核信息
	 * 
	 * @param param
	 * @return
	 */
	public PageResult<ActiveAuditVo> getActivateAuditList(ActiveAuditParam param) {
		logger().info("分页查询激活信息");
		PageResult<? extends Record> results = getPageList(param);
		PageResult<ActiveAuditVo> res = new PageResult<>();
		res.setPage(results.getPage());
		List<ActiveAuditVo> myList = new ArrayList<>();
		
		if(results.dataList.size()>0) {
			// 所有的卡
			List<String> nos = results.dataList.stream().map(x->x.get(CARD_EXAMINE.CARD_NO)).distinct().collect(Collectors.toList());
			Map<String, MemberCardRecord> cardMap = cardDaoSvc.getCardByNo(nos.toArray(new String[0]));
			Map<String,List<String>> cardCfgMap = new HashMap<>();
			// 提前获取激活的选项
			for(Map.Entry<String, MemberCardRecord> entry: cardMap.entrySet()) {
				List<String> cfg = CardUtil.parseActivationCfg(entry.getValue().getActivationCfg());
				cardCfgMap.put(entry.getKey(), cfg);
			}
			for(Record record: results.dataList) {
				ActiveAuditVo vo = new ActiveAuditVo();
				vo.setCardNo(record.get(CARD_EXAMINE.CARD_NO));
				vo.setMobile(record.get(USER.MOBILE));
				vo.setUsername(record.get(USER.USERNAME));
				vo.setCreateTime(record.get(CARD_EXAMINE.CREATE_TIME));
				vo.setStatus(record.get(CARD_EXAMINE.STATUS));
				vo.setId(record.get(CARD_EXAMINE.ID));
				vo.setRefuseDesc(record.get(CARD_EXAMINE.REFUSE_DESC));
				
				
				// 激活数据项
				List<String> activationCfg = cardCfgMap.get(vo.getCardNo());
				for(String name: activationCfg) {
					if("birthday".equals(name)) {
						vo.setBirthDayDay(record.get(CARD_EXAMINE.BIRTHDAY_DAY));
						vo.setBirthDayMonth(record.get(CARD_EXAMINE.BIRTHDAY_MONTH));
						vo.setBirthDayYear(record.get(CARD_EXAMINE.BIRTHDAY_YEAR));
					}else if("address".equals(name)){
						vo.setProvinceCode(record.get(CARD_EXAMINE.PROVINCE_CODE));
						vo.setCityCode(record.get(CARD_EXAMINE.CITY_CODE));
						vo.setDistrictCode(record.get(CARD_EXAMINE.DISTRICT_CODE));
					}else {
						try {
							Object value = record.get(Util.humpToUnderline(name));
							PropertyUtils.setProperty(vo, name, value);
						} catch (Exception e) {
							// 该属性为空 
						}		
					}
				}
				
				// deal with custom option
				String customOpts = record.get(CARD_EXAMINE.CUSTOM_OPTIONS);
				if(!StringUtils.isBlank(customOpts)) {
					 List<CardCustomActionParam> opts = Util.json2Object(customOpts,new TypeReference<List<CardCustomActionParam>>() {
				        }, false);
					 
					 //	deal with picture links
					 for(CardCustomActionParam item: opts) {
						 String[] links = item.getPictureLinks();
						 if(null != links && links.length>0) {
							 for(int i=0;i<links.length;i++) {
								 links[i] = imageUrl(links[i]);
							 }
						 }
					 }
					 vo.setCustomOptions(opts);
				}
				myList.add(vo);
			}
		}
		// deal with industry and education
		for (ActiveAuditVo activeAuditVo : myList) {
			// education
			if(activeAuditVo.getEducation()!= null) {
				String educationStr = MemberEducationEnum.getNameByCode((int)activeAuditVo.getEducation());
				activeAuditVo.setEducationStr(educationStr);
			}
			// industry
			if(activeAuditVo.getIndustryInfo() != null) {
				String industry = MemberIndustryEnum.getNameByCode((int)activeAuditVo.getIndustryInfo());
				activeAuditVo.setIndustry(industry);
			}
			// deal address
			if(activeAuditVo.getCityCode()!=null && activeAuditVo.getCityCode()!=null) {
				Map<String,Object> adMap = new HashMap<>();
				adMap.put(WxAppCardActivationService.PROVINCE_CODE, activeAuditVo.getProvinceCode());
				adMap.put(WxAppCardActivationService.CITY_CODE, activeAuditVo.getCityCode());
				adMap.put(WxAppCardActivationService.DISTRICT_CODE, activeAuditVo.getDistrictCode());
				wxCardActSvc.dealWithAddressCode(adMap);
				activeAuditVo.setCity((String)adMap.get(WxAppCardActivationService.CITY_CODE));
				activeAuditVo.setProvince((String)adMap.get(WxAppCardActivationService.PROVINCE_CODE));
				activeAuditVo.setDistrict((String)adMap.get(WxAppCardActivationService.DISTRICT_CODE));
			}
			
		}
		res.setDataList(myList);
		return res;
	}
	
	/**
	 * 导出激活数据为excel
	 * @param param
	 */
	public void exportToExcel(ActiveAuditParam param) {
		logger().info("导出会员卡审核数据为excel");
		
		PageResult<? extends Record> results = getPageList(param);
		if(results.dataList!=null && results.dataList.size()>0) {
			List<CardExamineDownVo> modelData = new ArrayList<>();
			for(int i=0;i<results.dataList.size();i++) {
				Record record = results.dataList.get(i);
				CardExamineDownVo vo = record.into(CardExamineDownVo.class);
			
		
				
				// 地址
				
				
				// 受教育程度
				
				
				//	所在行业
				
				// 生日
				
				// 自定义权益
				
				// 审核时间
				
				//	审核人
				
				
				// 审核状态
			
			
			
			
			
			
				modelData.add(vo);
			
			
			
			}
			
			
			
			
			
		}
		
		
		
		
		
	}

}
