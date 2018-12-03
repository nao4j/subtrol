package com.nao4j.subtrol.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class UserDetailsImpl(
    val id: String,
    private val email: String,
    private val password: String,
    private val roles: Collection<GrantedAuthority>
) : UserDetails {

    override fun getUsername(): String = email

    override fun getPassword(): String = password

    override fun getAuthorities(): Collection<GrantedAuthority> = roles

    override fun isEnabled(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

}
