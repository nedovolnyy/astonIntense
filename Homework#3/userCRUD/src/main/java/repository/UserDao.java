/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package repository;

import java.sql.SQLException;
import java.util.List;
import model.User;
import utils.enums.Operation;

/**
 *
 * @author AKrot
 */
public interface UserDao {

    User getById(Integer id) throws SQLException;

    List<User> getAll() throws SQLException;

    Operation insert(User user) throws SQLException;

    Operation update(User user) throws SQLException;

    Operation delete(User user) throws SQLException;

}
