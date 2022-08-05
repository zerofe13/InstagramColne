package hello.instacloneproject.dto.Post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostUploadDto {
    @Pattern(regexp="^([^\\s#.]*)$") // 공백및 # . 사용불가
    private String tag;
    private String text;
    private MultipartFile postImgFile;

}
