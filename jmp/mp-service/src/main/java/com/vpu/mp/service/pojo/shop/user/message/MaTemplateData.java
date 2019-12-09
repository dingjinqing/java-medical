package com.vpu.mp.service.pojo.shop.user.message;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MaTemplateData {

	/**参考SubcribeTemplateCategory*/
    private String config;

    private String[][] data;
}
