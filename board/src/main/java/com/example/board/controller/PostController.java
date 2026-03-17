package com.example.board.controller;

import com.example.board.dto.PostCreateRequest;
import com.example.board.dto.PostResponse;
import com.example.board.dto.PostUpdateRequest;
import com.example.board.entity.Post;
import com.example.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/posts") // 게시글 관련 요청은 여기로 모입니다.
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 기존 작성 API (반환 타입 수정!)
// [MODIFIED] 글 작성 API에 @Valid 수문장 추가!
    @PostMapping
    public PostResponse createPost(
            // 💡 @Valid: "이 바구니(request) 안에 빈칸 있는지 빡세게 검사해!" 라는 뜻입니다.
            @Valid @RequestBody PostCreateRequest request) {
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
// [MODIFIED] 게시글 전체 목록 페이징 조회 API (GET 요청)
    // 주소 예시: http://localhost:8080/api/posts?page=1&size=10
    @GetMapping
    public Page<PostResponse> getAllPosts(
            // @RequestParam: 주소창 뒤에 ?page=1 처럼 넘어오는 값을 받습니다.
            // 만약 프론트엔드가 아무것도 안 보내면 기본값(defaultValue)으로 1페이지, 10개씩을 세팅합니다!
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        return postService.getAllPosts(page, size);
    }

    // [NEW] 게시글 수정 API (PUT 요청)
    // 주소 예시: http://localhost:8080/api/posts/1
    @PutMapping("/{id}")
    public PostResponse updatePost(@PathVariable Long id, @RequestBody PostUpdateRequest request) {
        return postService.updatePost(id, request);
    }

    // [NEW] 게시글 삭제 API (DELETE 요청)
    @DeleteMapping("/{id}")
    public String deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return "게시글 삭제가 완료되었습니다!"; // 삭제 성공 메시지를 프론트엔드에 던져줍니다.
    }
}