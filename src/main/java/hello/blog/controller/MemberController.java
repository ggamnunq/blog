package hello.blog.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    @GetMapping("/login")
    public String login() {

        return "page/login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "page/signup";
    }

}
