package com.vpu.mp.service.saas.user;

import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.dao.main.user.MainUserDao;
import com.vpu.mp.service.foundation.service.MainBaseService;
import com.vpu.mp.service.pojo.saas.shop.ShopListInfoVo;
import com.vpu.mp.service.pojo.saas.user.MainUserPageListParam;
import com.vpu.mp.service.pojo.saas.user.MainUserVo;
import com.vpu.mp.service.saas.shop.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 主库用户信息表
 * @author 李晓冰
 * @date 2020年08月17日
 */
@Service
public class MainUserService extends MainBaseService {

    @Autowired
    private ShopService shopService;

    @Autowired
    private MainUserDao mainUserDao;

    /**
     *
     * @param pageListParam
     * @return
     */
    public PageResult<MainUserVo> getPageList(MainUserPageListParam pageListParam) {
        PageResult<MainUserVo> pageList = mainUserDao.getPageList(pageListParam);
        List<ShopListInfoVo> shopListInfo = shopService.getShopListInfo();
        Map<Integer, String> shopMap = shopListInfo.stream().collect(Collectors.toMap(ShopListInfoVo::getShopId, ShopListInfoVo::getShopName, (x1, x2) -> x1));
        for (MainUserVo mainUserVo : pageList.getDataList()) {
            mainUserVo.setShopName(shopMap.get(mainUserVo.getShopId()));
        }
        return pageList;
    }
}
