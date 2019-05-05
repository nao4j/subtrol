package com.nao4j.subtrol.security;

import io.jsonwebtoken.Jwts;
import org.apache.commons.io.IOUtils;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static io.jsonwebtoken.security.Keys.hmacShaKeyFor;
import static java.lang.System.currentTimeMillis;
import static java.util.stream.Collectors.joining;
import static javax.servlet.http.HttpServletResponse.SC_OK;

public class GenerateTokenForUserFilter extends AbstractAuthenticationProcessingFilter {

    private final AuthenticationManager authenticationManager;
    private final String secret;
    private final Long validityTimeMs;

    public GenerateTokenForUserFilter(
            final String defaultFilterProcessesUrl,
            final AuthenticationManager authenticationManager,
            final String secret,
            final Long validityTimeMs
    ) {
        super(defaultFilterProcessesUrl);
        this.authenticationManager = authenticationManager;
        this.secret = secret;
        this.validityTimeMs = validityTimeMs;
    }

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response)
            throws IOException {
        try {
            final var jsonString = IOUtils.toString(request.getInputStream(), "UTF-8");
            final var userJSON = new JSONObject(jsonString);
            final var username = userJSON.getString("username");
            final var password = userJSON.getString("password");
            final var authToken = new UsernamePasswordAuthenticationToken(username, password);
            return authenticationManager.authenticate(authToken);
        } catch (final JSONException | AuthenticationException e) {
            throw new AuthenticationServiceException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain chain,
            final Authentication authToken
    ) throws IOException {
        SecurityContextHolder.getContext().setAuthentication(authToken);

        final var tokenUser = (UserDetailsImpl) authToken.getPrincipal();
        final var tokenString = createTokenForUser(tokenUser);

        response.setStatus(SC_OK);
        response.getWriter().write(tokenString);
        response.getWriter().flush();
        response.getWriter().close();
    }

    private String createTokenForUser(final UserDetailsImpl user) {
        return Jwts.builder()
                .setExpiration(new Date(currentTimeMillis() + validityTimeMs))
                .setSubject(user.getUsername())
                .claim("userId", user.getId())
                .claim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(joining(",")))
                .signWith(hmacShaKeyFor(secret.getBytes()))    // todo: bad security solution
                .compact();
    }

}
