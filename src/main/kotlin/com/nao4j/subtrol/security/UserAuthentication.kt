package com.nao4j.subtrol.security

import com.nao4j.subtrol.dto.UserCredentials
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

class UserAuthentication(private val user: UserCredentials) : Authentication {

    private var authenticated = true

    override fun getName(): String {
        return user.email
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return user.roles.map { SimpleGrantedAuthority(it) }
    }

    override fun getCredentials(): Any {
        return user.password
    }

    override fun getDetails(): UserCredentials {
        return user
    }

    override fun getPrincipal(): Any {
        return user.email
    }

    override fun isAuthenticated(): Boolean {
        return authenticated
    }

    override fun setAuthenticated(authenticated: Boolean) {
        this.authenticated = authenticated
    }

}
