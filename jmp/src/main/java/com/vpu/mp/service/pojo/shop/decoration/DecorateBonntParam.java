package com.vpu.mp.service.pojo.shop.decoration;


import lombok.Data;

@Data
public class DecorateBonntParam {

    //名称
    private String text;
    //
    private Integer btn;
    //松开时显示图片
    private String normal;
    //按下时显示图片
    private String hover;
    //跳转页面
    private String page;

}
