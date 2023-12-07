package todolist.database;


import todolist.database.dataType.Token;
import todolist.database.dataType.User;

import java.time.LocalDateTime;

public interface DataInterface {
    public boolean createUser(User user);
    public int getUser(String email, String password);
    public Token getUserToken(int user);
    public boolean createUserToken(LocalDateTime now, int user);
    public boolean deleteUserToken(int user);
}
