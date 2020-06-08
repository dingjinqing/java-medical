package com.vpu.mp.service.shop.question;

import com.vpu.mp.service.pojo.shop.question.FeedbackParam;
import com.vpu.mp.service.shop.config.BaseShopConfigService;
import org.springframework.stereotype.Service;


@Service
public class FeedbackService extends BaseShopConfigService {

    public void insert(FeedbackParam param){
        saas().questionService.insert(getShopId(),param);
    }
}
