package com.vpu.mp.controller.store;

import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.store.goods.StoreGoodsListQueryParam;
import com.vpu.mp.service.pojo.shop.store.goods.StoreGoodsListQueryVo;
import com.vpu.mp.service.shop.store.store.StoreGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 李晓冰
 * @date 2020年08月31日
 */
@RestController
public class StoreGoodsController extends StoreBaseController{
    @Autowired
    private StoreGoodsService storeGoodsService;

    @PostMapping("/api/store/goods/list")
    public JsonResult getGoodsPageList(@RequestBody StoreGoodsListQueryParam param){
        param.setLimitedStoreIds(storeAuth.user().getStoreIds());
        PageResult<StoreGoodsListQueryVo> goodsPageList = storeGoodsService.getGoodsPageList(param);
        return success(goodsPageList);
    }
}
