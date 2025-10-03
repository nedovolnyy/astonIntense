/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.userservice.service;

import com.userservice.dto.UserDto;
import com.userservice.utils.enums.Operation;
import java.util.List;

/**
 *
 * @author AKrot
 */
public interface UserService {

    UserDto getById(Integer id);

    List<UserDto> getAll();

    Operation save(UserDto userDto);

    Operation update(UserDto userDto, Integer id);

    Operation delete(Integer id);

}
