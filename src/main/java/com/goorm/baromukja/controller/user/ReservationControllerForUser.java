package com.goorm.baromukja.controller.user;

import com.goorm.baromukja.baseUtil.config.JwtProperties;
import com.goorm.baromukja.baseUtil.config.service.JwtService;
import com.goorm.baromukja.baseUtil.exception.BussinessException;
import com.goorm.baromukja.baseUtil.exception.ExMessage;
import com.goorm.baromukja.baseUtil.response.dto.CommonResponse;
import com.goorm.baromukja.baseUtil.response.dto.ListResponse;
import com.goorm.baromukja.baseUtil.response.dto.SingleResponse;
import com.goorm.baromukja.baseUtil.response.service.ResponseService;
import com.goorm.baromukja.dto.reservation.ReservationRequest;
import com.goorm.baromukja.dto.reservation.ReservationResponse;
import com.goorm.baromukja.dto.restaurant.RestaurantResponse;
import com.goorm.baromukja.entity.MemberRole;
import com.goorm.baromukja.entity.Restaurant;
import com.goorm.baromukja.service.ReservationServiceImpl;
import com.goorm.baromukja.service.RestaurantServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author : Jeeseob
 * @CreateAt : 2022/10/05
 */

@Api(tags = "03-2. 예약 - user")
@RequestMapping("api/v1/user/reservation")
@RestController
@RequiredArgsConstructor
public class ReservationControllerForUser {
    private final ReservationServiceImpl reservationService;
    private final ResponseService responseService;
    private final JwtService jwtService;

    @ApiOperation(value = "예약 상세 정보", notes = "예약 id값을 기반으로 해당 예약 상세 정보를 조회")
    @GetMapping("/detail/{reservationId}")
    public SingleResponse<ReservationResponse> findById(HttpServletRequest request,
                                                        @PathVariable Long reservationId) {
        // TODO: 해당 예약을 한 유저가 맞는지 확인
        return responseService.singleResult(reservationService.findById(reservationId));
    }


    @ApiOperation(value = "예약 취소", notes = "예약 id값을 기반으로 해당 예약을 취소한다")
    @DeleteMapping("/delete/{reservationId}")
    public CommonResponse delete(HttpServletRequest request,
                                 @PathVariable Long reservationId) {
        // TODO: 해당 예약을 한 유저가 맞는지 확인
        reservationService.delete(reservationId);
        return responseService.successResult();
    }


    @ApiOperation(value = "예약 리스트(member)", notes = "token의 유저정보를 기반으로 해당 예약 정보를 조회")
    @GetMapping("/member/list")
    public ListResponse<ReservationResponse> findListByUser(HttpServletRequest request) {
        String username = jwtService.decode(request.getHeader("Authorization").replace(JwtProperties.TOKEN_PREFIX, ""));
        return responseService.listResult(reservationService.findAllByUsername(username));
    }
}
