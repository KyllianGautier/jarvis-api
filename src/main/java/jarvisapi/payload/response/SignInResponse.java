package jarvisapi.payload.response;

import jarvisapi.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignInResponse {
    private String token;
    private String refreshToken;
    private boolean isAdmin;
    private UserDTO user;
}
