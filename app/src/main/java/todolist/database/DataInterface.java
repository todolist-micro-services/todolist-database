package todolist.database;


import todolist.database.dataType.*;

import java.time.LocalDateTime;

public interface DataInterface {
    public String createUser(User user);
    public int getUser(String email, String password);
    public Token getUserToken(int user);
    public Token getUserTokenFromToken(String token);
    public String createUserToken(Token token);
    public String deleteUserToken(int user);
    public String updateUserToken(Token token);
    public User getUserFromToken(final String jwtToken);
    public String updateUser(final String jwtToken, final User user);
    public String deleteUser(final String jwtToken);
    public String createProject(Project project);
    public String updateProject(Project project);
    public String deleteProject(int projectId);
    public Project[] retrieveAllUserProjects(int userId);
    public Project retrieveOneUserProject(int userId, int projectId);
    public String createProjectEvent(Event event);
    public String updateProjectEvent(Event event);
    public String deleteProjectEvent(int eventId);
    public Event retrieveOneProjectEvent(int projectId, int eventId);
    public Event[] retrieveAllProjectEvents(int projectId);
    public String createProjectList();
    public String updateProjectList();
    public String deleteProjectList();
    public List retrieveOneProjectList(int projectId, int listId);
    public List[] allProjectLists(int projectId);
    public String createListTask(Task task);
    public String updateListTask(Task task);
    public String deleteListTask(int taskId);
    public Task getOneListTask(int listId, int taskId);
    public Task[] getAllListTasks(int listId);
    public Task[] getAllProjectTasks(int projectId);
    public String linkUserToProject(int userId, int projectId);
    public String unLinkUserToProject(int userId, int projectId);
    public String linkUserToEvent(int userId, int eventId);
    public String unLinkUserToEvent(int userId, int eventId);
    public String linkUserToList(int userId, int listId);
    public String unLinkUserToList(int userId, int listId);
    public String linkUserToTask(int userId, int taskId);
    public String unLinkUserToTask(int userId, int taskId);
}
