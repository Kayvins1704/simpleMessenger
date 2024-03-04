package com.example.simpleMessenger.service;

import com.example.simpleMessenger.dtos.UserDto;
import com.example.simpleMessenger.dtos.request.UserRequest;
import com.example.simpleMessenger.entity.User;
import com.example.simpleMessenger.exceptions.UserAlreadyExistsException;
import com.example.simpleMessenger.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public List<UserDto> fetchAllUsers() {
        List<User> userList = userRepository.fetchAllUsers();
        return userList.stream().map(user -> new UserDto(user.getUsername())).collect(Collectors.toList());
    }
}
