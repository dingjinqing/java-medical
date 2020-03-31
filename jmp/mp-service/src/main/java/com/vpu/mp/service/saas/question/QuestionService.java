package com.vpu.mp.service.saas.question;

import com.google.common.collect.Lists;
import com.vpu.mp.db.main.tables.records.QfImgRecord;
import com.vpu.mp.db.main.tables.records.ShopQuestionFeedbackRecord;
import com.vpu.mp.service.foundation.service.MainBaseService;
import com.vpu.mp.service.pojo.shop.question.FeedbackParam;
import com.vpu.mp.service.shop.config.BaseShopConfigService;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.vpu.mp.db.main.tables.ShopQuestionFeedback.SHOP_QUESTION_FEEDBACK;

@Service
public class QuestionService extends MainBaseService {



    public void insert(Integer shopId,FeedbackParam param){
        ShopQuestionFeedbackRecord record = buildRecord(param,shopId);
        Integer id = insertFeedback(record);
        batchInsertImg(buildImgRecord(param.getImgs(),id));
    }

    private void batchInsertImg(List<QfImgRecord> records){
        db().batchInsert(records).execute();
    }

    private Integer insertFeedback(ShopQuestionFeedbackRecord record){
        return  db().insertInto(SHOP_QUESTION_FEEDBACK).
            values(record).
            returning(SHOP_QUESTION_FEEDBACK.QUESTION_FEEDBACK_ID).
            fetchOne().
            getQuestionFeedbackId();
    }

    private ShopQuestionFeedbackRecord buildRecord(FeedbackParam param,Integer shopId){
        ShopQuestionFeedbackRecord record = new ShopQuestionFeedbackRecord();
        record.setCategoryId(param.getType().intValue());
        record.setContent(param.getContent());
        record.setShopId(shopId);
        record.setMobile(param.getMobile());
        return record;
    }

    private List<QfImgRecord> buildImgRecord(List<String> imgs,Integer id){
        List<QfImgRecord> result = Lists.newArrayList();
        for (int i = 0; i < imgs.size(); i++) {
            QfImgRecord record = new QfImgRecord();
            record.setImgUrl(imgs.get(i));
            record.setQuestionFeedbackId(id);
            record.setImgDesc(String.valueOf(i));
            result.add(record);
        }

        return result;
    }
}
