package hello.blog.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

@Entity
@Getter
public class Post {

    @Id @GeneratedValue
    @Column(name = "post_id", columnDefinition = "LONGTEXT")
    private Long id;
    private String title;
    @Lob
    @Column(columnDefinition = "longtext")
    private String content;

    public Post() {

    }

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
