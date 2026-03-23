package com.example.board.controller;

import com.example.board.dto.PostCreateRequest;
import com.example.board.dto.PostResponse;
import com.example.board.dto.PostUpdateRequest;
import com.example.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication; // 💡 더 안전한 Authentication으로 교체!
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public PostResponse createPost(
            @Valid @RequestBody PostCreateRequest request,
            Authentication authentication) {
        return postService.createPost(request, authentication.getName());
    }

    @GetMapping("/{id}")
    public PostResponse getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    @GetMapping
    public Page<PostResponse> getAllPosts(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        return postService.getAllPosts(page, size);
    }

    @PutMapping("/{id}")
    public PostResponse updatePost(
            @PathVariable Long id,
            @RequestBody PostUpdateRequest request,
            Authentication authentication) {
        return postService.updatePost(id, request, authentication.getName());
    }

    @DeleteMapping("/{id}")
    public String deletePost(
            @PathVariable Long id,
            Authentication authentication) {
        postService.deletePost(id, authentication.getName());
        return "게시글 삭제가 완료되었습니다!";
    }
}