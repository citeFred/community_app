package com.metaverse.community_app.auth.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class PrincipalDetails implements UserDetails, OAuth2User {

    private final User user;
    // attributes는 OAuth2 로그인 시에만 필요하므로 final이 아니어도 되거나,
    // final로 유지하려면 기본 생성자에서 null 또는 빈 Map으로 초기화해야 합니다.
    private Map<String, Object> attributes;

    // 1. 일반 로그인 (UserDetailsServiceImpl)을 위한 생성자
    public PrincipalDetails(User user) {
        this.user = user;
        this.attributes = null; // 일반 로그인 사용자는 OAuth2 attributes가 없으므로 null
    }

    // 2. OAuth2 로그인 (CustomOAuth2UserService)을 위한 생성자
    public PrincipalDetails(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    // --- UserDetails 인터페이스 구현 ---
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(this.user.getUserRole());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public User getUser() {
        return user;
    }

    // --- OAuth2User 인터페이스 구현 ---
    @Override
    public Map<String, Object> getAttributes() {
        // attributes가 null일 경우 NullPointerException 방지를 위해 빈 Map을 반환하거나,
        // 필요에 따라 Null을 허용하고 호출하는 쪽에서 null 체크하도록 할 수 있습니다.
        // 여기서는 안전하게 빈 Map을 반환합니다.
        return attributes != null ? attributes : Collections.emptyMap();
    }

    @Override
    public String getName() {
        return this.user.getUsername();
    }
}