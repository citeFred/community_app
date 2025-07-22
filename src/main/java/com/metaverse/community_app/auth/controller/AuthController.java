package com.metaverse.community_app.auth.controller;

import com.metaverse.community_app.auth.domain.User;
import com.metaverse.community_app.auth.domain.UserRole;
import com.metaverse.community_app.auth.dto.AuthResponseDto;
import com.metaverse.community_app.auth.dto.LoginRequestDto;
import com.metaverse.community_app.auth.dto.SignUpRequestDto;
import com.metaverse.community_app.auth.repository.UserRepository;
import com.metaverse.community_app.auth.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/auth/signup")
    public ResponseEntity<String> registerUser(@Valid @RequestBody SignUpRequestDto signUpRequestDto) {
        if (userRepository.existsByUsername(signUpRequestDto.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username 사용자 계정이 사용중입니다.");
        }

        if (userRepository.existsByEmail(signUpRequestDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email 사용자 이메일이 사용중입니다.");
        }

        User user = new User(
                signUpRequestDto.getUsername(),
                signUpRequestDto.getNickname(),
                passwordEncoder.encode(signUpRequestDto.getPassword()),
                signUpRequestDto.getEmail(),
                UserRole.ROLE_USER // 역할 기본값 임시 USER 부여
        );

        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body("회원 가입 성공");
    }

    // 로그인 API
    @PostMapping("/auth/login")
    public ResponseEntity<AuthResponseDto> authenticateUser(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        try {
            // AuthenticationManager를 사용하여 Spring Security에게 username과 password를 기반으로 인증을 수행하도록 지시
            // loadUserByUsername과 passwordEncoder.matches()가 내부적으로 실행
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDto.getUsername(),
                            loginRequestDto.getPassword()
                    )
            );

            // 인증 성공 후, SecurityContextHolder에 인증 객체를 설정 (선택 사항이나 권장)
            // 이후 요청에서 SecurityContextHolder를 통해 인증 정보에 접근 가능
            // SecurityContextHolder.getContext().setAuthentication(authentication);

            // UserDetails 객체는 인증정보의 주체 (principal)가 할당 됨
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // JWT 토큰 생성
            String accessToken = jwtUtil.generateToken(userDetails.getUsername());

            // 인증 응답 DTO 반환
            return ResponseEntity.ok(new AuthResponseDto(userDetails.getUsername(), accessToken));

        } catch (BadCredentialsException e) {
            // 비밀번호가 일치하지 않거나 사용자 이름이 없는 경우
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponseDto(null, null));
        } catch (Exception e) {
            // 그 외 인증 과정에서 발생할 수 있는 오류 (UsernameNotFoundException 등)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AuthResponseDto(null, null));
        }
    }
}