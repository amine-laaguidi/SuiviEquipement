package com.example.t2s.utilisateur;

import java.util.Optional;

public interface UserService {
    Optional<User> findById(String email) throws Exception;
    ApplicationUserDetails getPrincipal() throws Exception;
    User save(User user) throws Exception;
    User updateRoleByEmail(String email,String role) throws Exception;
    User enabledByEmail(String email,Boolean enabled ) throws Exception;
    User getPrincipalUser() throws Exception;
}