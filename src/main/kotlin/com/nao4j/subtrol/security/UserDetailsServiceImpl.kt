package com.nao4j.subtrol.security

import com.nao4j.subtrol.repository.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(private val userRepository: UserRepository) : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        val credentials = userRepository.findUserCredentialsByEmail(username)
                ?: throw UsernameNotFoundException("User not found")
        return UserDetailsImpl(
                credentials.id,
                credentials.email,
                credentials.password,
                credentials.roles.map { SimpleGrantedAuthority(it) }
        )
    }

}
