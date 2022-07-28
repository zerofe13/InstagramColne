package hello.instacloneproject.controller;

import hello.instacloneproject.domain.Post;
import hello.instacloneproject.domain.User;
import hello.instacloneproject.dto.Post.PostDto;
import hello.instacloneproject.dto.Post.PostInfoDto;
import hello.instacloneproject.dto.Post.PostUpdateDto;
import hello.instacloneproject.dto.Post.PostUploadDto;
import hello.instacloneproject.service.LikesService;
import hello.instacloneproject.service.PostService;
import hello.instacloneproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final UserService userService;
    private final PostService postService;
    private final LikesService likesService;

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

    @GetMapping("/post/update/{postId}")
    public String update(@PathVariable long postId,Model model,@AuthenticationPrincipal User user){
        PostDto findPostDto = postService.getPostDtoById(postId);
        User findUser = userService.findByEmail(user.getEmail());

        model.addAttribute("postDto",findPostDto);
        model.addAttribute("user",findUser);

        return "post/update";
    }

    @PostMapping("/post/update")
    public String postUpdate(@ModelAttribute PostUpdateDto postUpdateDto, @AuthenticationPrincipal User user, RedirectAttributes redirect){
        postService.updatePost(postUpdateDto);
        redirect.addAttribute("profileEmail",user.getEmail());
        return "redirect:/user/profile";
    }

    @PostMapping("/post/delete")
    public String delete(@RequestParam("postId") long postId,@AuthenticationPrincipal User user,RedirectAttributes redirect){
        postService.deletePost(postId);
        redirect.addAttribute("profileEmail",user.getEmail());
        return "redirect:/user/profile";
    }


}
