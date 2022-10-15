package com.goorm.baromukja.baseUtil.exception.exceptionHandleClass;

import com.goorm.baromukja.baseUtil.exception.BussinessException;
import com.goorm.baromukja.baseUtil.response.dto.CommonResponse;
import com.goorm.baromukja.baseUtil.response.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author : Jeeseob
 * @CreateAt : 2022/10/05
 */

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final ResponseService responseService;

    @ExceptionHandler(BussinessException.class)
    protected CommonResponse globalBusinessExceptionHandler(BussinessException e) {
        return responseService.failResult(e.getMessage());
    }
}
