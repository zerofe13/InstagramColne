package hello.instacloneproject.controller;

import hello.instacloneproject.config.PrincipalDetails;
import hello.instacloneproject.domain.User;
import hello.instacloneproject.dto.Post.PostDto;
import hello.instacloneproject.dto.Post.PostUpdateDto;
import hello.instacloneproject.dto.Post.PostUploadDto;
import hello.instacloneproject.service.LikesService;
import hello.instacloneproject.service.PostService;
import hello.instacloneproject.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final UserService userService;
    private final PostService postService;

    //스토리
    @GetMapping("/post/story")
    public String test(Model model, @AuthenticationPrincipal PrincipalDetails principal){
        User findUser = userService.findByEmail(principal.getUsername());
        model.addAttribute("user",findUser);
        return "post/story";
    }
    //포스트 업로드
    @GetMapping("/post/upload")
    public String upload(@AuthenticationPrincipal PrincipalDetails principal,Model model){
        User findUser = userService.findByEmail(principal.getUsername());
        model.addAttribute("user",findUser);
        model.addAttribute("post", new PostUploadDto());
        return "post/upload";
    }

    @PostMapping("post")
    public String uploadPost(@Validated @ModelAttribute("post") PostUploadDto postUploadDto, BindingResult bindingResult ,@AuthenticationPrincipal PrincipalDetails principal) throws IOException {
        //이미지 검증
        if(postUploadDto.getPostImgFile().isEmpty()){
            bindingResult.rejectValue("postImgFile","emptyMultipart","이미지를 등록해주세요.");
            log.info("error = {}",bindingResult);
        }

        if(bindingResult.hasErrors()){
            return "post/upload";
        }
        User findUser = userService.findByEmail(principal.getUsername());
        postService.uploadPost(postUploadDto, findUser.getEmail());
        return "redirect:/user/profile";
    }
    //포스트 업데이트
    @GetMapping("/post/update/{postId}")
    public String update(@PathVariable long postId,Model model,@AuthenticationPrincipal PrincipalDetails principal){
        PostUpdateDto findPostDto = postService.getPostDtoById(postId);
        User findUser = userService.findByEmail(principal.getUsername());

        model.addAttribute("post",findPostDto);
        model.addAttribute("user",findUser);

        return "post/update";
    }

    /**
     * bindingResult  바인딩 받는 객체 바로뒤에 있어야 한다.
     */
    @PostMapping("/post/update")
    public String postUpdate(@Validated @ModelAttribute("post") PostUpdateDto postUpdateDto,BindingResult bindingResult, @AuthenticationPrincipal PrincipalDetails principal,RedirectAttributes redirect){
        if(bindingResult.hasErrors()){
            return "post/update";
        }
        postService.updatePost(postUpdateDto);
        redirect.addAttribute("profileEmail",principal.getUsername());
        return "redirect:/user/profile";
    }
    //포스트 삭제
    @PostMapping("/post/delete")
    public String delete(@RequestParam("postId") long postId,@AuthenticationPrincipal PrincipalDetails principal,RedirectAttributes redirect){
        postService.deletePost(postId);
        redirect.addAttribute("profileEmail",principal.getUsername());
        return "redirect:/user/profile";
    }
    //tag 검색
    @GetMapping("/post/search")
    public String search(@RequestParam("tag") String tag,@AuthenticationPrincipal PrincipalDetails principal ,Model model){
        User findUser = userService.findByEmail(principal.getUsername());
        model.addAttribute("tag",tag);
        model.addAttribute("user",findUser);
        return "post/search";
    }

    @PostMapping("/post/searchForm")
    public String searchForm(String tag,RedirectAttributes redirect){
        redirect.addAttribute("tag",tag);
        return "redirect:/post/search";
    }

    //좋아요 페이지
    @GetMapping("/post/likes")
    public String likes(@AuthenticationPrincipal PrincipalDetails principal,Model model){
        User findUser = userService.findByEmail(principal.getUsername());
        model.addAttribute("user",findUser);
        return"post/likes";
    }

    @GetMapping("/post/popular")
    public String Popular(@AuthenticationPrincipal PrincipalDetails principal,Model model){
        User findUser = userService.findByEmail(principal.getUsername());
        model.addAttribute("user",findUser);
        return "post/popular";
    }

}
