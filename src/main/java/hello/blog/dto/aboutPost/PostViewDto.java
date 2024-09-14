package hello.blog.dto.aboutPost;

import hello.blog.domain.Post;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PostViewDto {

    private Long id;

    @NotBlank
    private String title;

    @NotNull
    private String content;

    public PostViewDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
    }
}
