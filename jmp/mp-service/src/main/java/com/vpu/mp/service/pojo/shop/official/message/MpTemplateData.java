package com.vpu.mp.service.pojo.shop.official.message;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
public class MpTemplateData {
    private MpTemplateConfig config;

    private String[][] data;
}
