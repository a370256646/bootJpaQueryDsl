package com.xp.exe.bootjpaquerydsl.config;

import com.xp.exe.bootjpaquerydsl.common.BaseResultData;
import com.xp.exe.bootjpaquerydsl.enums.BaseReturnCodeEnum;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * @description: 默认的全局异常处理类实现
 * @author: Administrator
 * @date: 2022/7/5 16:31
 * @version: 1.0
 */
@Slf4j
@RestControllerAdvice
public class BaseRestExceptionHandler {

    /**
     * @description: 全局默认异常处理
     * @param: [ex]
     * @return: com.xp.exe.bootjpaquerydsl.common.BaseResultData
     * @author: coderXp
     * @date: 2022/11/9
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public <T> BaseResultData<T> globalExceptionHandle(Exception ex) {
        log.error("全局异常信息:{}", ex.getMessage(), ex);
        return BaseResultData.failWithCodeAndMessage(BaseReturnCodeEnum.INTERNAL_FAIL, ex.getMessage());
    }

    /**
     * @description: spring validate参数校验错误异常处理
     * @param: [ex]
     * @return: com.xp.exe.bootjpaquerydsl.common.BaseResultData
     * @author: coderXp
     * @date: 2022/11/9
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public <T> BaseResultData<T> bodyValidationExceptionHandle(MethodArgumentNotValidException ex) {
        log.error("body参数校验异常信息:{}", ex.getMessage());
        // requestBody上validate失败后抛出此异常
        String errorMsg = ex.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(",", "[", "]"));
        return BaseResultData.failWithCodeAndMessage(BaseReturnCodeEnum.BAD_REQUEST, errorMsg);
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public <T> BaseResultData<T> requestGetValidationExceptionHandle(BindException ex) {
        log.error("get请求参数校验异常信息:{}", ex.getMessage());
        // get请求方式上validate失败后抛出此异常
        String errorMsg = ex.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(",", "[", "]"));
        return BaseResultData.failWithCodeAndMessage(BaseReturnCodeEnum.BAD_REQUEST, errorMsg);
    }


    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public <T> BaseResultData<T> requestParamValidationExceptionHandle(ConstraintViolationException ex) {
        log.error("requestParam请求参数校验异常信息:{}", ex.getMessage());
        // requestParam上validate失败会抛出此异常
        // 截取到:后面的部分
        String errorMsg = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(",", "[", "]"));
        return BaseResultData.failWithCodeAndMessage(BaseReturnCodeEnum.BAD_REQUEST, errorMsg);
    }
}
