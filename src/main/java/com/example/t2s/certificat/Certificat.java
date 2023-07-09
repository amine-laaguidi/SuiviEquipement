package com.example.t2s.certificat;

import com.example.t2s.equipement.Equipement;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;
@Entity @Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Certificat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idC;
    @Column(length = 50,unique = true)
    String numero;
    @Column(name = "valid_date",columnDefinition = "DATE")
    Date date;
    @Column(name = "active",columnDefinition = "BOOLEAN")
    Boolean active;
    String pdfPath;
    @ManyToOne @JoinColumn(name = "idE") @JsonBackReference
    Equipement equipement;

}
