package com.example.t2s.utilisateur;

public interface ApplicationUserDao {
    ApplicationUserDetails getUserByUsername(String username);
}
