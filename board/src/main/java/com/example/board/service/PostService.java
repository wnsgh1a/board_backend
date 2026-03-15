package com.example.board.service;

import com.example.board.dto.PostCreateRequest;
import com.example.board.entity.Post;
import com.example.board.entity.User;
import com.example.board.repository.PostRepository;
import com.example.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository; // 유저 정보도 찾아야 하니 같이 데려옵니다.

    public Post createPost(PostCreateRequest request) {
        // 1. 작성자(userId)가 실제 DB에 존재하는 유저인지 먼저 확인합니다.
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다. id=" + request.getUserId()));

        // 2. 게시글(Post) 엔티티를 만들고 데이터를 채워 넣습니다.
        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());

        // 3. 핵심! 게시글에 '이 글의 주인은 아까 찾은 그 유저야!' 라고 연결해 줍니다.
        post.setUser(user);

        // 4. DB에 저장!
        return postRepository.save(post);
    }
}