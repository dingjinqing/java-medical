package com.vpu.mp.service.pojo.shop.decoration;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 
 * @author 新国
 *
 */
@Data
public class PageStoreParam {
	private Integer pageId;
    private String pageName;
    @NotNull
    private String pageContent;
    /**
     * 0保存为草稿，1保存并发布，2预览，3回退到当前已发布版本
     */
    @NotNull
    private Byte pageState;
    private Integer catId;
    private Byte  pageTplType;
}
