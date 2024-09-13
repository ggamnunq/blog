package hello.blog.service;

import hello.blog.domain.Post;
import hello.blog.dto.PostDto;
import hello.blog.dto.PostEditDto;
import hello.blog.repository.PostRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final EntityManager em;

    public Post save(Post post) {
        postRepository.save(post);
        return post;
    }

    @Transactional(readOnly = true)
    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
    }

    @Transactional(readOnly = true)
    public Page<PostDto> loadByCondAndPaging(String type, String keyword, Pageable pageable) {

        int page = pageable.getPageNumber() - 1;
        int size = 5;
        Page<Post> postPages;
        Pageable pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"));
        if (type == null || keyword == null) {
            postPages = postRepository.findAll(pageRequest);
        }
        else if (type.equals("title")) {
            postPages = postRepository.findByTitle(keyword, pageRequest);
        } else if (type.equals("content")) {
            postPages = postRepository.findByContent(keyword, pageRequest);
        } else {
            postPages = postRepository.findByTitleOrContent(keyword, pageRequest);
        }
        return postPages.map(PostDto::new);
    }

    public Long update(Long id, PostEditDto editDto) {

        Post posts = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
        posts.update(editDto.getTitle(), editDto.getContent());
        return id;
    }

    public void remove(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
        postRepository.delete(post);
    }

    public PostDto findPrevious(Long id) {

        PostDto previousDto;
        try {
            previousDto = em.createQuery("select p from Post p where p.id < :id order by p.id desc", PostDto.class)
                    .setParameter("id", id)
                    .setMaxResults(1)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        return previousDto;
    }

    public PostDto findNext(Long id) {

        PostDto nextPostDto;
        try {
            nextPostDto = em.createQuery("select p from Post p where p.id > :id order by p.id asc", PostDto.class)
                    .setParameter("id", id)
                    .setMaxResults(1)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        return nextPostDto;
    }


}
