package com.vpu.mp.service.pojo.wxapp.goods.goodssort;

import lombok.Data;

import java.util.List;

/**
 * @author 李晓冰
 * @date 2019年10月18日
 * 小程序-商品分类界面菜单返回内容类
 */
@Data
public class GoodsSortMenuContentVo {

    private String menuImg;
    private String menuImgLink;
    /**返回内容的类型*/
    private Byte menuContentType;

    List<?> contentList;
}
