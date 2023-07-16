package com.example.t2s.certificat;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CertificatRepository extends JpaRepository<Certificat,Long> {
    Optional<Certificat> findCertificatByNumero(String numero) throws Exception;
    Optional<Certificat> findCertificatByNumeroAndIdCNot(String numero,Long idC) throws Exception;
}
