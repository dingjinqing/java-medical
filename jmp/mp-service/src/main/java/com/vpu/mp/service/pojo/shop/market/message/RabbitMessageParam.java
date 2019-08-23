package com.vpu.mp.service.pojo.shop.market.message;


import cn.binarywang.wx.miniapp.bean.WxMaTemplateData;
import com.vpu.mp.service.pojo.shop.user.message.MaAndMpTemplate;
import com.vpu.mp.service.pojo.shop.user.message.MaTemplateConfig;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class RabbitMessageParam {
    private Integer shopId;

    /**
     * 对应数据库表的ID
     */
    private Integer messageTemplateId;

    /**
     * 小程序和公众号模版之间的映射ID
     * {@link com.vpu.mp.service.pojo.shop.user.message.MaAndMpTemplate}
     */
    private Integer templateId;

    private List<Integer> userIdList;
    /**
     * 跳转的url
     */
    private String page;

    /**
     * 根据ID和type自动初始化
     */
    private List<WxMaTemplateData> data;

    private String emphasisKeyword;


//    public static ParamBuilder builder(MaTemplateConfig config, String[][] data) {
//        return new ParamBuilder(config,data,clz);
//    }
//    public static class ParamBuilder{
//        private String page;
//        private Integer shopId;
//        private Integer messageTemplateId;
//        private List<Integer> userIdList;
//
//
//        private List<WxMaTemplateData> data;
//        public ParamBuilder( MaTemplateConfig config,String[][] data) {
//            this.data = new ArrayList<>();
//            List<String> list=MaAndMpTemplate.TEMPLATE_MAP.get(template);
//            for(int i = 0,len = data.length;i<len;i++){
//                if( data[i].length == 2 ){
//                    this.data.add(new WxMaTemplateData(list.get(i),data[i][0],data[i][1]));
//                }else{
//                    this.data.add(new WxMaTemplateData(list.get(i),data[i][0]));
//                }
//            }
//        }
//        public ParamBuilder userIdList(List<Integer> userIdList){
//            this.userIdList = userIdList;
//            return this;
//        }
//        public ParamBuilder messageTemplateId(Integer messageTemplateId){
//            this.messageTemplateId = messageTemplateId;
//            return this;
//        }
//        public ParamBuilder shopId(Integer shopId){
//            this.shopId = shopId;
//            return this;
//        }
//        public ParamBuilder page(String page){
//            this.page = page;
//            return this;
//        }
//    }
}
