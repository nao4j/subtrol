package com.nao4j.subtrol.security;

import com.nao4j.subtrol.dto.UserCredentials;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

import static io.jsonwebtoken.security.Keys.hmacShaKeyFor;
import static java.time.Instant.now;
import static java.util.Collections.emptySet;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

@RequiredArgsConstructor
public class VerifyTokenFilter extends GenericFilterBean {

    private static final String AUTH_HEADER_NAME = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer";

    private final String secret;

    @Override
    public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain filterChain)
            throws IOException, ServletException {
        final var request = (HttpServletRequest) req;
        final var response = (HttpServletResponse) res;
        try {
            final var authentication = verifyToken(request);
            if (authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                SecurityContextHolder.getContext().setAuthentication(null);
            }
            filterChain.doFilter(req, res);
        } catch (final JwtException e) {
            response.setStatus(SC_UNAUTHORIZED);
        } finally {
            SecurityContextHolder.getContext().setAuthentication(null);
        }
    }

    private Authentication verifyToken(final HttpServletRequest request) {
        final var token = request.getHeader(AUTH_HEADER_NAME);
        if ((token != null) && !token.isEmpty()) {
            final var user = parseUserFromToken(token.replace(TOKEN_PREFIX, "").trim());
            if (user != null) {
                return new UserAuthentication(user);
            }
        }
        return null;
    }

    private UserCredentials parseUserFromToken(final String token) {
        final var claims = Jwts.parser()
                .setSigningKey(hmacShaKeyFor(secret.getBytes()))   // todo: bad security solution
                .parseClaimsJws(token)
                .getBody();
        if (now().isAfter(claims.getExpiration().toInstant())) {
            return null;
        }
        final var userId = (String) claims.get("userId");
        final Set<String> roles;
        if (claims.get("roles") != null) {
            roles = Set.of(((String) claims.get("roles")).split(","));
        } else {
            roles = emptySet();
        }
        if ((userId != null) && !roles.isEmpty()) {
            return new UserCredentials(userId, "", roles);
        } else {
            return null;
        }
    }

}
