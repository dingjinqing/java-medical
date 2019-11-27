package com.vpu.mp.service.foundation.jedis.data.label;

import java.util.List;

/**
 * 更新cache数据
 * @author 卢光耀
 * @date 2019/11/21 10:49 上午
 *
*/
public class GoodsLabelCacheParam {

    private Integer shopId;
    /**
     * 更新全部数据(清空所cache有数据重新建立)
     * 适用于：
     */
    private Boolean updateAll = Boolean.FALSE;

    private List<Integer> ids;





    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Boolean getUpdateAll() {
        return updateAll;
    }

    public void setUpdateAll(Boolean updateAll) {
        this.updateAll = updateAll;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }


}
