package com.example.board.controller;

import com.example.board.dto.PostCreateRequest;
import com.example.board.dto.PostResponse;
import com.example.board.entity.Post;
import com.example.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts") // 게시글 관련 요청은 여기로 모입니다.
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 기존 작성 API (반환 타입 수정!)
    @PostMapping
    public PostResponse createPost(@RequestBody PostCreateRequest request) {
        return postService.createPost(request);
    }

    // [NEW] 게시글 단건 조회 API (GET 요청)
    // 주소 예시: http://localhost:8080/api/posts/1
    @GetMapping("/{id}")
    public PostResponse getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    // [NEW] 게시글 전체 목록 조회 API (GET 요청)
    // 주소 예시: http://localhost:8080/api/posts
    @GetMapping
    public List<PostResponse> getAllPosts() {
        return postService.getAllPosts();
    }
}