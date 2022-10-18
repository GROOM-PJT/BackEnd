package com.goorm.baromukja.service;

import com.goorm.baromukja.baseUtil.exception.BussinessException;
import com.goorm.baromukja.baseUtil.exception.ExMessage;
import com.goorm.baromukja.dto.reservation.ReservationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Calendar;

/**
 * @Author : Jeeseob
 * @CreateAt : 2022/10/11
 */

@Slf4j
@Service
@KafkaListener(
        topics = "${topic.reservation.name}",
        groupId = "${spring.kafka.consumer.group-id}",
        containerFactory = "kafkaListenerContainerFactory"
)
@RequiredArgsConstructor
public class ReservationKafkaService {

    private final ReservationServiceImpl reservationService;
    private final RestaurantServiceImpl restaurantService;
//    @KafkaHandler
//    public void listData(@Payload List<ReservationRequest> reservations) {
//        for(ReservationRequest reservation : reservations) {
//            reservationService.save(reservation);
//            log.info("Consumed Message List: " + reservation.toString());
//        }
//    }

    @KafkaHandler
    public void singleData(@Payload ReservationRequest reservation) {
        // 예약 가능날짜를, 7일로 잡고, 시간을 고정으로 10AM으로 설정
        LocalDateTime canReservationTime = reservation.getReservationTime().minusDays(7).withHour(10).withMinute(0).withSecond(0);
        // 예약 신청날짜 - 7일의 10시 사이에 현재시간이 있으면, 예약 가능
        if (reservation.getCreateAt().isAfter(canReservationTime)) {
            int canReservations = reservationService.countNumberOfReservations(reservation.getRestaurantId(), reservation.getReservationTime());
            int availableCount = restaurantService.findById(reservation.getRestaurantId()).getAvailableCount();
            if(reservation.getNumberOfReservations() + canReservations <= availableCount) {
                reservationService.save(reservation);
                log.info("Consumed Message Single: " + reservation.toString());
            }
        }
        // TODO: 예약 결과 보내기 (Push 메세지 "webSocket")
    }

    // ReservationRequest가 아닌 다른 object데이터가 존재하는 경우, 무시
    @KafkaHandler(isDefault = true)
    public void ignore(ConsumerRecord<?, ?> record) {
        log.info("ignore");
        log.info("reservation kafka service (ignore data): " + record.toString());
        //log.info("reservation kafka service (ignore data): " + jsonSerializable.toString());
    }
}
