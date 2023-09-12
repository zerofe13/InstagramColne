package hello.siconnectproject.dto.Post;

import hello.siconnectproject.domain.UploadFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    private long id;
    private String tag;
    private String text;
    private UploadFile postImgFile;
}
