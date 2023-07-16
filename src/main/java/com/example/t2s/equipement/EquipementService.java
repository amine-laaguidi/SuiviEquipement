package com.example.t2s.equipement;

import com.example.t2s.certificat.Certificat;

import java.util.List;
import java.util.Optional;

public interface EquipementService {
    Equipement save(Equipement equipement) throws Exception;
    void deleteById(Long idE) throws Exception;
    Equipement findById(Long idE) throws Exception;
    List<Equipement> findAll() throws Exception;
}
