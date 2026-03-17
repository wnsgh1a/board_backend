package com.example.board.service;

import com.example.board.dto.PostCreateRequest;
import com.example.board.dto.PostResponse;
import com.example.board.entity.Post;
import com.example.board.entity.User;
import com.example.board.repository.PostRepository;
import com.example.board.repository.UserRepository;
import com.example.board.dto.PostUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository; // 유저 정보도 찾아야 하니 같이 데려옵니다.

    // 1. 기존 게시글 작성 로직 (반환 타입을 PostResponse로 수정!)
    public PostResponse createPost(PostCreateRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다. id=" + request.getUserId()));

        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setUser(user);

        // DB에 저장한 뒤, 안전한 바구니(PostResponse)에 담아서 리턴!
        Post savedPost = postRepository.save(post);
        return new PostResponse(savedPost);
    }

    // 2. [NEW] 게시글 단건 조회 로직 추가
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다. id=" + id));

        // 찾은 게시글을 안전한 바구니에 담아서 리턴!
        return new PostResponse(post);
    }

    // [NEW] 게시글 전체 목록 조회 로직
// [MODIFIED] 게시글 전체 목록 조회 (페이징 적용!)
    // 리턴 타입이 List에서 Page로 바뀌었습니다!
    public Page<PostResponse> getAllPosts(int page, int size) {

        // 💡 핵심 1: 스프링(JPA)은 페이지 번호를 0부터 셉니다. (개발자들 종특이죠 ㅋㅋㅋ)
        // 그래서 프론트엔드가 1페이지를 달라고 하면, 우리는 서버에서 0페이지로 바꿔서 찾아야 합니다.
        // 그리고 글은 무조건 최신순(createdAt 기준으로 내림차순 DESC)으로 가져옵니다!
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdAt").descending());

        // 💡 핵심 2: DB 창고에서 페이징 룰(pageable)에 맞게 잘라서 가져옵니다.
        Page<Post> posts = postRepository.findAll(pageable);

        // 💡 핵심 3: 잘라온 날것의 게시글(Post)들을 안전한 바구니(PostResponse)로 싹 바꿔줍니다.
        // List 때 썼던 stream().map() 보다 훨씬 짧고 예쁩니다!
        return posts.map(PostResponse::new);
    }

    // [NEW] 게시글 수정 로직
    @Transactional // 💡 마법: 이 메서드가 끝날 때, 변경된 데이터가 있으면 알아서 DB에 저장(UPDATE)해 줍니다!
    public PostResponse updatePost(Long id, PostUpdateRequest request) {
        // 1. 수정할 게시글을 찾습니다.
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다. id=" + id));

        // 2. 아까 뚫어둔 Setter를 이용해서 제목과 내용을 새것으로 덮어씌웁니다.
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());

        // 3. 수정한 결과를 안전한 바구니에 담아서 돌려줍니다.
        return new PostResponse(post);
    }

    // [NEW] 게시글 삭제 로직
    public void deletePost(Long id) {
        // 1. 삭제할 게시글을 찾습니다.
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다. id=" + id));

        // 2. 가차 없이 DB에서 날려버립니다!
        postRepository.delete(post);
    }
}