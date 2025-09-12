/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import dtos.UserDto;
import entities.User;

/**
 *
 * @author AKrot
 */
public class UserMapper {
    public UserDto userToDto(User user) {
        if (user == null) {
            return null;
        }
        return new UserDto(user.getId(), user.getName(), user.getEmail(), user.getAge(), user.getCreatedAt());
    }
}