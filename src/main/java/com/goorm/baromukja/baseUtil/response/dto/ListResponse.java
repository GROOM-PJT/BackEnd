package com.goorm.baromukja.baseUtil.response.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * @Author : Jeeseob
 * @CreateAt : 2022/10/04
 */

@Getter
@RequiredArgsConstructor
public class ListResponse<T> extends CommonResponse {
    private List<T> dataList;
    private int count;

    public ListResponse(List<T> dataList) {
        this.dataList = dataList;
        this.count = dataList.size();
    }
}
