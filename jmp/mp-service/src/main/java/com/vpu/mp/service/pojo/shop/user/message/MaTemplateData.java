package com.vpu.mp.service.pojo.shop.user.message;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MaTemplateData {

    private MaTemplateConfig config;

    private String[][] data;
}
