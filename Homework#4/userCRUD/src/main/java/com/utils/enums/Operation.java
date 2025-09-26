/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utils.enums;

import lombok.Getter;

/**
 *
 * @author AKrot
 */
@Getter
public enum Operation {
    INSERT(1),
    UPDATE(2),
    DELETE(3),
    GET_ALL(4),
    GET_BY_ID(5),
    NONE(0),
    ERROR(-1);
    
    final int operationCode;

    private Operation(int operationCode) {
        this.operationCode = operationCode;
    }
}
