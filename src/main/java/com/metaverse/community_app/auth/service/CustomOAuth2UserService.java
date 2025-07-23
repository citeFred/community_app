package com.metaverse.community_app.auth.service;

import com.metaverse.community_app.auth.domain.User;
import com.metaverse.community_app.auth.domain.UserRole;
import com.metaverse.community_app.auth.domain.PrincipalDetails;
import com.metaverse.community_app.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oauth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // google
        String oauth2Id = oauth2User.getName(); // Google의 sub 값

        // OAuth2User로부터 email, name(nickname) 등 속성 가져오기
        Map<String, Object> attributes = oauth2User.getAttributes();
        String email = (String) attributes.get("email");
        String nickname = (String) attributes.get("name");

        // 우리 시스템의 username (소셜 제공자_소셜 ID)
        String username = registrationId + "_" + oauth2Id;

        // DB에 해당 사용자가 있는지 확인하고, 없으면 새로 등록
        User user = userRepository.findByUsername(username)
                .orElseGet(() -> {
                    User newUser = new User(
                            username,
                            nickname,
                            passwordEncoder.encode(UUID.randomUUID().toString()), // 임시 비밀번호
                            email,
                            UserRole.ROLE_USER // 기본 역할
                    );
                    return userRepository.save(newUser);
                });

        // 변경: PrincipalDetails 생성자에 attributes도 함께 전달합니다.
        return new PrincipalDetails(user, attributes);
    }
}