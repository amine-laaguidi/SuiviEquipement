package com.example.t2s.accessoire;

import java.util.List;
import java.util.Optional;

public interface AccessoireService {
    Accessoire save(Accessoire accessoire) throws Exception;
    Accessoire deleteById(Long idA) throws Exception;
    Optional<Accessoire> findById(Long idA) throws Exception;
    List<Accessoire> findAll() throws Exception;
}
