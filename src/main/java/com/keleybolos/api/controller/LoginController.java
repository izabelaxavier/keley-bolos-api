package com.keleybolos.api.controller;

import com.keleybolos.api.domain.Usuario;
import com.keleybolos.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private UsuarioRepository repository;

    @PostMapping("/login")
    public ResponseEntity<?> logar(@RequestBody Usuario dados) {
        // Busca o usuário no banco pelo login
        Optional<Usuario> user = Optional.ofNullable(repository.findByLogin(dados.getLogin()));

        // Verifica se existe e se a senha bate
        if (user.isPresent() && user.get().getSenha().equals(dados.getSenha())) {
            return ResponseEntity.ok(user); // Retorna o usuário com o cargo (DONA ou FUNCIONARIO)
        }

        return ResponseEntity.status(401).body("Usuário ou senha inválidos");
    }
}