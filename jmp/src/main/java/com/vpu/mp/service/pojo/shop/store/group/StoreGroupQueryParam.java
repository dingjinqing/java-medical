package com.vpu.mp.service.pojo.shop.store.group;

import com.vpu.mp.service.foundation.Page;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author luguangyao
 */
@Data
public class StoreGroupQueryParam {

    private String groupName;

    private Integer currentPage;

    private Integer pageRows = Page.DEFAULT_PAGE_ROWS;
}
