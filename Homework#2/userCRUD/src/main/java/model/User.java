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

/**
 *
 * @author AKrot
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "`user`")
public class User extends BaseEntity {

    private String name;
    private String email;
    private int age;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Override
    public String toString() {
        return "ID: " + getId() + "\t | Name: " + name + "\t | email: " + email
                + "\t | age: " + age + "\t | Created: " + createdAt;
    }
}
