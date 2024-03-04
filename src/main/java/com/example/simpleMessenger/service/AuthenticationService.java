package com.example.simpleMessenger.service;

import com.example.simpleMessenger.dtos.UserDto;
import com.example.simpleMessenger.dtos.request.UserRequest;
import com.example.simpleMessenger.dtos.response.AuthenticationResponse;
import com.example.simpleMessenger.entity.User;
import com.example.simpleMessenger.exceptions.UserAlreadyExistsException;
import com.example.simpleMessenger.exceptions.UserDoesNotExistException;
import com.example.simpleMessenger.exceptions.UserNotLoggedInException;
import com.example.simpleMessenger.exceptions.UsernameOrPasswordIncorrectException;
import com.example.simpleMessenger.repository.UserRepository;
import com.example.simpleMessenger.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtService;
    private final AuthenticationManager authenticationManager;

    public void checkIfAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) throw new UserNotLoggedInException();
    }

    public AuthenticationResponse login(UserRequest request) {
        var userOptional = userRepository.findByUsername(request.getUsername());
        if(userOptional.isEmpty() ||
                !passwordEncoder.matches(request.getPasscode(), userOptional.get().getPasscode()))
            throw new UsernameOrPasswordIncorrectException();
        User user = userOptional.get();
        var jwtToken = jwtService.generateToken(user);
        userRepository.revokeAllUserTokens(user);
        userRepository.saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    public void logout(UserRequest request) {
        Optional<User> userOptional = userRepository.findByUsername(request.getUsername());
        if(userOptional.isEmpty()) throw new UserDoesNotExistException(request.getUsername());
        userRepository.revokeAllUserTokens(userOptional.get());
        SecurityContextHolder.clearContext();
    }

    public UserDto signupUser(UserRequest userRequest) {
        Optional<User> userOptional = userRepository.findByUsername(userRequest.getUsername());
        if(userOptional.isPresent()) throw new UserAlreadyExistsException();
        User user = User.builder()
                .username(userRequest.getUsername())
                .passcode(passwordEncoder.encode(userRequest.getPasscode()))
                .build();
        user = userRepository.createUser(user);
        return new UserDto(user.getUsername());
    }
}
