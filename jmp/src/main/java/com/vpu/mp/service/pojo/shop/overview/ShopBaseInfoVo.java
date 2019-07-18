package com.vpu.mp.service.pojo.shop.overview;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * @Author:liufei
 * @Date:2019/7/16
 * @Description:
 */
@Data
@Component
public class ShopBaseInfoVo {
    private Timestamp expireTime;
    private String versionName;
}
