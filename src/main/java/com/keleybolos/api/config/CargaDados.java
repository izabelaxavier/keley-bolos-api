package com.keleybolos.api.config;

import com.keleybolos.api.domain.Produto;
import com.keleybolos.api.domain.Usuario;
import com.keleybolos.api.repository.ProdutoRepository;
import com.keleybolos.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value; // Importante adicionar este
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.Arrays;

@Configuration
public class CargaDados {

    // Se houver uma variável 'SENHA_SISTEMA' no servidor, ele usa ela.
    // Se não houver, ele usa '123' (valor padrão após os dois pontos).
    @Value("${SENHA_SISTEMA:123}")
    private String senhaProtegida;

    @Bean
    public CommandLineRunner carregarBanco(ProdutoRepository produtoRepository, UsuarioRepository usuarioRepository) {
        return args -> {

            // 1. CARGA DE USUÁRIOS
            if (usuarioRepository.count() == 0) {
                System.out.println("👤 Criando usuários iniciais...");
                // Agora usamos a variável 'senhaProtegida' em vez do texto fixo "123"
                usuarioRepository.save(new Usuario(null, "Keley", "keley", senhaProtegida, "DONA"));
                usuarioRepository.save(new Usuario(null, "Atendente", "func", senhaProtegida, "FUNCIONARIO"));
            }

            // 2. CARGA DE PRODUTOS
            if (produtoRepository.count() > 0) {
                System.out.println("✅ O BANCO JÁ TEM PRODUTOS!");
                return;
            }

            System.out.println("🍰 Carregando cardápio da Keley Bolos...");

            produtoRepository.saveAll(Arrays.asList(
                    criar("Bolo 4 Leites (1.5kg - 15 pessoas)", 125.00),
                    criar("Bolo 4 Leites (2.5kg - 30 pessoas)", 200.00),
                    criar("Bolo 4 Leites (4kg - 50 pessoas)", 290.00),
                    criar("Bolo Creme com Abacaxi (1.5kg - 15 pessoas)", 125.00),
                    criar("Bolo Creme com Abacaxi (2.5kg - 30 pessoas)", 200.00),
                    criar("Bolo Creme com Abacaxi (4kg - 50 pessoas)", 290.00),
                    criar("Bolo 4 Leites com Geleia de Morango (1.5kg - 15 pessoas)", 135.00),
                    criar("Bolo 4 Leites com Geleia de Morango (2.5kg - 30 pessoas)", 210.00),
                    criar("Bolo 4 Leites com Geleia de Morango (4kg - 50 pessoas)", 300.00),
                    criar("Bolo Prestigio (1.5kg - 15 pessoas)", 125.00),
                    criar("Bolo Prestigio (2.5kg - 30 pessoas)", 200.00),
                    criar("Bolo Prestigio (4kg - 50 pessoas)", 290.00),
                    criar("Bolo Ninho com Bombom (1.5kg - 15 pessoas)", 130.00),
                    criar("Bolo Ninho com Bombom (2.5kg - 30 pessoas)", 210.00),
                    criar("Bolo Ninho com Bombom (4kg - 50 pessoas)", 330.00),
                    criar("Bolo 4 Leites com Morango e Brigadeiro (1.5kg - 15 pessoas)", 120.00),
                    criar("Bolo 4 Leites com Morango e Brigadeiro (2.5kg - 30 pessoas)", 200.00),
                    criar("Bolo 4 Leites com Morango e Brigadeiro (4kg - 50 pessoas)", 320.00),
                    criar("Bolo 4 Leites com Brigadeiro (1.5kg - 15 pessoas)", 130.00),
                    criar("Bolo 4 Leites com Brigadeiro (2.5kg - 30 pessoas)", 210.00),
                    criar("Bolo 4 Leites com Brigadeiro (4kg - 50 pessoas)", 330.00),
                    criar("Bolo Chocolatudo (1.5kg - 15 pessoas)", 145.00),
                    criar("Bolo Chocolatudo (2.5kg - 30 pessoas)", 230.00),
                    criar("Bolo Chocolatudo (4kg - 50 pessoas)", 360.00),
                    criar("Bolo de 1kg (Sabor a escolher)", 90.00),
                    criar("Bento Cake (Ninho com Bombom)", 60.00),
                    criar("Bento Cake (Ninho com Geleia de Morango)", 60.00),
                    criar("Bento Cake (Brigadeiro)", 60.00),
                    criar("Bolo Caseiro - Romeu e Julieta", 28.00),
                    criar("Bolo Caseiro - Prestigio", 25.00),
                    criar("Bolo Caseiro - Fuba com Queijo", 15.00),
                    criar("Docinhos Tradicionais (25 und)", 30.00),
                    criar("Docinhos Tradicionais (50 und)", 60.00),
                    criar("Docinhos Tradicionais (75 und)", 90.00),
                    criar("Docinhos Tradicionais (100 und)", 110.00),
                    criar("Docinhos Especiais (25 und)", 35.00),
                    criar("Docinhos Especiais (50 und)", 70.00),
                    criar("Docinhos Especiais (75 und)", 105.00),
                    criar("Docinhos Especiais (100 und)", 140.00),
                    criar("Bombons (1/2 Cento)", 100.00),
                    criar("Bombons (Cento)", 190.00),
                    criar("ADICIONAL / PERSONALIZADO", 0.00)
            ));

            System.out.println("✅ BANCO INICIADO COM USUÁRIOS E PRODUTOS!");
        };
    }

    private Produto criar(String nome, double preco) {
        Produto p = new Produto();
        p.setNome(nome);
        p.setPreco(new BigDecimal(preco));
        return p;
    }
}