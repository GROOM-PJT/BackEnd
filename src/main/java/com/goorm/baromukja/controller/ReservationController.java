package com.goorm.baromukja.controller;

import com.goorm.baromukja.baseUtil.response.dto.CommonResponse;
import com.goorm.baromukja.baseUtil.response.dto.ListResponse;
import com.goorm.baromukja.baseUtil.response.dto.SingleResponse;
import com.goorm.baromukja.baseUtil.response.service.ResponseService;
import com.goorm.baromukja.dto.reservation.ReservationRequest;
import com.goorm.baromukja.dto.reservation.ReservationResponse;
import com.goorm.baromukja.service.ReservationServiceImpl;
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

    @ApiOperation(value = "예약 리스트", notes = "예약 id값을 기반으로 해당 예약 상세 정보를 조회")
    @GetMapping("/list")
    public ListResponse<ReservationResponse> findAll(HttpServletRequest request) {
        // 해당 유저가 접근 권한이 있는지 확인해야함
        return responseService.listResult(reservationService.findAll());
    }

    @ApiOperation(value = "예약 취소", notes = "예약 id값을 기반으로 해당 예약을 취소한다")
    @DeleteMapping("/delete/{reservationId}")
    public CommonResponse delete(HttpServletRequest request,
                                 @PathVariable Long reservationId) {
        reservationService.delete(reservationId);
        return responseService.successResult();
    }
}
