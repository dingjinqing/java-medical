package com.vpu.mp.service.pojo.shop.market.form;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;

import javax.validation.constraints.NotNull;
import java.util.List;

import static com.vpu.mp.service.pojo.shop.market.form.FormConstant.MAPPER;

/**
 * @author liufei
 * @date 2019/8/12
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedBackInfoParam {
    /**
     * b2c_form_submit_list表所需入参
     */
    @NotNull
    private Integer pageId;
    @NotNull
    private Integer userId;

    /**
     * 反馈详细信息
     */
    private List<FeedBackDetail> detailList;
}
