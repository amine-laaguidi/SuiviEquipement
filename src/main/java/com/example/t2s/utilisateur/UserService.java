package com.example.t2s.utilisateur;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findById(String email) throws Exception;
    void deleteById(String email) throws Exception;
    List<User> findAll() throws Exception;
    ApplicationUserDetails getPrincipal() throws Exception;
    User save(User user) throws Exception;
    User update(User user) throws Exception;
    User updateRoleByEmail(String email,String role) throws Exception;
    User enabledByEmail(String email,Boolean enabled ) throws Exception;
    User getPrincipalUser() throws Exception;
}