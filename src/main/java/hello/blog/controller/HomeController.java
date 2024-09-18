package hello.blog.controller;

import hello.blog.controller.aboutMember.SessionConst;
import hello.blog.domain.Member;
import hello.blog.dto.aboutMember.MemberLoginDto;
import hello.blog.dto.aboutPost.PostDto;
import hello.blog.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {

    private final PostService postService;

    @GetMapping
    public String home(@RequestParam(value = "type", required = false) String type,
                       @RequestParam(value = "keyword", required = false) String keyword,
                       @PageableDefault(page=1) Pageable pageable,
                       Model model,
                       @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)MemberLoginDto loginMember) {

        Page<PostDto> page = postService.loadByCondAndPaging(type, keyword, pageable);
        List<PostDto> posts = page.getContent();
        model.addAttribute("posts", posts);
        model.addAttribute("page", page);
        model.addAttribute("type", type);
        model.addAttribute("keyword", keyword);

        if (loginMember == null) {
            return "page/home";
        }

        return "page/loginedHome";
    }

}
