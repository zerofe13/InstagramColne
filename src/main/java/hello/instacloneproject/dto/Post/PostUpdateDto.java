package hello.instacloneproject.dto.Post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostUpdateDto {

    private long id;
    private String tag;
    private String text;
}
