# Datenbankverbindung im Projekt

Dieses Dokument erklärt, wie die Datenbankverbindung im Projekt funktioniert und wie Sie damit arbeiten können.

## Übersicht

Das Projekt verwendet:
- **Hibernate** als ORM-Framework (Object-Relational Mapping)
- **Microsoft SQL Server** als Datenbank
- **DAO-Klassen** (Data Access Objects) für den Zugriff auf die Datenbank

## Konfiguration

Die Datenbankverbindung ist in der Datei `src/main/resources/hibernate.cfg.xml` konfiguriert:

```xml
<property name="hibernate.connection.driver_class">com.microsoft.sqlserver.jdbc.SQLServerDriver</property>
<property name="hibernate.connection.url">
    jdbc:sqlserver://185.119.119.126:1433;databaseName=SWP_2025_Deutsch_Group2_NoeTo;integratedSecurity=false;encrypt=true;trustServerCertificate=true;
</property>
<property name="hibernate.connection.username">maria</property>
<property name="hibernate.connection.password">maria</property>
```

## Verbindung zur Datenbank herstellen

Die Verbindung zur Datenbank wird über die `HibernateUtil`-Klasse hergestellt. Diese Klasse stellt eine `SessionFactory` bereit, die für alle Datenbankoperationen verwendet werden sollte.

```java
// Verbindung zur Datenbank herstellen
SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

// Eine Session öffnen
try (Session session = sessionFactory.openSession()) {
    // Datenbankoperationen durchführen
}
```

## Verwendung der DAO-Klassen

Für den Zugriff auf die Datenbank sollten immer die DAO-Klassen verwendet werden. Diese Klassen bieten Methoden für die grundlegenden CRUD-Operationen (Create, Read, Update, Delete).

### Beispiel: Benutzer verwalten mit UserDAO

```java
// Neuen Benutzer erstellen
UserDAO.createUser("benutzername", "passwort");

// Benutzer suchen
User user = UserDAO.findByUsername("benutzername");

// Alle Benutzer auflisten
List<User> allUsers = UserDAO.findAll();

// Benutzer authentifizieren
User authenticatedUser = UserDAO.authenticateAndGetUser("benutzername", "passwort");
```

### Beispiel: Hotels verwalten mit HotelDAO

```java
// Hotel suchen
Hotel hotel = HotelDAO.findById(1L);

// Alle Hotels auflisten
List<Hotel> allHotels = HotelDAO.findAll();
```

## Beispielanwendung

Eine Beispielanwendung, die zeigt, wie man mit der Datenbank verbindet, finden Sie in der Klasse `org.example.util.DatabaseConnectionExample`. Diese Klasse können Sie ausführen, um die Datenbankverbindung zu testen.

## Fehlerbehebung

Wenn Sie Probleme mit der Datenbankverbindung haben, überprüfen Sie Folgendes:

1. **Netzwerkverbindung**: Stellen Sie sicher, dass Sie eine Verbindung zum Datenbankserver haben.
2. **Firewall**: Überprüfen Sie, ob der Port 1433 (SQL Server) nicht durch eine Firewall blockiert wird.
3. **Zugangsdaten**: Überprüfen Sie, ob Benutzername und Passwort korrekt sind.
4. **JDBC-Treiber**: Der Microsoft SQL Server JDBC-Treiber ist als Abhängigkeit im Projekt enthalten.

## Wichtiger Hinweis

Alle DAO-Klassen sollten die `HibernateUtil`-Klasse für den Zugriff auf die Datenbank verwenden, um eine konsistente Datenbankverbindung zu gewährleisten. Die Klasse `BenutzerDAO` wurde entsprechend angepasst, um diesen Standard einzuhalten.