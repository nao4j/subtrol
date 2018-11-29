package com.nao4j.subtrol.security

import com.nao4j.subtrol.dto.UserCredentials
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean
import java.io.IOException
import java.time.Instant
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED

private const val AUTH_HEADER_NAME = "Authorization"
private const val TOKEN_PREFIX = "Bearer"

class VerifyTokenFilter(private val secret: String) : GenericFilterBean() {

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(req: ServletRequest, res: ServletResponse, filterChain: FilterChain) {
        val request = req as HttpServletRequest
        val response = res as HttpServletResponse
        try {
            val authentication = verifyToken(request)
            if (authentication != null) {
                SecurityContextHolder.getContext().authentication = authentication
            } else {
                SecurityContextHolder.getContext().authentication = null
            }
            filterChain.doFilter(req, res)
        } catch (e: JwtException) {
            response.status = SC_UNAUTHORIZED
        } finally {
            SecurityContextHolder.getContext().authentication = null
        }
    }

    private fun verifyToken(request: HttpServletRequest): Authentication? {
        val token = request.getHeader(AUTH_HEADER_NAME)
        if (token != null && !token.isEmpty()) {
            val user = parseUserFromToken(token.replace(TOKEN_PREFIX, "").trim { it <= ' ' })
            if (user != null) {
                return UserAuthentication(user)
            }
        }
        return null
    }

    private fun parseUserFromToken(token: String): UserCredentials? {
        val claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).body
        if (Instant.now() > claims.expiration.toInstant()) {
            return null
        }
        val userId = claims["userId"] as String?
        val username = claims.subject
        val roles: Set<String> = if (claims["roles"] != null) {
            (claims["roles"] as String).split(",").toSet()
        } else {
            emptySet()
        }
        return if (userId != null && !roles.isEmpty()) {
            UserCredentials(userId, username, "", roles)
        } else {
            null
        }
    }

}
