package hello.instacloneproject.controller;

import hello.instacloneproject.domain.Follow;
import hello.instacloneproject.domain.User;
import hello.instacloneproject.service.FollowService;
import hello.instacloneproject.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
}
