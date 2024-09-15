package hello.blog.dto.aboutMember;

import lombok.Data;

@Data
public class MemberDto {

    private Long id;
    private String loginId;
    private String name;
    private String password;

}
