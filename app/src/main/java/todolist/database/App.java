package todolist.database;

import todolist.database.dataType.List;
import todolist.database.dataType.Project;
import todolist.database.dataType.User;
import todolist.database.mysql.Mysql;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;

public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader("/Users/pad/delivery/todoList/todolist-database/.env"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (System.getProperties().containsKey(parts[0])) {
                    System.getProperties().setProperty(parts[0], parts[1]);
                } else {
                    System.setProperty(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        DataInterface dataInterface = new Mysql(System.getProperty("DATABASE_URL"), System.getProperty("DATABASE_USER"), System.getProperty("DATABASE_PASSWORD"));

        System.out.println(dataInterface.retrieveListTaskByName(2, "Test"));

//        System.out.println(dataInterface.getUserToken(16).isActivated);
//        System.out.println(dataInterface.getUserToken(18).isActivated);
        // System.out.println(dataInterface.createUser(new User(1, "", "", "", "")));
        // System.out.println(dataInterface.getUser("newemail@example.com", "newpassword"));
        // System.out.println(dataInterface.createUserToken(new Token(0, 1, "jwtValue", LocalDateTime.now(), false)));
//        System.out.println(dataInterface.getUserToken(1).isActivated);
        // System.out.println(dataInterface.deleteUserToken(1));
        // System.out.println(dataInterface.updateUserToken(new Token(1, 1, "update from java", LocalDateTime.now(), false)));
    }
}
