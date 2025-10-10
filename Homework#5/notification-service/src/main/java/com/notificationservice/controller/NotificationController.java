/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.notificationservice.controller;

import com.notificationservice.dto.UserNotificationMessageDto;
import com.notificationservice.service.NotificationService;
import com.notificationservice.utils.enums.OperationType;
import com.notificationservice.utils.enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author nedo
 */
@RestController
@RequestMapping("api/v1/notification")
@RequiredArgsConstructor
public class NotificationController {

    final NotificationService notificationService;

    @PostMapping(path = "/send-create-message", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendCreateMessage(@RequestBody String email) {
        var userNotificationMessageDto = new UserNotificationMessageDto(OperationType.CREATE, email);
        return handleServiceResponse(notificationService.sendMessage(userNotificationMessageDto));
    }

    @PostMapping(path = "/send-delete-message", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendDeleteMessage(@RequestBody String email) {
        var userNotificationMessageDto = new UserNotificationMessageDto(OperationType.DELETE, email);
        return handleServiceResponse(notificationService.sendMessage(userNotificationMessageDto));
    }

    private ResponseEntity<?> handleServiceResponse(Status code) {
        if (code == Status.APPROVED) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Sending error");
    }
}
