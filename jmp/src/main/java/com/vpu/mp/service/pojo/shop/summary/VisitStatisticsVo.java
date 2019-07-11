package com.vpu.mp.service.pojo.shop.summary;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class VisitStatisticsVo {

    private List<String> date;
    private List<Double> list;
}
