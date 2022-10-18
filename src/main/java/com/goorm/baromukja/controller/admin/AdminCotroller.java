package com.goorm.baromukja.controller.admin;

import com.goorm.baromukja.baseUtil.response.dto.CommonResponse;
import com.goorm.baromukja.baseUtil.response.dto.ListResponse;
import com.goorm.baromukja.baseUtil.response.service.ResponseService;
import com.goorm.baromukja.dto.member.MemberResponse;
import com.goorm.baromukja.dto.reservation.ReservationRequest;
import com.goorm.baromukja.dto.reservation.ReservationResponse;
import com.goorm.baromukja.service.MemberService;
import com.goorm.baromukja.service.ReservationServiceImpl;
import com.goorm.baromukja.service.service.ReservationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author : Jeeseob
 * @CreateAt : 2022/10/15
 */

@Api(tags = "00. 관리자")
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminCotroller {

    private final ReservationServiceImpl reservationService;
    private final ResponseService responseService;
    private final MemberService memberService;

    @ApiOperation(value = "모든 회원 조회", notes = "모든 회원을 조회합니다.(삭제 예정, 테스트용 API)")
    @GetMapping("/all")
    public ListResponse<MemberResponse> findAllMember() {
        List<MemberResponse> allByName = memberService.findAll();
        return responseService.listResult(allByName);
    }

    @ApiOperation(value = "예약 리스트(이후 삭제)", notes = "예약 id값을 기반으로 해당 예약 정보를 조회")
    @GetMapping("/reservation/list")
    public ListResponse<ReservationResponse> findAll(HttpServletRequest request) {
        return responseService.listResult(reservationService.findAll());
    }

    @ApiOperation(value = "예약 추가", notes = "예약 정보를 추가(삭제 예정 - front에서는 kafka producer의 api로 보내면 됨)")
    @PostMapping("/reservation/add")
    public CommonResponse addReservation(HttpServletRequest request,
                                         @RequestBody ReservationRequest reservation) {
        reservationService.save(reservation);
        return responseService.successResult();
    }
}
