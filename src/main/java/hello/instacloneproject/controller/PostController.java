package hello.instacloneproject.controller;

import hello.instacloneproject.domain.User;
import hello.instacloneproject.dto.Post.PostUploadDto;
import hello.instacloneproject.service.PostService;
import hello.instacloneproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final UserService userService;
    private final PostService postService;

    @GetMapping("/post/story")
    public String test(Model model, @AuthenticationPrincipal User user){
        User findUser = userService.findByEmail(user.getEmail());
        model.addAttribute("user",findUser);
        return "post/story";
    }

    @GetMapping("/post/upload")
    public String upload(@AuthenticationPrincipal User user,Model model){
        User findUser = userService.findByEmail(user.getEmail());
        model.addAttribute("user",findUser);
        return "post/upload";
    }

    @PostMapping("post")
    public String uploadPost(@ModelAttribute PostUploadDto postUploadDto,@AuthenticationPrincipal User user) throws IOException {
        User findUser = userService.findByEmail(user.getEmail());
        postService.uploadPost(postUploadDto, findUser.getEmail());
        return "redirect:/user/profile";
    }
}
