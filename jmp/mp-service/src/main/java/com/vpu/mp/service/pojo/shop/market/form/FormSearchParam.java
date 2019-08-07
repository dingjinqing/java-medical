package com.vpu.mp.service.pojo.shop.market.form;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.sql.Date;

/**
 * @author liufei
 * @date 2019/8/7
 * @description
 */
@Data
public class FormSearchParam {
    private String pageName;
    private Date startTime;
    private Date endTime;
    @Value(value = "-1")
    private Byte status;
    private Integer currentPage;
    private Integer pageRows;

}
