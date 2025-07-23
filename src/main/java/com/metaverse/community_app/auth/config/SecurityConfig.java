package com.metaverse.community_app.auth.config;

import com.metaverse.community_app.auth.filter.JwtAuthenticationFilter;
import com.metaverse.community_app.auth.handler.OAuth2LoginSuccessHandler;
import com.metaverse.community_app.auth.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserDetailsService userDetailsService;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2LoginSuccessHandler oauth2LoginSuccessHandler;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
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
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // 인가(Authorization, 엔드포인트의 접근 권한) 규칙 정의:
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/auth/**", "/login/oauth2/**").permitAll()
                        .anyRequest().authenticated()
                )

                // 요청 헤더의 JWT를 검증하고 SecurityContext에 인증 정보를 설정하는 역할
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

                // OAuth2 로그인 설정 추가
                .oauth2Login(oauth2 -> oauth2
                        // OAuth2 로그인 성공 후 처리할 서비스 지정
                        .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
                        // OAuth2 로그인 성공 후 JWT 발행 및 리디렉션 처리 핸들러 지정
                        .successHandler(oauth2LoginSuccessHandler)
                );
        return http.build();
    }
}