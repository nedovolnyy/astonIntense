/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.notificationservice.utils.enums;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author nedo
 */
@RequiredArgsConstructor
public enum OperationType {
    CREATE(1),
    UPDATE(2),
    DELETE(3);
    
    final int operationCode;
}
