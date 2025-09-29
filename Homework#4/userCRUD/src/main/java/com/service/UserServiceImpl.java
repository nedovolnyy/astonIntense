/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.service;

import com.dto.UserDto;
import com.entity.User;
import com.repository.UserRepository;
import com.utils.enums.Operation;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author AKrot
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDto getById(Integer id) {
        return new UserDto(userRepository.findById(id).orElseThrow());
    }

    public List<UserDto> getAll() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .map(UserDto::new)
                .collect(Collectors.toList());
    }

    public Operation save(UserDto userDto) {
        try {
            userRepository.save(userDto.toUser());
            return Operation.INSERT;
        } catch (Exception e) {
            return Operation.ERROR;
        }
    }

    public Operation update(UserDto userDto, Integer id) {
        try {
            userRepository.save(new User(id, userDto.getName(), userDto.getEmail(), userDto.getAge()));
            return Operation.UPDATE;
        } catch (Exception e) {
            return Operation.ERROR;
        }
    }

    public Operation delete(Integer id) {
        try {
            userRepository.deleteById(id);
            return Operation.DELETE;
        } catch (Exception e) {
            return Operation.ERROR;
        }
    }
}
