package com.keleybolos.api.repository;

import com.keleybolos.api.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // É essa linha aqui que estava faltando:
    Cliente findByTelefone(String telefone);
}