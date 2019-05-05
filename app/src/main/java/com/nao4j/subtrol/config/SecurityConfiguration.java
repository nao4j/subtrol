package com.nao4j.subtrol.config;

import com.nao4j.subtrol.security.GenerateTokenForUserFilter;
import com.nao4j.subtrol.security.VerifyTokenFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static java.lang.Long.parseLong;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${SECRET}")
    private String secret;

    @Value("${VALIDITY_TIME_MS:7200000}")
    private String validityTimeMs;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        super.configure(http);
        http.exceptionHandling()
                .and().anonymous()
                .and().csrf().disable()
                .addFilterBefore(new VerifyTokenFilter(secret), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(
                        new GenerateTokenForUserFilter(
                                "/auth",
                                authenticationManagerBean(),
                                secret,
                                parseLong(validityTimeMs)
                        ),
                        UsernamePasswordAuthenticationFilter.class
                ).authorizeRequests().anyRequest().authenticated()
                .and().sessionManagement().sessionCreationPolicy(STATELESS);
    }

}
