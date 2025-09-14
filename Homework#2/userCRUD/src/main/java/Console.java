
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;
import utils.enums.Operation;
import model.User;
import repository.*;
import service.*;
import utils.HibernateUtil;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author AKrot
 */
public class Console {

    private final UserDao userDao = new UserDaoImpl();
    private final UserService userService = new UserServiceImpl(userDao);
    private final Scanner scanner = new Scanner(System.in);

    public void start() throws SQLException, ClassNotFoundException {
        welcome();
        var operation = Operation.NONE;
        while (operation != Operation.QUIT) {
            operation = doMenu();
            switch (operation) {
                case INSERT ->
                    insert();
                case UPDATE ->
                    update();
                case DELETE ->
                    delete();
                case GET_ALL ->
                    getAll();
                case GET_BY_ID ->
                    getById();
                default -> {
                }
            }
        }
        HibernateUtil.shutDown();
    }

    public Operation doMenu() {
        System.out.println();
        System.out.println("\tMenu:");
        for (int i = 0; i < Operation.values().length - 2; ++i) {
            System.out.print("\t" + (i + 1) + ": " + Operation.values()[i].name());
        }
        System.out.println("\t" + "0: " + Operation.QUIT.name());
        return userInput();
    }

    private void insert() throws SQLException, ClassNotFoundException {
        System.out.println("Format to enter new user:");
        System.out.println("Lastname Firstname Patronymic, email, age");
        var input = scanner.nextLine();
        var newUser = parseUser("0, " + input, true);
        userService.insert(newUser);
    }

    private void update() throws SQLException, ClassNotFoundException {
        System.out.println("Format to update exist user:");
        System.out.println("Id, Lastname Firstname Patronymic, email, age");
        var input = scanner.nextLine();
        var updatedUser = parseUser(input, false);
        userService.update(updatedUser);
    }

    private void delete() throws SQLException, ClassNotFoundException {
        System.out.println("Enter user id to delete:");
        var input = scanner.nextLine();
        var delUser = userService.getById(parseInt(input));
        userService.delete(delUser);
    }

    private void getAll() throws SQLException, ClassNotFoundException {
        var users = userService.getAll();
        for (User user : users) {
            System.out.println(user.toString());
        }
    }

    private void getById() throws SQLException, ClassNotFoundException {
        System.out.println("Enter user id:");
        var input = scanner.nextLine();
        var user = userService.getById(parseInt(input));
        System.out.println("\tUser:");
        System.out.println(user.toString());
    }

    private Operation userInput() {
        try {
            var input = Integer.parseInt(scanner.nextLine());
            var foundMode = Arrays.stream(Operation.values())
                    .filter(x -> x.getOperationCode() == input)
                    .findAny();
            return foundMode.orElse(Operation.NONE);
        } catch (NumberFormatException e) {
            return Operation.NONE;
        }
    }

    private User parseUser(String input, boolean isNewUser) throws SQLException, ClassNotFoundException {
        if (input == null) {
            return null;
        }

        var parsed = input.split(", ");
        if (parsed.length != 4 || Arrays.stream(parsed).anyMatch(x -> x == null || x.isEmpty())) {
            return null;
        }
        var name = parsed[1];
        var email = parsed[2];
        int age, id;
        try {
            id = Integer.parseInt(parsed[0]);
            age = Integer.parseInt(parsed[3]);
        } catch (NumberFormatException e) {
            return null;
        }
        var user = new User(name, email, age);
        if (isNewUser) {
            return user;
        }
        user.setId(id);
        return user;
    }

    private int parseInt(String input) throws SQLException, ClassNotFoundException {
        if (input == null) {
            return 0;
        }

        int id = 0;
        try {
            id = Integer.parseInt(input);
        } catch (NumberFormatException e) {
        }
        return id;
    }

    private void welcome() {
        System.out.println();
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("\t\t\tWelcome to USER MANAGEMENT\n");
    }

}
