package com.example.t2s.certificat;

import com.example.t2s.accessoire.Accessoire;
import com.example.t2s.equipement.Equipement;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface CertificatService {
    Certificat save(Certificat certificat, MultipartFile file) throws Exception;
    void deleteById(Long idC) throws Exception;
    Optional<Certificat> findById(Long idC) throws Exception;
    boolean numeroExist(String numero,Long idC) throws Exception;
    boolean numeroExist(String numero) throws Exception;
    List<Certificat> findAll() throws Exception;
    void utiliser(Long idC, Equipement equipement) throws Exception;
}
