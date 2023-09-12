package hello.siconnectproject.api;

import hello.siconnectproject.config.PrincipalDetails;
import hello.siconnectproject.dto.Comment.CommentDto;
import hello.siconnectproject.dto.Comment.CommentUploadDto;
import hello.siconnectproject.service.CommentService;
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
