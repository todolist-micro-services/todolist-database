package todolist.database.mysql;

import todolist.database.DataInterface;
import todolist.database.dataType.Token;
import todolist.database.dataType.User;

import java.time.LocalDateTime;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Mysql implements DataInterface {

    public Mysql(String databaseUrl, String databaseUser, String databasePassword) {
        this.databaseUrl = databaseUrl;
        this.databaseUser = databaseUser;
        this.databasePassword = databasePassword;
    }

    @Override
    public boolean createUser(User test) {
        System.out.println("Create User");
        try {
            // Establish a connection
            Connection connection = DriverManager.getConnection(this.databaseUrl, this.databaseUser, this.databasePassword);

            // Perform database operations here

            // Close the connection when done
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int getUser(String email, String password) {
        return 0;
    }

    @Override
    public Token getUserToken(int user) {
        return null;
    }

    @Override
    public boolean createUserToken(LocalDateTime now, int user) {
        return false;
    }

    @Override
    public boolean deleteUserToken(int user) {
        return false;
    }

    private final String databaseUrl;
    private final String databaseUser;
    private final String databasePassword;
}
