/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.notificationservice.dto;

import com.notificationservice.utils.enums.OperationType;

/**
 *
 * @author nedo
 */
public record UserNotificationMessageDto(OperationType operationType,
        String email) {

}
