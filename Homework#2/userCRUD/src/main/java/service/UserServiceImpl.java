/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import model.User;
import repository.UserDao;

/**
 *
 * @author AKrot
 */
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    public UserServiceImpl(UserDao userDao) {
        super(userDao);
    }

}
