<p align="center">
  <a href="https://spring.io/projects/spring-boot" target="blank"><img src="https://spring.io/img/projects/spring-boot.svg" width="120" alt="Nest Logo" /></a>
</p>

# SpringBoot Community-App Sample
SpringBoot(Java) + JPA(ORM) 게시판 웹 서비스


### 🖥️ 프로젝트 소개
- 기본적인 커뮤니티의 게시판 백엔드 API 서버 구현 토이 프로젝트

### 🚧 실행 추가 설정
- `src/main/resources/application` 파일 작성 필요
```.env
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

### 🕰️ 개발 기간
* 25.7 - 현재

### 🧑‍🤝‍🧑 맴버구성
- 김인용 - 백엔드 : JWT 인증/인가, 게시판 기본 CRUD, 기능 추가 예정

### ⚙️ 개발 환경
- **MainLanguage** : `Java - JDK 17`
- **IDE** : `IntelliJ Ultimate`
- **Framework** : `SpringBoot 3.5x`, `JPA`
- **Database** : `MySQL@8.0`
- **Server** : `Inner TOMCAT`

### 📌 주요 기능
#### 게시판
- [x] 게시판 구분 생성, 읽기, 수정, 삭제(CRUD)
- ex) 자유게시판, 공지게시판, ...

#### 게시글
- [x] 게시글 작성, 읽기, 수정, 삭제(CRUD)
- [ ] 페이징처리 및 무한스크롤 기능(예정)
- [ ] 게시글 좋아요(likes) 기능(예정)

#### 댓글, 대댓글
- [x] 댓글 작성, 읽기, 수정, 삭제(CRUD)
- [ ] 댓글 좋아요(likes) 기능(예정)

#### 파일 첨부
- [x] 게시글 파일 첨부, 읽기, 수정, 삭제(CRUD)
- [ ] 댓글 파일 첨부, 읽기, 수정, 삭제(CRUD)

#### 로그인
- [ ] JWT + Cookie 로그인(예정)
- [ ] 카카오 등 소셜 API 로그인(예정)

#### 회원가입
- [ ] 다음 주소 API 연동(예정)
- [ ] Bcrypt Password 해싱(예정)