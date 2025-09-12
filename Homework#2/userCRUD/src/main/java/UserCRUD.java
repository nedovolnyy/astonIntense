/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

import java.sql.SQLException;
import model.User;
import repository.UserDaoImpl;
import service.UserServiceImpl;
import utils.LogUtil;

/**
 *
 * @author AKrot
 */
public class UserCRUD {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        LogUtil.info("Appication started.");

        var userDao = new UserDaoImpl();
        var userService = new UserServiceImpl(userDao);
        var users = userService.getAll();
        for (User user : users) {
            System.out.println(user);
        }

        System.out.println(userService.getById(6).toString());
    }
}
