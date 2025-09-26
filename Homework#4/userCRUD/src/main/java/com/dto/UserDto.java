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
    
    public UserDto(){
    }
    
    public UserDto(User user){
        name = user.getName();
        email = user.getEmail();
        age = user.getAge();
    }
    
    public User toUser(){
        return new User(this.name, this.email, this.age);
    }

    public String toString() {
        return "Name: " + name + "\t | email: " + email + "\t | age: " + age;
    }
}
