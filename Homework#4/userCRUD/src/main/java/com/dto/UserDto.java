/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dto;

import com.entity.User;
import lombok.*;

/**
 *
 * @author nedo
 */
@Data
public class UserDto {

    private String name;
    private String email;
    private int age;

    public UserDto() {
    }

    public UserDto(User user) {
        name = user.getName();
        email = user.getEmail();
        age = user.getAge();
    }

    public UserDto(String name, String email, Integer age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }

    public User toUser() {
        return new User(null, this.name, this.email, this.age);
    }

    public String toString() {
        return "Name: " + name + "\t | email: " + email + "\t | age: " + age;
    }
}
