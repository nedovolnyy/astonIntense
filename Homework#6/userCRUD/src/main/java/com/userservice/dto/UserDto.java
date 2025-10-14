/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.userservice.dto;

import com.userservice.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 *
 * @author nedo
 */
@Schema(description = "User")
public record UserDto(
        @Schema(description = "User's name", example = "Siarhei")
        String name,
        @Schema(description = "User's email", example = "mail@smail.su")
        String email,
        @Schema(description = "User's age")
        int age) {

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
