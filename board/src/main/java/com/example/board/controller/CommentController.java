package com.example.board.controller;

import com.example.board.dto.CommentCreateRequest;
import com.example.board.dto.CommentResponse;
import com.example.board.dto.CommentUpdateRequest;
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

    // [NEW] 댓글 수정 API (PUT 요청)
    // 주소 예시: http://localhost:8080/api/posts/2/comments/1
    @PutMapping("/{commentId}")
    public CommentResponse updateComment(
            @PathVariable Long postId, // 주소에서 게시글 번호 통과~
            @PathVariable Long commentId, // 주소에서 수정할 댓글 번호 쏙 빼오기
            @RequestBody CommentUpdateRequest request) {

        return commentService.updateComment(commentId, request);
    }

    // [NEW] 댓글 삭제 API (DELETE 요청)
    @DeleteMapping("/{commentId}")
    public String deleteComment(
            @PathVariable Long postId,
            @PathVariable Long commentId) {

        commentService.deleteComment(commentId);
        return "댓글 삭제가 완료되었습니다!";
    }
}