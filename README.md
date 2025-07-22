<p align="center">
  <a href="https://spring.io/projects/spring-boot" target="blank"><img src="https://spring.io/img/projects/spring-boot.svg" width="120" alt="Nest Logo" /></a>
</p>

# SpringBoot Community-App Sample
SpringBoot(Java) + JPA(ORM) 게시판 웹 서비스

### 🖥️ 프로젝트 소개
- 기본적인 커뮤니티의 게시판 백엔드 API 서버 구현 토이 프로젝트

### 🚧 실행 추가 설정
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
    ```
- MySQL 데이터베이스 생성 필요(MySQL 8.0 로컬환경구축 필요)
  ```
  mysql -u root -p
  ```
  ```
  create database community;
  ```
- 프로젝트 실행 시 JPA가 Entity 클래스의 테이블 자동 생성 

### 🕰️ 개발 기간
* 25.7 - 현재

### 🧑‍🤝‍🧑 맴버구성
- 김인용 - 백엔드 : JWT 인증/인가, 게시판 기본 CRUD, 기능 추가 예정

### ⚙️ 개발 환경(Development Environments - Non-Functional Requirements)
- **MainLanguage** : `Java - JDK 17`
- **IDE** : `IntelliJ Ultimate`
- **Framework** : `SpringBoot 3.5.3`, `JPA`, `Spring Security`
- **Database** : `MySQL 8.0`
- **Server** : `TOMCAT`

### 📰 엔터티 관계 다이어그램(ERD)
<details>
  <summary>1차 설계</summary>
<img width="450" height="480" alt="스크린샷 2025-07-22 095849" src="https://github.com/user-attachments/assets/f153ba9d-a159-42d6-b1e0-7fdc791afe39" />
</details>

<details>
  <summary>2차 설계</summary>
<img width="862" height="1192" alt="스크린샷 2025-07-22 213259" src="https://github.com/user-attachments/assets/20fcd210-b6a4-42df-aa08-e567e6a284fc" />
</details>

### 📌 주요 기능(Features - Functional Requirements)
#### 게시판 - 관리자 권한(Admin Only)
- [x] 게시판 구분 생성, 읽기, 수정, 삭제(CRUD)
- ex) 자유게시판, 공지게시판, ...

#### 게시글 - 회원 제한(Authorized User Only)
- [x] 게시글 작성, 읽기, 수정, 삭제(CRUD) - 
- [ ] 페이징처리 및 무한스크롤 기능(예정)
- [ ] 게시글 좋아요(likes) 기능(예정)

#### 댓글, 대댓글 - 회원 제한(Authorized User Only)
- [x] 댓글 작성, 읽기, 수정, 삭제(CRUD)
- [ ] 댓글 좋아요(likes) 기능(예정)

#### 파일 첨부 - 회원 제한(Authorized User Only)
- [x] 게시글 파일 첨부, 읽기, 수정, 삭제(CRUD)
- [ ] 댓글 파일 첨부, 읽기, 수정, 삭제(CRUD)

#### 로그인 - 비회원 가능(Public)
- [x] Spring Security + JWT 필터체인 구현체를 통한 로그인 구현
- [x] 역할기반권한제어(Role-Based Access Control)에 따른 엔드포인트 접근 제어
- [x] 로그인된 회원의 게시글 연관관계 설정
- [ ] 카카오 등 소셜 API 로그인(예정)

#### 회원가입 - 비회원 가능(Public)
- [x] Bcrypt Password 비밀번호 해싱을 통한 암호화
- [ ] 다음 주소 API 연동(예정)
