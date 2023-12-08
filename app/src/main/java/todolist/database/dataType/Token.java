package todolist.database.dataType;

import java.time.LocalDateTime;

public class Token {

    public Token(int tokenId, int user, String jwtValue, LocalDateTime expirationDate, Boolean isActivated) {
        this.tokenId = tokenId;
        this.user = user;
        this.jwtValue = jwtValue;
        this.expirationDate = expirationDate;
        this.isActivated = isActivated;
    }

    public int tokenId;
    public int user;
    public String jwtValue;
    public LocalDateTime expirationDate;
    public Boolean isActivated;
}
