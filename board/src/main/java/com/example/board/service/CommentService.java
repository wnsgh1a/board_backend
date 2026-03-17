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

    // [NEW] 댓글 수정 로직
    @Transactional // 💡 변경된 내용(content)을 메서드 끝날 때 알아서 DB에 덮어씌워 주는 마법!
    public CommentResponse updateComment(Long commentId, CommentUpdateRequest request) {
        // 1. 수정할 댓글을 DB에서 찾습니다.
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));

        // 2. 내용을 새것으로 싹 갈아 끼웁니다.
        comment.setContent(request.getContent());

        // 3. 수정한 결과를 예쁜 바구니에 담아서 돌려줍니다.
        return new CommentResponse(comment);
    }

    // [NEW] 댓글 삭제 로직
    public void deleteComment(Long commentId) {
        // 1. 삭제할 댓글을 찾습니다.
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));

        // 2. DB에서 가차 없이 날려버립니다!
        commentRepository.delete(comment);
    }
}