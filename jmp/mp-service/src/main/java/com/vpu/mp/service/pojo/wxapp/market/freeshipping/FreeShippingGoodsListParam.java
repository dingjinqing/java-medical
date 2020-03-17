package com.vpu.mp.service.pojo.wxapp.market.freeshipping;

import com.vpu.mp.service.pojo.shop.base.BasePageParam;
import jodd.util.StringUtil;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * 满包邮商品列表
 * @author 孔德成
 * @date 2019/12/11 10:25
 */
@Getter
@Setter
public class FreeShippingGoodsListParam extends BasePageParam {
    Integer userId;
    /**
     * 活动id
     */
    @NotNull
    Integer ruleId;
    /**
     * 查询内容
     */
    String searchText;

    /**
     * 小程序扫码进详情页带的参数
     */
    private String scene;

    public void initScene(){
        if((this.ruleId == null || this.ruleId <= 0) && StringUtil.isNotBlank(this.scene)){
            String scene = null;
            try {
                scene = URLDecoder.decode(this.scene,"UTF-8");
            } catch (UnsupportedEncodingException e) {
            }
            if(StringUtil.isNotEmpty(scene)){
                String[] sceneParam = scene.split("=",2);
                this.ruleId =  Integer.valueOf(sceneParam[1]);
            }
        }
    }
}
