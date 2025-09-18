/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author AKrot
 */
@AllArgsConstructor
@Getter
public enum Operation {
    INSERT(1),
    UPDATE(2),
    DELETE(3),
    GET_ALL(4),
    GET_BY_ID(5),
    QUIT(0),
    NONE(-1);
    
    final int operationCode;
}
