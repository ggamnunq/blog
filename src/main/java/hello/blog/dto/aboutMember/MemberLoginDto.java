package hello.blog.dto.aboutMember;

import lombok.Data;

@Data
public class MemberLoginDto {

    private String loginId;
    private String password;

    public MemberLoginDto() {
    }

    public MemberLoginDto(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }
}
