/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import java.time.LocalDateTime;
import lombok.*;

/**
 *
 * @author AKrot
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserDto {
    private int id;
    private String name;
    private String email;
    private int age;
    private LocalDateTime createdAt;

    @Override
    public String toString() {
        return "ID: " + id + " | Name: " + name + " | email: " + email +
                " | age: " + age + " | Created: " + createdAt;
    }
}
