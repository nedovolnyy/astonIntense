/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.userservice.producer;

import com.userservice.dto.MessageDto;
import com.userservice.utils.KafkaProducerConfigurationProperties;
import com.userservice.utils.enums.OperationType;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

/**
 *
 * @author nedo
 */
@Component
@RequiredArgsConstructor
public class MessageDtoKafkaSender {

    Logger logger = LoggerFactory.getLogger(MessageDtoKafkaSender.class);
    
    @Autowired
    final KafkaTemplate<String, MessageDto> kafkaTemplate;

    final KafkaProducerConfigurationProperties kafkaProducerConfigurationProperties;

    public void sendMessage(OperationType operationType, MessageDto messageDto) {
        CompletableFuture<SendResult<String, MessageDto>> future = null;
        switch (operationType){
            case OperationType.CREATE -> future = kafkaTemplate.send(kafkaProducerConfigurationProperties.createTopic(), messageDto);
            case OperationType.DELETE -> future = kafkaTemplate.send(kafkaProducerConfigurationProperties.deleteTopic(), messageDto);
        }
        
        future.whenComplete((result, throwable) -> {

            if (throwable != null) {
                logger.error("Unable to send: " + messageDto, throwable);
            } else {
                logger.info("Sent: " + messageDto + " with offset: " + result.getRecordMetadata().offset());
            }
        });

    }
}
