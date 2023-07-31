package com.example.t2s.demande;

import java.util.List;

public interface DemandeReinitService {
    DemandeReinitMDP save(DemandeReinitMDP demandeReinitMDP) throws Exception;
    List<DemandeReinitMDP> findAll() throws Exception;
    void deleteById(Long id) throws Exception;
}
