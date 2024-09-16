package hello.blog.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Entity
@Getter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    @NotBlank(message = "아이디는 필수 입력 항목입니다.")
    private String loginId;
    @NotBlank(message = "이름은 필수 입력 항목입니다.")
    private String name;
    @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    private String password;

    public Member() {
    }

    public Member(String loginId, String name, String password) {
        this.loginId = loginId;
        this.name = name;
        this.password = password;
    }
}
