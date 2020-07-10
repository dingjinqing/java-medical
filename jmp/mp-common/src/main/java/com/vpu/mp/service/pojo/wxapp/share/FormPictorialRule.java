package com.vpu.mp.service.pojo.wxapp.share;

import lombok.Builder;
import lombok.Data;

/**
 * @author liufei
 * @date 3/6/20
 */
@Data
@Builder
public class FormPictorialRule implements Rule {
    String page_name;
    String bg_img;
}
