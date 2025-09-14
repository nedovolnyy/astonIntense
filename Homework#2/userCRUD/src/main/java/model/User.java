/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import java.time.LocalDateTime;
import javax.print.attribute.standard.DateTimeAtCompleted;

/**
 *
 * @author AKrot
 */
@AllArgsConstructor
@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "`user`")
public class User extends BaseIdEntity {

    private String name;
    private String email;
    private int age;

    @Column(name = "created_at", columnDefinition="TIMESTAMP WITH TIME ZONE DEFAULT now()")
    private LocalDateTime createdAt = LocalDateTime.now();

    public User(String name, String email, int age){
        this.name = name;
        this.email = email;
        this.age = age;
    }
    
    @Override
    public String toString() {
        return "ID: " + getId() + "\t | Name: " + name + "\t | email: " + email
                + "\t | age: " + age + "\t | Created: " + createdAt;
    }
}
