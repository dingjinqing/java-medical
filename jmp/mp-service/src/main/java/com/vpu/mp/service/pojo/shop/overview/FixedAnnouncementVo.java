package com.vpu.mp.service.pojo.shop.overview;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * author liufei
 * date 2019/7/16
 */
@Data
@Component
public class FixedAnnouncementVo {
    private Integer id;
    private String title;
    private String formatTime;
    private Timestamp createTime;
}
