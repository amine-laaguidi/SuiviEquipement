package com.example.t2s.accessoire;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class AccessoireServiceImpl implements AccessoireService {
    private final AccessoireRepository accessoireRepository;
    @Override
    public Accessoire save(Accessoire accessoire) throws Exception {
        if(refExist(accessoire.getRef()))
            throw new Exception("référence déja utilisée");
        try{
            return accessoireRepository.save(accessoire);
        } catch (Exception e) {
            throw new Exception("impossible d'ajouter cet accessiore");
        }
    }

    @Override
    public void deleteById(Long idA) throws Exception {
        try {
            accessoireRepository.deleteById(idA);
        } catch (Exception e) {
            throw new Exception("impossible de supprimer cet accessoire");
        }
    }

    @Override
    public Optional<Accessoire> findById(Long idA) throws Exception {
        try {
            return accessoireRepository.findById(idA);
        } catch (Exception e) {
            throw new Exception("impossible de trouver cet accessoire");
        }
    }

    @Override
    public boolean refExist(String ref) throws Exception {
        return accessoireRepository.findAccessoireByRef(ref).isPresent();
    }

    @Override
    public List<Accessoire> findAll() throws Exception {
        try {
            return accessoireRepository.findAll();
        } catch (Exception e) {
            throw new Exception("impossble de trouver les accessoires");
        }
    }
}
