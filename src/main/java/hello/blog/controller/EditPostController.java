package hello.blog.controller;

import hello.blog.domain.Post;
import hello.blog.dto.PostEditDto;
import hello.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
@Slf4j
public class EditPostController {

    private final PostService postService;

    @GetMapping("/{postId}/edit")
    public String editPage(@PathVariable("postId") Long postId, Model model) {

        Post post = postService.findById(postId);
        model.addAttribute("post", post);
        return "post/edit";
    }

    @PostMapping("/{postId}/edit")
    public String edit(@PathVariable("postId") Long postId, @ModelAttribute("post") PostEditDto form) {

        postService.update(postId, form);
        return "redirect:/post/{postId}";
    }


}
