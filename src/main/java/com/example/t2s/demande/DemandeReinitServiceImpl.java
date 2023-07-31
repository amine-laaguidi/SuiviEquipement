package com.example.t2s.demande;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DemandeReinitServiceImpl implements DemandeReinitService {
    private final DemandeReinitRepository demandeReinitRepository;
    @Override
    public DemandeReinitMDP save(DemandeReinitMDP demandeReinitMDP) throws Exception {
        return demandeReinitRepository.save(demandeReinitMDP);
    }

    @Override
    public List<DemandeReinitMDP> findAll() throws Exception {
        return demandeReinitRepository.findAll();
    }

    @Override
    public void deleteById(Long id) throws Exception {
        demandeReinitRepository.deleteById(id);
    }
}
