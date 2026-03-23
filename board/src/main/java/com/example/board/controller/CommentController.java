package com.example.board.controller;

import com.example.board.dto.CommentCreateRequest;
import com.example.board.dto.CommentResponse;
import com.example.board.dto.CommentUpdateRequest;
import com.example.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/posts/{postId}/comments") // 💡 주소에 postId가 포함됩니다!
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public String createComment(
            @PathVariable Long postId,
            @Valid @RequestBody CommentCreateRequest request,
            Authentication authentication) {

        return commentService.createComment(postId, request, authentication.getName());
    }

    @PutMapping("/{commentId}")
    public CommentResponse updateComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @RequestBody CommentUpdateRequest request,
            Authentication authentication) {

        return commentService.updateComment(commentId, request, authentication.getName());
    }

    @DeleteMapping("/{commentId}")
    public String deleteComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            Authentication authentication) {

        commentService.deleteComment(commentId, authentication.getName());
        return "댓글 삭제가 완료되었습니다!";
    }
}