package hello.instacloneproject.api;

import hello.instacloneproject.config.PrincipalDetails;
import hello.instacloneproject.domain.Comment;
import hello.instacloneproject.domain.Post;
import hello.instacloneproject.domain.User;
import hello.instacloneproject.dto.Post.PopularPostDto;
import hello.instacloneproject.dto.Post.PostDto;
import hello.instacloneproject.dto.Post.PostInfoDto;
import hello.instacloneproject.service.CommentService;
import hello.instacloneproject.service.LikesService;
import hello.instacloneproject.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;
    private final LikesService likesService;
    private final CommentService commentService;

    @GetMapping("/post/{postId}")
    public PostInfoDto getPostInfo(@PathVariable long postId, @AuthenticationPrincipal PrincipalDetails principal){
        Post findPost = postService.findByIdWithLike(postId);
        boolean state = likesService.likeState(postId, principal.getUsername());
        List<Comment> commentList = commentService.getPostComments(postId);
        return PostInfoDto.builder()
                .id(findPost.getId())
                .postImgUrl(findPost.getPostImgFile().getStoreFileName())
                .dateTime(findPost.getDateTime())
                .tag(findPost.getTag())
                .text(findPost.getText())
                .postUploader(findPost.getUser())
                .uploader(findPost.getUser().getEmail().equals(principal.getUsername()))
                .likeCount(findPost.getLikeList().size())
                .likeState(state)
                .commentList(commentList)
                .build();
    }


    @PostMapping("/post/{postId}/likes")
    public void likes(@PathVariable long postId,@AuthenticationPrincipal PrincipalDetails principal){
        likesService.like(postId, principal.getUsername());
    }

    @DeleteMapping("/post/{postId}/likes")
    public void unlikes(@PathVariable long postId,@AuthenticationPrincipal PrincipalDetails principal){
        likesService.unlike(postId,principal.getUsername());
    }

    @GetMapping("/post")
    public Page<PostInfoDto> Story(@AuthenticationPrincipal PrincipalDetails principal, @PageableDefault(size=3)Pageable pageable){
        return postService.getStory(principal.getUsername(),pageable);
    }

    @GetMapping("/post/search")
    public Page<PostInfoDto> searchTag(@RequestParam String tag, @AuthenticationPrincipal PrincipalDetails principal,@PageableDefault(size=3) Pageable pageable){
        return postService.getTagSearch(tag, principal.getUsername(), pageable);
    }

    @GetMapping("/post/likes")
    public Page<PostDto> getLikesPost(@AuthenticationPrincipal PrincipalDetails principal,@PageableDefault(size=12) Pageable pageable){
        return postService.getLikePost(principal.getUsername(), pageable);
    }

    @GetMapping("/post/popular")
    public List<PopularPostDto> getPopularPost(){
        return postService.getPopularPosts();
    }

}
