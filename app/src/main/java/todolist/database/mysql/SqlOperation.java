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
    DELETE_USER("DELETE user, token FROM Token token JOIN User user ON token.user = user.user_id WHERE token.jwt_value = ?;");

    private final String sqlTemplate;

    SqlOperation(String sqlTemplate) {
        this.sqlTemplate = sqlTemplate;
    }

    public String getSqlTemplate() {
        return sqlTemplate;
    }
}
