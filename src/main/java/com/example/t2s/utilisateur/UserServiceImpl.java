package com.example.t2s.utilisateur;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public void deleteById(String email) throws Exception {
        try {
            userRepository.deleteById(email);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> findAll() throws Exception {
        List<User> users = userRepository.findAll();
        for(User user:users){
            user.setPassword("");
        }
        return users;
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
        if (userRepository.findById(user.getEmail()).isPresent())
            throw new Exception("Email déja utilisé");
        try {
            return userRepository.save(user);
        }catch (Exception e){
            throw new Exception("erreur d'inscription");
        }
    }
    @Override
    public User update(User user) throws Exception {
        try {
            return userRepository.save(user);
        }catch (Exception e){
            throw new Exception("erreur d'inscription");
        }
    }
    @Override
    public User updateRoleByEmail(String email,String role) throws Exception {
        try {
            User user = userRepository.findById(email).get();
            user.setRole(role);
            return userRepository.save(user);
        } catch (Exception e) {
            throw new Exception("Impossible de modifier le role");
        }
    }

    @Override
    public User enabledByEmail(String email, Boolean enabled) throws Exception {
        try {
            User user = userRepository.findById(email).get();
            user.setEnabled(enabled);
            return userRepository.save(user);
        } catch (Exception e) {
            throw new Exception("Impossible d'activer cet utilisateur");
        }
    }

    @Override
    public User getPrincipalUser() throws Exception {
        if (getPrincipal()==null)
            return null;
        Optional<User> user = findById(getPrincipal().getUsername());
        if (user.isPresent())
            return user.get();
        else
            throw new Exception("Utilisateur n'existe pas");
    }
}