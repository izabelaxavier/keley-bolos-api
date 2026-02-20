package com.keleybolos.api.config;

import com.keleybolos.api.domain.Usuario;
import com.keleybolos.api.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CargaUsuario {

    @Bean
    CommandLineRunner initDatabase(UsuarioRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                // Criando o seu acesso padrão
                Usuario admin = new Usuario();
                admin.setLogin("izabela"); // Pode mudar para o que quiser
                admin.setSenha("12345");   // Por enquanto sem criptografia para facilitar
                repository.save(admin);
                System.out.println("USUÁRIO IZABELA CRIADO COM SUCESSO!");
            }
        };
    }
}