package com.vpu.mp.service.saas.question;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.vpu.mp.db.main.tables.records.QfImgRecord;
import com.vpu.mp.db.main.tables.records.ShopAccountRecord;
import com.vpu.mp.db.main.tables.records.ShopChildAccountRecord;
import com.vpu.mp.db.main.tables.records.ShopQuestionFeedbackRecord;
import com.vpu.mp.service.foundation.service.MainBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.saas.question.bo.FeedbackBo;
import com.vpu.mp.service.pojo.saas.question.param.FeedBackParam;
import com.vpu.mp.service.pojo.saas.question.vo.FeedbackVo;
import com.vpu.mp.service.pojo.shop.auth.AdminTokenAuthInfo;
import com.vpu.mp.service.pojo.shop.question.FeedbackParam;
import com.vpu.mp.service.saas.shop.ShopService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Condition;
import org.jooq.SelectWhereStep;
import org.jooq.impl.DSL;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.vpu.mp.db.main.tables.ShopQuestionFeedback.SHOP_QUESTION_FEEDBACK;
import static com.vpu.mp.db.main.tables.QfImg.QF_IMG;

@Service
public class QuestionService extends MainBaseService {

    @Autowired
    private ShopService shopService;



    public void insert(Integer shopId, FeedbackParam param, AdminTokenAuthInfo userInfo){
        ShopQuestionFeedbackRecord record = buildRecord(param,shopId,userInfo);
        Integer id = insertFeedback(record);
        if(CollectionUtils.isEmpty(param.getImgs()) ){
            batchInsertImg(buildImgRecord(param.getImgs(),id));
        }

    }

    public PageResult<FeedbackVo> getPageByParam(FeedBackParam feedBackParam){
        SelectWhereStep<?> selectWhereStep = db().select().from(SHOP_QUESTION_FEEDBACK);
        selectWhereStep.where(buildParam(feedBackParam));
        PageResult<FeedbackBo> pageResult =
            getPageResult(selectWhereStep,feedBackParam.getCurrentPage(),feedBackParam.getPageRows(), FeedbackBo.class);

        return assemblyDataList(pageResult);
    }

    private PageResult<FeedbackVo> assemblyDataList(PageResult<FeedbackBo> boPageResult){
        PageResult<FeedbackVo> result = new PageResult<>();
        BeanUtils.copyProperties(boPageResult.page,result.page);
        List<Integer> feedbackIds = boPageResult.dataList.stream()
            .map(FeedbackBo::getQuestionFeedbackId)
            .collect(Collectors.toList());
        if( feedbackIds.isEmpty() ){
            return result;
        }
        Map<Integer,List<String>> urlMap = getQfImgByIds(feedbackIds);
        List<FeedbackVo> vos = Lists.newArrayList();
        for( FeedbackBo bo : boPageResult.getDataList() ){
            FeedbackVo vo = new FeedbackVo();
            BeanUtils.copyProperties(bo,vo);
            vo.setImageUrls(urlMap.getOrDefault(vo.getQuestionFeedbackId(),Lists.newArrayList()));
            vos.add(vo);
        }
        result.dataList = vos;
        return result;
    }

    private Map<Integer,List<String>> getQfImgByIds(List<Integer> ids){
        Map<Integer,List<String>> imgMap = Maps.newHashMap();
        List<QfImgRecord> qfImgRecords =db().select(QF_IMG.QUESTION_FEEDBACK_ID,QF_IMG.IMG_URL,QF_IMG.IMG_DESC)
            .from(QF_IMG)
            .where(QF_IMG.QUESTION_FEEDBACK_ID.in(ids))
            .orderBy(QF_IMG.IMG_DESC.asc())
            .fetch(r->r.into(QfImgRecord.class));
        for( QfImgRecord record : qfImgRecords ){
            Integer feedbackId= record.getQuestionFeedbackId();
            if( imgMap.containsKey(feedbackId) ){
                imgMap.get(feedbackId).add(record.getImgUrl());
            }else{
                List<String> urls = Lists.newArrayList(record.getImgUrl());
                imgMap.put(feedbackId,urls);
            }
        }
        return imgMap;
    }

    private Condition buildParam(FeedBackParam param){
        Condition condition = DSL.noCondition();
        if( StringUtils.isNotBlank(param.getName()) ){
            condition.and(SHOP_QUESTION_FEEDBACK.SUBMIT_USER.like(likeValue(param.getName())));
        }
        if( null != param.getCategoryId() ){
            condition.and(SHOP_QUESTION_FEEDBACK.CATEGORY_ID.eq(param.getCategoryId()));
        }
        if( null != param.getLookType() ){
            condition.and(SHOP_QUESTION_FEEDBACK.IS_LOOK.eq(param.getLookType()));
        }
        if( null != param.getStartTime() ){
            condition.and(SHOP_QUESTION_FEEDBACK.CREATE_TIME.greaterOrEqual(param.getStartTime()));
        }
        if( null != param.getEndTime() ){
            condition.and(SHOP_QUESTION_FEEDBACK.CREATE_TIME.lessOrEqual(param.getEndTime()));
        }
        return condition;
    }

    private void batchInsertImg(List<QfImgRecord> records){
        db().batchInsert(records).execute();
    }

    private Integer insertFeedback(ShopQuestionFeedbackRecord record){
        return  db().insertInto(SHOP_QUESTION_FEEDBACK).set(record).
            returning().
            fetchOne().
            getQuestionFeedbackId();
    }

    private ShopQuestionFeedbackRecord buildRecord(FeedbackParam param,Integer shopId,AdminTokenAuthInfo userInfo){
        ShopQuestionFeedbackRecord record = new ShopQuestionFeedbackRecord();
        record.setCategoryId(param.getType().intValue());
        record.setContent(param.getContent());
        record.setShopId(shopId);


        record.setSubmitUser(userInfo.getAccountName());
        if(StringUtils.isNotBlank(param.getMobile())){
            record.setMobile(param.getMobile());
        }

        Integer userId = userInfo.isSubLogin()? userInfo.getSubAccountId():userInfo.getSysId();
        if( userInfo.isSubLogin() ){
           ShopChildAccountRecord childAccountRecord =shopService.subAccount.getSubAccountInfo(userId);
           record.setSubmitUser(childAccountRecord.getAccountName());
           record.setSubmitUserPhone(childAccountRecord.getMobile());
        }else{
            ShopAccountRecord accountRecord =shopService.account.getAccountInfoForId(userId);
            record.setSubmitUser(accountRecord.getAccountName());
            record.setSubmitUserPhone(accountRecord.getMobile());
        }
        record.setVersion(shopService.getShopById(userInfo.getLoginShopId()).getShopType());

        return record;
    }

    private List<QfImgRecord> buildImgRecord(List<String> imgs,Integer id){
        List<QfImgRecord> result = Lists.newArrayList();
        for (int i = 0; i < imgs.size(); i++) {
            QfImgRecord record = new QfImgRecord();
            record.setImgUrl(imgs.get(i+1));
            record.setQuestionFeedbackId(id);
            record.setImgDesc(String.valueOf(i));
            result.add(record);
        }

        return result;
    }
}
