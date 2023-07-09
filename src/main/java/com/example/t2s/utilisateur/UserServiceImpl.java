package com.example.t2s.utilisateur;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public Optional<User> findById(String username) {
        return userRepository.findById(username);
    }
    @Override
    public ApplicationUserDetails getPrincipal(){
        ApplicationUserDetails user = null;
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof ApplicationUserDetails){
            user =(ApplicationUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        return user;
    }
    @Override
    public User save(User user) throws Exception {
        try {
            return userRepository.save(user);
        }catch (Exception e){
            throw new Exception("erreur d'inscription");
        }
    }
    @Override
    public User getPrincipalUser() throws Exception {
        Optional<User> user = findById(getPrincipal().getUsername());
        if (user.isPresent())
            return user.get();
        else
            throw new Exception("Utilisateur n'existe pas");
    }
}