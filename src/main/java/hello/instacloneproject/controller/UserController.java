package hello.instacloneproject.controller;

import hello.instacloneproject.domain.User;
import hello.instacloneproject.repository.dto.user.UserDto;
import hello.instacloneproject.repository.dto.user.UserProfileDto;
import hello.instacloneproject.repository.dto.user.UserSignupDto;
import hello.instacloneproject.repository.dto.user.UserUpdateDto;
import hello.instacloneproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

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
        UserDto findUserDto = userService.findByEmail(user.getEmail());
        model.addAttribute("user",findUserDto);
        return"user/update";
    }

    @PostMapping("/user/update")
    public String update(UserUpdateDto userUpdateDto){
        userService.update(userUpdateDto);
        return "redirect:/user/profile";
    }

//    @GetMapping
//    public String profile(@AuthenticationPrincipal User user, Model model){
//        UserDto findUser = userService.findByEmail(user.getEmail());
//        model.addAttribute("user")
//    }
}
