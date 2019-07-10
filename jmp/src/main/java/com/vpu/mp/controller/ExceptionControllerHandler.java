package com.vpu.mp.controller;

import com.vpu.mp.service.foundation.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

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
            logger.debug("valid msg:"+result.getFieldError().getDefaultMessage());
            if( result.hasErrors() ){
                return this.fail(result.getFieldError().getDefaultMessage());
            }
        return null;
    }

}
