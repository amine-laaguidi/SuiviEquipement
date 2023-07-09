package com.example.t2s.accessoire;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccessoireRepository extends JpaRepository<Accessoire,Long> {
    Optional<Accessoire> findAccessoireByRef(String ref) throws Exception;
}
