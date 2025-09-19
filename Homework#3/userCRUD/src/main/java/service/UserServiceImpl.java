/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.sql.SQLException;
import java.util.List;
import lombok.AllArgsConstructor;
import model.User;
import repository.UserDao;
import utils.enums.Operation;

/**
 *
 * @author AKrot
 */
@AllArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserDao userDao;

    public User getById(Integer id) throws SQLException {
        return userDao.getById(id);
    }

    public List<User> getAll() throws SQLException {
        return userDao.getAll();
    }

    public Operation insert(User user) throws SQLException {
        return userDao.insert(user);
    }

    public Operation update(User user) throws SQLException {
        return userDao.update(user);
    }

    public Operation delete(User user) throws SQLException {
        return userDao.delete(user);
    }
}
