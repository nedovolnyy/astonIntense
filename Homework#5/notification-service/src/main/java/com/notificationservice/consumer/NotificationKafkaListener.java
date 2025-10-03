/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.notificationservice.consumer;

import com.notificationservice.dto.MessageDto;
import com.notificationservice.service.NotificationService;
import com.notificationservice.utils.enums.OperationType;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

/**
 *
 * @author nedo
 */
@Component
@RequiredArgsConstructor
public class NotificationKafkaListener {

    Logger logger = LoggerFactory.getLogger(NotificationKafkaListener.class);

    final NotificationService notificationService;

    @KafkaListener(topics = "#{'${spring.kafka.consumer.listenable-topics}'.split(',')}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "messageDtoKafkaListenerContainerFactory")
    public void listenMessageDtoTopic(MessageDto messageDto,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        OperationType operationType;
        if (topic.equals("user_create")) {
            operationType = OperationType.CREATE;
        } else {
            operationType = OperationType.DELETE;
        }
        var newMessageDto = new MessageDto(operationType, messageDto.email());
        notificationService.sendMessage(newMessageDto);
        logger.info(String.format("MessageDto recieved -> %s", newMessageDto));
    }

}
