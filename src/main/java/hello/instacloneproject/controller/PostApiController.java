package hello.instacloneproject.controller;

import hello.instacloneproject.domain.Post;
import hello.instacloneproject.domain.User;
import hello.instacloneproject.dto.Post.PostInfoDto;
import hello.instacloneproject.service.LikesService;
import hello.instacloneproject.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;
    private final LikesService likesService;

    @GetMapping("/post/{postId}")
    public PostInfoDto getPostInfo(@PathVariable long postId, @AuthenticationPrincipal User user){
        Post findPost = postService.findByIdWithLike(postId);
        boolean state = likesService.likeState(postId, user.getEmail());
        return PostInfoDto.builder()
                .id(findPost.getId())
                .postImgUrl(findPost.getPostImgFile().getStoreFileName())
                .dateTime(findPost.getDateTime())
                .tag(findPost.getTag())
                .text(findPost.getText())
                .postUploader(findPost.getUser())
                .uploader(findPost.getUser().getEmail().equals(user.getEmail()))
                .likeCount(findPost.getLikeList().size())
                .likeState(state)
                .build();
    }


    @PostMapping("/post/{postId}/likes")
    public void likes(@PathVariable long postId,@AuthenticationPrincipal User user){
        likesService.like(postId,user.getEmail());
    }

    @DeleteMapping("/post/{postId}/likes")
    public void unlikes(@PathVariable long postId,@AuthenticationPrincipal User user){
        likesService.unlike(postId,user.getEmail());
    }
}
