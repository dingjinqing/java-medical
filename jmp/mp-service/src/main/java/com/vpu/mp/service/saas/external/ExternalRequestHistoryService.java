package com.vpu.mp.service.saas.external;

import com.vpu.mp.dao.main.ExternalRequestHistoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * @author 李晓冰
 * @date 2020年07月15日
 */
@Service
public class ExternalRequestHistoryService {

    @Autowired
    private ExternalRequestHistoryDao externalRequestHistoryDao;

    /**
     * 获取服务最后一次请求的时间
     * @param appId
     * @param shopId
     * @param serviceName
     * @return
     */
    public Long getLastRequestTime(String appId,Integer shopId,String serviceName){
        Timestamp lastRequestTime = externalRequestHistoryDao.getLastRequestTime(appId, shopId, serviceName);
        long time = lastRequestTime.getTime();
        return time/1000;
    }

    /**
     * 添加请求记录
     */
    public void insertRequest(String appId,Integer shopId,String serviceName,String param,Integer errorCode){
        externalRequestHistoryDao.insert(appId,shopId,serviceName,param,errorCode);
    }
}
