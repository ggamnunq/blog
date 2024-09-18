package hello.blog.controller.aboutMember;

import hello.blog.domain.Member;
import hello.blog.dto.aboutMember.MemberDto;
import hello.blog.dto.aboutMember.MemberLoginDto;
import hello.blog.dto.aboutMember.MemberSaveDto;
import hello.blog.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
    public String login(@Valid @ModelAttribute("member") MemberDto memberDto, BindingResult bindingResult,
                        HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return "page/login";
        }

        MemberLoginDto loginMember = memberService.login(memberDto.getLoginId(), memberDto.getPassword());
        if (loginMember == null) {
            log.info("incorrect login");
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 올바르지 않습니다.");
            return "page/login";
        }
        //로그인 성공

        //세션이 있으면 세션 반환, 없으면 신규 생성
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        return "redirect:/";

    }

    //로그아웃
    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {

        //세션 삭제
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); //세션 제거
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
