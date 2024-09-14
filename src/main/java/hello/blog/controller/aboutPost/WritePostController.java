package hello.blog.controller.aboutPost;

import hello.blog.dto.aboutPost.PostAddDto;
import hello.blog.domain.Post;
import hello.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/post")
public class WritePostController {

    private final PostService postService;

    @GetMapping("/write")
    public String write(Model model) {

        model.addAttribute("post", new Post());

        return "page/writePost";
    }

    @PostMapping("/write")
    public String writePost(@ModelAttribute("post") PostAddDto form) {

        //post 추가
        Post post = new Post(form.getTitle(), form.getContent());
        log.info("글 저장| title : {}| content: {}", form.getTitle(), form.getContent());
        postService.save(post);

        //나중에 글 보는 페이지로 이동하는거 만들기
        return "redirect:/";
    }
}
