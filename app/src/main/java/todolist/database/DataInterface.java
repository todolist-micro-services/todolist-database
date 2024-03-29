package todolist.database;


import todolist.database.dataType.*;

public interface DataInterface {
    public String createUser(User user);
    public int retrieveUser(String email, String password);
    public Token retrieveUserToken(int user);
    public Token retrieveUserTokenFromToken(String token);
    public String createUserToken(Token token);
    public String deleteUserToken(int user);
    public String updateUserToken(Token token);
    public User retrieveUserFromToken(final String jwtToken);
    public String updateUser(final String jwtToken, final User user);
    public String deleteUser(final String jwtToken);
    public String createProject(Project project);
    public String updateProject(Project project);
    public String deleteProject(int projectId);
    public java.util.List<Project> retrieveAllUserProjects(int userId);
    public Project retrieveOneUserProject(int userId, int projectId);
    public Project retrieveProjectById(int projectId);
    public Project retrieveUserProjectByName(int userId, String name);
    public List retrieveProjectListByName(int projectId, String name);
    public Task retrieveListTaskByName(int list, String name);
    public String createProjectList(List list);
    public String updateProjectList(List list);
    public String deleteProjectList(int listId);
    public List retrieveOneProjectList(int projectId, int listId);
    public java.util.List<List> retrieveAllProjectLists(int projectId);
    public String createListTask(Task task);
    public String updateListTask(Task task);
    public String deleteListTask(int taskId);
    public Task retrieveOneListTask(int listId, int taskId);
    public java.util.List<Task> retrieveAllListTasks(int listId);
    public java.util.List<Task> retrieveAllProjectTasks(int projectId);
    public String linkUserToProject(int userId, int projectId);
    public String unLinkUserToProject(int userId, int projectId);
    public String linkUserToList(int userId, int listId);
    public String unLinkUserToList(int userId, int listId);
    public String linkUserToTask(int userId, int taskId);
    public String unLinkUserToTask(int userId, int taskId);
    public java.util.List<User> retrieveAllUserLinkToTask(int taskId);
    public java.util.List<User> retrieveAllUserLinkToList(int listId);
    public java.util.List<User> retrieveAllUserLinkToProject(int projectId);
}
