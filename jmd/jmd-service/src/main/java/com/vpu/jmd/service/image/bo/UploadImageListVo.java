package com.vpu.jmd.service.image.bo;

import com.vpu.mp.service.foundation.util.PageResult;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 图片链接异常
 * @author 孔德成
 * @date 2020/6/17 16:44
 */
@Getter
@Setter
@ToString
public class UploadImageListVo extends PageResult<UploadImageCatNameVo> {
    /**
     * 剩余内存空间
     */
    private BigDecimal freeMemory;
}
