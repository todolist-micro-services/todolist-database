package todolist.database.mysql;

import todolist.database.DataInterface;
import todolist.database.dataType.Token;
import todolist.database.dataType.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class Mysql implements DataInterface {

    public Mysql(String databaseUrl, String databaseUser, String databasePassword) {
        this.databaseUrl = databaseUrl;
        this.databaseUser = databaseUser;
        this.databasePassword = databasePassword;
    }

    private List<Map<String, String>> select(Connection connection, String sql, List<Object> params) {
        List<Map<String, String>> resultList = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.size(); i++) {
                preparedStatement.setObject(i + 1, params.get(i));
            }
            try (ResultSet res = preparedStatement.executeQuery()) {
                while (!res.isClosed() && res.next()) {
                    ResultSetMetaData name = res.getMetaData();
                    Map<String, String> columnValuesMap = new HashMap<>();
                    for (int i = 1; i <= name.getColumnCount(); ++i) {
                        columnValuesMap.put(name.getColumnName(i), res.getString(name.getColumnName(i)));
                    }
                    resultList.add(columnValuesMap);
                }
            } catch (SQLException e) {
                Map<String, String> errorData = new HashMap<>();
                errorData.put("Error", e.getMessage());
                resultList.clear();
                resultList.add(errorData);
                e.printStackTrace();
            }
        } catch (SQLException e) {
            Map<String, String> errorData = new HashMap<>();
            errorData.put("Error", e.getMessage());
            resultList.clear();
            resultList.add(errorData);
            e.printStackTrace();
        }
        return resultList;
    }

    private String insert(Connection connection, String sql, List<Object> params) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.size(); i++) {
                preparedStatement.setObject(i + 1, params.get(i));
            }
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return "";
    }


    @Override
    public String createUser(User user) {
        String result = new String();

        try {
            Connection connection = DriverManager.getConnection(this.databaseUrl, this.databaseUser, this.databasePassword);
            result = this.insert(connection, SqlOperation.CREATE_USER.getSqlTemplate(), Arrays.asList(user.firstname, user.lastname, user.email, user.password));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return result;
    }

    @Override
    public int getUser(String email, String password) {
        List<Map<String, String>> data = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(this.databaseUrl, this.databaseUser, this.databasePassword);
            data = this.select(connection, SqlOperation.RETRIEVE_USER.getSqlTemplate(), Arrays.asList(email, password));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return -2;
        }
        if ((data.size() == 1 && data.get(0).containsKey("Error")) || data.isEmpty()) {
            return -1;
        }
        return Integer.parseInt(data.get(0).get("user_id"));
    }

    @Override
    public Token getUserToken(int user) {
        List<Map<String, String>> data = new ArrayList<>();
        Token token = null;

        try {
            Connection connection = DriverManager.getConnection(this.databaseUrl, this.databaseUser, this.databasePassword);
            data = this.select(connection, SqlOperation.RETRIEVE_USER_TOKEN.getSqlTemplate(), Arrays.asList(user));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        if ((data.size() == 1 && data.get(0).containsKey("Error")) || data.isEmpty()) {
            return null;
        }
        token = new Token(Integer.parseInt(data.get(0).get("token_id")), Integer.parseInt(data.get(0).get("user")), data.get(0).get("jwt_value"), LocalDateTime.parse(data.get(0).get("expiration_date").replace(" ", "T")), Integer.parseInt(data.get(0).get("token_id")) != 0);
        return token;
    }

    @Override
    public Token getUserTokenFromToken(String test) {
        List<Map<String, String>> data = new ArrayList<>();
        Token token = null;

        try {
            Connection connection = DriverManager.getConnection(this.databaseUrl, this.databaseUser, this.databasePassword);
            data = this.select(connection, SqlOperation.RETRIEVE_USER_TOKEN_FROM_TOKEN.getSqlTemplate(), Arrays.asList(test));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        if ((data.size() == 1 && data.get(0).containsKey("Error")) || data.isEmpty()) {
            return null;
        }
        token = new Token(Integer.parseInt(data.get(0).get("token_id")), Integer.parseInt(data.get(0).get("user")), data.get(0).get("jwt_value"), LocalDateTime.parse(data.get(0).get("expiration_date").replace(" ", "T")), Integer.parseInt(data.get(0).get("token_id")) != 0);
        return token;
    }

    @Override
    public String createUserToken(Token token) {
        String result = new String();

        try {
            Connection connection = DriverManager.getConnection(this.databaseUrl, this.databaseUser, this.databasePassword);
            result = this.insert(connection, SqlOperation.CREATE_USER_TOKEN.getSqlTemplate(), Arrays.asList(token.jwtValue, token.expirationDate.toString(), token.user, token.isActivated));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return result;
    }

    @Override
    public String deleteUserToken(int user) {
        String result = new String();

        try {
            Connection connection = DriverManager.getConnection(this.databaseUrl, this.databaseUser, this.databasePassword);
            result = this.insert(connection, SqlOperation.DELETE_USER_TOKEN.getSqlTemplate(), Arrays.asList(user));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return result;
    }

    @Override
    public String updateUserToken(Token token) {
        String result = new String();

        try {
            Connection connection = DriverManager.getConnection(this.databaseUrl, this.databaseUser, this.databasePassword);
            result = this.insert(connection, SqlOperation.UPDATE_USER_TOKEN.getSqlTemplate(), Arrays.asList(token.jwtValue, token.expirationDate.toString(), token.isActivated, token.tokenId));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return result;
    }

    private final String databaseUrl;
    private final String databaseUser;
    private final String databasePassword;
}
