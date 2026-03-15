package com.example.board.controller;

import com.example.board.dto.PostCreateRequest;
import com.example.board.entity.Post;
import com.example.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts") // 게시글 관련 요청은 여기로 모입니다.
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 게시글 작성 API (POST 요청)
    @PostMapping
    public Post createPost(@RequestBody PostCreateRequest request) {
        return postService.createPost(request);
    }
}