package com.keleybolos.api.controller;

import com.keleybolos.api.domain.Cliente;
import com.keleybolos.api.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository repository;

    // Buscar todos os clientes
    @GetMapping
    public List<Cliente> listar() {
        return repository.findAll();
    }

    // Salvar um novo cliente
    @PostMapping
    public Cliente cadastrar(@RequestBody Cliente cliente) {
        return repository.save(cliente);
    }
}