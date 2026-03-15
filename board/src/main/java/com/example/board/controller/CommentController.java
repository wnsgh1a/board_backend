package com.example.board.controller;

import com.example.board.dto.CommentCreateRequest;
import com.example.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts/{postId}/comments") // 💡 주소에 postId가 포함됩니다!
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성 API
    @PostMapping
    public String createComment(
            @PathVariable Long postId, // URL에서 postId를 뽑아옵니다.
            @RequestBody CommentCreateRequest request) {

        return commentService.createComment(postId, request);
    }
}