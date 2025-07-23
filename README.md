<p align="center">
  <a href="https://spring.io/projects/spring-boot" target="blank"><img src="https://spring.io/img/projects/spring-boot.svg" width="120" alt="Nest Logo" /></a>
</p>

# SpringBoot Community-App Sample
SpringBoot(Java) + JPA(ORM) 게시판 웹 서비스

## 🖥️ 프로젝트 소개
- 기본적인 커뮤니티의 게시판 백엔드 API 서버 구현 토이 프로젝트

## 🚧 실행 추가 설정
- `src/main/resources/application.properties` 파일 작성 필요
    ```
    # DATABASE 설정 정보
    spring.datasource.url=jdbc:mysql://localhost:3306/community
    spring.datasource.username={USERNAME}
    spring.datasource.password={PASSWORD}
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    
    # JPA 추가 설정 정보
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.properties.hibernate.show_sql=true
    spring.jpa.properties.hibernate.format_sql=true
    spring.jpa.properties.hibernate.use_sql_comments=true
    
    # 파일업로드 경로 설정 정보
    file.upload-dir=uploads

    # JWT Configs (JwtUtil)
    jwt.secret.key={BASE64EncodedKey}
    jwt.expiration.time=3600000000

    # Google SNS Login Configs
    spring.security.oauth2.client.registration.google.client-id={YOUR_GOOGLE_CLIENT_ID}
    spring.security.oauth2.client.registration.google.client-secret={YOUR_GOOGLE_CLIENT_SECRET}
    spring.security.oauth2.client.registration.google.redirect-uri=http://localhost:8080/login/oauth2/code/google
    spring.security.oauth2.client.registration.google.scope=profile,email
    spring.security.oauth2.client.registration.google.client-name=Google
    ```
- MySQL 데이터베이스 생성 필요(MySQL 8.0 로컬환경구축 필요)
  ```
  mysql -u root -p
  ```
  ```
  create database community;
  ```
- 프로젝트 실행 시 JPA가 Entity 클래스의 테이블 자동 생성 

## 🕰️ 개발 기간
* 25.7 ~ 현재

## 🧑‍🤝‍🧑 맴버구성
- 김인용 - 백엔드 : JWT 인증/인가, 게시판 기본 CRUD, 기능 추가 예정

## ⚙️ 개발 환경(Development Environments - Non-Functional Requirements)
- **MainLanguage** : `Java - JDK 17`
- **IDE** : `IntelliJ Ultimate`
- **Framework** : `SpringBoot 3.5.3`, `JPA`, `Spring Security`
- **Database** : `MySQL 8.0`
- **Server** : `TOMCAT`

## 📰 엔터티 관계 다이어그램(ERD)
<details>
  <summary>1차 설계</summary>
<img width="450" height="480" alt="스크린샷 2025-07-22 095849" src="https://github.com/user-attachments/assets/f153ba9d-a159-42d6-b1e0-7fdc791afe39" />
  
  ```
  erDiagram
    BOARD {
        long id PK "board_id"
        string title
        datetime createdAt
        datetime updatedAt
    }

    ARTICLE {
        long id PK "article_id"
        string title
        string content
        long board_id FK
        datetime createdAt
        datetime updatedAt
    }

    COMMENT {
        long id PK "comment_id"
        string content
        long article_id FK
        long parent_comment_id FK
        datetime createdAt
        datetime updatedAt
    }

    FILE {
        long id PK "file_id"
        string originalFileName
        string storedFileName
        string filePath
        long article_id FK
        datetime createdAt
        datetime updatedAt
    }

    BOARD ||--o{ ARTICLE : hasArticle
    ARTICLE ||--o{ COMMENT : hasComment
    ARTICLE ||--o{ FILE : hasFile
    COMMENT |o--o{ COMMENT : hasReply
  ```
</details>

<details>
  <img width="450" height="480" alt="스크린샷 2025-07-22 213259" src="https://github.com/user-attachments/assets/20fcd210-b6a4-42df-aa08-e567e6a284fc" />

  <summary>2차 설계(회원 테이블 추가)</summary>
  
  ```
  erDiagram
      USER {
          long id PK "user_id"
          string username UK
          string nickname
          string password
          string email UK
          enum user_role
          datetime created_at
          datetime modified_at
      }
  
      BOARD {
          long id PK "board_id"
          string title
          datetime created_at
          datetime modified_at
      }
  
      ARTICLE {
          long id PK "article_id"
          string title
          string content
          long board_id FK
          long user_id FK
          datetime created_at
          datetime modified_at
      }
  
      COMMENT {
          long id PK "comment_id"
          string content
          long article_id FK
          long parent_comment_id FK
          datetime created_at
          datetime modified_at
      }
  
      FILE {
          long id PK "file_id"
          string original_file_name
          string stored_file_name
          string file_path
          long article_id FK
          datetime created_at
          datetime modified_at
      }
  
      USER ||--o{ ARTICLE : creates
      BOARD ||--o{ ARTICLE : belongs_to
      ARTICLE ||--o{ COMMENT : has_comment
      ARTICLE ||--o{ FILE : has_file
      COMMENT |o--o{ COMMENT : replies_to
  ```
</details>
<details>
  <summary>3차 설계(좋아요 테이블 추가)</summary>
  
  ```
  erDiagram
      USER {
          long id PK "user_id"
          string username UK
          string nickname
          string password
          string email UK
          enum user_role
          datetime created_at
          datetime modified_at
      }
  
      BOARD {
          long id PK "board_id"
          string title
          datetime created_at
          datetime modified_at
      }
  
      ARTICLE {
          long id PK "article_id"
          string title
          string content
          long board_id FK
          long user_id FK
          datetime created_at
          datetime modified_at
      }
  
      COMMENT {
          long id PK "comment_id"
          string content
          long article_id FK
          long parent_comment_id FK
          datetime created_at
          datetime modified_at
      }
  
      FILE {
          long id PK "file_id"
          string original_file_name
          string stored_file_name
          string file_path
          long article_id FK
          datetime created_at
          datetime modified_at
      }
  
      USER ||--o{ ARTICLE : creates
      BOARD ||--o{ ARTICLE : belongs_to
      ARTICLE ||--o{ COMMENT : has_comment
      ARTICLE ||--o{ FILE : has_file
      COMMENT |o--o{ COMMENT : replies_to
  ```
</details>
<img width="450" height="480" alt="image" src="https://github.com/user-attachments/assets/84c6cb57-f496-40e3-bd73-eed7d0648afd" />


## 📌 주요 기능(Features - Functional Requirements)
### ✅ 게시판 - 관리자 권한(Admin Only)
<details>
  <summary>게시판 기능 세부 요구사항</summary>
  
  ```
  1. 시스템은 여러 종류의 게시판을 생성, 조회, 수정, 삭제할 수 있도록 지원해야 한다.
  2. 게시판의 생성, 수정, 삭제 기능은 시스템 관리자 권한을 가진 사용자만 수행할 수 있도록 접근이 제어되어야 한다.
  3. 게시판 목록 조회 및 상세 조회 기능은 로그인한 사용자라면 누구나 접근할 수 있어야 한다.
  ```
</details>

- [x] 게시판 구분 생성, 읽기, 수정, 삭제(CRUD)

### ✅ 게시글 - 회원 제한(Authorized User Only)
<details>
  <summary>게시글 기능 세부 요구사항</summary>

  ```
  1. 사용자는 특정 게시판에 게시글을 작성, 조회, 수정, 삭제할 수 있어야 한다.
  2. 게시글 작성 시, 작성자는 현재 로그인한 사용자로 자동 지정되어야 한다.
  3. 게시글의 수정 및 삭제는 해당 게시글을 작성한 본인만 가능해야 한다.
  4. 게시글 조회 시 게시글의 제목, 내용뿐만 아니라 작성자 정보, 소속 게시판, 좋아요 수, 댓글, 첨부 파일 등의 관련 정보를 함께 확인할 수 있어야 한다.
  ```
</details>

<details>
  <summary>페이징 및 무한 스크롤 세부 요구사항</summary>

  ```
  1. 게시글 목록은 페이지 단위로 조회되어야 하며, 클라이언트는 페이지 번호, 페이지당 항목 수, 정렬 기준을 지정하여 요청할 수 있어야 한다.
  2. 시스템은 요청된 페이징 정보에 따라 게시글 목록과 함께 전체 항목 수, 전체 페이지 수, 현재 페이지 번호 등의 메타데이터를 응답해야 한다.
  3. 무한 스크롤을 구현하기 위해 클라이언트가 연속적으로 다음 페이지를 요청할 수 있도록 지원한다.
  ```
</details>

- [x] 게시글 작성, 읽기, 수정, 삭제(CRUD)
- [x] 페이징처리 및 무한스크롤 기능

### ✅ 댓글, 대댓글 - 회원 제한(Authorized User Only)
<details>
  <summary>댓글 및 대댓글 기능 세부 요구사항</summary>

  ```
  1. 사용자는 게시글에 댓글을 작성, 조회, 수정, 삭제할 수 있어야 한다.
  2. 댓글에 또 다른 댓글(대댓글)을 작성할 수 있는 계층형 구조를 지원해야 한다.
  3. 댓글 작성 시, 작성자는 현재 로그인한 사용자로 자동 지정되어야 한다.
  4. 댓글의 수정 및 삭제는 해당 댓글을 작성한 본인만 가능해야 한다.
  5. 대댓글은 해당 부모 댓글과 동일한 게시글에 속해야 한다.
  ```
</details>

- [x] 댓글 작성, 읽기, 수정, 삭제(CRUD)

### ✅ 좋아요(Like) - 회원 제한(Authorized User Only)
<details>
  <summary>좋아요 기능 세부 요구사항</summary>

  ```
  1. 사용자는 게시글에 '좋아요'를 누르거나 취소할 수 있어야 한다.
  2. 게시글별로 총 '좋아요' 수를 계산하여 표시할 수 있어야 한다.
  3. 동일 사용자가 동일 게시글에 여러 번 '좋아요'를 누를 수 없어야 한다 (토글 기능).
  4. '좋아요' 기능은 로그인한 사용자만 사용할 수 있어야 한다.
  ```
</details>

- [x] 게시글 좋아요 기능 구현(CRUD)
- [x] 댓글 좋아요 기능 구현(CRUD)

### ✅ 파일 첨부 - 회원 제한(Authorized User Only)
<details>
  <summary>게시글 파일 첨부 세부 요구사항</summary>

  ```
  1. 사용자는 게시글 작성 시 하나 이상의 파일을 첨부할 수 있어야 한다.
  2. 첨부된 파일은 서버에 안전하게 저장되고 관리되어야 한다.
  3. 게시글 조회 시 첨부된 파일 목록을 확인할 수 있어야 하며, 해당 파일을 다운로드할 수 있어야 한다.
  4. 첨부 파일은 게시글과 함께 관리(생성, 수정, 삭제)되어야 한다.
  ```
</details>

- [x] 게시글 파일 첨부, 읽기, 수정, 삭제(CRUD)

### ✅ 로그인 - 비회원 가능(Public)
<details>
  <summary>로그인 기능 세부 요구사항</summary>

  ```
  1. 사용자는 아이디와 비밀번호를 통해 시스템에 로그인할 수 있어야 한다.
  2. 로그인 성공 시, 클라이언트는 API 요청 시 인증에 사용할 JWT(JSON Web Token)를 발급받아야 한다.
  3. 시스템은 JWT를 검증하여 유효한 사용자인지 판단해야 한다.
  4. JWT는 세션 상태를 서버에 유지하지 않는 무상태(stateless) 방식으로 인증을 처리한다.
  5. 비밀번호는 안전하게 암호화되어 관리되어야 한다.
  6. 클라이언트(웹 또는 모바일 앱)는 로그인 응답 바디에서 JWT를 받아 저장하고, 모든 후속 요청 헤더에 JWT를 포함하여 전송해야 한다.
  ```
</details>
<details>
  <summary>역할 기반 접근 제어 세부 요구사항</summary>

  ```
  1. 시스템은 사용자에게 관리자(`ADMIN`), 일반 사용자(`USER`)와 같은 고정된 역할을 부여하고 관리할 수 있어야 한다.
  2. 로그인한 사용자만이 대부분의 서비스 기능에 접근할 수 있도록 제어해야 한다.
  3. 특정 기능(예: 게시판 생성, 수정, 삭제)은 관리자 역할의 사용자만 접근할 수 있도록 제어되어야 한다.
  4. 역할에 따른 접근 제어는 시스템의 URL 패턴 수준과 개별 메서드 호출 수준 모두에서 적용 가능해야 한다.
  ```
</details>
<details>
  <summary>로그인된 회원과 게시글 연관관계 세부 요구사항</summary>

  ```
1. 게시글은 반드시 이를 작성한 사용자와 연결되어야 한다.
2. 게시글을 조회할 때, 해당 게시글을 작성한 사용자의 정보(ID, 닉네임)를 함께 확인할 수 있어야 한다.
3. 게시글의 생성, 수정, 삭제와 같은 작업 시 현재 로그인한 사용자가 유효한 작성자인지 자동으로 확인하고 연결해야 한다.
  ```
</details>

- [x] Spring Security + JWT 필터체인 구현체를 통한 로그인 구현
- [x] 역할기반권한제어(Role-Based Access Control)에 따른 엔드포인트 접근 제어
- [x] 로그인된 회원의 게시글 연관관계 설정
- [x] Google 등 소셜 API 로그인

### ✅ 회원가입 - 비회원 가능(Public)
<details>
  <summary>회원가입 기능 세부 요구사항</summary>

  ```
1. 새로운 사용자는 아이디, 비밀번호, 닉네임, 이메일을 제공하여 회원가입할 수 있어야 한다.
2. 아이디, 이메일, 닉네임은 시스템 내에서 중복될 수 없으며, 중복 시 적절한 오류 메시지를 제공해야 한다.
3. 사용자 비밀번호는 데이터베이스에 저장되기 전에 강력한 해싱 알고리즘을 사용하여 안전하게 암호화되어야 한다.
4. 회원가입된 사용자에게는 기본적으로 일반 사용자 역할을 부여해야 한다.
  ```
</details>

- [x] Bcrypt Password 비밀번호 해싱을 통한 암호화

### ✅ Spring AI 활용 AI 챗봇 - 회원 제한(Authorized User Only)
<details>
  <summary>AI 챗봇 기능 세부 요구사항</summary>

  ```
1. 로그인한 사용자(회원)는 AI 챗봇과 대화할 수 있어야 한다.
2. 사용자는 챗봇에게 텍스트 기반 질문을 전송할 수 있어야 한다.
3. 챗봇은 사용자의 질문에 대한 응답을 텍스트 형태로 제공해야 한다.
4. 챗봇과의 대화는 Spring AI 프레임워크를 활용하여 OpenAI의 특정 모델(예: GPT-3.5 Turbo)과 연동되어야 한다.
5. 챗봇과의 상호작용은 RESTful API 엔드포인트를 통해 이루어져야 한다.
6. 챗봇과의 대화 내용은 향후 특정 목적(예: 기록, 학습)을 위해 저장될 수 있는 구조를 고려한다.
  ```
</details>

- [ ] Open AI 외부 API 요청 및 응답 구조 구현
