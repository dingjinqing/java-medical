package com.vpu.mp.service.pojo.shop.user.message;

/**
 * 小程序模版和公众号模版映射
 * @author 卢光耀
 * @date 2019-08-22 09:53
 *
*/
public enum MaAndMpTemplate {
    /**
     * 营销管理-消息推送模版
     */
    MESSAGE_TEMPLATE(MaTemplateConstant.ACTIVITY_CONFIG,MpTemplateConstant.ACTIVITY_CONFIG);


    private MaTemplateConfig maTemplateConfig;
    private MpTemplateConfig mpTemplateConfig;

     MaAndMpTemplate(MaTemplateConfig maTemplateConfig,MpTemplateConfig mpTemplateConfig){
        this.maTemplateConfig = maTemplateConfig;
        this.mpTemplateConfig = mpTemplateConfig;
    }

    public static MpTemplateConfig getMpTemplateConfigByMa(MaTemplateConfig maTemplateConfig){
         for (MaAndMpTemplate x:MaAndMpTemplate.values()){
             if(x.maTemplateConfig.getId().equals(maTemplateConfig.getId())){
                 return x.mpTemplateConfig;
             }
         }
         return null;
    }
}
