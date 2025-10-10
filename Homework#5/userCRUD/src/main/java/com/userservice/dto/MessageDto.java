/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.userservice.dto;

import com.userservice.utils.enums.OperationType;
import java.io.Serializable;

/**
 *
 * @author nedo
 */
public record MessageDto(OperationType operationType,
        String email) implements Serializable {

}
