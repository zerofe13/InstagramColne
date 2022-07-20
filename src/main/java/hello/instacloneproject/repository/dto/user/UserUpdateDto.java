package hello.instacloneproject.repository.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserUpdateDto {

    private long id;
    private String password;
    private String phone;
    private String name;
    private String profileImgUrl;
    private String title;
    private String website;
}
