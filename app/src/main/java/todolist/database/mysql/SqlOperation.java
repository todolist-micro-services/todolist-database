package todolist.database.mysql;

public enum SqlOperation {
    CREATE_USER("INSERT INTO User (firstname, lastname, email, password) VALUES (?, ?, ?, ?);"),
    RETRIEVE_USER("SELECT * FROM User WHERE email = ? AND password = ?;"),
    RETRIEVE_USER_TOKEN("SELECT * FROM Token WHERE user = ?;"),
    CREATE_USER_TOKEN("INSERT INTO Token (jwt_value, expiration_date, user, is_activate) VALUES (?, ?, ?, ?);"),
    DELETE_USER_TOKEN("DELETE FROM Token WHERE user = ?;"),
    UPDATE_USER_TOKEN("UPDATE Token SET jwt_value = ?, expiration_date = ?, is_activate = ? WHERE token_id = ?;");

    private final String sqlTemplate;

    SqlOperation(String sqlTemplate) {
        this.sqlTemplate = sqlTemplate;
    }

    public String getSqlTemplate() {
        return sqlTemplate;
    }
}
