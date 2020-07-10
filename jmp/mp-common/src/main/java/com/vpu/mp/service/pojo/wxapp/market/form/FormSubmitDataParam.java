package com.vpu.mp.service.pojo.wxapp.market.form;

import com.vpu.mp.service.pojo.shop.market.form.FeedBackDetail;
import com.vpu.mp.service.pojo.wxapp.login.WxAppSessionUser;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 小程序表单提交数据
 * @author 孔德成
 * @date 2020/3/13
 */
@Data
@NoArgsConstructor
public class FormSubmitDataParam {
    /**
     * b2c_form_submit_list表所需入参
     */
    @NotNull
    private Integer pageId;
    private WxAppSessionUser user;

    /**
     * 反馈详细信息
     */
    private List<FeedBackDetail> detailList;

}
