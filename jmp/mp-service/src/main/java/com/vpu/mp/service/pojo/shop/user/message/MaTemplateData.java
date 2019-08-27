package com.vpu.mp.service.pojo.shop.user.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
public class MaTemplateData {

    private MaTemplateConfig config;

    private String[][] data;
}
