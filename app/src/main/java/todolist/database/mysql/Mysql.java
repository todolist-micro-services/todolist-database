package todolist.database.mysql;

import todolist.database.DataInterface;
import todolist.database.dataType.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.*;

public class Mysql implements DataInterface {

    public Mysql(String databaseUrl, String databaseUser, String databasePassword) {
        this.databaseUrl = databaseUrl;
        this.databaseUser = databaseUser;
        this.databasePassword = databasePassword;
    }

    private List<Map<String, String>> retrieveData(Connection connection, String sql, List<Object> params) {
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

    private String updateData(Connection connection, String sql, List<Object> params) {
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
            result = this.updateData(connection, SqlOperation.CREATE_USER.getSqlTemplate(), Arrays.asList(user.firstname, user.lastname, user.email, user.password));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return result;
    }

    @Override
    public int retrieveUser(String email, String password) {
        List<Map<String, String>> data = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(this.databaseUrl, this.databaseUser, this.databasePassword);
            data =  this.retrieveData(connection, SqlOperation.RETRIEVE_USER.getSqlTemplate(), Arrays.asList(email, password));
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
    public Token retrieveUserToken(int user) {
        List<Map<String, String>> data = new ArrayList<>();
        Token token = null;

        try {
            Connection connection = DriverManager.getConnection(this.databaseUrl, this.databaseUser, this.databasePassword);
            data =  this.retrieveData(connection, SqlOperation.RETRIEVE_USER_TOKEN.getSqlTemplate(), Arrays.asList(user));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        if ((data.size() == 1 && data.get(0).containsKey("Error")) || data.isEmpty()) {
            return null;
        }
        token = new Token(data.getFirst());
        return token;
    }

    @Override
    public Token retrieveUserTokenFromToken(String test) {
        List<Map<String, String>> data = new ArrayList<>();
        Token token = null;

        try {
            Connection connection = DriverManager.getConnection(this.databaseUrl, this.databaseUser, this.databasePassword);
            data =  this.retrieveData(connection, SqlOperation.RETRIEVE_USER_TOKEN_FROM_TOKEN.getSqlTemplate(), Arrays.asList(test));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        if ((data.size() == 1 && data.get(0).containsKey("Error")) || data.isEmpty()) {
            return null;
        }
        token = new Token(data.getFirst());
        return token;
    }

    @Override
    public String createUserToken(Token token) {
        String result = new String();

        try {
            Connection connection = DriverManager.getConnection(this.databaseUrl, this.databaseUser, this.databasePassword);
            result = this.updateData(connection, SqlOperation.CREATE_USER_TOKEN.getSqlTemplate(), Arrays.asList(token.jwtValue, token.expirationDate.toString(), token.user, token.isActivated));
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
            result = this.updateData(connection, SqlOperation.DELETE_USER_TOKEN.getSqlTemplate(), Arrays.asList(user));
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
            result = this.updateData(connection, SqlOperation.UPDATE_USER_TOKEN.getSqlTemplate(), Arrays.asList(token.jwtValue, token.expirationDate.toString(), token.isActivated, token.tokenId));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return result;
    }

    @Override
    public User retrieveUserFromToken(final String jwtToken) {
        List<Map<String, String>> data = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(this.databaseUrl, this.databaseUser, this.databasePassword);
            data =  this.retrieveData(connection, SqlOperation.RETRIEVE_USER_FROM_TOKEN.getSqlTemplate(), Arrays.asList(jwtToken));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        if ((data.size() == 1 && data.get(0).containsKey("Error")) || data.isEmpty()) {
            return null;
        }
        return new User(data.getFirst());
    }

    @Override
    public String updateUser(final String jwtToken, final User user) {
        String result = new String();

        try {
            Connection connection = DriverManager.getConnection(this.databaseUrl, this.databaseUser, this.databasePassword);
            result = this.updateData(connection, SqlOperation.UPDATE_USER.getSqlTemplate(), Arrays.asList(user.firstname, user.lastname, jwtToken));
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
            result = this.updateData(connection, SqlOperation.DELETE_USER.getSqlTemplate(), Arrays.asList(jwtToken));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return result;
    }

    @Override
    public String createProject(Project project) {
        String result;

        try {
            Connection connection = DriverManager.getConnection(this.databaseUrl, this.databaseUser, this.databasePassword);
            result = this.updateData(connection, SqlOperation.CREATE_PROJECT.getSqlTemplate(), Arrays.asList(project.name, project.description, project.creationDate.toString(), project.creator.userId));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return result;
    }
    @Override
    public String updateProject(Project project) {
        String result;

        try {
            Connection connection = DriverManager.getConnection(this.databaseUrl, this.databaseUser, this.databasePassword);
            result = this.updateData(connection, SqlOperation.UPDATE_PROJECT.getSqlTemplate(), Arrays.asList(project.name, project.description, project.creationDate.toString(), project.creator.userId, project.projectId));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return result;
    }

    @Override
    public String deleteProject(int projectId) {
        String result;

        try {
            Connection connection = DriverManager.getConnection(this.databaseUrl, this.databaseUser, this.databasePassword);
            result = this.updateData(connection, SqlOperation.DELETE_PROJECT.getSqlTemplate(), Arrays.asList(projectId));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return result;
    }

    @Override
    public List<Project> retrieveAllUserProjects(int userId) {
        List<Map<String, String>> data = new ArrayList<>();
        List<Project> projectList = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(this.databaseUrl, this.databaseUser, this.databasePassword);
            data =  this.retrieveData(connection, SqlOperation.RETRIEVE_ALL_USER_PROJECTS.getSqlTemplate(), Arrays.asList(userId));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        if ((data.size() == 1 && data.getFirst().containsKey("Error")) || data.isEmpty()) {
            return null;
        }
        for (int i = 0; i != data.size(); ++i)
            projectList.add(new Project(data.get(i)));
        return projectList;
    }

    @Override
    public Project retrieveOneUserProject(int userId, int projectId) {
        List<Map<String, String>> data = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(this.databaseUrl, this.databaseUser, this.databasePassword);
            data =  this.retrieveData(connection, SqlOperation.RETRIEVE_ONE_USER_PROJECT.getSqlTemplate(), Arrays.asList(userId, projectId));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        if ((data.size() == 1 && data.getFirst().containsKey("Error")) || data.isEmpty()) {
            return null;
        }
        return new Project(data.getFirst());
    }

    @Override
    public Project retrieveProjectById(int projectId) {
        List<Map<String, String>> data = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(this.databaseUrl, this.databaseUser, this.databasePassword);
            data =  this.retrieveData(connection, SqlOperation.RETRIEVE_PROJECT_BY_ID.getSqlTemplate(), Arrays.asList(projectId));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        if ((data.size() == 1 && data.getFirst().containsKey("Error")) || data.isEmpty()) {
            return null;
        }
        return new Project(data.getFirst());
    }

    @Override
    public Project retrieveUserProjectByName(int userId, String name) {
        List<Map<String, String>> data = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(this.databaseUrl, this.databaseUser, this.databasePassword);
            data =  this.retrieveData(connection, SqlOperation.RETRIEVE_USER_PROJECT_BY_NAME.getSqlTemplate(), Arrays.asList(userId, name));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        if ((data.size() == 1 && data.getFirst().containsKey("Error")) || data.isEmpty()) {
            return null;
        }
        return new Project(data.getFirst());
    }

    @Override
    public String createProjectEvent(Event event) {
        String result;

        try {
            Connection connection = DriverManager.getConnection(this.databaseUrl, this.databaseUser, this.databasePassword);
            result = this.updateData(connection, SqlOperation.CREATE_PROJECT_EVENT.getSqlTemplate(), Arrays.asList(event.name, event.description, event.startDateTime.toString(), event.endDateTime.toString(), event.creator));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return result;
    }

    @Override
    public String updateProjectEvent(Event event) {
        String result;

        try {
            Connection connection = DriverManager.getConnection(this.databaseUrl, this.databaseUser, this.databasePassword);
            result = this.updateData(connection, SqlOperation.UPDATE_PROJECT_EVENT.getSqlTemplate(), Arrays.asList(event.name, event.description, event.startDateTime.toString(), event.endDateTime.toString(), event.creator, event.eventId));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return result;
    }

    @Override
    public String deleteProjectEvent(int eventId) {
        String result;

        try {
            Connection connection = DriverManager.getConnection(this.databaseUrl, this.databaseUser, this.databasePassword);
            result = this.updateData(connection, SqlOperation.CREATE_USER.getSqlTemplate(), Arrays.asList(eventId));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return result;
    }

    @Override
    public Event retrieveOneProjectEvent(int projectId, int eventId) {
        List<Map<String, String>> data = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(this.databaseUrl, this.databaseUser, this.databasePassword);
            data =  this.retrieveData(connection, SqlOperation.RETRIEVE_ONE_PROJECT_EVENT.getSqlTemplate(), Arrays.asList(eventId, projectId));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        if ((data.size() == 1 && data.getFirst().containsKey("Error")) || data.isEmpty()) {
            return null;
        }
        return new Event(data.getFirst());
    }
    @Override

    public List<Event> retrieveAllProjectEvents(int projectId) {
        List<Map<String, String>> data = new ArrayList<>();
        List<Event> events = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(this.databaseUrl, this.databaseUser, this.databasePassword);
            data =  this.retrieveData(connection, SqlOperation.RETRIEVE_ALL_PROJECTS_EVENTS.getSqlTemplate(), Arrays.asList(projectId));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        if ((data.size() == 1 && data.getFirst().containsKey("Error")) || data.isEmpty()) {
            return null;
        }
        for (int i = 0; i != data.size(); ++i)
            events.add(new Event(data.get(i)));
        return events;
    }

    @Override
    public String createProjectList(todolist.database.dataType.List list) {
        String result;

        try {
            Connection connection = DriverManager.getConnection(this.databaseUrl, this.databaseUser, this.databasePassword);
            result = this.updateData(connection, SqlOperation.CREATE_PROJECT_LIST.getSqlTemplate(), Arrays.asList(list.name, list.description, list.parent, list.creator, list.project));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return result;
    }

    @Override
    public String updateProjectList(todolist.database.dataType.List list) {
        String result;

        try {
            Connection connection = DriverManager.getConnection(this.databaseUrl, this.databaseUser, this.databasePassword);
            result = this.updateData(connection, SqlOperation.UPDATE_PROJECT_LIST.getSqlTemplate(), Arrays.asList(list.name, list.description, list.parent, list.creator, list.project, list.listId));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return result;
    }

    @Override
    public String deleteProjectList(int listId) {
        String result;

        try {
            Connection connection = DriverManager.getConnection(this.databaseUrl, this.databaseUser, this.databasePassword);
            result = this.updateData(connection, SqlOperation.DELETE_PROJECT_LIST.getSqlTemplate(), Arrays.asList(listId));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return result;
    }

    @Override
    public todolist.database.dataType.List retrieveOneProjectList(int projectId, int listId) {
        List<Map<String, String>> data = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(this.databaseUrl, this.databaseUser, this.databasePassword);
            data =  this.retrieveData(connection, SqlOperation.RETRIEVE_ONE_PROJECT_LIST.getSqlTemplate(), Arrays.asList(listId, projectId));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        if ((data.size() == 1 && data.getFirst().containsKey("Error")) || data.isEmpty()) {
            return null;
        }
        return new todolist.database.dataType.List(data.getFirst());
    }

    @Override
    public List<todolist.database.dataType.List> allProjectLists(int projectId) {
        List<Map<String, String>> data = new ArrayList<>();
        List<todolist.database.dataType.List> lists = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(this.databaseUrl, this.databaseUser, this.databasePassword);
            data =  this.retrieveData(connection, SqlOperation.RETRIEVE_ALL_PROJECT_LISTS.getSqlTemplate(), Arrays.asList(projectId));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        if ((data.size() == 1 && data.getFirst().containsKey("Error")) || data.isEmpty()) {
            return null;
        }
        for (int i = 0; i != data.size(); ++i)
            lists.add(new todolist.database.dataType.List(data.get(i)));
        return lists;
    }

    @Override
    public String createListTask(Task task) {
        String result;

        try {
            Connection connection = DriverManager.getConnection(this.databaseUrl, this.databaseUser, this.databasePassword);
            result = this.updateData(connection, SqlOperation.CREATE_LIST_TASK.getSqlTemplate(), Arrays.asList(task.name, task.description, task.creationDateTime.toString(), task.list, task.creator));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return result;
    }

    @Override
    public String updateListTask(Task task) {
        String result;

        try {
            Connection connection = DriverManager.getConnection(this.databaseUrl, this.databaseUser, this.databasePassword);
            result = this.updateData(connection, SqlOperation.UPDATE_LIST_TASK.getSqlTemplate(), Arrays.asList(task.name, task.description, task.creationDateTime.toString(), task.list, task.creator, task.taskId));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return result;
    }

    @Override
    public String deleteListTask(int taskId) {
        String result;

        try {
            Connection connection = DriverManager.getConnection(this.databaseUrl, this.databaseUser, this.databasePassword);
            result = this.updateData(connection, SqlOperation.DELETE_LIST_TASK.getSqlTemplate(), Arrays.asList(taskId));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return result;
    }

    @Override
    public Task retrieveOneListTask(int listId, int taskId) {
        List<Map<String, String>> data = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(this.databaseUrl, this.databaseUser, this.databasePassword);
            data =  this.retrieveData(connection, SqlOperation.RETRIEVE_ONE_LIST_TASK.getSqlTemplate(), Arrays.asList(taskId, listId));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        if ((data.size() == 1 && data.getFirst().containsKey("Error")) || data.isEmpty()) {
            return null;
        }
        return new Task(data.getFirst());
    }

    @Override
    public List<Task> retrieveAllListTasks(int listId) {
        List<Map<String, String>> data = new ArrayList<>();
        List<Task> tasks = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(this.databaseUrl, this.databaseUser, this.databasePassword);
            data =  this.retrieveData(connection, SqlOperation.RETRIEVE_ALL_LIST_TASKS.getSqlTemplate(), Arrays.asList(listId));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        if ((data.size() == 1 && data.getFirst().containsKey("Error")) || data.isEmpty()) {
            return null;
        }
        for (int i = 0; i != data.size(); ++i)
            tasks.add(new Task(data.get(i)));
        return tasks;
    }

    @Override
    public List<Task> retrieveAllProjectTasks(int projectId) {
        List<Map<String, String>> data = new ArrayList<>();
        List<Task> tasks = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(this.databaseUrl, this.databaseUser, this.databasePassword);
            data = this.retrieveData(connection, SqlOperation.RETRIEVE_ALL_PROJECT_TASKS.getSqlTemplate(), Arrays.asList(projectId));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        if ((data.size() == 1 && data.getFirst().containsKey("Error")) || data.isEmpty()) {
            return null;
        }
        for (int i = 0; i != data.size(); ++i)
            tasks.add(new Task(data.get(i)));
        return tasks;
    }

    @Override
    public String linkUserToProject(int userId, int projectId) {
        String result;

        try {
            Connection connection = DriverManager.getConnection(this.databaseUrl, this.databaseUser, this.databasePassword);
            result = this.updateData(connection, SqlOperation.LINK_USER_TO_PROJECT.getSqlTemplate(), Arrays.asList(userId, projectId));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return result;
    }

    @Override
    public String unLinkUserToProject(int userId, int projectId) {
        String result;

        try {
            Connection connection = DriverManager.getConnection(this.databaseUrl, this.databaseUser, this.databasePassword);
            result = this.updateData(connection, SqlOperation.UNLINK_USER_TO_PROJECT.getSqlTemplate(), Arrays.asList(userId, projectId));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return result;
    }

    @Override
    public String linkUserToEvent(int userId, int eventId) {
        String result;

        try {
            Connection connection = DriverManager.getConnection(this.databaseUrl, this.databaseUser, this.databasePassword);
            result = this.updateData(connection, SqlOperation.LINK_USER_TO_EVENT.getSqlTemplate(), Arrays.asList(userId, eventId));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return result;
    }

    @Override
    public String unLinkUserToEvent(int userId, int eventId) {
        String result;

        try {
            Connection connection = DriverManager.getConnection(this.databaseUrl, this.databaseUser, this.databasePassword);
            result = this.updateData(connection, SqlOperation.UNLINK_USER_TO_EVENT.getSqlTemplate(), Arrays.asList(userId, eventId));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return result;
    }

    @Override
    public String linkUserToList(int userId, int listId) {
        String result;

        try {
            Connection connection = DriverManager.getConnection(this.databaseUrl, this.databaseUser, this.databasePassword);
            result = this.updateData(connection, SqlOperation.LINK_USER_TO_LIST.getSqlTemplate(), Arrays.asList(userId, listId));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return result;
    }

    @Override
    public String unLinkUserToList(int userId, int listId) {
        String result;

        try {
            Connection connection = DriverManager.getConnection(this.databaseUrl, this.databaseUser, this.databasePassword);
            result = this.updateData(connection, SqlOperation.UNLINK_USER_TO_LIST.getSqlTemplate(), Arrays.asList(userId, listId));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return result;
    }

    @Override
    public String linkUserToTask(int userId, int taskId) {
        String result;

        try {
            Connection connection = DriverManager.getConnection(this.databaseUrl, this.databaseUser, this.databasePassword);
            result = this.updateData(connection, SqlOperation.LINK_USER_TO_TASK.getSqlTemplate(), Arrays.asList(userId, taskId));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return result;
    }

    @Override
    public String unLinkUserToTask(int userId, int taskId) {
        String result;

        try {
            Connection connection = DriverManager.getConnection(this.databaseUrl, this.databaseUser, this.databasePassword);
            result = this.updateData(connection, SqlOperation.UNLINK_USER_TO_TASK.getSqlTemplate(), Arrays.asList(userId, taskId));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return result;
    }

    @Override
    public List<User> retrieveAllUserLinkToTask(int taskId) {
        List<Map<String, String>> data = new ArrayList<>();
        List<User> users = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(this.databaseUrl, this.databaseUser, this.databasePassword);
            data = this.retrieveData(connection, SqlOperation.RETRIEVE_ALL_USER_LINK_TO_TASK.getSqlTemplate(), Arrays.asList(taskId));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        if ((data.size() == 1 && data.getFirst().containsKey("Error")) || data.isEmpty()) {
            return null;
        }
        for (int i = 0; i != data.size(); ++i)
            users.add(new User(data.get(i)));
        return users;
    }

    @Override
    public List<User> retrieveAllUserLinkToList(int listId) {
        List<Map<String, String>> data = new ArrayList<>();
        List<User> users = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(this.databaseUrl, this.databaseUser, this.databasePassword);
            data = this.retrieveData(connection, SqlOperation.RETRIEVE_ALL_USER_LINK_TO_LIST.getSqlTemplate(), Arrays.asList(listId));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        if ((data.size() == 1 && data.getFirst().containsKey("Error")) || data.isEmpty()) {
            return null;
        }
        for (int i = 0; i != data.size(); ++i)
            users.add(new User(data.get(i)));
        return users;
    }

    @Override
    public List<User> retrieveAllUserLinkToEvent(int eventId) {
        List<Map<String, String>> data = new ArrayList<>();
        List<User> users = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(this.databaseUrl, this.databaseUser, this.databasePassword);
            data = this.retrieveData(connection, SqlOperation.RETRIEVE_ALL_USER_LINK_TO_EVENT.getSqlTemplate(), Arrays.asList(eventId));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        if ((data.size() == 1 && data.getFirst().containsKey("Error")) || data.isEmpty()) {
            return null;
        }
        for (int i = 0; i != data.size(); ++i)
            users.add(new User(data.get(i)));
        return users;
    }

    @Override
    public List<User> retrieveAllUserLinkToProject(int projectId) {
        List<Map<String, String>> data = new ArrayList<>();
        List<User> users = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(this.databaseUrl, this.databaseUser, this.databasePassword);
            data = this.retrieveData(connection, SqlOperation.RETRIEVE_ALL_USER_LINK_TO_PROJECT.getSqlTemplate(), Arrays.asList(projectId));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        if ((data.size() == 1 && data.getFirst().containsKey("Error")) || data.isEmpty()) {
            return null;
        }
        for (int i = 0; i != data.size(); ++i)
            users.add(new User(data.get(i)));
        return users;
    }

    private final String databaseUrl;
    private final String databaseUser;
    private final String databasePassword;
}
