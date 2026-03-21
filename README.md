# 📝 Spring Boot REST API 게시판 프로젝트

## 💡 프로젝트 소개
이 프로젝트는 Spring Boot와 JPA를 활용하여 구축한 **RESTful API 기반의 게시판 백엔드 서비스**입니다.
단순한 게시글 작성을 넘어, 실제 서비스에서 요구하는 **페이징 처리, DTO를 통한 데이터 은닉, 양방향 매핑, 그리고 전역 예외 처리**까지 실무 백엔드의 핵심 기본기를 탄탄하게 구현했습니다.

## 🛠️ 기술 스택 (Tech Stack)
* **Language:** Java 17
* **Framework:** Spring Boot 3.x
* **Database:** MySQL, Spring Data JPA
* **Tools:** IntelliJ IDEA, Postman, GitHub
* **Libraries:** Lombok, Spring Boot Validation

## ✨ 핵심 기능 (Key Features)

### 1. 게시글 (Post) CRUD & 페이징
* 게시글 작성, 단건 조회, 수정, 삭제 기능
* `Pageable`을 활용한 게시글 전체 목록 페이징 및 최신순 정렬 반환
* 프론트엔드 처리를 위한 풍부한 페이징 메타데이터(totalElements, totalPages 등) 제공

### 2. 댓글 (Comment) CRUD & 양방향 매핑
* 특정 게시글에 종속된 댓글 작성, 수정, 삭제 기능
* JPA `@OneToMany`, `@ManyToOne` 양방향 매핑을 통해 게시글 단건 조회 시 댓글 목록 함께 반환 (N+1 문제 고려 및 DTO 중첩 적용)
* `CascadeType.ALL`, `orphanRemoval`을 통한 부모-자식 엔티티 생명주기 관리

### 3. 보안 및 데이터 은닉 (DTO 패턴)
* Entity 클래스를 API 응답으로 직접 노출하지 않고, Request/Response DTO를 분리하여 설계
* 유저 비밀번호 등 민감한 정보는 제외하고 작성자 '닉네임' 등 필요한 데이터만 안전하게 전달

### 4. 탄탄한 예외 처리 & 입력값 검증
* `@Valid`와 `@NotBlank`를 활용한 클라이언트 입력값(게시글 제목, 내용 등) 1차 검증
* `@RestControllerAdvice`와 `@ExceptionHandler`를 활용한 **전역 예외 처리 (Global Exception Handling)**
* 클라이언트가 이해하기 쉬운 규격화된 커스텀 JSON 에러 응답(Status Code, Message) 제공

## 🌐 API 명세서 (API Endpoints)

| 기능 | Method | URL |
|---|---|---|
| **게시글 작성** | `POST` | `/api/posts` |
| **게시글 전체 조회(페이징)** | `GET` | `/api/posts?page={page}&size={size}` |
| **게시글 단건 조회(댓글 포함)**| `GET` | `/api/posts/{id}` |
| **게시글 수정** | `PUT` | `/api/posts/{id}` |
| **게시글 삭제** | `DELETE` | `/api/posts/{id}` |
| **댓글 작성** | `POST` | `/api/posts/{postId}/comments` |
| **댓글 수정** | `PUT` | `/api/posts/{postId}/comments/{commentId}` |
| **댓글 삭제** | `DELETE` | `/api/posts/{postId}/comments/{commentId}` |
