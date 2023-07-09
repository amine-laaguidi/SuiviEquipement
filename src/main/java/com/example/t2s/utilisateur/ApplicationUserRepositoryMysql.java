package com.example.t2s.utilisateur;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository("mysqlRepo")
@RequiredArgsConstructor
public class ApplicationUserRepositoryMysql implements ApplicationUserDao{

    private final UserRepository userRepository;
    @Override
    public ApplicationUserDetails getUserByUsername(String username) {
        Optional<User> userFound = userRepository.findById(username);
        if(userFound.isEmpty())
            throw new RuntimeException("user not found");
        User user = userFound.get();
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));
        return new ApplicationUserDetails(
                user.getPassword(),
                user.getEmail(),
                authorities,
                user.isAccountNonExpired(),
                user.isAccountNonLocked(),
                user.isCredentialsNonExpired(),
                user.isEnabled()
        );
    }
}