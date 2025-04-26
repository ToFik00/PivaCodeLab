package org.piva.codelab.service.security.oauth2;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public abstract class Oauth2UserDetails implements OAuth2AuthenticatedPrincipal {
    @Override
    public abstract String getName();

    public abstract String getEmail();

    @Override
    public <A> A getAttribute(String name) {
        return OAuth2AuthenticatedPrincipal.super.getAttribute(name);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return Map.of();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
}
