package com.goorm.baromukja.controller;

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

@Api(tags = "03. 예약")
@RequestMapping("api/v1/reservation")
@RestController
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationServiceImpl reservationService;
    private final ResponseService responseService;
    private final RestaurantServiceImpl restaurantService;
    private final JwtService jwtService;

    @ApiOperation(value = "예약 추가", notes = "예약 정보를 추가")
    @PostMapping("/add")
    public CommonResponse addReservation(HttpServletRequest request,
                                         @RequestBody ReservationRequest reservation) {
        // request의 header에서 jwt를 가져와서, 유저 정보를 가져온다.
        reservationService.save(reservation);
        return responseService.successResult();
    }

    @ApiOperation(value = "예약 상세 정보", notes = "예약 id값을 기반으로 해당 예약 상세 정보를 조회")
    @GetMapping("/detail/{reservationId}")
    public SingleResponse<ReservationResponse> findById(HttpServletRequest request,
                                                        @PathVariable Long reservationId) {
        return responseService.singleResult(reservationService.findById(reservationId));
    }


    @ApiOperation(value = "예약 취소", notes = "예약 id값을 기반으로 해당 예약을 취소한다")
    @DeleteMapping("/delete/{reservationId}")
    public CommonResponse delete(HttpServletRequest request,
                                 @PathVariable Long reservationId) {
        reservationService.delete(reservationId);
        return responseService.successResult();
    }


    @ApiOperation(value = "예약 리스트(member)", notes = "token의 유저정보를 기반으로 해당 예약 정보를 조회")
    @GetMapping("/member/list")
    public ListResponse<ReservationResponse> findListByUser(HttpServletRequest request) {
        String username = jwtService.decode(request.getHeader("Authorization"));
        return responseService.listResult(reservationService.findAllByUsername(username));
    }


    @ApiOperation(value = "예약 리스트(restaurantId) ", notes = "restaurantId를 기반으로 해당 예약 정보를 조회")
    @GetMapping("/restaurant/list/{restaurantId}")
    public ListResponse<ReservationResponse> findListByRestaurant(HttpServletRequest request,
                                                                  @PathVariable Long restaurantId) {

        RestaurantResponse restaurantResponse = restaurantService.findById(restaurantId);
        String username = jwtService.decode(request.getHeader("Authorization"));
        if (username.equals(restaurantResponse.getMemberRes().getUsername())) {
            return responseService.listResult(reservationService.findAllByRestaurantId(restaurantId));
        }
        throw new BussinessException(ExMessage.NO_AUTHORITY.getMessage());
    }


    @ApiOperation(value = "예약 리스트(이후 삭제)", notes = "예약 id값을 기반으로 해당 예약 정보를 조회")
    @GetMapping("/list")
    public ListResponse<ReservationResponse> findAll(HttpServletRequest request) {
        // 해당 유저가 접근 권한이 있는지 확인해야함
        return responseService.listResult(reservationService.findAll());
    }
}
