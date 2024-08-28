package hello.blog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PostAddDto {

    @NotBlank
    private String title;

    @NotNull
    private String content;

}
