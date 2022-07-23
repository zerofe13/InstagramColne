package hello.instacloneproject.controller;

import hello.instacloneproject.domain.User;
import hello.instacloneproject.dto.user.UserProfileDto;
import hello.instacloneproject.dto.user.UserSignupDto;
import hello.instacloneproject.dto.user.UserUpdateDto;
import hello.instacloneproject.service.FollowService;
import hello.instacloneproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor

public class UserController {
    private final UserService userService;
    private final FollowService followService;

    @GetMapping("/login")
    public String login(){
        return "login";
    }


    @GetMapping("/signup")
    public String signup(){
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(UserSignupDto userLoginDto){
        userService.join(userLoginDto);
        return "redirect:/login";
    }

    @GetMapping("/user/update")
    public String update(@AuthenticationPrincipal User user, Model model){
        User findUser = userService.findByEmail(user.getEmail());
        model.addAttribute("user",findUser);
        return"user/update";
    }

    @PostMapping("/user/update")
    public String update(UserUpdateDto userUpdateDto, RedirectAttributes redirect){
        userService.update(userUpdateDto);
        redirect.addAttribute("profileEmail",userUpdateDto.getEmail());
        return "redirect:/user/profile";
    }

    @GetMapping("/user/profile")
    public String profile(@AuthenticationPrincipal User user, @RequestParam(required = false) String profileEmail, Model model){
        User findUser = userService.findByEmail(user.getEmail());
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

    private UserProfileDto getUserProfile(String profileEmail, User loginUser){
        User profileUser = userService.findByEmail(profileEmail);
        return UserProfileDto.builder()
                .user(profileUser)
                .loginUser(profileUser.getId()==loginUser.getId())
                .follow(followService.checkFollow(loginUser.getEmail(),profileEmail))
                .followerCount(followService.countFollower(profileEmail))
                .followingCount(followService.countFollowing(profileEmail))
                .postCount(0)
                .build();

    }
}
