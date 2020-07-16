package com.vpu.mp.service.saas.external;

import com.vpu.mp.common.foundation.data.BaseConstant;
import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.common.pojo.main.table.AppAuthDo;
import com.vpu.mp.common.pojo.main.table.AppDo;
import com.vpu.mp.dao.main.AppAuthDao;
import com.vpu.mp.dao.main.AppDao;
import com.vpu.mp.service.foundation.service.MainBaseService;
import com.vpu.mp.service.pojo.saas.app.vo.AppAuthVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 对接外部应用服务
 * @author 李晓冰
 * @date 2020年07月15日
 */
@Service
public class AppAuthService extends MainBaseService {

    @Autowired
    AppDao appDao;
    @Autowired
    AppAuthDao appAuthDao;

    public AppAuthVo getAppAuth(String appId,Integer shopId){
        AppDo appDo = appDao.getAppDo(appId);
        AppAuthDo appAuthDo = appAuthDao.getAppAuth(shopId, appId, BaseConstant.ACTIVITY_STATUS_NORMAL);
        AppAuthVo vo =new AppAuthVo();
        FieldsUtil.assign(appAuthDo,vo);
        vo.setAppSecret(appDo.getAppSecret());
        return vo;
    }
}
