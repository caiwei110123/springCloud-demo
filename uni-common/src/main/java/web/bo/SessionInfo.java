package web.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SessionInfo implements Serializable {
    private Long tenantId;
    private String userId;


}
