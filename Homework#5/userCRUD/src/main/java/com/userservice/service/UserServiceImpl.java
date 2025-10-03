/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.userservice.service;

import com.userservice.dto.MessageDto;
import com.userservice.dto.UserDto;
import com.userservice.entity.User;
import com.userservice.producer.MessageDtoKafkaSender;
import com.userservice.repository.UserRepository;
import com.userservice.utils.enums.OperationType;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author AKrot
 */
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    final MessageDtoKafkaSender messageDtoKafkaSender;
    
    public UserDto getById(Integer id) {
        return new UserDto(userRepository.findById(id).orElseThrow());
    }

    public List<UserDto> getAll() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .map(UserDto::new)
                .collect(Collectors.toList());
    }

    public OperationType save(UserDto userDto) {
        try {
            userRepository.save(userDto.toUser());
            var messageDto = new MessageDto(OperationType.CREATE, userDto.email());
            messageDtoKafkaSender.sendMessage(OperationType.CREATE, messageDto);
            return OperationType.CREATE;
        } catch (Exception e) {
            return OperationType.ERROR;
        }
    }

    public OperationType update(UserDto userDto, Integer id) {
        try {
            userRepository.save(new User(id, userDto.name(), userDto.email(), userDto.age()));
            return OperationType.UPDATE;
        } catch (Exception e) {
            return OperationType.ERROR;
        }
    }

    public OperationType delete(Integer id) {
        try {
            var user = userRepository.findById(id).orElseThrow();
            userRepository.delete(user);
            var messageDto = new MessageDto(OperationType.DELETE, user.getEmail());
            messageDtoKafkaSender.sendMessage(OperationType.DELETE, messageDto);
            return OperationType.DELETE;
        } catch (Exception e) {
            return OperationType.ERROR;
        }
    }
}
