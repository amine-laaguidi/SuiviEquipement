package com.example.t2s.utilisateur;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class UserConfig {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Bean
    CommandLineRunner init_users() {
        return args -> {
            User user = userRepository.save(
                    new User(
                            "Utili sateur",
                            "user@gmail.com",
                            passwordEncoder.encode("utilisateur"),
                            true,
                            true,
                            true,
                            false,
                            "ROLE_USER")
            );
            User admin = userRepository.save(
                    new User(
                        "Administrateur",
                        "admin@gmail.com",
                        passwordEncoder.encode("adminadmin"),
                        true,
                        true,
                        true,
                        true,
                        "ROLE_USER")
            );
        };
    }
}