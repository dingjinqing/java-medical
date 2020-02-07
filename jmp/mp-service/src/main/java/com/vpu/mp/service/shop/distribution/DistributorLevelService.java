package com.vpu.mp.service.shop.distribution;

import com.vpu.mp.db.shop.tables.records.DistributorLevelRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.config.distribution.DistributionParam;
import com.vpu.mp.service.pojo.shop.distribution.*;
import org.jooq.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.vpu.mp.db.shop.Tables.*;
import static org.jooq.impl.DSL.count;
import static org.jooq.impl.DSL.sum;

/**
 * 分销员等级配置
 * @author 常乐
 * 2019年8月1日
 */
@Service
public class DistributorLevelService extends ShopBaseService{

	/**
	 * 分销员等级配置
	 * @return 
	 * @return
	 */
	public DistributorLevelCfgVo levelConfig() {
	    //获取每级分销员等级信息
		Integer level_info = db().selectCount()
				.from(DISTRIBUTOR_LEVEL)
				.where(DISTRIBUTOR_LEVEL.LEVEL_ID.eq((byte) 1))
				.fetchOne().into(Integer.class);
		
		//初始化没有默认等级，自动创建默认等级
		if(level_info == 0) {
			db().insertInto(DISTRIBUTOR_LEVEL,DISTRIBUTOR_LEVEL.LEVEL_ID,DISTRIBUTOR_LEVEL.LEVEL_NAME,DISTRIBUTOR_LEVEL.LEVEL_STATUS)
			.values((byte) 1,"普通分销员",(byte) 1).execute();
		}
		
		//获取全部等级信息
		List<DistributorLevelVo> allLevel = db().select().from(DISTRIBUTOR_LEVEL).fetch().into(DistributorLevelVo.class);
		HashMap<Byte, DistributorLevelVo> levelData = new HashMap<Byte, DistributorLevelVo>();
		for(DistributorLevelVo level : allLevel) {
			levelData.put(level.getLevelId(),level);
		}
		//等级用户数量
		List<DistributorLevelUserNumVo> levelUserNum = this.getUserDistributorLevel();

		DistributorLevelCfgVo distributorLevelCfg = new DistributorLevelCfgVo();
		distributorLevelCfg.setLavelData(allLevel);
		distributorLevelCfg.setLevelUserNum(levelUserNum);
		return distributorLevelCfg;
	}
	
	/**
	 * 获取等级用户的数量
	 * @return
	 */
	public List<DistributorLevelUserNumVo> getUserDistributorLevel() {
		SelectHavingStep<Record2<Byte, Integer>> sql = db().select(USER.DISTRIBUTOR_LEVEL,count().as("user_number")).from(USER).groupBy(USER.DISTRIBUTOR_LEVEL);
		
		DistributionParam fanliCfg = saas().getShopApp(getShopId()).config.distributionCfg.getDistributionCfg();
		
		if(fanliCfg.getJudgeStatus() == 1) {
			sql = ((SelectWhereStep<Record2<Byte, Integer>>) sql).where(USER.IS_DISTRIBUTOR.eq((byte) 1));
		}
		List<DistributorLevelUserNumVo> res = sql.fetch().into(DistributorLevelUserNumVo.class);
		return res;
	}

	/**
	 * 获得单个等级信息
	 * @param levelId
	 * @return
	 */
	public DistributorLevelVo getOneLevelInfo(Byte levelId) {
		DistributorLevelVo res = null;
		Record levelInfo = db().select().from(DISTRIBUTOR_LEVEL)
				.where(DISTRIBUTOR_LEVEL.LEVEL_ID.eq(levelId)).fetchOne();
		if(levelInfo != null){
			res = levelInfo.into(DistributorLevelVo.class);
		}
		return res;
	}
	
	/**
	 * 编辑保存分销员等级设置
	 * @param level
	 * @return
	 */
	public boolean updateLevel(DistributorLevelParam level) {
		DistributorLevelRecord record = db().newRecord(DISTRIBUTOR_LEVEL,level);
		return record.update() > 0 ? true : false;
	}

	/**
	 * 获取分销员等级配置信息
	 * @return
	 */
	public DistributorLevelListVo distributorLevelList(){
		List<DistributorLevelParam> lists = db().select(DISTRIBUTOR_LEVEL.ID, DISTRIBUTOR_LEVEL.LEVEL_ID, DISTRIBUTOR_LEVEL.LEVEL_NAME,
				DISTRIBUTOR_LEVEL.LEVEL_STATUS, DISTRIBUTOR_LEVEL.TOTAL_BUY_MONEY, DISTRIBUTOR_LEVEL.TOTAL_DISTRIBUTION_MONEY,
				DISTRIBUTOR_LEVEL.INVITE_NUMBER, DISTRIBUTOR_LEVEL.LEVEL_UP_ROUTE)
				.from(DISTRIBUTOR_LEVEL).fetch().into(DistributorLevelParam.class);
		DistributorLevelListVo level = new DistributorLevelListVo();
		for(DistributorLevelParam list : lists){
			int userNum = db().selectCount().from(USER).where(USER.DISTRIBUTOR_LEVEL.eq(list.getLevelId())).fetchOne().into(Integer.class);
			list.setUsers(userNum);
		}
		level.setLevelList(lists);
		return level;
	}

	/**
	 * 停用分销员等级配置
	 * @param id
	 * @return
	 */
	public int pauseDistributorLevel(Integer id){
		int res = db().update(DISTRIBUTOR_LEVEL).set(DISTRIBUTOR_LEVEL.LEVEL_STATUS,(byte)0).where(DISTRIBUTOR_LEVEL.ID.eq(id)).execute();
		return res;
	}

	/**
	 * 启用分销员等级配置
	 * @param id
	 * @return
	 */
	public int openDistributorLevel(Integer id){
		int res = db().update(DISTRIBUTOR_LEVEL).set(DISTRIBUTOR_LEVEL.LEVEL_STATUS,(byte)1).where(DISTRIBUTOR_LEVEL.ID.eq(id)).execute();
		return res;
	}
	
	/**
	 * 创建保存分销员等级配置
	 * @param level
	 * @return
	 */
	public int saveLevel(DistributorLevelParam level) {
		DistributorLevelRecord record = new DistributorLevelRecord();
		assign(level, record);
		return db().executeInsert(record);
	}
	
	/**
	 * 可升级的等级
	 * @param levelId
	 * @return 
	 * @return 
	 */
	public List<Byte> getLowerCanUpLevels(int levelId) {
		List<Byte> canUplevels = new ArrayList<Byte>();
		if(levelId <= 1) {
			canUplevels.add((byte)1);
			return canUplevels;
		}
		
		List<DistributorLevelVo> goingLevels = this.getGoingLevels();
		for(levelId = levelId - 1;levelId >= 1;levelId--) {
			for(DistributorLevelVo level : goingLevels) {
				if(levelId == level.getLevelId()) {
					if(level.getLevelUpRoute() == 1) {
						canUplevels.add((byte)levelId);
					}
				}
			}
		}
		return canUplevels;	
	}
	
	/**
	 * 获取启用中的等级
	 * @return
	 */
	public List<DistributorLevelVo> getGoingLevels() {
		List<DistributorLevelVo> res = db().select().from(DISTRIBUTOR_LEVEL)
				.where(DISTRIBUTOR_LEVEL.LEVEL_STATUS.eq((byte) 1)).orderBy(DISTRIBUTOR_LEVEL.LEVEL_ID)
				.fetch().into(DistributorLevelVo.class);
		return res;
	}
	
	/**
	 * 每级下用户id
	 * @param canUpLevel
	 * @return
	 */
	public List<Integer> getLevelUser(Byte canUpLevel) {
		 List<Integer> userIds = db().select(USER.USER_ID).from(USER).where(USER.DISTRIBUTOR_LEVEL.eq(canUpLevel)).fetch().into(Integer.class);
		 return userIds;
	}
	
	/**
	 * 手动升级改为自动升级分销员，等级更新为最低级
	 * @param updateUserIds
	 */
	public void updateLevelToOne(List<Integer> updateUserIds) {
		Result<Record> users = db().select().from(USER .leftJoin(DISTRIBUTOR_LEVEL)
				.on(USER.DISTRIBUTOR_LEVEL.eq(DISTRIBUTOR_LEVEL.LEVEL_ID)))
				.where(USER.USER_ID .in(updateUserIds))
				.and(DISTRIBUTOR_LEVEL.LEVEL_ID.ne((byte)1)).fetch();	
		Record levelInfo = db().select().from(DISTRIBUTOR_LEVEL).where(DISTRIBUTOR_LEVEL.LEVEL_ID.eq((byte)1)).fetchOne();	
		
		for(Record user : users) {
			Byte isGoUP = user.get(USER.DISTRIBUTOR_LEVEL) > 1 ? (byte)0 : (byte)1;
				
			db().insertInto(DISTRIBUTOR_LEVEL_RECORD,DISTRIBUTOR_LEVEL_RECORD.USER_ID,
					DISTRIBUTOR_LEVEL_RECORD.IS_GO_UP,DISTRIBUTOR_LEVEL_RECORD.OLD_LEVEL,
					DISTRIBUTOR_LEVEL_RECORD.OLD_LEVEL_NAME,DISTRIBUTOR_LEVEL_RECORD.NEW_LEVEL,
					DISTRIBUTOR_LEVEL_RECORD.NEW_LEVEL_NAME,DISTRIBUTOR_LEVEL_RECORD.UPDATE_NOTE)
			.values(user.get(USER.USER_ID),isGoUP,user.get(USER.DISTRIBUTOR_LEVEL),user.get(DISTRIBUTOR_LEVEL.LEVEL_NAME),(byte) 1,
					levelInfo.get(DISTRIBUTOR_LEVEL.LEVEL_NAME),"后台修改配置")
			.execute();
		}
	}
	
	public boolean updateUserLevel(List<Integer> upUserIds) {
		Result<Record> auto_levels = this.getAutoUpdateLevels();
		if(auto_levels.size() <= 0) {
			return true;
		}
		//获取分销员分销信息（分销金额）
		Result<Record4<Integer, Integer, Byte, String>> distributorInfo = this.getUserRebateLevelDetail(upUserIds);
		//获取邀请人的推广金额
		Result<Record2<Integer, BigDecimal>> distributorRebate = this.getDistributorRebate(upUserIds);
		
		//根据配置条件给分销员定等级
		for(Record4<Integer, Integer, Byte, String> upUser : distributorInfo) {
			//获取用户的订单和门店买单消费总额
			DistributorSpendVo user_spend = this.getTotalSpend(upUser.get(USER.USER_ID));
			for(Record level : auto_levels) {
				//不用升级
				if(upUser.get(USER.DISTRIBUTOR_LEVEL) > level.get(DISTRIBUTOR_LEVEL.LEVEL_ID)){
					continue;
				}
				
				//todo:用户重新定级
//				boolean compareInviteNumber = level.get(DISTRIBUTOR_LEVEL.INVITE_NUMBER) > 0 && upUser.get(USER_TOTAL_FANLI.SUBLAYER_NUMBER) > level.get(DISTRIBUTOR_LEVEL.INVITE_NUMBER);
//				boolean compareDistributionMoney = level.get(DISTRIBUTOR_LEVEL.TOTAL_DISTRIBUTION_MONEY) > 0 && distributorRebate.get(USER_FANLI_STATISTICS)
//				boolean compareFanliMoney = 
			}
		}
		return true;
	}
	
	/**
	 * 自动升级的等级
	 * @return
	 */
	public Result<Record> getAutoUpdateLevels() {
		Result<Record> auto_levels = db().select().from(DISTRIBUTOR_LEVEL)
				.where(DISTRIBUTOR_LEVEL.LEVEL_UP_ROUTE.eq((byte)0))
				.orderBy(DISTRIBUTOR_LEVEL.LEVEL_ID)
				.fetch();
		return auto_levels;
	}
	
	/**
	 * 	分销员分销信息
	 * @param upUserIds
	 * @return
	 */
	public Result<Record4<Integer, Integer, Byte, String>> getUserRebateLevelDetail(List<Integer> upUserIds) {
		Result<Record4<Integer, Integer, Byte, String>> distributorInfo = db().select(USER.USER_ID,USER_TOTAL_FANLI.SUBLAYER_NUMBER,USER.DISTRIBUTOR_LEVEL,DISTRIBUTOR_LEVEL.LEVEL_NAME)
				.from(USER.leftJoin(USER_TOTAL_FANLI).on(USER.USER_ID.eq(USER_TOTAL_FANLI.USER_ID))
				.leftJoin(DISTRIBUTOR_LEVEL).on(USER.DISTRIBUTOR_LEVEL.eq(DISTRIBUTOR_LEVEL.LEVEL_ID)))
				.where(USER.USER_ID.in(upUserIds)).fetch();
		return distributorInfo;
	}
	
	/**
	 * 邀请人推广金
	 * @param upUserIds
	 * @return
	 */
	public Result<Record2<Integer, BigDecimal>> getDistributorRebate(List<Integer> upUserIds) {
		 Result<Record2<Integer, BigDecimal>> rebeteInfo = db()
				.select(USER_FANLI_STATISTICS.FANLI_USER_ID,sum(USER_FANLI_STATISTICS.TOTAL_CAN_FANLI_MONEY).as("fanli_money"))
				.from(USER_FANLI_STATISTICS)
				.where(USER_FANLI_STATISTICS.FANLI_USER_ID.in(upUserIds))
				.and(USER_FANLI_STATISTICS.REBATE_LEVEL.eq((byte) 1))
				.groupBy(USER_FANLI_STATISTICS.FANLI_USER_ID)
				.fetch();
		 return rebeteInfo;
	}
	
	/**
	 * 获取用户的订单和门店买单消费总额
	 */
	public DistributorSpendVo getTotalSpend(int user_id) {
		//会员卡支付
		BigDecimal memeberCardBalance = db().select(sum(ORDER_INFO.MEMBER_CARD_BALANCE)).from(ORDER_INFO).where(ORDER_INFO.ORDER_STATUS.eq((byte) 6)).and(ORDER_INFO.USER_ID.eq(user_id)).fetchSingleInto(BigDecimal.class);
		logger().info("会员卡支付"+memeberCardBalance);
		//微信实际支付
		BigDecimal moneyPaid = db().select(sum(ORDER_INFO.MONEY_PAID)).from(ORDER_INFO).where(ORDER_INFO.PAY_CODE.in("balance","wxpay")).and(ORDER_INFO.ORDER_STATUS.eq((byte) 6)).and(ORDER_INFO.USER_ID.eq(user_id)).fetchSingleInto(BigDecimal.class);
		logger().info("微信实际支付"+moneyPaid);
		//余额支付
		BigDecimal useAccount = db().select(sum(ORDER_INFO.USE_ACCOUNT)).from(ORDER_INFO).where(ORDER_INFO.ORDER_STATUS.eq((byte) 6)).and(ORDER_INFO.USER_ID.eq(user_id)).fetchSingleInto(BigDecimal.class);
		logger().info("余额支付"+useAccount);
		//门店消费
		BigDecimal storeMemeberCardBalance = db().select(sum(STORE_ORDER.MEMBER_CARD_BALANCE)).from(STORE_ORDER).where(STORE_ORDER.ORDER_STATUS.eq((byte)1)).and(STORE_ORDER.USER_ID.eq(user_id)).fetchSingleInto(BigDecimal.class);
		BigDecimal storeMoneyPaid = db().select(sum(STORE_ORDER.MONEY_PAID)).from(STORE_ORDER).where(STORE_ORDER.ORDER_STATUS.eq((byte)1)).and(STORE_ORDER.USER_ID.eq(user_id)).fetchSingleInto(BigDecimal.class);
		BigDecimal storeUseAccount = db().select(sum(STORE_ORDER.USE_ACCOUNT)).from(STORE_ORDER).where(STORE_ORDER.ORDER_STATUS.eq((byte)1)).and(STORE_ORDER.USER_ID.eq(user_id)).fetchSingleInto(BigDecimal.class);
		logger().info("门店消费"+storeMemeberCardBalance);
		logger().info("门店消费"+storeMoneyPaid);
		logger().info("门店消费"+storeUseAccount);
		//门店预约
		BigDecimal serviceMoneyPaid = db().select(sum(SERVICE_ORDER.MONEY_PAID)).from(SERVICE_ORDER).where(SERVICE_ORDER.ORDER_STATUS.eq((byte)2)).and(SERVICE_ORDER.PAY_CODE.in("balance","wxpay")).and(SERVICE_ORDER.USER_ID.eq(user_id)).fetchSingleInto(BigDecimal.class);
		logger().info("门店预约"+serviceMoneyPaid);
		BigDecimal card = check(memeberCardBalance).add(check(storeMemeberCardBalance));
		BigDecimal paid = check(moneyPaid).add(check(storeMoneyPaid));
		BigDecimal account = check(useAccount).add(check(storeUseAccount));
		BigDecimal total = check(card).add(check(paid).add(check(account)).add(check(serviceMoneyPaid)));
		logger().info("card:"+card+"paid:"+paid+"account"+account+"total"+total);
		DistributorSpendVo spendInfo = new DistributorSpendVo();
		spendInfo.setCard(card);
		spendInfo.setPaid(paid);
		spendInfo.setAccount(account);
		spendInfo.setTotal(total);
		
		return spendInfo;
		
	}
	private BigDecimal check(BigDecimal bigDecimal) {
		if(null==bigDecimal) {
			return new BigDecimal(0);
		}
		return bigDecimal;
		
	}

    /**
     * 分销等等级配置，手动升级添加分销员
     * @param param
     * @return
     */
	public Integer addDistributorTOLevel(AddDistributorToLevelParam param){
        int result = db().update(USER)
            .set(USER.DISTRIBUTOR_LEVEL,(param.getLevel()))
            .where(USER.USER_ID.in(param.getUserIds()))
            .execute();
        return result;
    }

    /**
     * 获取下级用户数
     * @param userId
     * @return
     */
    public Integer getNextUser(Integer userId){
        Record1<Integer> record1 = db().selectCount().from(USER).where(USER.INVITE_ID.eq(userId)).fetchOne();
        if(record1 != null){
            return record1.into(Integer.class);
        }else{
            return 0;
        }
    }

    /**
     * 根据userId获取对应分组
     * @param userId
     * @return
     */
    public DistributorLevelVo getLevelByUser(Integer userId){
        Record record = db().select().from(USER.leftJoin(DISTRIBUTOR_LEVEL).on(USER.DISTRIBUTOR_LEVEL.eq(DISTRIBUTOR_LEVEL.LEVEL_ID)))
            .where(USER.USER_ID.eq(userId)).fetchOne();
        if(record != null){
            return record.into(DistributorLevelVo.class);
        }else{
            return null;
        }
    }

}
