package com.vpu.mp.service.pojo.saas.user;

import com.vpu.mp.common.pojo.shop.base.BasePageParam;
import lombok.Data;

/**
 * 主库用户查询分页表
 * @author 李晓冰
 * @date 2020年08月17日
 */
@Data
public class MainUserPageListParam extends BasePageParam {
    private Integer shopId;
}
