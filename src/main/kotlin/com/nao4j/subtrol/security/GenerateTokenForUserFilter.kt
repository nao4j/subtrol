package com.nao4j.subtrol.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.apache.commons.io.IOUtils
import org.springframework.boot.configurationprocessor.json.JSONException
import org.springframework.boot.configurationprocessor.json.JSONObject
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import java.io.IOException
import java.util.Date
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletResponse.SC_OK

class GenerateTokenForUserFilter(
        defaultFilterProcessesUrl: String,
        authenticationManager: AuthenticationManager,
        private val secret: String,
        private val validityTimeMs: Long
) : AbstractAuthenticationProcessingFilter(defaultFilterProcessesUrl) {

    init {
        this.authenticationManager = authenticationManager
    }

    @Throws(AuthenticationException::class, IOException::class, ServletException::class, JSONException::class)
    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        try {
            val jsonString = IOUtils.toString(request.inputStream, "UTF-8")
            val userJSON = JSONObject(jsonString)
            val username = userJSON.getString("username")
            val password = userJSON.getString("password")
            val authToken = UsernamePasswordAuthenticationToken(username, password)
            return authenticationManager.authenticate(authToken)
        } catch (e: JSONException) {
            throw AuthenticationServiceException(e.message)
        } catch (e: AuthenticationException) {
            throw AuthenticationServiceException(e.message)
        }
    }

    @Throws(IOException::class, ServletException::class)
    override fun successfulAuthentication(
            req: HttpServletRequest,
            res: HttpServletResponse,
            chain: FilterChain,
            authToken: Authentication
    ) {
        SecurityContextHolder.getContext().authentication = authToken

        val tokenUser = authToken.principal as UserDetailsImpl
        val tokenString = createTokenForUser(tokenUser)

        res.status = SC_OK
        res.writer.write(tokenString)
        res.writer.flush()
        res.writer.close()
    }


    private fun createTokenForUser(user: UserDetailsImpl): String {
        return Jwts.builder()
                .setExpiration(Date(System.currentTimeMillis() + validityTimeMs))
                .setSubject(user.username)
                .claim("userId", user.id)
                .claim("roles", user.authorities.map {it.authority}.joinToString(separator = ","))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact()
    }

}
