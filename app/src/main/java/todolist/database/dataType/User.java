package todolist.database.dataType;

import java.util.Map;

public class User {

    public User(int userId, String firstname, String lastname, String email, String password) {
        this.userId = userId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

    public User(Map<String, String> data) {
        this.userId = Integer.parseInt(data.get("user_id"));
        this.firstname = data.get("firstname");
        this.lastname = data.get("lastname");
        this.email = data.get("email");
        this.password = "";
    }

    public int userId;
    public String firstname;
    public String lastname;
    public String email;
    public String password;
}
