package com.metaverse.community_app.auth.handler;

import com.metaverse.community_app.auth.domain.PrincipalDetails;
import com.metaverse.community_app.auth.util.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;

    // OAuth2 로그인 성공 후 처리
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 인증된 PrincipalDetails (우리 시스템의 UserDetails) 객체를 가져옵니다.
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

        // JWT Access Token 생성
        String accessToken = jwtUtil.generateToken(principalDetails.getUsername());

        // 클라이언트로 JWT를 전달하는 방식은 여러가지가 있습니다.
        // 1. JWT를 URL 쿼리 파라미터로 포함하여 클라이언트(프론트엔드) 페이지로 리디렉션
        // 2. JWT를 응답 바디에 담아 JSON으로 반환 (SPA에서 선호)

        // 여기서는 1번 방식을 사용 (가장 일반적인 OAuth2 연동 방식)
        // 프론트엔드 URL에 JWT를 포함하여 리디렉션
        String targetUrl = "/oauth2/success"; // 클라이언트의 특정 URL (프론트엔드 라우팅에 따라 변경)
        String encodedAccessToken = URLEncoder.encode(accessToken, "UTF-8");
        String finalRedirectUrl = targetUrl + "?accessToken=" + encodedAccessToken;

        // 클라이언트(프론트엔드)에서 JWT를 추출할 수 있도록 리다이렉트
        response.sendRedirect(finalRedirectUrl);
    }
}