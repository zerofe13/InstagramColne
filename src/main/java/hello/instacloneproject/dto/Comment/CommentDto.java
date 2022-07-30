package hello.instacloneproject.dto.Comment;

import hello.instacloneproject.domain.Post;
import hello.instacloneproject.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private long id;
    private User user;
    private String text;
}
