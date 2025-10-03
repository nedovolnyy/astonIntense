/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.notificationservice.service;

import com.notificationservice.dto.MessageDto;
import com.notificationservice.utils.KafkaConsumerConfigurationProperties;
import com.notificationservice.utils.enums.OperationType;
import com.notificationservice.utils.enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

/**
 *
 * @author nedo
 */
@RequiredArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {

    final MailSender mailSender;
    final KafkaConsumerConfigurationProperties kafkaConfigurationProperties;

    public Status sendMessage(MessageDto messageDto) {
        var mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(kafkaConfigurationProperties.fromEmail());
        mailMessage.setTo(messageDto.email());
        mailMessage.setSubject("Account notification");
        if (messageDto.operationType() == OperationType.CREATE) {
            mailMessage.setText(kafkaConfigurationProperties.createMessage());
        } else {
            mailMessage.setText(kafkaConfigurationProperties.deleteMessage());
        }
        try {
            mailSender.send(mailMessage);
            return Status.APPROVED;
        } catch (Exception e) {
            return Status.CANCELED;
        }
    }
}
