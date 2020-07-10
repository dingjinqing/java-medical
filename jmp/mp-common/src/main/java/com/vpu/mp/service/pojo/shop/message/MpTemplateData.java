package com.vpu.mp.service.pojo.shop.message;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MpTemplateData {
    private MpTemplateConfig config;

    private String[][] data;
}
