package hello.blog.service;

import hello.blog.domain.Member;
import hello.blog.repository.MemberRepository;
import jakarta.persistence.EntityManager;
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

    public void validateDuplicateMember(Member member, BindingResult bindingResult) {

        Optional<Member> findByLoginId = memberRepository.findByLoginId(member.getLoginId());

        if (findByLoginId.isPresent()) {
            bindingResult.rejectValue(
                    "loginId", "duplicate", "이미 사용중인 아이디입니다"
            );
        }
    }

    public void registerMember(Member member) {
        memberRepository.save(member);
    }

}
