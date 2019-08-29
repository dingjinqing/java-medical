package com.vpu.mp.service.pojo.shop.market.message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vpu.mp.service.pojo.shop.official.message.MpTemplateConfig;
import com.vpu.mp.service.pojo.shop.official.message.MpTemplateData;
import com.vpu.mp.service.pojo.shop.user.message.MaTemplateConfig;
import com.vpu.mp.service.pojo.shop.user.message.MaTemplateData;


/**
 * 消息专用反序列化
 * @author 卢光耀
 * @date 2019-08-26 16:22
 *
*/
public class MessageParamDeserializer extends JsonDeserializer<RabbitMessageParam> {

    @Override
    public RabbitMessageParam deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        RabbitMessageParam param = RabbitMessageParam.builder().build();
        JsonNode node = p.getCodec().readTree(p);
        Iterator<String> iterator = node.get(0).fieldNames();
        while( iterator.hasNext() ){
            String key = iterator.next();
            JsonNode j_node = node.get(0);
            if( key.equals("shopId") ){
                param.setShopId(j_node.findValue(key).asInt());
            }else if( key.equals("messageTemplateId") ){
                param.setMessageTemplateId(j_node.findValue(key).asInt());
            }else if( key.equals("userIdList") ){
                List<Integer> ids = new ArrayList<>();
                j_node.get(key).forEach(x->
                    ids.add(x.intValue()) );
                param.setUserIdList(ids);
            }else if( key.equals("page") ){
                param.setPage(j_node.findValue(key).asText());
            }else if( key.equals("type") && j_node.findValue(key).asText()!= "null"){
                param.setType(j_node.findValue(key).asInt());
            }else if( key.equals("maTemplateData") )  {
                JsonNode maData = j_node.findValue(key);
                String[][] data = new String[maData.findValue("data").size()][3];
                MaTemplateConfig config = MaTemplateConfig.getConfig(maData.findPath("config").asText());
                for (int i = 0,len = maData.findValue("data").size(); i < len; i++) {
                    JsonNode i_node = maData.findValue("data").get(i);
                    for (int j = 0,j_len=i_node.size(); j < j_len ; j++) {
                        data[i][j] = i_node.get(j).asText();
                    }
                }
                MaTemplateData ma = MaTemplateData.builder()
                    .data(data)
                    .config(config)
                    .build();
                param.setMaTemplateData(ma);
            }else if( key.equals("mpTemplateData") )  {
                JsonNode mpData = j_node.findValue(key);
                String[][] data = new String[mpData.findValue("data").size()][3];
                MpTemplateConfig config = MpTemplateConfig.getConfig(mpData.findPath("config").asText());
                for (int i = 0,len = mpData.findValue("data").size(); i < len; i++) {
                    JsonNode i_node = mpData.findValue("data").get(i);
                    for (int j = 0,j_len=i_node.size(); j < j_len ; j++) {
                        data[i][j] = i_node.get(j).asText();
                    }
                }
                MpTemplateData mp = MpTemplateData.builder()
                    .data(data)
                    .config(config)
                    .build();
                param.setMpTemplateData(mp);
            }else if( key.equals("emphasisKeywordSn") ){
                param.setEmphasisKeyword(j_node.findValue(key).asText());
            }
        }
        return param;
    }
}
