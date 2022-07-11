package hello.instacloneproject.controller;

import org.springframework.data.jpa.domain.AbstractAuditable_;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/")
    public String login(){
        return "login";
    }
}
