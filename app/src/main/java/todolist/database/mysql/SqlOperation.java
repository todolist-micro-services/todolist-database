package todolist.database.mysql;

public enum SqlOperation {
    CREATE_USER("INSERT INTO User (firstname, lastname, email, password) VALUES (?, ?, ?, ?);"),
    RETRIEVE_USER("SELECT * FROM User WHERE email = ? AND password = ?;"),
    RETRIEVE_USER_TOKEN("SELECT * FROM Token WHERE user = ?;"),
    RETRIEVE_USER_TOKEN_FROM_TOKEN("SELECT * FROM Token WHERE jwt_value = ?;"),
    CREATE_USER_TOKEN("INSERT INTO Token (jwt_value, expiration_date, user, is_activate) VALUES (?, ?, ?, ?);"),
    DELETE_USER_TOKEN("DELETE FROM Token WHERE user = ?;"),
    UPDATE_USER_TOKEN("UPDATE Token SET jwt_value = ?, expiration_date = ?, is_activate = ? WHERE token_id = ?;"),
    RETRIEVE_USER_FROM_TOKEN("SELECT user.user_id, user.firstname, user.lastname, user.email, user.password FROM Token token JOIN User user ON token.user = user.user_id WHERE token.jwt_value = ?;"),
    UPDATE_USER("UPDATE Token token JOIN User user ON token.user = user.user_id SET user.firstname = ?, user.lastname = ? WHERE token.jwt_value = ?;"),
    DELETE_USER("DELETE user, token FROM Token token JOIN User user ON token.user = user.user_id WHERE token.jwt_value = ?;"),
    CREATE_PROJECT("INSERT INTO Project (name, description, creation_date, creator) VALUES (?, ?, ?, ?);"),
    UPDATE_PROJECT("UPDATE Project SET name = ?, description = ?, creation_date = ?, creator = ? WHERE project_id = ?;"),
    DELETE_PROJECT("DELETE FROM Project WHERE project_id = ?;"),
    RETRIEVE_ALL_USER_PROJECTS("SELECT project.project_id, project.name, project.description, project.creation_date, project.creator FROM UserProject userProject JOIN Project project ON project.project_id = userProject.project_id WHERE userProject.user_id = ?;"),
    RETRIEVE_ONE_USER_PROJECT("SELECT project.project_id, project.name, project.description, project.creation_date, project.creator FROM UserProject userProject JOIN Project project ON project.project_id = userProject.project_id WHERE userProject.user_id = ? AND project.project_id = ?;"),
    CREATE_PROJECT_EVENT("INSERT INTO Event (name, description, start_date, end_date, creator, project) VALUES (?, ?, ?, ?, ?, ?);"),
    UPDATE_PROJECT_EVENT("UPDATE Event SET name = ?, description = ?, start_date = ?, end_date = ?, creator = ?, project = ? WHERE event_id = ?;"),
    DELETE_PROJECT_EVENT("DELETE FROM Event WHERE event_id = ?;"),
    RETRIEVE_ONE_PROJECT_EVENT("SELECT * FROM Event WHERE event_id = ? AND project = ?;"),
    RETRIEVE_ALL_PROJECTS_EVENTS("SELECT * FROM Event WHERE project = ?;"),
    CREATE_PROJECT_LIST("INSERT INTO List (name, description, parent, creator, project) VALUES (?, ?, ?, ?, ?);"),
    UPDATE_PROJECT_LIST("UPDATE List SET name = ?, description = ?, parent = ?, creator = ?, project = ? WHERE list_id = ?;"),
    DELETE_PROJECT_LIST("DELETE FROM List WHERE list_id = ?;"),
    RETRIEVE_ONE_PROJECT_LIST("SELECT * FROM List WHERE list_id = ? AND project = ?;"),
    RETRIEVE_ALL_PROJECT_LISTS("SELECT * FROM List WHERE project = ?;"),
    CREATE_LIST_TASK("INSERT INTO Task (name, description, creation_date, creator, list) VALUES (?, ?, ?, ?, ?);"),
    UPDATE_LIST_TASK("UPDATE Task SET name = ?, description = ?, creation_date = ?, creator = ?, list = ? WHERE task_id = ?;"),
    DELETE_LIST_TASK("DELETE FROM Task WHERE task_id = ?;"),
    RETRIEVE_ONE_LIST_TASK("SELECT * FROM Task WHERE task_id = ? AND list = ?;"),
    RETRIEVE_ALL_LIST_TASKS("SELECT * FROM Task WHERE list = ?;"),
    RETRIEVE_ALL_PROJECT_TASKS("SELECT task.task_id, task.name, task.description, task.creation_date, task.creator, task.list From Task task JOIN List list ON list.list_id = task.list JOIN Project project ON project.project_id = list.project WHERE project.project_id = ?;"),
    LINK_USER_TO_PROJECT("INSERT INTO UserProject (user_id, project_id) VALUES (?, ?);"),
    UNLINK_USER_TO_PROJECT("DELETE FROM UserProject WHERE user_id = ? AND project_id = ?;"),
    LINK_USER_TO_EVENT("INSERT INTO UserEvent (user_id, event_id) VALUES (?, ?);"),
    UNLINK_USER_TO_EVENT("DELETE FROM UserEvent WHERE user_id = ? AND event_id = ?;"),
    LINK_USER_TO_LIST("INSERT INTO UserList (user_id, list_id) VALUES (?, ?);"),
    UNLINK_USER_TO_LIST("DELETE FROM UserList WHERE user_id = ? AND list_id = ?;"),
    LINK_USER_TO_TASK("INSERT INTO UserTask (user_id, task_id) VALUES (?, ?);"),
    UNLINK_USER_TO_TASK("DELETE FROM UserProject WHERE user_id = ? AND task_id = ?;"),
    RETRIEVE_ALL_USER_LINK_TO_TASK("SELECT user.user_id, user.firstname, user.lastname, user.email FROM UserTask JOIN User user ON user.user_id = UserTask.user_id WHERE project_id = ?;"),
    RETRIEVE_ALL_USER_LINK_TO_LIST("SELECT user.user_id, user.firstname, user.lastname, user.email FROM UserList JOIN User user ON user.user_id = UserList.user_id WHERE list_id = ?;"),
    RETRIEVE_ALL_USER_LINK_TO_EVENT("SELECT user.user_id, user.firstname, user.lastname, user.email FROM UserEvent JOIN User user ON user.user_id = UserEvent.user_id WHERE event_id = ?;"),
    RETRIEVE_ALL_USER_LINK_TO_PROJECT("SELECT user.user_id, user.firstname, user.lastname, user.email FROM UserProject JOIN User user ON user.user_id = UserProject.user_id WHERE project_id = ?;");

    private final String sqlTemplate;

    SqlOperation(String sqlTemplate) {
        this.sqlTemplate = sqlTemplate;
    }

    public String getSqlTemplate() {
        return sqlTemplate;
    }
}
