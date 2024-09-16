package hello.blog.controller.aboutMember;

import hello.blog.domain.Member;
import hello.blog.dto.aboutMember.MemberDto;
import hello.blog.dto.aboutMember.MemberLoginDto;
import hello.blog.dto.aboutMember.MemberSaveDto;
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

    //로그인
    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("member", new MemberLoginDto());
        return "page/login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("member") MemberDto memberDto, BindingResult bindingResult) {

        String loginId = memberDto.getLoginId();
        String password = memberDto.getPassword();
        Member member = memberService.login(loginId, password);
        if (member == null) {
            log.info("incorrect login");
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 올바르지 않습니다.");
            return "page/login";
        }
        return "redirect:/";

    }

    //회원가입
    @GetMapping("/signup")
    public String signUpPage(Model model) {

        model.addAttribute("member", new MemberSaveDto());

        return "page/signup";
    }

    @PostMapping("/signup")
    public String signUp(@Valid @ModelAttribute("member") MemberSaveDto form, BindingResult bindingResult) {

        Member member = new Member(form.getLoginId(), form.getName(), form.getPassword());
        memberService.validateDuplicateId(member, bindingResult);
        memberService.validatePasswordCorrect(form, bindingResult);

        //폼에 에러 있을 경우 다시 회원가입 페이지 리턴
        if (bindingResult.hasErrors()) {
            return "page/signup";
        }

        //문제 없을 경우 회원가입 진행
        memberService.registerMember(member);
        return "redirect:/";
    }
}
