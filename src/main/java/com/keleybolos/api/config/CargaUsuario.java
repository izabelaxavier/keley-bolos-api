package com.keleybolos.api.config;

import com.keleybolos.api.domain.Usuario;
import com.keleybolos.api.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CargaUsuario {

    @Bean
    CommandLineRunner carregarUsuarios(UsuarioRepository repository) {
        return args -> {
            // Cria a conta da Dona se não existir
            if (repository.count() == 0) {
                repository.save(new Usuario(null, "Keley", "keley", "123", "DONA"));
                repository.save(new Usuario(null, "Atendente", "func", "123", "FUNCIONARIO"));
            }
        };
    }
}