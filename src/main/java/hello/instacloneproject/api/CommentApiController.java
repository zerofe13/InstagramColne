package hello.instacloneproject.api;

import hello.instacloneproject.config.PrincipalDetails;
import hello.instacloneproject.domain.Comment;
import hello.instacloneproject.domain.User;
import hello.instacloneproject.dto.Comment.CommentDto;
import hello.instacloneproject.dto.Comment.CommentUploadDto;
import hello.instacloneproject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping("/comment")
    public CommentDto addComment(@RequestBody CommentUploadDto commentUploadDto, @AuthenticationPrincipal PrincipalDetails principal){
        return commentService.addComment(commentUploadDto.getPostId(), principal.getUsername(), commentUploadDto.getText());
    }

    @DeleteMapping("/comment/{id}")
    public void deleteComment(@PathVariable long id){
        commentService.deleteComment(id);
    }
}
