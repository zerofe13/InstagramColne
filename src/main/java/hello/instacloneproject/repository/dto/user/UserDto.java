package hello.instacloneproject.repository.dto.user;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDto {
    private long id;

    private String email;
    private String phone;
    private String name;

    private String profileImgUrl;

    private String title;
    private  String website;
}
