package com.example.board.service;

import com.example.board.dto.PostCreateRequest;
import com.example.board.dto.PostResponse;
import com.example.board.entity.Post;
import com.example.board.entity.User;
import com.example.board.repository.PostRepository;
import com.example.board.repository.UserRepository;
import com.example.board.dto.PostUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostResponse createPost(PostCreateRequest request, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setUser(user);

        Post savedPost = postRepository.save(post);
        return new PostResponse(savedPost);
    }

    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다. id=" + id));
        return new PostResponse(post);
    }

    public Page<PostResponse> getAllPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdAt").descending());
        Page<Post> posts = postRepository.findAll(pageable);
        return posts.map(PostResponse::new);
    }

    @Transactional
    public PostResponse updatePost(Long id, PostUpdateRequest request, String email) { // 💡 email 파라미터 추가!
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다. id=" + id));

        if (!post.getUser().getEmail().equals(email)) {
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        }

        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        return new PostResponse(post);
    }

    public void deletePost(Long id, String email) { // 💡 email 파라미터 추가!
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다. id=" + id));

        if (!post.getUser().getEmail().equals(email)) {
            throw new IllegalArgumentException("작성자만 삭제할 수 있습니다.");
        }

        postRepository.delete(post);
    }
}