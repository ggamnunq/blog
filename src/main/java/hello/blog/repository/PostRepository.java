package hello.blog.repository;

import hello.blog.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {

    //제목으로 검색
    @Query("select p from Post p where p.title LIKE %:title%")
    Page<Post> findByTitle(@Param("title") String title, Pageable pageable);

    //내용으로 검색
    @Query("select p from Post p where p.content LIKE %:content%")
    Page<Post> findByContent(@Param("content") String content, Pageable pageable);

    //제목 또는 내용으로 검색
    @Query("select p from Post p where p.title LIKE %:keyword% or p.content LIKE %:keyword%")
    Page<Post> findByTitleOrContent(@Param("keyword") String keyword, Pageable pageable);

    Page<Post> findById(Long id, Pageable pageable);

}
