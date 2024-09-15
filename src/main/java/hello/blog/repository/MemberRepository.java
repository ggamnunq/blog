package hello.blog.repository;

import hello.blog.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    //loginId로 Member 조회
    Optional<Member> findByLoginId(String loginId);

}
