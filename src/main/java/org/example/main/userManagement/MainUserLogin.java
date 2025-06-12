package org.example.main.userManagement;

import org.example.dao.UserDAO;
import org.example.entity.Role;
import org.example.entity.User;

public class MainUserLogin {


    public static void main(String[] args) {

        User u = UserDAO.authenticateAndGetUser("admin", "admin");
        Role current = u.getRole();

        if (current == Role.ADMIN) {


            System.out.println("Admin");
        } else if (current == Role.USER) {
            System.out.println("User");
        } else {
            System.out.println("Unknown role");
        }
    }
}
