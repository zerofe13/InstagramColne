package hello.instacloneproject.controller;

import hello.instacloneproject.domain.User;
import hello.instacloneproject.repository.dto.UserLoginDto;
import hello.instacloneproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.AbstractAuditable_;
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

    @GetMapping("/signup")
    public String signup(Model model){
//        model.addAttribute("user",new UserLoginDto());
        return "signup";
    }
    @PostMapping("/signup")
    public String signup(UserLoginDto userLoginDto){
        userService.join(userLoginDto);
        return "redirect:/login";
    }
}
