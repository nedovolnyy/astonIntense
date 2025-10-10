/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.userservice.dto;

import com.userservice.entity.User;

/**
 *
 * @author nedo
 */
public record UserDto(String name, String email, int age) {

    public UserDto(User user) {
        this(user.getName(), user.getEmail(), user.getAge());
    }

    public User toUser() {
        return new User(null, this.name, this.email, this.age);
    }

    public String toString() {
        return "Name: " + name + "\t | email: " + email + "\t | age: " + age;
    }
}
