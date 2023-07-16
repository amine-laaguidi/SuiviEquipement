package com.example.t2s.utilisateur;
import lombok.*;
import javax.persistence.*;

@Entity @Getter @Setter @AllArgsConstructor
public class User {
    private String nomComplet ;
    @Id @Column(length = 30)
    private String email ;
    private String password ;
    private  boolean accountNonExpired;
    private  boolean accountNonLocked;
    private  boolean credentialsNonExpired;
    private  boolean enabled;
    private String role;
    public User() {
        role = "ROLE_USER";
        enabled = false;
        credentialsNonExpired = true;
        accountNonLocked = true;
        accountNonExpired = true;
    }


}