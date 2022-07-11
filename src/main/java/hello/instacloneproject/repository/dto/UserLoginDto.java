package hello.instacloneproject.repository.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLoginDto {

    private String email;
    private String password;
    private String phone;
    private String name;

}
