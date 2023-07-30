package com.example.t2s.utilisateur;

import com.example.t2s.demande.DemandeReinitMDP;
import com.example.t2s.equipement.Equipement;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

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
                            "ROLE_USER",
                            new ArrayList<DemandeReinitMDP>(),
                            new ArrayList<Equipement>())
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
                        "ROLE_USER",
                            new ArrayList<DemandeReinitMDP>(),
                            new ArrayList<Equipement>())
            );
        };
    }
}