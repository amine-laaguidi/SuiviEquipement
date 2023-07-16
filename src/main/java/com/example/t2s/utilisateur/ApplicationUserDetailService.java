package com.example.t2s.utilisateur;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserDetailService implements UserDetailsService {
    private final ApplicationUserDao applicationUserDao;

    public ApplicationUserDetailService
            (@Qualifier("mysqlRepo") ApplicationUserDao applicationUserDao) {
        this.applicationUserDao = applicationUserDao;
    }

    @Override
    public ApplicationUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return applicationUserDao.getUserByUsername(username);
    }
}