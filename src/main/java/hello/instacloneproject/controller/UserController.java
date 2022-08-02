package hello.instacloneproject.controller;

import hello.instacloneproject.config.PrincipalDetails;
import hello.instacloneproject.domain.User;
import hello.instacloneproject.dto.user.UserProfileDto;
import hello.instacloneproject.dto.user.UserSignupDto;
import hello.instacloneproject.dto.user.UserUpdateDto;
import hello.instacloneproject.file.FileStore;
import hello.instacloneproject.service.FollowService;
import hello.instacloneproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.MalformedURLException;

@Controller
@RequiredArgsConstructor

public class UserController {

    private final UserService userService;
    private final FollowService followService;
    private final FileStore fileStore;

    @GetMapping("/")
    public String initPage(){
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }


    @GetMapping("/signup")
    public String signup(){
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute UserSignupDto userLoginDto){
        userService.join(userLoginDto);
        return "redirect:/login";
    }

    @GetMapping("/user/update")
    public String update(@AuthenticationPrincipal PrincipalDetails principal, Model model){
        User findUser = userService.findByEmail(principal.getUsername());
        model.addAttribute("user",findUser);
        return"user/update";
    }

    @PostMapping("/user/update")
    public String update(@ModelAttribute UserUpdateDto userUpdateDto, RedirectAttributes redirect) throws IOException {
        userService.update(userUpdateDto);
        redirect.addAttribute("profileEmail",userUpdateDto.getEmail());
        return "redirect:/user/profile";
    }

    @GetMapping("/user/profile")
    public String profile(@AuthenticationPrincipal PrincipalDetails principal, @RequestParam(required = false) String profileEmail, Model model){
        User findUser = userService.findByEmailWithPostList(principal.getUsername());
        if(profileEmail == null){
            UserProfileDto userProfile = getUserProfile(findUser.getEmail(), findUser);
            model.addAttribute("user",findUser);
            model.addAttribute("userProfile",userProfile);
            return "user/profile";
        }
        UserProfileDto userProfile = getUserProfile(profileEmail, findUser);
        model.addAttribute("user",findUser);
        model.addAttribute("userProfile",userProfile);
        return "user/profile";
    }


    //<Img>태그로 이미지를 조회할때 사용
    @ResponseBody
    @GetMapping("/image/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:"+fileStore.getFullPath(filename));
    }
    private UserProfileDto getUserProfile(String profileEmail, User loginUser){
        User profileUser = userService.findByEmailWithPostList(profileEmail);
        return UserProfileDto.builder()
                .user(profileUser)
                .loginUser(profileUser.getId()==loginUser.getId())
                .follow(followService.checkFollow(loginUser.getEmail(),profileEmail))
                .followerCount(followService.countFollower(profileEmail))
                .followingCount(followService.countFollowing(profileEmail))
                .postCount(profileUser.getPostList().size())
                .build();

    }
}
