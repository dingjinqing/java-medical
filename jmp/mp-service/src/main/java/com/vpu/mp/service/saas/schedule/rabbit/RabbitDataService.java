package com.vpu.mp.service.saas.schedule.rabbit;

import com.rabbitmq.http.client.Client;
import com.rabbitmq.http.client.domain.QueueInfo;
import com.vpu.mp.common.foundation.util.MathUtil;
import com.vpu.mp.service.foundation.service.MainBaseService;
import com.vpu.mp.service.pojo.saas.schedule.rabbit.RabbitDataConstant;
import com.vpu.mp.service.pojo.saas.schedule.rabbit.RabbitInfoData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class RabbitDataService extends MainBaseService {

    @Autowired
    private Client client;

    public List<RabbitInfoData> getDetails(){
        List<QueueInfo> list = client.getQueues();
        List<RabbitInfoData>  result = new ArrayList<>(list.size());
        for( QueueInfo info:list ){
            result.add(RabbitInfoData.builder()
                .messages(info.getTotalMessages())
                .name(info.getName())
                .memory(MathUtil.deciMal(info.getMemoryUsed(),1024L))
                .stateName(RabbitDataConstant.State.getNameByState(info.getState()))
                .build());

        }
        return result;
    }
}
