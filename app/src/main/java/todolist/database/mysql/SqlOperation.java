package todolist.database.mysql;

public enum SqlOperation {
    CREATE_USER("INSERT INTO User (firstname, lastname, email, password) VALUES (?, ?, ?, ?)"),
    RETRIEVE_USER("SELECT * FROM User;");

    private final String sqlTemplate;

    SqlOperation(String sqlTemplate) {
        this.sqlTemplate = sqlTemplate;
    }

    public String getSqlTemplate() {
        return sqlTemplate;
    }
}
