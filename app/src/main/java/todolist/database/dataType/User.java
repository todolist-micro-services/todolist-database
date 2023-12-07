package todolist.database.dataType;

public class User {

    public User(int userId, String firstname, String lastname, String email, String password) {
        this.userId = userId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

    public int userId;
    public String firstname;
    public String lastname;
    public String email;
    public String password;
}
