package hello.instacloneproject.dto.Post;

import hello.instacloneproject.domain.Comment;
import hello.instacloneproject.domain.UploadFile;
import hello.instacloneproject.domain.User;
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
