package web.bo;

import lombok.Data;

@Data
public class TokenResponse {
    private String token;
    private String userId;
    private String uid;
}
