package org.example.main.userManagement;


import org.example.dao.UserDAO;
import org.example.entity.Role;
import org.example.entity.User;

public class mainUserCreate {
    public static void main(String[] args) {
        // === Admin-Benutzer erstellen ===
        User admin = UserDAO.findByUsername("admin");
        if (admin == null) {
            admin = new User("admin", "admin", Role.ADMIN);
            UserDAO.saveUser(admin);
            System.out.println("Admin user created.");
        } else {
            System.out.println("Admin user already exists.");
        }

        // === Normaler Benutzer ===
        User user = UserDAO.findByUsername("normalUser");
        if (user == null) {
            user = new User("normalUser", "1234", Role.USER);
            UserDAO.saveUser(user);
            System.out.println("Normal user created.");
        } else {
            System.out.println("Normal user already exists.");
        }
    }
}
