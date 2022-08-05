package hello.instacloneproject.dto.user;

import hello.instacloneproject.domain.UploadFile;
import hello.instacloneproject.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDto {

    private long id;

    @Email
    @NotBlank
    private String email;
    @NotBlank
    @Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$") //문자 숫자 특수문자  8자이상
    private String password;
    @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$")
    private String phone;
    private String name;

    private MultipartFile profileImgFile;

    private String title;
    private  String website;

}
