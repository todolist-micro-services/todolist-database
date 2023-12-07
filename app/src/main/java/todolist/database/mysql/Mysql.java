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
    public String createUser(User test) {
        List<Map<String, String>> data = new ArrayList<>();
        String result = new String();
        try {
            Connection connection = DriverManager.getConnection(this.databaseUrl, this.databaseUser, this.databasePassword);
            // data = this.select(connection, SqlOperation.RETRIEVE_USER.getSqlTemplate(), Arrays.asList());
            result = this.insert(connection, SqlOperation.CREATE_USER.getSqlTemplate(), Arrays.asList("P", "P", "P2", "P"));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        if (data.size() == 1 && data.get(0).containsKey("Error")) {
            return data.get(0).get("Error");
        }
        System.out.println(data);
        return result;
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
    public String createUserToken(LocalDateTime now, int user) {
        return "";
    }

    @Override
    public String deleteUserToken(int user) {
        return "";
    }

    private final String databaseUrl;
    private final String databaseUser;
    private final String databasePassword;
}
