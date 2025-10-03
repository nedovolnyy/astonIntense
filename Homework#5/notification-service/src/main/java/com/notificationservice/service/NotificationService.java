/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.notificationservice.service;

import com.notificationservice.dto.MessageDto;
import com.notificationservice.utils.enums.Status;

/**
 *
 * @author nedo
 */
public interface NotificationService {

    Status sendMessage(MessageDto messageDto);

}
