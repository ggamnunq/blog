package hello.blog.service;

import hello.blog.domain.Member;
import hello.blog.dto.aboutMember.MemberDto;
import hello.blog.dto.aboutMember.MemberLoginDto;
import hello.blog.dto.aboutMember.MemberSaveDto;
import hello.blog.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final EntityManager em;


    public void validateDuplicateId(Member member, BindingResult bindingResult) {

        Optional<Member> findByLoginId = memberRepository.findByLoginId(member.getLoginId());

        if (findByLoginId.isPresent()) {
            bindingResult.rejectValue(
                    "loginId", "duplicate", "이미 사용중인 아이디입니다"
            );
        }
    }

    public MemberLoginDto login(String loginId, String password) {

        Member member = memberRepository.findByLoginId(loginId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);

        if (member == null) {
            return null;
        }
        //Member -> MemberLoginDto
        return new MemberLoginDto(member.getLoginId(), member.getPassword());
    }


    public void validatePasswordCorrect(MemberSaveDto member, BindingResult bindingResult) {

        String password = member.getPassword();
        String confirm = member.getConfirmPassword();
        if (!password.equals(confirm)) { //비밀번호 일치하지 않을 경우
            bindingResult.rejectValue("password", "pwCorrect", "비밀번호가 일치하여야 합니다.");
        }
    }

    public void registerMember(Member member) {
        memberRepository.save(member);
    }

}
