package todolist.database.mysql;

import todolist.database.DataInterface;
import todolist.database.dataType.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

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
        token = new Token(Integer.parseInt(data.get(0).get("token_id")), Integer.parseInt(data.get(0).get("user")), data.get(0).get("jwt_value"), LocalDateTime.parse(data.get(0).get("expiration_date").replace(" ", "T")), Integer.parseInt(data.get(0).get("is_activate")) != 0);
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
        token = new Token(Integer.parseInt(data.get(0).get("token_id")), Integer.parseInt(data.get(0).get("user")), data.get(0).get("jwt_value"), LocalDateTime.parse(data.get(0).get("expiration_date").replace(" ", "T")), Integer.parseInt(data.get(0).get("is_activate")) != 0);
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

    @Override
    public User getUserFromToken(final String jwtToken) {
        List<Map<String, String>> data = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(this.databaseUrl, this.databaseUser, this.databasePassword);
            data = this.select(connection, SqlOperation.RETRIEVE_USER_FROM_TOKEN.getSqlTemplate(), Arrays.asList(jwtToken));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        if ((data.size() == 1 && data.get(0).containsKey("Error")) || data.isEmpty()) {
            return null;
        }
        return new User(Integer.parseInt(data.get(0).get("user_id")), data.get(0).get("firstname"), data.get(0).get("lastname"), data.get(0).get("email"), data.get(0).get("password"));
    }

    @Override
    public String updateUser(final String jwtToken, final User user) {
        String result = new String();

        try {
            Connection connection = DriverManager.getConnection(this.databaseUrl, this.databaseUser, this.databasePassword);
            result = this.insert(connection, SqlOperation.UPDATE_USER.getSqlTemplate(), Arrays.asList(user.firstname, user.lastname, jwtToken));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return result;
    }

    @Override
    public String deleteUser(final String jwtToken) {
        String result = new String();

        try {
            Connection connection = DriverManager.getConnection(this.databaseUrl, this.databaseUser, this.databasePassword);
            result = this.insert(connection, SqlOperation.DELETE_USER.getSqlTemplate(), Arrays.asList(jwtToken));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return result;
    }

    @Override
    public String createProject(Project project) {
        return "";
    }
    @Override
    public String updateProject(Project project) {
        return "";
    }

    @Override
    public String deleteProject(int projectId) {
        return "";
    }

    @Override
    public Project[] retrieveAllUserProjects(int userId) {
        return new Project[0];
    }

    @Override
    public Project retrieveOneUserProject(int userId, int projectId) {
        return new Project(0, "", "", LocalDateTime.now(), null);
    }

    @Override
    public String createProjectEvent(Event event) {
        return "";
    }

    @Override
    public String updateProjectEvent(Event event) {
        return "";
    }

    @Override
    public String deleteProjectEvent(int eventId) {
        return "";
    }

    @Override
    public Event retrieveOneProjectEvent(int projectId, int eventId) {
        return new Event(0, "", "", LocalDateTime.now(), LocalDateTime.now(), null, null);
    }
    @Override

    public Event[] retrieveAllProjectEvents(int projectId) {
        return new Event[0];
    }

    @Override
    public String createProjectList() {
        return "";
    }

    @Override
    public String updateProjectList() {
        return "";
    }

    @Override
    public String deleteProjectList() {
        return "";
    }

    @Override
    public todolist.database.dataType.List retrieveOneProjectList(int projectId, int listId) {
        return new todolist.database.dataType.List(0, "", "", null, null, null);
    }

    @Override
    public todolist.database.dataType.List[] allProjectLists(int projectId) {
        return new todolist.database.dataType.List[0];
    }

    @Override
    public String createListTask(Task task) {
        return "";
    }

    @Override
    public String updateListTask(Task task) {
        return "";
    }

    @Override
    public String deleteListTask(int taskId) {
        return "";
    }

    @Override
    public Task getOneListTask(int listId, int taskId) {
        return new Task(0, "", "", LocalDateTime.now(), null, null);
    }

    @Override
    public Task[] getAllListTasks(int listId) {
        return new Task[0];
    }

    @Override
    public Task[] getAllProjectTasks(int projectId) {
        return new Task[0];
    }

    @Override
    public String linkUserToProject(int userId, int projectId) {
        return "";
    }

    @Override
    public String unLinkUserToProject(int userId, int projectId) {
        return "";
    }

    @Override
    public String linkUserToEvent(int userId, int eventId) {
        return "";
    }

    @Override
    public String unLinkUserToEvent(int userId, int eventId) {
        return "";
    }

    @Override
    public String linkUserToList(int userId, int listId) {
        return "";
    }

    @Override
    public String unLinkUserToList(int userId, int listId) {
        return "";
    }

    @Override
    public String linkUserToTask(int userId, int taskId) {
        return "";
    }

    @Override
    public String unLinkUserToTask(int userId, int taskId) {
        return "";
    }


    private final String databaseUrl;
    private final String databaseUser;
    private final String databasePassword;
}
