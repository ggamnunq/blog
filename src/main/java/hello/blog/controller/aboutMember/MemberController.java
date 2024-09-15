package hello.blog.controller.aboutMember;

import hello.blog.domain.Member;
import hello.blog.dto.aboutMember.MemberDto;
import hello.blog.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String login() {
        return "page/login";
    }

    @GetMapping("/signup")
    public String signUpPage(Model model) {

        model.addAttribute("member", new Member());

        return "page/signup";
    }

    @PostMapping("/signup")
    public String signUp(@Valid @ModelAttribute("member") MemberDto form, BindingResult bindingResult) {

        Member member = new Member(form.getLoginId(), form.getName(), form.getPassword());
        memberService.validateDuplicateMember(member, bindingResult);

        //폼에 에러 있을 경우 다시 회원가입 페이지 리턴
        if (bindingResult.hasErrors()) {
            return "page/signup";
        }

        //문제 없을 경우 회원가입 진행
        memberService.registerMember(member);
        return "redirect:/";
    }
}
