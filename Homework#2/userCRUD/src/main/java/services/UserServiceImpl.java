/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import entities.User;
import repositories.UserDaoImpl;

/**
 *
 * @author AKrot
 */
public class UserServiceImpl extends BaseServiceImpl<User> {

    public UserServiceImpl(UserDaoImpl userDao) {
        super(userDao);
    }

}
