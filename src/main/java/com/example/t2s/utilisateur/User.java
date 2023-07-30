package com.example.t2s.utilisateur;
import com.example.t2s.demande.DemandeReinitMDP;
import com.example.t2s.equipement.Equipement;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import javax.persistence.*;
import java.util.List;

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
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    List<DemandeReinitMDP> demandeReinitMDPS;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    List<Equipement> equipements;
    public User() {
        role = "ROLE_USER";
        enabled = false;
        credentialsNonExpired = true;
        accountNonLocked = true;
        accountNonExpired = true;
    }


}