package com.vpu.mp.generate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vpu.mp.service.pojo.wxapp.order.CreateOrderVo;
import com.vpu.mp.service.pojo.wxapp.order.CreateParam;
import com.vpu.mp.service.pojo.wxapp.order.goods.OrderGoodsBo;
import com.vpu.mp.service.pojo.wxapp.order.must.OrderMustParam;
import com.vpu.mp.service.pojo.wxapp.pay.base.WebPayVo;
import com.vpu.mp.service.pojo.wxapp.pay.jsapi.JsApiVo;

import java.lang.reflect.Field;

public class GenerateWike {
    public static void main(String[] args) {
        Class<?> cls = OrderMustParam.class;
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(JsonIgnore.class)) {
                continue;
            }
            System.out.print("||");
            System.out.print(field.getName());
            System.out.print("||");
            System.out.print(field.getType().getSimpleName());
            System.out.print("||");
            System.out.print(" ||");
            System.out.println();
        }
    }
}
