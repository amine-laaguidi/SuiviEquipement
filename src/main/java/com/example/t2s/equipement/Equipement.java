package com.example.t2s.equipement;

import com.example.t2s.accessoire.Accessoire;
import com.example.t2s.certificat.Certificat;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import javax.persistence.*;
import java.util.List;

@Entity @Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Equipement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idE;
    String designation;
    @Column(length = 10)
    String classe;
    Integer gmdn;
    @OneToMany(mappedBy = "equipement",cascade = CascadeType.ALL) @JsonBackReference
    List<Accessoire> accessoires;
    @OneToMany(mappedBy = "equipement",cascade = CascadeType.ALL) @JsonBackReference
    List<Certificat> certificats;
}
