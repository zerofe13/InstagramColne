package hello.siconnectproject.controller;

import hello.siconnectproject.config.PrincipalDetails;
import hello.siconnectproject.domain.User;
import hello.siconnectproject.dto.Follow.FollowDto;
import hello.siconnectproject.service.FollowService;
import hello.siconnectproject.service.UserService;
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
    private final UserService userService;


    @PostMapping("/follow/{followedId}")
    public void following(@PathVariable Long followedId, @AuthenticationPrincipal PrincipalDetails principal){
        User findUser = userService.findById(followedId);
        followService.save(principal.getUsername(), findUser.getEmail());
    }

    @DeleteMapping("/follow/{followedId}")
    public void unfollowing(@PathVariable Long followedId,@AuthenticationPrincipal PrincipalDetails principal){
        User findUser = userService.findById(followedId);
        followService.unFollow(principal.getUsername(), findUser.getEmail());
    }

    /**
     * 팔로워에 대한 리스트정보를 dto를 이용하여 반환
     */
    @GetMapping("/follow/{profileEmail}/follower")
    public List<FollowDto> getFollower(@PathVariable String profileEmail,@AuthenticationPrincipal PrincipalDetails principal){
        return followService.getFollowedList(profileEmail, principal.getUsername());
    }

    @GetMapping("/follow/{profileEmail}/following")
    public List<FollowDto> getFollowing(@PathVariable String profileEmail,@AuthenticationPrincipal PrincipalDetails principal){
        return followService.getFollowingList(profileEmail,principal.getUsername());
    }

}
