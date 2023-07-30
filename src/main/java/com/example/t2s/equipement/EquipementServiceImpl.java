package com.example.t2s.equipement;

import com.example.t2s.certificat.Certificat;
import com.example.t2s.certificat.CertificatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class EquipementServiceImpl implements EquipementService {
    private final EquipementRepository equipementRepository;
    private final CertificatService certificatService;

    @Override
    public Equipement save(Equipement equipement) throws Exception {
        try{
            return equipementRepository.save(equipement);
        }catch (Exception e){
            throw new Exception("Impossible d'ajouter cet equipement");
        }
    }


    @Override
    public void deleteById(Long idE) throws Exception {
//        for (Certificat certificat:equipementRepository.findById(idE).get().certificats)
//            certificatService.deleteById(certificat.getIdC());
        equipementRepository.deleteById(idE);
    }

    @Override
    public Equipement findById(Long idE) throws Exception {
        return equipementRepository.findById(idE).orElseThrow(() -> new Exception("Impossible de trouver cet equipement"));
    }

    @Override
    public List<Equipement> findAll() throws Exception {
        try {
            return equipementRepository.findAll();
        } catch (Exception e) {
            throw new Exception("Impossible de trouver les equipements");
        }
    }
}
