/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.notificationservice.dto;

import com.notificationservice.utils.enums.OperationType;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 *
 * @author nedo
 */
@Schema(description = "Message")
public record UserNotificationMessageDto(
        @Schema(description = "Type of current operation", example = "CREATE")
        OperationType operationType,
        @Schema(description = "User's email", example = "mail@smail.su")
        String email) {

}
