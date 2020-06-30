package com.vpu.mp.service.shop.config.message;

import com.vpu.mp.dao.shop.config.MessageTemplateConfigDao;
import com.vpu.mp.db.shop.tables.records.MessageTemplateConfigRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.config.message.MessageConfigParam;
import com.vpu.mp.service.pojo.shop.config.message.MessageConfigVo;
import com.vpu.mp.service.pojo.shop.market.message.RabbitParamConstant;

import org.jooq.Batch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.tables.MessageTemplateConfig.MESSAGE_TEMPLATE_CONFIG;

@Service
public class MessageConfigService extends ShopBaseService {

	@Autowired
	MessageTemplateConfigDao messageTemplateConfigDao;
	
    public void updateMessageConfig(MessageConfigParam param){
    	messageTemplateConfigDao.batchUpdate(param.getConfigs());
    }

    public List<MessageConfigVo> getAllMessageConfig(){
       return  messageTemplateConfigDao.getAllMessageConfig();
    }

    public MessageConfigVo getMessageConfig(Integer id){
        return  messageTemplateConfigDao.getMessageConfig(id);
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
        return messageTemplateConfigDao.hasConfig(type);
    }
}
