package com.example.board.service;

import com.example.board.dto.PostCreateRequest;
import com.example.board.dto.PostResponse;
import com.example.board.entity.Post;
import com.example.board.entity.User;
import com.example.board.repository.PostRepository;
import com.example.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository; // 유저 정보도 찾아야 하니 같이 데려옵니다.

    // 1. 기존 게시글 작성 로직 (반환 타입을 PostResponse로 수정!)
    public PostResponse createPost(PostCreateRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다. id=" + request.getUserId()));

        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setUser(user);

        // DB에 저장한 뒤, 안전한 바구니(PostResponse)에 담아서 리턴!
        Post savedPost = postRepository.save(post);
        return new PostResponse(savedPost);
    }

    // 2. [NEW] 게시글 단건 조회 로직 추가
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다. id=" + id));

        // 찾은 게시글을 안전한 바구니에 담아서 리턴!
        return new PostResponse(post);
    }

}