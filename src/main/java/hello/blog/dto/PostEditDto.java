package hello.blog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PostEditDto {

    private Long id;

    @NotBlank
    private String title;

    @NotNull
    private String content;

}
