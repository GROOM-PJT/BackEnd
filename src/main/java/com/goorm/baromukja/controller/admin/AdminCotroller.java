package com.goorm.baromukja.controller.admin;

import com.goorm.baromukja.baseUtil.response.dto.CommonResponse;
import com.goorm.baromukja.baseUtil.response.dto.ListResponse;
import com.goorm.baromukja.baseUtil.response.service.ResponseService;
import com.goorm.baromukja.dto.member.MemberResponse;
import com.goorm.baromukja.dto.reservation.ReservationRequest;
import com.goorm.baromukja.dto.reservation.ReservationResponse;
import com.goorm.baromukja.service.MemberService;
import com.goorm.baromukja.service.NotificationService;
import com.goorm.baromukja.service.ReservationServiceImpl;
import com.goorm.baromukja.service.RestaurantServiceImpl;
import com.goorm.baromukja.service.service.ReservationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author : Jeeseob
 * @CreateAt : 2022/10/15
 */

@Slf4j
@Api(tags = "00. 관리자")
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member/admin")
public class AdminCotroller {

    private final ReservationServiceImpl reservationService;
    private final ResponseService responseService;
    private final RestaurantServiceImpl restaurantService;
    private final MemberService memberService;
    private final NotificationService notificationService;

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
        LocalDateTime canReservationTime = reservation.getReservationTime().minusDays(7).withHour(10).withMinute(0).withSecond(0);
        // 예약 신청날짜 - 7일의 10시 사이에 현재시간이 있으면, 예약 가능
        if (reservation.getCreateAt().isAfter(canReservationTime)) {
            int canReservations = reservationService.countNumberOfReservations(reservation.getRestaurantId(), reservation.getReservationTime());
            int availableCount = restaurantService.findById(reservation.getRestaurantId()).getAvailableCount();
            if (reservation.getNumberOfReservations() + canReservations <= availableCount) {
                reservationService.save(reservation);
                log.info("Consumed Message Single: " + reservation.toString());
                notificationService.send(reservation.getUsername(), "reservation Success.");
                log.info("예약 성공");
                return responseService.successResult();
            }
        }
        notificationService.send(reservation.getUsername(), "reservation Failure.");
        return responseService.failResult("인원 초과");
    }
}

