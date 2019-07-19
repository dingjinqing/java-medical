package com.vpu.mp.service.shop.distribution;

import static com.vpu.mp.db.shop.Tables.DISTRIBUTION_STRATEGY;

import java.sql.Timestamp;
import java.util.List;

import org.jooq.Record8;
import org.jooq.SelectConditionStep;
import org.jooq.SelectJoinStep;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.DistributionStrategyRecord;
import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.pojo.shop.distribution.DistributionStrategyParam;
import com.vpu.mp.service.pojo.shop.distribution.DistributionStrategyVo;

/**
 *返利策略配置
 * @author 常乐
 * 2019年7月18日
 */
@Service
@Scope("prototype")
public class RebateStrategyService extends BaseService{
	
	/**
	 * 添加返利策略
	 * @param info
	 * @return
	 */
	public boolean setRebateStrategy(DistributionStrategyParam info) {
		DistributionStrategyRecord record = new DistributionStrategyRecord();
		this.assign(info,record);
		return db().executeInsert(record) > 0 ? true : false;
	}
	
	/**
	 * 返利策略分页列表
	 * @return
	 */
	public PageResult<DistributionStrategyVo> getStrategyList(DistributionStrategyParam param) {
		SelectJoinStep<Record8<String, Timestamp, Timestamp, Double, Byte, Timestamp, Byte, Byte>> select = db()
				.select(DISTRIBUTION_STRATEGY.STRATEGY_NAME,DISTRIBUTION_STRATEGY.START_TIME,DISTRIBUTION_STRATEGY.END_TIME,DISTRIBUTION_STRATEGY.FANLI_RATIO,DISTRIBUTION_STRATEGY.STRATEGY_LEVEL,DISTRIBUTION_STRATEGY.CREATE_TIME,DISTRIBUTION_STRATEGY.STATUS,DISTRIBUTION_STRATEGY.DEL_FLAG)
				.from(DISTRIBUTION_STRATEGY);
		SelectConditionStep<Record8<String,Timestamp,Timestamp,Double,Byte,Timestamp,Byte,Byte>> sql = buildOptions(select,param);
		PageResult<DistributionStrategyVo> list = this.getPageResult(sql, param.currentPage, param.pageRows,DistributionStrategyVo.class);
		return list;
	}
	
	/**
	 * 返利策略列表按状态查询
	 * @param select
	 * @param param
	 * @return
	 */
	public SelectConditionStep<Record8<String,Timestamp,Timestamp,Double,Byte,Timestamp,Byte,Byte>> buildOptions(SelectJoinStep<Record8<String, Timestamp, Timestamp, Double, Byte, Timestamp, Byte, Byte>> select,DistributionStrategyParam param) {
		SelectConditionStep<Record8<String,Timestamp,Timestamp,Double,Byte,Timestamp,Byte,Byte>> sql = select.where(DISTRIBUTION_STRATEGY.DEL_FLAG.eq((byte) 0));
		
		Timestamp nowDate = new Timestamp(System.currentTimeMillis());
		
		if(param.getNav() != null) {
			switch(param.getNav()) {
				//进行中
				case 1:
					sql = sql.and(DISTRIBUTION_STRATEGY.START_TIME.le(nowDate))
							  .and(DISTRIBUTION_STRATEGY.END_TIME.ge(nowDate));
					break;
				//未开始
				case 2:
					sql = sql.and(DISTRIBUTION_STRATEGY.START_TIME.ge(nowDate));
					break;
				//已过期
				case 3:
					sql = sql.and(DISTRIBUTION_STRATEGY.END_TIME.le(nowDate));
					break;
				//已停用
				case 4:
					sql = sql.and(DISTRIBUTION_STRATEGY.STATUS.eq((byte) 1));
					break;
			}
		}
		return sql;
	}
	
	/**
	 * 编辑返利策略
	 * @param id
	 * @return
	 */
	public List<DistributionStrategyParam> getOneInfo(Integer id) {
		List<DistributionStrategyParam> info = db().select().from(DISTRIBUTION_STRATEGY)
				.where(DISTRIBUTION_STRATEGY.ID.eq(id))
				.fetch().into(DistributionStrategyParam.class);
		return info;
	}
	
	/**
	 * 返利策略编辑保存
	 * @param param
	 * @return
	 */
	public boolean saveRebateStrategy(DistributionStrategyParam param) {
		DistributionStrategyRecord record = new DistributionStrategyRecord();
		this.assign(param,record);
		return db().executeUpdate(record) > 0 ? true : false;
	}
	
	/**
	 * 返利策略停用
	 * @param id
	 * @return
	 */
	public boolean pauseRebate(Integer id) {
		int res = db().update(DISTRIBUTION_STRATEGY)
				.set(DISTRIBUTION_STRATEGY.STATUS,(byte) 1)
				.where(DISTRIBUTION_STRATEGY.ID.eq(id))
				.execute();
		return res > 0 ? true : false;
	}
	
	/**
	 * 返利策略删除
	 * @param id
	 * @return
	 */
	public boolean deleteRebate(Integer id) {
		int res = db().update(DISTRIBUTION_STRATEGY)
				.set(DISTRIBUTION_STRATEGY.DEL_FLAG,(byte) 1)
				.where(DISTRIBUTION_STRATEGY.ID.eq(id))
				.execute();
		return res > 0 ? true : false;
	}
}
