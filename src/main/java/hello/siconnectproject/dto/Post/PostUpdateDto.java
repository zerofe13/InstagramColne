package hello.siconnectproject.dto.Post;

import hello.siconnectproject.domain.UploadFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostUpdateDto {

    private long id;
    @Pattern(regexp="^([^\\s#.]*)$") // 공백및 # . 사용불가
    private String tag;
    private String text;
    private UploadFile postImgFile;
}
