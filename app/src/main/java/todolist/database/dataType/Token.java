package todolist.database.dataType;

import java.time.LocalDateTime;
import java.util.Map;

public class Token {

    public Token(int tokenId, int user, String jwtValue, LocalDateTime expirationDate, Boolean isActivated) {
        this.tokenId = tokenId;
        this.user = user;
        this.jwtValue = jwtValue;
        this.expirationDate = expirationDate;
        this.isActivated = isActivated;
    }

    public Token(Map<String, String> data) {
        this.tokenId = Integer.parseInt(data.get("token_id"));
        this.user = Integer.parseInt(data.get("user"));
        this.jwtValue = data.get("jwt_value");
        this.expirationDate = LocalDateTime.parse(data.get("expiration_date").replace(" ", "T"));
        this.isActivated = Integer.parseInt(data.get("is_activate")) != 0;
    }

    public int tokenId;
    public int user;
    public String jwtValue;
    public LocalDateTime expirationDate;
    public Boolean isActivated;
}
