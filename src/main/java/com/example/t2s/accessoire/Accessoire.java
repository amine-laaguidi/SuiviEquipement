package com.example.t2s.accessoire;

import com.example.t2s.equipement.Equipement;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@Entity @Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Accessoire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idA;
    @Column(length = 30,unique = true)
    String ref;
    String description;
    @ManyToOne @JoinColumn(name = "idE") @JsonBackReference
    Equipement equipement;
}
