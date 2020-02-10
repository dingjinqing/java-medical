package com.vpu.mp.service.pojo.shop.overview.commodity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.sql.Date;
import java.time.LocalDate;

/**
 * @author liufei
 * @date 2/10/2020
 */
@Data
public class RankingParam {
    @NotEmpty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date startTime;
    @NotEmpty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date endTime;

    /**
     * 默认获取30天的数据
     */
    private RankingParam defaultValue() {
        LocalDate now = LocalDate.now();
        this.endTime = Date.valueOf(now);
        this.startTime = Date.valueOf(now.minusDays(30));
        return this;
    }

}
