package com.example.t2s.demande;

import com.example.t2s.utilisateur.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class DemandeReinitMDP {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String token;
    @Column(name = "expiration",columnDefinition = "DATE")
    Date expiration;
    @Column(name = "valide",columnDefinition = "BOOLEAN")
    Boolean valide;
    @ManyToOne @JoinColumn(name = "email") @JsonBackReference
    User user;
}
