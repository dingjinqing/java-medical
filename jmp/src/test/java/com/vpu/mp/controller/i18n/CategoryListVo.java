package com.vpu.mp.controller.i18n;

import com.vpu.mp.controller.I18N;
import lombok.Data;

import java.util.List;

/**
 * @author 郑保乐
 */
@Data
public class CategoryListVo {

    @I18N(propertiesFileName = "category")
    private List<String> list;
}
