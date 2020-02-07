package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.config.distribution.DistributionParam;
import com.vpu.mp.service.pojo.shop.decoration.DistributorApplyParam;
import com.vpu.mp.service.pojo.shop.distribution.*;
import com.vpu.mp.service.pojo.shop.member.MemberEducationEnum;
import com.vpu.mp.service.pojo.shop.member.MemberIndustryEnum;
import com.vpu.mp.service.pojo.shop.member.MemberMarriageEnum;
import com.vpu.mp.service.pojo.shop.member.data.IndustryVo;
import com.vpu.mp.service.shop.ShopApplication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 分销模块
 * @author 常乐
 * 2019年7月17日
 */
@RestController
@RequestMapping("/api")
public class AdminDistributionController extends AdminBaseController{
//	@Override
//    protected ShopApplication shop() {
//        return saas.getShopApp(471752);
//    }
	//分销配置
	/**
	 * 获取分销配置
	 * @return
	 */
	@GetMapping("/admin/distribution/get")
	public JsonResult distributionCfg() {
		DistributionParam result = shop().config.distributionCfg.getDistributionCfg();
		return this.success(result);
	}
	
	/**
	 *设置分销配置
	 * @param param
	 * @return
	 */
	@PostMapping("/admin/distribution/set")
	public JsonResult setDistributionCfg(@RequestBody DistributionParam param) {
		int result = shop().config.distributionCfg.setDistributionCfg(param);
		return this.success(result);
	}

    /**
     * 获取推广文案二维码
     * @return
     * @throws Exception
     */
    @GetMapping("/admin/distribution/share/code")
    public JsonResult getDistributionShareCode() throws Exception {
        return success(shop().config.distributionCfg.getShareQrCode());
    }

	/**
	 * 获取分销推广文案
	 * @return
	 */
	@GetMapping("/admin/distribution/document/get")
	public JsonResult getDistributionDocument(){
		DistributionDocumentParam distributionDocument = shop().config.distributionCfg.getDistributionDocument();
		return this.success(distributionDocument);
	}

	/**
	 * 设置分销推广文案
	 * @return
	 */
	@PostMapping("/admin/distribution/document/set")
	public JsonResult setDistributionDocument(@RequestBody DistributionDocumentParam param){
		int result = shop().config.distributionCfg.setDistributionDocument(param);
		return this.success(result);
	}
	
	//返利策略配置
	/**
	 * 添加返利策略
	 * @param info
	 * @return
	 */
	@PostMapping("/admin/distribution/rebate/set")
	public JsonResult setRebateStrategy(@RequestBody DistributionStrategyParam info) {
		boolean result = shop().rebateStrategy.setRebateStrategy(info);
		if(result) {
			return this.success(result);
		}else {
			return this.fail();
		}
	}
	
	/**
	 * 返利策略分页列表
	 * @param param
	 * @return
	 */
	@PostMapping("/admin/distribution/rebate/list")
	public JsonResult rebateStrategyList(@RequestBody DistributionStrategyParam param) {
		PageResult<DistributionStrategyVo> list = shop().rebateStrategy.getStrategyList(param);
		return this.success(list);
	}
	
	/**
	 * 编辑返利策略
	 * @param id
	 * @return
	 */
	@GetMapping("/admin/distribution/rebate/edit")
	public JsonResult oneRebateStrategyInfo(Integer id) {
		List<DistributionStrategyParam> result = shop().rebateStrategy.getOneInfo(id);
		return this.success(result);
	}
	
	/**
	 * 返利策略编辑保存
	 * @param param
	 * @return
	 */
	@PostMapping("/admin/distribution/rebate/save")
	public JsonResult saveRebateStrategy(@RequestBody DistributionStrategyParam param) {
		boolean result = shop().rebateStrategy.saveRebateStrategy(param);
		return this.success(result);
	}
	
	/**
	 * 返利策略停用
	 * @param id
	 * @return
	 */
	@GetMapping("/admin/distribution/rebate/pause")
	public JsonResult pauseRebateStrategy(Integer id) {
		boolean result = shop().rebateStrategy.pauseRebate(id);
		if(result) {
			return this.success(result);
		}else {
			return this.fail();
		}
	}

	/**
	 * 返利策略启用
	 * @param id
	 * @return
	 */
	@GetMapping("/admin/distribution/rebate/open")
	public JsonResult openRebateStrategy(Integer id) {
		boolean result = shop().rebateStrategy.openRebate(id);
		if(result) {
			return this.success(result);
		}else {
			return this.fail();
		}
	}
	
	/**
	 * 返利策略删除
	 * @param id
	 * @return
	 */
	@GetMapping("/admin/distribution/rebate/delete")
	public JsonResult deleteRebateStrategy(Integer id) {
		boolean result = shop().rebateStrategy.deleteRebate(id);
		if(result) { 
			return this.success(result);
		}else {
			return this.fail();
		}
	}
	
	//分销员分组
	/**
	 * 分销员分组列表
	 * @param param
	 */
	@PostMapping("/admin/distribution/group/list")
	public JsonResult distributorGroupList(@RequestBody DistributorGroupListParam param) {
		PageResult<DistributorGroupListVo> groupList = shop().distributorGroup.getDistributorGroupList(param);
		return this.success(groupList);
	}
	
	/**
	 * 添加分销员分组
	 * @param param
	 * @return
	 */
	@PostMapping("/admin/distribution/group/add")
	public JsonResult distributorGroupAdd(@RequestBody DistributorGroupListParam param) {
		//判断是否存在该分组
		boolean isExists = shop().distributorGroup.isExistGroup(param);
		if(isExists) {
			return this.fail(JsonResultCode.DISTRIBUTOR_GROUP_NAME_EXIST);
		}
		boolean result = shop().distributorGroup.adddistributorGroup(param);
		if(result) {
			return this.success(result);
		}else {
			return this.fail();
		}
	}
	
	
	/**
	 * 设置默认分组
	 * @param id
	 * @return
	 */
	@GetMapping("/admin/distribution/group/default")
	public JsonResult setDefaultGroup(Integer id) {
		boolean result = shop().distributorGroup.setDefault(id);
		if(result) {
			return this.success(result);
		}else {
			return this.fail();
		}
	}
	
	/**
	 * 取消默认分组
	 * @param id
	 * @return
	 */
	@GetMapping("/admin/distribution/group/cancle")
	public JsonResult cancleDefaultGroup(Integer id) {
		boolean result = shop().distributorGroup.cancleDefault(id);
		return this.success(result);
	}
	
	/**
	 * 编辑分组，获取单条信息
	 * @param id
	 * @return
	 */
	@GetMapping("/admin/distribution/group/edit")
	public JsonResult distributorGroupEdit(Integer id) {
		DistributorGroupListVo info = shop().distributorGroup.getOneInfo(id);
		return this.success(info);
	}
	/**
	 * 编辑保存分销分组
	 * @param param
	 * @return
	 */
	@PostMapping("/admin/distribution/group/edit")
	public JsonResult distributorGroupSave(@RequestBody DistributorGroupListParam param) {
		int res = shop().distributorGroup.groupSave(param);
		return this.success(res);
	}
	
	/**
	 * 删除分组
	 * @param id
	 * @return
	 */
	@GetMapping("/admin/distribution/group/del")
	public JsonResult distributorGroupDel(Integer id) {
		boolean result = shop().distributorGroup.delGroup(id);
		if(result) {
			return this.success(result);
		}else {
			return this.fail();
		}
	}
	
	/**
	 * 分销员分组添加分销员
	 * @param param
	 * @return
	 */
	@PostMapping("/admin/distribution/distributor/add")
	public JsonResult addDistributorToGroup(@RequestBody AddDistributorToGroupParam param) {
		boolean res = shop().distributorGroup.addDistributorGroup(param);
		return this.success(res);
	}
	
	//分销员等级配置
	/**
	 * 分销员等级配置列表
	 * @return
	 */
	@GetMapping("/admin/distribution/level/config")
	public JsonResult distributorLevelConfig() {
		DistributorLevelCfgVo levelCfg = shop().distributorLevel.levelConfig();
		return this.success(levelCfg);
	}

	/**
	 * 分销员等级配置设置
	 * @param levelData
	 * @return
	 */
	@PostMapping("/admin/distribution/level/save")
	public JsonResult saveDistributorLevel(@RequestBody DistributorLevelParam[] levelData) {
		for(DistributorLevelParam level : levelData) {
			//各等级信息
			DistributorLevelVo levelInfo = shop().distributorLevel.getOneLevelInfo(level.getLevelId());
			//定义需要重新定等级的用户
			List<Integer> upUserIds = new ArrayList<Integer>();
			
			if(levelInfo != null) {
				if(levelInfo.getLevelStatus() == 1) { //等级启用中
					if(level.getLevelUpRoute() == 0 && level.getInviteNumber() == 0 && level.getTotalDistributionMoney() == "0" && level.getTotalBuyMoney() == "0") {
						return this.fail();//已启用等级设置不能为空
					}
					
					//编辑配置保存
					level.setId(levelInfo.getId());
					boolean res = shop().distributorLevel.updateLevel(level);
					
					//配置是否有更新
					boolean noChange = levelInfo.getInviteNumber().equals(level.getInviteNumber())
									&& levelInfo.getTotalBuyMoney().equals(level.getTotalBuyMoney())
									&& levelInfo.getTotalDistributionMoney().equals(level.getTotalDistributionMoney());
					
					//自动升级更改
					if(res && level.getLevelUpRoute() == 0 && !noChange) {
						//可自动升级等级
						List<Byte> canUpLevels = shop().distributorLevel.getLowerCanUpLevels(level.getLevelId());
						for(Byte canUpLevel : canUpLevels) {	
							//该等级下用户ID
							List<Integer> userIds = shop().distributorLevel.getLevelUser(canUpLevel);
							upUserIds.addAll(userIds);
						}
						//原来为手动升级，更新为自动升级，该级别的分销员回到第一级重新定级
						if(levelInfo.getLevelUpRoute() == 1) {
							List<Integer> updateUserIds = shop().distributorLevel.getLevelUser(levelInfo.getLevelId());
							shop().distributorLevel.updateLevelToOne(updateUserIds);
							upUserIds.addAll(updateUserIds);
						} 
					} 
					
					//自动升级改为手动升级
					if(res && level.getLevelUpRoute() == 1 && levelInfo.getLevelUpRoute() != 1) {
						List<Integer> updateUserIds = shop().distributorLevel.getLevelUser(levelInfo.getLevelId());
						shop().distributorLevel.updateLevelToOne(updateUserIds);
						upUserIds.addAll(updateUserIds);
					}
				}else { //未启用，直接更新数据
					level.setId(levelInfo.getId());
					shop().distributorLevel.updateLevel(level);
				}
			}else {//没有数据，插入数据
				System.out.println(level);
				shop().distributorLevel.saveLevel(level);
			}
			//受影响的等级用户重新定级
			if(upUserIds.size() > 0) {
				shop().distributorLevel.updateUserLevel(upUserIds);
			}
		}
		return this.success();
	}

	/**
	 * 获取分销员等级配置信息
	 * @return
	 */
	@PostMapping("/admin/distribution/level/list")
	public JsonResult DistributorLevelList(){
		DistributorLevelListVo res = shop().distributorLevel.distributorLevelList();
		return this.success(res);
	}

	/**
	 * 分销员等级配置停用
	 * @param id
	 * @return
	 */
	@GetMapping("/admin/distribution/level/pause")
	public JsonResult pauseDistributorLevel(Integer id){
		int res = shop().distributorLevel.pauseDistributorLevel(id);
		return this.success(res);
	}

	/**
	 * 分销员等级配置启用
	 * @param id
	 * @return
	 */
	@GetMapping("/admin/distribution/level/open")
	public JsonResult openDistributorLevel(Integer id){
		int res = shop().distributorLevel.openDistributorLevel(id);
		return this.success(res);
	}

    /**
     * 分销员等级配置，手动升级添加分销员
     * @param param
     * @return
     */
	@PostMapping("/admin/distribution/level/distributor/add")
    public JsonResult addDistributorTOLevel(@RequestBody AddDistributorToLevelParam param){
        int res = shop().distributorLevel.addDistributorTOLevel(param);
        return this.success(res);
    }

	/**
	 * 分销员列表
	 * @param param
	 * @return
	 */
	@PostMapping("/admin/distribution/distrobutor/list")
	public JsonResult distributorList(@RequestBody DistributorListParam param) {
		PageResult<DistributorListVo> distributorList = shop().distributorList.getPageList(param);
		return this.success(distributorList); 
	}
	
	/**
	 * 分销员已邀请用户列表
	 * @param param
	 * @return
	 */
	@PostMapping("/admin/distribution/invited/list")
	public JsonResult invitedList(@RequestBody DistributorInvitedListParam param) {
		PageResult<DistributorInvitedListVo> invitedlist = shop().distributorList.getInvitedList(param);
		return this.success(invitedlist);
	}
	
	/**
	 * 清除分销员身份
	 * @param userId
	 * @return
	 */
	@GetMapping("/admin/distribution/distributor/del")
	public JsonResult delDistributor(Integer userId) {
		int result = shop().distributorList.delDistributor(userId);
		return this.success(result);
	}
	
	/**
	 * 佣金统计
	 * @param param
	 * @return
	 */
	@PostMapping("/admin/distributio/brokerage/list")
	public JsonResult brokerageList(@RequestBody BrokerageListParam param) {
		PageResult<BrokerageListVo> list = shop().brokerage.getbrokerageList(param);
		return this.success(list);
	}
	
	/**
	 * 分销员等级列表
	 * @return
	 */
	@GetMapping("/admin/distribution/distributor/level")
	public JsonResult distributorLevelList() {
		List<DistributorLevelVo> levelList = shop().brokerage.getLevelList();
		return this.success(levelList);
	}
	
	/**
	 * 分销员分组列表
	 * @return
	 */
	@GetMapping("/admin/distribution/distributor/group")
	public JsonResult distributorGroupList() {
		List<DistributorGroupListVo> groupList = shop().brokerage.getGroupList();
		return this.success(groupList);
	}
	
	/**
	 * 商品返利统计
	 * @param param
	 * @return
	 */
	@PostMapping("/admin/distribution/rebate/goods/list")
	public JsonResult rebateGoodsList(@RequestBody RebateGoodsParam param) {
		PageResult<RebateGoodsVo> rebateGoodsList = shop().rebateGoods.getRebateGoods(param);
		return this.success(rebateGoodsList);
	}
	
	/**
	 * 商品返利明细
	 * @param param
	 * @return
	 */
	@PostMapping("/admin/distribution/rebate/goods/detail")
	public JsonResult rebateGoodsDetail(@RequestBody RebateGoodsDetailParam param) {
		PageResult<RebateGoodsDetailVo> detail = shop().rebateGoods.getRebateGoodsDetail(param);
		return this.success(detail);
	}
	
	//分销推广语
	/**
	 * 分销推广语列表
	 * @param param
	 * @return
	 */
	@PostMapping("/admin/distribution/promotion/list")
	public JsonResult promotionLanguageList(@RequestBody PromotionLanguageListParam param) {
		PageResult<PromotionLanguageListVo> promotionLanguageList = shop().promotionLanguage.getPromotionLanguageList(param);
		return this.success(promotionLanguageList);
	}
	
	/**
	 * 添加分销推广语
	 * @param param
	 * @return
	 */
	@PostMapping("/admin/distribution/promotion/add")
	public JsonResult promotionLanguageAdd(@RequestBody PromotionLanguageAddParam param) {
		int result = shop().promotionLanguage.addPromotionLanguage(param);
		return this.success(result);
	}
	
	/**
	 * 获取单条分销推广语信息
	 * @param id
	 * @return
	 */
	@GetMapping("/admin/distribution/promotion/edit")
	public JsonResult promotionLanguageEdit(Integer id) {
		PromotionLanguageListVo result = shop().promotionLanguage.getOnePromotion(id);
		return this.success(result);
	}
	
	/**
	 * 分销推广语编辑保存
	 * @param param
	 * @return
	 */
	@PostMapping("/admin/distribution/promotion/save")
	public JsonResult promotionLanguageSave(@RequestBody PromotionLanguageAddParam param) {
		int result = shop().promotionLanguage.savePromotionLanguage(param);
		return this.success(result);
	}
	
	/**
	 * 删除分销推广语，假删除
	 * @param id
	 * @return
	 */
	@GetMapping("/admin/distribution/promotion/delete")
	public JsonResult promotionLanguageDelete(Integer id) {
		int result = shop().promotionLanguage.delPromotionLanguage(id);
		return this.success(result);
	}
	
	/**
	 * 停用分销推广语
	 * @param id
	 * @return
	 */
	@GetMapping("/admin/distribution/promotion/pause")
	public JsonResult promotionLanguagePause(Integer id) {
		int result = shop().promotionLanguage.pausePromotionLanguage(id);
		return this.success(result);
	}
	
	/**
	 *启用分销推广语
	 * @param id
	 * @return
	 */
	@GetMapping("/admin/distribution/promotion/open")
	public JsonResult promotionLanguageOpen(Integer id) {
		int result = shop().promotionLanguage.openPromotionLanguage(id);
		return this.success(result);
	}
	
	//分销提现审核
	/**
	 * 分销提现审核列表
	 * @param param
	 * @return
	 */
	@PostMapping("/admin/distribution/withdraw/list")
	public JsonResult withdrawList(DistributorWithdrawListParam param) {
		PageResult<DistributorWithdrawListVo> withdrawList = shop().withdraw.getWithdrawList(param);
		return this.success(withdrawList);
	}
	
	/**
	 * 提现审核详情
	 * @param id
	 * @return
	 */
	@GetMapping("/admin/distribution/withdraw/detail")
	public JsonResult withdrawDetail(Integer id) {
		DistributorWithdrawDetailVo detail = shop().withdraw.getWithdrawDetail(id);
		return this.success(detail);
	}

    /**
     * 分销员审核列表
     * @param param
     * @return
     */
    @PostMapping("/admin/distribution/distributor/check/list")
	public JsonResult distributorCheckList(@RequestBody DistributorCheckListParam param){
        PageResult<DistributorCheckListVo> distributorCheckList = shop().distributorCheck.getDistributorCheckList(param);
        System.out.print(distributorCheckList.dataList);
        for(DistributorCheckListVo list:distributorCheckList.dataList){
            //转换行业码对应的名称
            if(list.getCheckField().getIndustryInfo() != null){
                String industryInfo = MemberIndustryEnum.getNameByCode(list.getCheckField().getIndustryInfo(),getLang());
                list.getCheckField().setIndustryName(industryInfo);
            }
            //教育程度
            if(list.getCheckField().getEducation() != null){
                String education = MemberEducationEnum.getNameByCode(list.getCheckField().getEducation(),getLang());
                list.getCheckField().setEducationName(education);
            }
            //性别
            if(list.getCheckField().getSex().equalsIgnoreCase("f")){
                list.getCheckField().setSex("女");
            }else if(list.getCheckField().getSex().equalsIgnoreCase("m")){
                list.getCheckField().setSex("男");
            }
            //婚姻状况
            if(list.getCheckField().getMaritalStatus() != null){
                String maritalInfo = MemberMarriageEnum.getNameByCode(list.getCheckField().getMaritalStatus(),getLang());
                list.getCheckField().setMaritalName(maritalInfo);
            }

        }
        return this.success(distributorCheckList);
    }

    /**
     * 分销员审核通过
     * @param param
     * @return
     */
    @PostMapping("/admin/distribution/distributor/check/pass")
    public JsonResult applyPass(@RequestBody DistributionApplyOptParam param){
        boolean res = shop().distributorCheck.applyPass(param);
        return this.success(res);
    }

    /**
     * 分销员审核拒绝
     * @param param
     * @return
     */
    @PostMapping("/admin/distribution/distributor/check/refuse")
    public JsonResult applyRefuse(@RequestBody DistributionApplyOptParam param){
        boolean res = shop().distributorCheck.applyRefuse(param);
        return this.success(res);
    }
}
