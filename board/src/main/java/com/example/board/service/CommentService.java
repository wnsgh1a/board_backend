package com.example.board.service;

import com.example.board.dto.CommentCreateRequest;
import com.example.board.entity.Comment;
import com.example.board.entity.Post;
import com.example.board.entity.User;
import com.example.board.repository.CommentRepository;
import com.example.board.repository.PostRepository;
import com.example.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository; // 게시글도 찾아야 함!
    private final UserRepository userRepository; // 유저도 찾아야 함!

    public String createComment(Long postId, CommentCreateRequest request) {
        // 1. 댓글을 달 게시글이 진짜 있는지 확인!
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        // 2. 댓글을 작성하는 유저가 진짜 있는지 확인!
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));

        // 3. 댓글 엔티티를 만들고 데이터 세팅
        Comment comment = new Comment();
        comment.setContent(request.getContent());
        comment.setUser(user); // "이 유저가 썼어!"
        comment.setPost(post); // "이 게시글에 달렸어!"

        // 4. DB에 저장
        commentRepository.save(comment);

        return "댓글이 성공적으로 작성되었습니다!";
    }
}