package com.example.t2s.certificat;

import com.example.t2s.accessoire.Accessoire;

import java.util.List;
import java.util.Optional;

public interface CertificatService {
    Certificat save(Certificat certificat) throws Exception;
    Certificat deleteById(Long idC) throws Exception;
    Optional<Certificat> findById(Long idC) throws Exception;
    List<Certificat> findAll() throws Exception;
}
