package com.nao4j.subtrol.config

import com.nao4j.subtrol.security.GenerateTokenForUserFilter
import com.nao4j.subtrol.security.VerifyTokenFilter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy.STATELESS
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
class SecurityConfiguration : WebSecurityConfigurerAdapter() {

    @Value("\${SECRET}")
    private lateinit var secret: String

    @Value("\${VALIDITY_TIME_MS:7200000}")
    private lateinit var validityTimeMs: String

    override fun configure(http: HttpSecurity) {
        super.configure(http)
        http.exceptionHandling()
                .and().anonymous()
                .and().csrf().disable()
                .addFilterBefore(VerifyTokenFilter(secret), UsernamePasswordAuthenticationFilter::class.java)
                .addFilterBefore(
                        GenerateTokenForUserFilter("/auth", authenticationManager(), secret, validityTimeMs.toLong()),
                        UsernamePasswordAuthenticationFilter::class.java
                )
                .authorizeRequests().anyRequest().authenticated()
                .and().sessionManagement().sessionCreationPolicy(STATELESS)
    }

}
