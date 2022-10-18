package com.goorm.baromukja.baseUtil.response.service;

import com.goorm.baromukja.baseUtil.response.dto.CommonResponse;
import com.goorm.baromukja.baseUtil.response.dto.CommonResult;
import com.goorm.baromukja.baseUtil.response.dto.ListResponse;
import com.goorm.baromukja.baseUtil.response.dto.SingleResponse;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author : Jeeseob
 * @CreateAt : 2022/10/04
 */

@Service
public class ResponseService {

    private void setSuccessResult(CommonResponse response) {
        response.setCode(CommonResult.SUCCESS.getCode());
        response.setMessage(CommonResult.SUCCESS.getMessage());
    }

    public CommonResponse successResult() {
        CommonResponse response = new CommonResponse();
        this.setSuccessResult(response);
        return response;
    }

    public CommonResponse failResult(String message) {
        CommonResponse response = new CommonResponse();
        response.setCode(CommonResult.FAIL.getCode());
        response.setMessage(message);

        return response;
    }

    public <T> SingleResponse<T> singleResult(T data) {
        SingleResponse<T> response = new SingleResponse<>(data);
        this.setSuccessResult(response);

        return response;
    }

    public <T> ListResponse<T> listResult(List<T> dataList) {
        ListResponse<T> response = new ListResponse<>(dataList);
        this.setSuccessResult(response);

        return response;
    }
}
