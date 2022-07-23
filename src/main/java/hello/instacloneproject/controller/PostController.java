package hello.instacloneproject.controller;

import hello.instacloneproject.domain.User;
import hello.instacloneproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final UserService userService;

    @GetMapping("/post/story")
    public String test(Model model, @AuthenticationPrincipal User user){
        User findUser = userService.findByEmail(user.getEmail());
        model.addAttribute("user",findUser);
        return "post/story";
    }
}
