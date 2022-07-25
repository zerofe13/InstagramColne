package hello.instacloneproject.dto.user;

import hello.instacloneproject.domain.UploadFile;
import hello.instacloneproject.domain.User;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Data
public class UserUpdateDto {

    private long id;

    private String email;
    private String password;
    private String phone;
    private String name;

    private MultipartFile profileImgFile;

    private String title;
    private  String website;

}
