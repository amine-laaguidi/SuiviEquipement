package com.example.t2s.certificat;

import com.example.t2s.equipement.Equipement;
import com.example.t2s.equipement.EquipementService;
import com.example.t2s.file.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class CertificatServiceImpl implements CertificatService {
    private final CertificatRepository certificatRepository;
    private final FileService fileService;
    @Override
    public Certificat save(Certificat certificat, MultipartFile file) throws Exception {
        if(certificat.getIdC()==null){
            if(numeroExist(certificat.getNumero()))
                throw new Exception("Numéro déja utilisé");
            if(!file.isEmpty())
                certificat.setPdfPath(fileService.saveFile(file));
            else
                throw new Exception("L'ajout d'un fichier pdf est obligatoire");
        }else{
            if( numeroExist(certificat.getNumero(),certificat.getIdC()))
                throw new Exception("Numéro déja utilisé");
            if(!file.isEmpty()){
                fileService.deleteFile(certificat.getPdfPath());
                certificat.setPdfPath(fileService.saveFile(file));
            }
        }
        try {
            return certificatRepository.save(certificat);
        } catch (Exception e) {
            throw new Exception("Impossible d'jouter ce certificat");
        }
    }
    @Override
    public void utiliser(Long idC, Equipement equipement) throws Exception{
        try {
            for (Certificat certificat:equipement.getCertificats()){
                if(certificat.getActive()){
                    certificat.setActive(false);
                    certificatRepository.save(certificat);
                }
                if(certificat.idC==idC){
                    certificat.setActive(true);
                    certificatRepository.save(certificat);
                }
            }
        } catch (Exception e) {
            throw new Exception("Impossible d'utiliser ce certificat");
        }
    }
    @Override
    public void deleteById(Long idC) throws Exception {
        try {
            fileService.deleteFile(certificatRepository.findById(idC).get().getPdfPath());
            certificatRepository.deleteById(idC);
        } catch (Exception e) {
            throw new Exception("Impossible de supprimer ce certificat");
        }
    }

    @Override
    public Optional<Certificat> findById(Long idC) throws Exception {
        try {
            return certificatRepository.findById(idC);
        } catch (Exception e) {
            throw new Exception("Impossible de trouver ce certificat");
        }
    }

    @Override
    public boolean numeroExist(String numero,Long idC) throws Exception {
            return certificatRepository.findCertificatByNumeroAndIdCNot(numero,idC).isPresent();
    }

    @Override
    public boolean numeroExist(String numero) throws Exception {
        return certificatRepository.findCertificatByNumero(numero).isPresent();
    }

    @Override
    public List<Certificat> findAll() throws Exception {
        try {
            return certificatRepository.findAll();
        } catch (Exception e) {
            throw new Exception("Impossible de trouver les certificats");
        }
    }
}
