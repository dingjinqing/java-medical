package com.vpu.mp.controller;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.foundation.exception.BusinessException;
import me.chanjar.weixin.common.error.WxErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

/**
 * controller全局异常捕获处理
 * @author: 卢光耀
 * @date: 2019-07-10 11:22
 *
*/
@RestControllerAdvice(basePackages = "com.vpu.mp.controller")
public class ExceptionControllerHandler extends BaseController {

    Logger logger= LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public JsonResult request(MethodArgumentNotValidException e){
            BindingResult result = ((MethodArgumentNotValidException) e).getBindingResult();
            logger.error("valid msg:"+result.getFieldError().getDefaultMessage());
            if( result.hasErrors() ){
                return this.fail(result.getFieldError().getDefaultMessage());
            }
        return null;
    }
    @ExceptionHandler(BindException.class)
    public Object validExceptionHandler(BindException e){
        BindingResult result = e.getBindingResult();
        if( result.hasErrors() ){
            return this.fail(result.getFieldError().getDefaultMessage());
        }
        return null;
    }
    /**
     * 对于WxErrorException的统一处理
     * @param e
     * @return
     */
    @ExceptionHandler({WxErrorException.class})
    public JsonResult procesErrorException(WxErrorException e){
    	return fail(JsonResultCode.WX_ERROR_EXCEPTION,e.getError().getErrorCode(),e.getError().getErrorMsg());
    }

    /**
     * json参数转换错误处理
     *
     * @param e
     * @return
     * @throws IOException
     */
     @ExceptionHandler(HttpMessageNotReadableException.class)
    public JsonResult request1(HttpMessageNotReadableException e) throws IOException {
         if (e.getCause()==null){
             logger.debug("valid msg:"+e.getMessage());
             return fail(JsonResultMessage.MSG_PARAM_ERROR);
         }
         logger.debug("valid msg:"+e.getCause().getMessage());
        return fail(JsonResultMessage.MSG_PARAM_ERROR);
    }

    /**
     * 处理参数异常
     *
     * @author 郑保乐
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public JsonResult handleArgumentExceptions(IllegalArgumentException e) {
        return fail(e.getMessage());
    }

    /**
     * 常规业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public JsonResult businessException(BusinessException e){
        if (e.getCode() != null) {
            if (e.getArgs().length != 0) {
                return fail(e.getCode(), e.getArgs());
            }
            return fail(e.getCode());
        } else {
            return fail(e.getErrorMessage());
        }
    }
}
