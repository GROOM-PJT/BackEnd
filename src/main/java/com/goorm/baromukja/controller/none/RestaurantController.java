package com.goorm.baromukja.controller.none;

import com.goorm.baromukja.baseUtil.response.dto.ListResponse;
import com.goorm.baromukja.baseUtil.response.dto.SingleResponse;
import com.goorm.baromukja.baseUtil.response.service.ResponseService;
import com.goorm.baromukja.dto.restaurant.RestaurantResponse;
import com.goorm.baromukja.dto.restaurant.RestaurantResponseDetail;
import com.goorm.baromukja.service.RestaurantServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author : Jeeseob
 * @CreateAt : 2022/10/15
 */

@Slf4j
@Api(tags = "04. 식당")
@CrossOrigin
@RequestMapping("api/v1/restaurant")
@RestController
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantServiceImpl restaurantService;
    private final ResponseService responseService;

    @ApiOperation(value = "식당 상세 정보", notes = "식당의 상세 정보를 조회")
    @GetMapping("/detail/{restaurantId}")
    public SingleResponse<RestaurantResponseDetail> findById(HttpServletRequest request,
                                                             @PathVariable Long restaurantId) {
        return responseService.singleResult(restaurantService.findByIdDetail(restaurantId));
    }


    @ApiOperation(value = "식당 리스트", notes = "식당 리스트")
    @GetMapping("/all")
    public ListResponse<RestaurantResponse> findAll(HttpServletRequest request) {
        return responseService.listResult(restaurantService.findAll());
    }

    /**
     * TODO : 지역별, 분류별 식당 리스트 controller 필요
     */
    @ApiOperation(value = "식당 리스트(지역별)", notes = "식당 리스트")
    @GetMapping("/location/list")
    public ListResponse<RestaurantResponse> findAllByProvince(HttpServletRequest request,
                                                           @RequestParam String location) {
        return responseService.listResult(restaurantService.findAllByProvince(location));
    }

    @ApiOperation(value = "식당 리스트(분류별)", notes = "식당 리스트")
    @GetMapping("/theme/list")
    public ListResponse<RestaurantResponse> findAllByTheme(HttpServletRequest request,
                                                        @RequestParam String theme) {
        return responseService.listResult(restaurantService.findAllByTheme(theme));
    }


}
