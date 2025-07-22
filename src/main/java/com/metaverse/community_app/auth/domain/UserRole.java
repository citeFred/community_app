package com.metaverse.community_app.auth.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Getter
@RequiredArgsConstructor
public enum UserRole implements GrantedAuthority {
    ROLE_USER("USER", "일반 사용자"),
    ROLE_ADMIN("ADMIN", "관리자"),
    ROLE_GUEST("GUEST", "게스트");

    private final String authority;
    private final String description;

    @Override
    public String getAuthority() {
        return this.authority;
    }
}