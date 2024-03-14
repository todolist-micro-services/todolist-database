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
        DataInterface dataInterface = new Mysql(System.getenv("DB_URL"), System.getenv("DB_USERNAME"), System.getenv("DB_PASSWORD"));

        System.out.println(dataInterface.linkUserToProject(21, 24));
        //System.out.println(dataInterface.retrieveAllUserProjects(21).get(0).name);

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
