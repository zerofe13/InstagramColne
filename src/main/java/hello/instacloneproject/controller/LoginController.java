package hello.instacloneproject.controller;

import hello.instacloneproject.domain.User;
import hello.instacloneproject.repository.dto.UserLoginDto;
import hello.instacloneproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

//    @PostMapping("/login")
//    public String login(String email, String password){
//
//    }

    @GetMapping("/signup")
    public String signup(){
        return "signup";
    }
    @PostMapping("/signup")
    public String signup(UserLoginDto userLoginDto){
        userService.join(userLoginDto);
        return "redirect:/login";
    }

    @GetMapping("/test")
    public String test(Model model,@AuthenticationPrincipal  User user){
        model.addAttribute("user",user);
        return "test";
    }
}
