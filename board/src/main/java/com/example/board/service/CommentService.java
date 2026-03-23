package com.example.board.service;

import com.example.board.dto.CommentCreateRequest;
import com.example.board.dto.CommentResponse;
import com.example.board.dto.CommentUpdateRequest;
import com.example.board.entity.Comment;
import com.example.board.entity.Post;
import com.example.board.entity.User;
import com.example.board.repository.CommentRepository;
import com.example.board.repository.PostRepository;
import com.example.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public String createComment(Long postId, CommentCreateRequest request, String email) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));

        Comment comment = new Comment();
        comment.setContent(request.getContent());
        comment.setUser(user);
        comment.setPost(post);

        commentRepository.save(comment);

        return "댓글이 성공적으로 작성되었습니다!";
    }

    @Transactional
    public CommentResponse updateComment(Long commentId, CommentUpdateRequest request, String email) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));

        if (!comment.getUser().getEmail().equals(email)) {
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        }

        comment.setContent(request.getContent());
        return new CommentResponse(comment);
    }

    public void deleteComment(Long commentId, String email) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));

        if (!comment.getUser().getEmail().equals(email)) {
            throw new IllegalArgumentException("작성자만 삭제할 수 있습니다.");
        }

        commentRepository.delete(comment);
    }
}