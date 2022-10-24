package com.goorm.baromukja.controller.admin;

import com.goorm.baromukja.baseUtil.config.JwtProperties;
import com.goorm.baromukja.baseUtil.config.service.JwtService;
import com.goorm.baromukja.baseUtil.exception.BussinessException;
import com.goorm.baromukja.baseUtil.exception.ExMessage;
import com.goorm.baromukja.baseUtil.response.dto.CommonResponse;
import com.goorm.baromukja.baseUtil.response.dto.ListResponse;
import com.goorm.baromukja.baseUtil.response.service.ResponseService;
import com.goorm.baromukja.dto.reservation.ReservationRequest;
import com.goorm.baromukja.dto.reservation.ReservationResponse;
import com.goorm.baromukja.dto.restaurant.RestaurantResponse;
import com.goorm.baromukja.dto.restaurant.RestaurantResponseWithMember;
import com.goorm.baromukja.service.ReservationServiceImpl;
import com.goorm.baromukja.service.RestaurantServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author : Jeeseob
 * @CreateAt : 2022/10/15
 */


@Api(tags = "03-1. 예약 - admin")
@CrossOrigin
@RequestMapping("api/v1/admin/reservation")
@RestController
@RequiredArgsConstructor
public class ReservationControllerForAdmin {

    private final ReservationServiceImpl reservationService;
    private final ResponseService responseService;
    private final RestaurantServiceImpl restaurantService;
    private final JwtService jwtService;

    @ApiOperation(value = "예약 리스트(restaurantId) ", notes = "restaurantId를 기반으로 해당 예약 정보를 조회")
    @GetMapping("/restaurant/list/{restaurantId}")
    public ListResponse<ReservationResponse> findListByRestaurant(HttpServletRequest request,
                                                                  @PathVariable Long restaurantId) {

        RestaurantResponseWithMember restaurantResponseWithMember = restaurantService.findByIdWithMember(restaurantId);
        String username = jwtService.decode(request.getHeader("Authorization").replace(JwtProperties.TOKEN_PREFIX, ""));
        if (username.equals(restaurantResponseWithMember.getUsername())) {
            return responseService.listResult(reservationService.findAllByRestaurantId(restaurantId));
        }
        throw new BussinessException(ExMessage.NO_AUTHORITY.getMessage());
    }
}
