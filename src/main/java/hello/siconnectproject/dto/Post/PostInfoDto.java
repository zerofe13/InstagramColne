package hello.siconnectproject.dto.Post;

import hello.siconnectproject.domain.Comment;
import hello.siconnectproject.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostInfoDto {

    private long id;
    private String text;
    private String tag;
    private User postUploader;
    private LocalDateTime dateTime;
    private boolean uploader;
    private String postImgUrl;
    private int likeCount;
    private boolean likeState;
    private List<Comment> commentList;
}
