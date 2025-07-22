package com.metaverse.community_app.auth.config;

import com.metaverse.community_app.auth.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // Spring Security를 활성화하는 어노테이션
@RequiredArgsConstructor
public class SecurityConfig {
    // JwtAuthenticationFilter 주입을 위한 final 필드 추가
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // 비밀번호 인코더 (BCrypt)를 Bean으로 등록
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // AuthenticationManager를 수동 Bean으로 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // SecurityFilterChain을 수동 Bean으로 등록하여 HTTP 보안 규칙 정의
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF (Cross-Site Request Forgery) 보호 비활성화:
                // JWT와 같은 토큰 기반 인증 시스템에서는 CSRF 공격 방어가 토큰 자체에 의해 이루어지므로,
                // 서버 측에서 별도의 CSRF 토큰을 관리할 필요가 없음. (세션을 사용하지 않으므로)
                .csrf(AbstractHttpConfigurer::disable)

                // HTTP Basic 인증 비활성화:
                // JWT를 통한 인증을 사용하므로 HTTP Basic 인증은 필요 없음
                .httpBasic(AbstractHttpConfigurer::disable)

                // 시큐리티 제공 폼 로그인 비활성화:
                // 별도의 로그인 폼 대신 API를 통해 로그인하므로 기본 폼 로그인(시큐리티 제공폼)을 사용하지 않도록 설정
                .formLogin(AbstractHttpConfigurer::disable)

                // 세션 관리 설정:
                // JWT 기반 인증에서는 서버에 세션 상태를 저장하지 않는 Stateless 방식을 사용
                // 각 요청이 JWT 토큰을 통해 자체적으로 인증 정보를 포함하도록 설정
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // 인가(Authorization, 엔드포인트의 접근 권한) 규칙 정의:
                .authorizeHttpRequests(authorize -> authorize
                        // 회원가입 및 로그인 API는 인증 없이 접근을 허용
                        .requestMatchers("/api/auth/**").permitAll()
                        // 그 외의 모든 요청은 인증을 요구(==로그인상태 == jwt토큰여부)
                        // (향후 JWT 필터를 통해 인증될 예정)
                        .anyRequest().authenticated()
                )

                // JWT 필터를 UsernamePasswordAuthenticationFilter 이전에 추가합니다.
                // 이 필터는 요청 헤더의 JWT를 검증하고 SecurityContext에 인증 정보를 설정하는 역할을 합니다.
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}