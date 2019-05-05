package com.nao4j.subtrol.security;

import com.nao4j.subtrol.dto.UserCredentials;
import com.nao4j.subtrol.repository.UserCredentialsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserCredentialsRepository credentialsRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) {
        final UserCredentials credentials = credentialsRepository.findUserCredentialsByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new UserDetailsImpl(
                credentials.getId(),
                credentials.getPassword(),
                credentials.getRoles().stream().map(SimpleGrantedAuthority::new).collect(toList())
        );
    }

}
