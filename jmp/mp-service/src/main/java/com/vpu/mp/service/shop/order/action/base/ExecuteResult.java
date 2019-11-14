package com.vpu.mp.service.shop.order.action.base;

import com.vpu.mp.service.foundation.data.JsonResultCode;
import lombok.Getter;
import lombok.ToString;

/**
 * 订单操作返回值
 * @author 王帅
 */
@ToString
@Getter
public class ExecuteResult {
    private JsonResultCode errorCode;
    /**返回参数*/
    private Object result;
    /**errorCode参数*/
    private String errorParam[];

    /**
     * 是否成功
     * @return
     */
    public boolean IsSuccess(){
        if(errorCode == null){
            return true;
        }
        return false;
    }

    public ExecuteResult(JsonResultCode errorCode, Object result, String[] errorParam) {
        this.errorCode = errorCode;
        this.result = result;
        this.errorParam = errorParam;
    }

    /**
     * 静态构造器
     * @param errorCode errorCode参数
     * @param result 返回结果
     * @param errorParam errorCode参数
     * @return this
     */
    public static ExecuteResult create(JsonResultCode errorCode, Object result, String... errorParam){
        return new ExecuteResult(errorCode, result, errorParam);
    }

    /**
     * 静态构造器
     * @param errorCode errorCode参数
     * @param errorParam errorCode参数
     * @return this
     */
    public static ExecuteResult create(JsonResultCode errorCode, String... errorParam){
        return new ExecuteResult(errorCode, null, errorParam);
    }

}
