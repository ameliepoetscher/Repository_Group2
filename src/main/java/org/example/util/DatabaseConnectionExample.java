package org.example.util;

import org.example.entity.User;
import org.example.entity.Role;
import org.example.dao.UserDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.List;

/**
 * Diese Klasse zeigt ein einfaches Beispiel, wie man mit der Datenbank verbindet
 * und grundlegende Operationen durchführt.
 */
public class DatabaseConnectionExample {

    public static void main(String[] args) {
        System.out.println("Datenbankverbindung Beispiel");
        System.out.println("===========================");

        // 1. Verbindung zur Datenbank testen
        try {
            // Verbindung zur Datenbank herstellen über HibernateUtil
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

            // Eine Session öffnen um zu testen, ob die Verbindung funktioniert
            try (Session session = sessionFactory.openSession()) {
                System.out.println("✓ Verbindung zur Datenbank hergestellt!");
                System.out.println("  Datenbank: " + session.getSessionFactory().getProperties().get("hibernate.connection.url"));
            }

            // 2. Beispiel für Datenbankoperationen mit DAO-Klassen
            System.out.println("\nBeispiel für Datenbankoperationen:");
            System.out.println("--------------------------------");

            // Alle Benutzer auflisten
            List<User> allUsers = UserDAO.findAll();
            System.out.println("Benutzer in der Datenbank: " + allUsers.size());
            for (User user : allUsers) {
                System.out.println("- " + user.getUsername() + " (Rolle: " + user.getRole() + ")");
            }

            System.out.println("\nHinweis: In der Praxis sollten immer die DAO-Klassen verwendet werden,");
            System.out.println("um mit der Datenbank zu interagieren (siehe daoExample() Methode).");

        } catch (Exception e) {
            System.err.println("❌ Fehler bei der Datenbankverbindung: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * In der Praxis sollte man die DAO-Klassen verwenden, um mit der Datenbank zu interagieren.
     * Hier ist ein Beispiel, wie man die UserDAO verwenden würde:
     */
    public static void daoExample() {
        // Benutzer über UserDAO erstellen
        // org.example.dao.UserDAO.createUser("beispiel_benutzer", "sicheres_passwort");

        // Benutzer über UserDAO suchen
        // User user = org.example.dao.UserDAO.findByUsername("beispiel_benutzer");

        // Alle Benutzer auflisten
        // List<User> allUsers = org.example.dao.UserDAO.findAll();
    }
}
