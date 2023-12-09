package todolist.database;


import todolist.database.dataType.Token;
import todolist.database.dataType.User;

import java.time.LocalDateTime;

public interface DataInterface {
    public String createUser(User user);
    public int getUser(String email, String password);
    public Token getUserToken(int user);
    public Token getUserTokenFromToken(String token);
    public String createUserToken(Token token);
    public String deleteUserToken(int user);
    public String updateUserToken(Token token);
}
