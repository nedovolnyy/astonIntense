/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */



import entities.User;
import repositories.UserDaoImpl;
import services.UserServiceImpl;

/**
 *
 * @author AKrot
 */
public class UserCRUD {

    public static void main(String[] args) {
        var userDao = new UserDaoImpl();
        var userService = new UserServiceImpl(userDao);
        var users = userService.getAll();
        for (User user : users) {
            System.out.println(user);
        }
        
        System.out.println(userService.getById(6).toString());
    }
}
