package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.question.FeedbackParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/question")
public class AdminQuestionController extends AdminBaseController {


    @PostMapping("/feedback")
    public JsonResult insertQuestion(FeedbackParam param){
        shop().questionService.insert(param);
        return success();
    }
}
