package com.nao4j.subtrol.security;

import com.nao4j.subtrol.dto.UserCredentials;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;

import static java.util.stream.Collectors.toList;

@Data
public class UserAuthentication implements Authentication {

    private final UserCredentials user;
    private boolean authenticated = true;

    @Override
    public String getName() {
        return user.getId();
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return user.getRoles().stream().map(SimpleGrantedAuthority::new).collect(toList());
    }

    @Override
    public String getCredentials() {
        return user.getPassword();
    }

    @Override
    public UserCredentials getDetails() {
        return user;
    }

    @Override
    public String getPrincipal() {
        return user.getId();
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(final boolean authenticated) {
        this.authenticated = authenticated;
    }

}
