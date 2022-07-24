package hello.instacloneproject.controller;

import hello.instacloneproject.domain.User;
import hello.instacloneproject.dto.Follow.FollowDto;
import hello.instacloneproject.service.FollowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/follow/{followedEmail}")
    public void following(@PathVariable String followedEmail, @AuthenticationPrincipal User user){
        followService.save(user.getEmail(),followedEmail);
    }

    @DeleteMapping("/follow/{followedEmail}")
    public void unfollowing(@PathVariable String followedEmail,@AuthenticationPrincipal User user){
        followService.unFollow(user.getEmail(), followedEmail);
    }

    /**
     * 팔로워에 대한 리스트정보를 dto를 이용하여 반환
     * @param profileEmail
     * @param user
     * @return
     */
    @GetMapping("/follow/{profileEmail}/follower")
    public List<FollowDto> getFollower(@PathVariable String profileEmail,@AuthenticationPrincipal User user){
        return followService.getFollowedList(profileEmail,user.getEmail());
    }

    @GetMapping("/follow/{profileEmail}/following")
    public List<FollowDto> getFollowing(@PathVariable String profileEmail,@AuthenticationPrincipal User user){
        return followService.getFollowingList(profileEmail,user.getEmail());
    }

}
