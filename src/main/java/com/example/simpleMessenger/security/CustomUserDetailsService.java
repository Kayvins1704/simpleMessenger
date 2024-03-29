package com.example.simpleMessenger.security;

import com.example.simpleMessenger.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<com.example.simpleMessenger.entity.User> userOptional = userRepository.findByUsername(username);
        if(userOptional.isEmpty()) throw new UsernameNotFoundException("User not found with username: " + username);
        com.example.simpleMessenger.entity.User user = userOptional.get();
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPasscode(), Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }
}

