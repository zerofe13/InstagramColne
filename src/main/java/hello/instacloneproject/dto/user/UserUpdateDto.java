package hello.instacloneproject.repository.dto.user;

import hello.instacloneproject.domain.User;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserUpdateDto {

    private long id;

    private String email;
    private String password;
    private String phone;
    private String name;

    private String profileImgUrl;

    private String title;
    private  String website;

}
