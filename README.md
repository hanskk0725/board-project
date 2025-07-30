# Board Project

---

<aside>
💡

로그인 기능이 포함된 간단한 게시판 기능을 제공하는 Spring Boot 기반 웹 애플리케이션 입니다.

</aside>

---

## 개요

- 프로젝트명 : 게시판 서비스
- 개발 인원 : 1명
- 개발기간 : 2025.07.21 ~ 2025.07.29

## 주요 기능

- 게시글 CRUD
- 사용자 회원가입 / 로그인
- 페이징 및 검색 기능
- 세션 기반 로그인 유지

---

## 기술 스택

---

- Java 17
- Spring Boot 3.5.3
- Spring Data JPA
- MySQL
- QueryDSL
- Thymeleaf
- Bootstrap

### 기타 사용 기술

- 필터(Spring Interceptor)
    - 세션 쿠키를 이용한 로그인 상태 확인
- 유효성 검사(Spring Bean Validation)
    - 사용자 입력시 유효성 검사

---

## API 설계

> 게시판 관련
> 

| 기능 | Method | URL | redirect |
| --- | --- | --- | --- |
| Index Page | GET | / |  |
| 게시글 전체 목록 | GET | /posts |  |
| 게시글 상세 | GET | /posts/{id} |  |
| 게시글 작성 폼 | GET | /posts/add |  |
| 게시글 등록 | POST | /posts/add | /posts → 게시글 전체 목록 |
| 게시글 수정 폼 | GET | /posts/edit/{id} |  |
| 게시글 수정 | POST | /posts/edit/{id} | /posts/{id} → 게시글 상세 |
| 게시글 삭제 | POST | /posts/delete/{id} | /posts → 게시글 전체 목록 |

> 회원 관련
> 

| 기능 | Method | URL | redirect |
| --- | --- | --- | --- |
| 회원 가입 폼 | GET | /users/add |  |
| 회원 가입 | POST | /users/add | Index Page |
| 내 정보 조회 | GET | /users/{id} |  |
| 내 정보 수정 폼 | GET | /users/edit/{id} |  |
| 내 정보 수정 | POST | /users/edit/{id} | /users/{id} → 내 정보 조회 |
| 로그인 폼 | GET | /login |  |
| 로그인 | POST | /login | 로그인 전 방문한 페이지 지로 이동 |
| 로그아웃 | GET | /logout |  |

> 댓글 관련
>

| 기능 | Method | URL | Redirect |
| --- | --- | --- | --- |
| 댓글 등록 | Post | /comments | /post/{id} → 내정보 조회 |