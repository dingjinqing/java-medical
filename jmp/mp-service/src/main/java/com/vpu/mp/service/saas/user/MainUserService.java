package com.vpu.mp.service.saas.user;

import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.dao.main.user.MainUserDao;
import com.vpu.mp.service.foundation.service.MainBaseService;
import com.vpu.mp.service.pojo.saas.user.MainUserPageListParam;
import com.vpu.mp.service.pojo.saas.user.MainUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 主库用户信息表
 * @author 李晓冰
 * @date 2020年08月17日
 */
@Service
public class MainUserService extends MainBaseService {

    @Autowired
    private MainUserDao mainUserDao;

    /**
     *
     * @param pageListParam
     * @return
     */
    public PageResult<MainUserVo> getPageList(MainUserPageListParam pageListParam) {
        return mainUserDao.getPageList(pageListParam);
    }
}
