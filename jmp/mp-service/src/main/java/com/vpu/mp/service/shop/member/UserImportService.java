package com.vpu.mp.service.shop.member;

import static com.vpu.mp.db.shop.Tables.SHOP_CFG;
import static com.vpu.mp.db.shop.Tables.USER_IMPORT;
import static com.vpu.mp.db.shop.Tables.USER_IMPORT_DETAIL;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.poifs.filesystem.FileMagic;
import org.apache.poi.ss.usermodel.Workbook;
import org.jooq.Result;
import org.jooq.SelectWhereStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vpu.mp.db.main.tables.records.DictCityRecord;
import com.vpu.mp.db.shop.tables.records.MemberCardRecord;
import com.vpu.mp.db.shop.tables.records.ShopCfgRecord;
import com.vpu.mp.db.shop.tables.records.UserImportDetailRecord;
import com.vpu.mp.db.shop.tables.records.UserImportRecord;
import com.vpu.mp.db.shop.tables.records.UserRecord;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.excel.ExcelFactory;
import com.vpu.mp.service.foundation.excel.ExcelReader;
import com.vpu.mp.service.foundation.excel.ExcelTypeEnum;
import com.vpu.mp.service.foundation.excel.ExcelUtil;
import com.vpu.mp.service.foundation.excel.ExcelWriter;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.FieldsUtil;
import com.vpu.mp.service.foundation.util.IdentityUtils;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.distribution.DistributorGroupListVo;
import com.vpu.mp.service.pojo.shop.member.userImp.CardInfoVo;
import com.vpu.mp.service.pojo.shop.member.userImp.SetNoticeJson;
import com.vpu.mp.service.pojo.shop.member.userImp.SetNoticeParam;
import com.vpu.mp.service.pojo.shop.member.userImp.UIGetListParam;
import com.vpu.mp.service.pojo.shop.member.userImp.UIGetListVo;
import com.vpu.mp.service.pojo.shop.member.userImp.UIGetNoActListParam;
import com.vpu.mp.service.pojo.shop.member.userImp.UIGetNoActListVo;
import com.vpu.mp.service.pojo.shop.member.userImp.UserImportActivePojo;
import com.vpu.mp.service.pojo.shop.member.userImp.UserImportErroPojo;
import com.vpu.mp.service.pojo.shop.member.userImp.UserImportParam;
import com.vpu.mp.service.pojo.shop.member.userImp.UserImportPojo;
import com.vpu.mp.service.shop.member.dao.CardDaoService;
import com.vpu.mp.service.shop.member.excel.UserImExcelWrongHandler;
import com.vpu.mp.service.shop.user.user.UserService;

/**
 * 会员导入
 * 
 * @author zhaojianqiang
 * @time 下午1:51:15
 */
@Service
public class UserImportService extends ShopBaseService {
	@Autowired
	private UserService userService;
	@Autowired
	private CardDaoService cardDaoService;

	private static final String PHONEREG = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
	private static final String USER_IMPORT_NOTICE = "user_import_notice";
	private static final BigDecimal ZERO = new BigDecimal("0");
	private static final String[] sexs = { "男", "女" };
	private static final String[] marriages = { "未婚", "已婚", "保密" };
	private static final String[] educations = { "初中", "高中", "中专", "大专", "本科", "硕士", "博士", "其他" };
	private static final String[] industrys = { "计算机硬件及网络设备", "计算机软件", "IT服务（系统/数据/维护）/多领域经营", "互联网/电子商务", "网络游戏",
			"通讯（设备/运营/增值服务）", "电子技术/半导体/集成电路", "仪器仪表及工业自动化", "金融/银行/投资/基金/证券", "保险", "房地产/建筑/建材/工程", "家居/室内设计/装饰装潢",
			"物业管理/商业中心", "广告/会展/公关/市场推广", "媒体/出版/影视/文化/艺术", "印刷/包装/造纸", "咨询/管理产业/法律/财会", "教育/培训", "检验/检测/认证", "中介服务",
			"贸易/进出口", "零售/批发", "快速消费品（食品/饮料/烟酒/化妆品", "耐用消费品（服装服饰/纺织/皮革/家具/家电）", "办公用品及设备", "礼品/玩具/工艺美术/收藏品",
			"大型设备/机电设备/重工业", "加工制造（原料加工/模具）", "汽车/摩托车（制造/维护/配件/销售/服务）", "交通/运输/物流", "医药/生物工程", "医疗/护理/美容/保健", "医疗设备/器械",
			"酒店/餐饮", "娱乐/体育/休闲", "旅游/度假", "石油/石化/化工", "能源/矿产/采掘/冶炼", "电气/电力/水利", "航空/航天", "学术/科研", "政府/公共事业/非盈利机构",
			"环保", "农/林/牧/渔", "跨领域经营", "其它" };
	private static final Byte ONE = 1;

	private static final String DATE_FORMATE = "yyyy/MM/dd";

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
		if (couponIds != null) {
			json.setMrkingVoucherId(setMrkingVoucher(couponIds));
		}
		json.setScore(StringUtils.isEmpty(score) ? "" : score);

		String json2 = Util.toJson(json);
		int setShopCfg = setShopCfg(USER_IMPORT_NOTICE, json2);
		return setShopCfg > 0 ? JsonResultCode.CODE_SUCCESS : JsonResultCode.CODE_FAIL;
	}

	/**
	 * 获取模板
	 * 
	 * @param lang
	 * @return
	 */
	public Workbook getTemplate(String lang) {
		List<UserImportPojo> list = new ArrayList<UserImportPojo>();
		UserImportPojo vo = new UserImportPojo();
		vo.setMobile("15093037027");
		vo.setName("李建鹏");
		vo.setInviteUserMobile("18700000000");
		vo.setScore(1000);
		vo.setSex("男");
		vo.setBirthday("2019/12/30");
		vo.setProvince("北京");
		vo.setCity("北京市");
		vo.setDistrict("海淀区");
		vo.setAddress("天波中润");
		vo.setIdNumber("450328198102039022");
		vo.setEducation("初中");
		vo.setIndustry("互联网/电子商务");
		vo.setMarriage("未婚");
		vo.setIncome(new BigDecimal("100"));
		vo.setIsDistributor("1");
		list.add(vo);
		return getModel(lang, list);
	}

	public Boolean insertUser(String lang, UserImportParam param) {
		MultipartFile multipartFile = param.getFile();
		ExcelTypeEnum type = checkFile(multipartFile);
		if (type == null) {
			// 文件类型不正确，请上传Excel文件
			return false;
		}

		Workbook workbook = null;
		try {
			InputStream inputStream = multipartFile.getInputStream();
			workbook = ExcelFactory.createWorkbook(inputStream, type);
		} catch (IOException e) {
			logger().info("excel读取错误");
			logger().info(e.getMessage(), e);
			return false;
		}
		/**
		 * excel解析错误处理器
		 */
		UserImExcelWrongHandler handler = new UserImExcelWrongHandler();
		ExcelReader excelReader = new ExcelReader(lang, workbook, handler);
		List<UserImportPojo> models = excelReader.readModelList(UserImportPojo.class);
		checkList(models, param);
		return true;

	}

	public void checkList(List<UserImportPojo> list, UserImportParam param) {
		String cardId = param.getCardId();
		Integer groupId = param.getGroupId();
		Integer tagId = param.getTagId();
		int successNum = 0;
		int totalNum = list.size();
		for (UserImportPojo userImportPojo : list) {
			String mobile = userImportPojo.getMobile();
			if (StringUtils.isEmpty(mobile)) {
				logger().info("手机号为空");
				userImportPojo.setErrorMsg("手机号为空");
				continue;
			}
			if (!Pattern.matches(PHONEREG, mobile)) {
				logger().info("手机号格式错误");
				userImportPojo.setErrorMsg("手机号格式错误");
				continue;
			}
			UserRecord userRecord = userService.getUserByMobile(mobile);
			if (userRecord != null) {
				logger().info("会员手机号已存在");
				userImportPojo.setErrorMsg("会员手机号已存在");
				continue;
			}
			String name = userImportPojo.getName();

			if (StringUtils.isNotEmpty(name)) {
				if (name.length() > 10) {
					logger().info("姓名限制10个字符");
					userImportPojo.setErrorMsg("姓名限制10个字符");
					continue;
				}
			}
			String inviteUserMobile = userImportPojo.getInviteUserMobile();
			if (StringUtils.isNotEmpty(inviteUserMobile)) {
				if (!Pattern.matches(PHONEREG, inviteUserMobile)) {
					logger().info("邀请人手机号格式错误");
					userImportPojo.setErrorMsg("邀请人手机号格式错误");
					continue;
				}
				UserRecord userByMobile = userService.getUserByMobile(inviteUserMobile);
				if (userByMobile == null) {
					logger().info("邀请人不存在");
					userImportPojo.setErrorMsg("邀请人不存在");
					continue;
				}
			}
			Integer score = userImportPojo.getScore();
			if (null == score) {
				logger().info("积分为空");
				userImportPojo.setErrorMsg("积分为空");
				continue;
			}
			if (score < 0) {
				logger().info("无效积分");
				userImportPojo.setErrorMsg("无效积分");
				continue;
			}

			String sex = userImportPojo.getSex();
			if (StringUtils.isNotEmpty(sex) && !checkRule(sexs, sex)) {
				logger().info("性别仅限男女");
				userImportPojo.setErrorMsg("性别仅限男女");
				continue;
			}
			String birthday = userImportPojo.getBirthday();
			System.out.println("生日");
			if (StringUtils.isNotEmpty(birthday)) {
				try {
					// ExcelUtil.DATE_FORMAT
					LocalDate parse = LocalDate.parse(birthday, DateTimeFormatter.ofPattern(DATE_FORMATE));
					userImportPojo.setBirthday(parse.toString());
				} catch (Exception e) {
					logger().info("生日日期格式错误");
					userImportPojo.setErrorMsg("生日日期格式错误");
					logger().info(e.getMessage(), e);
					continue;
				}
			}
			String province = userImportPojo.getProvince();
			String city = userImportPojo.getCity();
			String district = userImportPojo.getDistrict();
			userImportPojo.setCity(city);
			userImportPojo.setDistrict(district);
			boolean isProvince = StringUtils.isEmpty(province);
			boolean isCity = StringUtils.isEmpty(city);
			boolean isDistrict = StringUtils.isEmpty(district);
			if (isProvince || isCity || isDistrict) {
				logger().info("省，市，区需完整填写");
				userImportPojo.setErrorMsg("省，市，区需完整填写");
				continue;
			}
			if (!isProvince) {
				Integer provinceId = saas.region.province.getProvinceIdByName(province);
				if (provinceId == null) {
					logger().info("无效省份");
					userImportPojo.setErrorMsg("无效省份");
					continue;
				}
				if (!isCity) {
					DictCityRecord cityId = saas.region.city.getCityId(city, provinceId);
					if (cityId == null) {
						logger().info("无效市");
						userImportPojo.setErrorMsg("无效市");
						continue;
					}
					if (!isDistrict) {
						Integer districtId = saas.region.district.getDistrictIdByNameAndCityId(cityId.getCityId(),
								district);
						if (districtId == null) {
							logger().info("无效区");
							userImportPojo.setErrorMsg("无效区");
							continue;
						}
					}
				}
			}

			String idNumber = userImportPojo.getIdNumber();
			if (!IdentityUtils.isLegalPattern(idNumber)) {
				logger().info("无效身份证号");
				userImportPojo.setErrorMsg("无效身份证号");
				continue;
			}
			BigDecimal income = userImportPojo.getIncome();
			if (income != null && income.compareTo(ZERO) == -1) {
				logger().info("无效收入");
				userImportPojo.setErrorMsg("无效收入");
				continue;
			}
			String marriage = userImportPojo.getMarriage();
			if (StringUtils.isNotEmpty(marriage) && !checkRule(marriages, marriage)) {
				logger().info("无效婚姻状况");
				userImportPojo.setErrorMsg("无效婚姻状况");
				continue;
			}
			String education = userImportPojo.getEducation();
			if (StringUtils.isNotEmpty(education) && !checkRule(educations, education)) {
				logger().info("无效教育");
				userImportPojo.setErrorMsg("无效教育");
				continue;
			}
			String industry = userImportPojo.getIndustry();
			if (StringUtils.isNotEmpty(industry) && !checkRule(industrys, industry)) {
				logger().info("无效行业");
				userImportPojo.setErrorMsg("无效行业");
				continue;
			}
			successNum++;
		}
		// 可能存在id不正确
		UserImportRecord newRecord = db().newRecord(USER_IMPORT);
		newRecord.setSuccessNum(successNum);
		newRecord.setTotalNum(totalNum);
		newRecord.setCardId(cardId);
		newRecord.setTagId(tagId);
		newRecord.setGroupId(groupId);
		newRecord.insert();
		for (UserImportPojo userImportPojo2 : list) {
			UserImportDetailRecord record = db().newRecord(USER_IMPORT_DETAIL);
			record.setCardId(cardId);
			record.setTagId(tagId);
			record.setGroupId(groupId);
			FieldsUtil.assignNotNull(userImportPojo2, record);
			record.setBatchId(newRecord.getId());
			int insert = record.insert();
			logger().info("插入" + insert);
		}
	}

	private Boolean checkRule(String[] list, String mark) {
		for (String string : list) {
			if (string.equals(mark)) {
				return true;
			}
		}
		return false;
	}

	public ExcelTypeEnum checkFile(MultipartFile multipartFile) {
		if (multipartFile == null) {
			return null;
		}
		ExcelTypeEnum type = null;
		try {
			InputStream inputStream = multipartFile.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(inputStream);
			FileMagic fileMagic = FileMagic.valueOf(bis);
			if (Objects.equals(fileMagic, FileMagic.OLE2)) {
				type = ExcelTypeEnum.XLS;
			}
			if (Objects.equals(fileMagic, FileMagic.OOXML)) {
				type = ExcelTypeEnum.XLSX;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return type;
	}

	/**
	 * 返回Excel文件
	 * 
	 * @param lang
	 * @param list
	 * @return
	 */
	public Workbook getModel(String lang, List<UserImportPojo> list) {
		Workbook workbook = ExcelFactory.createWorkbook(ExcelTypeEnum.XLSX);
		ExcelWriter excelWriter = new ExcelWriter(lang, workbook);
		excelWriter.writeModelList(list, UserImportPojo.class);
		return workbook;
	}

	/**
	 * 错误信息的返回
	 * 
	 * @param lang
	 * @param list
	 * @return
	 */
	public Workbook getModelErrorMsg(String lang, List<UserImportErroPojo> list) {
		Workbook workbook = ExcelFactory.createWorkbook(ExcelTypeEnum.XLSX);
		ExcelWriter excelWriter = new ExcelWriter(lang, workbook);
		excelWriter.writeModelList(list, UserImportErroPojo.class);
		return workbook;
	}

	/**
	 * 查询激活成功
	 * 
	 * @param lang
	 * @param list
	 * @return
	 */
	public Workbook getModelActive(String lang, List<UserImportActivePojo> list) {
		Workbook workbook = ExcelFactory.createWorkbook(ExcelTypeEnum.XLSX);
		ExcelWriter excelWriter = new ExcelWriter(lang, workbook);
		excelWriter.writeModelList(list, UserImportActivePojo.class);
		return workbook;
	}

	/**
	 * 查询失败数据
	 * 
	 * @param batchId
	 * @return
	 */
	public List<UserImportErroPojo> getErrorMsgById(Integer batchId) {
		Result<UserImportDetailRecord> fetch = db().selectFrom(USER_IMPORT_DETAIL).where(USER_IMPORT_DETAIL.ERROR_MSG
				.isNotNull().or(USER_IMPORT_DETAIL.ERROR_MSG.eq("")).and(USER_IMPORT_DETAIL.BATCH_ID.eq(batchId)))
				.fetch();
		List<UserImportErroPojo> into = new ArrayList<UserImportErroPojo>();
		if (fetch != null) {
			into = fetch.into(UserImportErroPojo.class);
		}
		return into;
	}

	/**
	 * 查询激活成功数据
	 * 
	 * @param batchId
	 * @return
	 */
	public List<UserImportActivePojo> getActiveById(Integer batchId) {
		Result<UserImportDetailRecord> fetch = db().selectFrom(USER_IMPORT_DETAIL)
				.where(USER_IMPORT_DETAIL.IS_ACTIVATE.eq(ONE).and(USER_IMPORT_DETAIL.BATCH_ID.eq(batchId))).fetch();
		List<UserImportActivePojo> into = new ArrayList<UserImportActivePojo>();
		if (fetch != null) {
			into = fetch.into(UserImportActivePojo.class);
		}
		return into;
	}

	/**
	 * 返回失败的信息
	 * 
	 * @param batchId
	 * @param lang
	 * @return
	 */
	public Workbook getErrorMsg(Integer batchId, String lang) {
		return getModelErrorMsg(lang, getErrorMsgById(batchId));
	}

	/**
	 * 激活成功的用户信息
	 * 
	 * @param batchId
	 * @param lang
	 * @return
	 */
	public Workbook getActiveExcel(Integer batchId, String lang) {
		return getModelActive(lang, getActiveById(batchId));
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

	public PageResult<UIGetListVo> getList(UIGetListParam param) {
		SelectWhereStep<UserImportRecord> selectFrom = db().selectFrom(USER_IMPORT);
		Integer batchId = param.getBatchId();
		if (batchId != null) {
			selectFrom.where(USER_IMPORT.ID.eq(batchId));
		}
		Timestamp startTime = param.getStartTime();
		if (startTime != null) {
			selectFrom.where(USER_IMPORT.CREATE_TIME.ge(startTime));
		}
		Timestamp endTime = param.getEndTime();
		if (endTime != null) {
			selectFrom.where(USER_IMPORT.CREATE_TIME.le(endTime));
		}
		selectFrom.where(USER_IMPORT.TOTAL_NUM.gt(0));
		selectFrom.orderBy(USER_IMPORT.ID.desc());
		return this.getPageResult(selectFrom, param.getCurrentPage(), param.getPageRows(), UIGetListVo.class);
	}

	/**
	 * 会员导入列表
	 * 
	 * @param param
	 * @return
	 */
	public PageResult<UIGetListVo> descList(UIGetListParam param) {
		PageResult<UIGetListVo> list = getList(param);
		for (UIGetListVo vo : list.getDataList()) {
			vo.setFailNum(vo.getTotalNum() - vo.getSuccessNum());
			int activateNum = getActivateNum(vo.getId(), ONE);
			vo.setActivateNum(activateNum);
			String cardIds = vo.getCardId();
			if (StringUtils.isNotEmpty(cardIds)) {
				String[] caStrings = cardIds.split(",");
				List<CardInfoVo> cardList = new ArrayList<CardInfoVo>();
				for (String cardId : caStrings) {
					CardInfoVo cardVo = new CardInfoVo();
					MemberCardRecord cardInfo = cardDaoService.getInfoByCardId(Integer.parseInt(cardId));
					if (cardInfo != null) {
						cardVo.setCardId(cardInfo.getId());
						cardVo.setCardName(cardInfo.getCardName());
					}
					cardList.add(cardVo);
				}
				vo.setCardList(cardList);
			}
		}
		return list;
	}

	public int getActivateNum(Integer batchId, Byte isActivate) {
		return db().selectCount().from(USER_IMPORT_DETAIL)
				.where(USER_IMPORT_DETAIL.BATCH_ID.eq(batchId).and(USER_IMPORT_DETAIL.IS_ACTIVATE.eq(isActivate))).fetchOne(0, int.class);
	}

	/**
	 * 会员导入明细列表
	 * 
	 * @param param
	 * @return
	 */
	public PageResult<UIGetNoActListVo> getDetailList(UIGetNoActListParam param) {
		SelectWhereStep<UserImportDetailRecord> selectFrom = db().selectFrom(USER_IMPORT_DETAIL);
		selectFrom.where(USER_IMPORT_DETAIL.ERROR_MSG.isNull());
		Timestamp startTime = param.getStartTime();
		if (startTime != null) {
			selectFrom.where(USER_IMPORT_DETAIL.CREATE_TIME.ge(startTime));
		}
		Timestamp endTime = param.getEndTime();
		if (endTime != null) {
			selectFrom.where(USER_IMPORT_DETAIL.CREATE_TIME.le(endTime));
		}
		Byte isActivate = param.getIsActivate();
		if (isActivate != null) {
			selectFrom.where(USER_IMPORT_DETAIL.IS_ACTIVATE.eq(isActivate));
		}
		Integer batchId = param.getBatchId();
		if (batchId != null) {
			selectFrom.where(USER_IMPORT_DETAIL.BATCH_ID.eq(batchId));
		}
		String mobile = param.getMobile();
		if (StringUtils.isNotEmpty(mobile)) {
			selectFrom.where(USER_IMPORT_DETAIL.MOBILE.eq(mobile));
		}
		String realName = param.getRealName();
		if (StringUtils.isNotEmpty(realName)) {
			selectFrom.where(USER_IMPORT_DETAIL.NAME.eq(realName));
		}
		Byte isDistributor = param.getIsDistributor();
		if (isDistributor != null) {
			selectFrom.where(USER_IMPORT_DETAIL.IS_DISTRIBUTOR.eq(isDistributor));
		}
		Integer groupId = param.getGroupId();
		if (groupId != null) {
			selectFrom.where(USER_IMPORT_DETAIL.GROUP_ID.eq(groupId));
		}
		selectFrom.orderBy(USER_IMPORT_DETAIL.ID.desc());
		return this.getPageResult(selectFrom, param.getCurrentPage(), param.getPageRows(), UIGetNoActListVo.class);
	}

	public PageResult<UIGetNoActListVo> addGroupName(UIGetNoActListParam param) {
		PageResult<UIGetNoActListVo> detailList = getDetailList(param);
		for (UIGetNoActListVo vo : detailList.dataList) {
			DistributorGroupListVo oneInfo = saas.getShopApp(getShopId()).distributorGroup.getOneInfo(vo.getGroupId());
			if (oneInfo == null) {
				vo.setGroupName("分组已删除");
			} else {
				vo.setGroupName(oneInfo.getGroupName());
			}
		}
		return detailList;

	}
}
