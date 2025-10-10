/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.notificationservice.service;

import com.notificationservice.dto.UserNotificationMessageDto;
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

    public Status sendMessage(UserNotificationMessageDto userNotificationMessageDto) {
        var mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("admin@userservice.su");
        mailMessage.setTo(userNotificationMessageDto.email());
        mailMessage.setSubject("Account notification");
        if (userNotificationMessageDto.operationType() == OperationType.CREATE) {
            mailMessage.setText("Здравствуйте! Ваш аккаунт на сайте ваш сайт был успешно создан.");
        } else {
            mailMessage.setText("Здравствуйте! Ваш аккаунт был удалён.");
        }
        try {
            mailSender.send(mailMessage);
            return Status.APPROVED;
        } catch (Exception e) {
            return Status.CANCELED;
        }
    }
}
