/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package todolist.database;

import todolist.database.dataType.User;
import todolist.database.mysql.Mysql;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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

        System.out.println(dataInterface.createUser(new User(1, "", "", "", "")));
    }
}
