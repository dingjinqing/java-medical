package com.vpu.mp.service.shop.config.message;

import com.vpu.mp.db.shop.tables.records.MessageTemplateConfigRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.config.message.MessageConfigParam;
import com.vpu.mp.service.pojo.shop.config.message.MessageConfigVo;
import com.vpu.mp.service.pojo.shop.market.message.RabbitParamConstant;

import org.jooq.Batch;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.tables.MessageTemplateConfig.MESSAGE_TEMPLATE_CONFIG;

@Service
public class MessageConfigService extends ShopBaseService {

    public void updateMessageConfig(MessageConfigParam param){
        List<MessageTemplateConfigRecord> recordList = new ArrayList<>();
        Batch batch = db().batchUpdate(param.getConfigs().stream()
            .map(x ->
                db().newRecord(MESSAGE_TEMPLATE_CONFIG, x)
            )
            .collect(Collectors.toList()));
        batch.execute();
    }

    public List<MessageConfigVo> getAllMessageConfig(){
       return  db().selectFrom(MESSAGE_TEMPLATE_CONFIG).fetch().into(MessageConfigVo.class);
    }

    public MessageConfigVo getMessageConfig(Integer id){
        return  db().selectFrom(MESSAGE_TEMPLATE_CONFIG).where(MESSAGE_TEMPLATE_CONFIG.ID.eq(id)).fetchOneInto(MessageConfigVo.class);
    }

    /**
     * 校验是否配置开通发送对因类型的消息
     * @param type 消息类型
     * @return true 开通
     */
    public boolean checkConfig(Integer type){
    	if(type.equals(RabbitParamConstant.Type.MP_TEMPLE_TYP_NO)||type.equals(RabbitParamConstant.Type.MP_TEMPLE_TYPE_NO_USER)) {
    		return true;
    	}
        Byte openMp = db().select(MESSAGE_TEMPLATE_CONFIG.OPEN_MP).
            from(MESSAGE_TEMPLATE_CONFIG).
            where(MESSAGE_TEMPLATE_CONFIG.ID.eq(type)).fetchOne(MESSAGE_TEMPLATE_CONFIG.OPEN_MP);
        return openMp != null && openMp == 1;
    }
}
