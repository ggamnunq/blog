package hello.blog.controller;

import hello.blog.domain.Post;
import hello.blog.dto.PostDto;
import hello.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
@Slf4j
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/{postId}")
    public String post2(@PathVariable("postId") Long postId, Model model) {

        Post post = postService.findById(postId);
        PostDto previousPostDto = postService.findPrevious(postId);
        PostDto nextPostDto = postService.findNext(postId);

        model.addAttribute("post", post);
        model.addAttribute("previousPost", previousPostDto);
        model.addAttribute("nextPost", nextPostDto);
        return "page/post";
    }

    @PostMapping("/{postId}/delete")
    public String delete(@PathVariable("postId") Long postId ) {
        postService.remove(postId);
        return "redirect:/";
    }
}
