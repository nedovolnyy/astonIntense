/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

/**
 *
 * @author AKrot
 */
@Data
@Entity
@Table(name = "`user`")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;
    private int age;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT now()")
    private LocalDateTime createdAt = LocalDateTime.now();

    public User(String name, String email, int age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }

    public User(Integer id, String name, String email, int age) {
        this(name, email, age);
        this.id = id;
    }

    public User(String name, String email, int age, LocalDateTime createdAt) {
        this(name, email, age);
        this.createdAt = createdAt;
    }

    public User(Integer id, String name, String email, int age, LocalDateTime createdAt) {
        this(id, name, email, age);
        this.createdAt = createdAt;
    }
}
