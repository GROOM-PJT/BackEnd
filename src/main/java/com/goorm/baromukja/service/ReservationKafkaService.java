package com.goorm.baromukja.service;

import com.goorm.baromukja.dto.reservation.ReservationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

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

//    @KafkaHandler
//    public void listData(@Payload List<ReservationRequest> reservations) {
//        for(ReservationRequest reservation : reservations) {
//            reservationService.save(reservation);
//            log.info("Consumed Message List: " + reservation.toString());
//        }
//    }

    @KafkaHandler
    public void singleData(@Payload ReservationRequest reservation) {
        // TODO: 알림형태로, 현재 상황을 보내줘야 한다.
        // log.info("Consumed Header : " + headers.toString());
        reservationService.save(reservation);
        log.info("Consumed Message Single: " + reservation.toString());
    }

    // ReservationRequest가 아닌 다른 object데이터가 존재하는 경우, 무시
    @KafkaHandler(isDefault = true)
    public void ignore(ConsumerRecord<?, ?> record) {
        log.info("ignore");
        log.info("reservation kafka service (ignore data): " + record.toString());
        //log.info("reservation kafka service (ignore data): " + jsonSerializable.toString());
    }
}
