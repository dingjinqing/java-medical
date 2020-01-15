package com.vpu.mp.service.shop.market.bargain;

import com.vpu.mp.config.DomainConfig;
import com.vpu.mp.db.shop.Tables;
import com.vpu.mp.db.shop.tables.records.BargainRecord;
import com.vpu.mp.db.shop.tables.records.BargainRecordRecord;
import com.vpu.mp.db.shop.tables.records.BargainUserListRecord;
import com.vpu.mp.db.shop.tables.records.UserRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.excel.ExcelFactory;
import com.vpu.mp.service.foundation.excel.ExcelTypeEnum;
import com.vpu.mp.service.foundation.excel.ExcelWriter;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.saas.schedule.TaskJobsConstant;
import com.vpu.mp.service.pojo.shop.coupon.give.CouponGiveQueueParam;
import com.vpu.mp.service.pojo.shop.market.bargain.BargainUserExportVo;
import com.vpu.mp.service.pojo.shop.market.bargain.BargainUserListQueryParam;
import com.vpu.mp.service.pojo.shop.market.bargain.BargainUserListQueryVo;
import com.vpu.mp.service.pojo.shop.market.message.RabbitMessageParam;
import com.vpu.mp.service.pojo.shop.market.message.RabbitParamConstant;
import com.vpu.mp.service.pojo.shop.official.message.MpTemplateConfig;
import com.vpu.mp.service.pojo.shop.official.message.MpTemplateData;
import com.vpu.mp.service.pojo.shop.order.OrderInfoVo;
import com.vpu.mp.service.pojo.shop.user.message.MaTemplateData;
import com.vpu.mp.service.pojo.wxapp.market.bargain.BargainInfoVo;
import com.vpu.mp.service.pojo.wxapp.market.bargain.BargainUsersListParam;
import com.vpu.mp.service.pojo.wxapp.market.bargain.BargainUsersListVo;
import com.vpu.mp.service.shop.user.message.maConfig.SubcribeTemplateCategory;
import jodd.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.jooq.SelectWhereStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.vpu.mp.db.shop.tables.Bargain.BARGAIN;
import static com.vpu.mp.db.shop.tables.BargainRecord.BARGAIN_RECORD;
import static com.vpu.mp.db.shop.tables.BargainUserList.BARGAIN_USER_LIST;
import static com.vpu.mp.db.shop.tables.User.USER;
import static com.vpu.mp.db.shop.tables.UserDetail.USER_DETAIL;

/**
 * 帮砍价用户
 * @author 王兵兵
 *
 * 2019年7月26日
 */
@Service
public class BargainUserService extends ShopBaseService{

    @Autowired
    private DomainConfig domainConfig;

	public PageResult<BargainUserListQueryVo> getPageList(BargainUserListQueryParam param){
		SelectWhereStep<? extends Record> select = db().select(
				BARGAIN_USER_LIST.ID,USER.USERNAME,USER.MOBILE,BARGAIN_USER_LIST.CREATE_TIME,BARGAIN_USER_LIST.BARGAIN_MONEY
				).
				from(BARGAIN_USER_LIST).
				leftJoin(USER).on(BARGAIN_USER_LIST.USER_ID.eq(USER.USER_ID));
		select = this.buildOptions(select, param);
		select.where(BARGAIN_USER_LIST.RECORD_ID.eq(param.getRecordId())).orderBy(BARGAIN_USER_LIST.CREATE_TIME.desc());
		return getPageResult(select,param.getCurrentPage(),param.getPageRows(),BargainUserListQueryVo.class); 
	}
	
	private SelectWhereStep<? extends Record> buildOptions(SelectWhereStep<? extends  Record> select, BargainUserListQueryParam param) {
		if (param == null) {
			return select;
		}
		if(!StringUtils.isBlank(param.getUsername())) {
			select.where(USER.USERNAME.contains(param.getUsername()));
		}
		if(!StringUtils.isBlank(param.getMobile())) {
			select.where(USER.MOBILE.contains(param.getMobile()));
		}
		return select;
	}

	public Workbook exportBargainUserList(BargainUserListQueryParam param, String lang){
		SelectWhereStep<? extends Record> select = db().select(
				BARGAIN_USER_LIST.ID,USER.USERNAME,USER.MOBILE,BARGAIN_USER_LIST.CREATE_TIME,BARGAIN_USER_LIST.BARGAIN_MONEY
		).
				from(BARGAIN_USER_LIST).
				leftJoin(USER).on(BARGAIN_USER_LIST.USER_ID.eq(USER.USER_ID));
		select = this.buildOptions(select, param);
		select.where(BARGAIN_USER_LIST.RECORD_ID.eq(param.getRecordId())).orderBy(BARGAIN_USER_LIST.CREATE_TIME.desc());
		List<BargainUserExportVo> voList = select.fetchInto(BargainUserExportVo.class);
		Workbook workbook= ExcelFactory.createWorkbook(ExcelTypeEnum.XLSX);
		ExcelWriter excelWriter = new ExcelWriter(lang,workbook);
		excelWriter.writeModelList(voList,BargainUserExportVo.class);
		return workbook;
	}

    public BargainUsersListVo getWxPageList(BargainUsersListParam param){
        BargainUsersListVo vo = new BargainUsersListVo();
        SelectConditionStep<? extends Record> select = db().select(
            BARGAIN_USER_LIST.asterisk(),USER.MOBILE,USER.USERNAME,USER.WX_OPENID,USER_DETAIL.USER_AVATAR
        ).
            from(BARGAIN_USER_LIST).
            leftJoin(USER).on(BARGAIN_USER_LIST.USER_ID.eq(USER.USER_ID)).
            leftJoin(USER_DETAIL).on(BARGAIN_USER_LIST.USER_ID.eq(USER_DETAIL.USER_ID)).
            where(BARGAIN_USER_LIST.RECORD_ID.eq(param.getRecordId()));
        select.orderBy(BARGAIN_USER_LIST.CREATE_TIME.desc());
        PageResult<BargainUsersListVo.BargainUsers> res = getPageResult(select,param.getCurrentPage(),param.getPageRows(),BargainUsersListVo.BargainUsers.class);
        vo.setBargainUsers(res);
        vo.setTimestamp(DateUtil.getLocalDateTime());
        return vo;
    }

    /**
     * userId是否能给recordId砍一刀
     * @param userId
     * @param bargainRecord
     * @return 0正常 1该砍价已结束 2该用户已砍过 3已经砍到了底价
     */
	public byte canBargainCut(int userId,BargainRecordRecord bargainRecord,BargainRecord bargain){
	    if(!bargainRecord.getStatus().equals(BargainRecordService.STATUS_IN_PROCESS)){
	        return 1;
        }

        int userBargainNumber = getUserBargainNumber(userId,bargainRecord.getId());
        if(userId == bargainRecord.getUserId()){
            //给自己砍价，可以砍2次
            if(userBargainNumber > 1){
                return 2;
            }
        }else{
            //别人帮忙砍价，只能砍一次
            if(userBargainNumber > 0){
                return 2;
            }
        }

        if(bargain.getBargainType() == BargainService.BARGAIN_TYPE_RANDOM){
            //砍到区间可以结算类型
            BigDecimal remainMoney = bargainRecord.getGoodsPrice().subtract(bargainRecord.getBargainMoney()).subtract(bargain.getFloorPrice().compareTo(BigDecimal.ZERO) > 0 ? bargain.getFloorPrice() : BigDecimal.ZERO);
            if(remainMoney.compareTo(BigDecimal.ZERO) < 1){
                //已经砍到了底价
                return 3;
            }
        }else{
            //固定砍价人数和底价类型
            if(bargainRecord.getUserNumber() >= bargain.getExpectationNumber()){
                return 1;
            }
            BigDecimal remainMoney = bargainRecord.getGoodsPrice().subtract(bargainRecord.getBargainMoney()).subtract(bargain.getExpectationPrice().compareTo(BigDecimal.ZERO) > 0 ? bargain.getExpectationPrice() : BigDecimal.ZERO);
            if(remainMoney.compareTo(BigDecimal.ZERO) < 1){
                //已经砍到了底价
                return 3;
            }
        }

        return 0;
    }

    /**
     * userId给recordId砍一刀
     * @param userId
     * @param recordId
     * @return 是否成功
     */
    public BigDecimal addUserBargain(int userId,int recordId){
        BargainRecordRecord bargainRecord = db().selectFrom(BARGAIN_RECORD).where(BARGAIN_RECORD.ID.eq(recordId)).fetchAny();
        BargainRecord bargain = db().selectFrom(BARGAIN).where(BARGAIN.ID.eq(bargainRecord.getBargainId())).fetchAny();

        BigDecimal bargainMoney;
        if(bargain.getBargainType().equals(BargainService.BARGAIN_TYPE_RANDOM)){
            //区间内结算类型
            bargainMoney = getAbnormalBargainMoney(bargain,bargainRecord,userId);
        }else{
            //固定人数类型
            bargainMoney = getNormalBargainMoney(bargain,bargainRecord,userId);
        }

        //插入帮砍价记录
        BargainUserListRecord insertRecord = db().newRecord(BARGAIN_USER_LIST);
        insertRecord.setRecordId(recordId);
        insertRecord.setUserId(userId);
        insertRecord.setBargainMoney(bargainMoney);

        transaction(()->{
            insertRecord.insert();

            boolean isSuccess = bargain.getBargainType().equals(BargainService.BARGAIN_TYPE_RANDOM) ? (bargainRecord.getStatus().equals(BargainRecordService.STATUS_IN_PROCESS) && (bargainRecord.getGoodsPrice().subtract(bargainRecord.getBargainMoney().add(bargainMoney)).compareTo(bargain.getExpectationPrice()) <= 0)) : (bargain.getExpectationNumber() == (bargainRecord.getUserNumber() + 1));

            if(isSuccess){
                //砍价成功了
                //库存更新
                saas.getShopApp(getShopId()).bargain.updateBargainStock(bargain.getId(),1);
                saas.getShopApp(getShopId()).goods.updateGoodsNumberAndSale(bargainRecord.getGoodsId(),bargainRecord.getPrdId(),1);

                //砍价record的状态更新
                db().update(BARGAIN_RECORD).set(BARGAIN_RECORD.STATUS,BargainRecordService.STATUS_SUCCESS).set(BARGAIN_RECORD.BARGAIN_MONEY,BARGAIN_RECORD.BARGAIN_MONEY.add(bargainMoney)).set(BARGAIN_RECORD.USER_NUMBER,BARGAIN_RECORD.USER_NUMBER.add(1)).where(BARGAIN_RECORD.ID.eq(recordId)).execute();

                //推送砍价成功消息
                String goodsName = saas.getShopApp(getShopId()).goods.getGoodsRecordById(bargainRecord.getGoodsId()).getGoodsName();
                bargainSuccessSubscribeNotify(bargainRecord.getUserId(),bargain.getBargainName(),goodsName);
                bargainSuccessTemplateNotify(bargainRecord.getUserId(),bargain.getExpectationPrice(),goodsName,bargainRecord.getId());
                //TODO 向用户bargainRecord.getUserId发送砍价成功的消息
            }else {
                //砍价record的状态更新
                db().update(BARGAIN_RECORD).set(BARGAIN_RECORD.BARGAIN_MONEY,BARGAIN_RECORD.BARGAIN_MONEY.add(bargainMoney)).set(BARGAIN_RECORD.USER_NUMBER,BARGAIN_RECORD.USER_NUMBER.add(1)).where(BARGAIN_RECORD.ID.eq(recordId)).execute();
            }
        });


        if(StringUtil.isNotEmpty(bargain.getMrkingVoucherId()) && userId != bargainRecord.getUserId()){
            //向帮忙砍价的用户赠送优惠券
            CouponGiveQueueParam newParam = new CouponGiveQueueParam(
                getShopId(), Arrays.asList(userId), bargain.getId(), bargain.getMrkingVoucherId().split(","), BaseConstant.ACCESS_MODE_ISSUE, BaseConstant.GET_SOURCE_ACT);
            saas.taskJobMainService.dispatchImmediately(newParam, CouponGiveQueueParam.class.getName(), getShopId(), TaskJobsConstant.TaskJobEnum.GIVE_COUPON.getExecutionType());
        }

        return bargainMoney;
    }

    /**
     * userId用户已对该发起砍价record的砍价次数
     * @param userId
     * @param recordId
     * @return
     */
    public int getUserBargainNumber(int userId,int recordId){
        return db().selectCount().from(BARGAIN_USER_LIST).where(BARGAIN_USER_LIST.USER_ID.eq(userId).and(BARGAIN_USER_LIST.RECORD_ID.eq(recordId))).fetchOptionalInto(Integer.class).orElse(0);
    }

    /**
     * 砍一刀能砍掉的金额(区间内结算)
     * @param bargain
     * @param bargainRecord
     * @param userId
//     * @param userBargainNumber
     * @return
     */
    private BigDecimal getAbnormalBargainMoney(BargainRecord bargain,BargainRecordRecord bargainRecord,int userId){
        BigDecimal bargainMoney;
        BargainUserListRecord firstBargain;
        if(bargainRecord.getUserId() == userId && (firstBargain = getFirstUserBargain(userId,bargainRecord.getId())) != null){
            //给自己砍的第二次，与第一次相同金额(翻倍)，或者剩下的全砍掉
            BigDecimal remainMoney = bargainRecord.getGoodsPrice().subtract(bargainRecord.getBargainMoney()).subtract(bargain.getFloorPrice().compareTo(BigDecimal.ZERO) > 0 ? bargain.getFloorPrice() : BigDecimal.ZERO);
            if(remainMoney.compareTo(firstBargain.getBargainMoney()) > 0){
                bargainMoney = firstBargain.getBargainMoney();
            }else{
                bargainMoney = remainMoney;
            }
        }else{
            bargainMoney = getFixedBargainMoney(bargain);
        }

        return bargainMoney;
    }

    /**
     * 砍到任意金额（区间）结算模式，砍出的金额
     * @param bargain
     * @return
     */
    private BigDecimal getFixedBargainMoney(BargainRecord bargain){
        if(bargain.getBargainMoneyType().equals(BargainService.BARGAIN_MONEY_TYPE_FIXED)){
            return bargain.getBargainFixedMoney();
        }else{
            //区间内随机金额
            return new BigDecimal(Math.random() * (bargain.getBargainMaxMoney().floatValue() - bargain.getBargainMinMoney().floatValue()) + bargain.getBargainMinMoney().floatValue());
        }
    }

    /**
     * 砍到固定金额结算模式，砍出的金额
     * @param bargain
     * @return
     */
    private BigDecimal getRandomBargainMoney(BargainRecord bargain,BargainRecordRecord bargainRecord){
        BigDecimal randomBargainMoney;
        double randomMultiple;
        if(bargainRecord.getUserNumber() == 0){
            //首次砍价
            if(bargain.getBargainMin() != null && bargain.getBargainMin() >= (double) 1 && bargain.getBargainMax() != null && bargain.getBargainMax() <= (double)49 && bargain.getBargainMin() <= bargain.getBargainMax()){
                //设置了有效的首次砍价可砍价比例区间（1-49之内）
                randomMultiple = Math.random() * (bargain.getBargainMax() - bargain.getBargainMin()) + bargain.getBargainMin();
            }else{
                //默认首次砍价比例10-49
                randomMultiple = Math.random() * (double)39 + (double)10;
            }
        }else{
            //默认砍价比例1-49
            randomMultiple = Math.random() * (double)48 + (double)1;
        }

        randomMultiple /= 100;

        int currentUserNumber = bargainRecord.getUserNumber() + 1;
        BigDecimal remainMoney = bargainRecord.getGoodsPrice().subtract(bargainRecord.getBargainMoney()).subtract(bargain.getExpectationPrice().compareTo(BigDecimal.ZERO) > 0 ? bargain.getExpectationPrice() : BigDecimal.ZERO);
        if(currentUserNumber < bargain.getExpectationNumber()){
            //不是最后一个
            double userRatio = ((bargain.getExpectationNumber() - currentUserNumber)/(bargain.getExpectationNumber()/2.0) + 2)/4.0;
            if(remainMoney.compareTo(new BigDecimal(((bargain.getExpectationNumber() - bargainRecord.getUserNumber()) * 0.5))) >= 0){
                if(bargainRecord.getUserNumber() > 0){
                    randomBargainMoney = new BigDecimal(randomMultiple * (remainMoney.subtract(new BigDecimal(((bargain.getExpectationNumber() - bargainRecord.getUserNumber()) * 0.25)))).doubleValue() * userRatio);
                }else{
                    randomBargainMoney = new BigDecimal(randomMultiple * remainMoney.doubleValue() * userRatio);
                }
            }else{
                randomBargainMoney = new BigDecimal((randomMultiple + 0.5) * (remainMoney.doubleValue()/(double) (bargain.getExpectationNumber() - bargainRecord.getUserNumber())) * userRatio);
            }
            if(randomBargainMoney.compareTo(remainMoney) >= 0){
                randomBargainMoney = remainMoney.divide(new BigDecimal((double)(bargain.getExpectationNumber() - currentUserNumber)));
            }
        }else{
            //最后一个砍价的人
            randomBargainMoney = remainMoney;
        }
        return randomBargainMoney.compareTo(BigDecimal.ZERO) > 0 ? randomBargainMoney : BigDecimal.ZERO;
    }

    /**
     * 砍一刀能砍掉的金额(固定金额结算)
     * @param bargain
     * @param bargainRecord
     * @param userId
     * @return
     */
    private BigDecimal getNormalBargainMoney(BargainRecord bargain,BargainRecordRecord bargainRecord,int userId){
        BigDecimal bargainMoney;
        BargainUserListRecord firstBargain;
        if(bargainRecord.getUserId() == userId && (firstBargain = getFirstUserBargain(userId,bargainRecord.getId())) != null){
            //给自己砍的第二次，与第一次相同金额(翻倍)，或者剩下的全砍掉
            BigDecimal remainMoney = bargainRecord.getGoodsPrice().subtract(bargainRecord.getBargainMoney()).subtract(bargain.getExpectationPrice().compareTo(BigDecimal.ZERO) > 0 ? bargain.getExpectationPrice() : BigDecimal.ZERO);
            if(remainMoney.compareTo(firstBargain.getBargainMoney()) > 0){
                if(bargainRecord.getUserNumber() + 1 == bargain.getExpectationNumber()){
                    //最后一刀
                    bargainMoney = remainMoney;
                }else {
                    bargainMoney = firstBargain.getBargainMoney();
                }
            }else{
                bargainMoney = getRandomBargainMoney(bargain,bargainRecord);
            }
        }else{
            bargainMoney = getRandomBargainMoney(bargain,bargainRecord);
        }
        return bargainMoney;
    }

    /**
     * userId在这次recordId砍价中砍的第一刀
     * @param userId
     * @param recordId
     * @return
     */
    public BargainUserListRecord getFirstUserBargain(int userId,int recordId){
        return db().selectFrom(BARGAIN_USER_LIST).where(BARGAIN_USER_LIST.USER_ID.eq(userId).and(BARGAIN_USER_LIST.RECORD_ID.eq(recordId))).orderBy(BARGAIN_USER_LIST.CREATE_TIME.asc()).limit(1).fetchOne();
    }

    /**
     * 帮忙砍价的用户列表
     * @param recordId
     * @return
     */
    public List<BargainInfoVo.BargainUser> getBargainUserList(int recordId){
        List<BargainInfoVo.BargainUser> res =  db().select(BARGAIN_USER_LIST.asterisk(),USER.USERNAME,USER.WX_OPENID,USER_DETAIL.USER_AVATAR).from(
            BARGAIN_USER_LIST
            .leftJoin(USER).on(USER.USER_ID.eq(BARGAIN_USER_LIST.USER_ID))
            .leftJoin(USER_DETAIL).on(USER_DETAIL.USER_ID.eq(BARGAIN_USER_LIST.USER_ID))
        ).where(BARGAIN_USER_LIST.RECORD_ID.eq(recordId)).orderBy(BARGAIN_USER_LIST.CREATE_TIME.asc()).limit(20).fetchInto(BargainInfoVo.BargainUser.class);
        return res;
    }

    /**
     * 用户当天砍价次数
     * @param userId
     * @return
     */
    public int getUserTodayCutTimes(int userId){
        return db().selectCount().from(BARGAIN_USER_LIST).leftJoin(BARGAIN_RECORD).on(BARGAIN_USER_LIST.RECORD_ID.eq(BARGAIN_RECORD.ID)).where(BARGAIN_USER_LIST.USER_ID.eq(userId).and(BARGAIN_USER_LIST.CREATE_TIME.gt(DateUtil.getLocalDateTime()))).fetchOptionalInto(Integer.class).orElse(0);
    }

    /**
     * 取消砍价订单库存处理
     * @param order
     */
    public void cancelBargainOrderStock(OrderInfoVo order){
        saas.getShopApp(getShopId()).bargain.updateBargainStock(order.getActivityId(),-1);
        BargainRecordRecord bargainRecord = db().selectFrom(BARGAIN_RECORD).where(BARGAIN_RECORD.ORDER_SN.eq(order.getOrderSn())).fetchAny();
        saas.getShopApp(getShopId()).goods.updateGoodsNumberAndSale(bargainRecord.getGoodsId(),bargainRecord.getPrdId(),-1);

    }

    /**
     * 砍价完成小程序订阅消息通知
     */
    private void bargainSuccessSubscribeNotify(int userId,String bargainName,String goodsName){
        String[][] data = new String[][] { { bargainName }, { goodsName + "砍价已完成" }, { Util.getdate("yyyy-MM-dd HH:mm:ss") } };
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(userId);
        RabbitMessageParam param = RabbitMessageParam.builder()
            .maTemplateData(
                MaTemplateData.builder().config(SubcribeTemplateCategory.INVITE_SUCCESS).data(data).build())
            .page(null).shopId(getShopId())
            .userIdList(arrayList)
            .type(RabbitParamConstant.Type.MA_SUBSCRIBEMESSAGE_TYPE).build();
        saas.taskJobMainService.dispatchImmediately(param, RabbitMessageParam.class.getName(), getShopId(), TaskJobsConstant.TaskJobEnum.SEND_MESSAGE.getExecutionType());
    }

    /**
     *  砍价完成公众号模板消息通知
     * @param userId
     * @param bargainPrice
     * @param goodsName
     * @param recordId
     */
    private void bargainSuccessTemplateNotify(int userId,BigDecimal bargainPrice,String goodsName,int recordId){
        String wxUnionId = db().select(Tables.USER.WX_UNION_ID).from(USER).where(USER.USER_ID.eq(userId)).fetchOptionalInto(String.class).orElse(null);
        String officeAppId = saas.shop.mp.findOffcialByShopId(getShopId());

        UserRecord wxUserInfo = saas.getShopApp(getShopId()).user.getUserByUnionId(wxUnionId);

        if(wxUnionId == null || officeAppId == null || wxUserInfo == null){
            return;
        }
        String page = "pages/bargaininfo/bargaininfo?record_id=" + recordId;
        List<Integer> userIdList = new ArrayList<>();
        userIdList.add(wxUserInfo.getUserId());

        String[][] data = new String[][] { { "您有新的砍价进度", "#173177" }, { goodsName, "#173177" },
            { bargainPrice.toString(), "#173177" },
            { "已砍价成功，您可以告知您的好友砍价成功哦！", "#173177" } };
        RabbitMessageParam param = RabbitMessageParam.builder()
            .mpTemplateData(MpTemplateData.builder().config(MpTemplateConfig.BARGAIN_SUCCESS).data(data).build())
            .page(page).shopId(getShopId()).userIdList(userIdList).type(RabbitParamConstant.Type.MP_TEMPLE_TYPE)
            .build();
        saas.taskJobMainService.dispatchImmediately(param, RabbitMessageParam.class.getName(), getShopId(),
            TaskJobsConstant.TaskJobEnum.SEND_MESSAGE.getExecutionType());
    }

}
