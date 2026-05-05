package com.keleybolos.api.controller;

import com.keleybolos.api.domain.Usuario;
import com.keleybolos.api.dto.LoginResponse;
import com.keleybolos.api.repository.UsuarioRepository;
import com.keleybolos.api.security.TokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder encoder;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> logar(@RequestBody Usuario dados) {

        Usuario user = repository.findByLogin(dados.getLogin());

        if (user == null) {
            return ResponseEntity.status(401).body("Usuário não encontrado");
        }

        if (!encoder.matches(dados.getSenha(), user.getSenha())) {
            return ResponseEntity.status(401).body("Senha inválida");
        }

        String token = tokenService.gerarToken(
                user.getLogin(),
                user.getRole()
        );

        return ResponseEntity.ok(new LoginResponse(token));
    }
}