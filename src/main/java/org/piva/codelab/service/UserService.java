package org.piva.codelab.service;

import org.piva.codelab.dto.SignUpDto;
import org.piva.codelab.model.User;
import org.piva.codelab.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService { //TODO: Разделить сервис на AuthService и другие?
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User createUser(SignUpDto signUpDto) {
        userRepository.findByUsername(signUpDto.getUsername()).ifPresent(user -> {
           throw new RuntimeException("Username already exists"); // TODO: Создать отдельный exception
        });
        userRepository.findByEmail(signUpDto.getEmail()).ifPresent(user -> {
            throw new RuntimeException("Email already exists"); // TODO: Создать отдельный exception
        });
        return userRepository.save(User.builder()
                .email(signUpDto.getEmail())
                .username(signUpDto.getUsername())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .emailVerified(false)
                .build());
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByUsername(username)
                .map(user -> org.springframework.security.core.userdetails.User.builder()
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .roles(user.getRoles().stream().map(String::valueOf).toArray(String[]::new))
                        .build()
                )
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
    }
}
